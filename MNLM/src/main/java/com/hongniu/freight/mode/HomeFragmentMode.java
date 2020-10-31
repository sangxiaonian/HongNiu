package com.hongniu.freight.mode;

import android.text.TextUtils;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/6.
 */
public class HomeFragmentMode implements HomeControl.IHomeFragmentMode {

    private   Role role=Role.SHIPPER_COMPANY;
    private RoleOrder roleOrder;
    private PersonInfor personInfo;
    private boolean isShowBalance;
    private List<OrderInfoBean> list;
    private boolean isLogin;

    public HomeFragmentMode() {
        list=new ArrayList<>();
        personInfo = InfoUtils.getMyInfo();
        if (personInfo!=null){
            role=InfoUtils.getRole(personInfo);
        }else {
            role=InfoUtils.getRole(InfoUtils.getLoginInfo());
        }
        chagetRoleOrder();
    }

    private void chagetRoleOrder() {
        roleOrder=InfoUtils.chagetRoleOrder(role);
    }


    /**
     * 储存是否是从登录页面跳转过来的
     *
     * @param isLogin
     */
    @Override
    public void saveInfo(boolean isLogin) {
        this.isLogin=isLogin;
    }

    /**
     * @return true 登录
     */
    @Override
    public boolean isLogin() {
        return isLogin;
    }

    /**
     * @return 获取当前类型
     * 0 托运人
     * 1 承运人
     * 2 司机
     */
    @Override
    public Role getRole() {
        return role;
    }

    /**
     * 查询个信息
     *
     * @return
     */
    @Override
    public Observable<CommonBean<PersonInfor>> queryMyInfo() {
        return HttpAppFactory.queryMyInfo();
    }

    /**
     * 查询订单数量
     *
     * @return
     */
    @Override
    public Observable<CommonBean<OrderNumberInfoBean>> queryOrderNum() {
        return HttpAppFactory.queryOrderNumber();
    }

    /**
     * 储存个人信息
     *
     * @param data
     */
    @Override
    public void savePersonInfo(PersonInfor data) {
        this.personInfo = data==null?InfoUtils.getMyInfo():data;
        role=InfoUtils.getRole(personInfo);
        chagetRoleOrder();
    }



    @Override
    public PersonInfor getPersonInfo() {
        return personInfo==null?InfoUtils.getMyInfo():personInfo;
    }

    /**
     * 余额显示状态
     *
     * @return true 显示 false隐藏
     */
    @Override
    public boolean isShowBalance() {
        return isShowBalance;
    }

    /**
     * 切换余额显示状态
     *
     * @param balanceStatus
     * @return true 显示 false隐藏
     */
    @Override
    public void switchBalance(boolean balanceStatus) {
        this.isShowBalance=balanceStatus;
    }

    /**
     * 获取余额
     *
     * @return
     */
    @Override
    public String getBalanceTotle() {
       return TextUtils.isEmpty(getPersonInfo().getTotalBalance())?"0":getPersonInfo().getTotalBalance();
    }

    /**
     * 查询订单数量
     * @return
     */
    @Override
    public Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrderList() {
        if (getRoleOrder()==RoleOrder.CARRIER) {
            return HttpAppFactory
                    .queryOwnerOrderList(1, 3);
        }else {
            QueryOrderListBean bean=new QueryOrderListBean();
            bean.setPageSize(3);
            bean.setPageNum(1);
            bean.setUserType(roleOrder.getType());
            return HttpAppFactory
                    .queryOrderList(bean);
        }
    }

    @Override
    public RoleOrder getRoleOrder() {
        return roleOrder;
    }

    @Override
    public void saveOrderList(List list) {
        this.list.clear();
        if (!CollectionUtils.isEmpty(list)){
            this.list.addAll(list);
        }
    }

    @Override
    public List<OrderInfoBean> getOrderList() {
return list;
    }


}
