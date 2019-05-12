package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItem;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/5/12
 * @Author PING
 * @Description 车货匹配列表页面
 */
@Route(path = ArouterParams.activity_match_car_good)
public class MatchCarGoodActivity extends RefrushActivity<GoodsOwnerInforBean> {

    private SwitchTextLayout switchLeft;
    private SwitchTextLayout switchRight;

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
                ToastUtils.getInstance().show("我的记录");
            }
        });
        switchLeft = findViewById(R.id.switch_left);
        switchRight = findViewById(R.id.switch_right);
    }

    @Override
    protected Observable<CommonBean<PageBean<GoodsOwnerInforBean>>> getListDatas() {
        CommonBean<PageBean<GoodsOwnerInforBean>> commonBean = new CommonBean<>();
        PageBean<GoodsOwnerInforBean> pageBean = new PageBean<>();
        commonBean.setCode(200);
        commonBean.setData(pageBean);
        List<GoodsOwnerInforBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new GoodsOwnerInforBean());
        }
        pageBean.setList(list);
        return Observable.just(commonBean);
    }

    @Override
    protected XAdapter<GoodsOwnerInforBean> getAdapter(List<GoodsOwnerInforBean> datas) {
        return new XAdapter<GoodsOwnerInforBean>(mContext, datas) {
            @Override
            public BaseHolder<GoodsOwnerInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<GoodsOwnerInforBean>(mContext, parent, R.layout.item_match_car_good) {
                    @Override
                    public void initView(View itemView, int position, GoodsOwnerInforBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
                        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
                        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
                        TextView bt_grabs = itemView.findViewById(R.id.bt_grabs);
                        TextView bt_contact = itemView.findViewById(R.id.bt_contact);

                        tvTitle.setText("罗超正在寻找小面包车");
                        tvTime.setText("需要发货时间：2019-07-10");
                        tv_start_point.setText("发货地：上海市普陀区中山北路208号");
                        tv_end_point.setText("收货地：上海市静安区广中路788号");
                        tv_goods.setText("货物名：医疗器材(2方、0.5吨)");
                        bt_grabs.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().show("我要抢单");
                                ArouterUtils.getInstance().builder(ArouterParams.activity_match_grap_single)
                                        .navigation(mContext);
                            }
                        });
                        bt_contact.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, "10", "测试");
                            }
                        });


                    }
                };
            }
        };
    }
}
