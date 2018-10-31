package com.hongniu.modulefinance.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.hongniu.baselibrary.widget.WithDrawProgress;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.BalanceOfAccountBean;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * 作者： ${PING} on 2018/10/9.
 * 添加新的提现到账方式
 */
public class WithDrawDialog implements DialogControl.IDialog, View.OnClickListener {

    private View imgCancel;//取消按钮
    private Dialog dialog;

    OnAddNewPayWayListener listener;
    private TextView tv_start_title;
    private TextView tv_start_time;
    private TextView tv_center_title;
    private TextView tv_center_time;
    private TextView tv_bottom_title;
    private TextView tv_bottom_time;
    private TextView tv_money;
    private TextView tv_withdraw;
    private WithDrawProgress progress;

    Context context;

    public interface OnAddNewPayWayListener {

        void onAddUnipay(DialogControl.IDialog dialog);

        void onAddWechat(DialogControl.IDialog dialog);

        void onAddAli(DialogControl.IDialog dialog);

    }

    public WithDrawDialog(@NonNull Context context) {
        this(context, 0);
    }


    public WithDrawDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(Context context, int themeResId) {
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_withdraw, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        tv_start_title = inflate.findViewById(R.id.tv_start_title);
        tv_start_time = inflate.findViewById(R.id.tv_start_time);
        tv_center_title = inflate.findViewById(R.id.tv_center_title);
        tv_center_time = inflate.findViewById(R.id.tv_center_time);
        tv_bottom_title = inflate.findViewById(R.id.tv_bottom_title);
        tv_bottom_time = inflate.findViewById(R.id.tv_bottom_time);
        tv_money = inflate.findViewById(R.id.tv_money);
        tv_withdraw = inflate.findViewById(R.id.tv_withdraw);
        progress = inflate.findViewById(R.id.progress);


        imgCancel.setOnClickListener(this);


        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }


    public void setData(BalanceOfAccountBean balanceOfAccountBean) {
        if (balanceOfAccountBean == null) {
            return;
        }
        tv_money.setText(String.format(context.getString(R.string.money_symbol_des), balanceOfAccountBean.getAmount()));
        tv_withdraw.setText(balanceOfAccountBean.getSubtitle() == null ? "" : balanceOfAccountBean.getSubtitle());
        int reviewState = balanceOfAccountBean.getReviewState();
        reviewState=2;

        tv_start_time.setTextColor(  Color.parseColor("#999999"));
        tv_start_title.setTextColor( Color.parseColor("#999999"));
        tv_start_time.setText("发起时间："+(balanceOfAccountBean.getCreateTime() == null ? "" : balanceOfAccountBean.getCreateTime()));
        tv_bottom_title.setTextColor( Color.parseColor("#999999"));
        tv_bottom_title.setText("系统已转账");

        if (reviewState == 0) {//待审核，审核中
            tv_center_time.setTextColor(  Color.parseColor("#333333") );
            tv_center_title.setTextColor(  Color.parseColor("#333333")  );
            tv_center_title.setText("提现审核中");
            tv_center_time.setText((balanceOfAccountBean.getReviewTime()==null?"":("审核时间："+balanceOfAccountBean.getReviewTime())));
            progress.setCurrentProgress( 50);


        } else if (reviewState == 1) {//审核通过
            tv_center_time.setTextColor(  Color.parseColor("#999999") );
            tv_center_title.setTextColor(  Color.parseColor("#999999")  );
            tv_center_time.setText("审核时间："+(balanceOfAccountBean.getReviewTime()==null?"":balanceOfAccountBean.getReviewTime()));
            tv_center_title.setText("提现审核已通过");

            tv_bottom_title.setTextColor(Color.parseColor("#333333"));
            tv_bottom_time.setTextColor(Color.parseColor("#333333"));
            tv_bottom_time.setText("具体到账时间以银行为准");
            progress.setCurrentProgress( 100);
        } else if (reviewState == 2) {//审核不通过
            tv_center_time.setTextColor(  Color.parseColor("#333333") );
            tv_center_title.setTextColor(  Color.parseColor("#333333")  );
            tv_center_time.setText((balanceOfAccountBean.getReviewRemark()==null?"":balanceOfAccountBean.getReviewRemark()));
            tv_center_title.setText("提现审核被拒");
            progress.setCurrentProgress( 50);
        }

        tv_center_time.setVisibility(TextUtils.isEmpty(tv_center_time.getText().toString().trim())?View.GONE:View.VISIBLE);
        tv_bottom_time.setVisibility(TextUtils.isEmpty(tv_bottom_time.getText().toString().trim())?View.GONE:View.VISIBLE);



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
        } else if (v.getId() == R.id.ali) {
            if (listener != null) {
                listener.onAddAli(this);
            }
        } else if (v.getId() == R.id.wechat) {
            if (listener != null) {
                listener.onAddWechat(this);
            }
        } else if (v.getId() == R.id.unipay) {
            if (listener != null) {
                listener.onAddUnipay(this);
            }
        }
    }
}
