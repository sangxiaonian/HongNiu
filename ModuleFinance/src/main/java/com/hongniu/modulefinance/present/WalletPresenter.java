package com.hongniu.modulefinance.present;

import com.hongniu.modulefinance.control.FinanceWalletControl;
import com.hongniu.modulefinance.mode.FinanceWalletMode;
import com.sang.common.net.listener.TaskControl;

/**
 * 作者： ${PING} on 2018/10/8.
 * 我的钱包
 */
public class WalletPresenter implements FinanceWalletControl.IWalletPresent {
    private FinanceWalletControl.IWalletView view;
    private FinanceWalletControl.IWalletMode mode;

    public WalletPresenter(FinanceWalletControl.IWalletView view) {
        this.view = view;
        mode = new FinanceWalletMode();
    }


    /**
     * 获取余额明细
     *
     * @param listener
     */
    @Override
    public void getBalanceData(TaskControl.OnTaskListener listener) {

    }

    /**
     * 获取到牛贝数据
     *
     * @param listener
     */
    @Override
    public void getNiuData(TaskControl.OnTaskListener listener) {

    }
}
