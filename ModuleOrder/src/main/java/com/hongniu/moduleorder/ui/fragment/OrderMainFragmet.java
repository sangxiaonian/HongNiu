package com.hongniu.moduleorder.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.sang.common.widget.popu.OrderMainPop;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;

/**
 * 订单列表Fragment
 */
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
        View inflate = inflater.inflate(R.layout.fragment_order_list_fragmet, null);
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
            if (roleState != CARGO_OWNER) {
                if (s.equals("待支付")
                        ||s.equals("支付审核中")
                        ||s.equals("审核被拒")
                ){
                    continue;
                }
            }
            states.add(s);
        }


        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));

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
        }
        queryData(true, true);
        pop.dismiss();
    }


    private void changeState(View view, boolean open) {
        if (view.getId() == R.id.switch_left) {
            ClickEventUtils.getInstance().onClick(ClickEventParams.订单_发车时间);

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
            ClickEventUtils.getInstance().onClick(ClickEventParams.订单_全部状);

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
