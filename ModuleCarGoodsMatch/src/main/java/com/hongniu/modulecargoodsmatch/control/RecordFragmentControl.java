package com.hongniu.modulecargoodsmatch.control;

/**
 * 作者： ${PING} on 2019/7/12.
 */
public class RecordFragmentControl {

    /**
     * 切换筛选条件
     */
    public interface OnSwitchFiltrateListener{
        /**
         * 切换数据
         * @param time
         * @param carType
         */
        void onSwithFiltrate(String time,String carType);
    }
}
