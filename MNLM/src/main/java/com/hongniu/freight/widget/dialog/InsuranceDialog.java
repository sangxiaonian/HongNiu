package com.hongniu.freight.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.widget.dialog.inter.DialogControl;

import java.util.List;


/**
 * 作者： ${桑小年} on 2018/12/1.
 * 保险人选择数据
 */
public class InsuranceDialog extends AccountDialog<InsuranceInfoBean> {
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
        adapter.addFoot(new PeakHolder(context, rv, R.layout.item_order_insuranc_foot) {
            @Override
            public void initView(View itemView, int position) {
                super.initView(itemView, position);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onClickAdd(InsuranceDialog.this);
                        }
                    }
                });
            }
        });
    }

    OnInsuranceDialogListener itemClickListener;


    /**
     * 点击编辑监听
     *
     * @param itemClickListener
     */
    public void setItemClickListener(OnInsuranceDialogListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    protected XAdapter<InsuranceInfoBean> getAdapter(Context context, List<InsuranceInfoBean> inforBeans) {
        return new XAdapter<InsuranceInfoBean>(context, InsuranceDialog.this.inforBeans) {
            @Override
            public BaseHolder<InsuranceInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<InsuranceInfoBean>(context, parent, R.layout.item_order_insuranc) {
                    @Override
                    public void initView(View itemView, final int position, final InsuranceInfoBean def) {
                        super.initView(itemView, position, def);
                        TextView tvPayWay = itemView.findViewById(R.id.tv_pay_way);
                        TextView tvPayAccount = itemView.findViewById(R.id.tv_pay_account);
                        TextView tvEdit = itemView.findViewById(R.id.tv_edit);
                        String cardID = def.getInsuredType() == 2 ? def.getCompanyCreditCode() : def.getIdnumber();
                        CommonUtils.setText(tvPayWay,def.getInsuredType()==2?def.getCompanyName():def.getUsername());
                        CommonUtils.setText(tvPayAccount,"身份证  "+ cardID);


                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onChoice(InsuranceDialog.this, position, def);
                                }
                            }
                        });


                        tvEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onClickEdite(InsuranceDialog.this, position, def);
                                }
                            }
                        });


                    }
                };
            }
        };
    }

    public interface OnInsuranceDialogListener {
        /**
         * 编辑被保险人
         *
         * @param position
         * @param def
         */
        void onClickEdite(DialogControl.IDialog dialog, int position, InsuranceInfoBean def);

        /**
         * 点击添加新的被保险人
         */
        void onClickAdd(DialogControl.IDialog dialog);

        void onChoice(DialogControl.IDialog dialog, int position, InsuranceInfoBean def);
    }
}
