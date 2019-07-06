package com.hongniu.modulecargoodsmatch.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者： ${PING} on 2019/5/12.
 * 车货匹配列表获取寻车信息
 */
public class GoodsOwnerInforBean implements Parcelable {

  public String id;//	true	string	货源id
  public String gsNum;//	true	string	货源单号
  public String startTime;//	true	string	发货日期（字符串，格式YYYY-MM-dd）
  public String creationTime;//	true	string	创建时间
  public String startPlaceInfo;//	true	string(128)	开始地点描述
  public String startPlaceX;//	true	string	开始地点经度
  public String startPlaceY;//	true	string	开始地点纬度
  public String destinationInfo;//	true	string(128)	目的地描述
  public String destinationX;//	true	string	目的地经度
  public String destinationY;//	true	string	目的地纬度
  public String departNum;//	true	string(128)	发车编号
  public String goodName;//	true	string(128)	货物名称
  public String goodVolume;//	true	string	货物体积 (方)
  public String goodWeight;//	true	string	货物质量(吨)
  public String freightAmount;//	true	string	运费
  public String carTypeId;//	false	string	车辆类型id
  public String carType;//	true	string	车辆类型名称
  public String carLength;//	true	string	车辆长度
  public String userId;//	true	string	创建用户id
  public String userName;//	true	string	创建用户名
  public String userMobile;//	true	string	创建用户手机号
  public String status;//	true	string	货源状态(0生成1已预定2已失效 3完成)


  public String goodsSourceDetail;//货物详情
  public String remark;


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.gsNum);
    dest.writeString(this.startTime);
    dest.writeString(this.creationTime);
    dest.writeString(this.startPlaceInfo);
    dest.writeString(this.startPlaceX);
    dest.writeString(this.startPlaceY);
    dest.writeString(this.destinationInfo);
    dest.writeString(this.destinationX);
    dest.writeString(this.destinationY);
    dest.writeString(this.departNum);
    dest.writeString(this.goodName);
    dest.writeString(this.goodVolume);
    dest.writeString(this.goodWeight);
    dest.writeString(this.freightAmount);
    dest.writeString(this.carTypeId);
    dest.writeString(this.carType);
    dest.writeString(this.carLength);
    dest.writeString(this.userId);
    dest.writeString(this.userName);
    dest.writeString(this.userMobile);
    dest.writeString(this.status);
    dest.writeString(this.goodsSourceDetail);
    dest.writeString(this.remark);
  }

  public GoodsOwnerInforBean() {
  }

  protected GoodsOwnerInforBean(Parcel in) {
    this.id = in.readString();
    this.gsNum = in.readString();
    this.startTime = in.readString();
    this.creationTime = in.readString();
    this.startPlaceInfo = in.readString();
    this.startPlaceX = in.readString();
    this.startPlaceY = in.readString();
    this.destinationInfo = in.readString();
    this.destinationX = in.readString();
    this.destinationY = in.readString();
    this.departNum = in.readString();
    this.goodName = in.readString();
    this.goodVolume = in.readString();
    this.goodWeight = in.readString();
    this.freightAmount = in.readString();
    this.carTypeId = in.readString();
    this.carType = in.readString();
    this.carLength = in.readString();
    this.userId = in.readString();
    this.userName = in.readString();
    this.userMobile = in.readString();
    this.status = in.readString();
    this.goodsSourceDetail = in.readString();
    this.remark = in.readString();
  }

  public static final Parcelable.Creator<GoodsOwnerInforBean> CREATOR = new Parcelable.Creator<GoodsOwnerInforBean>() {
    @Override
    public GoodsOwnerInforBean createFromParcel(Parcel source) {
      return new GoodsOwnerInforBean(source);
    }

    @Override
    public GoodsOwnerInforBean[] newArray(int size) {
      return new GoodsOwnerInforBean[size];
    }
  };
}
