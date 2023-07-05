package com.yxg.protocol;

import com.yxg.common.Invocation;
import com.yxg.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServletHandler {
    public void handler(HttpServletRequest req, HttpServletResponse res) {
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interfaceName = invocation.getInterfaceName();
            System.out.println("打印接口名："+interfaceName);
            Class classImpl = LocalRegister.get(interfaceName, "1.0");
            // 获取方法名以及参数的类型
            Method method = classImpl.getMethod(invocation.getMethodName(),invocation.getParameterTypes());
            // 方法的调用需要通过具体的实例来实现
            String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());

            IOUtils.write(result, res.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
