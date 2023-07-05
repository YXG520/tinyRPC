package com.yxg;

import com.yxg.common.URL;
import com.yxg.protocol.HttpServer;
import com.yxg.register.LocalRegister;
import com.yxg.register.MapRemoteRegister;

public class Provider {
    public static void main(String[] args) {
        // 本地注册
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        System.out.println("刚刚注册的服务是："+LocalRegister.get(HelloService.class.getName(),"1.0"));

        // 存储到注册中心
        URL url = new URL("localhost", 8080);
        MapRemoteRegister.register(HelloService.class.getName(), url);

        // 注册到Netty、tomcat服务器中
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
