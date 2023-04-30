package com.example.demo.controller;

import com.example.demo.domain.ResourceResponse;
import com.example.demo.entity.ItemEntity;
import com.example.demo.form.ItemForm;
import com.example.demo.model.CountReponse;
import com.example.demo.service.ItemService;
import com.example.demo.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
//@RequestMapping(value = "/admin")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private TopicService topicService;


    @GetMapping(value = "/QAM/go_create_item")
    public String goIdea(Model model){
        model.addAttribute("item", new ItemForm());
        return "createItem";
    }

    @PostMapping(value = "/QAM/create_item")
    public String createItem(@Valid @ModelAttribute("item") ItemForm itemForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "createItem";
        }
        itemService.createItem(itemForm);
        return "redirect:/forum_item";
    }

    @GetMapping(value = "delete_item")
    public String deleteItem(@RequestParam("id") Long id){
        itemService.deleteItem(id);
        return "redirect:/forum_item";
    }

    @GetMapping(value = "/get_item")
    public String getItemId(Model model, @RequestParam("id") Long id){
        ItemEntity item = itemService.findById(id);
        if (item == null){
            return "redirect:/forum_item";
        }
        model.addAttribute("item", item);
        return "editItem";
    }

    @PostMapping(value = "edit_item")
    public String editItem(@ModelAttribute("item") ItemForm itemForm){
        itemService.createItem(itemForm);
        return "redirect:/forum_item";
    }



    @GetMapping("/forum_item")
    public String showItemAndCategory(Model model, Authentication authentication){
        model.addAttribute("authentication", authentication.getName());

        List<ItemEntity> itemEntities = itemService.findAllItem();
        CountReponse countReponse = topicService.countNotification();
        model.addAttribute("count", countReponse);
        model.addAttribute("items", itemEntities);
        return "showItem";
    }

    @RequestMapping(value = "/search_item")
    public String search(Model model, @RequestParam("title") String title){

        List<ItemEntity> search = itemService.searchItem(title);
        CountReponse countReponse = topicService.countNotification();
        model.addAttribute("count", countReponse);
        model.addAttribute("items", search);
        return "showItem";
    }





}
