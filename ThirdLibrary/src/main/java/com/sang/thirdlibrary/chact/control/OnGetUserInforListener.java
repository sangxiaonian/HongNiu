package com.sang.thirdlibrary.chact.control;

import com.sang.thirdlibrary.chact.UserInfor;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/11/29.
 * 当需要异步获取用户信息的时候
 */
public interface OnGetUserInforListener {

    /**
     * 异步获取用户信息
     * @param usrID 用户ID
     */
    Observable<UserInfor> onGetUserInfor(String usrID);
}
