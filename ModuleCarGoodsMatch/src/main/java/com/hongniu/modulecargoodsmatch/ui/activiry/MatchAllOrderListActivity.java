package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.control.RecordFragmentControl;
import com.hongniu.modulecargoodsmatch.entity.MatchCarPreInforBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.fragment.MatchRecordFragmet;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.OrderMainPop;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @data 2019/12/22
 * @Author PING
 * @Description 车货匹配首页，全部信息列表
 */
@Route(path = ArouterParamsMatch.activity_match_all_order_list)
public class MatchAllOrderListActivity extends BaseActivity implements SwitchTextLayout.OnSwitchListener, OnPopuDismissListener, OrderMainPop.OnPopuClickListener, View.OnClickListener {
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;

    private String time;
    private String carType;

    RecordFragmentControl.OnSwitchFiltrateListener switchFiltrateListener;
    private int leftSelection;
    private int rightSelection;
    private View bt_sum;
    private MatchCarPreInforBean preCarInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_all_order_list);
        setToolbarTitle("车货匹配");
        setToolbarSrcRight("我的订单");
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_my_record)
                        .navigation(mContext);
            }
        });
        initView();
        initData();
        initListener();
        queryCarType();
    }

    @Override
    protected void initView() {
        super.initView();
        switchLeft = findViewById(R.id.switch_left);
        switchRight = findViewById(R.id.switch_right);
        bt_sum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();
        orderMainPop = new OrderMainPop<>(mContext);
        String[] stringArray = getResources().getStringArray(R.array.order_main_state);
        states = new ArrayList<>();
        states.addAll(Arrays.asList(stringArray));
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));

        MatchRecordFragmet matchRecordFragmet = new MatchRecordFragmet();
        Bundle bundle = new Bundle();
        bundle.putInt(Param.TYPE, 1);
        matchRecordFragmet.setArguments(bundle);
        switchFiltrateListener = matchRecordFragmet;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, matchRecordFragmet)
                .commit()
        ;

    }


    @Override
    public void initListener() {
        super.initListener();
        switchRight.setListener(this);
        switchLeft.setListener(this);
        orderMainPop.setOnDismissListener(this);
        orderMainPop.setListener(this);
        bt_sum.setOnClickListener(this);

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
            switch (position) {
                case 0://全部
                    time=null;
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
            switchFiltrateListener.onSwithFiltrate(time, carType);
        } else if (view.getId() == R.id.switch_right) {
            rightSelection = position;
            carType = states.get(position);
            switchRight.setTitle(carType);
            switchFiltrateListener.onSwithFiltrate(time, carType);
        }
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
                if (states.size() == 0) {
                    queryCarType();
                }
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_car_goods_crate_order)
                    .navigation(mContext);
        }
    }

//    @Override
//    public boolean isShowing() {
//        if (orderMainPop != null) {
//            return orderMainPop.isShow();
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public void closePop() {
//        if (orderMainPop != null) {
//            orderMainPop.dismiss();
//        }
//    }


    public void queryCarType() {
        HttpMatchFactory
                .queryGoodCarInfor()
                .subscribe(new NetObserver<MatchCarPreInforBean>(this) {

                    @Override
                    public void doOnSuccess(MatchCarPreInforBean data) {
                        preCarInfor = data;
                        states.clear();
                        List<String> carType = data.getCarType();
                        if (carType != null) {
                            states.addAll(carType);
                        }
                    }
                });
    }


    ;
}
