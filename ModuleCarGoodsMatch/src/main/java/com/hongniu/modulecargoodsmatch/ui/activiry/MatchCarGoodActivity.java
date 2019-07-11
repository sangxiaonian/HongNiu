package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCarPreInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
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
    private OrderMainPop<String> popLeft;
    private OrderMainPop<String> popRight;
    private int leftSelection;
    private int rightSelection;
    private MatchQueryGoodsInforParams params;
    private List<String> states;//车辆长度预加载信息
    private String id;//自己的id，用于判断订单是否是自己创建的订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_car_good);
        initView();
        initData();
        initListener();
        querPreloadInfor();
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
        LoginBean loginInfor = Utils.getLoginInfor();
          id =loginInfor==null?"": loginInfor.getId();
        params = new MatchQueryGoodsInforParams(currentPage);
        popLeft = new OrderMainPop<>(mContext);
        popRight = new OrderMainPop<>(mContext);
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSave.setOnClickListener(this);
        switchRight.setListener(this);
        switchLeft.setListener(this);
        popLeft.setOnDismissListener(this);
        popLeft.setListener(this);
        popRight.setOnDismissListener(this);
        popRight.setListener(this);
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
                        TextView tv_remark = itemView.findViewById(R.id.tv_remark);
                        TextView tv1 = itemView.findViewById(R.id.tv1);
                        TextView tv_state = itemView.findViewById(R.id.tv_state);
                        tv1.setVisibility(View.GONE);
                        tv_state.setVisibility(View.VISIBLE);
                        tv_price.setVisibility(View.GONE);
                        String statusName = "";
                        switch (data.status) {
                            case 0:
                                statusName = "待接单";
                                break;
                            case 1:
                                statusName = "待确认";
                                break;
                            case 2:
                                statusName = "已下单";
                                break;
                            case 3:
                                statusName = "运输中";
                                break;
                            case 4:
                                statusName = "已完成";
                                break;
                            case 5:
                                statusName = "已失效";
                                break;
                        }
                        tv_state.setText(statusName);

                        tvTitle.setText(String.format("%s正在寻找%s（%s米）", data.userName==null?"":data.userName, data.carType==null?"车辆":data.carType, data.carLength==null?"0":data.carLength));
                        tvTime.setText(String.format("需要发货时间：%s", data.startTime));
                        tv_start_point.setText(String.format("发货地：%s", data.startPlaceInfo));
                        tv_end_point.setText(String.format("收货地：%s", data.destinationInfo));
                        tv_goods.setText(String.format("货物名：%s", data.goodsSourceDetail));
                        tv_remark.setVisibility(TextUtils.isEmpty(data.remark)?View.GONE:View.VISIBLE);
                        tv_remark.setText(String.format("货主备注：%s", data.remark==null?"":data.remark));
                        bt_left.setText("联系货主");
                        bt_right.setText("我要接单");


                        JLog.i(id+">>>>>>"+data.id+">>>"+data.userId);
                        bt_left.setVisibility((data.status==0||data.status==1)?View.VISIBLE:View.GONE);
                        bt_right.setVisibility((!id.equals(data.userId)&&(data.status==0||data.status==1))?View.VISIBLE:View.GONE);
                        bt_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, data.userId, data.userName);
                            }
                        });
                        bt_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParams.activity_match_grap_single)
                                        .withString(Param.TITLE, data.userName)
                                        .withString(Param.CAR_TYPE, data.carType)
                                        .withString(Param.TRAN, data.id)
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
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();
            if (open) {
                popLeft.setSelectPosition(leftSelection);
                popLeft.upDatas(times);
                popLeft.show(view);
            } else {
                popLeft.dismiss();
            }
            popRight.dismiss();
        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
            if (open) {
                if (states==null){
                    querPreloadInfor();
                }else {
                    popRight.setSelectPosition(rightSelection);
                    popRight.upDatas(states);
                    popRight.show(view);
                }
            } else {
                popRight.dismiss();
            }
            popLeft.dismiss();
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
            params.deliveryDateType = time;
        } else if (target.getId() == R.id.switch_right) {
            rightSelection = position;
            String s = states.get(position);
            params.carType=s;
            switchRight.setTitle(s);
        }
        queryData(true, true);
        pop.dismiss();
    }
    private void querPreloadInfor() {
        HttpMatchFactory
                .queryGoodCarInfor()
                .subscribe(new NetObserver<MatchCarPreInforBean>(this) {

                    @Override
                    public void doOnSuccess(MatchCarPreInforBean data) {
                        states=data.getCarType();
                        popRight.upDatas(states);
                    }
                })
        ;
    }
}
