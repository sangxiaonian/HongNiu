package com.hongniu.modulefinance.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.modulefinance.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/10/9.
 * 选择提现到账方式
 */
public class AccountDialog implements DialogControl.IDialog, View.OnClickListener {

    private View imgCancel;//取消按钮
    private RecyclerView rv;
    private View llAdd;//添加新的收款方式
    private Dialog dialog;
    private List<PayInforBeans> inforBeans;

    private XAdapter<PayInforBeans> adapter;
    OnDialogClickListener listener;



    public interface OnDialogClickListener {
        void onChoice(DialogControl.IDialog dialog, int position, PayInforBeans bean);

        void onAddClick(DialogControl.IDialog dialog);
    }

    public AccountDialog(@NonNull Context context) {
        this(context, 0);
    }


    public AccountDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
        initData(context);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_account, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        imgCancel.setOnClickListener(this);
        rv = inflate.findViewById(R.id.rv);
        llAdd = inflate.findViewById(R.id.ll_add);
        llAdd.setOnClickListener(this);


        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(context, 360));
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    private void initData(Context context) {
        inforBeans = new ArrayList<PayInforBeans>();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new XAdapter<PayInforBeans>(context, inforBeans) {
            @Override
            public BaseHolder<PayInforBeans> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<PayInforBeans>(context, parent, R.layout.item_account_dialog) {
                    @Override
                    public void initView(View itemView, final int position, final PayInforBeans def) {
                        super.initView(itemView, position, def);
                        ImageView imgPayIcon = itemView.findViewById(R.id.img);
                        TextView tvPayWay = itemView.findViewById(R.id.tv_pay_way);
                        TextView tvPayAccount = itemView.findViewById(R.id.tv_pay_account);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onChoice(AccountDialog.this, position, def);
                                }
                            }
                        });
                        if (def.getType()==0) {//微信
                            imgPayIcon.setImageResource(R.mipmap.icon_wechat_40);
                            tvPayWay.setText(context.getString(R.string.wallet_balance_withDrawal_weiChat));
                            tvPayAccount.setText(String.format(context.getString(R.string.account),def.getWxNickName())==null?"":def.getWxNickName());
                        }else if (def.getType()==1){//银行卡
                            imgPayIcon.setImageResource(R.mipmap.icon_ylzf_40);
                            tvPayWay.setText(def.getBankName()==null?"":def.getBankName());
                            if (def.getCardNo()!=null&&def.getCardNo().length()>4){
                                tvPayAccount.setText(String.format(context.getString(R.string.wallet_balance_withdrawal_card_num),def.getCardNo().substring(0,4)) );
                            }else {
                                tvPayAccount.setText(String.format(context.getString(R.string.wallet_balance_withdrawal_card_num),"") );
                            }

                        }else if (def.getType()==3){//支付宝
                            imgPayIcon.setImageResource(R.mipmap.icon_zfb_40);
                            tvPayWay.setText(context.getString(R.string.wallet_balance_withDrawal_ali));
                            tvPayAccount.setText(String.format(context.getString(R.string.account),def.getWxNickName())==null?"":def.getWxNickName());
                        }


                    }
                };
            }
        };
        rv.setAdapter(adapter);
    }



    public void setData(List<PayInforBeans> data) {
        inforBeans.clear();
        if (!CommonUtils.isEmptyCollection(data)) {
            inforBeans.addAll(data);
        }
        adapter.notifyDataSetChanged();
    }


    public void setListener(OnDialogClickListener listener) {
        this.listener = listener;
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
        if (v.getId() == R.id.ll_add) {
            if (listener != null) {
                listener.onAddClick(this);
            }
            dismiss();
        } else if (v.getId() == R.id.img_cancel) {
            dismiss();
        }
    }
}
