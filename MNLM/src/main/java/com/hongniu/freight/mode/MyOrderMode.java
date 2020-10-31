package com.hongniu.freight.mode;

import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.control.MyOrderControl;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.net.HttpAppFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class MyOrderMode implements MyOrderControl.IMyOrderMode {

    private RoleOrder role;
    private List<Status> titles;
    private Status statusOrder;

    public MyOrderMode() {
        titles = new ArrayList<>(Arrays.asList(Status.values()));
    }

    @Override
    public void saveRole(RoleOrder role) {
        this.role=role;
        titles.clear();
        if (role==RoleOrder.CARRIER||role==RoleOrder.DRIVER){
            titles.add(Status.WAITE_DEPART_NO_INSURANCE);
            titles.add(Status.IN_TRANSIT);
            titles.add(Status.ARRIVE);
            titles.add(Status.RECEIVING);
        }else {
            titles.addAll(Arrays.asList(Status.values()));

        }
    }

    @Override
    public void saveStatus(Status position) {
        this.statusOrder =position;
    }

    /**
     * 获取当前角色类型
     *
     * @return
     */
    @Override
    public RoleOrder getRole() {
        return role;
    }

    /**
     * 获取头部选择标题
     *
     * @return
     */
    @Override
    public List<Status> getTitles() {

        return titles;
    }

    /**
     * 查询当前订单
     *
     * @param currentPage
     * @return
     */
    @Override
    public Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage) {
        QueryOrderListBean bean=new QueryOrderListBean();
        bean.setPageSize(Param.PAGE_SIZE);
        bean.setPageNum(currentPage);
        bean.setUserType(role.getType());
        if (statusOrder!=null&& statusOrder!=Status.All){
            bean.setStatus(statusOrder.getStatus()+"");
        }
       return HttpAppFactory.queryOrderList(bean);
    }
}
