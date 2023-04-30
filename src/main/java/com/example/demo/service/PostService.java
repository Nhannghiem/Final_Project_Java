package com.example.demo.service;

import com.example.demo.entity.PostEntity;
import com.example.demo.form.PostForm;
import com.example.demo.model.PostDashboardReponse;

import java.util.List;

public interface PostService {

    List<PostEntity> findByTopicIdAndIsActive(Long id);

    PostEntity createPost(PostForm postForm,Long id);

    PostEntity editPost(PostForm postForm);

    void deleteSoftPost(PostEntity postEntity);

    PostEntity findById(Long id);

    void permanentlyDeleted(Long id);

    List<PostEntity> findAllByUserId(Long id);

    Integer countContribution(Long id);


    List<PostEntity> findAllPostByUser();

    List<PostDashboardReponse> countPostMonth();

    PostEntity restorePostMyself(Long id);


    PostEntity findAPost(Long id);


}
