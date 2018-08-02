package com.hongniu.moduleorder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.moduleorder.widget.OrderMainPop;
import com.hongniu.moduleorder.widget.OrderMainTitlePop;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 订单中心主页
 */
@Route(path = ArouterParamOrder.activity_order_main)
public class OrderMainActivity extends BaseActivity implements SwitchTextLayout.OnSwitchListener, OrderMainTitlePop.OnOrderMainClickListener, OnPopuDismissListener, OrderMainPop.OnPopuClickListener {

    private SwitchTextLayout switchTitle;
    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private RecyclerView rv;

    private OrderMainTitlePop titlePop;
    private OrderMainPop<String> orderMainPop;
    private List<String> times;
    private List<String> states;


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
        rv=findViewById(R.id.rv);
    }

    @Override
    protected void initData() {
        super.initData();
        states = Arrays.asList(getResources().getStringArray(R.array.order_main_state));
        times = Arrays.asList(getResources().getStringArray(R.array.order_main_time));
        LinearLayoutManager manager =new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("");
        }
        XAdapter<String> adapter = new XAdapter<String>(mContext,data) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(mContext,parent,R.layout.order_item){
                    @Override
                    public void initView(View itemView, int position, String data) {
                        super.initView(itemView, position, data);
                        TextView tvIdentity         = itemView.findViewById(R.id.tv_identity);//身份角色
                        TextView tv_order           = itemView.findViewById(R.id.tv_order);//订单号
                        TextView tv_state           = itemView.findViewById(R.id.tv_state);//待发车状态
                        TextView tv_time            = itemView.findViewById(R.id.tv_time);//发车时间
                        TextView tv_start_loaction = itemView.findViewById(R.id.tv_start_loaction);//发车地点
                        TextView tv_end_loaction         = itemView.findViewById(R.id.tv_end_loaction);//到达地点
                        TextView tv_price           = itemView.findViewById(R.id.tv_price);//运费
                        TextView bt_left            = itemView.findViewById(R.id.bt_left);//左侧按钮
                        TextView bt_right           = itemView.findViewById(R.id.bt_right);//右侧按钮
                        TextView tv_order_detail           = itemView.findViewById(R.id.tv_order_detail);//右侧按钮


                        tvIdentity       .setText("货主");
                        tv_order         .setText("80080018000");
                        tv_state         .setText("运输中");
                        tv_time          .setText("发车时间：2017-08-05");
                        tv_start_loaction.setText("上海虹桥机场国际物流中心");
                        tv_end_loaction  .setText("青岛市国际物流中心");
                        tv_price         .setText("1520");

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
        titlePop.setListener(this);
        titlePop.setOnDismissListener(this);
        orderMainPop.setOnDismissListener(this);

        orderMainPop.setListener(this);
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout,true);


    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        changeState(switchTextLayout,false);
    }


    private void changeState(View view, boolean open) {
        if (view.getId() == R.id.switch_left) {
            switchLeft.setSelect(true);
            switchRight.setSelect(false);
            switchRight.closeSwitch();
            orderMainPop.setSelectPosition(-1);
            if (open){
                orderMainPop.setSelectPosition(times.indexOf(switchLeft.getTitle()));
                orderMainPop.upDatas(times);
                orderMainPop.show(view);
            }else {
                orderMainPop.dismiss();
            }

        } else if (view.getId() == R.id.switch_right) {
            switchRight.setSelect(true);
            switchLeft.setSelect(false);
            switchLeft.closeSwitch();
            if (open){

                orderMainPop.setSelectPosition(states.indexOf(switchRight.getTitle()));
                orderMainPop.upDatas(states);
                orderMainPop.show(view);
            }else {
                orderMainPop.dismiss();

            }
        }else {
            if (open) {
                orderMainPop.dismiss();
                titlePop.show(view);
            }else {
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
        ToastUtils.showTextToast(position+"");
        if (view.getId() == R.id.switch_left) {
             switchLeft.setTitle(times.get(position));
        } else if (view.getId() == R.id.switch_right) {
            switchRight.setTitle(states.get(position));

        }
    }
}
