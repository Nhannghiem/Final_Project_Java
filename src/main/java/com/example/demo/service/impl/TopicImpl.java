package com.example.demo.service.impl;

import com.example.demo.entity.UsersEntity;
import com.example.demo.entity.TopicEntity;
import com.example.demo.form.TopicForm;
import com.example.demo.model.*;
import com.example.demo.reponsitory.CategoryReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.reponsitory.TopicReponsitory;
import com.example.demo.reponsitory.ViewReponsitory;
import com.example.demo.service.PostService;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.ArrayUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TopicImpl implements TopicService {


    @Autowired
    private TopicReponsitory topicReponsitory;

    @Autowired
    private PostService postService;



    @Autowired
    private UsersReponsitory usersReponsitory;

    @Autowired
    private CategoryReponsitory categoryReponsitory;
    @Autowired
    private ViewReponsitory viewReponsitory;

    @Override
    public List<ListTopicReponse> findAllByIsActiveAndCategoryId(Long id) {
        List<TopicEntity> topicEntityList = topicReponsitory.findByCategoryAndIsActive(id);
        return topicEntityList.stream().map(topicEntity -> new ListTopicReponse(topicEntity.getId(),topicEntity.getUser().getEmail() ,topicEntity.getContent(), topicEntity.getTitle(), topicEntity.getCreatAt(), viewReponsitory.countView(topicEntity.getId()), postService.countContribution(topicEntity.getId()),topicEntity.getUser().getId())).collect(Collectors.toList());
    }

    @Override
    public TopicEntity userCreateTopic(Long id, TopicForm topicForm) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UsersEntity users = usersReponsitory.findByEmail(userEmail);
        TopicEntity topicEntity = new TopicEntity();

        if (users ==null){
            topicEntity.setIsActive(0);

        } else if (users.getRole().equals("admin") || users.getRole().equals("QAM")) {
            topicEntity.setIsActive(1);
            System.out.println(users.getRole());
        } else {
            topicEntity.setIsActive(0);
            System.out.println(users.getRole());
        }
        topicEntity.setCreatAt(LocalDateTime.now());
        topicEntity.setContent(topicForm.getContent());

        topicEntity.setTitle(topicForm.getTitle());
        topicEntity.setCategory(categoryReponsitory.findById(id).orElseThrow());
        topicEntity.setUser(users);

        return topicReponsitory.save(topicEntity);
    }

    @Override
    public TopicEntity staffCreateTopic(Long id, TopicForm topicForm) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setCreatAt(LocalDateTime.now());
        topicEntity.setContent(topicForm.getContent());
        topicEntity.setIsActive(1);
        topicEntity.setTitle(topicForm.getTitle());
        topicEntity.setCategory(categoryReponsitory.findById(id).orElseThrow());
        topicEntity.setUser(usersReponsitory.findByEmail(userEmail));

        return topicReponsitory.save(topicEntity);
    }

    @Override
    public TopicEntity findById(Long id) {

        return topicReponsitory.findById(id).orElseThrow();
    }

    @Override
    public TopicEntity editTopic(TopicEntity topic) {
        topic.setTitle(topic.getTitle());
        topic.setContent(topic.getContent());

        return topicReponsitory.save(topic);
    }

    @Override
    public void delete(Long id) {
        topicReponsitory.deleteById(id);
    }

    @Override
    public List<TopicReponse> findTopicByIsActive() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UsersEntity systemManagement = usersReponsitory.findByEmail(userEmail);
        if (systemManagement.getRole().equals("admin") || systemManagement.getRole().equals("QAM")){
            List<TopicData> topicData = categoryReponsitory.findAllBySystemManagement(systemManagement.getId());
            return topicData.stream().map(topicData1 -> new TopicReponse(topicData1.getId(), topicData1.getContent(), topicData1.getTitle(), topicData1.getCreateAt())).collect(Collectors.toList());
        }else {
            List<TopicData> topicData = categoryReponsitory.notificationUser(systemManagement.getId());
            return topicData.stream().map(topicData1 -> new TopicReponse(topicData1.getId(), topicData1.getContent(), topicData1.getTitle(), topicData1.getCreateAt())).collect(Collectors.toList());
        }


    }

    @Override
    public CountReponse countNotification() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UsersEntity systemManagement = usersReponsitory.findByEmail(userEmail);
        if (systemManagement.getRole().equals("admin") || systemManagement.getRole().equals("QAM")) {

            CountData countData = categoryReponsitory.countNotificationStaff(systemManagement.getId());
            if (countData != null){
                return CountReponse.builder().counts(countData.getCounts()).build();
            }else {
                return CountReponse.builder().counts(0).build();
            }
        }else {
            CountData countDatas = categoryReponsitory.countNotificationUser(systemManagement.getId());
            if (countDatas != null){
                return CountReponse.builder().counts(countDatas.getCounts()).build();
            }else {
                return CountReponse.builder().counts(0).build();
            }
        }
    }

    @Override
    public void confirm(TopicEntity topic) {
        topic.setIsActive(1);
        topicReponsitory.save(topic);
    }



    @Override
    public List<TopicDashboardReponse> countMothly() {
        List<TopicDashboardData> topicDashboardData = topicReponsitory.countMonthly();
        return topicDashboardData.stream().map(topicDashboardData1 -> new TopicDashboardReponse(topicDashboardData1.getCreateAt(), topicDashboardData1.getCounts())).collect(Collectors.toList());
    }


}
