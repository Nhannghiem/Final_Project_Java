package com.example.demo.service;

import com.example.demo.entity.ItemEntity;
import com.example.demo.form.ItemForm;
import com.example.demo.model.CountReponse;

import java.util.List;

public interface ItemService {

    List<ItemEntity> findAllItem();

    ItemEntity createItem(ItemForm itemForm);

    List<ItemEntity> searchItem(String keyword);

    void deleteItem(Long id);

    ItemEntity findById(Long id);

    CountReponse countItemInMoth();


//    List<ItemEntity> findAllCategoryById(CategoryEntity id);

}
