package com.charles.xfz.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController {


    @RequestMapping("/search")
    public String search(){
        return "front/search/search";
    }
}
