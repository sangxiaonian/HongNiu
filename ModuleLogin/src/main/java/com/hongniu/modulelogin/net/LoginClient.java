package com.hongniu.modulelogin.net;

import com.sang.common.net.HttpClient;

/**
 * 作者： ${PING} on 2018/8/13.
 */
public class LoginClient {
    private final LoginService loginService;

    private static class InnerLoginClient{
        private static LoginClient client=new LoginClient();
    }

    public static LoginClient getInstance(){
        return InnerLoginClient.client;
    }

    private LoginClient() {
          loginService = HttpClient.getClient().creatService(LoginService.class);
    }

    public LoginService getLoginService() {
        return loginService;
    }
}
