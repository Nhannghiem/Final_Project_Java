package com.example.demo.reponsitory;

import com.example.demo.entity.FeelingEntity;
import com.example.demo.model.FellingCountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeelingReponsitory extends JpaRepository<FeelingEntity, Long> {


    @Query(value = "SELECT * FROM feeling f where f.user_id = :userId and f.post_id = :postId", nativeQuery = true)
    FeelingEntity findByUserAndPost(Long userId, Long postId);

    void deleteByIdAndUser_IdAndPost_Id(Long id, Long userId, Long postId);


    @Query(value = "select count(f.post_id) as counts from  web_project.feeling f where f.post_id = :postId", nativeQuery = true)
    FellingCountData countLike(Long postId);




}
