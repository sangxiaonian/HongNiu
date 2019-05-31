package com.hongniu.baselibrary.widget.pay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hongniu.baselibrary.R;

/**
 * 作者： ${PING} on 2019/5/21.
 */
public class PayWayView extends FrameLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ViewGroup rlWechact;//微信支付
    private ImageView cbWechat;//选择是否微信支付
    private ViewGroup rlAli;//支付宝
    private ImageView cbAli;//选择是否支付宝支付
    private ViewGroup rlUnion;//银联支付
    private ImageView cbUnion;//选择是银联支付
    private ViewGroup rlYue;//余额支付
    private ImageView cbYue;//选择余额支付
    private RadioGroup rg1;//余额支付方式
    private RadioButton rbCompany;//企业支付
    private RadioButton rbPerson;//个人支付
    private int payType;//支付方式
    private int yueType=1;
    private OnPayTypeChangeListener changeListener;
    private boolean showCompany;

    public PayWayView(Context context) {
        this(context, null);
    }

    public PayWayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public PayWayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View inflate = LayoutInflater.from(context).inflate(R.layout.pay_way, this, false);
        addView(inflate);
        rlAli = inflate.findViewById(R.id.rl_ali);
        cbAli = inflate.findViewById(R.id.ali_box);
        rlUnion = inflate.findViewById(R.id.rl_union);
        cbUnion = inflate.findViewById(R.id.union_box);
        rlYue = inflate.findViewById(R.id.rl_yue);
        cbYue = inflate.findViewById(R.id.cb_yue);
        rlWechact = inflate.findViewById(R.id.rl_wechact);
        cbWechat = inflate.findViewById(R.id.checkbox);
        rg1 = inflate.findViewById(R.id.rg1);
        rbCompany = inflate.findViewById(R.id.rb_company);
        rbPerson = inflate.findViewById(R.id.rb_person);

        if (showCompany){

        }

        rlAli.setOnClickListener(this);
        rlUnion.setOnClickListener(this);
        rlYue.setOnClickListener(this);
        rlWechact.setOnClickListener(this);
        rg1.setOnCheckedChangeListener(this);

    }

    public void setChangeListener(OnPayTypeChangeListener changeListener) {
        this.changeListener = changeListener;
    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rl_wechact) {//选择微信支付
            changePayType(2);
        } else if (i == R.id.rl_ali) {//选择支付宝
            changePayType(3);
        } else if (i == R.id.rl_union) {//选择银联
            changePayType(4);
        } else if (i == R.id.rl_yue) {//余额支付
            changePayType(1);
        }
    }

    /**
     * 更改当前支付方法
     * @param payType 1 余额 2微信 3支付宝 4银联
     */
    public void setPayType(int payType) {
        changePayType(payType);
    }  /**
     * 更改当前支付方法
     * @param yueType  0 企业支付 1余额支付
     */
    public void setYue(int yueType) {
        this.yueType=yueType;
        changePayType(1);
    }


    //更改支付方式

    /**
     *
     * @param payType
     */
    private void changePayType(int payType) {
        this.payType = payType;
        cbYue.setImageResource(payType == 1 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbWechat.setImageResource(payType == 2 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbAli.setImageResource(payType == 3 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbUnion.setImageResource(payType == 4 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        boolean yue = payType == 1;
        rg1.setEnabled(yue);
        //只有在有企业支付权限的情况下，才会触发
        if (showCompany) {
            if (!yue) {
                rg1.clearCheck();
            } else {
                if (yueType == 0) {
                    rbCompany.performClick();
                } else {
                    rbPerson.performClick();
                }
            }
        }else {
            yueType=1;
        }
        rbPerson.setEnabled(yue);
        rbCompany.setEnabled(yue);
        rg1.setEnabled(yue);

        if (changeListener!=null){
            changeListener.onPayTypeChang(payType,yueType);
        }

    }

    /**
     * <p>Called when the checked radio button has changed. When the
     * selection is cleared, checkedId is -1.</p>
     *
     * @param group     the group in which the checked radio button has changed
     * @param checkedId the unique identifier of the newly checked radio button
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (showCompany) {
            if (checkedId == R.id.rb_company) {//企业账号
                yueType = 0;
            } else if ((checkedId == R.id.rb_person)) {//个人账户
                yueType = 1;
            }
            if (changeListener != null) {
                changeListener.onPayTypeChang(payType, yueType);
            }
        }
    }

    /**
     * 设置是否支持企业账号支付
     * @param showCompany true 支持 默认为false
     */
    public void setShowCompany(boolean showCompany){
        this.showCompany=showCompany;
        yueType = showCompany?yueType:1;
        if (rg1!=null){
            rg1.setVisibility(showCompany?VISIBLE:GONE);
        }
    }


    public interface OnPayTypeChangeListener {
        /**
         * 支付方式更改监听
         *
         * @param payType  1 余额 2微信 3支付宝 4银联
         * @param yueWay  余额支付方式更改监听 0 企业支付 1余额支付 2 申请支付
         */
        void onPayTypeChang(int payType, int yueWay);
    }

}
