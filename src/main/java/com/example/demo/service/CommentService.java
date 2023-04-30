package com.example.demo.service;

import com.example.demo.entity.CommentEntity;
import com.example.demo.form.CommentForm;

import java.util.List;

public interface CommentService {

    List<CommentEntity> findByPostId(Long id);

    CommentEntity createComment(CommentForm commentForm, Long id);

    CommentEntity editComment(CommentEntity commentEntity);

    void deleteComment(Long id, String userId);

    CommentEntity findById(Long id);
}
