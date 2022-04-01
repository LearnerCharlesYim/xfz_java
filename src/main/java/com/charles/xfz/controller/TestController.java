package com.charles.xfz.controller;

import com.charles.xfz.domain.dto.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/test")
    public R test() {
        return R.ok();
    }
}
