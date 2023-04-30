package com.example.demo.controller;

import com.example.demo.entity.CategoryEntity;
import com.example.demo.entity.TopicEntity;
import com.example.demo.form.TopicForm;
import com.example.demo.model.ListTopicReponse;

import com.example.demo.service.CategoryService;
import com.example.demo.service.TopicService;
import com.example.demo.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;


    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/forum_topic")
    public String showTopic(Model model, @RequestParam("id") Long id , Authentication authentication){
        model.addAttribute("authentication" , authentication.getName());
        List<ListTopicReponse> topicEntities = topicService.findAllByIsActiveAndCategoryId(id);
        CategoryEntity category = categoryService.findById(id);
        model.addAttribute("topics", topicEntities);
        model.addAttribute("category", category);
        return "showTopic";
    }

//    @GetMapping (value= "/testFindAllTopic/{id}")
//    public ResponseEntity<?> find(@PathVariable("id") Long id) {
//        return new ResourceResponse<>(topicService.findAllByIsActiveAndCategoryId(id));
//    }

    @GetMapping(value = "/go_create_topic")
    public String goCreateTopic(Model model){
        model.addAttribute("topic", new TopicForm());

        return "createTopic";
    }

    @PostMapping(value = "/create_topic")
    public String create_topic(@Valid @ModelAttribute("topic") TopicForm topicForm, BindingResult bindingResult, @RequestParam("id") Long id){
        if (bindingResult.hasErrors()){
            return "createTopic";
        }
        topicService.userCreateTopic(id, topicForm);
        return "redirect:/forum_topic/?id=" + id;

    }

    @GetMapping(value = "/go_edit_topic")
    public String goEditTopic(Model model, @RequestParam("id") Long id){
        TopicEntity topic = topicService.findById(id);
        model.addAttribute("topic", topic);
        return "editTopic";

    }

    @PostMapping(value = "/edit_topic")
    public String editTopic(@ModelAttribute("topic") TopicEntity topicEntity){
        TopicEntity topic = topicService.editTopic(topicEntity);
        return "redirect:/forum_topic/?id=" + topic.getCategory().getId();
    }

    @GetMapping(value = "/delete_topic")
    public String deleteTopic(@RequestParam("id") Long id){
        topicService.delete(id);
        return "redirect:/forum_item" ;
    }



}
