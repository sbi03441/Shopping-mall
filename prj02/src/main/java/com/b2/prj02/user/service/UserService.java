package com.b2.prj02.user.service;

import com.b2.prj02.user.dto.UserDeleteRequestDTO;
import com.b2.prj02.user.dto.UserLoginRequestDTO;
import com.b2.prj02.user.dto.UserSignupRequestDTO;
import com.b2.prj02.user.entity.User;

import com.b2.prj02.user.role.UserActiveStatus;
import com.b2.prj02.user.repository.UserRepository;
import com.b2.prj02.user.service.Image.S3Service;
import com.b2.prj02.config.security.jwt.JwtTokenProvider;
import com.b2.prj02.config.security.jwt.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final LockedUser lockedUser;
    private final S3Service s3Service;

    public User signup(UserSignupRequestDTO userData) {
        if(!checkUser(userData.getEmail())){
            throw new DataIntegrityViolationException("이미 존재하는 이메일입니다.");
        }
        return User.builder()
                .email(userData.getEmail())
                .password(passwordEncoder.encode(userData.getPassword()))
                .address(userData.getAddress())
                .gender(userData.getGender())
                .nickName(userData.getNickName())
                .filePath(userData.getFilePath())
                .userRole(userData.getUserRole())
                .userActiveStatus(UserActiveStatus.ACTIVE)
                .stack(0)
                .build();
    }


    public  Map<String, String> login(UserLoginRequestDTO user) {
        User loginUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("이메일을 다시 확인해주세요.")
        );

        if(loginUser.getUserActiveStatus().equals(UserActiveStatus.DELETED)){
            throw new DisabledException("회원 탈퇴된 계정입니다.");
        }

        if (loginUser.getStack() >= 5) {
            throw new LockedException("로그인 5회 이상 오류로 인해 접속이 1분간 불가 합니다.");
        }

        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            lockedUser.addToFailedStack(loginUser);
            throw new BadCredentialsException("비밀번호를 다시 확인해주세요.");
        }

        loginUser.resetStack();
        userRepository.save(loginUser);

        String newToken = jwtTokenProvider.createToken(loginUser);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(newToken);

        Map<String, String> response = new HashMap<>();
        response.put("email", loginUser.getEmail());
        response.put("address", loginUser.getAddress());
        response.put("userRole", loginUser.getUserRole().name());
        response.put("nick_name", loginUser.getNickName());
        response.put("file_path", loginUser.getFilePath());

        return response;
    }


    public void logout(String token) {
        checkToken(token);
        TokenBlacklist.addToBlacklist(token);
    }

    public User deleteUser(String token, UserDeleteRequestDTO deleteUser) {
        User user = checkToken(token);

        if (user.getEmail().equals(deleteUser.getEmail()) && passwordEncoder.matches(deleteUser.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("이메일과 비밀번호를 다시 확인해주세요.");
        }
        user.deleteUser();
        TokenBlacklist.addToBlacklist(token);
        userRepository.save(user);
        return user;
    }

    public String saveImage(MultipartFile file) throws IOException {
        return s3Service.uploadFileAndGetUrl(file);
    }

    public String saveImageToToken(MultipartFile file, User user) throws IOException {
        String url = s3Service.uploadFileAndGetUrl(file);
        user.setFilePath(url);
        userRepository.save(user);

        return url;
    }

    public User checkToken(String token) {
        String email = jwtTokenProvider.findEmailBytoken(token);
        return userRepository.findByEmail(email).orElseThrow(
                () ->  new UsernameNotFoundException("로그인을 다시 해주세요.")
        );
    }

    public Boolean checkUser(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.isEmpty() || user.get().getUserActiveStatus().equals(UserActiveStatus.DELETED);
    }
}
