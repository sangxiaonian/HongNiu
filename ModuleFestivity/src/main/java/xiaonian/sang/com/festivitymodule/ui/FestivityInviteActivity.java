package xiaonian.sang.com.festivitymodule.ui;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.base.BaseActivity;
import com.sang.common.utils.ToastUtils;

import xiaonian.sang.com.festivitymodule.R;

/**
 * @data 2018/10/17
 * @Author PING
 * @Description 邀请有礼
 */
@Route(path = ArouterParamFestivity.activity_festivity_invite)
public class FestivityInviteActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_invite_count;//邀请总人数
    private TextView tv_money;//赚取的佣金
    private Button bt_share;//微信分享
    private TextView tv_invite_scan;//二维码邀请
    private TextView tv_invite_rule;//邀请规则

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

    }

    @Override
    protected void initData() {
        super.initData();

        tv_invite_count.setText(getSpanner(0));
        tv_money.setText("0");
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_invite_count.setOnClickListener(this);
        tv_money.setOnClickListener(this);
        bt_share.setOnClickListener(this);
        tv_invite_scan.setOnClickListener(this);
        tv_invite_rule.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_invite_count) {
            ToastUtils.getInstance().show("邀请人数");
        } else if (v.getId() == R.id.tv_money) {
            ToastUtils.getInstance().show("佣金");

        } else if (v.getId() == R.id.bt_share) {
            ToastUtils.getInstance().show("微信分享");

        } else if (v.getId() == R.id.tv_invite_scan) {
            ToastUtils.getInstance().show("当面邀请");

        }else   if (v.getId() == R.id.tv_invite_rule) {
            ToastUtils.getInstance().show("邀请规则");

        }
    }


    private SpannableStringBuilder getSpanner(int count){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append("您已成功邀请 ");
        int start = builder.length();
        builder.append(count+"");
        int end = builder.length();
        builder.append(" 人");
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.color_light));
        builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }


}
