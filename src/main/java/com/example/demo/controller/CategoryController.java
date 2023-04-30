package com.example.demo.controller;


import com.example.demo.domain.ResourceResponse;
import com.example.demo.entity.CategoryEntity;
import com.example.demo.form.CategoryForm;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/go_category")
    private String goCategory(){
        return "createCategory";
    }


    @GetMapping(value = "/delete_category")
    private String delete(@RequestParam("id") Long id){
        categoryService.deleteCategory(id);
        return "redirect:/forum_item";
    }

    @GetMapping(value = "/QAM/go_create_category")
    private String goCreateCategory(Model model){
        model.addAttribute("category", new CategoryForm());
        return "createCategory";
    }

    @PostMapping(value = "/QAM/create_category")
    private String createCategory(@Valid @ModelAttribute("category") CategoryForm categoryForm, BindingResult bindingResult, @RequestParam("id") Long id){
        if (bindingResult.hasErrors()){
            return "createCategory";
        }
        categoryService.createCategory(categoryForm, id);
        return "redirect:/forum_item";
    }


    @GetMapping(value = "/go_edit_category")
    private String goEditCategory(Model model, @RequestParam("id") Long id){
        CategoryEntity category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "editCategory";
    }

    @PostMapping(value = "/edit_category")
    private String editCategory(@ModelAttribute("category") CategoryEntity categoryEntity){
        categoryService.editCategory(categoryEntity);
        return "redirect:/forum_item";
    }






//    @PostMapping(value = "/go_edit")
//    private String editCategory(@ModelAttribute("category") CategoryForm categoryForm){
//        categoryService.createCategory(categoryForm);
//        return "redirect:/showCategory";
//    }

}
