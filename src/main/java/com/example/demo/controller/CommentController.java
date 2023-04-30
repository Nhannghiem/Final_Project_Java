package com.example.demo.controller;

import com.example.demo.entity.CommentEntity;
import com.example.demo.entity.UsersEntity;
import com.example.demo.form.CommentForm;
import com.example.demo.reponsitory.CommentReponsitory;
import com.example.demo.reponsitory.UsersReponsitory;
import com.example.demo.service.CommentService;
import com.example.demo.service.UsersService;
import com.example.demo.service.impl.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UsersReponsitory usersReponsitory;

    @GetMapping(value = "/go_create_comment")
    public String goCreateComment(Model model){
        model.addAttribute("comment", new CommentForm());
        return "createComment";
    }

    @PostMapping(value = "/create_comment")
    public String createComment(@ModelAttribute("comment") CommentForm commentForm, @RequestParam("id") Long id){
        commentService.createComment(commentForm,id);
        return "redirect:/detail_post/?id=" + id;
    }

    @GetMapping(value = "/go_edit_comment")
    public String goEdiComment(Model model, @RequestParam("id") Long id){
        CommentEntity comment = commentService.findById(id);
        model.addAttribute("comment", comment);

        return "editComment";
    }

    @PostMapping(value = "/edit_comment")
    public String editComment(@ModelAttribute("comment") CommentEntity commentEntity){
        commentService.editComment(commentEntity);
        return "redirect:/detail_post/?id=" + commentEntity.getPost().getId();
    }

    @GetMapping("/delete_comment")
    @Transactional
    public String deleteComment(@RequestParam("id") Long id){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        CommentEntity commentEntity = commentService.findById(id);
        commentService.deleteComment(id, userEmail);
        return "redirect:/detail_post/?id="+ commentEntity.getPost().getId();
    }
}
