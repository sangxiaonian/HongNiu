package com.hongniu.freight.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * 作者：  on 2020/12/28.
 *
 */
data class OrderSelectDriverInfoBean(var id:String?,var mobile:String?,var contact:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor() : this(null,null,null)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(mobile)
        parcel.writeString(contact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderSelectDriverInfoBean> {
        override fun createFromParcel(parcel: Parcel): OrderSelectDriverInfoBean {
            return OrderSelectDriverInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<OrderSelectDriverInfoBean?> {
            return arrayOfNulls(size)
        }
    }
}