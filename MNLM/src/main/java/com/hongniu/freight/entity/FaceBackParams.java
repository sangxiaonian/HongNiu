package com.hongniu.freight.entity;

import java.util.List;

/**
 * 作者：  on 2020/3/15.
 */
public class FaceBackParams {
  private String content;//	true	string	用户反馈内容
  private List<String> imageUrls;//	true	Array	用户上传图片url(相对路径)数组传入文件类型参考11.1接口)

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
