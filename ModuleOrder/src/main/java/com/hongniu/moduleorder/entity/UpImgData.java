package com.hongniu.moduleorder.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 作者： ${PING} on 2018/10/23.
 */
public class UpImgData {
    /**
     * true	string	图片相对路径,保存时使用，如果使用的是本地图片，则为null，对应的是相对路径
      */
    @SerializedName(value = "path", alternate = {"relativeImageUrl"})
    private String  path	       ;
    /**
     * 	true	string	图片绝对路径，带http,或者本地图图片的路径
      */
    @SerializedName(value = "absolutePath", alternate = {"imageUrl"})
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
