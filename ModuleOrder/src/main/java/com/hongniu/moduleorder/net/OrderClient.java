package com.hongniu.moduleorder.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class OrderClient {
    private final OrderService loginService;

    private static class InnerLoginClient {
        private static OrderClient client = new OrderClient();
    }

    public static OrderClient getInstance() {
        return InnerLoginClient.client;
    }

    private OrderClient() {
        loginService = BaseClient.getInstance()
                .creatService(OrderService.class);
    }

    public OrderService getService() {
        return loginService;
    }
}
