package com.fy.androidlibrary.widget.autoline.helper;

import android.view.View;

import com.fy.androidlibrary.widget.autoline.inter.AutoSingleSelectListener;
import com.fy.androidlibrary.widget.autoline.inter.MultipleSingleSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：  on 2019/10/31.
 */
public abstract class AutoTagAdapter<T> extends AutoAdapter<T> {

    private boolean single;//是否是单选
    private boolean canEmpty;//是否可以选空
    List<SelectInfoBean<T>> infoBeans;
    private List<Integer> selects;
    AutoSingleSelectListener<T> singleSelectedListener;
    private MultipleSingleSelectListener multipleSelectedListener;

    public AutoTagAdapter() {
        infoBeans = new ArrayList<>();
        selects = new ArrayList<>();
    }


    public void setSingle(boolean single) {
        this.single = single;
    }

    public void setCanEmpty(boolean canEmpty) {
        this.canEmpty = canEmpty;
    }


    public void setSingleSelectedListener(AutoSingleSelectListener<T> singleSelectedListener) {
        this.singleSelectedListener = singleSelectedListener;
    }

    public void setMultipleSelectedListener(MultipleSingleSelectListener multipleSelectedListener) {
        this.multipleSelectedListener = multipleSelectedListener;
    }

    @Override
    public void setDatas(List<T> datas) {
        super.setDatas(datas);
        _upDataSelectInfor(datas);
    }


    @Override
    public void onBindView(View rootView, final int position) {
        final SelectInfoBean<T> data = infoBeans.get(position);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean currentSelect = data.select;
                if (single) {
                    _singleClick(currentSelect, data, position);
                } else {
                    _multipClick(currentSelect, data, position);
                }
            }
        });

        initView(rootView, position, datas.get(position), selects.contains(position));

    }

    /**
     * 对数据初始化
     *
     * @param rootView
     * @param position 当前位置
     * @param t        数据
     * @param check    是否选中
     */
    protected abstract void initView(View rootView, int position, T t, boolean check);

    private int lastPosition;

    private void _singleClick(boolean currentSelect, SelectInfoBean<T> data, int position) {
        selects.clear();
        for (SelectInfoBean<T> tSelectInfoBean : infoBeans) {
            if (canEmpty) {
                tSelectInfoBean.select = (!tSelectInfoBean.select && tSelectInfoBean == data);
            } else {
                tSelectInfoBean.select = (tSelectInfoBean == data);
            }
            if (tSelectInfoBean.select) {
                selects.add(position);
            }
        }
        if ((data.select != currentSelect)) {
            notifyItemChange(position);
            if (singleSelectedListener != null) {
                singleSelectedListener.onAutoSingleSelected(position, data.t, data.select);
            }
        }
        lastPosition = position;
    }

    private void _multipClick(boolean currentSelect, SelectInfoBean<T> data, int position) {
        if (canEmpty) {
            data.select = !data.select;
        } else {
            data.select = !data.select;
            if (getSelectInfo().size() == 0) {
                data.select = true;
            }
        }

        if (data.select && !selects.contains(position)) {
            selects.add(position);
        } else if (!data.select) {
            Integer integer = position;
            selects.remove(integer);
        }
        if ((data.select != currentSelect)) {
            notify(position);
            if (multipleSelectedListener != null) {
                multipleSelectedListener.onAutoMultipleSelected(position, getSelectInfo());
            }
        }

    }

    private void notify(int position) {
        notifyItemChange(position);
        if (lastPosition != position && lastPosition >= 0 && lastPosition < infoBeans.size()) {
            notifyItemChange(lastPosition);
        }
        lastPosition = position;
    }

    public List<T> getSelectInfo() {
        List<T> resullt = new ArrayList<>();
        if (infoBeans != null) {
            for (SelectInfoBean<T> tSelectInfoBean : infoBeans) {
                if (tSelectInfoBean.select) {
                    resullt.add(tSelectInfoBean.t);
                }
            }
        }
        return resullt;
    }

    public List<Integer> getSelectPoint() {
        List<Integer> resullt = new ArrayList<>();
        if (infoBeans != null) {
            for (int i = 0; i < infoBeans.size(); i++) {
                SelectInfoBean<T> tSelectInfoBean = infoBeans.get(i);
                if (tSelectInfoBean.select) {
                    resullt.add(i);
                }
            }

        }
        return resullt;
    }


    /**
     * 设置被选中的数据
     */
    public void setSelectsPosition(List<Integer> selectsPositions) {
        this.selects.clear();
        if (selectsPositions != null) {
            this.selects.addAll(selectsPositions);
        }
    }

    /**
     * 设置被选中的数据
     */
    public void setSelectsPosition(int selectsPositions) {
        this.selects.clear();
        this.selects.add(selectsPositions);
    }

    /**
     * 设置被选中的数据
     */
    public void clearSelect() {
        this.selects.clear();

    }


    /**
     * 更新所有数据
     */
    @Override
    public void notifyAllItemChange() {
        _upDataSelectInfor(datas);
        super.notifyAllItemChange();
    }


    private void _upDataSelectInfor(List<T> datas) {
        infoBeans.clear();
        for (int i = 0; i < datas.size(); i++) {
            SelectInfoBean<T> infoBean = new SelectInfoBean<>();
            infoBean.select = selects != null && selects.contains(i);
            infoBean.t = datas.get(i);
            infoBeans.add(infoBean);
        }
    }

    protected static class SelectInfoBean<T> {
        protected boolean select;
        protected T t;
    }


}
