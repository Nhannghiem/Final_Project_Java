package com.example.demo.reponsitory;

import com.example.demo.entity.UsersEntity;
import com.example.demo.model.CountData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsersReponsitory extends JpaRepository<UsersEntity,Long> {

    @Query(value = "SELECT * FROM users u WHERE u.email = ?1", nativeQuery = true)
    UsersEntity findByEmail(String email);


    @Query(value = "SELECT count(*) as countUser FROM users", nativeQuery = true)
    CountData countUsers();



}
