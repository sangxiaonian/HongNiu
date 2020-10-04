package com.fy.androidlibrary.widget.autoline.inter;

import android.view.View;

import java.util.List;

/**
 * 作者：  on 2019/10/31.
 */
public interface MultipleSingleSelectListener<T> {



    <T> void onAutoMultipleSelected(int position, List<T> getSelectInfo);
}
