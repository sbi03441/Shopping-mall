package com.b2.prj02.service.User;

import com.b2.prj02.entity.User;
import com.b2.prj02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;

@Component
@RequiredArgsConstructor
public class LockedUser {
    private final UserRepository userRepository;
    private Set<String> failedStack = new HashSet<>();

    public void addToFailedStack(User user) {
        failedStack.add(user.getEmail());
        user.addStack();
        userRepository.save(user);
        if(user.getStack()>=5)
            lockUser(user);
    }

    public void lockUser(User user){
        // 5분 후에 계정 잠금 상태를 해제하는 타이머 설정
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                failedStack.remove(user.getEmail());
                user.resetStack();
            }
        }, 5 * 60 * 1000); // 5분 후에 실행
    }
}
