package com.hongniu.freight.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.widget.autoline.helper.AutoTagAdapter;
import com.hongniu.freight.R;

/**
 * 作者：  on 2020/2/12.
 */
public class AutoTagSingleAdapter extends AutoTagAdapter<String> {

    private Context mContext;

    public AutoTagSingleAdapter(Context mContext) {
        this.mContext = mContext;
        setCanEmpty(false);
        setSingle(true);
    }

    /**
     * 对数据初始化
     *
     * @param rootView
     * @param position 当前位置
     * @param s        数据
     * @param check    是否选中
     */
    @Override
    protected void initView(View rootView, int position, String s, boolean check) {
        TextView tvTitle = rootView.findViewById(R.id.tv_title);
        View flag = rootView.findViewById(R.id.view_flag);
        tvTitle.setText(s);
        tvTitle.setTextColor(mContext.getResources().getColor(check ? R.color.color_of_040000 : R.color.color_of_999999));
        tvTitle.setTypeface(Typeface.defaultFromStyle(check ? Typeface.BOLD : Typeface.NORMAL));
        flag.setBackgroundResource(check ? R.drawable.ovl_2_e50000 : 0);

    }

    @Override
    public View onCreateView(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.mnlm_item_title, parent, false);
        return inflate;
    }






}
