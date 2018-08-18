package com.sang.common.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.recycleview.inter.IXAdapter;
import com.sang.common.recycleview.inter.OnItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
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
