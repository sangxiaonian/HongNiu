package com.sang.modulebreakbulk.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class BreakbulkClient {
    private final BreabulkService service;

    private static class InnerBreakbulkClient {
        private static BreakbulkClient client = new BreakbulkClient();
    }

    public static BreakbulkClient getInstance() {
        return InnerBreakbulkClient.client;
    }

    private BreakbulkClient() {
        service = BaseClient.getInstance()
                .creatService(BreabulkService.class);
    }

    public BreabulkService getService() {
        return service;
    }
}
