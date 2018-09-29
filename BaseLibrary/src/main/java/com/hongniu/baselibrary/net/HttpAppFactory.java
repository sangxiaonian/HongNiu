package com.hongniu.baselibrary.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.sang.common.net.rx.RxUtils;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/13.
 * <p>
 * 登录使用的App
 */
public class HttpAppFactory {


    /**
     * 获取用户最近使用角色
     */
    public static Observable<CommonBean<RoleTypeBean>> getRoleType() {
        return AppClient.getInstance().getService().getRoleType()
                .compose(RxUtils.<CommonBean<RoleTypeBean>>getSchedulersObservableTransformer());
    }

}
