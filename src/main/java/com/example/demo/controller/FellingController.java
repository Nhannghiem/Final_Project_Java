package com.example.demo.controller;

import com.example.demo.service.FellingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FellingController {

    @Autowired
    private FellingService fellingService;

    @GetMapping(value = "/userLike")

    public String userLikePost(@RequestParam("id") Long id){
        fellingService.saveUserAndPst(id);
        return "redirect:/detail_post/?id=" +id;
    }


}
