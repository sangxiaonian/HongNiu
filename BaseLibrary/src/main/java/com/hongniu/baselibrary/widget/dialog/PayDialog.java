package com.hongniu.baselibrary.widget.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.widget.pay.PayWayView;
import com.sang.common.utils.ToastUtils;

/**
 * 作者： ${PING} on 2019/5/19.
 */
public class PayDialog extends DialogFragment implements View.OnClickListener, PayWayView.OnPayTypeChangeListener {


    private View imgCancel;
    private TextView tvTitle;
    private TextView tvDescribe;
    private PayWayView payWay;
    private String describe;
    private String title;
    private PayWayView.OnPayTypeChangeListener changeListener;
    private OnClickPayListener payListener;
    private int showCompany;//1:不可用企业支付  2:企业支付需要审核  3:企业支付不需要审核
    private Button btPay;
    private int yueWay;
    private int payType;
    private float payAmount;//支付金额
    private WalletDetail data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_pay, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        tvTitle = inflate.findViewById(R.id.tv_title);
        tvDescribe = inflate.findViewById(R.id.tv_describe);
        payWay = inflate.findViewById(R.id.pay_way);
        btPay = inflate.findViewById(R.id.bt_pay);
        payWay.setChangeListener(this);
        btPay.setOnClickListener(this);
        imgCancel.setOnClickListener(this);
        payWay.setShowCompany(showCompany!=1);

        payWay.setPayType(1);

        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTitle(title);
        setDescribe(describe);
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.dialog_ani);

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = (int) (getResources().getDisplayMetrics().heightPixels * 2.0 / 3);
        window.setAttributes(params);

    }

    public void setChangeListener(PayWayView.OnPayTypeChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public void setPayListener(OnClickPayListener payListener) {
        this.payListener = payListener;
    }

    public void setTitle(String title) {
        this.title=title;
        if (tvTitle != null) {
            tvTitle.setText(title == null ? "" : title);
        }
    }

    public void setDescribe(String describe) {
        this.describe=describe;
        if (tvDescribe != null) {
            tvDescribe.setVisibility(TextUtils.isEmpty(describe)?View.GONE:View.VISIBLE);
            tvDescribe.setText(describe == null ? "" : describe);
        }
    }

    /**
     * 设置是否支持企业账号支付
     * @param showCompany true 支持 默认为false
     */
    public void setShowCompany(int showCompany){
        this.showCompany=showCompany;
        if (payWay!=null) {
            payWay.setShowCompany(showCompany!=1);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cancel) {
            dismiss();
        }else if (v.getId()==R.id.bt_pay){
            if (payListener!=null){
                payListener.onClickPay(payAmount,payType,yueWay);
            }
        }
    }

    /**
     * 支付方式更改监听
     *
     * @param payType 1 余额 2微信 3支付宝 4银联
     * @param yueWay  余额支付方式更改监听 0 企业支付 1余额支付
     */
    @Override
    public void onPayTypeChang(int payType, int yueWay) {
        if (payType==1&&data!=null){//如果是余额支付
            float balance = 0;//个人月
            float balanceCom = (float) data.getCompanyAvailableBalance();//公司余额
            if (!TextUtils.isEmpty(data.getAvailableBalance())) {
                try {
                    balance = Float.parseFloat(data.getAvailableBalance());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (yueWay==1&&balance<payAmount){
                //选择个人账户支付切个人账户余额不足
                if (balanceCom>=payAmount||data.getType()==2){
                    //如果个人余额不足,公司余额充足，选择公司余额
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("个人账户余额不足");
                    payWay.setYue(0);
                    return;
                }else {
                    //如果企业账户余额也不足
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("余额不足");
                    payWay.setPayType(2);
                    return;
                }
            }else if (yueWay==0&&data.getType()==3&&balanceCom<payAmount){
                //如果企业支付可以直接支付,并且企业支付余额不足
                if (balance>=payAmount){
                    //如果个人余额不足,公司余额充足，选择公司余额
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("企业账户余额不足");
                    payWay.setYue(1);
                    return;
                }else {
                    //如果个人账户余额也不足
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("余额不足");
                    payWay.setPayType(2);
                    return;
                }
            }
        }


        if (payType==1&&yueWay==0&&showCompany==2){
            yueWay=2;
            btPay.setText("申请支付");
        }else {
            btPay.setText("立即支付");
        }
        this.yueWay=yueWay;
        this.payType=payType;

        if (changeListener!=null){
            changeListener.onPayTypeChang(payType,yueWay);
        }
    }

    public void setPayAmount(float payAmount) {
        this.payAmount=payAmount;
    }

    /**
     * 个人钱包信息
     * @param data
     */
    public void setWalletDetaile(WalletDetail data) {
        this.data=data;
    }

    public interface OnClickPayListener{
        /**
         * 点击支付
         * @param amount     支付金额
         * @param payType    1 余额 2微信 3支付宝 4银联
         * @param yueWay     余额支付方式更改监听 0 企业支付 1余额支付 2 申请支付
         */
      void   onClickPay(float amount, int payType, int yueWay);
    }
}
