package com.hongniu.moduleorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.model.LatLng;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CAR_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.DRIVER;

/**
 * 订单列表Fragment
 */
@Route(path = ArouterParamOrder.fragment_order_main)
public class OrderMainFragmet extends OrderFragmet implements SwitchStateListener, SwitchTextLayout.OnSwitchListener, OrderMainPop.OnPopuClickListener, OnPopuDismissListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private int leftSelection;
    private int rightSelection;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;


    public OrderMainFragmet() {
    }


    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_main_fragmet, null);
        switchLeft = inflate.findViewById(R.id.switch_left);
        switchRight = inflate.findViewById(R.id.switch_right);


        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        orderMainPop = new OrderMainPop<>(getContext());
        String[] stringArray = getResources().getStringArray(R.array.order_main_state);
        states = new ArrayList<>();
        for (String s : stringArray) {
            states.add(s);
        }

        if (roleState != CARGO_OWNER) {
            states.remove(1);
        }

        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));
        View view = new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(getContext(), 75));
        view.setLayoutParams(params);
        adapter.addFoot(new PeakHolder(view));
    }



    @Override
    protected void initListener() {
        super.initListener();
        switchRight.setListener(this);
        switchLeft.setListener(this);
        orderMainPop.setOnDismissListener(this);
        orderMainPop.setListener(this);

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

        if (view.getId() == R.id.switch_left) {//时间
            leftSelection = position;
            switchLeft.setTitle(position == 0 ? getString(R.string.order_main_start_time) : times.get(position));
            String time = null;
            switch (position) {
                case 0://全部
                    break;
                case 1://今天
                    time = "today";

                    break;
                case 2://明天
                    time = "tomorrow";
                    break;
                case 3://本周
                    time = "thisweek";
                    break;
                case 4://下周
                    time = "nextweek";
                    break;
            }
            queryBean.setDeliveryDateType(time);
        } else if (view.getId() == R.id.switch_right) {
            rightSelection = position;
            switchRight.setTitle(states.get(position));
            queryBean.setQueryStatus(null);
            queryBean.setHasFreight(null);
            if (position == 0) {//全部状态
                queryBean.setQueryStatus(null);
            } else if (position == 1 && roleState == CARGO_OWNER) {//待支付状态
                queryBean.setQueryStatus(null);
                queryBean.setHasFreight(false);
            } else {
                queryBean.setHasFreight(true);
                if (roleState == CARGO_OWNER) {
                    queryBean.setQueryStatus((position) + "");
                } else {
                    queryBean.setQueryStatus((1 + position) + "");

                }
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
                orderMainPop.setSelectPosition(leftSelection);
                orderMainPop.upDatas(times);
                orderMainPop.show(view);
            } else {
                orderMainPop.dismiss();
            }

        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
            if (open) {

                orderMainPop.setSelectPosition(rightSelection);
                orderMainPop.upDatas(states);
                orderMainPop.show(view);
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


}
