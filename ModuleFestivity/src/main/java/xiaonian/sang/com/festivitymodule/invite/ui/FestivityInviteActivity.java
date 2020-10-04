package xiaonian.sang.com.festivitymodule.invite.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.dialog.ShareDialog;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.toast.ToastUtils;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.share.ShareClient;

import xiaonian.sang.com.festivitymodule.R;
import xiaonian.sang.com.festivitymodule.invite.entity.QueryInvitedInfo;
import xiaonian.sang.com.festivitymodule.net.HttpFestivityFactory;
import xiaonian.sang.com.festivitymodule.widget.ScanDialog;

/**
 * @data 2018/10/17
 * @Author PING
 * @Description 邀请有礼活动
 */
@Route(path = ArouterParamFestivity.activity_festivity_home)
public class FestivityInviteActivity extends BaseActivity implements View.OnClickListener, ShareDialog.OnShareListener {

    private TextView tv_invite_count;//邀请总人数
    private TextView tv_money;//赚取的佣金
    private Button bt_share;//微信分享
    private TextView tv_invite_scan;//二维码邀请
    private TextView tv_invite_rule;//邀请规则
    private QueryInvitedInfo invitedInfor;//个人邀请信息
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festivity_invite);
        setToolbarTitle(getString(R.string.festivity_inveite));
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_invite_count = findViewById(R.id.tv_invite_count);
        tv_money = findViewById(R.id.tv_money);
        bt_share = findViewById(R.id.bt_share);
        tv_invite_scan = findViewById(R.id.tv_invite_scan);
        tv_invite_rule = findViewById(R.id.tv_invite_rule);
        shareDialog = new ShareDialog(this);
    }

    @Override
    protected void initData() {
        super.initData();

        tv_invite_count.setText(getSpanner(0));
        tv_money.setText("0");


        getData();
    }


    private void getData() {
        HttpFestivityFactory.queryInvitedInfor()
                .subscribe(new NetObserver<QueryInvitedInfo>(this) {
                    @Override
                    public void doOnSuccess(QueryInvitedInfo data) {
                        invitedInfor = data;
                        tv_money.setText(data.getRebateTotalAmount()+"个");
                        tv_invite_count.setText(getSpanner(data.getInvitedCount()));
                    }
                });
    }


    @Override
    protected void initListener() {
        super.initListener();
        tv_invite_count.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        bt_share.setOnClickListener(this);
        tv_invite_scan.setOnClickListener(this);
        tv_invite_rule.setOnClickListener(this);
        shareDialog.setShareListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_invite_count) {
//            ToastUtils.getInstance().show("邀请人数");
            ArouterUtils.getInstance().builder(ArouterParamFestivity.activity_festivity_invite_detail).navigation(this);
        } else if (v.getId() == R.id.tv_money) {
//            ToastUtils.getInstance().show("佣金");
            ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_niu).navigation(this);

        } else if (v.getId() == R.id.bt_share) {
//            ToastUtils.getInstance().show("微信分享");
            if (invitedInfor != null) {
                shareDialog.show();
            } else {
                getData();
            }

        } else if (v.getId() == R.id.tv_invite_scan) {
//            ToastUtils.getInstance().show("当面邀请");
            if (invitedInfor != null) {
                showScanDialog(invitedInfor.getInviterName()
                        , invitedInfor.getInvitedQrCodeUrl());
            } else {
                getData();
            }
        } else if (v.getId() == R.id.tv_invite_rule) {
            H5Config h5Config = new H5Config("邀请规则", Param.festivity_invity_notify, true);
            ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(this);

        }
    }


    private SpannableStringBuilder getSpanner(int count) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("您已成功邀请 ");
        int start = builder.length();
        builder.append(count + "");
        int end = builder.length();
        builder.append(" 人");
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.color_light));
        builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }


    private void showScanDialog(String title, String img) {
        new CenterAlertBuilder()
                .setDialogTitle("邀请人：" + title)
                .setDialogContent(img)
                .creatDialog(new ScanDialog(mContext)).show();

    }


    /**
     * 分享到微信好友
     *
     * @param dialog
     */
    @Override
    public void shareSession(DialogControl.IDialog dialog) {
        dialog.dismiss();
        new ShareClient(1).shareUrl(mContext, invitedInfor.getInvitedUrl(), invitedInfor.getTitle(), invitedInfor.getSubtitle(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

    }

    /**
     * 分享到微信朋友圈
     *
     * @param dialog
     */
    @Override
    public void shareTimeLine(DialogControl.IDialog dialog) {
        dialog.dismiss();
        new ShareClient(0).shareUrl(mContext, invitedInfor.getInvitedUrl(), invitedInfor.getTitle(), invitedInfor.getSubtitle(), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

    }
}
