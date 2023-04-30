package com.example.demo.service.impl;

import com.example.demo.entity.FeelingEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.model.FellingCountData;
import com.example.demo.model.FellingCountReponse;
import com.example.demo.reponsitory.FeelingReponsitory;
import com.example.demo.reponsitory.PostReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.FellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FellingImpl implements FellingService {

    @Autowired
    private FeelingReponsitory feelingReponsitory;

    @Autowired
    private UsersReponsitory usersReponsitory;

    @Autowired
    private PostReponsitory postReponsitory;

    @Override
    public void saveUserAndPst(Long id) {
        FeelingEntity feelingEntity = new FeelingEntity();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UsersEntity users = usersReponsitory.findByEmail(userEmail);
        FeelingEntity feeling = feelingReponsitory.findByUserAndPost(users.getId(),id);
        if (feeling == null){
            feelingEntity.setUser(users);
            feelingEntity.setPost(postReponsitory.findById(id).orElseThrow());
            feelingReponsitory.save(feelingEntity);
        }else {
            try{
                var a = feelingReponsitory.findById(feeling.getId());
                feelingReponsitory.deleteById(feeling.getId());
            }catch (Exception e){
                System.out.println(e);
            }
        }



    }

    @Override
    public FellingCountReponse countLike(Long id) {
        FellingCountData countLike = feelingReponsitory.countLike(id);
        if (countLike == null){
            return FellingCountReponse.builder().counts(0).build();
        }else {
            return FellingCountReponse.builder().counts(countLike.getCounts()).build();
        }

    }
}
