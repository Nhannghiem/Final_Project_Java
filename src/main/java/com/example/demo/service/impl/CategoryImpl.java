package com.example.demo.service.impl;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.form.CategoryForm;
import com.example.demo.model.CountData;
import com.example.demo.model.CountReponse;
import com.example.demo.reponsitory.CategoryReponsitory;
import com.example.demo.reponsitory.ItemReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryImpl implements CategoryService {



    @Autowired
    private CategoryReponsitory categoryReponsitory;

    @Autowired
    private ItemReponsitory itemReponsitory;

    @Autowired
    private UsersReponsitory usersReponsitory;

    @Override
    public List<CategoryEntity> findAllCategory(Long id) {
        return categoryReponsitory.findAllByItemId(id);
    }

    @Override
    public CategoryEntity createCategory(CategoryForm categoryForm,Long id) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        CategoryEntity category = new CategoryEntity();

        UsersEntity usersEntity = usersReponsitory.findByEmail(userEmail);
        category.setItem(itemReponsitory.findById(id).orElseThrow());
        category.setUser(usersEntity);
        category.setCreateAt(LocalDateTime.now());
        category.setContent(categoryForm.getContent());
        category.setTitle(categoryForm.getTitle());

        return categoryReponsitory.save(category);
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        category.setId(category.getId());
        category.setContent(category.getContent());
        category.setTitle(category.getTitle());
        return categoryReponsitory.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryReponsitory.deleteById(id);
    }

    @Override
    public CategoryEntity findById(Long id) {

        return categoryReponsitory.findById(id).orElseThrow();
    }

    @Override
    public List<CategoryEntity> findAllCategoryById(CategoryEntity id) {
        return null;
    }

    @Override
    public CountReponse countCategoryInMoth() {
        CountData countData = categoryReponsitory.countCategoryInMoth();
        if (countData != null){
            return CountReponse.builder().countCategory(countData.getCountCategory()).build();
        }else {
            return CountReponse.builder().countCategory(0).build();
        }
    }
}
