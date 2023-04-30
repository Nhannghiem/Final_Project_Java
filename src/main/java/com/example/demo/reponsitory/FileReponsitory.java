package com.example.demo.reponsitory;

import com.example.demo.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileReponsitory extends JpaRepository<FileEntity,Long> {
}
