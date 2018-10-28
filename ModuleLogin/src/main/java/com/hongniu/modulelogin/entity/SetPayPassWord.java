package com.hongniu.modulelogin.entity;

/**
 * 作者： ${PING} on 2018/10/26.
 */
public class SetPayPassWord {
    public String newPassWord;
    public String checkCode;

    public SetPayPassWord(String newPassWord, String checkCode) {
        this.newPassWord = newPassWord;
        this.checkCode = checkCode;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
