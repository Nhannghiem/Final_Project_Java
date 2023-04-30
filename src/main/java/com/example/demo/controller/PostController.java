package com.example.demo.controller;


import com.example.demo.domain.ResourceResponse;
import com.example.demo.entity.PostEntity;
import com.example.demo.entity.TopicEntity;
import com.example.demo.form.PostForm;
import com.example.demo.model.FellingCountReponse;
import com.example.demo.reponsitory.FeelingReponsitory;
import com.example.demo.reponsitory.PostReponsitory;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;

@Controller
public class PostController {


    @Autowired
    private PostService postService;

    @Autowired
    private FellingService fellingService;


    @Autowired
    private FileService fileService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private PostReponsitory postReponsitory;

    @Autowired
    private ViewService viewService;


    @GetMapping (value= "/deleteId/{id}")
    public String deletesss( @PathVariable("id") Long id) {
        PostEntity postEntity = postReponsitory.findById(id).orElseThrow();
        postService.deleteSoftPost(postEntity);
        return "redirect:/show_forum_post/?id=" + postEntity.getTopic().getId();
    }

    @GetMapping(value = "/restore_post")
    public String restore(@RequestParam("id") Long id){
        postService.restorePostMyself(id);
        return "redirect:/trash_post";
    }

    @GetMapping (value= "/findAllPost/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id) {
        return new ResourceResponse<>(postService.findById( id));
    }

    @GetMapping(value = "/show_forum_post")
    public String showPost(Model model, @RequestParam("id") Long id, Authentication authentication){
        model.addAttribute("authentication", authentication.getName());
        model.addAttribute(authentication);
        List<PostEntity> postEntities = postService.findByTopicIdAndIsActive(id);
        TopicEntity topic = topicService.findById(id);
        viewService.saveViewUser(topic.getId());
        System.out.println(topic.getCategory().getId() + "Category Id");
        model.addAttribute("posts", postEntities);
        model.addAttribute("topic", topic);
        model.addAttribute("categoryId", topic.getCategory().getId());
        return "showPost";
    }

    @GetMapping(value = "/go_create_post")
    public String goCreatePost(Model model){
        model.addAttribute("post", new PostForm());
        return "createPost";
    }

    @PostMapping(value = "/create_post")
    public String createPost(@ModelAttribute("post") PostForm postForm, @RequestParam("id") Long id, @RequestParam("files") MultipartFile[] multipartFiles){
        PostEntity postEntity = postService.createPost(postForm, id);

        for (MultipartFile file : multipartFiles){
            if (!file.isEmpty()){
                fileService.saveFile(file, postEntity.getId());
            }

        }
        return "redirect:/show_forum_post/?id=" + id;
    }

    @GetMapping(value = "/go_edit_post")
    public String goEditPost(Model model, @RequestParam("id") Long id){
        PostEntity post = postService.findById(id);
        if (post == null){
            String errorRole = "Do not have permission to access the site:((";
            model.addAttribute("msg",errorRole);
            return "haventRole";
        }
        model.addAttribute("post", post);
        return"editPost";
    }

    @PostMapping(value = "/edit_post")
    public String editPost(@ModelAttribute("post") PostForm postForm , @RequestParam("files") MultipartFile[] multipartFiles){
        PostEntity post = postService.editPost(postForm);
        for (MultipartFile file : multipartFiles){
            if (!file.isEmpty()){
                fileService.saveFile(file, post.getId());
            }

        }
        return "redirect:/show_forum_post/?id=" + post.getTopic().getId();
    }






    @GetMapping(value = "/detail_post")
    public String detailPost(Model model, @RequestParam("id") Long id, Authentication authentication){

        PostEntity post = postService.findAPost(id);
        FellingCountReponse likes = fellingService.countLike(id);
        model.addAttribute("post", post);
        model.addAttribute("likes", likes);
        model.addAttribute("authentication", authentication.getName());

        return "detailPost";
    }

    @GetMapping(value = "/trash_post")
    public String trashPost(Model model){

        model.addAttribute("posts", postService.findAllPostByUser());

        return "postTrash";
    }

    @GetMapping(value = "/delete_post/{id}")
    public String deletePost(@PathVariable("id") Long id ){
        postService.permanentlyDeleted(id);
        return "redirect:/trash_post";
    }


    @PostMapping("/upload/post/image")
    public ResponseEntity<String> saveImage(@RequestParam("file") MultipartFile file) {
        String url = null;

        try {
            url = copyFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(url);
    }


    private String copyFile(MultipartFile file) throws Exception {
        String url = null;
        String fileName = Instant.now().toEpochMilli() + file.getOriginalFilename();

        try (InputStream is = file.getInputStream()) {
            Path path = FileUtil.getImagePath(fileName);

            Files.copy(is, path);

            url = FileUtil.getImageUrl(fileName);
        } catch (IOException ie) {
            ie.printStackTrace();
            throw new Exception("Failed to upload!");
        }

        return url;
    }




}
