package com.CS6310.Team045.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
    @GetMapping(value = "/")
    public ModelAndView hello(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("homepage.html");
        return modelAndView;}

    @GetMapping("/user")
    public String user(){ return "This is the User Page. ";}

    @GetMapping("/admin")
    public String admin(){
        return "This is Admin Page";
    }
}
