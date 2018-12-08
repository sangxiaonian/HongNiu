package rongyun.sang.com.chactmodule.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.net.HttpAppFactory;
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
    }
}
