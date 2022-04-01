package com.charles.xfz.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.charles.xfz.domain.dto.R;
import com.charles.xfz.domain.enums.ResultCode;
import com.charles.xfz.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
@Slf4j
public class GlobeExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(NotLoginException.class)
    private void handleNotLoginException(NotLoginException e,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {

        if (HttpUtil.isAjaxRequest(request)) {
            String message;
            switch (e.getType()) {
                case NotLoginException.NOT_TOKEN:
                    message = "未提供token";
                    break;
                case NotLoginException.INVALID_TOKEN:
                    message = "token无效";
                    break;
                case NotLoginException.TOKEN_TIMEOUT:
                    message = "token已过期";
                    break;
                case NotLoginException.BE_REPLACED:
                    message = "token已被顶下线";
                    break;
                case NotLoginException.KICK_OUT:
                    message = "token已被踢下线";
                    break;
                default:
                    message = "当前会话未登录";
                    break;
            }
            //处理编码方式，防止中文乱码的情况
            response.setContentType("application/json;charset=utf-8");
            //塞到HttpServletResponse中返回给前台
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(R.fail(message)));
            writer.close();
        } else {
            response.sendRedirect("/");
        }
    }

    @ResponseBody
    @ExceptionHandler(BizException.class)
    private R bizExceptionHandler(BizException e) {
        log.error("发送业务异常！原因是：{}", e.getErrorMsg());
        return R.fail(e.getErrorCode(), e.getErrorMsg());
    }

    @ResponseBody
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public R handleParamValidateException(Exception e) {
        if (e instanceof BindException) {
            // ex.getFieldError():随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，
            // 则调用ex.getAllErrors()
            FieldError fieldError = ((BindException) e).getFieldError();
            // 生成返回结果
            assert fieldError != null;
            log.error("发送参数验证异常！原因是：{}", fieldError.getDefaultMessage());
            return R.fail(ResultCode.PARAM_NOT_VALID.getCode(), fieldError.getDefaultMessage());
        } else {
            log.error("发送参数验证异常！原因是：{}", e.getMessage().split(": ")[1]);
            return R.fail(ResultCode.PARAM_NOT_VALID.getCode(), e.getMessage().split(": ")[1]);
        }
    }

    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handlerRequestMethodException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        String method = request.getMethod();
        log.error("{}不支持{}方法", requestUrl, method);
        return R.fail(requestUrl + "不支持" + method + "方法");
    }

    @ResponseBody
    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpMessageNotReadableException.class,
    })
    private R paramExceptionHandler(Exception e) {
        if (e instanceof HttpMediaTypeNotSupportedException) {
            return R.fail(ResultCode.PARAM_TYPE_ERROR);
        } else {
            return R.fail(ResultCode.PARAM_NOT_COMPLETE);
        }
    }
}
