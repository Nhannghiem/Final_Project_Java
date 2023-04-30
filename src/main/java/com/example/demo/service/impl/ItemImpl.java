package com.example.demo.service.impl;

import com.example.demo.entity.ItemEntity;
import com.example.demo.form.ItemForm;
import com.example.demo.model.CountData;
import com.example.demo.model.CountReponse;
import com.example.demo.reponsitory.ItemReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemImpl implements ItemService {

    @Autowired
    private ItemReponsitory itemReponsitory;

    @Autowired
    private UsersReponsitory usersReponsitory;

    @Override
    public List<ItemEntity> findAllItem() {
        return itemReponsitory.findAll();
    }

    @Override
    public ItemEntity createItem(ItemForm itemForm) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        ItemEntity entity = new ItemEntity();
        if(itemForm.getId() != null){
            entity.setId(itemForm.getId());
        }
        entity.setTitle(itemForm.getTitle());
        entity.setContent(itemForm.getContent());
        entity.setCreateAt(LocalDateTime.now());
        entity.setUser(usersReponsitory.findByEmail(userEmail));
        return itemReponsitory.save(entity);
    }

    @Override
    public List<ItemEntity> searchItem(String keyword) {
        return itemReponsitory.searchByTitle(keyword);
    }

    @Override
    public void deleteItem(Long id) {
        itemReponsitory.deleteById(id);

    }

    @Override
    public ItemEntity findById(Long id) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        return itemReponsitory.findByIdAndUser_Email(id, userEmail);
    }

    @Override
    public CountReponse countItemInMoth() {
        CountData countData = itemReponsitory.countItemInMoth();
        if (countData != null){
            return CountReponse.builder().countItem(countData.getCountItem()).build();
        }else {
            return CountReponse.builder().countItem(0).build();
        }
    }
}
