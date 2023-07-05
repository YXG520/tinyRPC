package com.yxg.loalBalance;

import com.yxg.common.URL;

import java.util.List;
import java.util.Random;

public class LoadBalance {
    public static URL randomUrl(List<URL> urls) {
        Random random = new Random();

        int i = random.nextInt(urls.size());
        return urls.get(i);
    }
}
