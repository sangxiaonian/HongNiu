package xiaonian.sang.com.festivitymodule.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.sang.common.imgload.ImageLoader;
import com.sang.common.widget.dialog.inter.DialogControl;

import xiaonian.sang.com.festivitymodule.R;

/**
 * 作者： ${PING} on 2018/7/30.
 * 显示二维码邀请的Dialog
 */

public class ScanDialog implements View.OnClickListener, DialogControl.ICenterDialog {
    TextView tvTitle;
    private View inflate;
    private Dialog dialog;
    private ImageView imgScan;
    private Context context;

    public ScanDialog(@NonNull Context context) {
        initView(context, 0);
    }


    public ScanDialog(@NonNull Context context, int themeResId) {
        initView(context, themeResId);
    }


    private void initView(Context context, int themeResId) {
        this.context = context;
        inflate = LayoutInflater.from(context).inflate(R.layout.festivity_dialog_scan, null);

        tvTitle = inflate.findViewById(R.id.tv_title);
        imgScan = inflate.findViewById(R.id.img_scan);
        dialog = new Dialog(context, themeResId);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(inflate);
        dialog.getWindow().setGravity(Gravity.CENTER);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    }

    public void setBtRight(String btRightString, int btRightColor, final DialogControl.OnButtonRightClickListener rightClickListener) {


    }

    public void setBtLeft(String btLeftString, int btLeftColor, DialogControl.OnButtonLeftClickListener leftClickListener) {

    }

    public void setContent(String content, int contentColor, int contentTextSize) {
        ImageLoader.getLoader().skipMemoryCache().load(context, imgScan, content);
    }

    public void setTitle(String title, int titleColor, int textSize, boolean titleBold) {
        if (tvTitle != null) {
            if (TextUtils.isEmpty(title)) {
                hideTitle(true);
            } else {
                hideTitle(false);
                tvTitle.setText(title);
                if (textSize > 0) {
                    tvTitle.setTextSize(textSize);
                }
                if (titleColor != 0) {
                    tvTitle.setTextColor(titleColor);
                }
                if (titleBold) {
                    tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                } else {
                    tvTitle.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                }
            }
        }
    }

    public void hideTitle(boolean hideTitle) {

    }

    public void hideContent(boolean hideContent) {

    }

    public void hideBtLeft(boolean hideBtLeft) {

    }

    public void hideBtRight(boolean hideBtRight) {

    }

    public void setBtLeftBgRes(int btLeftBgRes) {

    }

    public void setBtRightBgRes(int btRightBgRes) {

    }

    @Override
    public void setbtSize(int btSize) {

    }

    @Override
    public void setDialogSize(int width, int height) {
        if (width != 0 && height != 0) {
//            ViewGroup.LayoutParams params = inflate.getLayoutParams();
//            if (params != null) {
//                params.width = width;
//                params.height = height;
//            } else {
//                params = new ViewGroup.LayoutParams(width, height);
//            }
//            inflate.setLayoutParams(params);
            dialog.getWindow().setLayout(width, height);

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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }


}
