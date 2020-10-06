package com.fy.companylibrary.widget.pop.inter;

import android.app.Dialog;
import android.view.View;



/**
 * 作者： ${PING} on 2018/8/1.
 */
public interface OnFiltrateDismissListener {

    /**
     * Filtrat dimiss 监听
     * @param dialog   当前popu
     * @param target 目标View
     */
    void onFiltrateDismiss(Dialog dialog, View target);

}
