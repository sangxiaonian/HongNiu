package com.hongniu.baselibrary.widget.order.helper;

/**
 * 作者： ${PING} on 2018/10/23.
 */
public class ButtonInforBean {

    private int type;
    private String text;

    public ButtonInforBean(int type, String text) {
        this.type = type;
        this.text = text;
    }



    public ButtonInforBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
