package com.b2.prj02.controller;

import com.b2.prj02.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
@CrossOrigin(origins = "http://localhost:5173")
public class ShopController {
    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> get(){
        return ResponseEntity.status(200).body(userRepository.findAll());
    }
}
