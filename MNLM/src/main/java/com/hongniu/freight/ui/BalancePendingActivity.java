package com.hongniu.freight.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.net.error.NetException;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.widget.editext.PointLengthFilter;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.AccountDetailBean;
import com.hongniu.freight.entity.PayInforBeans;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.widget.DialogComment;
import com.sang.thirdlibrary.pay.PasswordDialog;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/13
 * @Author PING
 * @Description 余额提现
 */
@Route(path = ArouterParamApp.activity_balance_pending)
public class BalancePendingActivity extends CompanyBaseActivity implements View.OnClickListener, PasswordDialog.OnPasswordDialogListener {

    private TextView tv_blank;//银行卡信息
    private EditText et_count;//提现金额
    private TextView tv_pending_all;//全部提现
    private TextView tv_count;//余额
    private TextView tv_pending;//提现
    private ViewGroup ll_blank;//提现

    private String all = "0";
    private PayInforBeans cardInfo;
    private AccountDetailBean accountInfo;//账户详情
    PasswordDialog passwordDialog;
    String bankID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_balance_pending);
        setWhitToolBar("余额提现");
        initView();
        initData();
        initListener();
        queryCardInfo();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_blank = findViewById(R.id.tv_blank);
        et_count = findViewById(R.id.et_count);
        tv_pending_all = findViewById(R.id.tv_pending_all);
        tv_count = findViewById(R.id.tv_count);
        tv_pending = findViewById(R.id.tv_pending);
        ll_blank = findViewById(R.id.ll_blank);
        passwordDialog = new PasswordDialog(this);
        passwordDialog.setPayDes("提现金额");
    }

    @Override
    protected void initData() {
        super.initData();
        tv_blank.setText(String.format("%s（%s）", "", ""));
        tv_count.setText(String.format("账户余额¥%s", all));
        et_count.setFilters(new InputFilter[]{new PointLengthFilter(2)});
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_pending_all.setOnClickListener(this);
        tv_pending.setOnClickListener(this);
        ll_blank.setOnClickListener(this);
        passwordDialog.setListener(this);
    }

    private void queryCardInfo() {
        Observable.concat(HttpAppFactory.queryAccountDetails(),
                HttpAppFactory.queryMyCards())
                .subscribe(new BaseObserver<CommonBean<? extends Object>>(this) {
                    @Override
                    public void onNext(CommonBean<?> result) {
                        super.onNext(result);
                        if (result.getCode() != 200) {
                            onError(new NetException(result.getCode(), result.getMsg()));
                        } else {
                            Object data = result.getData();
                            if (data != null) {
                                if (data instanceof AccountDetailBean) {
                                    accountInfo = (AccountDetailBean) data;
                                    all = ConvertUtils.changeFloat(accountInfo.getAvailableBalance(), 2);
                                    tv_count.setText(String.format("账户余额¥%s", all));

                                } else if (data instanceof List) {
                                    List<PayInforBeans> payInforBeans = (List<PayInforBeans>) data;
                                    if (!CollectionUtils.isEmpty(payInforBeans)) {
                                        cardInfo = payInforBeans.get(0);
                                        String cardNo = cardInfo.getCardNo();
                                        if (!TextUtils.isEmpty(cardNo) && cardNo.length() > 4) {
                                            cardNo = cardNo.substring(cardNo.length() - 4);
                                        }
                                          bankID = cardInfo.getId();
                                        tv_blank.setText(String.format("%s（%s）", cardInfo.getBankName(), cardNo));
                                    }
                                }
                            }
                        }
                    }


                });


    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_pending_all) {
            et_count.setText(all);
            et_count.setSelection(et_count.getText().toString().length());
        } else if (v.getId() == R.id.tv_pending) {
            ToastUtils.getInstance().show("提现");
            if (accountInfo==null||cardInfo==null){
                queryCardInfo();
            }else {
                if (accountInfo.isSetPassWord()) {
                    final String trim = et_count.getText().toString().trim();
                    passwordDialog.setCount(trim);
                    passwordDialog.show();
                } else {
                    new DialogComment.Builder()
                            .setBtLeft("取消")
                            .setBtRight("去设置")
                            .setDialogTitle(" 使用余额支付前，必须设置木牛流马支付密码")
                            .hideContent()
                            .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                                @Override
                                public void onRightClick(View view, Dialog dialog) {
                                    dialog.dismiss();
                                    ArouterUtils.getInstance().builder(ArouterParamApp.activity_forget_pass)
                                            .withInt(Param.TRAN, 0)
                                            .navigation((Activity) mContext, 2);
                                }
                            })
                            .creatDialog(mContext)
                            .show();
                }
            }
        } else if (v.getId() == R.id.ll_blank) {
             new DialogComment.Builder()
                    .setBtLeft("取消")
                    .setBtRight("确定")
                    .setDialogTitle("确认要解绑银行卡吗？")
                    .hideContent()
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, Dialog dialog) {
                                dialog.dismiss();
                                deletedCard();
                        }
                    })
                    .creatDialog(mContext)
                    .show();
                    ;
        }
    }


    private void deletedCard() {
        if (cardInfo==null){
            queryCardInfo();
            return;
        }
        HttpAppFactory.deleadCard(cardInfo.getId()).subscribe(new NetObserver<Object>(this) {
            @Override
            public void doOnSuccess(Object data) {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show("解绑成功");
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_main).navigation(mContext);
            }
        });
    }

    /**
     * 取消支付
     *
     * @param dialog
     */
    @Override
    public void onCancle(Dialog dialog) {
        dialog.dismiss();
    }

    /**
     * 密码输入完成
     *
     * @param dialog
     * @param count
     * @param passWord
     */
    @Override
    public void onInputPassWordSuccess(Dialog dialog, String count, String passWord) {
        dialog.dismiss();
        //ID应该是服务器返回，不过此时由于只有银行卡，因此写死
        HttpAppFactory.withdraw(count, passWord, bankID)
                .subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(Dialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance().builder(ArouterParamApp.activity_forget_pass)
                .withInt(Param.TRAN, 1)
                .navigation(this, 2);
    }
}
