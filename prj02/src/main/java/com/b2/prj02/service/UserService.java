package com.b2.prj02.service;

import com.b2.prj02.dto.UserDeleteRequestDTO;
import com.b2.prj02.dto.UserLoginRequestDTO;
import com.b2.prj02.dto.UserSignupRequestDTO;
import com.b2.prj02.entity.User;
import com.b2.prj02.repository.ProfileRepository;
import com.b2.prj02.role.UserStatus;
import com.b2.prj02.repository.UserRepository;
import com.b2.prj02.service.jwt.JwtTokenProvider;
import com.b2.prj02.service.jwt.TokenBlacklist;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final LockedUser lockedUser;
    public ResponseEntity<?> signup(UserSignupRequestDTO user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            User newUser = User.builder()
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .address(user.getAddress())
                    .gender(user.getGender())
                    .nickName(user.getNickName())
                    .stack(0)
                    .build();

            String status = user.getStatus();

            switch (status){
                case "USER" :
                    newUser.updateStatus(UserStatus.USER);
                    userRepository.save(newUser);
                    return ResponseEntity.status(200).body(newUser.getNickName() + " 님 회원 가입을 축하드립니다.");

                case "SELLER" :
                    newUser.updateStatus(UserStatus.SELLER);
                    userRepository.save(newUser);
                    return ResponseEntity.status(200).body(newUser.getNickName() + " 님 회원 가입을 축하드립니다.");

                default:
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("없는 Status입니다.");
            }
        }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이미 가입된 정보입니다.");
    }

    public ResponseEntity<?> login(UserLoginRequestDTO user) {
        Optional<User> loginUser = userRepository.findByEmail(user.getEmail());

        if (loginUser.isEmpty())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이메일을 다시 확인해주세요.");


        if (loginUser.get().getStack() >= 5)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("5회 이상 오류로 인해 접속이 1분간 불가 합니다");


        if (!passwordEncoder.matches(user.getPassword(), loginUser.get().getPassword())) {
            lockedUser.addToFailedStack(loginUser.get());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호를 다시 확인해주세요.");
        }

        loginUser.get().resetStack();
        userRepository.save(loginUser.get());
        String newToken = jwtTokenProvider.createToken(user.getEmail(), loginUser.get().getStatus());
        if(jwtTokenProvider.findStatusBytoken(newToken).equals("DELETED")) {
            TokenBlacklist.addToBlacklist(newToken);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원 탈퇴한 유저입니다.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        Map<String, String> response = new HashMap<>();
        response.put("email", loginUser.get().getEmail());
        response.put("address", loginUser.get().getAddress());
        response.put("staus", loginUser.get().getStatus().name());
        response.put("nickname", loginUser.get().getNickName());
//        login.put("profileimage", loginUser.get().getProfileimage());

        return ResponseEntity.status(200).headers(headers).body(response);
    }


    public ResponseEntity<?> logout(String token) {
        try {
            String userEmail = jwtTokenProvider.findEmailBytoken(token);
            if (userRepository.findByEmail(userEmail).isEmpty()) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("로그아웃에 실패하셨습니다.");
            TokenBlacklist.addToBlacklist(token);
            return ResponseEntity.status(200).body("이용해 주셔서 감사합니다.");
        } catch (Exception e) {
            e.getMessage();
            throw new MalformedJwtException("로그아웃에 실패하셨습니다.");
        }
    }


    public ResponseEntity<?> deleteUser(String token, UserDeleteRequestDTO deleteUser) {
        String email = jwtTokenProvider.findEmailBytoken(token);
        Optional<User> storedUser = userRepository.findByEmail(email);

        User updatedUser = storedUser.map(user -> user.updateStatus(UserStatus.DELETED))
                .orElseThrow(() -> new RuntimeException("없는 유저입니다."));

        if(email.equals(deleteUser.getEmail()) && passwordEncoder.matches(deleteUser.getPassword(), storedUser.get().getPassword())){
            userRepository.save(updatedUser);
            return ResponseEntity.status(200).body("회원 탈퇴되셨습니다.");
        }else return ResponseEntity.status(HttpStatus.FORBIDDEN).body("이메일 또는 비밀번호가 틀렸습니다.");
    }



    @Transactional
    public void saveImage(MultipartFile file, String token) throws IOException {
        Path filePath = Paths.get("C:\\Project\\BackEnd\\image").resolve(Objects.requireNonNull(file.getOriginalFilename()));
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String email = jwtTokenProvider.findEmailBytoken(token);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            user.get().setFilePath(filePath.toString());
            userRepository.save(user.get());
        }
    }

//    public ResponseEntity<String> saveImage2(MultipartFile file, User user) throws IOException {
//        // 예시: 파일 시스템에 저장
//        Path filePath = Paths.get("C:\\Project\\BackEnd\\image").resolve(Objects.requireNonNull(file.getOriginalFilename()));
//        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//        // 예시: 데이터베이스에 저장
//        user.setFilePath(filePath.toString());
//        userRepository.save(user);
//        return ResponseEntity.status(200).body("ok");
//    }
}