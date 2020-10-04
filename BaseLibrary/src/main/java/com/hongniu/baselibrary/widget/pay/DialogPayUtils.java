package com.hongniu.baselibrary.widget.pay;

import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.PayPasswordKeyBord;
import com.hongniu.baselibrary.widget.dialog.PayDialog;
import com.fy.androidlibrary.net.listener.TaskControl;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;

/**
 * 作者：  on 2019/11/3.
 */
public class DialogPayUtils implements PayPasswordKeyBord.PayKeyBordListener, PayDialog.OnClickPayListener, PayDialog.OnClickCancleListener {

    private Context mContext;
    protected PayParam payParam;
    private PayPasswordKeyBord payPasswordKeyBord;
    private PayDialog payDialog;

    //用于支付的类型

    private TaskControl.OnTaskListener listener;
    private String title;//标题
    private String subtitle;//副标题
    private String subtitleDes;
    PayListener payListener;


    public DialogPayUtils(Context mContext) {
        this.mContext = mContext;
        payPasswordKeyBord = new PayPasswordKeyBord((Activity) mContext);
        payDialog = new PayDialog();
        payPasswordKeyBord.sePaytListener(this);
        payDialog.setPayListener(this);
        payDialog.setCancleListener(this);


    }

    public void setPayListener(PayListener payListener) {
        this.payListener = payListener;
    }


    public void setPayParam(PayParam payParam) {
        this.payParam = payParam;
    }


    public void setListener(TaskControl.OnTaskListener listener) {
        this.listener = listener;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setSubtitleDes(String subtitle) {
        this.subtitleDes = subtitle;
    }

    String payCount;

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }



    private void build() {
        payPasswordKeyBord.setPayDes("付款金额");
        if (!TextUtils.isEmpty(payCount)) {
            try {
                payDialog.setPayAmount(Float.parseFloat(payCount));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        payDialog.setTitle(title);
        payDialog.setDescribe(subtitle);
//        payDialog.setDescribeSub(subtitleDes);
        payDialog.setDescribeSub("");
        payPasswordKeyBord.setProgressListener(listener);
    }

    private boolean showCompany = true;

    public void setShowCompany(boolean showCompany) {
        this.showCompany = showCompany;
    }

    public void show(final FragmentManager manager) {
        payParam.setPayPassword(null);
        build();
        HttpAppFactory.queryAccountdetails()
                .subscribe(new NetObserver<WalletDetail>(listener) {
                    @Override
                    public void doOnSuccess(WalletDetail data) {
                        payDialog.setShowCompany(showCompany ? data.getType() : 1);
                        payDialog.setWalletDetaile(data);
                        payDialog.show(manager, "");
                    }
                });

    }


    /**
     * 取消支付
     *
     * @param dialog
     */
    @Override
    public void onCancle(DialogControl.IDialog dialog) {

        dialog.dismiss();
        ToastUtils.getInstance().show("支付已取消");
        if (payListener != null) {
            payListener.canclePay(dialog);
        }

    }

    /**
     * 密码输入完成
     *
     * @param dialog
     * @param count    金额
     * @param passWord 密码
     */
    @Override
    public void onInputPassWordSuccess(DialogControl.IDialog dialog, String count, String passWord) {
        dialog.dismiss();

        if (!TextUtils.isEmpty(passWord)) {
            payParam.setPayPassword(ConvertUtils.MD5(passWord));
        }
        showPayDialog();
    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(DialogControl.IDialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_login_forget_pass)
                .navigation(mContext);
    }

    /**
     * 从未设置过密码
     *
     * @param dialog
     */
    @Override
    public void hasNoPassword(DialogControl.IDialog dialog) {
        Utils.creatDialog(mContext, "使用余额支付前，必须设置泓牛支付密码", null, "取消", "去设置")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ArouterUtils.getInstance()
                                .builder(ArouterParamLogin.activity_login_forget_pass)
                                .withInt(Param.TRAN, 1)
                                .navigation(mContext);
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    /**
     * 点击支付
     *
     * @param amount  支付金额
     * @param payType 1 余额 2微信 3支付宝 4银联
     * @param yueWay  余额支付方式更改监听 0 企业支付 1余额支付 2 申请支付
     */
    @Override
    public void onClickPay(float amount, int payType, int yueWay) {
        payDialog.dismiss();
        payParam.setPayPassword(null);
//        0微信支付 1银联支付 2线下支付3 支付宝支付 4余额支付 5企业支付
        int type = -1;
        switch (payType) {
            case 1:
                if (yueWay == 0 || yueWay == 2) {
                    type = 5;
                } else {
                    type = 4;
                }
                break;
            case 2:
                type = 0;
                break;
            case 3:
                type = 3;
                break;
            case 4:
                type = 1;
                break;
        }
        payParam.setPayType(type);
        if (payType != 1) {
            showPayDialog();
        } else {
            if (Utils.querySetPassword()) {//设置过支付密码
                if (yueWay == 2) {
                    showPayDialog();
                } else {
                    try {
                        payPasswordKeyBord.setPayCount(ConvertUtils.changeFloat(amount, 2));
                        payPasswordKeyBord.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Utils.creatDialog(mContext, "使用余额支付前，必须设置泓牛支付密码", null, "取消", "去设置")
                        .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                            @Override
                            public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                                dialog.dismiss();
                            }
                        })
                        .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                            @Override
                            public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                                dialog.dismiss();
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamLogin.activity_login_forget_pass)
                                        .withInt(Param.TRAN, 1)
                                        .navigation(mContext);
                            }
                        })
                        .creatDialog(new CenterAlertDialog(mContext))
                        .show();
            }
        }

    }


    private void showPayDialog() {
        HttpAppFactory.pay(payParam)
                .subscribe(new NetObserver<PayBean>(listener) {
                    @Override
                    public void doOnSuccess(PayBean data) {
                        if (payListener != null) {
                            payListener.jump2Pay(data, payParam.getPayType(), payParam);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        ToastUtils.getInstance().show("支付失败");
                        if (payListener != null) {
                            payListener.canclePay(null);
                        }
                    }
                });

    }

    /**
     * 点击取消
     */
    @Override
    public void onClicCancle() {
        if (payListener!=null){
            payListener.canclePay(null);
        }
    }

    public interface PayListener {
        void canclePay(DialogControl.IDialog dialog);

        void jump2Pay(PayBean data, int payType, PayParam payParam);
    }


//    /**
//     * @param activity
//     * @param payType    支付方式
//     * @param payBean    支付需要的数据
//     * @param orderId    订单ID
//     * @param havePolicy 是否购买保险
//     * @param isDebug    是否是测试模式
//     * @param queryType  //查询类型 0 订单支付 1支付保证金 2确认收货并支付 3零担预估运费 4零担差额 5
//     */
//    public static void startPay(Activity activity, int payType, PayBean payBean, String orderId, boolean havePolicy, boolean isDebug, int queryType) {
//        Intent intent = new Intent(activity, WaitePayActivity.class);
//        intent.putExtra(PAYTYPE, payType);
//        intent.putExtra(PAYINFOR, payBean);
//        intent.putExtra(ISDEUBG, isDebug);
//        intent.putExtra(ORDERID, orderId);
//        intent.putExtra(HAVEPOLICY, havePolicy);
//        intent.putExtra("queryType", queryType);
//        activity.startActivityForResult(intent, 1);
//    }

}
