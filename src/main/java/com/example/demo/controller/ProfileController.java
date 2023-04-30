package com.example.demo.controller;

import com.example.demo.entity.PostEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.service.PostService;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private PostService postService;


    @GetMapping(value = "/profile")
    public String showProfile(Model model){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UsersEntity usersEntity = usersService.findByEmail(userEmail);
        model.addAttribute("user", usersEntity);
        List<PostEntity> postEntity = postService.findAllByUserId(usersEntity.getId());
        model.addAttribute("posts", postEntity);
        return "profile";
    }


}
