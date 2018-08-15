package com.hongniu.modulelogin.net;

import com.hongniu.baselibrary.net.BaseClient;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class LoginClient {

    private final LoginService loginService;

    private static class InnerLoginClient {
        private static LoginClient client = new LoginClient();
    }

    public static LoginClient getInstance() {
        return InnerLoginClient.client;
    }

    private LoginClient() {
        loginService = BaseClient.getInstance()
                .creatService(LoginService.class);
    }

    public LoginService getLoginService() {
        return loginService;
    }
}
