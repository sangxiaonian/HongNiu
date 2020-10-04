package com.fy.androidlibrary.widget.viewpager;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;

import java.util.List;

/**
 * 作者：  on 2019/11/14.
 */
public abstract class ViewPagerAdapter<T> extends RecyclerView.Adapter {

    private List<T> datas;
    private boolean recycle;

    public ViewPagerAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position % (datas.size() == 0 ? 1 : datas.size()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        return initHolder(parent, viewType);

    }

    protected abstract BaseHolder<T> initHolder(ViewGroup parent, int viewType);


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder<T> holder1 = (BaseHolder) holder;
        holder1.initView(holder1.getItemView(), position, getItemData(position));


    }

    private T getItemData(int position) {
        if (datas == null || datas.size() == 0) {
            return null;
        } else {
            return datas.get(position % datas.size());
        }
    }


    @Override
    public int getItemCount() {
        return datas == null || datas.size() == 0 ? 0 : (recycle ? Integer.MAX_VALUE : datas.size());
    }


    public void setRecycle(boolean recycle) {
        this.recycle = recycle;
    }
}
