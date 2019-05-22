package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.OrderMainPop;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/5/12
 * @Author PING
 * @Description 车货匹配列表页面
 */
@Route(path = ArouterParams.activity_match_car_good)
public class MatchCarGoodActivity extends RefrushActivity<GoodsOwnerInforBean> implements View.OnClickListener, SwitchTextLayout.OnSwitchListener, OnPopuDismissListener, OrderMainPop.OnPopuClickListener {

    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private Button btSave;
    private List<String> times;
    private OrderMainPop<String> orderMainPop;
    private ArrayList<String> states;
    private int leftSelection;
    private int rightSelection;
    private MatchQueryGoodsInforParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_car_good);
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        setToolbarTitle("车货匹配");
        setToolbarSrcRight("我的记录");
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParams.activity_match_my_record)
                        .navigation(mContext);
            }
        });
        switchLeft = findViewById(R.id.switch_left);
        switchRight = findViewById(R.id.switch_right);
        btSave = findViewById(R.id.bt_save);
    }

    @Override
    protected void initData() {
        super.initData();
        params = new MatchQueryGoodsInforParams(currentPage);
        orderMainPop = new OrderMainPop<>(mContext);
        String[] stringArray = getResources().getStringArray(R.array.order_main_state);
        states = new ArrayList<>();
        states.addAll(Arrays.asList(stringArray));
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSave.setOnClickListener(this);
        switchRight.setListener(this);
        switchLeft.setListener(this);
        orderMainPop.setOnDismissListener(this);
        orderMainPop.setListener(this);
    }

    @Override
    protected Observable<CommonBean<PageBean<GoodsOwnerInforBean>>> getListDatas() {
        params.queryType = 1;
        params.pageNum = currentPage;
        return HttpMatchFactory.queryMatchGoodsInfor(params);
    }
    @Override
    public void onStart() {
        super.onStart();
        queryData(true);
    }

    @Override
    protected XAdapter<GoodsOwnerInforBean> getAdapter(List<GoodsOwnerInforBean> datas) {
        return new XAdapter<GoodsOwnerInforBean>(mContext, datas) {
            @Override
            public BaseHolder<GoodsOwnerInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<GoodsOwnerInforBean>(mContext, parent, R.layout.item_match_my_record) {
                    @Override
                    public void initView(View itemView, int position, final GoodsOwnerInforBean data) {
                        super.initView(itemView, position, data);
                        TextView bt_left = itemView.findViewById(R.id.bt_left);
                        TextView bt_right = itemView.findViewById(R.id.bt_right);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
                        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
                        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv1 = itemView.findViewById(R.id.tv1);

                        tv1.setVisibility(View.GONE);
                        tv_price.setVisibility(View.GONE);

                        tvTitle.setText(data.userName + "正在寻找" + data.carType);
                        tvTime.setText("需要发货时间：" + data.startTime);
                        tv_start_point.setText("发货地：" + data.startPlaceInfo);
                        tv_end_point.setText("收货地：" + data.destinationInfo);
                        tv_goods.setText("货物名：" + data.goodName);
                        bt_right.setText("联系货主");
                        bt_left.setText("我要抢单");

                        bt_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, data.userId, data.userName);
                            }
                        });
                        bt_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParams.activity_match_grap_single)
                                        .withString(Param.TITLE,data.userName)
                                        .withString(Param.TRAN,data.id)
                                        .navigation(mContext);
                            }
                        });


                    }
                };
            }
        };
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_save) {
            ArouterUtils.getInstance().builder(ArouterParams.activity_match_creat_order)
                    .navigation(mContext);
        }
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
    public void onPopuClick(OrderMainPop pop, View target, int position) {
        if (target.getId() == R.id.switch_left) {//时间
            leftSelection = position;
            switchLeft.setTitle(position == 0 ? getString(R.string.order_main_start_time) : times.get(position));
            params.deliveryDateType = times.get(position);
        } else if (target.getId() == R.id.switch_right) {
            rightSelection = position;
            switchRight.setTitle(states.get(position));
            params.carTypeId = states.get(position);
        }
        queryData(true, true);
        pop.dismiss();
    }
}
