package com.hongniu.freight.control;

import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.entity.OrderInfoBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/2/7.
 */
public class MyOrderControl {
    public interface IMyOrderView{
        /**
         * 初始化标题
         * @param titles
         */
        void initTitles(List<Status> titles);
    }
    public interface IMyOrderPresenter{
        /**
         * 初始化数据
         * @param role
         */
        void initData(RoleOrder role);
        /**
         * 获取当前角色类型
         * @return
         */
        RoleOrder getType();


        /**
         * 查询当前订单
         * @param currentPage
         * @return
         */
        Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage);

        /**
         * 切换当前状态
         * @param position
         * @param selected
         */
        void switchStatus(int position, Status selected);
    }
    public interface IMyOrderMode{
        void saveRole(RoleOrder role);
        void saveStatus(Status position);

        /**
         * 获取当前角色类型
         * @return
         */
        RoleOrder getRole();

        /**
         * 获取头部选择标题
         * @return
         */
        List<Status> getTitles();


        /**
         * 查询当前订单
         * @param currentPage
         * @return
         */
        Observable<CommonBean<PageBean<OrderInfoBean>>> queryOrder(int currentPage);

    }
}
