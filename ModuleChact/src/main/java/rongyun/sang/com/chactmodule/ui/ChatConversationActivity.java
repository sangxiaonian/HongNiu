package rongyun.sang.com.chactmodule.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.baselibrary.base.ModuleBaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.fy.androidlibrary.toast.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.chact.UserInfor;

import rongyun.sang.com.chactmodule.R;

/**
 * 泓牛单聊界面
 */
public class ChatConversationActivity extends ModuleBaseActivity {

    private UserInfor userInfor;
    private Uri data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_conversation);
        setToolbarSrcRight(R.mipmap.phone);

        tvToolbarRight.setText("举报");
        tvToolbarRight.setVisibility(View.VISIBLE);
        tvToolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CenterAlertBuilder()
                        .setDialogTitle("举报")
                        .setDialogContent("您确定要举报该用户？")
                        .setBtLeft("取消")
                        .setBtRight("举报")
                        .setBtLeftColor(getResources().getColor(R.color.color_007aff))
                        .setBtRightColor(getResources().getColor(R.color.color_007aff))
                        .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                            @Override
                            public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                dialog.dismiss();
                                ToastUtils.getInstance().show("举报成功，我们将在个工作日进行处理");
                                queryInfo(data);
                            }
                        })
                        .creatDialog(new CenterAlertDialog(mContext))
                        .show();
            }
        });

        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfor != null) {
                    new CenterAlertBuilder()
                            .setDialogTitle(userInfor.getContact())
                            .setDialogContent(userInfor.getMobile())
                            .setBtLeft("取消")
                            .setBtRight("拨打")
                            .setBtLeftColor(getResources().getColor(R.color.color_007aff))
                            .setBtRightColor(getResources().getColor(R.color.color_007aff))
                            .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                                @Override
                                public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                    dialog.dismiss();
                                    CommonUtils.call(mContext, userInfor.getMobile());
                                }
                            })
                            .creatDialog(new CenterAlertDialog(mContext))
                            .show();
                } else {
                    ToastUtils.getInstance().show("正在获取信息，请稍后");
                }
            }
        });


          data = getIntent().getData();
        String title = data.getQueryParameter("title");
        if (data != null && !TextUtils.isEmpty(title)) {
            setToolbarTitle(title);
        } else {
            setToolbarTitle(data.getQueryParameter("聊天"));
        }

        queryInfo(data);


        HttpAppFactory.queryRongInfor(null)
                .subscribe(new NetObserver<UserInfor>(null) {
                    @Override
                    public void doOnSuccess(UserInfor data) {
                        if (data!=null) {
                            ChactHelper.getHelper().refreshUserInfoCache(data.getRongId(), data);
                        }
                    }
                });
    }

    private void queryInfo(Uri data) {
        if (data==null){
            return;
        }
        final String userId = data.getQueryParameter("targetId");
        if (userId != null) {
            HttpAppFactory.queryRongInfor(userId)
                    .subscribe(new NetObserver<UserInfor>(null) {
                        @Override
                        public void doOnSuccess(UserInfor data) {
                            if (data != null) {
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
                        }
                    });

        }
    }
}
