package com.hongniu.modulelogin.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.transition.ChangeBounds;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.dialog.PayWaysDialog;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.entity.PayInforBeans;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.net.error.NetException;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.recycleview.touchhelper.DragSortHelper;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.CenteredImageSpan;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.wechat.WeChatAppPay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * 我的收款方式
 */
@Route(path = ArouterParamLogin.activity_pay_ways)
public class LoginPayWaysActivity extends BaseActivity implements DialogControl.OnEntryClickListener<String> {

    RecyclerView recyclerView;
    private ArrayList<PayInforBeans> datas;
    private XAdapter<PayInforBeans> adapter;
    private PayWaysDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pay_ways);
        setToolbarTitle(getString(R.string.login_pay_title));

        initView();
        initData();
        upData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        JLog.i("--------------------------");
        upData();
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

        adapter = new XAdapter<PayInforBeans>(mContext, datas) {
            @Override
            public BaseHolder<PayInforBeans> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<PayInforBeans>(mContext, parent, R.layout.login_item_pay_ways) {
                    @Override
                    public void initView(View rootView, int position, final PayInforBeans data) {
                        super.initView(rootView, position, data);

                        rootView.setBackgroundResource(data.getPayWays() == 1
                                ? R.drawable.shape_4_gradient_45b649_6fcc3c : R.drawable.shape_4_gradient_fe5163_fe8a71);
                        //微信背景
                        ImageView bgIcon = itemView.findViewById(R.id.img_icon_big);
                        ImageView imgDefault = itemView.findViewById(R.id.img_default);
                        //图标
                        ImageView icon = itemView.findViewById(R.id.src_icon_small);
                        //银行或者微信的方式
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        //微信号或者卡号
                        TextView tvNum = itemView.findViewById(R.id.tv_num);


                        bgIcon.setVisibility(data.getPayWays() == 1 ? View.VISIBLE : View.GONE);
                        imgDefault.setVisibility(data.getIsDefault() == 1 ? View.VISIBLE : View.GONE);
                        ImageLoader.getLoader().load(mContext, icon, data.getPayWays() == 1 ? R.mipmap.icon_skfs_wechat_40 : R.mipmap.app_logo);
                        tvTitle.setText(data.getBankName() == null ? "" : data.getBankName());
                        if (data.getPayWays() == 0) {//银联卡号
                            if (data.getCardNo() != null) {
                                if (data.getCardNo().length() >= 4) {
                                    tvNum.setText(data.getCardNo().substring(data.getCardNo().length() - 4));
                                } else {
                                    tvNum.setText(data.getCardNo());
                                }
                            } else {
                                tvNum.setText("");
                            }
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data.getIsDefault() != 1) {
                                    changeDefault(data.getId());
                                }
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

                        //一起版本只有银联卡一种方式，因此此处不做选择项
//                        dialog.show(v);

                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login_add_blank_card).navigation((Activity) mContext, 1);


                    }
                });

            }
        };
        adapter.addFoot(peakHolder);
        recyclerView.setAdapter(adapter);


    }

    /**
     * 更改默认支付方式
     */
    private void changeDefault(String id) {
        HttpLoginFactory.changeDefaultPayWay(id, 1)
                .map(new Function<CommonBean<String>, CommonBean<String>>() {
                    @Override
                    public CommonBean<String> apply(CommonBean<String> stringCommonBean) throws Exception {
                        if (stringCommonBean.getCode() == 200) {
                            return stringCommonBean;
                        } else {
                            throw new NetException(stringCommonBean.getCode(), stringCommonBean.getMsg());
                        }
                    }
                })
                .flatMap(new Function<CommonBean<String>, ObservableSource<CommonBean<List<PayInforBeans>>>>() {
                    @Override
                    public ObservableSource<CommonBean<List<PayInforBeans>>> apply(CommonBean<String> stringCommonBean) throws Exception {

                        return HttpLoginFactory.queryMyPayInforList();
                    }
                })
                .subscribe(new NetObserver<List<PayInforBeans>>(this) {
                    @Override
                    public void doOnSuccess(List<PayInforBeans> data) {
                        datas.clear();
                        if (data != null) {
                            datas.addAll(data);
                        }
                        adapter.notifyDataSetChanged();
                    }

                });
        ;
    }

    //更新数据，用于绑定微信和添加银行卡信息之后
    private void upData() {
        HttpLoginFactory.queryMyPayInforList()
                .subscribe(new NetObserver<List<PayInforBeans>>(this) {
                    @Override
                    public void doOnSuccess(List<PayInforBeans> data) {
                        datas.clear();
                        if (data != null) {
                            datas.addAll(data);
                        }
                        adapter.notifyDataSetChanged();
                    }

                });
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
                ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login_add_blank_card).navigation(this, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        upData();
    }
}
