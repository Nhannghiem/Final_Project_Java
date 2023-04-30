package com.example.demo.service;

import com.example.demo.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface FileService {

    FileEntity saveFile(MultipartFile multipartFile, Long id);


    Optional<FileEntity> getFile(Long id);

    List<FileEntity> getAllFile(Long postId);

    void deleteFiles(Long id);

}
