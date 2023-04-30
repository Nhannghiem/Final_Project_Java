package com.example.demo.service;

import com.example.demo.entity.TopicEntity;
import com.example.demo.form.TopicForm;
import com.example.demo.model.*;

import java.util.List;

public interface TopicService {

    List<ListTopicReponse> findAllByIsActiveAndCategoryId(Long id);

    TopicEntity userCreateTopic(Long id, TopicForm topicForm);

    TopicEntity staffCreateTopic(Long id, TopicForm topicForm);

    TopicEntity findById(Long id);

    TopicEntity editTopic( TopicEntity topic);

    void delete(Long id);

    List<TopicReponse> findTopicByIsActive();

    CountReponse countNotification();

    void confirm(TopicEntity topic);

    List<TopicDashboardReponse> countMothly();


}
