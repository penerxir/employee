package com.wangpx.controller;



import com.wangpx.exception.MyException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ExceptionHandlerController implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();
        MyException myException = null;

        //如果抛出的是系统自定义的异常则直接转换
        if(e instanceof MyException) {
            myException = (MyException) e;
        } else {
            //如果抛出的不是系统自定义的异常则重新构造一个未知错误异常

            myException = new MyException("系统未知错误");
        }

        //向前台返回错误信息
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", myException.getMessage());
        modelAndView.setViewName("/WEB-INF/jsp/error.jsp");

        return modelAndView;

    }
}
