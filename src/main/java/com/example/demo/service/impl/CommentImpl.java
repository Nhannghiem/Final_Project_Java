package com.example.demo.service.impl;

import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.PostEntity;
import com.example.demo.form.CommentForm;
import com.example.demo.form.PostForm;
import com.example.demo.reponsitory.CommentReponsitory;
import com.example.demo.reponsitory.PostReponsitory;

import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentImpl implements CommentService {

    @Autowired
    private CommentReponsitory commentReponsitory;

    @Autowired
    private UsersReponsitory usersReponsitory;


    @Autowired
    private PostReponsitory postReponsitory;



    @Override
    public List<CommentEntity> findByPostId(Long id) {
        return null;
    }

    @Override
    public CommentEntity createComment(CommentForm commentForm,Long id) {


        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCreateAt(LocalDateTime.now());
        commentEntity.setContent(commentForm.getContent());
        commentEntity.setUser(usersReponsitory.findByEmail(userEmail));
        commentEntity.setPost(postReponsitory.findById(id).orElseThrow());
        return commentReponsitory.save(commentEntity);
    }

    @Override
    public CommentEntity editComment(CommentEntity commentEntity) {
        commentEntity.setId(commentEntity.getId());
        commentEntity.setContent(commentEntity.getContent());
        return commentReponsitory.save(commentEntity);
    }

    @Override
    public void deleteComment(Long id, String userEmail) {
        commentReponsitory.deleteByIdAndUser_Email(id, userEmail);
    }

    @Override
    public CommentEntity findById(Long id) {
        return commentReponsitory.findById(id).orElseThrow();
    }
}
