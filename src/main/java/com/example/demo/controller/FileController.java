package com.example.demo.controller;

import com.example.demo.entity.FileEntity;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/dowload/{id}")
    public ResponseEntity<ByteArrayResource> dowoad(@PathVariable("id") Long id){
        FileEntity fileEntity = fileService.getFile(id).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"attachment:filename=\""+fileEntity.getFileName()+"\"")
                .body(new ByteArrayResource(fileEntity.getData()));


    }

    @GetMapping("/deleteFile/{id}/post/{post}")
    public String delete(@PathVariable("id") Long id, @PathVariable("post") Long postId){
        fileService.deleteFiles(id);
        return "redirect:/go_edit_post/?id=" + postId;
    }


}
