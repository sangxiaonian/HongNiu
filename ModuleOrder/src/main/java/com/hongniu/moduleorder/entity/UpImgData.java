package com.hongniu.moduleorder.entity;

/**
 * 作者： ${PING} on 2018/10/23.
 */
public class UpImgData {
    /**
     * true	string	图片相对路径,保存时使用
      */
    private String  path	       ;
    /**
     * 	true	string	图片绝对路径
      */
    private String  absolutePath;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
}
