package com.charles.xfz.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    @RequestMapping("/{id}")
    public String detail(@PathVariable Integer id){
        return "front/news/news_detail";
    }
}
