package com.hongniu.moduleorder.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hongniu.moduleorder.R;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class InsuranceNoticeDialog extends Dialog {


    private TextView tv_content;
    private TextView tv_title;
    private Button bt_sum;
    private DialogControl.OnButtonBottomClickListener listener;

    public InsuranceNoticeDialog(@NonNull Context context) {
        this(context, 0);
    }


    public InsuranceNoticeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_insurance_notice, null);
        tv_content = inflate.findViewById(R.id.tv_notice);
        tv_title = inflate.findViewById(R.id.tv_title);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        bt_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBottomClick(null, null);
                }
                dismiss();
            }
        });


        setContentView(inflate);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 450));
        getWindow().setWindowAnimations(R.style.dialog_ani);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public void setTitle(String title) {
        if (title != null) {
            tv_title.setText(title);
        }
    }

    public void setContent(String content) {
        if (content != null) {
            tv_content.setText(content);
        }
    }

    public void setOnBottomClickListener(DialogControl.OnButtonBottomClickListener listener) {
        this.listener = listener;
    }

}
