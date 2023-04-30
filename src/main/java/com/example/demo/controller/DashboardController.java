package com.example.demo.controller;

import com.example.demo.model.CountReponse;
import com.example.demo.model.PostDashboardReponse;
import com.example.demo.model.TopicDashboardReponse;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class DashboardController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/dashboard")
    private String dashboard(Model model){
        CountReponse user = usersService.countUsers();
        CountReponse item = itemService.countItemInMoth();
        CountReponse category = categoryService.countCategoryInMoth();
        List<TopicDashboardReponse> topics = topicService.countMothly();
        List<PostDashboardReponse> posts = postService.countPostMonth();
        model.addAttribute("topics",topics );
        model.addAttribute("posts",posts );
        model.addAttribute("user",user );
        model.addAttribute("item",item );
        model.addAttribute("category",category );
        return "dashboard";
    }




}
