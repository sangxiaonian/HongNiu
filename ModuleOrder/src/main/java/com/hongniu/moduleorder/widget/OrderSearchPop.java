package com.hongniu.moduleorder.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hongniu.moduleorder.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.widget.popu.BasePopu;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public class OrderSearchPop<T> extends BasePopu {


    RecyclerView rv;
    private List<SelectBean<T>> datas;
    XAdapter<SelectBean<T>> adapter;

    public OrderSearchPop(Context context) {
        super(context);

        initView(context);
        initDatas(context);
    }

    public void setContentView(View view) {
        pop.setContentView(view);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.pop_ani);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (dismissListener != null) {
                    dismissListener.onPopuDismsss(OrderSearchPop.this, tragetView);
                }
            }
        });


    }


    void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_search_pop, null);
        rv = inflate.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        setContentView(inflate);
    }

    private int selectPosition = -1;

    void initDatas(Context context) {
        datas = new ArrayList<>();
        adapter = new XAdapter<SelectBean<T>>(context, datas) {
            @Override
            public BaseHolder<SelectBean<T>> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<SelectBean<T>>(context, parent, R.layout.order_pop_search_item) {
                    @Override
                    public void initView(View itemView, final int position, final SelectBean<T> data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);

                        tvTitle.setText(data.t.toString());

                        if (selectPosition == position) {
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_light));
                        } else {
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_title_dark));
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (selectPosition != position) {
                                    if (listener != null) {
                                        listener.onPopuClick(OrderSearchPop.this, tragetView, position);
                                    }
                                    selectPosition = position;
                                    adapter.notifyDataSetChanged();
                                }

                            }
                        });
                    }
                };
            }
        };
        rv.setAdapter(adapter);
    }

    public void setSelectPosition(int position) {
        selectPosition = position;
    }

    public void upDatas(List<T> datas) {
        this.datas.clear();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                if (selectPosition == i) {
                    this.datas.add(new SelectBean<T>(datas.get(i), true));
                } else {
                    this.datas.add(new SelectBean<T>(datas.get(i), false));

                }
            }
        }

    }

    @Override
    public void show(View view) {
        tragetView = view;
        adapter.notifyDataSetChanged();
        if (!isShow()) {
            pop.showAsDropDown(view);
        }

    }

    public static class SelectBean<T> {
        public T t;
        public boolean select;

        public SelectBean(T t, boolean select) {
            this.t = t;
            this.select = select;
        }
    }

    OnPopuClickListener listener;

    public void setListener(OnPopuClickListener listener) {
        this.listener = listener;
    }

    public interface OnPopuClickListener {
        void onPopuClick(OrderSearchPop pop, View target, int position);
    }


}
