package com.b2.prj02.service.User;

import com.b2.prj02.entity.User;
import com.b2.prj02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class LockedUser {
    private final UserRepository userRepository;
    private Set<String> failedStack = new HashSet<>();

    public void addToFailedStack(User user) {
        failedStack.add(user.getEmail());
        user.addStack();
        userRepository.save(user);

        if (user.getStack() == 5)
            lockUser(user);
    }

    public void lockUser(User user) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            failedStack.remove(user.getEmail());
            user.resetStack();
            userRepository.save(user);
        }, 1, TimeUnit.MINUTES);
    }
}