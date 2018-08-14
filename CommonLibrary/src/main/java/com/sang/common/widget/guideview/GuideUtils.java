package com.sang.common.widget.guideview;

import android.app.Activity;
import android.view.View;

/**
 * 作者： ${PING} on 2018/2/26.
 */

public class GuideUtils {
    private GuideBuilder builder;
    private Guide guide;
    private View.OnClickListener clickListener;
    private String msg;
    public GuideUtils() {
        builder = new GuideBuilder();
        builder.setAlpha(150)
                .setAutoDismiss(false)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
    }


    public void showGuide(Activity activity, View view) {
        dismiss();
        builder.setTargetView(view);
        MutiComponent component = new MutiComponent();
        component.setTargView(view);
        component.setOnClickListener(clickListener);
        component.setInfor(msg);
        builder.addComponent(component);
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        guide.show(activity);
    }










    public void showBottomGuide(Activity activity, View view) {
        dismiss();
        builder.setTargetView(view);

        MutiBottomComponent component = new MutiBottomComponent();


        component.setTargView(view);

        component.setOnClickListener(clickListener);
        component.setInfor(msg);

        builder.addComponent(component);
        guide = builder.createGuide();

        guide.setShouldCheckLocInWindow(true);
        guide.show(activity);
    }


    public GuideUtils setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 设置高亮区域的形状 0矩形 1圆形
     *
     * @param style
     * @return
     */
    public GuideUtils setHighTargetGraphStyle(int style) {
        builder.setHighTargetGraphStyle(style);
        return this;
    }

    /**
     * 设置高亮的padding
     *
     * @param padding
     * @return
     */
    public GuideUtils setHighTargetPadding(int padding) {
        builder.setHighTargetPadding(padding);
        return this;
    }

    public GuideUtils setOnVisibilityChangedListener(GuideBuilder.OnVisibilityChangedListener listener) {
        builder.setOnVisibilityChangedListener(listener);
        return this;
    }

    public GuideUtils setOnClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
        return this;
    }

    public void dismiss() {
        if (guide != null) {
            guide.dismiss();
        }
    }
}
