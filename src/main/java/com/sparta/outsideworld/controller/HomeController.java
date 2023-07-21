package com.sparta.outsideworld.controller;


import com.sparta.outsideworld.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
        return "index";
    }

    // 글상세 페이지 이동
    @GetMapping("/post/detail/{postId}")
    public String selectPost(@PathVariable String postId, Model model) {
        model.addAttribute(postId, postId);
        return "postdetail";
    }

    //글 작성 페이지 이동
    @GetMapping("/post/new-post")
    public String newPost(){
        return "newpost";
    }

    //글 수정 페이지 이동
    @GetMapping("/post/update/{postId}")
    public String updatePost(@PathVariable String postId, Model model) {
        model.addAttribute(postId, postId);
        return "editpost";
    }

    //로그인시 홈으로 이동
    @GetMapping("/user/login")
    public String loginUser(){
        return "index";
    }

    //로그아웃시 홈으로 이동
    @GetMapping("/user/logout")
    public String logoutUser(){
        return "index";
    }
}

