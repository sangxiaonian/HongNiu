package com.hongniu.freight.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.JLog;
import com.hongniu.freight.R;
import com.hongniu.freight.config.PayType;
import com.hongniu.freight.entity.AccountDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2020/3/5.
 * 支付方式选择
 */
public class PayWayView extends LinearLayout {

    List<PayWayInfo> payWayInfos;
    private AccountDetailBean accountInfo;//账户信息
    PayWayChangeListener payWayChangeListener;
    private float payCount;//需要支付的金额

    public PayWayView(Context context) {
        this(context, null);
    }

    public PayWayView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PayWayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        setBackgroundResource(R.drawable.ovl_5_ffffff);
        payWayInfos = new ArrayList<>();
    }

    public void setPayWayChangeListener(PayWayChangeListener payWayChangeListener) {
        this.payWayChangeListener = payWayChangeListener;
    }

    private void addView(PayWayInfo info) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_pay_way, this, false);
        inflate.setTag(info);
        inflate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PayWayInfo selectInfo = refrushCheck(((PayWayInfo) v.getTag()).type);
                if (selectInfo != null && payWayChangeListener != null) {
                    payWayChangeListener.onPayChange(selectInfo.type);
                }

            }
        });
        addView(inflate);
        refrushView(inflate);
    }

    //刷新选中支付方式
    private PayWayInfo refrushCheck(PayType type) {
        PayWayInfo result = null;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            PayWayInfo tag = (PayWayInfo) child.getTag();
            boolean change = (type == tag.type);
            if (tag.check != change) {
                tag.check = change;
                refrushView(child);
                if (tag.check) {
                    result = tag;
                }
            }
        }
        if (type == PayType.BALANCE) {
            //余额支付
            if (getBalancePerson() < payCount) {
                //如果个人余额不足,选中微信支付
                result = refrushCheck(PayType.WEICHAT);
                ToastUtils.getInstance().show("余额不足");
            }
        }

        if (type == PayType.COMPANY) {
            //企业支付
            if (accountInfo.getType() == 3 && accountInfo.getCompanyAvailableBalance() < payCount) {
                //如果是企业需要支付密码支付,并且余额不足,选中微信支付
                result = refrushCheck(PayType.WEICHAT);
                ToastUtils.getInstance().show("余额不足");
            }
        }

        return result;
    }


    /**
     * 设置账户信息
     *
     * @param data     账户信息
     * @param payCount 需要支付的金额
     */
    public void setAccountInfo(AccountDetailBean data, float payCount) {
        this.accountInfo = data;
        this.payCount = payCount;
        payWayInfos.clear();
        removeAllViews();
        payWayInfos.add(new PayWayInfo(PayType.BALANCE, R.mipmap.icon_gr_40, "个人余额", "可用余额" + ConvertUtils.changeFloat(getBalancePerson(), 2), false));
        if (data.getType() == 2) {
            payWayInfos.add(new PayWayInfo(PayType.COMPANY_APPLY, R.mipmap.icon_qy_40, "企业余额", "需提交申请企业账号支付", false));
        } else if (data.getType() == 3) {
            payWayInfos.add(new PayWayInfo(PayType.COMPANY, R.mipmap.icon_qy_40, "企业余额", "可用余额" + ConvertUtils.changeFloat(data.getCompanyAvailableBalance(), 2), false));
        }
        payWayInfos.add(new PayWayInfo(PayType.WEICHAT, R.mipmap.icon_wx_40, "微信支付", "微信安全支付", false));
        payWayInfos.add(new PayWayInfo(PayType.ALIPAY, R.mipmap.icon_zfb_40, "支付宝", "不可使用花呗", false));
//        payWayInfos.add(new PayWayInfo(PayType.UNIONPAY, R.mipmap.icon_ylzf_40, "银联支付", "中国银联在线支付服务", false));
        for (PayWayInfo payWayInfo : payWayInfos) {
            addView(payWayInfo);
        }

    }


    /**
     * 设置当前默认选中支付方式
     *
     * @param type type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
     */
    public void setCurrentPayType(PayType type) {
        refrushCheck(type);
    }

    public PayType getPayWay() {
        PayType type = null;
        for (PayWayInfo payWayInfo : payWayInfos) {
            if (payWayInfo.check) {
                type = payWayInfo.type;
                break;
            }
        }
        return type;
    }


    private double getBalancePerson() {
        check();
        return accountInfo == null ? 0 : accountInfo.getAvailableBalance();
    }


    private void check() {
        if (accountInfo == null) {
            JLog.e("尚未设置账户信息,无法获取个人余额等数据");
        }
    }

    private void refrushView(View child) {
        PayWayInfo tag = (PayWayInfo) child.getTag();
        ImageView imgLogo = child.findViewById(R.id.img_logo);
        ImageView img_check = child.findViewById(R.id.img_check);
        TextView tv_title = child.findViewById(R.id.tv_title);
        TextView tv_des = child.findViewById(R.id.tv_des);
        imgLogo.setImageResource(tag.logoId);
        img_check.setImageResource(tag.check ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        tv_title.setText(tag.title);
        tv_des.setText(tag.des);
    }


    public class PayWayInfo {
        private PayType type;
        private int logoId;
        private String title;
        private String des;
        private boolean check;

        public PayWayInfo(PayType type, int logoId, String title, String des, boolean check) {
            this.type = type;
            this.logoId = logoId;
            this.title = title;
            this.des = des;
            this.check = check;
        }
    }

    public interface PayWayChangeListener {
        /**
         * 支付方式发生改变
         *
         * @param type 0 余额支付 1企业支付 2微信支付 3支付宝 4银联
         */
        void onPayChange(PayType type);
    }

}
