package com.hongniu.modulefinance.widget;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongniu.baselibrary.entity.PayInforBeans;
import com.hongniu.baselibrary.widget.dialog.AccountDialog;
import com.hongniu.modulefinance.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${桑小年} on 2018/12/1.
 * 努力，为梦长留
 */
public class AccountWayDialog extends AccountDialog<PayInforBeans> {
    public AccountWayDialog(@NonNull Context context) {
        super(context);
    }

    public AccountWayDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected XAdapter<PayInforBeans> getAdapter(Context context, List<PayInforBeans> inforBeans) {
        return new XAdapter<PayInforBeans>(context, AccountWayDialog.this.inforBeans) {
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
                                    listener.onChoice(AccountWayDialog.this, position, def);
                                }
                            }
                        });
                        if (def.getType() == 0) {//微信
                            imgPayIcon.setImageResource(R.mipmap.icon_wechat_40);
                            tvPayWay.setText(context.getString(R.string.wallet_balance_withDrawal_weiChat));
                            tvPayAccount.setText(String.format(context.getString(R.string.account), def.getWxNickName()) == null ? "" : def.getWxNickName());
                        } else if (def.getType() == 1) {//银行卡
                            imgPayIcon.setImageResource(R.mipmap.icon_ylzf_40);
                            tvPayWay.setText(def.getBankName() == null ? "" : def.getBankName());
                            if (def.getCardNo() != null && def.getCardNo().length() > 4) {
                                tvPayAccount.setText(String.format(context.getString(R.string.wallet_balance_withdrawal_card_num), def.getCardNo().substring(0, 4)));
                            } else {
                                tvPayAccount.setText(String.format(context.getString(R.string.wallet_balance_withdrawal_card_num), ""));
                            }

                        } else if (def.getType() == 3) {//支付宝
                            imgPayIcon.setImageResource(R.mipmap.icon_zfb_40);
                            tvPayWay.setText(context.getString(R.string.wallet_balance_withDrawal_ali));
                            tvPayAccount.setText(String.format(context.getString(R.string.account), def.getWxNickName()) == null ? "" : def.getWxNickName());
                        }


                    }
                };
            }
        };
    }
}
