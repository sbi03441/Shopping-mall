package com.b2.prj02.service.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucketName}")
    private String bucketName;


    public String uploadFileAndGetUrl(MultipartFile multipartFile) {
        try {
//            1. 로컬에 저장할 파일 경로를 생성합니다.
            Path localFilePath = Paths.get("C:/Project/BackEnd/image").resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));

//            2. multipartFile의 입력 스트림을 읽어와서 로컬 파일 경로에 저장합니다.
            Files.copy(multipartFile.getInputStream(), localFilePath, StandardCopyOption.REPLACE_EXISTING);

//            3. AWS S3에 접근하기 위한 클라이언트를 생성합니다
            S3Client s3Client = S3Client.builder()
                    .region(Region.of(region)) // 사용할 AWS 리전을 설정합니다.
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey))) //  AWS 자격 증명을 제공합니다.
                    .build();

//            4. S3에 업로드할 때 사용할 객체 키 생성 - localFilePath.getFileName()을 사용하여 로컬 파일 경로의 파일 이름을 가져와서 "uploads/"와 결합하여 객체 키를 생성합니다.
            String objectKey = "uploads/" + localFilePath.getFileName();

//            5. S3에 파일 업로드 - 업로드할 버킷과 객체 키를 지정합니다.
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build(), localFilePath); //로컬 파일을 업로드

//            6. 파일 업로드 완료 후 URL 생성 - 생성된 URL은 업로드된 파일에 접근할 수 있는 주소입니다.
//            builder -> builder.bucket(bucketName).key(objectKey).build()를 사용하여 버킷과 객체 키를 지정합니다.
            URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(objectKey).build());

//            7. 최종적으로 생성된 URL을 문자열로 변환하여 반환합니다.
//            이 URL을 사용하면 S3에 업로드된 파일에 웹에서 접근할 수 있습니다.
            return url.toString();

        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리를 수행하거나 로깅할 수 있습니다.
            System.err.println("Exception while uploading file: " + e.getMessage());
            return "없음";
        }
    }

//    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
//        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        file.transferTo(convertedFile);
//        return convertedFile;
//    }

//    public void deleteFileFromS3(String fileUrl) {
//        String key = extractKeyFromUrl(fileUrl); // URL에서 키(파일 경로 및 이름) 추출
//        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
//    }
//
//    // URL에서 키(파일 경로 및 이름)를 추출하는 메서드
//    private String extractKeyFromUrl(String fileUrl) {
//        try {
//            URL url = new URL(fileUrl);
//            return URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8).substring(1); // 앞의 '/' 제거
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException("Invalid file URL");
//        }
//    }
}
