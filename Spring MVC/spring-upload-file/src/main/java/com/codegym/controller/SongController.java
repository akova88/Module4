package com.codegym.controller;

import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.model.Song;
import com.codegym.model.SongForm;
import com.codegym.service.ISongService;
import com.codegym.service.SongService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    @Value("${file-upload}")
    private String fileUpload;

    private final ISongService songService = new SongService();

    @GetMapping("")
    public String index(Model model) {
        List<Song> songs = songService.findAll();
        model.addAttribute("songs", songs);
        return "/song";
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/addsong");
        modelAndView.addObject("songForm", new SongForm());
        return modelAndView;
    }

    @PostMapping("/save")
    public String saveSong(@ModelAttribute SongForm songForm, RedirectAttributes redirect) {
        MultipartFile multipartFile = songForm.getSong();
        String fileName = multipartFile.getOriginalFilename();
        ModelAndView modelAndView = new ModelAndView("/create");
        if (!isSupportedFormat(fileName)) {
            modelAndView.addObject("error", "File không đúng định daạng");
        }
        try {
            FileCopyUtils.copy(songForm.getSong().getBytes(), new File(fileUpload, fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Song song = new Song(songForm.getId(), songForm.getName(), songForm.getArtist(), songForm.getGenre(), fileName);
        songService.save(song);
//        ModelAndView modelAndView = new ModelAndView("/create");
//        modelAndView.addObject("productForm", productForm);
//        modelAndView.addObject("message", "Created new product successfully !");
//        return modelAndView;
        return "redirect:/songs";
    }

    private boolean isSupportedFormat(String fileName) {
        String[] supportedFormats = {".mp3", ".wav", ".ogg", ".m4p"};
        for (String format : supportedFormats) {
            if (fileName.toLowerCase().endsWith(format)) {
                return true;
            }
        }
        return false;
    }
}
