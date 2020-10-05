package com.sang.thirdlibrary.picture;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 作者： ${PING} on 2018/11/13.
 * 图片上传所需要的bean
 */
public class ImageInforBean implements Cloneable, Parcelable {

    /**
     * 图片的路径、url
     */
    private String path;
    /**
     * 原始路径
     */
    private String pathOriginal;

    /**
     * 图片上传完成之后的绝对url
     */
    private String pathUrl;
    /**
     * 图片上传完成之后的相对url
     */
    private String pathRelativeUrl;

    /**
     * fileTypeId 1 意见反馈、个人资料 2 户型图  3 房源图片
     */
    private int type = 3;


    /**
     * 图片上传的进度 0-100 小于0的情况，图片上传失败
     */
    private float progress;

    /**
     * 错误类型，0 上传失败 1 图片太小
     */
    private int errorType = -1;

    public String getPathOriginal() {
        return pathOriginal;
    }

    public void setPathOriginal(String pathOriginal) {
        this.pathOriginal = pathOriginal;
    }

    public String getPathRelativeUrl() {
        return pathRelativeUrl;
    }

    public void setPathRelativeUrl(String pathRelativeUrl) {
        this.pathRelativeUrl = pathRelativeUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }


    public String getImageAddress() {
        return TextUtils.isEmpty(path) ? (TextUtils.isEmpty(pathUrl) ? "" : pathUrl) : path;
    }

    @Override
    public ImageInforBean clone() throws CloneNotSupportedException {
        return (ImageInforBean) super.clone();
    }

    @Override
    public String toString() {
        return "ImageInforBean{" +
                "type=" + type +
                '}';
    }

    public ImageInforBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.pathOriginal);
        dest.writeString(this.pathUrl);
        dest.writeString(this.pathRelativeUrl);
        dest.writeInt(this.type);
        dest.writeFloat(this.progress);
        dest.writeInt(this.errorType);
    }

    protected ImageInforBean(Parcel in) {
        this.path = in.readString();
        this.pathOriginal = in.readString();
        this.pathUrl = in.readString();
        this.pathRelativeUrl = in.readString();
        this.type = in.readInt();
        this.progress = in.readFloat();
        this.errorType = in.readInt();
    }

    public static final Creator<ImageInforBean> CREATOR = new Creator<ImageInforBean>() {
        @Override
        public ImageInforBean createFromParcel(Parcel source) {
            return new ImageInforBean(source);
        }

        @Override
        public ImageInforBean[] newArray(int size) {
            return new ImageInforBean[size];
        }
    };
}
