package com.hongniu.moduleorder.ui.fragment;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CAR_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.DRIVER;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.event.BusFactory;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.RoleTypeBean;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderMainControl;
import com.hongniu.moduleorder.control.SwitchStateListener;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 订单中心主页
 */
//@Route(path = ArouterParamsApp.activity_main)
@Route(path = ArouterParamOrder.fragment_order_main)
public class OrderHomeFragment extends BaseFragment implements OrderMainControl.IOrderMainView, SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener, View.OnClickListener, OrderMainTitlePop.OnBackClickListener {

    private SwitchTextLayout switchTitle;


    private OrderMainTitlePop titlePop;


    private Fragment cargoFragment, carOwnerFragmeng, driverFragmeng, currentFragmeng;
    private SwitchStateListener switchStateListener;


    private OrderDetailItemControl.RoleState roleState = CARGO_OWNER;
    private Context mContext;
    private int type = 3;
    private View back;
    private boolean showBack;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_order_main_fragmet, null, false);
        inflate.findViewById(R.id.img_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_search)
                        .withSerializable(Param.TRAN, roleState)
                        .navigation(getContext());
                ClickEventUtils.getInstance().onClick(ClickEventParams.订单_搜索);

            }
        });

        switchTitle = inflate.findViewById(R.id.switch_title);
        back = inflate.findViewById(R.id.toolbar_left);
        titlePop = new OrderMainTitlePop(getContext());
        back.setVisibility(showBack ? View.VISIBLE : View.GONE);
        return inflate;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeStaff(type);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }



    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
          type =   args.getInt(Param.TRAN,3);
          showBack =   args.getBoolean(Param.TYPE,false);


    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        switchTitle.setListener(this);
        titlePop.setListener(this);
        titlePop.setOnDismissListener(this);
        titlePop.setOnBackClickListener(this);

    }


    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }



//    //进入首页时候，根据获取到的数据切换当前角色
//    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(final RoleTypeBean event) {
//        if (event != null) {
//            changeStaff(event.getRoleId());//此处接收到用户类型
//
//        }
//        BusFactory.getBus().removeStickyEvent(RoleTypeBean.class);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }




    /**
     * 改变角色信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeRole(Event.UpRoale event) {
        if (event != null && event.roleState != null) {
            switch (event.roleState) {
                case CARGO_OWNER:
                    changeStaff(3);
                    break;
                case CAR_OWNER:
                    changeStaff(1);
                    break;
                case DRIVER:
                    changeStaff(2);
                    break;
            }

        }
    }

    /**
     * 顶部角色类型被选中点击的时候
     *
     * @param popu
     * @param position
     */
    @Override
    public void onMainClick(OrderMainTitlePop popu, int position) {
        popu.dismiss();
        changeStaff(position);
    }

    /**
     * 切换用户角色
     *
     * @param position
     */
    private void changeStaff(int position) {

        ClickEventUtils.getInstance().onClick(ClickEventParams.订单_切换角色);


        switch (position) {
            case 3:
                this.roleState = CARGO_OWNER;
                break;
            case 1:
                this.roleState = CAR_OWNER;
                break;
            case 2:
                this.roleState = DRIVER;
                break;
        }


        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        if (currentFragmeng != null) {
            fragmentTransaction.hide(currentFragmeng);
        }
        if (position == 1) {
            switchTitle.setTitle("我是车主");
            if (carOwnerFragmeng == null) {
                carOwnerFragmeng = new OrderMainFragmet();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Param.TRAN, roleState);
                carOwnerFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, carOwnerFragmeng);
            } else {
                fragmentTransaction.show(carOwnerFragmeng);
            }
            currentFragmeng = carOwnerFragmeng;
        } else if (position == 2) {
            switchTitle.setTitle("我是司机");

            if (driverFragmeng == null) {
                driverFragmeng = new OrderMainFragmet();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Param.TRAN, roleState);
                driverFragmeng.setArguments(bundle);
                fragmentTransaction.add(R.id.content, driverFragmeng);
            } else {
                fragmentTransaction.show(driverFragmeng);
            }
            currentFragmeng = driverFragmeng;
        } else {
            switchTitle.setTitle("我是货主");
            if (cargoFragment == null) {
                cargoFragment = new OrderMainFragmet();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Param.TRAN, roleState);
                cargoFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.content, cargoFragment);
            } else {
                fragmentTransaction.show(cargoFragment);
            }
            currentFragmeng = cargoFragment;
        }

        if (currentFragmeng != null && currentFragmeng instanceof SwitchStateListener) {
            switchStateListener = (SwitchStateListener) currentFragmeng;
        }

        fragmentTransaction.commitAllowingStateLoss();
    }


    public void onBackPressed() {
        if (titlePop.isShow()) {
            titlePop.dismiss();
        } else if (switchStateListener != null && switchStateListener.isShowing()) {
            switchStateListener.closePop();
        } else {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
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
        switchTitle.closeSwitch();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        if (switchStateListener != null) {
            switchStateListener.closePop();
        }
        titlePop.show(view);

    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        titlePop.dismiss();
    }


}
