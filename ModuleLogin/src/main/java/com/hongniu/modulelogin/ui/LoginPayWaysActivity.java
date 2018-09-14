package com.hongniu.modulelogin.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.widget.dialog.PayWaysDialog;
import com.hongniu.modulelogin.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.recycleview.touchhelper.DragSortHelper;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.CenteredImageSpan;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 我的收款方式
 */
@Route(path = ArouterParamLogin.activity_pay_ways)
public class LoginPayWaysActivity extends BaseActivity implements DialogControl.OnEntryClickListener<String> {

    RecyclerView recyclerView;
    private ArrayList<String> datas;
    private XAdapter<String> adapter;
    private PayWaysDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pay_ways);
        setToolbarTitle(getString(R.string.login_pay_title));

        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);


        List<String> payWays = Arrays.asList(getResources().getStringArray(R.array.order_pay_way));
        dialog = new PayWaysDialog(mContext);
        dialog.builder()
                .setEntryClickListener(this)
                .setDatas(payWays);

    }

    @Override
    protected void initData() {
        super.initData();
        datas = new ArrayList<>();
//        datas.add("15515551555");
//        datas.add("15515551555");
//        datas.add("15515551555");
//        datas.add("15515551555");
//        datas.add("15515551555");
//        datas.add("15515551555");
//        datas.add("15515551555");
//        datas.add("15515551555");
        adapter = new XAdapter<String>(mContext, datas) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(mContext, parent, R.layout.login_item_pay_ways) {
                    @Override
                    public void initView(View rootView, int position, final String data) {
                        super.initView(rootView, position, data);
                        itemView.animate().scaleY(1).scaleY(1).start();
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        if (data.length() == 11) {
                            builder.append(data);
                            String tag = "\t\t\t";
                            builder.insert(3, tag);
                            builder.insert(3 + tag.length() + 4, tag);
                            String cellTag = " ";
                            int start = 4 + tag.length();
                            for (int i = 0; i < 3; i++) {
                                builder.insert(start, cellTag);
                                start += 1 + cellTag.length();
                            }
                            start = 3 + tag.length();
                            for (int i = 0; i < 4; i++) {
                                CenteredImageSpan imageSpan = new CenteredImageSpan(mContext, R.drawable.shape_circle_ffffff);
                                imageSpan.setSpanSize(DeviceUtils.dip2px(context, 8), DeviceUtils.dip2px(context, 8));
                                builder.setSpan(imageSpan, start, start + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                start += 1 + cellTag.length();
                            }
                        }

                        TextView tvPhone = itemView.findViewById(R.id.tv_phone);
                        tvPhone.setText(builder);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(data);
                            }
                        });

                    }
                };
            }


        };


        PeakHolder peakHolder = new PeakHolder(mContext, recyclerView, R.layout.login_item_foot_pay_ways) {
            @Override
            public void initView(int position) {
                super.initView(position);
                getItemView().setLongClickable(false);
                getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.show(v);


                    }
                });

            }
        };
        adapter.addFoot(peakHolder);


        DragSortHelper dragSortHelper = new DragSortHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(dragSortHelper);
        dragSortHelper.addIngoreItem(adapter.getItemCount() - 1);
        recyclerView.setAdapter(adapter);
        helper.attachToRecyclerView(recyclerView);

    }

    /**
     * 选择支付方式
     *
     * @param dialog
     * @param position
     * @param data
     */
    @Override
    public void onEntryClick(Dialog dialog, int position, String data) {
        dialog.dismiss();
        switch (position) {
            case 0://绑定微信
                WeChatAppPay.jumpToXia(mContext, Param.isDebug);
                break;
            case 1://绑定银联卡
                ArouterUtils.getInstance().builder( ArouterParamLogin.activity_login_add_blank_card).navigation(this);
                break;
        }
    }
}
