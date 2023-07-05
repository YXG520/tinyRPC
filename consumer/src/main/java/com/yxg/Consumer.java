package com.yxg;

import com.yxg.common.Invocation;
import com.yxg.protocol.HttpClient;
import com.yxg.proxy.ProxyFactory;

import java.io.IOException;

public class Consumer {
    public static void main(String[] args) {
        // 构造调用请求的入参

        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String res = helloService.sayHello("yxgss");
        System.out.println(res);

    }
    public static void main2(String[] args) {
        // 构造调用请求的入参
        Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
                new Class[]{String.class}, new Object[]{"yxg"});

        HttpClient httpClient = new HttpClient();
        try {
            System.out.println("调用httpClient");
            String result = httpClient.send("localhost", 8080, invocation);
            System.out.println("打印响应结果："+result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
