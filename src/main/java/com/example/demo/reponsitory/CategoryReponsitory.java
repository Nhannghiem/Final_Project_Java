package com.example.demo.reponsitory;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.model.CountData;
import com.example.demo.model.CountReponse;
import com.example.demo.model.TopicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryReponsitory extends JpaRepository<CategoryEntity,Long> {

    @Query(value = "SELECT * FROM category c where c.item_id = :id", nativeQuery = true)
    List<CategoryEntity> findAllByItemId(Long id);

    @Query(value = "SELECT t.id as id, t.content as content, t.title as title, t.create_at as createAt FROM web_project.category c join web_project.topic t on t.category_id in (c.id) and t.is_active = 0 where c.users_id = :userId", nativeQuery = true)
    List<TopicData> findAllBySystemManagement(Long userId);


    @Query(value = "SELECT count(*) as counts FROM web_project.category c join web_project.topic t on t.category_id in (c.id) and t.is_active = 0 where c.users_id = :userId", nativeQuery = true)
    CountData countNotificationStaff(Long userId);


    CategoryEntity findByIdAndUser_Email(Long id, String userEmail);

    @Query(value = "SELECT t.id as id, t.content as content, t.title as title, t.create_at as createAt FROM web_project.topic t where t.user_id = :userId and t.is_active = 1 ", nativeQuery = true)
    List<TopicData> notificationUser(Long userId);


    @Query(value = "SELECT count(*) as counts FROM web_project.topic t where t.user_id = :userId and t.is_active = 1", nativeQuery = true)
    CountData countNotificationUser(Long userId);

    @Query(value = "SELECT count(c.id) as countCategory FROM web_project.category c where MONTH(create_at) =  MONTH(CURDATE()) GROUP BY MONTH(CURDATE())", nativeQuery = true)
    CountData countCategoryInMoth();



}
