package com.b2.prj02.service.Image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Service
public class S3Service {

    @Value("${aws.s3.bucketName}")
    private String bucketName; // 본인의 S3 버킷 이름으로 변경

    @Value(("${aws.region}"))
    private Region region;

    private final S3Client s3Client;

    public S3Service() {
        this.s3Client = S3Client.builder()
                .region(region) // 본인이 사용하는 AWS 리전으로 변경
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    public String uploadFileToS3(MultipartFile file) throws IOException {
        File convertedFile = convertMultipartFileToFile(file);

        String key = "uploads/" + convertedFile.getName(); // S3에 저장될 경로 및 파일 이름 지정
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(), convertedFile.toPath());

        URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(key).build());

        return url.toString();
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(convertedFile);
        return convertedFile;
    }
}
