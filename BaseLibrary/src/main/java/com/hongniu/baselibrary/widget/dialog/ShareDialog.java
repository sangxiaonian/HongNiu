package com.hongniu.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;


import com.hongniu.baselibrary.R;
import com.sang.common.widget.dialog.inter.DialogControl;

/**
 * 作者： ${PING} on 2018/7/30.
 */

public class ShareDialog implements View.OnClickListener, DialogControl.IDialog {

    private DialogControl.OnButtonTopClickListener topClickListener;
    private DialogControl.OnButtonBottomClickListener bottomClickListener;
    private Dialog dialog;
    private View llWeiXinFriend;//微信好友
    private View llWeiXinFriendCircle;//微信朋友圈
    OnShareListener shareListener;
    private View tvCancel;

    public ShareDialog(@NonNull Context context) {
        this(context, 0);
    }


    public ShareDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }

    private void initView(Context context, int themeResId) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        llWeiXinFriend = inflate.findViewById(R.id.llWeiXinFriend);
        llWeiXinFriendCircle = inflate.findViewById(R.id.llWeiXinFriendCircle);
        tvCancel = inflate.findViewById(R.id.txt_cancel);
        llWeiXinFriend.setOnClickListener(this);
        llWeiXinFriendCircle.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));


    }

    public void setShareListener(OnShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public interface OnShareListener {
        /**
         * 分享到微信好友
         */
        void shareSession(DialogControl.IDialog dialog);

        /**
         * 分享到微信朋友圈
         *
         * @param dialog
         */
        void shareTimeLine(DialogControl.IDialog dialog);
    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.llWeiXinFriendCircle) {
            if (shareListener!=null) {
                shareListener.shareTimeLine(this);
            }
        } else if (i == R.id.llWeiXinFriend) {
            if (shareListener!=null) {
                shareListener.shareSession(this);
            }
        }else if (i==R.id.txt_cancel){
            dialog.dismiss();
        }

    }


    @Override
    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

}
