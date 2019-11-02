package com.hongniu.modulecargoodsmatch.entity;

/**
 * 作者：  on 2019/11/1.
 */
public class MatchCarTypeInfoBean {
   private int id	;//true	number	车辆类型id
   private String carType	;//true	string	车辆类型名称
   private String carImage	;//true	string	图片url
   private float load	;//true	number	载重 (吨)
   private float length	;//true	number	车长 (米)
   private float width	;//true	number	宽
   private float height	;//true	number	高
   private float startPrice	;//true	number	起步价(10公里内)
   private float exceedMileagePrice	;//true	number	超里程费(每超出一公里增加费用)

   public int getId() {
      return id;
   }

   public void setId(int id) {
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
}
