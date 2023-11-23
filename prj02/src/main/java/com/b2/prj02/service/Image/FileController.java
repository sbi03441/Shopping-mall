package com.b2.prj02.service.Image;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileUploadService.processFile(file);
            return ResponseEntity.ok("File uploaded successfully. URL: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed." + e.getMessage());
        }
    }

//    @PutMapping("/update")
//    public ResponseEntity<String> handleFileUpdate(@RequestParam("url") String existingFileUrl,
//                                                   @RequestParam("file") MultipartFile newFile) {
//        try {
//            fileUploadService.updateFile(existingFileUrl, newFile);
//            return ResponseEntity.ok("File updated successfully.");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File update failed.");
//        }
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseEntity<String> handleFileDelete(@RequestParam("url") String fileUrl) {
//        fileUploadService.deleteFile(fileUrl);
//        return ResponseEntity.ok("File deleted successfully.");
//    }
}
