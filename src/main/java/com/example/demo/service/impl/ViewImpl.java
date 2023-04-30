package com.example.demo.service.impl;

import com.example.demo.entity.TopicEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.entity.ViewEntity;
import com.example.demo.model.TopicReponse;
import com.example.demo.reponsitory.TopicReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.reponsitory.ViewReponsitory;
import com.example.demo.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ViewImpl implements ViewService {

    @Autowired
    private ViewReponsitory viewReponsitory;

    @Autowired
    private TopicReponsitory topicReponsitory;

    @Autowired
    private UsersReponsitory usersReponsitory;

    @Override
    public void saveViewUser(Long id) {
        ViewEntity viewEntity = new ViewEntity();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UsersEntity users = usersReponsitory.findByEmail(userEmail);
        if (users != null){
            ViewEntity view = viewReponsitory.findByPostAndUserId(users.getId(), id);
            if (view == null){
                viewEntity.setUserId(users.getId());
                viewEntity.setTopic(topicReponsitory.findById(id).orElseThrow());
                viewReponsitory.save(viewEntity);
            }
        }

    }

    @Override
    public Integer countView(Long id) {
        Integer countView = viewReponsitory.countView(id);
        if (countView != null ){
            return countView;
        }
        return 0;
    }
}
