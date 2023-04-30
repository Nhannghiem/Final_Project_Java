package com.example.demo.reponsitory;

import com.example.demo.entity.CommentEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentReponsitory extends JpaRepository<CommentEntity,Long> {

    @Modifying
    @Query(value = "delete from comment t where t.id = ?1", nativeQuery = true)
    void delete(Long entityId);

    void deleteByIdAndUser_Email(Long id, String userEmail);
}
