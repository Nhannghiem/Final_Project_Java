package com.example.demo.reponsitory;

import com.example.demo.entity.TopicEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.model.TopicDashboardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicReponsitory extends JpaRepository<TopicEntity, Long> {

  @Query(value = "SELECT * FROM web_project.topic t where t.is_active = 1 and t.category_id = :categoryId", nativeQuery = true)
  List<TopicEntity> findByCategoryAndIsActive(Long categoryId);


  @Query(value = "SELECT * FROM web_project.topic where t.category_id in (:categoryId) and t.is_active = 0", nativeQuery = true)
  List<TopicEntity> findAllByIsActive(Long categoryId);

  @Query(value = "SELECT date_format(t.create_at, '%M %d %Y') as createAt, count(t.id) AS counts FROM web_project.topic t where t.is_active = 1 GROUP BY (t.is_active), MONTH(t.create_at) , YEAR(t.create_at)", nativeQuery = true)
  List<TopicDashboardData> countMonthly();








}
