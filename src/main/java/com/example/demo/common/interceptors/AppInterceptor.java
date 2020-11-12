package com.example.demo.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author duanxiaoduan
 * @version 2020/8/7
 */
public class AppInterceptor extends HttpServletRequestWrapper {
    public AppInterceptor(HttpServletRequest request) {
        super(request);
    }
}
