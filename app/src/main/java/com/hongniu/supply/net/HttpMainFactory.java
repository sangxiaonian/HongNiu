package com.hongniu.supply.net;

import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.supply.entity.HomeADBean;
import com.sang.common.net.rx.RxUtils;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class HttpMainFactory {


    /**
     * 查询首页活动信息
     *
     * @return
     */
    public static Observable<CommonBean<List<HomeADBean>>> queryActivity() {
       return MainClient.getInstance().getService()
                .queryActivity()
                .compose(RxUtils.<CommonBean<List<HomeADBean>>>getSchedulersObservableTransformer())
        ;
    }


}
