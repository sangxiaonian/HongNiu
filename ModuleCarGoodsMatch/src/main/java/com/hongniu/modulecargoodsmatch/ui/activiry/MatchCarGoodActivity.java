package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.control.RecordFragmentControl;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCarPreInforBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryGoodsInforParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.holder.MatchGoodsListHolder;
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
public class MatchCarGoodActivity extends BaseActivity implements View.OnClickListener, SwitchTextLayout.OnSwitchListener, OnPopuDismissListener, OrderMainPop.OnPopuClickListener {

    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;
    private Button btSave;
    private List<String> times;
    private OrderMainPop<String> popLeft;
    private OrderMainPop<String> popRight;
    private int leftSelection;
    private int rightSelection;
    private List<String> states;//车辆类型预加载信息
    private Fragment fragment;//列表
    private String time;//发车时间
    private String carType;//车辆类型

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

        fragment= (Fragment) ArouterUtils.getInstance().builder(ArouterParams.fragment_match_my_record).navigation();
        Bundle bundle=new Bundle();
        bundle.putInt(Param.TYPE,1);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment).commit();
    }

    @Override
    protected void initData() {
        super.initData();
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
            this.time = time;
        } else if (target.getId() == R.id.switch_right) {
            rightSelection = position;
            String s =  states.get(position);
            this.carType=position==0?null:states.get(position);
            switchRight.setTitle(s);
        }
        if (fragment instanceof RecordFragmentControl.OnSwitchFiltrateListener){
            ((RecordFragmentControl.OnSwitchFiltrateListener) fragment).onSwithFiltrate(time,carType);
        }
        pop.dismiss();
    }
    private void querPreloadInfor() {
        HttpMatchFactory
                .queryGoodCarInfor()
                .subscribe(new NetObserver<MatchCarPreInforBean>(this) {

                    @Override
                    public void doOnSuccess(MatchCarPreInforBean data) {
                        states=data.getCarType();
                        if (states!=null&&!states.contains("全部")){
                            states.add(0,"全部");
                        }
                        popRight.upDatas(states);
                    }
                })
        ;
    }
}
