package com.hongniu.baselibrary.widget;

import android.content.Context;

import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.sang.common.net.listener.TaskControl;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.LoadDialog;
import com.sang.common.widget.dialog.PasswordDialog;
import com.sang.common.widget.dialog.inter.DialogControl;

import io.reactivex.disposables.Disposable;

/**
 * 作者： ${PING} on 2018/10/26.
 * 支付密码键盘
 */
public class PayPasswordKeyBord implements PasswordDialog.OnPasswordDialogListener, TaskControl.OnTaskListener {

    PasswordDialog passwordDialog;
    private LoadDialog loading;
    protected Disposable disposable;

    PayKeyBordListener payListener;
    TaskControl.OnTaskListener taskListener;

    public PayPasswordKeyBord(Context context) {
        passwordDialog = new PasswordDialog(context);
        passwordDialog.setListener(this);
        if (context instanceof TaskControl.OnTaskListener) {
            taskListener = (TaskControl.OnTaskListener) context;
        }
    }

    public PayPasswordKeyBord sePaytListener(PayKeyBordListener listener) {
        this.payListener = listener;
        return this;
    }

    public PayPasswordKeyBord setProgressListener(TaskControl.OnTaskListener listener) {
        this.taskListener = listener;
        return this;
    }


    public PayPasswordKeyBord setPayCount(String count) {
        passwordDialog.setCount(count);
        return this;
    }

    public void show() {
        if (Utils.querySetPassword()) {
            passwordDialog.show();
        } else {
            HttpAppFactory.queryPayPassword()
                    .subscribe(new NetObserver<QueryPayPassword>(this) {
                        @Override
                        public void doOnSuccess(QueryPayPassword data) {
                            Utils.setPassword(data.isSetPassWord());
                            if (data.isSetPassWord()) {
                                passwordDialog.show();
                            } else {
                                if (payListener != null) {
                                    payListener.hasNoPassword(passwordDialog);
                                }
                            }
                        }
                    });

        }

    }


    /**
     * 取消支付
     *
     * @param dialog
     */
    @Override
    public void onCancle(DialogControl.IDialog dialog) {
        if (payListener != null) {
            payListener.onCancle(dialog);
        } else {
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("取消支付");
        }
    }

    /**
     * 密码输入完成
     *  @param dialog
     * @param count
     * @param passWord
     */
    @Override
    public void onInputPassWordSuccess(DialogControl.IDialog dialog, String count, String passWord) {
        if (payListener != null) {
            payListener.onInputPassWordSuccess(dialog,count, passWord);
        }
    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(DialogControl.IDialog dialog) {
        if (payListener != null) {
            payListener.onForgetPassowrd(dialog);
        }
    }

    @Override
    public void onTaskStart(Disposable d) {
        if (taskListener != null) {
            taskListener.onTaskStart(d);
        }
    }

    @Override
    public void onTaskSuccess() {
        if (taskListener != null) {
            taskListener.onTaskSuccess();
        }
    }


    @Override
    public void onTaskDetail(float present) {
        if (taskListener != null) {
            taskListener.onTaskDetail(present);
        }

    }

    @Override
    public void onTaskFail(Throwable e, String code, String msg) {
        if (taskListener != null) {
            taskListener.onTaskFail(e, code, msg);
        }
    }


    public interface PayKeyBordListener {
        /**
         * 取消支付
         *
         * @param dialog
         */
        void onCancle(DialogControl.IDialog dialog);

        /**
         * 密码输入完成
         *  @param dialog
         * @param count      金额
         * @param passWord   密码
         */
        void onInputPassWordSuccess(DialogControl.IDialog dialog, String count, String passWord);

        /**
         * 忘记密码
         *
         * @param dialog
         */
        void onForgetPassowrd(DialogControl.IDialog dialog);

        /**
         * 从未设置过密码
         *
         * @param dialog
         */
        void hasNoPassword(DialogControl.IDialog dialog);


    }


}
