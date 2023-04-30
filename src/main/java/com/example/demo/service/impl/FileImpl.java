package com.example.demo.service.impl;

import com.example.demo.entity.FileEntity;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.TopicEntity;
import com.example.demo.reponsitory.FileReponsitory;
import com.example.demo.reponsitory.PostReponsitory;
import com.example.demo.reponsitory.TopicReponsitory;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FileImpl implements FileService {

    @Autowired
    private FileReponsitory fileReponsitory;

    @Autowired
    private PostReponsitory postReponsitory;

    @Override
    public FileEntity saveFile(MultipartFile multipartFile, Long id) {
        FileEntity fileEntity = new FileEntity();
        PostEntity post = postReponsitory.findById(id).orElseThrow();
        String docName = multipartFile.getOriginalFilename();
        try{
            fileEntity.setFileName(docName);
            fileEntity.setFileType(multipartFile.getContentType());
            fileEntity.setData(multipartFile.getBytes());
            fileEntity.setPost(post);

            return fileReponsitory.save(fileEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<FileEntity> getFile(Long id) {
        return fileReponsitory.findById(id);
    }

    @Override
    public List<FileEntity> getAllFile(Long postId) {
        return null;
    }

    @Override
    public void deleteFiles(Long id) {
        fileReponsitory.deleteById(id);

    }
}
