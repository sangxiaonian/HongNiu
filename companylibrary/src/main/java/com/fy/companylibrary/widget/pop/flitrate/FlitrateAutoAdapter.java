package com.fy.companylibrary.widget.pop.flitrate;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.widget.CircularFrameLayout;
import com.fy.androidlibrary.widget.autoline.helper.AutoTagAdapter;
import com.fy.companylibrary.R;

/**
 * 作者：  on 2019/11/1.
 */
public class FlitrateAutoAdapter<T> extends AutoTagAdapter<T> {

    private Context mContext;

    public FlitrateAutoAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    /**
     * 对数据初始化
     *
     * @param rootView
     * @param position 当前位置
     * @param t        数据
     * @param check    是否选中
     */
    @Override
    protected void initView(View rootView, int position, T t, boolean check) {
        TextView tvTag = rootView.findViewById(R.id.tv_tag);
        tvTag.setText(t.toString());
        tvTag.setTextColor(mContext.getResources().getColor(check ? R.color.color_of_1c234d : R.color.color_of_3d3d3d));
        tvTag.setBackgroundColor(mContext.getResources().getColor(check ? R.color.color_of_ebedfa : R.color.color_of_tran));
        ((CircularFrameLayout) rootView).setShowBorder(!check);
        tvTag .setTypeface(Typeface.defaultFromStyle(check?Typeface.BOLD:Typeface.NORMAL));

    }

    @Override
    public View onCreateView(@NonNull ViewGroup parent, int viewType) {
        return LayoutInflater.from(mContext).inflate(R.layout.item_pop_check,parent,false);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


}
