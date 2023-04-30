package com.example.demo.reponsitory;

import com.example.demo.entity.ViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ViewReponsitory extends JpaRepository<ViewEntity,Long> {

    @Query(value="SELECT * FROM web_project.views v where v.user_id = :userId and v.topic_id = :topicId", nativeQuery = true)
    ViewEntity findByPostAndUserId(Long userId, Long topicId);

    @Query(value="SELECT count(v.id) FROM web_project.views v where v.topic_id = :topicId", nativeQuery = true)
    Integer countView( Long topicId);
}
