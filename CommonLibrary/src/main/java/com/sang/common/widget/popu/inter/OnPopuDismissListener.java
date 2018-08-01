package com.sang.common.widget.popu.inter;

import android.view.View;

import com.sang.common.widget.popu.BasePopu;

/**
 * 作者： ${PING} on 2018/8/1.
 */
public interface OnPopuDismissListener {

    /**
     * Popu dimiss 监听
     * @param popu   当前popu
     * @param target 目标View
     */
    void onPopuDismsss(BasePopu popu, View target);

}
