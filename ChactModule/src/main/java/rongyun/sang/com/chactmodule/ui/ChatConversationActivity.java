package rongyun.sang.com.chactmodule.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.hongniu.baselibrary.base.BaseActivity;

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
        if (data != null && !TextUtils.isEmpty(data.getQueryParameter("title"))) {
            setToolbarTitle(data.getQueryParameter("title"));
        } else {
            setToolbarTitle(data.getQueryParameter("聊天"));

        }

    }
}
