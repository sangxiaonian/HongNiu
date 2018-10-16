package com.hongniu.moduleorder.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hongniu.moduleorder.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.widget.popu.BasePopu;
import com.sangxiaonian.xcalendar.SequenceCalendar;
import com.sangxiaonian.xcalendar.entity.DateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public class OrderTimePop<T> extends BasePopu implements View.OnClickListener {


    SequenceCalendar calendar;
    private TextView tvReset;
    private TextView tvEntry;

    public OrderTimePop(Context context) {
        super(context);
        initView(context);
    }

    void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_main_time_pop, null);
        calendar = inflate.findViewById(R.id.calendar);
        tvReset=inflate.findViewById(R.id.tv_reset);
        tvEntry=inflate.findViewById(R.id.tv_entry);
        setContentView(inflate);
        View viewById = inflate.findViewById(R.id.view_out);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvReset.setOnClickListener(this);
        tvEntry.setOnClickListener(this);




        viewById.setFocusable(true);
        viewById.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

    }



    @Override
    public void show(View view) {
        tragetView = view;
        if (!isShow()) {
            pop.showAsDropDown(view);
        }

    }



    OnCalenderListener listener;

    public void setListener(OnCalenderListener listener) {
        this.listener = listener;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_reset){
            calendar.clearSelects();
        }else if (v.getId()==R.id.tv_entry){
            if (listener!=null){
                listener.onClickEntry(this,null,calendar.getStartDatebean(),calendar.getEndDateBean());
            }
        }
    }

    public interface OnCalenderListener {
        void onClickEntry(OrderTimePop pop, View target, DateBean start,DateBean end);
    }


}
