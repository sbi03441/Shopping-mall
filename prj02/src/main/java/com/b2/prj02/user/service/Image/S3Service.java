package com.b2.prj02.user.service.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


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

//    3. AWS S3에 접근하기 위한 클라이언트를 생성합니다
    private S3Client buildS3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    private String generateUniqueKey(String originalFilename) {
        // 고유한 키 생성 로직 (예: UUID 사용)
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }

    public String uploadFileAndGetUrl(MultipartFile file) throws IOException {
//            1. AWS S3에 접근하기 위한 클라이언트를 생성합니다
        S3Client s3Client = buildS3Client();
//            2. S3에 업로드할 때 사용할 객체 키 생성 - localFilePath.getFileName()을 사용하여 로컬 파일 경로의 파일 이름을 가져와서 "uploads/"와 결합하여 객체 키를 생성합니다.
        String objectKey = generateUniqueKey(file.getOriginalFilename());

//            3. S3에 파일 업로드 - 업로드할 버킷과 객체 키를 지정합니다.
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build(), RequestBody.fromBytes(file.getBytes())); //로컬 파일을 업로드

//            4. 파일 업로드 완료 후 URL 생성 - 생성된 URL은 업로드된 파일에 접근할 수 있는 주소입니다.
//            builder -> builder.bucket(bucketName).key(objectKey).build()를 사용하여 버킷과 객체 키를 지정합니다.
        URL url = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(objectKey).build());

//            5. 최종적으로 생성된 URL을 문자열로 변환하여 반환합니다.
//            이 URL을 사용하면 S3에 업로드된 파일에 웹에서 접근할 수 있습니다.
        return url.toString();
    }

//    public void deleteFileFromS3(String fileUrl) {
//        S3Client s3Client = buildS3Client();
//        String objectKey = getObjectKeyFromUrl(fileUrl); // URL에서 키(파일 경로 및 이름) 추출
//        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build());
//    }
//
    // URL에서 키(파일 경로 및 이름)를 추출하는 메서드
//    public String getObjectKeyFromUrl(String imageUrl) {
//        // 예: https://your-bucket-name.s3.amazonaws.com/uploads/filename.ext
//        String[] parts = imageUrl.split("/");
//        List<String> partList = Arrays.asList(parts);
//        return String.join("/", partList.subList(3, partList.size()));
//    }
}

