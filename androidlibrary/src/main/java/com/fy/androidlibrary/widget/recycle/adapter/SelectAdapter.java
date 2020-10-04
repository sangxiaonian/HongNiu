package com.fy.androidlibrary.widget.recycle.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2019/10/29.
 * 选择Adapter
 */
public abstract class SelectAdapter<T> extends XAdapter<SelectAdapter.SelectInfoBean<T>> {


    private int itemLayoutID;
    private boolean single;//是否是单选
    private boolean canEmpty;//能否选空

    private SingleSelectedListener<T> singleSelectedListener;
    private MultipleSelectedListener<T> multipleSelectedListener;


    public SelectAdapter(Context context, RecyclerView recyclerView) {
        this(context, recyclerView,null);
    }
  public SelectAdapter(Context context, RecyclerView recyclerView,List<SelectAdapter.SelectInfoBean<T>> list) {
        super(context, list);
        if (recyclerView!=null) {
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    public SelectAdapter<T> setItemLayoutID(int itemLayoutID) {
        this.itemLayoutID = itemLayoutID;
        return this;
    }

    public SelectAdapter<T> setSingle(boolean single) {
        this.single = single;
        return this;
    }

    public SelectAdapter<T> setCanEmpty(boolean canEmpty) {
        this.canEmpty = canEmpty;
        return this;
    }

    public SelectAdapter<T> setSingleSelectedListener(SingleSelectedListener<T> singleSelectedListener) {
        this.singleSelectedListener = singleSelectedListener;
        return this;
    }

    public SelectAdapter<T> setMultipleSelectedListener(MultipleSelectedListener<T> multipleSelectedListener) {
        this.multipleSelectedListener = multipleSelectedListener;
        return this;
    }

    public void notifyAllItem(List<T> data) {
        notifyAllItem(data,-1);
    }
    public void notifyAllItem(List<T> data,int selectPosition) {
        _switchData(data,selectPosition);
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder<SelectAdapter.SelectInfoBean<T>> initHolder(ViewGroup parent, int viewType) {
        return new BaseHolder<SelectAdapter.SelectInfoBean<T>>(context, parent, itemLayoutID) {
            @Override
            public void initView(View itemView, final int position, final SelectAdapter.SelectInfoBean<T> data) {
                super.initView(itemView, position, data);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick(position,data);
                    }
                });
                SelectAdapter.this.initView(itemView, position, data.t, data.select);

            }


        };
    }

    public void onItemClick(int position, SelectInfoBean<T> data){
        boolean currentSelect = data.select;
        if (single) {
            _singleClick(currentSelect, data, position);
        } else {
            _multipClick(currentSelect, data, position);
        }
    }

    private int lastPosition;
    private void _singleClick(boolean currentSelect, SelectInfoBean<T> data, int position) {
        for (SelectInfoBean<T> tSelectInfoBean : list) {
            if (canEmpty) {
                tSelectInfoBean.select = (!tSelectInfoBean.select && tSelectInfoBean == data);
            } else {
                tSelectInfoBean.select = (tSelectInfoBean == data);
            }
        }
        if ((data.select != currentSelect)) {
            notify(position);
            if (singleSelectedListener != null) {
                singleSelectedListener.onSingleSelected(position, data.t, data.select);
            }
        }
        lastPosition=position;
    }

    private void notify(int position) {
        notifyItemChanged(position );
        if (lastPosition!=position&&lastPosition>=0&&lastPosition<list.size()) {
            notifyItemChanged(lastPosition);
        }
        lastPosition=position;
    }

    private void _multipClick(boolean currentSelect, SelectInfoBean<T> data, int position) {
        if (canEmpty) {
            data.select = !data.select;
        } else {
            data.select = !data.select;
            if (_getSelectInfo().size() == 0) {
                data.select = true;
            }
        }
        if ((data.select != currentSelect)) {
            notify(position);
            if (multipleSelectedListener != null) {
                multipleSelectedListener.onMultipleSelected(data.select,position, _getSelectInfo());
            }
        }

    }

    public abstract void initView(View itemView, int position, T t, boolean select);




    private List<T> _getSelectInfo() {
        List<T> resullt = new ArrayList<>();
        if (this.list != null) {
            for (SelectInfoBean<T> tSelectInfoBean : this.list) {
                if (tSelectInfoBean.select) {
                    resullt.add(tSelectInfoBean.t);
                }
            }
        }
        return resullt;
    }



    private void _switchData(List<T> data, int selectPosition) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        } else {
            list.clear();
        }
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                T t=data.get(i);
                SelectInfoBean infoBean = new SelectInfoBean();
                infoBean.t = t;
                infoBean.select=selectPosition==i;
                this.list.add(infoBean);
            }

        }
    }

    public interface SingleSelectedListener<T> {

        /**
         * 单选时候
         *
         * @param position
         * @param selected
         * @param check
         */
        void onSingleSelected(int position, T selected, boolean check);
    }

    public interface MultipleSelectedListener<T> {

        /**
         * 多选时候
         *
         * @param check
         * @param position
         * @param selected
         */
        void onMultipleSelected(boolean check, int position, List<T> selected);

    }

    public static class SelectInfoBean<T> {
        protected boolean select;
        protected T t;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

}
