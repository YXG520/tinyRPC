package com.yxg.protocol;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServlet extends HttpServlet {

    // 这是一种责任链or过滤器模式
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) {
        System.out.println("责任链，查看本地配置，转发到相应的servlet");
        // 为什么要用Dispatch，因为这样可以依据传入的参数决定调用什么处理器
        // 如果是心跳请求，日志请求，还是涉及到业务的请求
        // 每一种请求都可以分发给不同的处理器。
        new HttpServletHandler().handler(req, res);
    }
}
