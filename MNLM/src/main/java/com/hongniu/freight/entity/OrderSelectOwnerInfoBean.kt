package com.hongniu.freight.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * 作者：  on 2020/12/28.
 * 选择实际承运人
 */
data class OrderSelectOwnerInfoBean(var carNumber:String?,//车牌号
                                    var ownerCompanyAccountId:String?,//承运人企业ID
                                    var vehicleType:String?,//车辆类型
                                    var model:String?,//车辆品牌
                                    var ownerId:String?,//承运人id
                                    var ownerName:String?,//承运人姓名
                                    var ownerMobile:String?,//承运人手机
                                    var isDriver:String?,//是否是司机
                                    var carid:String?,//车辆ID
                                    ) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor() : this(null,null,null,null,null,null,null,null,null);
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(carNumber)
        parcel.writeString(ownerCompanyAccountId)
        parcel.writeString(vehicleType)
        parcel.writeString(model)
        parcel.writeString(ownerId)
        parcel.writeString(ownerName)
        parcel.writeString(ownerMobile)
        parcel.writeString(isDriver)
        parcel.writeString(carid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderSelectOwnerInfoBean> {
        override fun createFromParcel(parcel: Parcel): OrderSelectOwnerInfoBean {
            return OrderSelectOwnerInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<OrderSelectOwnerInfoBean?> {
            return arrayOfNulls(size)
        }
    }

}