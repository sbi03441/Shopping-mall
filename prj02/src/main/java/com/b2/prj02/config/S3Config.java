package com.b2.prj02.config;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {
    @Bean
    public AmazonS3Client s3Client() {
        // S3Client를 만들어서 반환
        return S3Client.builder()
                .region("your-region")  // 본인이 사용하는 AWS 리전으로 변경
                .build();
    }
}