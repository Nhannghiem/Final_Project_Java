package com.example.demo.service.impl;

import com.example.demo.service.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;

public class FileUpload {

    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static String copyFile(MultipartFile file) throws Exception {
        String url = null;
        String fileName = Instant.now().toEpochMilli() + file.getOriginalFilename();

        try (InputStream is = file.getInputStream()) {
            Path path = FileUtil.getImagePath(fileName);

            Files.copy(is, path);

            url = FileUtil.getImageUrl(fileName);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new Exception("Failed to upload!");
        }

        return url;
    }
}
