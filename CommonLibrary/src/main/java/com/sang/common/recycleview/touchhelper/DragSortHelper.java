package com.sang.common.recycleview.touchhelper;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sang.common.recycleview.inter.OnItemTouchHelper;

/**
 * 作者： ${桑小年} on 2018/8/5.
 * 努力，为梦长留
 */
public class DragSortHelper extends ItemTouchHelper.Callback {
    private OnItemTouchHelper helper;
    private Context context;

    public DragSortHelper(OnItemTouchHelper helper) {
        this.helper = helper;
    }

    /**
     * 设置Drag/Swipe的Flag
     * 这里我们把滑动(Drag)的四个方向全都设置上了,说明Item可以随意移动
     * 然后把删除(暂且叫删除/swipe)的方向设置为Start和End,说明可以水平拖动删除
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.RIGHT;
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlag, swipeFlag);


    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        helper.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        /**
         * 回调
         */
        helper.onSwipe(viewHolder.getAdapterPosition());
    }


    /**
     * Item是否能被Swipe到dismiss
     * 也就是删除这条数据
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /**
     * Item长按是否可以拖拽
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        helper.onSelectedChanged( viewHolder,   actionState);
    }
}
