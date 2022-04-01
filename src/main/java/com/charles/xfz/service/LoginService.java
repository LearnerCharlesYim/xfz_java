package com.charles.xfz.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.charles.xfz.domain.entity.User;
import com.charles.xfz.domain.enums.ResultCode;
import com.charles.xfz.domain.vo.LoginParam;
import com.charles.xfz.domain.vo.RegisterParam;
import com.charles.xfz.exception.BizException;
import com.charles.xfz.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserRepository userRepository;

    public void login(LoginParam loginParam) {
        User user = userRepository.findByTelephone(loginParam.getTelephone());
        if (user == null) {
            throw new BizException(ResultCode.USER_CREDENTIALS_ERROR);
        }
        if (!user.getPassword().equals(loginParam.getPassword())) {
            throw new BizException(ResultCode.USER_CREDENTIALS_ERROR);
        }
        if (!user.getActive()) {
            throw new BizException(ResultCode.USER_ACCOUNT_DISABLE);
        }

        if (loginParam.getRememberMe() == 1) {
            StpUtil.login(user.getId(), new SaLoginModel()
                    .setIsLastingCookie(true)
                    .setTimeout(60 * 60 * 24 * 7));
        } else {
            StpUtil.login(user.getId(), false);
        }

        StpUtil.getSession().set("loginUser", user);
    }

    public void register(RegisterParam registerParam) {
        if (!StringUtils.pathEquals(registerParam.getPassword(), registerParam.getRepeatPassword())) {
            throw new BizException(ResultCode.INCONSISTENT_PASSWORD);
        }

        String code = redisTemplate.boundValueOps("captcha:graph-captcha:" + registerParam.getCaptchaUuid()).get();

        if (code == null) {
            throw new BizException(ResultCode.GRAPH_CAPTCHA_EXPIRED);
        }

        if (!StringUtils.pathEquals(registerParam.getImgCaptcha(), code)) {
            throw new BizException(ResultCode.GRAPH_CAPTCHA_ERROR);
        }

        User user = userRepository.findByTelephone(registerParam.getTelephone());
        if (user != null) {
            throw new BizException(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
        }
        User registerUser = new User();
        BeanUtils.copyProperties(registerParam, registerUser);
        userRepository.save(registerUser);
        redisTemplate.delete("captcha:graph-captcha:" + registerParam.getCaptchaUuid());
    }
}
