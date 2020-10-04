package com.sang.common.recycleview.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * 作者： ${PING} on 2017/9/4.
 * 带看记录使用的ViewPager
 */

public abstract class XAdapter<T> extends BaseAdapter<T> {


    public XAdapter(Context context, List<T> list) {
        super(context, list);
    }

}
