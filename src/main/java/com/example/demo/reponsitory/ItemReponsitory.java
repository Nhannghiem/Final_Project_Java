package com.example.demo.reponsitory;

import com.example.demo.entity.ItemEntity;
import com.example.demo.model.CountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemReponsitory extends JpaRepository<ItemEntity,Long> {

    ItemEntity findByIdAndUser_Email(Long id, String usersEmail);

    @Query(value = "SELECT count(i.id) AS countItem FROM web_project.item i where MONTH(create_at) =  MONTH(CURDATE()) GROUP BY MONTH(CURDATE())", nativeQuery = true)
    CountData countItemInMoth();

    @Query(value = "SELECT * FROM web_project.item i WHERE i.title LIKE %?1%", nativeQuery = true)
    List<ItemEntity> searchByTitle(String keyword);
}
