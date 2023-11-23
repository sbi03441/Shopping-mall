package com.b2.prj02.service.Image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final S3Service s3Service;

    public String processFile(MultipartFile file) throws IOException {
        return s3Service.uploadFileAndGetUrl(file);
    }

//    public void updateFile(String existingFileUrl, MultipartFile newFile) throws IOException {
//        // 기존 파일 삭제
//        s3Service.deleteFileFromS3(existingFileUrl);
//
//        // 새로운 파일 업로드
//        s3Service.uploadFileToS3(newFile);
//    }
//
//    public void deleteFile(String fileUrl) {
//        s3Service.deleteFileFromS3(fileUrl);
//    }
}
