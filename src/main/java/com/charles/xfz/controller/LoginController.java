package com.charles.xfz.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.charles.xfz.domain.dto.R;
import com.charles.xfz.domain.vo.LoginParam;
import com.charles.xfz.domain.vo.RegisterParam;
import com.charles.xfz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("cms/login")
    public String loginUi() {
        return "cms/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R login(@Validated LoginParam loginParam) {
        loginService.login(loginParam);
        return R.ok();
    }

    @RequestMapping("/logout")
    public String logout() {
        StpUtil.logout();
        return "redirect:/";
    }

    @PostMapping("/register")
    @ResponseBody
    public R register(@Validated RegisterParam registerParam) {
        loginService.register(registerParam);
        return R.ok();
    }
}
