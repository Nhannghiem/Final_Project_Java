package com.example.demo.controller;

import com.example.demo.entity.TopicEntity;
import com.example.demo.model.CountReponse;
import com.example.demo.model.TopicReponse;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.CategoryService;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UsersReponsitory managementReponsitory;



    @GetMapping(value = "/notification")
    public String notificationTopic(Model model){
        List<TopicReponse> topicReponses = topicService.findTopicByIsActive();
        model.addAttribute("topics", topicReponses);
        return "notification";
    }

    @GetMapping(value = "/confirmTopic")
    public String confirm(@RequestParam("id") Long id){
        TopicEntity topic = topicService.findById(id);
        topicService.confirm(topic);
        return "redirect:/notification";
    }

    @GetMapping(value = "/cancel_topic")
    public String cancelTopic(@RequestParam("id") Long id){
        topicService.delete(id);
        return "redirect:/notification";
    }





}
