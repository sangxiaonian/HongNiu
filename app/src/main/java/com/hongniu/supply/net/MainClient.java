package com.hongniu.supply.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class MainClient {
    private final MainService service;

    private static class InnerLoginClient {
        private static MainClient client = new MainClient();
    }

    public static MainClient getInstance() {
        return InnerLoginClient.client;
    }

    private MainClient() {
        service = BaseClient.getInstance()
                .creatService(MainService.class);
    }

    public MainService getService() {
        return service;
    }
}
