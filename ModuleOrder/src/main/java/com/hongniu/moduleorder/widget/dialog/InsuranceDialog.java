package com.hongniu.moduleorder.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongniu.baselibrary.widget.dialog.AccountDialog;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.entity.OrderInsuranceInforBean;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

/**
 * 作者： ${桑小年} on 2018/12/1.
 * 保险人选择数据
 */
public class InsuranceDialog extends AccountDialog<OrderInsuranceInforBean> {
    public InsuranceDialog(@NonNull Context context) {
        super(context);
    }

    public InsuranceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initData(Context context) {
        super.initData(context);
        setTitle("选择被保险人");
        setAddInfor("添加新的被保险人");
    }

    @Override
    protected XAdapter<OrderInsuranceInforBean> getAdapter(Context context, List<OrderInsuranceInforBean> inforBeans) {
        return new XAdapter<OrderInsuranceInforBean>(context, InsuranceDialog.this.inforBeans) {
            @Override
            public BaseHolder<OrderInsuranceInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderInsuranceInforBean>(context, parent, R.layout.item_account_dialog) {
                    @Override
                    public void initView(View itemView, final int position, final OrderInsuranceInforBean def) {
                        super.initView(itemView, position, def);
                        ImageView imgPayIcon = itemView.findViewById(R.id.img);
                        TextView tvPayWay = itemView.findViewById(R.id.tv_pay_way);
                        TextView tvPayAccount = itemView.findViewById(R.id.tv_pay_account);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onChoice(InsuranceDialog.this, position, def);
                                }
                            }
                        });
                        imgPayIcon.setVisibility(View.GONE);
                        int insuredType = def.getInsuredType();
                        String title="";
                        String number="";
                        if (insuredType==1){
                            title=def.getUsername()==null?"":def.getUsername();
                            number=def.getIdnumber()==null?"":def.getIdnumber();
                        }else if (insuredType==2){
                            title=def.getCompanyName()==null?"":def.getCompanyName();
                            number=def.getCompanyCreditCode()==null?"":def.getCompanyCreditCode();

                        }
                        tvPayWay.setText(title);
                        tvPayAccount.setText(number);

                    }
                };
            }
        };
    }
}
