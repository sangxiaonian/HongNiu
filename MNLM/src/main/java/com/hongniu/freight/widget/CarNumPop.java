package com.hongniu.freight.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.hongniu.freight.R;

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
        void onItemClick(View tragetView, int position, T data);

        void onDissmiss();
    }

    onItemClickListener<T> listener;

    public void setListener(onItemClickListener<T> listener) {
        this.listener = listener;
    }

    private void setContentView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mnlm_order_carnum_pup, null);
        rv = inflate.findViewById(R.id.rv);
        pop.setContentView(inflate);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setOutsideTouchable(true);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (listener != null) {
                    listener.onDissmiss();
                }
            }
        });

        final LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new XAdapter<T>(context, datas) {
            @Override
            public BaseHolder<T> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<T>(context, parent, R.layout.mnlm_order_item_carnum_item) {
                    @Override
                    public void initView(View itemView, final int position, final T data) {
                        super.initView(itemView, position, data);
                        TextView tv = itemView.findViewById(R.id.tv);
                        if (!TextUtils.isEmpty(mark) && data.toString().contains(mark)) {
                            int start = 0;
                            SpannableStringBuilder builder = new SpannableStringBuilder(data.toString());
                            do {
                                start = data.toString().indexOf(mark,start);
                                ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(R.color.color_of_f06f28));
                                builder.setSpan(span, start, start+mark.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                start += mark.length();
                            } while (data.toString().indexOf(mark, start) > 0);
                            tv.setText(builder);
                        } else {
                            tv.setText(data.toString());
                        }

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onItemClick(tragetView, position, data);
                                }
                            }
                        });
                        JLog.i("TextView:" + tv.getWidth());

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
        rv.post(new Runnable() {
            @Override
            public void run() {
                if (tragetView != null) {
                    pop.update((location[0] + tragetView.getWidth() / 2) - rv.getWidth() / 2, location[1] - rv.getHeight(), -1, -1);
                } else {
                    pop.update((location[0]) - rv.getWidth() / 2, location[1] - rv.getHeight(), -1, -1);

                }
            }
        });

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
