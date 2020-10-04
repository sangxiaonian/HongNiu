package com.fy.androidlibrary.widget.autoline.helper;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.widget.autoline.AutoLineLayout;
import com.fy.androidlibrary.widget.autoline.inter.IAutoAdapter;

import java.util.List;

/**
 * 作者：  on 2019/10/31.
 */
public abstract class AutoAdapter<T> implements IAutoAdapter<T> {

    protected List<T> datas;
    private AutoLineLayout parent;



    public void setDatas(List<T> datas) {
        this.datas = datas;
    }



    public int getItemCount() {
        return datas.size();
    }

    public abstract View onCreateView(@NonNull ViewGroup parent, int viewType);


    public abstract void onBindView(View rootView, int position);


    public int getItemViewType(int position) {
        return 0;
    }


    /**
     * 更新所有数据
     */
    @Override
    public void notifyAllItemChange() {
        if (parent != null) {
            parent.notifyAllItemChange();
        }
    }

    /**
     * 更新指定位置数据
     *
     * @param position
     */
    @Override
    public void notifyItemChange(int position) {
        if (parent != null) {
            parent.notifyItemChange(position);
        }

    }

    /**
     * 更新指定的位置数据
     *
     * @param positions
     */
    @Override
    public void notifyItemsChange(List<Integer> positions) {
        if (parent != null && positions != null && positions.size() > 0) {
            for (int i = 0; i < positions.size(); i++) {
                notifyItemChange(i);
            }
        }
    }

    /**
     * 更新指定的位置数据
     *
     * @param parent
     */
    @Override
    public void onAttchParent(AutoLineLayout parent) {
        this.parent = parent;
    }


}
