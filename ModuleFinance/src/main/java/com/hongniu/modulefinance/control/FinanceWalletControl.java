package com.hongniu.modulefinance.control;

import com.fy.androidlibrary.net.listener.TaskControl;

/**
 * 作者： ${PING} on 2018/10/8.
 * 我的钱包界面MVP控制类
 */
public class FinanceWalletControl {
    public interface IWalletView {

    }

    public interface IWalletPresent {

        /**
         * 获取余额明细
         * @param listener
         */
        void getBalanceData(TaskControl.OnTaskListener listener);

        /**
         * 获取到牛贝数据
         * @param listener
         */
        void getNiuData(TaskControl.OnTaskListener listener);
    }

    public interface IWalletMode {

    }


}
