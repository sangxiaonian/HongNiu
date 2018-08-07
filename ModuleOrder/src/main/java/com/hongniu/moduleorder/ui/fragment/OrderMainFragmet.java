package com.hongniu.moduleorder.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.baselibrary.widget.order.RoleState;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.JLog;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单列表Fragment
 */
@Route(path = ArouterParamOrder.fragment_order_main)
public class OrderMainFragmet extends BaseFragment implements SwitchStateListener,SwitchTextLayout.OnSwitchListener, OrderMainPop.OnPopuClickListener, OnPopuDismissListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private RecyclerView rv;

    private int leftSelection;
    private int rightSelection;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;

    private RoleState roleState;//角色
    private XAdapter<String> adapter;


    public OrderMainFragmet() {
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        int anInt = args.getInt(Param.TRAN);
        switch (anInt){
            case 0:
                roleState=RoleState.CARGO_OWNER;
                break;
            case 1:
                roleState=RoleState.CAR_OWNER;
            case 2:
                roleState=RoleState.DRIVER;
                break;
        }
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        JLog.i(anInt+">>>>>>>"+this);

    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_main_fragmet, null);
        switchLeft = inflate.findViewById(R.id.switch_left);
        switchRight = inflate.findViewById(R.id.switch_right);
        rv = inflate.findViewById(R.id.rv);

        return inflate;
    }

    @Override
    protected void initDataa() {
        super.initDataa();
        orderMainPop = new OrderMainPop<>(getContext());
        states = Arrays.asList(getResources().getStringArray(R.array.order_main_state));
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("");
        }
       adapter = new XAdapter<String>(getContext(), data) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(getContext(), parent, R.layout.order_main_item) {
                    @Override
                    public void initView(View itemView, int position, String data) {
                        super.initView(itemView, position, data);
                        OrderDetailItem item = itemView.findViewById(R.id.order_detail);//身份角色
                        item.setDebug();
                        item.setIdentity(roleState);

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_pay).navigation(mContext);
                            }
                        });
                    }
                };
            }
        };
        rv.setAdapter(adapter);
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

        if (view.getId() == R.id.switch_left) {
            leftSelection = position;
            switchLeft.setTitle(times.get(position));
        } else if (view.getId() == R.id.switch_right) {
            rightSelection = position;
            switchRight.setTitle(states.get(position));

        }
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
        return orderMainPop.isShow();
    }

    @Override
    public void closePop() {
        orderMainPop.dismiss();
    }

    @Override
    public void setRoalState(RoleState state) {
        this.roleState=state;
    }


}
