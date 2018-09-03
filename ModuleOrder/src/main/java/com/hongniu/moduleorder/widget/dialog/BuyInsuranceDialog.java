package com.hongniu.moduleorder.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.sang.common.utils.PointLengthFilter;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2018/8/6.
 */
public class BuyInsuranceDialog extends Dialog implements TextWatcher, DialogInterface.OnDismissListener {


    private View bt_cancle;
    private EditText et_price;
    private CheckBox checkbox;
    private TextView tv_notice;
    private Button bt_sum;
    OnBuyInsuranceClickListener listener;
    private TextView tv_insurance;//保费
    String orderID;

    private Disposable disposable;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (orderID != null && et_price != null) {
                if (disposable!=null){
                    disposable.dispose();
                }
                HttpOrderFactory.queryInstancePrice(et_price.getText().toString().trim(), orderID)
                        .subscribe(new NetObserver<String>(null) {
                            @Override
                            public void onSubscribe(Disposable d) {
                                super.onSubscribe(d);
                                disposable=d;
                            }

                            @Override
                            public void doOnSuccess(String data) {
                                if (tv_insurance!=null){
                                    tv_insurance.setText("￥"+Float.parseFloat(data));
                                }
                            }
                        });
            }
        }
    };


    public BuyInsuranceDialog(@NonNull Context context) {
        this(context, 0);
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public BuyInsuranceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }



    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_buy_insurance, null);
        bt_cancle = inflate.findViewById(R.id.bt_cancle);
        tv_insurance = inflate.findViewById(R.id.tv_insurance);

        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        et_price = inflate.findViewById(R.id.et_price);

        et_price.addTextChangedListener(this);


        checkbox = inflate.findViewById(R.id.checkbox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String trim = et_price.getText().toString().trim();
                bt_sum.setEnabled(!TextUtils.isEmpty(trim) && isChecked);

            }
        });

        tv_notice = inflate.findViewById(R.id.tv_notice);
        tv_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.noticeClick(BuyInsuranceDialog.this, checkbox.isChecked());
                }
            }
        });
        bt_sum = inflate.findViewById(R.id.bt_sum);
        bt_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.entryClick(BuyInsuranceDialog.this, checkbox.isChecked(), et_price.getText().toString().trim());
                }
            }
        });


        et_price.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8), new PointLengthFilter()});
        setContentView(inflate);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setWindowAnimations(R.style.dialog_ani);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        setOnDismissListener(this);
    }

    /**
     * 设置是否已读
     *
     * @param hasRead
     */
    public void setReadInsurance(boolean hasRead) {
        checkbox.setChecked(hasRead);
    }

    public void setListener(OnBuyInsuranceClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        bt_sum.setEnabled(!TextUtils.isEmpty(et_price.getText().toString().trim()) && checkbox.isChecked());
        if (handler != null) {

            handler.removeMessages(0);
            if (!TextUtils.isEmpty(et_price.getText().toString().trim())) {
                handler.sendEmptyMessageDelayed(0, 200);
            }else {
                tv_insurance.setText("￥"+0);

            }
        }
    }

    /**
     * This method will be invoked when the dialog is dismissed.
     *
     * @param dialog the dialog that was dismissed will be passed into the
     *               method
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        if (disposable!=null){
            disposable.dispose();
        }
        handler.removeMessages(0);
    }

    @Override
    public void onDetachedFromWindow() {
        if (disposable!=null){
            disposable.dispose();
        }
        handler.removeMessages(0);
        super.onDetachedFromWindow();

    }

    public interface OnBuyInsuranceClickListener {

        /**
         * 购买保险点击确定按钮
         *
         * @param dialog
         * @param checked    是否阅读条款
         * @param cargoPrice 货物金额
         */
        void entryClick(Dialog dialog, boolean checked, String cargoPrice);

        void noticeClick(BuyInsuranceDialog buyInsuranceDialog, boolean checked);
    }
}
