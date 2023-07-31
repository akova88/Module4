package com.cg.thithuchanh_md4.controller;

import com.cg.thithuchanh_md4.model.Video;
import com.cg.thithuchanh_md4.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/videos")
public class VideoController {
    @Autowired
    private IVideoService videoService;


    @GetMapping
    public String showListPage(Model model) {
        List<Video> videos = videoService.findAll();
        model.addAttribute("videos", videos);
        return "list";
    }
}
