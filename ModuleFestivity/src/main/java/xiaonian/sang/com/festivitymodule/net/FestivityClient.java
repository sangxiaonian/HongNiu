package xiaonian.sang.com.festivitymodule.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class FestivityClient {
    private final FestivityService service;

    private static class InnerLoginClient {
        private static FestivityClient client = new FestivityClient();
    }

    public static FestivityClient getInstance() {
        return InnerLoginClient.client;
    }

    private FestivityClient() {
        service = BaseClient.getInstance()
                .creatService(FestivityService.class);
    }

    public FestivityService getService() {
        return service;
    }
}
