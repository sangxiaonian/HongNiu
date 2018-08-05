package com.hongniu.moduleorder;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.dialog.BottomDialog;
import com.sang.common.widget.dialog.CenterDialog;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单中心主页
 */
@Route(path = ArouterParamOrder.activity_order_main)
public class OrderMainActivity extends BaseActivity implements SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener, OrderMainPop.OnPopuClickListener, View.OnClickListener {

    private SwitchTextLayout switchTitle;
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private RecyclerView rv;
    private DrawerLayout drawerLayout;

    private OrderMainTitlePop titlePop;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;

    private LinearLayout llOrder;//我要下单
    private LinearLayout llLoginOut;//退出登录
    private LinearLayout llContactService;//联系客服
    private LinearLayout llAboutUs;//关于我们
    private LinearLayout llMyCar;//我的车辆
    private LinearLayout llPersonInfor;//个人资料
    private LinearLayout llPayMethod;//收款方式

    private ImageView srcFinance;
    private ImageView srcPersonCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_main);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        titlePop = new OrderMainTitlePop(this);
        orderMainPop = new OrderMainPop<>(this);
        switchTitle = findViewById(R.id.switch_title);
        switchLeft = findViewById(R.id.switch_left);
        switchRight = findViewById(R.id.switch_right);
        drawerLayout = findViewById(R.id.drawer);
        rv = findViewById(R.id.rv);
        srcFinance = findViewById(R.id.src_finance);
        srcPersonCenter = findViewById(R.id.src_me);
        llOrder = findViewById(R.id.ll_order);
        llLoginOut = findViewById(R.id.ll_login_out);
        llContactService = findViewById(R.id.ll_contact_service);
        llAboutUs = findViewById(R.id.ll_about_us);
        llMyCar = findViewById(R.id.ll_my_car);
        llPersonInfor = findViewById(R.id.ll_person_infor);
        llPayMethod = findViewById(R.id.ll_pay_method);


    }

    @Override
    protected void initData() {
        super.initData();
        states = Arrays.asList(getResources().getStringArray(R.array.order_main_state));
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("");
        }
        XAdapter<String> adapter = new XAdapter<String>(mContext, data) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(mContext, parent, R.layout.order_item) {
                    @Override
                    public void initView(View itemView, int position, String data) {
                        super.initView(itemView, position, data);
                        TextView tvIdentity = itemView.findViewById(R.id.tv_identity);//身份角色
                        TextView tv_order = itemView.findViewById(R.id.tv_order);//订单号
                        TextView tv_state = itemView.findViewById(R.id.tv_state);//待发车状态
                        TextView tv_time = itemView.findViewById(R.id.tv_time);//发车时间
                        TextView tv_start_loaction = itemView.findViewById(R.id.tv_start_loaction);//发车地点
                        TextView tv_end_loaction = itemView.findViewById(R.id.tv_end_loaction);//到达地点
                        TextView tv_price = itemView.findViewById(R.id.tv_price);//运费
                        TextView bt_left = itemView.findViewById(R.id.bt_left);//左侧按钮
                        TextView bt_right = itemView.findViewById(R.id.bt_right);//右侧按钮
                        TextView tv_order_detail = itemView.findViewById(R.id.tv_order_detail);//右侧按钮


                        tvIdentity.setText("货主");
                        tv_order.setText("80080018000");
                        tv_state.setText("运输中");
                        tv_time.setText("发车时间：2017-08-05");
                        tv_start_loaction.setText("上海虹桥机场国际物流中心");
                        tv_end_loaction.setText("青岛市国际物流中心");
                        tv_price.setText("1520");

                        StringBuffer buffer = new StringBuffer();
                        buffer
                                .append("发车编号：2831929482").append("\n")
                                .append("车牌号：沪A666888").append("\n")
                                .append("车主：李先生 17602150486").append("\n")
                                .append("货物：医疗器材").append("\n")
                                .append("司机：杨先生 13795244936");
                        tv_order_detail.setText(buffer.toString());


                    }
                };
            }
        };
        rv.setAdapter(adapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
        switchTitle.setListener(this);
        switchRight.setListener(this);
        switchLeft.setListener(this);
        srcFinance.setOnClickListener(this);
        srcPersonCenter.setOnClickListener(this);
        llOrder.setClickable(true);
        llLoginOut.setClickable(true);
        llContactService.setClickable(true);
        llAboutUs.setClickable(true);
        llMyCar.setClickable(true);
        llPersonInfor.setClickable(true);
        llPayMethod.setClickable(true);


        llOrder.setOnClickListener(this);
        llLoginOut.setOnClickListener(this);
        llContactService.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llMyCar.setOnClickListener(this);
        llPersonInfor.setOnClickListener(this);
        llPayMethod.setOnClickListener(this);

        titlePop.setListener(this);
        titlePop.setOnDismissListener(this);
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


    private void changeState(View view, boolean open) {
        if (view.getId() == R.id.switch_left) {
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();
            orderMainPop.setSelectPosition(-1);
            if (open) {
                orderMainPop.setSelectPosition(times.indexOf(switchLeft.getTitle()));
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

                orderMainPop.setSelectPosition(states.indexOf(switchRight.getTitle()));
                orderMainPop.upDatas(states);
                orderMainPop.show(view);
            } else {
                orderMainPop.dismiss();

            }
        } else {
            if (open) {
                orderMainPop.dismiss();
                titlePop.show(view);
            } else {
                titlePop.dismiss();
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
        switch (position) {
            case 0:
                switchTitle.setTitle("我是货主");
                break;
            case 1:
                switchTitle.setTitle("我是车主");
                break;
            case 2:
                switchTitle.setTitle("我是司机");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (titlePop.isShow()) {
            titlePop.dismiss();
        } else if (orderMainPop.isShow()) {
            orderMainPop.dismiss();
        } else {
            super.onBackPressed();
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
        switchTitle.closeSwitch();
    }

    @Override
    public void onPopuClick(OrderMainPop pop, View view, int position) {

        if (view.getId() == R.id.switch_left) {
            switchLeft.setTitle(times.get(position));
        } else if (view.getId() == R.id.switch_right) {
            switchRight.setTitle(states.get(position));

        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.src_finance) {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("财务");
        } else if (i == R.id.src_me) {
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("个人中心");
            drawerLayout.openDrawer(Gravity.START);
        } else if (i == R.id.ll_order) {
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("我要下单");
            ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_create).navigation(mContext);

        } else if (i == R.id.ll_login_out) {
//            ToastUtils.showTextToast("退出登录");
            drawerLayout.closeDrawer(Gravity.START);
            new BottomDialog.Builder()
                    .setDialogTitle(getString(R.string.login_out_entry))
                    .setTopClickListener(new BottomDialog.OnButtonTopClickListener() {
                        @Override
                        public void onTopClick(View view, Dialog dialog) {
                            dialog.dismiss();
                            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(mContext);
                            finish();
                        }
                    })
                    .setBottomClickListener(new BottomDialog.OnButtonBottomClickListener() {
                        @Override
                        public void onBottomClick(View view, Dialog dialog) {
                            dialog.dismiss();
                        }
                    }).creatDialog(mContext).show();




        } else if (i == R.id.ll_contact_service) {
//            ToastUtils.showTextToast("联系客服");
            drawerLayout.closeDrawer(Gravity.START);
            new CenterDialog.Builder()
                    .setDialogTitle(getString(R.string.login_contact_service))
                    .setDialogContent(getString(R.string.login_contact_phone))
                    .setLeftClickListener(new CenterDialog.OnButtonLeftClickListener() {
                        @Override
                        public void onLeftClick(View view, Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                    .setRightClickListener(new CenterDialog.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, Dialog dialog) {
                            dialog.dismiss();
                            CommonUtils.toDial(mContext,getString(R.string.hongniu_phone));

                        }
                    }).creatDialog(mContext).show();

        } else if (i == R.id.ll_about_us) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("关于我们");

            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_about_us).navigation(mContext);
        } else if (i == R.id.ll_my_car) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.showTextToast("我的车辆");
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_list).navigation(mContext);
        } else if (i == R.id.ll_person_infor) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("个人资料");
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(mContext);

        } else if (i == R.id.ll_pay_method) {
            drawerLayout.closeDrawer(Gravity.START);
//            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL) .show("收款方式");
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_pay_ways).navigation(mContext);

        }
    }
}
