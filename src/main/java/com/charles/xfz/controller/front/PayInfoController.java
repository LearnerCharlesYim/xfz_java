package com.charles.xfz.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay-info")
public class PayInfoController {

    @RequestMapping
    public String index(){
        return "front/payinfo/payinfo";
    }
}
