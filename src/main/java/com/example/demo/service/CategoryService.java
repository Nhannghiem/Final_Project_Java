package com.example.demo.service;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.form.CategoryForm;
import com.example.demo.model.CountReponse;

import java.util.List;

public interface CategoryService {

    List<CategoryEntity> findAllCategory(Long id);

    CategoryEntity createCategory(CategoryForm categoryForm, Long id);

    CategoryEntity editCategory(CategoryEntity category);


    void deleteCategory(Long id);

    CategoryEntity findById(Long id);


    List<CategoryEntity> findAllCategoryById(CategoryEntity id);

    CountReponse countCategoryInMoth();

}
