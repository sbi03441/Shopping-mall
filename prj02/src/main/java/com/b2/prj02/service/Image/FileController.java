package com.b2.prj02.service.Image;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKeyId, secretKey))
                .build();

        try {
            // S3에 파일 업로드
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(file.getOriginalFilename())
                    .build(), file.getInputStream());

            return new ResponseEntity<>("File uploaded successfully!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to upload the file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

