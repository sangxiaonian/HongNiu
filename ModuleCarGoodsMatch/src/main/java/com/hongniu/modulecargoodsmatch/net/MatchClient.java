package com.hongniu.modulecargoodsmatch.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class MatchClient {
    private final MatchService service;

    private static class InnerLoginClient {
        private static MatchClient client = new MatchClient();
    }

    public static MatchClient getInstance() {
        return InnerLoginClient.client;
    }

    private MatchClient() {
        service = BaseClient.getInstance()
                .creatService(MatchService.class);
    }

    public MatchService getService() {
        return service;
    }
}
