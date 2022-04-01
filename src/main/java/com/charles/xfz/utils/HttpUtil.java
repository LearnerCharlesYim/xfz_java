package com.charles.xfz.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return (request.getHeader("X-Requested-With") != null &&
                "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
    }
}
