package com.example.demo.reponsitory;

import com.example.demo.entity.PostEntity;
import com.example.demo.model.PostDashboardData;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
public interface PostReponsitory extends JpaRepository<PostEntity, Long> {


    @Query(value = "SELECT * FROM post p where p.topic_id = :topicId and p.is_active = 1", nativeQuery = true)
    List<PostEntity> findAllByTopicAndIsActive(Long topicId);

    @Query(value = "SELECT * FROM post p where p.user_id = :userId and p.is_active = 0", nativeQuery = true)
    List<PostEntity> findAllByUserIAndIsActive(Long userId);

    @Query(value = "SELECT * FROM post p where p.user_id = ?1 and p.is_active = 1", nativeQuery = true)
    List<PostEntity> findAllByUsers(Long userId);


    @Query(value = "update post p set p.is_active = 0 where p.id = ?1", nativeQuery = true)
    void deleteSort(@Param("id") Long id);

    @Query(value = "SELECT count(p.id) FROM web_project.post p where p.topic_id = :topicId and p.is_active = 1", nativeQuery = true)
    Integer countContributions(Long topicId);

    @Query(value = "SELECT date_format(p.create_at, '%M %d %Y') as createAt, count(p.id) as counts FROM web_project.post p where p.is_active = 1 GROUP BY (p.is_active), MONTH(p.create_at) , YEAR(p.create_at)", nativeQuery = true)
    List<PostDashboardData> countPostMonthly();


    PostEntity findByIdAndUsers_Email(Long id, String usersEmail);




}
