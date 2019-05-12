package rongyun.sang.com.chactmodule.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.chact.UserInfor;

import rongyun.sang.com.chactmodule.R;

/**
 * 单聊界面
 */
public class ChatConversationActivity extends BaseActivity {

    private UserInfor userInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        setToolbarSrcRight(R.mipmap.phone);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfor != null) {
                    new CenterAlertBuilder()
                            .setDialogTitle(userInfor.getContact())
                            .setDialogContent(userInfor.getMobile())
                            .setBtLeft("取消")
                            .setBtRight("拨打")
                            .setBtLeftColor( getResources().getColor(R.color.color_007aff))
                            .setBtRightColor( getResources().getColor(R.color.color_007aff))
                            .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                @Override
                                public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                    dialog.dismiss();
                                    CommonUtils.toDial(mContext, userInfor.getMobile());
                                }
                            })
                            .creatDialog(new CenterAlertDialog(mContext))
                            .show();
                }else {
                    ToastUtils.getInstance().show("正在获取信息，请稍后");
                }
            }
        });


        Uri data = getIntent().getData();
        String title = data.getQueryParameter("title");
        if (data != null && !TextUtils.isEmpty(title)) {
            setToolbarTitle(title);
        } else {
            setToolbarTitle(data.getQueryParameter("聊天"));
        }

        final String userId = data.getQueryParameter("targetId");
        if (userId != null) {
            HttpAppFactory.queryRongInfor(userId)
                    .subscribe(new NetObserver<UserInfor>(null) {
                        @Override
                        public void doOnSuccess(UserInfor data) {
                            userInfor = data;
                            ChactHelper.getHelper().refreshUserInfoCache(userId, data);
                            StringBuilder builder = new StringBuilder();
                            if (!TextUtils.isEmpty(data.getContact())) {
                                builder.append(data.getContact()).append("\t\t");

                            }
                            builder.append(data.getMobile());
                            final String name = builder.toString();
                            setToolbarTitle(name);
                        }
                    });

        }

        //更改自己的用户名信息
        LoginBean loginInfor = Utils.getLoginInfor();
        if (loginInfor != null && loginInfor.getId() != null) {
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
