package com.hongniu.supply.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.supply.R;
import com.hongniu.supply.ui.fragment.HomeFragment;
import com.sang.common.recycleview.holder.PeakHolder;

/**
 * 作者： ${PING} on 2018/11/27.
 */
public class HomeHeader extends PeakHolder implements View.OnClickListener {

    TextView tv_balance;
    Button bt_wallet;
//    ViewGroup ll_search;
    ViewGroup ll_cargo;
    ViewGroup ll_car;
    ViewGroup ll_driver;
    ViewGroup card_policy;
    ViewGroup card_yongjin;
    ViewGroup card_etc;
    private View.OnClickListener onClickListener;
    private String walletInfor;


    public HomeHeader(View itemView) {
        super(itemView);
    }

    public HomeHeader(Context context, int layoutID) {
        super(context, layoutID);
    }

    public HomeHeader(Context context, ViewGroup parent, int layoutID) {
        super(context, parent, layoutID);
    }
    public HomeHeader(Context context, ViewGroup parent ) {
        super(context, parent, R.layout.item_home_head);
        
        
        
        
        
        
    }

    @Override
    public void initView(int position) {
        super.initView(position);
        tv_balance = itemView.findViewById(R.id.tv_balance);
        bt_wallet = itemView.findViewById(R.id.bt_wallet);
//        ll_search = itemView.findViewById(R.id.ll_search);
        ll_cargo = itemView.findViewById(R.id.ll_cargo);
        ll_car = itemView.findViewById(R.id.ll_car);
        ll_driver = itemView.findViewById(R.id.ll_driver);
        card_policy = itemView.findViewById(R.id.card_policy);
        card_yongjin = itemView.findViewById(R.id.card_yongjin);
        card_etc = itemView.findViewById(R.id.card_etc);

        tv_balance.setText(walletInfor==null?"0.00":walletInfor);

        bt_wallet.setOnClickListener(this);
//        ll_search.setOnClickListener(this);
        ll_cargo.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        ll_driver.setOnClickListener(this);
        card_policy.setOnClickListener(this);
        card_yongjin.setOnClickListener(this);
        card_etc.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (onClickListener!=null){
            onClickListener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setWalletInfor(String walletInfor) {
        this.walletInfor = walletInfor;
    }
}
