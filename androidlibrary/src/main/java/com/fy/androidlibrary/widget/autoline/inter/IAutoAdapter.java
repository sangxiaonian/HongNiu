package com.fy.androidlibrary.widget.autoline.inter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.widget.autoline.AutoLineLayout;

import java.util.List;

/**
 * 作者：  on 2019/10/31.
 */
public interface IAutoAdapter<T> {



    /**
     * 设置数据
     * @param datas
     */
    void setDatas(List<T> datas);


    /**
     * 获取数据数目
     * @return
     */
    int getItemCount();

    /**
     * 创建view
     * @param parent   自动换行控件
     * @param viewType 子控件类型
     * @return
     */
    View onCreateView(@NonNull ViewGroup parent, int viewType);

    /**
     * 绑定数据
     * @param rootView
     * @param position
     */
    void onBindView(View rootView, int position);


    /**
     * 获取指定位置子控件类型
     * @param position 指定位置
     * @return
     */
    int getItemViewType(int position);


   /**
     * 更新所有数据
     */
    void notifyAllItemChange();

    /**
     * 更新指定位置数据
     * @param position
     */
    void notifyItemChange(int position);

    /**
     * 更新指定的位置数据
     * @param positions
     */
    void notifyItemsChange(List<Integer> positions);

   /**
     * 更新指定的位置数据
     * @param parent
     */
    void onAttchParent(AutoLineLayout parent);


}
