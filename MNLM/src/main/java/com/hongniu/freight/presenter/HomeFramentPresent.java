package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.mode.HomeFragmentMode;
import com.hongniu.freight.utils.InfoUtils;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeFramentPresent implements HomeControl.IHomeFragmentPresent {

    HomeControl.IHomeFragmentView view;
    HomeControl.IHomeFragmentMode mode;

    public HomeFramentPresent(HomeControl.IHomeFragmentView view) {
        this.view = view;
        mode = new HomeFragmentMode();
    }

    /**
     * 储存是否是从登录页面跳转过来的
     *
     * @param isLogin
     */
    @Override
    public void saveInfo(boolean isLogin) {
        mode.saveInfo(isLogin);
        if (isLogin) {
            //如果是登录
            if (InfoUtils.getRole(InfoUtils.getLoginInfo()) == Role.UNKNOW) {
                view.jump2SelectRole();
            }
        }
    }

    /**
     * 初始化数据
     *
     * @param listener
     */
    @Override
    public void queryInfo(TaskControl.OnTaskListener listener) {

        Observable.concat(
                mode.queryMyInfo(),
                mode.queryOrderNum(),
                mode.queryOrderList()
        )
                .subscribe(new BaseObserver<CommonBean<? extends Object>>(listener) {
                    @Override
                    public void onNext(CommonBean<?> result) {
                        super.onNext(result);
                        if (result.getCode() != 200) {
                            onError(new NetException(result.getCode(), result.getMsg()));
                        } else {
                            Object data = result.getData();
                            if (data instanceof PersonInfor) {
                                //个人数据

                                mode.savePersonInfo((PersonInfor) data);
                                PersonInfor myInfo = mode.getPersonInfo();
                                view.showPersonInfo(myInfo);
                                if ((!mode.isLogin() && InfoUtils.isShowAlert())
                                        || (mode.isLogin() && InfoUtils.isShowAlert() && InfoUtils.getRole(myInfo) != Role.UNKNOW)) {
                                    //跳转到实名认证
                                    view.showAttestationAlert(myInfo);
                                }
                            } else if (data instanceof PageBean) {
                                //订单数量数据
                                mode.saveOrderList(((PageBean) data).getList());
                                view.showOrderInfo(mode.getOrderList(), mode.getRoleOrder());
                            } else if (data instanceof OrderNumberInfoBean) {
                                //订单数量数据
                                view.showOrderNum((OrderNumberInfoBean) data);
                                if (!CollectionUtils.isEmpty(((OrderNumberInfoBean) data).getDriverTransOrderList())) {
                                    //有正在运输中的订单
                                    view.startLoaction(((OrderNumberInfoBean) data).getDriverTransOrderList().get(0));
                                } else {
                                    view.stopLocation();
                                }
                            }
                        }
                    }
                });


//        mode.queryOrderList();
//
//        Observable.zip(mode.queryMyInfo()
//                , mode.queryOrderNum()
//                , new BiFunction<CommonBean<PersonInfor>, CommonBean<OrderNumberInfoBean>, CommonBean<PersonInfor>>() {
//                    @Override
//                    public CommonBean<PersonInfor> apply(CommonBean<PersonInfor> personInforCommonBean, CommonBean<OrderNumberInfoBean> orderNumberInfoBeanCommonBean) throws Exception {
//                        mode.savePersonInfo(personInforCommonBean.getData());
//                        view.showPersonInfo(mode.getPersonInfo());
//                        view.showOrderNum(orderNumberInfoBeanCommonBean.getData());
//                        return personInforCommonBean;
//                    }
//                }
//
//        )
//                .doFinally(new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        view.showBalance(mode.isShowBalance(), mode.getBalanceTotle());
//                        //查询订单数据
//                    }
//                })
//            .subscribe(new NetObserver<PersonInfor>(listener))
//        ;


    }

    /**
     * 切换是否隐藏数据
     */
    @Override
    public void switchBalance() {
        mode.switchBalance(!mode.isShowBalance());
        view.showBalance(mode.isShowBalance(), mode.getBalanceTotle());
    }

    /**
     * 更新认证状态
     *
     * @param listener
     */
    @Override
    public void upDateState(TaskControl.OnTaskListener listener) {
        mode.queryMyInfo()
                .subscribe(new NetObserver<PersonInfor>(listener) {
                    @Override
                    public void doOnSuccess(PersonInfor personInfor) {
                        super.doOnSuccess(personInfor);
                        mode.savePersonInfo(personInfor);
                        view.showPersonInfo(mode.getPersonInfo());
                        view.showBalance(mode.isShowBalance(), mode.getBalanceTotle());
                    }
                });
    }

    /**
     * 查看认证信息
     */
    @Override
    public void checkStateInfo() {
        //跳转到查看信息
        view.jump2CheckState(mode.getRole(), mode.getPersonInfo());

    }

    /**
     * 点击更多
     */
    @Override
    public void clickMore() {
        view.clickMore(mode.getRoleOrder());
    }

    /**
     * 点击跳转到实名认证
     */
    @Override
    public void jump2Attestion() {
        PersonInfor personInfo = mode.getPersonInfo();
        if (personInfo == null) {
            return;
        }
        view.jump2Attestion(personInfo);
    }
}
