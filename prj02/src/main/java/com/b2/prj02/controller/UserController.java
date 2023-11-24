package com.b2.prj02.controller;


import com.b2.prj02.Exception.NotFoundException;
import com.b2.prj02.dto.request.UserDeleteRequestDTO;
import com.b2.prj02.dto.request.UserLoginRequestDTO;
import com.b2.prj02.dto.request.UserSignupRequestDTO;
import com.b2.prj02.entity.User;
import com.b2.prj02.repository.UserRepository;
import com.b2.prj02.service.User.UserService;
import com.b2.prj02.service.jwt.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

//***** 회원가입 *****

    //1. 유저 정보를 DTO로 받아들임
//2. 해당 유저 정보를 DB와 비교
//3. 없는 유저일 시 password Encoding 후 DB에 Save
    @Transactional
    @PostMapping(value = "/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserSignupRequestDTO user){
        return userService.signup(user);
    }

    @Transactional
    @PostMapping(value = "/signup/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> saveImage(@RequestHeader("X-AUTH-TOKEN") String token,
                                       @RequestParam("file") MultipartFile file) throws IOException {
        User user = userService.checkUser(token);
        String url = userService.saveImage(file, user);
        return ResponseEntity.status(200).body(url);
    }

    @PostMapping("/signup/dupEmail")
    public Boolean checkEmail(@RequestBody String email){
        return userService.checkEmail(email);
    }

    @Transactional
    @PostMapping(value = "/signup-image")
    public ResponseEntity<?> userSignupImage(@RequestPart(value = "user") UserSignupRequestDTO user,
                                             @RequestPart(value = "file") MultipartFile file) throws IOException {
        return userService.signup(user, file);
    }



//***** 로그인 *****

    //1. 유저 email & password 받아들임
//2. 해당 email의 DB에 저장된 password를 decoding 후 해당 password 비교
//3. 일치 시 Refresh Token 유무 확인
//4-1. Refresh Token이 없다면 유저 정보를 저장한 Claims로 Access토큰과 Refresh토큰 발급
//4-2. Refresh Token이 있다면 해당 Refresh Token의 Claims로 Access토큰 재발급
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequestDTO user){
        return userService.login(user);
    }

//***** 로그아웃 *****

//1. 로그아웃 시 유저의 Token을 blacklist에 추가

    @PostMapping("/logout")
    public ResponseEntity<?> userLogout(@RequestHeader("X-AUTH-TOKEN") String token){
        return userService.logout(token);
    }


    @GetMapping("/blacklist")
    public Set<String> getBlackList(){
        return TokenBlacklist.getBlacklist();
    }

//***** 회원 탈퇴 *****

    //1. 해당 유저의 Token값과 유저 정보를 받음
//2. 유저 일치 여부 확인 (DB에서 유저 확인 & Token의 sub와 유저 정보 매치)
//3. 해당 유저 확인 시 status를 DELETED로 변경
//4. 해당 유저의 Refresh Token과 Access Token을 blacklist에 추가
    @PutMapping("/delete")
    public ResponseEntity<?> userDelete(@RequestHeader("X-AUTH-TOKEN") String token,
                                        @RequestBody UserDeleteRequestDTO deleteUser){
        return userService.deleteUser(token, deleteUser);
    }
}