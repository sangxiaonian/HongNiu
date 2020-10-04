package com.sang.common.widget.popu;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.sang.common.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public class OrderMainPop<T> extends BasePopu {


    RecyclerView rv;
    private List<SelectBean<T>> datas;
    XAdapter<SelectBean<T>> adapter;

    public OrderMainPop(Context context) {
        super(context);
        initView(context);
        initDatas(context);
    }

    void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_main_pop, null);
        rv = inflate.findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        setContentView(inflate);
        View viewById = inflate.findViewById(R.id.view_out);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


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

    private int selectPosition = -1;

    void initDatas(Context context) {
        datas = new ArrayList<>();
        adapter = new XAdapter<SelectBean<T>>(context, datas) {
            @Override
            public BaseHolder<SelectBean<T>> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<SelectBean<T>>(context, parent, R.layout.order_mian_pop_select_item) {
                    @Override
                    public void initView(View itemView, final int position, final SelectBean<T> data) {
                        super.initView(itemView, position, data);
                        TextView tvTitle = itemView.findViewById(R.id.tv_title);
                        final View img = itemView.findViewById(R.id.img);

                        tvTitle.setText(data.t.toString());

                        if (selectPosition == position) {
                            img.setVisibility(View.VISIBLE);
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_light));
                        } else {
                            tvTitle.setTextColor(context.getResources().getColor(R.color.color_title_dark));
                            img.setVisibility(View.GONE);
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (selectPosition != position) {
                                    if (listener != null) {
                                        listener.onPopuClick(OrderMainPop.this, tragetView, position);
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
        void onPopuClick(OrderMainPop pop, View target, int position);
    }


}
