package com.hongniu.supply.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class AppClient {

    private final AppService service;

    private static class InnerAppClient {
        private static AppClient client = new AppClient();
    }

    public static AppClient getInstance() {
        return InnerAppClient.client;
    }

    private AppClient() {
        service = BaseClient.getInstance()
                .creatService(AppService.class);
    }

    public AppService getService() {
        return service;
    }
}
