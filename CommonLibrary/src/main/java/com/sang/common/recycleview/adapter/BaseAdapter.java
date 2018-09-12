package com.sang.common.recycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.recycleview.inter.IXAdapter;
import com.sang.common.recycleview.inter.OnItemTouchHelper;
import com.sang.common.utils.JLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 作者： ${PING} on 2017/9/4.
 * 带看记录使用的ViewPager
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter implements IXAdapter<T>, OnItemTouchHelper {


    public Context context;
    protected List<T> list;
    protected List<PeakHolder> heads;
    protected List<PeakHolder> foots;
    protected final int HEADTYPE = 100000;
    protected final int FOOTTYPE = 200000;

    public BaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        heads = new ArrayList<>();
        foots = new ArrayList<>();
    }


    public void addHeard(PeakHolder heardHolder) {
        heads.add(heardHolder);
    }

    public void addHeard(int index, PeakHolder heardHolder) {
        heads.add(index, heardHolder);
    }

    public void removeHeard(PeakHolder heardHolder) {
        heads.remove(heardHolder);
    }

    public void removeHeard(int index) {
        heads.remove(index);
    }

    public void addFoot(PeakHolder heardHolder) {
        foots.add(heardHolder);
    }

    public void addFoot(int index, PeakHolder heardHolder) {
        foots.add(index, heardHolder);
    }

    public List<PeakHolder> getFoots() {
        return foots;
    }

    public void removeFoot(int index) {
        foots.remove(index);
    }

    public void removeFoot(PeakHolder heardHolder) {
        if (foots.contains(heardHolder)) {
            foots.remove(heardHolder);
        }
    }


    public void notifyItemAdd(int position) {
        position += heads.size();
        notifyItemInserted(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    public void notifyItemDeleted(int position) {
        position += heads.size();
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }


    @Override
    public int getItemViewType(int position) {
        if (position < heads.size()) {
            return position + HEADTYPE;
        } else if (position >= heads.size() + list.size()) {
            return FOOTTYPE + position - heads.size() - list.size();
        } else {
            position -= heads.size();
        }

        return getViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        if (viewType >= HEADTYPE && viewType < FOOTTYPE) {
            return heads.get(viewType - HEADTYPE);
        } else if (viewType >= FOOTTYPE) {
            return foots.get(viewType - FOOTTYPE);

        } else {
            return initHolder(parent, viewType);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int viewType = getItemViewType(position);
        if (viewType >= HEADTYPE) {//脚布局
            PeakHolder holder1 = (PeakHolder) holder;
            holder1.initView(viewType - HEADTYPE);
        } else if (viewType >= FOOTTYPE) {//头布局
            PeakHolder holder1 = (PeakHolder) holder;
            holder1.initView(viewType - FOOTTYPE);
        } else {//一般布局
            position -= heads.size();
            BaseHolder holder1 = (BaseHolder) holder;
            holder1.initView(holder1.getRootView(), position, list.get(position));
        }


    }


    @Override
    public int getItemCount() {
        return list.size() + heads.size() + foots.size();
    }

    /**
     * 初始化ViewHolder,{@link BaseAdapter#onCreateViewHolder(ViewGroup, int)}处,用于在非头布局\脚布局\刷新时候
     * 调用
     *
     * @param parent   父View,即为RecycleView
     * @param viewType holder类型,在{@link BaseAdapter#getItemViewType(int)}处使用
     * @return BaseHolder或者其父类
     */
    public abstract BaseHolder<T> initHolder(ViewGroup parent, final int viewType);

    /**
     * 初始化XAdapter 的viewType,且此处已经经过处理,去除Header等的影响,可以直接从0开始使用
     *
     * @param position 当前item的Position(从0开始)
     * @return
     */
    public int getViewType(int position) {
        return 0;
    }


    public T getItemData(int position) {
        return list.get(position);
    }


    public List<PeakHolder> getHeads() {
        return heads;
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        if (fromPosition>=0&&toPosition<list.size()) {
            Collections.swap(list, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onSwipe(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    private RecyclerView.ViewHolder selectHolder;

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null && actionState == 2) {
            selectHolder = viewHolder;
            viewHolder.itemView.animate().scaleX(0.9f).scaleY(0.9f).start();
        } else if (selectHolder != null && actionState == 0) {
            selectHolder.itemView.animate().scaleX(1f).scaleY(1f).start();
        }
    }
}
