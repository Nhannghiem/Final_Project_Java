package com.example.demo.controller;

import com.example.demo.entity.UsersEntity;
import com.example.demo.form.UsersForm;
import com.example.demo.service.UsersService;
import com.example.demo.service.impl.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class UsersController {


    @Autowired
    private UsersService managementService;



    @GetMapping("/admin/admin_register")
    public String adminRegister(Model model){
        model.addAttribute("admin", new UsersForm());
        return "register";
    }

    @PostMapping("/admin/process_register_admin")
    public String adminRegister( @ModelAttribute("admin") UsersForm systemManagementForm, BindingResult bindingResult, @RequestParam("photo") MultipartFile multipartFile) throws Exception {

        StringUtils.cleanPath(multipartFile.getOriginalFilename());
        UsersEntity usersEntity = managementService.findByEmail(systemManagementForm.getEmail());
        if (usersEntity != null){
            bindingResult.addError(new FieldError("admin", "email","This account already exists"));
            return "register";
        } else if (!systemManagementForm.getPassword().equals(systemManagementForm.getConfirm())) {
            bindingResult.addError(new FieldError("admin", "confirm","Confirm and Password do not match. "));
            return "register";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword= passwordEncoder.encode(systemManagementForm.getPassword());
        systemManagementForm.setPassword(encodePassword);
        systemManagementForm.setPhoto(FileUpload.copyFile(multipartFile));
        UsersEntity users = managementService.saveUser(systemManagementForm);
        String uploadDir = "user-photo/" + users.getId();
        return "redirect:/forum_item";

    }

    @GetMapping("/user_register")
    public String userSignUp(Model model){
        model.addAttribute("user", new UsersForm());
        return "registerUser";
    }

    @PostMapping("/process_register_user")
    public String saveUserSignUp( @ModelAttribute("user") UsersForm usersForm, BindingResult bindingResult, @RequestParam("photo")MultipartFile multipartFile) throws Exception {
//        if (bindingResult.hasErrors()){
//            return "registerUser";
//        }
        StringUtils.cleanPath(multipartFile.getOriginalFilename());
        UsersEntity usersEntity = managementService.findByEmail(usersForm.getEmail());
        if (usersEntity != null){
            bindingResult.addError(new FieldError("user", "email","This account already exists"));
            return "registerUser";
        } else if (!usersForm.getPassword().equals(usersForm.getConfirm())) {
            bindingResult.addError(new FieldError("user", "confirm","Confirm and Password do not match. "));
            return "registerUser";
        }


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword= passwordEncoder.encode(usersForm.getPassword());
        usersForm.setPassword(encodePassword);
        usersForm.setPhoto(FileUpload.copyFile(multipartFile));
        UsersEntity users = managementService.saveUserRes(usersForm);
        String uploadDir = "user-photo/" + users.getId();

        return "redirect:/login";

    }




    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model){
        String errorRole = "Do not have permission to access the site:((";
        model.addAttribute("msg",errorRole);
        return "haventRole";
    }


    @GetMapping ("/login")
    public String authentication(){
        return "login";
    }



    @GetMapping("/test")
    public String testUser(){
        return "Test";
    }

}
