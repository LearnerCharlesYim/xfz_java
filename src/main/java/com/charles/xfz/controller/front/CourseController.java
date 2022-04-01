package com.charles.xfz.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {

    @RequestMapping
    public String index(){
        return "front/course/course";
    }

    @RequestMapping("/{id}")
    public String detail(@PathVariable Integer id){
        return "front/course/course_detail";
    }
}
