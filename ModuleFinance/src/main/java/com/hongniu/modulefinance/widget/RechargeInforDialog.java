package com.hongniu.modulefinance.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.QueryBindHuaInforsBean;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/10/9.
 * 充值到账方式
 */
public class RechargeInforDialog implements DialogControl.IDialog, View.OnClickListener {

    private View imgCancel ,btSume,bt_copy_fuyan;//取消按钮
    private TextView tvInfor  ;//取消按钮
    private Dialog dialog;
    OnEntryClickListener clickListener;
    private QueryBindHuaInforsBean countInfor;
    private TextView tvTitle;

    public void setInfor(QueryBindHuaInforsBean countInfor) {
        this.countInfor=countInfor;
    }

    public interface OnEntryClickListener{
        /**
         *
         * @param type 0 复制账号
         * @param msg  1 复制附言
         */
        void onClickEntry(int type, String msg);
    }


    public void setClickListener(OnEntryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RechargeInforDialog(@NonNull Context context) {
        this(context, 0);
    }


    public RechargeInforDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_recharge_infor, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        bt_copy_fuyan = inflate.findViewById(R.id.bt_copy_fuyan);

        tvInfor = inflate.findViewById(R.id.tv_infor);
        tvTitle = inflate.findViewById(R.id.tv_2);
        btSume = inflate.findViewById(R.id.bt_sum);

        imgCancel.setOnClickListener(this);
        btSume.setOnClickListener(this);
        bt_copy_fuyan.setOnClickListener(this);

        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }



    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    public void show() {

        dialog.show();
        if (countInfor!=null) {
            StringBuffer buffer=new StringBuffer();
            buffer.append("户名：")
                    .append(countInfor.getOthBankPayeeSubAccName())
                    .append("\n")
                    .append("账号：")
                    .append(countInfor.getOthBankPayeeSubAcc())
                    .append("\n")
                    .append("支行：")
                    .append(countInfor.getOthBankPayeeSubAccSetteName())
                    .append("\n")
                    .append("附言：")
                    .append(countInfor.getTransacRemark())
            ;
            tvInfor.setText(buffer.toString());
            tvTitle.setText("请采用你绑定的银行卡"+countInfor.getBankCardNum()+"，通过网银转账或汇款方式转入华夏银行监管账户，收款账户信息如下（"+countInfor.getTransacExplain()+"）：");
        }
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
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
        }else if (v.getId()==R.id.bt_sum){



            if (clickListener!=null){
                if (countInfor!=null) {
                    String buffer = "户名：" +
                            countInfor.getOthBankPayeeSubAccName() +
                            "\n" +
                            "账号：" +
                            countInfor.getOthBankPayeeSubAcc() +
                            "\n" +
                            "支行：" +
                            countInfor.getOthBankPayeeSubAccSetteName();
                    clickListener.onClickEntry(0, buffer.trim());
                }
            }

            dismiss();

        }else if (v.getId()==R.id.bt_copy_fuyan){
            if (clickListener!=null){
                clickListener.onClickEntry(1, countInfor!=null?countInfor.getTransacRemark():"");
            }
            dismiss();

        }
    }
}
