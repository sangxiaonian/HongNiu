package com.hongniu.freight.entity;

import java.io.Serializable;

public class H5Config implements Serializable {
    public String title;
    public String url;
    public boolean changeTitle;
    public boolean isDarkTitle;

    public H5Config(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public H5Config(String title, String url, boolean changeTitle) {
        this.title = title;
        this.url = url;
        this.changeTitle = changeTitle;
    }
}