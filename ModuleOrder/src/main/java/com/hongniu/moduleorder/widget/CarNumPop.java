package com.hongniu.moduleorder.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hongniu.moduleorder.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2018/8/9.
 * 车牌联想pupWindow
 */
public class CarNumPop<T> {
    RecyclerView rv;
    protected PopupWindow pop;
    protected View tragetView;
    XAdapter<T> adapter;
    private List<T> datas = new ArrayList<>();
    private String mark = "沪";

    final int[] location = new int[2];


    public CarNumPop(Context context) {
        pop = new PopupWindow(context);
        setContentView(context);
    }

    public interface onItemClickListener<T> {
        void onItemClick(int position, T data);
    }

    onItemClickListener<T> listener;

    public void setListener(onItemClickListener<T> listener) {
        this.listener = listener;
    }

    private void setContentView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_carnum_pup, null);
        rv = inflate.findViewById(R.id.rv);
        pop.setContentView(inflate);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setOutsideTouchable(true);
//        pop.setAnimationStyle(R.style.pop_ani);
        rv.post(new Runnable() {
            @Override
            public void run() {
                if (tragetView != null) {
                    pop.update((location[0] + tragetView.getWidth() / 2) - rv.getWidth() / 2, location[1] - rv.getHeight(), rv.getWidth(), rv.getHeight());
                } else {
                    pop.update((location[0]) - rv.getWidth() / 2, location[1] - rv.getHeight(), rv.getWidth(), rv.getHeight());

                }
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new XAdapter<T>(context, datas) {
            @Override
            public BaseHolder<T> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<T>(context, parent, R.layout.order_item_carnum_item) {
                    @Override
                    public void initView(View itemView, final int position, final T data) {
                        super.initView(itemView, position, data);
                        TextView tv = itemView.findViewById(R.id.tv);
                        if (!TextUtils.isEmpty(mark) && data.toString().startsWith(mark)) {
                            SpannableStringBuilder builder = new SpannableStringBuilder(data.toString());
                            ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.color_light));
                            builder.setSpan(span, 0, mark.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv.setText(builder);
                        } else {
                            tv.setText(data.toString());
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onItemClick(position, data);
                                }
                            }
                        });

                    }
                };
            }
        };
        rv.setAdapter(adapter);


    }


    public void upData(String mark, List<T> datas) {
        this.mark = mark;
        this.datas.clear();
        if (datas != null) {
            this.datas.addAll(datas);
        }
        adapter.notifyDataSetChanged();


    }


    public void show(final View view) {
        tragetView = view;
        if (datas.size() == 0) {
            return;
        }
        view.getLocationOnScreen(location);
        pop.showAtLocation(view, Gravity.NO_GRAVITY, (location[0] + view.getWidth() / 2) - rv.getWidth() / 2, location[1] - rv.getHeight());
    }

    public void dismiss() {
        if (pop.isShowing()) {
            pop.dismiss();
        }

    }

    public boolean isShow() {
        return pop.isShowing();
    }


}
