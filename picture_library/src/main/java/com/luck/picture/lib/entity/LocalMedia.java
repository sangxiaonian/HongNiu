package com.luck.picture.lib.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.lib.entity
 * describe：for PictureSelector media entity.
 * email：893855882@qq.com
 * data：2017/5/24
 */

public class LocalMedia implements Parcelable {
    //相对路径
   private String relativePath;
    //路径
    private String path;
    private String compressPath;
    private String cutPath;
    private long duration;
    private boolean isChecked;
    private boolean isCut;
    public int position;
    private int num;
    private int mimeType;
    private String pictureType;
    private boolean compressed;
    private int width;
    private int height;


    public LocalMedia() {

    }

    public LocalMedia(String path, long duration, int mimeType, String pictureType) {
        this.path = path;
        this.duration = duration;
        this.mimeType = mimeType;
        this.pictureType = pictureType;
    }

    public LocalMedia(String path, long duration, int mimeType, String pictureType, int width, int height) {
        this.path = path;
        this.duration = duration;
        this.mimeType = mimeType;
        this.pictureType = pictureType;
        this.width = width;
        this.height = height;
    }

    public LocalMedia(String path, long duration,
                      boolean isChecked, int position, int num, int mimeType) {
        this.path = path;
        this.duration = duration;
        this.isChecked = isChecked;
        this.position = position;
        this.num = num;
        this.mimeType = mimeType;
    }

    protected LocalMedia(Parcel in) {
        relativePath = in.readString();
        path = in.readString();
        compressPath = in.readString();
        cutPath = in.readString();
        duration = in.readLong();
        isChecked = in.readByte() != 0;
        isCut = in.readByte() != 0;
        position = in.readInt();
        num = in.readInt();
        mimeType = in.readInt();
        pictureType = in.readString();
        compressed = in.readByte() != 0;
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<LocalMedia> CREATOR = new Creator<LocalMedia>() {
        @Override
        public LocalMedia createFromParcel(Parcel in) {
            return new LocalMedia(in);
        }

        @Override
        public LocalMedia[] newArray(int size) {
            return new LocalMedia[size];
        }
    };

    public String getPictureType() {
        if (TextUtils.isEmpty(pictureType)) {
            pictureType = "image/jpeg";
        }
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCompressPath() {
        return compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public String getCutPath() {
        return cutPath;
    }

    public void setCutPath(String cutPath) {
        this.cutPath = cutPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * 获取相对路径
     * @return
     */
    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCut() {
        return isCut;
    }

    public void setCut(boolean cut) {
        isCut = cut;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMimeType() {
        return mimeType;
    }

    public void setMimeType(int mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(relativePath);
        dest.writeString(path);
        dest.writeString(compressPath);
        dest.writeString(cutPath);
        dest.writeLong(duration);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isCut ? 1 : 0));
        dest.writeInt(position);
        dest.writeInt(num);
        dest.writeInt(mimeType);
        dest.writeString(pictureType);
        dest.writeByte((byte) (compressed ? 1 : 0));
        dest.writeInt(width);
        dest.writeInt(height);
    }


}
