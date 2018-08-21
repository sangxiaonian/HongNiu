package com.sang.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import com.sang.common.R;
import com.sang.common.imgload.ImageLoader;


/**
 * 作者： ${PING} on 2017/10/18.
 */

public class LoadDialog extends Dialog {

    private ImageView img;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading);
        View view = getWindow().getDecorView();
        img =  view.findViewById(R.id.iv);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        setCancelable(false);
        setCanceledOnTouchOutside(false);

    }

    public LoadDialog setImageLoad(int res) {
        ImageLoader.getLoader().load(getContext(), img, res);
        return this;

    }

}
