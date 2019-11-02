package com.hongniu.modulecargoodsmatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：  on 2019/11/1.
 */
public class MatchCarTypeInfoBean implements Parcelable {
   private String id	;//true	number	车辆类型id
   private String carType	;//true	string	车辆类型名称
   private String carImage	;//true	string	图片url
   private float load	;//true	number	载重 (吨)
   private float length	;//true	number	车长 (米)
   private float width	;//true	number	宽
   private float height	;//true	number	高
   private float startPrice	;//true	number	起步价(10公里内)
   private float exceedMileagePrice	;//true	number	超里程费(每超出一公里增加费用)

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getCarType() {
      return carType;
   }

   public void setCarType(String carType) {
      this.carType = carType;
   }

   public String getCarImage() {
      return carImage;
   }

   public void setCarImage(String carImage) {
      this.carImage = carImage;
   }

   public float getLoad() {
      return load;
   }

   public void setLoad(float load) {
      this.load = load;
   }

   public float getLength() {
      return length;
   }

   public void setLength(float length) {
      this.length = length;
   }

   public float getWidth() {
      return width;
   }

   public void setWidth(float width) {
      this.width = width;
   }

   public float getHeight() {
      return height;
   }

   public void setHeight(float height) {
      this.height = height;
   }

   public float getStartPrice() {
      return startPrice;
   }

   public void setStartPrice(float startPrice) {
      this.startPrice = startPrice;
   }

   public float getExceedMileagePrice() {
      return exceedMileagePrice;
   }

   public void setExceedMileagePrice(float exceedMileagePrice) {
      this.exceedMileagePrice = exceedMileagePrice;
   }

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.id);
      dest.writeString(this.carType);
      dest.writeString(this.carImage);
      dest.writeFloat(this.load);
      dest.writeFloat(this.length);
      dest.writeFloat(this.width);
      dest.writeFloat(this.height);
      dest.writeFloat(this.startPrice);
      dest.writeFloat(this.exceedMileagePrice);
   }

   public MatchCarTypeInfoBean() {
   }

   protected MatchCarTypeInfoBean(Parcel in) {
      this.id = in.readString();
      this.carType = in.readString();
      this.carImage = in.readString();
      this.load = in.readFloat();
      this.length = in.readFloat();
      this.width = in.readFloat();
      this.height = in.readFloat();
      this.startPrice = in.readFloat();
      this.exceedMileagePrice = in.readFloat();
   }

   public static final Parcelable.Creator<MatchCarTypeInfoBean> CREATOR = new Parcelable.Creator<MatchCarTypeInfoBean>() {
      @Override
      public MatchCarTypeInfoBean createFromParcel(Parcel source) {
         return new MatchCarTypeInfoBean(source);
      }

      @Override
      public MatchCarTypeInfoBean[] newArray(int size) {
         return new MatchCarTypeInfoBean[size];
      }
   };
}
