package com.hongniu.baselibrary.widget.dialog;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.utils.LinearDividerDecoration;
import com.sang.common.recycleview.adapter.XAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： ${PING} on 2019/5/19.
 */
public class ListDialog<T> extends DialogFragment implements View.OnClickListener {


    private View imgCancel;
    private RecyclerView rv;
    private TextView tvTitle;
    private TextView tvDescribe;
    private List<T> datas;
    private XAdapter<T> adapter;
    private String describe;
    private String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog_list, null);
        imgCancel = inflate.findViewById(R.id.img_cancel);
        tvTitle = inflate.findViewById(R.id.tv_title);
        tvDescribe = inflate.findViewById(R.id.tv_describe);
        imgCancel.setOnClickListener(this);
        rv = inflate.findViewById(R.id.rv);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datas = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.addItemDecoration(new LinearDividerDecoration(getContext()));
        rv.setAdapter(adapter);
        setTitle(title);
        setDescribe(describe);

    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.dialog_ani);

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        params.height = (int) (getResources().getDisplayMetrics().heightPixels * 2.0 / 3);
        window.setAttributes(params);

    }


    public void setTitle(String title) {
        this.title=title;
        if (tvTitle != null) {
            tvTitle.setText(title == null ? "" : title);
        }
    }

    public void setDescribe(String describe) {
        this.describe=describe;
        if (tvDescribe != null) {
            tvDescribe.setVisibility(TextUtils.isEmpty(describe)?View.GONE:View.VISIBLE);
            tvDescribe.setText(describe == null ? "" : describe);
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_cancel) {
            dismiss();
        }
    }

    public void setAdapter(XAdapter<T> adapter) {
        this.adapter = adapter;
    }
}
