package com.sang.common.recycleview.inter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.sang.common.recycleview.holder.PeakHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public interface IXAdapter<T> {

    /**
     * 初始化ViewHolder,{@link XAdapter#onCreateViewHolder(ViewGroup, int)}处,用于在非头布局\脚布局\刷新时候
     * 调用
     *
     * @param parent   父View,即为RecycleView
     * @param viewType holder类型,在{@link XAdapter#getItemViewType(int)}处使用
     * @return BaseHolder或者其父类
     */
    RecyclerView.ViewHolder initHolder(ViewGroup parent, final int viewType);

    /**
     * 初始化XAdapter 的viewType,且此处已经经过处理,去除Header等的影响,可以直接从0开始使用
     *
     * @param position 当前item的Position(从0开始)
     * @return
     */
    int getViewType(int position);

    /**
     * 获取指定条目的Item，该position位置为非 header的位置，且从0开始，
     *
     * @param position
     */
    T getItemData(int position);


    /**
     * 获取所有的头布局
     */
    List<PeakHolder> getHeads();


    /**
     * 添加头布局
     */
    void addHeard(PeakHolder heardHolder);

    /**
     * 移出指定的头布局
     */
    void removeHeard(PeakHolder heardHolder);

    /**
     * 移出指定位置的头布局
     */
    void removeHeard(int index);

    /**
     * 添加脚布局
     *
     * @param heardHolder
     */
    void addFoot(PeakHolder heardHolder);

    /**
     * 添加指定位置的脚布局
     *
     * @param index
     * @param heardHolder
     */
    void addFoot(int index, PeakHolder heardHolder);

    /**
     * 获取所有的脚布局
     */
    List<PeakHolder> getFoots();

    /**
     * 移除指定位置脚布局
     */
    void removeFoot(int index);

    /**
     * 移除指定脚布局
     */
    void removeFoot(PeakHolder heardHolder);


    /**
     * 在指定位置添加一个布局后刷新
     *
     * @param position
     */
    void notifyItemAdd(int position);

    /**
     * 在指定位删除一个布局后刷新
     *
     * @param position
     */
    void notifyItemDeleted(int position);

}
