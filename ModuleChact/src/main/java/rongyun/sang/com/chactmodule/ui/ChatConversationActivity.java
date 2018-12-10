package rongyun.sang.com.chactmodule.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.sang.common.utils.JLog;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.chact.UserInfor;

import rongyun.sang.com.chactmodule.R;

/**
 * 单聊界面
 */
public class ChatConversationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        Uri data = getIntent().getData();
        String title = data.getQueryParameter("title");
        if (data != null && !TextUtils.isEmpty(title)) {
            setToolbarTitle(title);
        } else {
            setToolbarTitle(data.getQueryParameter("聊天"));
        }

        final String userId = data.getQueryParameter("targetId");
        if (userId!=null) {
            HttpAppFactory.queryRongInfor(userId)
                    .subscribe(new NetObserver<UserInfor>(null) {
                        @Override
                        public void doOnSuccess(UserInfor data) {

                            ChactHelper.getHelper().refreshUserInfoCache(userId, data);
                            StringBuilder builder=new StringBuilder();
                            if (!TextUtils.isEmpty(data.getContact())) {
                                builder.append(data.getContact()).append("\t\t");

                            }
                            builder.append(data.getMobile() );
                            final String name = builder.toString();
                            setToolbarTitle(name);
                        }
                    });

        }

        //更改自己的用户名信息
        LoginBean loginInfor = Utils.getLoginInfor();
        if (loginInfor!=null&&loginInfor.getId()!=null) {
            final String id = loginInfor.getId();
            HttpAppFactory.queryRongInfor(loginInfor.getId())
                    .subscribe(new NetObserver<UserInfor>(null) {
                        @Override
                        public void doOnSuccess(UserInfor data) {
                            ChactHelper.getHelper().refreshUserInfoCache(id, data);
                        }
                    });
        }

    }
}
