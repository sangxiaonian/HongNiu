package com.hongniu.moduleorder.entity;

import java.util.List;

/**
 * 作者： ${PING} on 2018/10/23.
 * 查看回单
 */
public class QueryReceiveBean {


    /**
     * remark : 测试
     * images : [{"id":1,"imageUrl":null},{"id":2,"imageUrl":null}]
     */

    private String remark;
    private List<ImagesBean> images;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * id : 1
         * imageUrl : null
         */

        private String id;
        private String imageUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
