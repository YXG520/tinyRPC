package com.yxg.common;

import java.io.Serializable;

public class URL implements Serializable {
    private String hostname;
    private int port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public URL(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
}
