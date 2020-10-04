package com.fy.androidlibrary.widget.load;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.cardview.widget.CardView;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.imgload.loader.glide.GlideApp;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.widget.dialog.BaseFragmentDialog;
import com.fy.androidlibrary.widget.dialog.inter.OnBackPressListener;
import com.fy.androidlibrary.widget.shadow.ShadowLayout;


/**
 * 作者： ${PING} on 2017/10/18.
 */

public class LoadDialog extends Dialog {

    private ImageView img;
    private int res;
    private ShadowLayout parent;


    public LoadDialog(@NonNull Context context) {
        this(context, 0);
    }

    public LoadDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected LoadDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        int size = DeviceUtils.dip2px(context, 65);
        final int parentSize = DeviceUtils.dip2px(context, 80);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        parent = new ShadowLayout(context);
        parent.setmCornerRadius(parentSize / 2);
        FrameLayout.LayoutParams parengParams = new FrameLayout.LayoutParams(parentSize, parentSize);
        parengParams.gravity = Gravity.CENTER;
        parent.setLayoutParams(parengParams);

        img = new ImageView(context);
        img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        CardView.LayoutParams params = new CardView.LayoutParams(size, size);
        params.gravity = Gravity.CENTER;
        img.setLayoutParams(params);
        setContentView(parent);

        parent.addView(img);

        getWindow().setLayout(parentSize, parentSize);
        getWindow().setGravity(Gravity.CENTER);
//        getWindow().setDimAmount(0);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        parent.post(new Runnable() {
            @Override
            public void run() {
                parent.setRightShow(true);
                parent.setLeftShow(true);
                parent.setTopShow(true);
                parent.setBottomShow(true);
                parent.setmShadowColor(Color.parseColor("#2a000000"));
                parent.setmShadowLimit(DeviceUtils.dip2px(getContext(), 5));
                parent.setmCornerRadius(parentSize / 2);
            }
        });

    }

    public LoadDialog setImageLoad(int res) {
        this.res = res;
        if (img != null) {
            GlideApp.with(getContext()).asBitmap().load(res).into(img);
            ImageLoader.getLoader().placeholder(-1).load(getContext(), img, res);
        }
        return this;
    }



}
