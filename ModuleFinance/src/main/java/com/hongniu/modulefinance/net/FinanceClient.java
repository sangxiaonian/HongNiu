package com.hongniu.modulefinance.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class FinanceClient {
    private final FinanceService financeService;

    private static class InnerLoginClient {
        private static FinanceClient client = new FinanceClient();
    }

    public static FinanceClient getInstance() {
        return InnerLoginClient.client;
    }

    private FinanceClient() {
        financeService = BaseClient.getInstance()
                .creatService(FinanceService.class);
    }

    public FinanceService getService() {
        return financeService;
    }
}
