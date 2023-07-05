package com.yxg.register;


import com.yxg.common.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapRemoteRegister {
    private static Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName, URL url) {
        List<URL> list = map.get(interfaceName);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(url);
        map.put(interfaceName, list);
        saveFile();
    }

    public static List<URL> get(String interfaceName) {
        Map<String, List<URL>> tmpmap = getFile();
        return tmpmap.get(interfaceName);
    }
    // 存的时候将map写入文件中
    private static void saveFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
            System.out.println("存入文件成功："+map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //存的时候从文件中取出
    private static Map<String, List<URL>> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            System.out.println("从文件中取到："+map);

            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
