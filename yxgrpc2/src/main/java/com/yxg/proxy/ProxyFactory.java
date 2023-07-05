package com.yxg.proxy;

import com.yxg.common.Invocation;
import com.yxg.common.URL;
import com.yxg.loalBalance.LoadBalance;
import com.yxg.protocol.HttpClient;
import com.yxg.register.MapRemoteRegister;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {

    public static void fallback() {
        System.out.println("执行容错逻辑");
    }
    public static <T>T getProxy(Class interfaceClass) {
        // 第一个参数：被代理的类型的类加载器
        // 第二个：被代理的接口
        // 第三个：代理逻辑
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String mock = System.getProperty("mock");

                if (mock != null && mock.startsWith("return:")) {
                    String result = mock.replace("return:", "");
                    return result;
                }


                // 构造调用请求的入参
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(), args);
                System.out.println("准备调用httpClient。。。");
                HttpClient httpClient = new HttpClient();
                String result=null;
                int max = 3;
                // 服务发现：从配置中心获取ip和端口号
                List<URL> urls = MapRemoteRegister.get(interfaceClass.getName());
                List<URL> invokedUrls = new ArrayList<>();
                // 本地负载均衡
                System.out.println("打印服务发现数据："+urls.toString());
                while (max > 0) {
                    urls.remove(invokedUrls);
                    URL url = LoadBalance.randomUrl(urls);
                    invokedUrls.add(url);

                    try {
                        // 服务调用
                        result = httpClient.send(url.getHostname(), url.getPort(), invocation);
                        System.out.println("打印响应结果："+result);
                        break;
                    } catch (IOException e) {
                        // 重试逻辑
                        if (max-- !=0 ) continue;
                        // 写容错逻辑：error Callback, 也可以针对不同的服务调用不同的fallbackMethod
                        // 这个fallbackMethod的类也应该实现对应的业务
                        fallback();
                        // 返回错误
                        return "error";
                    }
                }

                return result;
            }
        });
        return (T) proxyInstance;
    }
}

