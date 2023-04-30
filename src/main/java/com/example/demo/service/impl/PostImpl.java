package com.example.demo.service.impl;

import com.example.demo.entity.PostEntity;
import com.example.demo.form.PostForm;
import com.example.demo.model.PostDashboardReponse;
import com.example.demo.reponsitory.PostReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.reponsitory.TopicReponsitory;

import com.example.demo.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostImpl implements PostService {



    @Autowired
    private PostReponsitory postReponsitory;


    @Autowired
    private UsersReponsitory usersReponsitory;

    @Autowired
    private TopicReponsitory topicReponsitory;


    @Override
    public List<PostEntity> findByTopicIdAndIsActive(Long id) {

        return postReponsitory.findAllByTopicAndIsActive(id);
    }

    @Override
    public PostEntity createPost(PostForm postForm,Long id) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        PostEntity postEntity = new PostEntity();
        postEntity.setContent(postForm.getContent());
        postEntity.setUsers(usersReponsitory.findByEmail(userEmail));
        postEntity.setTitle(postForm.getTitle());
        postEntity.setIsActive(1);
        postEntity.setCreateAt(LocalDateTime.now());
        postEntity.setTopic(topicReponsitory.findById(id).orElseThrow());

        return postReponsitory.save(postEntity);
    }

    @Override
    public PostEntity editPost(PostForm postForm) {
       PostEntity post = new PostEntity();
       post.setId(postForm.getId());
       post.setTitle(postForm.getTitle());
       post.setContent(postForm.getContent());
       post.setUsers(postForm.getUsers());
       post.setTopic(postForm.getTopic());
       post.setIsActive(postForm.getIsActive());
       post.setCreateAt(LocalDateTime.now());

        return postReponsitory.save(post);
    }

    @Override
    public void deleteSoftPost(PostEntity post) {
        post.setIsActive(0);
        postReponsitory.save(post);
    }



    @Override
    public PostEntity findById(Long id) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return postReponsitory.findByIdAndUsers_Email(id, userEmail);
    }

    @Override
    public void permanentlyDeleted(Long id) {
        postReponsitory.deleteById(id);
    }

    @Override
    public List<PostEntity> findAllByUserId(Long id) {
        return postReponsitory.findAllByUsers(id);
    }

    @Override
    public Integer countContribution(Long id) {
        Integer post  = postReponsitory.countContributions(id);
        if (post != null){
            return post;
        }
        return 0;

    }

    @Override
    public List<PostEntity> findAllPostByUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return postReponsitory.findAllByUserIAndIsActive(usersReponsitory.findByEmail(userEmail).getId());
    }

    @Override
    public List<PostDashboardReponse> countPostMonth() {
        return postReponsitory.countPostMonthly().stream().map(postDashboardData -> new PostDashboardReponse(postDashboardData.getCreateAt(), postDashboardData.getCounts())).collect(Collectors.toList());
    }

    @Override
    public PostEntity restorePostMyself(Long id) {
        PostEntity postEntity = postReponsitory.findById(id).orElseThrow();
        postEntity.setIsActive(1);
        return postReponsitory.save(postEntity);
    }

    @Override
    public PostEntity findAPost(Long id) {
        return postReponsitory.findById(id).orElseThrow();
    }
}
