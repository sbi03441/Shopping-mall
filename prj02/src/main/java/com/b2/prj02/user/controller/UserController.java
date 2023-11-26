package com.b2.prj02.user.controller;

import com.b2.prj02.user.dto.request.UserDeleteRequestDTO;
import com.b2.prj02.user.dto.request.UserLoginRequestDTO;
import com.b2.prj02.user.dto.request.UserSignupRequestDTO;
import com.b2.prj02.user.dto.response.UserLoginResponseDTO;
import com.b2.prj02.user.entity.User;
import com.b2.prj02.user.repository.UserRepository;
import com.b2.prj02.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
//***** 회원가입 *****

//1. 유저 정보를 DTO로 받아들임
//2. 해당 유저 정보를 DB와 비교
//3. 없는 유저일 시 password Encoding 후 DB에 Save
    @Transactional
    @PostMapping(value = "/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserSignupRequestDTO user){
        User newUser = userService.signup(user);
        userRepository.save(newUser);
        return ResponseEntity.status(200).body(newUser);
    }

    @Transactional
    @PostMapping(value = "/signup/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveImage(@RequestParam("file") MultipartFile file) throws IOException {
        String url = userService.saveImage(file);
        return ResponseEntity.status(200).body(url);
    }

    @PostMapping("/signup/dupEmail")
    public Boolean checkEmail(@RequestBody String email){
        return userService.checkUser(email);
    }


    @Transactional
    @PostMapping(value = "/signup/image/token",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveImageToToken(@RequestHeader("X-AUTH-TOKEN") String token,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        User user = userService.checkToken(token);
        String url = userService.saveImageToToken(file, user);
        return ResponseEntity.status(200).body(url);
    }



//***** 로그인 *****

//1. 유저 email & password 받아들임
//2. 해당 email의 DB에 저장된 password를 decoding 후 해당 password 비교
//3. 일치 시 Refresh Token 유무 확인
//4-1. Refresh Token이 없다면 유저 정보를 저장한 Claims로 Access토큰과 Refresh토큰 발급
//4-2. Refresh Token이 있다면 해당 Refresh Token의 Claims로 Access토큰 재발급
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequestDTO user){
        UserLoginResponseDTO loginUser = userService.login(user);

        Map<String, String> response = new HashMap<>();
        response.put("email", loginUser.getEmail());
        response.put("nick_name", loginUser.getNickName());
        response.put("address", loginUser.getAddress());
        response.put("userRole", loginUser.getUserRole().name());
        response.put("gender", loginUser.getGender());
        response.put("file_path", loginUser.getFilePath());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginUser.getToken());

        return ResponseEntity.status(200).headers(headers).body(response);
    }

//***** 로그아웃 *****

//1. 로그아웃 시 유저의 Token을 blacklist에 추가

    @PostMapping("/logout")
    public ResponseEntity<?> userLogout(@RequestHeader("X-AUTH-TOKEN") String token){
        userService.logout(token);
        return ResponseEntity.status(200).body("로그아웃이 성공적으로 완료되었습니다.");
    }


//***** 회원 탈퇴 *****

//1. 해당 유저의 Token값과 유저 정보를 받음
//2. 유저 일치 여부 확인 (DB에서 유저 확인 & Token의 sub와 유저 정보 매치)
//3. 해당 유저 확인 시 status를 DELETED로 변경
//4. 해당 유저의 Refresh Token과 Access Token을 blacklist에 추가
    @Transactional
    @PutMapping("/delete")
    public ResponseEntity<?> userDelete(@RequestHeader("X-AUTH-TOKEN") String token,
                                        @RequestBody UserDeleteRequestDTO deleteUser){
        User deletedUser = userService.deleteUser(token, deleteUser);
        return ResponseEntity.status(200).body(deletedUser);
    }
}

