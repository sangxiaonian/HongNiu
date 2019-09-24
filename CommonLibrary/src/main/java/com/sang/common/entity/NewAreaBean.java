package com.sang.common.entity;

/**
 * 作者： ${PING} on 2018/9/12.
 */
public class NewAreaBean {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name==null?"":name;
    }
}
