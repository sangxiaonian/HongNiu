package com.hongniu.supply.weight;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.supply.R;


/**
 * description:
 */

public class RuleAlertDialog extends Dialog {


    private TextView titleTv;
    private Button negtiveBn, positiveBn;
    private TextView contentTv;

    private int typeView = 0; //0 初试界面  1错误界面

    public RuleAlertDialog(Context context, IDialog callBack) {
        super(context, R.style.dialog);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_alert_rule);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = findViewById(R.id.negtive);
        positiveBn = findViewById(R.id.positive);
        titleTv = findViewById(R.id.title);
        contentTv = findViewById(R.id.content);
    }


    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeView == 1) {
                    typeView = 0;
                    dismiss();
                    if (callBack != null) {
                        callBack.onClickReportAlert(false);
                    }
                } else {
                    if (callBack != null) {
                        callBack.onClickReportAlert(true);
                    }
                    dismiss();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeView == 0) {
                    typeView = 1;
                    setErrorView();
                }
            }
        });
    }


    @Override
    public void show() {
        super.show();
        refreshView();
    }


    private void refreshView() {
        titleTv.setVisibility(View.VISIBLE);
        contentTv.setVisibility(View.VISIBLE);
        negtiveBn.setVisibility(View.VISIBLE);
        titleTv.setText("欢迎使用泓牛");
        positiveBn.setText("同意");
        negtiveBn.setText("不同意");


        final SpannableStringBuilder style = new SpannableStringBuilder();
        //设置文字
        String str1 = "泓牛非常重视您的隐私保护和个人信息保护，在您使用泓牛APP前，请认真阅读以下条款：";
        String str2 = "《隐私权政策》";
        String str3 = "和";
        String str4 = "《用户协议》";
        String str5 = "。如同意以上条款，请点击“同意”开始接受我们的服务。";

        int start1 = str1.length();
        int end1 = start1 + str2.length();
        int start2 = end1 + str3.length();
        int end2 = start2 + str4.length();

        style.append(str1);
        style.append(str2);
        style.append(str3);
        style.append(str4);
        style.append(str5);


//        //设置部分文字点击事件
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                H5Config h5Config = new H5Config("隐私权政策", Param.privacy, false);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(getContext());

            }

            //去除连接下划线
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                H5Config h5Config = new H5Config("用户协议", Param.hongniu_agreement, true);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(getContext());

            }

            //去除连接下划线
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        };
        style.setSpan(clickableSpan1, start1, end1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(clickableSpan2, start2, end2, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(Color.parseColor("#FC5151"));
        style.setSpan(foregroundColorSpan1, start1, end1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(Color.parseColor("#FC5151"));
        style.setSpan(foregroundColorSpan2, start2, end2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        //设置字体大小
//        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(20);
//        style.setSpan(absoluteSizeSpan, 0, style.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        contentTv.setMovementMethod(LinkMovementMethod.getInstance());
        contentTv.setText(style);
        contentTv.setHighlightColor(contentTv.getContext().getResources().getColor(android.R.color.transparent));

    }


    private void setErrorView() {
        titleTv.setVisibility(View.VISIBLE);
        contentTv.setVisibility(View.GONE);
        negtiveBn.setVisibility(View.GONE);
        titleTv.setText("抱歉，您在同意隐私权政策与用户协议后方可继续使用本软件");
        positiveBn.setText("确定");
    }


    //-------------回调接口
    public interface IDialog {
        void onClickReportAlert(boolean isPositive);
    }

    IDialog callBack;

}
