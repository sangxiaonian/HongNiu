package com.hongniu.moduleorder.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.baselibrary.widget.order.OrderUtils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 订单列表Fragment
 */
@Route(path = ArouterParamOrder.fragment_order_main)
public class OrderMainFragmet extends BaseFragment implements SwitchStateListener, SwitchTextLayout.OnSwitchListener, OrderMainPop.OnPopuClickListener, OnPopuDismissListener, OrderDetailItemControl.OnOrderDetailBtClickListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private RecyclerView rv;

    private int leftSelection;
    private int rightSelection;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;

    private OrderDetailItemControl.RoleState roleState;//角色
    private XAdapter<OrderDetailBean> adapter;


    public OrderMainFragmet() {
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        int anInt = args.getInt(Param.TRAN);
        switch (anInt) {
            case 0:
                roleState = OrderDetailItemControl.RoleState.CARGO_OWNER;
                break;
            case 1:
                roleState = OrderDetailItemControl.RoleState.CAR_OWNER;
                break;
            case 2:
                roleState = OrderDetailItemControl.RoleState.DRIVER;
                break;
        }


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
    protected void initData() {
        super.initData();
        orderMainPop = new OrderMainPop<>(getContext());
        states = Arrays.asList(getResources().getStringArray(R.array.order_main_state));
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        List<OrderDetailBean> data = new ArrayList<>();
        boolean insurance = new Random().nextBoolean();
        data.add(OrderUtils.creatBean(1, insurance));
        data.add(OrderUtils.creatBean(2, insurance));
        data.add(OrderUtils.creatBean(3, insurance));
        data.add(OrderUtils.creatBean(4, insurance));
        data.add(OrderUtils.creatBean(5, insurance));

        adapter = new XAdapter<OrderDetailBean>(getContext(), data) {
            @Override
            public BaseHolder<OrderDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderDetailBean>(getContext(), parent, R.layout.order_main_item) {
                    @Override
                    public void initView(View itemView, int position, OrderDetailBean data) {
                        super.initView(itemView, position, data);
                        OrderDetailItem item = itemView.findViewById(R.id.order_detail);//身份角色
                        item.setDebug();
                        item.setIdentity(roleState);
                        item.setEndLocation(data.getDestinationInfo());
                        item.setStartLocation(data.getStratPlaceInfo());
                        item.setOrder(data.getOrderNum());
                        item.setTiem(data.getDeliverydate());
                        item.setPrice(data.getMoney());

                        item.setOrderState(data.getOrderState());

                        item.setContent(data.getDepartNum(), data.getCarnum(), data.getUserName(), data.getUserPhone()
                                , data.getGoodName(), data.getDrivername(), data.getDrivermobile());

                        item.setOnButtonClickListener(OrderMainFragmet.this);
                        item.buildButton(data.isInsurance());

                    }
                };
            }
        };

        View view=new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(getContext(),75));
        view.setLayoutParams(params);
        adapter.addFoot(new PeakHolder(view));
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
    public void setRoalState(OrderDetailItemControl.RoleState state) {
        this.roleState = state;
    }


    /**
     * "取消订单";
     */
    @Override
    public void onOrderCancle() {
        creatDialog("确认要取消订单？", "订单一旦去取消，无法恢复", "返回订单", "取消订单")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("取消订单");
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
        ;
    }

    /**
     * 购买保险
     */
    @Override
    public void onOrderBuyInsurance() {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_pay).withBoolean(Param.TRAN, true).navigation(getContext());
    }

    /**
     * ORDER_PAY 继续付款
     */
    @Override
    public void onOrderPay() {
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_pay).navigation(getContext());

    }

    /**
     * ORDER_CHECK_INSURANCE 查看保单
     */
    @Override
    public void onCheckInsruance() {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看保单");
    }

    /**
     * ORDER_CHECK_PATH 查看轨迹
     */
    @Override
    public void onCheckPath() {


        PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_map_path).withBoolean(Param.TRAN,true).navigation(getContext());

            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });
    }

    /**
     * ORDER_ENTRY_ORDER 确认收货
     */
    @Override
    public void onEntryOrder() {
        creatDialog("确认已收到货物？", "收货请务必检查货物完好无损", "返回订单", "确定收货")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("确认收货");
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * ORDER_START_CAR           ="开始发车";
     */
    @Override
    public void onStartCar() {
        creatDialog("确认要开始发车？", "发车时请记得安全驾车哦", "返回订单", "开始发车")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("开始发车");
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    /**
     * ORDER_CHECK_ROUT          ="查看路线";
     */
    @Override
    public void onCheckRout() {
        PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_map_path).withBoolean(Param.TRAN,false).navigation(getContext());

            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {

            }
        });

    }

    /**
     * ORDER_ENTRY_ARRIVE        ="确认到达";
     */
    @Override
    public void onEntryArrive() {
        creatDialog("确认已到达目的地？", "感谢您的辛苦付出", "返回订单", "确定到达")
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("确认已到达目的地");
                    }
                }).creatDialog(new CenterAlertDialog(getContext()))
                .show();
    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String btRight) {
        return new CenterAlertBuilder()
                .setDialogTitle(title)
                .setDialogContent(content)
                .setBtLeft(btleft)
                .setBtRight(btRight)
                .setBtLeftColor(getResources().getColor(R.color.color_title_dark))
                .setBtRightColor(getResources().getColor(R.color.color_white))
                .setBtRightBgRes(R.drawable.shape_f06f28);
    }


}
