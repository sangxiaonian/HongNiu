package com.hongniu.supply.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.supply.R;
import com.sang.common.recycleview.holder.PeakHolder;

/**
 * 作者： ${PING} on 2018/11/27.
 */
public class HomeNewHeader extends PeakHolder implements View.OnClickListener {


    ViewGroup ll_cargo;
    ViewGroup ll_car;
    ViewGroup ll_driver;

    ViewGroup card_star;
    ViewGroup card_match;
    ViewGroup card_goods_match;
    ViewGroup card_insurance;
    ViewGroup card_invite;
    private View.OnClickListener onClickListener;



    public HomeNewHeader(View itemView) {
        super(itemView);
    }

    public HomeNewHeader(Context context, int layoutID) {
        super(context, layoutID);
    }

    public HomeNewHeader(Context context, ViewGroup parent, int layoutID) {
        super(context, parent, layoutID);
    }
    public HomeNewHeader(Context context, ViewGroup parent ) {
        super(context, parent, R.layout.item_home_head1);
    }

    @Override
    public void initView(int position) {
        super.initView(position);

        ll_cargo = itemView.findViewById(R.id.ll_cargo);
        ll_car = itemView.findViewById(R.id.ll_car);
        ll_driver = itemView.findViewById(R.id.ll_driver);
        card_star=itemView.findViewById(R.id.card_star);
        card_match=itemView.findViewById(R.id.card_match);
        card_goods_match=itemView.findViewById(R.id.card_goods_match);
        card_insurance=itemView.findViewById(R.id.card_insurance);
        card_invite=itemView.findViewById(R.id.card_invite);

        ll_cargo.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        ll_driver.setOnClickListener(this);
        card_star.setOnClickListener(this);
        card_match.setOnClickListener(this);
        card_goods_match.setOnClickListener(this);
        card_insurance.setOnClickListener(this);
        card_invite.setOnClickListener(this);


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


}
