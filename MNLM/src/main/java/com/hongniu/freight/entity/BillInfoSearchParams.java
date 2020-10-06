package com.hongniu.freight.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.fy.companylibrary.config.Param;

/**
 * 作者：  on 2020/3/8.
 */
public class BillInfoSearchParams implements Parcelable {
  private String year;//		int	年份
  private String month;//	false	int	月份，从1开始，1代表1月，2代表2月以此类推。
  private String carNo;//	false	String	车牌号
  private int financeType;//	true	int	财务类型，0支出和收入；1支出；2收入
  private int pageNum;//	false	int	页面索引
  private int pageSize= Param.PAGE_SIZE;//	false	int	页面记录条数
  private int feeType;//	false	int	1 运费，2保费。默认1运费。支出搜索时请传0或者1。

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public int getFinanceType() {
        return financeType;
    }

    public void setFinanceType(int financeType) {
        this.financeType = financeType;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFeeType() {
        return feeType;
    }

    public void setFeeType(int feeType) {
        this.feeType = feeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.year);
        dest.writeString(this.month);
        dest.writeString(this.carNo);
        dest.writeInt(this.financeType);
        dest.writeInt(this.pageNum);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.feeType);
    }

    public BillInfoSearchParams() {
    }

    protected BillInfoSearchParams(Parcel in) {
        this.year = in.readString();
        this.month = in.readString();
        this.carNo = in.readString();
        this.financeType = in.readInt();
        this.pageNum = in.readInt();
        this.pageSize = in.readInt();
        this.feeType = in.readInt();
    }

    public static final Parcelable.Creator<BillInfoSearchParams> CREATOR = new Parcelable.Creator<BillInfoSearchParams>() {
        @Override
        public BillInfoSearchParams createFromParcel(Parcel source) {
            return new BillInfoSearchParams(source);
        }

        @Override
        public BillInfoSearchParams[] newArray(int size) {
            return new BillInfoSearchParams[size];
        }
    };
}
