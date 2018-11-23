package com.hongniu.moduleorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.hongniu.moduleorder.widget.OrderTimePop;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;
import com.sangxiaonian.xcalendar.entity.DateBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;

/**
 * 订单列表Fragment
 */
@Route(path = ArouterParamOrder.fragment_order_search)
public class OrderSearchFragmet extends OrderFragmet implements SwitchStateListener, SwitchTextLayout.OnSwitchListener, OrderMainPop.OnPopuClickListener, OnPopuDismissListener, OrderTimePop.OnCalenderListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private int rightSelection;
    private OrderMainPop<String> orderMainPop;
    private List<String> states;
    private OrderTimePop timePop;
    private String title;


    public OrderSearchFragmet() {
    }


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_list_fragmet, null);
        switchLeft = inflate.findViewById(R.id.switch_left);
        switchRight = inflate.findViewById(R.id.switch_right);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        orderMainPop = new OrderMainPop<>(getContext());
        timePop = new OrderTimePop(getContext());
        String[] stringArray = getResources().getStringArray(R.array.order_main_state);
        states = new ArrayList<>();
        for (String s : stringArray) {
            states.add(s);
        }

        if (roleState != CARGO_OWNER) {
            states.remove(1);
        }

        View view = new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(getContext(), 75));
        view.setLayoutParams(params);
        adapter.addFoot(new PeakHolder(view));
    }


//    @Override
//    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
//        queryBean.setPageNum(currentPage);
//        return HttpOrderFactory.queryOrder(queryBean);
//
//    }


    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        queryBean.setSearchText(title);
        return super.getListDatas();
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);

        title = args.getString(Param.TITLE);
        roleState = (OrderDetailItemControl.RoleState) args.get(Param.TRAN);
        if (roleState == null) {
            roleState = CARGO_OWNER;
        }
        switch (roleState) {
            case CARGO_OWNER:
                queryBean.setUserType(3);
                break;
            case CAR_OWNER:
                queryBean.setUserType(1);
                break;
            case DRIVER:
                queryBean.setUserType(2);
                break;
        }


    }


    @Override
    protected void initListener() {
        super.initListener();
        switchRight.setListener(this);
        switchLeft.setListener(this);
        orderMainPop.setOnDismissListener(this);
        orderMainPop.setListener(this);
        timePop.setListener(this);
        timePop.setOnDismissListener(this);

    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout, true);


    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout, false);
    }

    @Override
    public void onPopuClick(OrderMainPop pop, View view, int position) {
        rightSelection = position;
        switchRight.setTitle(states.get(position));
        queryBean.setQueryStatus(null);
        if (position == 0) {//全部状态
            queryBean.setQueryStatus(null);
        } else if (position == 1 && roleState == CARGO_OWNER) {//待支付状态
            queryBean.setQueryStatus("0");
        } else {
            if (roleState == CARGO_OWNER) {
                queryBean.setQueryStatus((position) + "");
            } else {
                queryBean.setQueryStatus((1 + position) + "");

            }
        }
        queryData(true, true);
        pop.dismiss();
    }


    private void changeState(View view, boolean open) {
        if (view.getId() == R.id.switch_left) {
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();

            if (open) {
                timePop.show(view);
                orderMainPop.dismiss();
            } else {
                timePop.dismiss();
            }

        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
            if (open) {
                orderMainPop.setSelectPosition(rightSelection);
                orderMainPop.upDatas(states);
                orderMainPop.show(view);
                timePop.dismiss();
            } else {
                orderMainPop.dismiss();

            }
        }
    }


    /**
     * Popu dimiss 监听
     *
     * @param popu   当前popu
     * @param target 目标View
     */
    @Override
    public void onPopuDismsss(BasePopu popu, View target) {
        switchLeft.closeSwitch();
        switchRight.closeSwitch();
    }

    @Override
    public boolean isShowing() {
        if (orderMainPop != null) {
            return orderMainPop.isShow();
        } else {
            return false;
        }
    }

    @Override
    public void closePop() {
        if (orderMainPop != null) {
            orderMainPop.dismiss();
        }
    }

    @Override
    public void setRoalState(OrderDetailItemControl.RoleState state) {
        this.roleState = state;
    }


    @Override
    public void onClickEntry(OrderTimePop pop, View target, DateBean start, DateBean end) {
        pop.dismiss();

        if (end == null && start == null) {
            return;
        } else if (start == null) {
            start = end;
        } else if (end == null) {
            end = start;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(start.getYear(), start.getMonth(), start.getDay());
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(end.getYear(), end.getMonth(), end.getDay());
        queryBean.setDeliveryDateStart(ConvertUtils.formatTime(startCalendar.getTime(), "yyyy-MM-dd"));
        queryBean.setDeliveryDateEnd(ConvertUtils.formatTime(endCalendar.getTime(), "yyyy-MM-dd"));
        queryData(true,true);
    }
}
