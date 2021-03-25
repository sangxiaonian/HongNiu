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
  public String rongId;//	true	string	聊天融云ID
  public String userName;//	true	string	创建用户名
  public String userMobile;//	true	string	创建用户手机号
  public int status;//	true	货源状态(0待接单 1待确认 2已下单 3运输中 4已完成 5已失效)



  public String goodsSourceDetail;//货物详情
  public String remark;


  public GoodsOwnerInforBean() {
  }

  protected GoodsOwnerInforBean(Parcel in) {
    id = in.readString();
    gsNum = in.readString();
    startTime = in.readString();
    creationTime = in.readString();
    startPlaceInfo = in.readString();
    startPlaceX = in.readString();
    startPlaceY = in.readString();
    destinationInfo = in.readString();
    destinationX = in.readString();
    destinationY = in.readString();
    departNum = in.readString();
    goodName = in.readString();
    goodVolume = in.readString();
    goodWeight = in.readString();
    freightAmount = in.readString();
    carTypeId = in.readString();
    carType = in.readString();
    carLength = in.readString();
    userId = in.readString();
    rongId = in.readString();
    userName = in.readString();
    userMobile = in.readString();
    status = in.readInt();
    goodsSourceDetail = in.readString();
    remark = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(id);
    dest.writeString(gsNum);
    dest.writeString(startTime);
    dest.writeString(creationTime);
    dest.writeString(startPlaceInfo);
    dest.writeString(startPlaceX);
    dest.writeString(startPlaceY);
    dest.writeString(destinationInfo);
    dest.writeString(destinationX);
    dest.writeString(destinationY);
    dest.writeString(departNum);
    dest.writeString(goodName);
    dest.writeString(goodVolume);
    dest.writeString(goodWeight);
    dest.writeString(freightAmount);
    dest.writeString(carTypeId);
    dest.writeString(carType);
    dest.writeString(carLength);
    dest.writeString(userId);
    dest.writeString(rongId);
    dest.writeString(userName);
    dest.writeString(userMobile);
    dest.writeInt(status);
    dest.writeString(goodsSourceDetail);
    dest.writeString(remark);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<GoodsOwnerInforBean> CREATOR = new Creator<GoodsOwnerInforBean>() {
    @Override
    public GoodsOwnerInforBean createFromParcel(Parcel in) {
      return new GoodsOwnerInforBean(in);
    }

    @Override
    public GoodsOwnerInforBean[] newArray(int size) {
      return new GoodsOwnerInforBean[size];
    }
  };
}
