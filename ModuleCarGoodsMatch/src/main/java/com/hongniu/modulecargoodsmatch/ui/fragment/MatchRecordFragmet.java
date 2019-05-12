package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushFragmet;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * 作者： ${PING} on 2019/5/12.
 */
@Route(path = ArouterParams.fragment_match_my_record)
public class MatchRecordFragmet extends RefrushFragmet<GoodsOwnerInforBean> {


    private int type;

    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_match_my_record,null,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        queryData(true);
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        if (args!=null) {
            type=args.getInt(Param.TRAN,0);
        }
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
        return new XAdapter<GoodsOwnerInforBean>(getContext(), datas) {
            @Override
            public BaseHolder<GoodsOwnerInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<GoodsOwnerInforBean>(getContext(), parent, R.layout.item_match_my_record) {
                    @Override
                    public void initView(View itemView, int position, GoodsOwnerInforBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_start_point = itemView.findViewById(R.id.tv_start_point);
                        TextView tv_end_point = itemView.findViewById(R.id.tv_end_point);
                        TextView tv_goods = itemView.findViewById(R.id.tv_goods);
                        TextView bt_left = itemView.findViewById(R.id.bt_left);
                        TextView bt_right = itemView.findViewById(R.id.bt_right);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv1 = itemView.findViewById(R.id.tv1);

                        if (type==0) {
                            bt_left.setText("删除发布");
                            bt_right.setText("抢单明细");
                        }else {
                            bt_left.setText("取消参与");
                            bt_right.setText("联系货主");
                        }

                        tv1.setVisibility(type==0?View.GONE:View.VISIBLE);
                        tv_price.setVisibility(type==0?View.GONE:View.VISIBLE);
                        tv_price.setText("￥3100");

                        tvTitle.setText("罗超正在寻找小面包车");
                        tvTime.setText("需要发货时间：2019-07-10");
                        tv_start_point.setText("发货地：上海市普陀区中山北路208号");
                        tv_end_point.setText("收货地：上海市静安区广中路788号");
                        tv_goods.setText("货物名：医疗器材(2方、0.5吨)");
                        bt_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (type==0) {
                                    CenterAlertBuilder builder = Utils.creatDialog(mContext, "确认删除车货匹配？", "发布一旦删除，无法恢复", "返回记录", "确定删除");
                                    builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                        @Override
                                        public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                            dialog.dismiss();
                                            ToastUtils.getInstance().show("删除发布");
                                            queryData(true, true);
                                        }
                                    })
                                            .creatDialog(new CenterAlertDialog(mContext))
                                            .show();
                                    ;

                                }else {
                                    CenterAlertBuilder builder = Utils.creatDialog(mContext, "确认取消参与抢单？", "参与一旦取消，无法恢复", "返回记录", "确定取消");
                                    builder.setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                        @Override
                                        public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                            dialog.dismiss();
                                            ToastUtils.getInstance().show("确定取消");
                                            queryData(true, true);
                                        }
                                    })
                                            .creatDialog(new CenterAlertDialog(mContext))
                                            .show();
                                    ;
                                }
                            }
                        });
                        bt_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (type==0) {
                                    ToastUtils.getInstance().show("抢单明细");

                                }else {
                                    ChactHelper.getHelper().startPriver(mContext, "10", "测试");
                                    ToastUtils.getInstance().show("联系货主");
                                }
                            }
                        });


                    }
                };
            }
        };
    }
}


