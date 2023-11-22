package com.b2.prj02.controller;

import com.b2.prj02.dto.request.UserDeleteRequestDTO;
import com.b2.prj02.dto.request.UserLoginRequestDTO;
import com.b2.prj02.dto.request.UserSignupRequestDTO;
import com.b2.prj02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
public class UserController {
    private final UserService userService;
//***** 회원가입 *****

//1. 유저 정보를 DTO로 받아들임
//2. 해당 유저 정보를 DB와 비교
//3. 없는 유저일 시 password Encoding 후 DB에 Save

    @PostMapping("/signup")
    public ResponseEntity<?> userSignup(@RequestBody UserSignupRequestDTO user){
        return userService.signup(user);
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

    // 수정
//    @PostMapping("/login")
//    public ResponseEntity<?> userLogin(@RequestBody UserLoginRequestDTO user){
//        return userService.login(user.getEmail(), user.getPassword());
//    }

//***** 로그아웃 *****

//1. 로그아웃 시 유저의 Token을 blacklist에 추가

    @PostMapping("/logout")
    public ResponseEntity<?> userLogout(@RequestHeader("X-AUTH-TOKEN") String token){
        return userService.logout(token);
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

