package com.sang.common.widget.guideview;

import android.app.Activity;
import android.view.View;

import com.sang.common.R;
import com.sang.common.utils.SharedPreferencesUtils;


/**
 * 作者： ${PING} on 2018/2/26.
 */

public class BaseGuide {
    private GuideBuilder builder;
    private Guide guide;
    private String msg;
    private View view;
    private Activity activity;
    private BaseGuide nextGuide;
    private View.OnClickListener clickListener;
    private String sharedPreferencesKey;

    private int layoutID= R.layout.item_guide;

    private boolean showTop;

    public BaseGuide() {
        builder = new GuideBuilder();
        builder.setAlpha(150)
                .setAutoDismiss(false)
                .setHighTargetCorner(20)
                .setHighTargetPadding(10)
                .setOverlayTarget(false)
                .setOutsideTouchable(false);
    }


    public BaseGuide setLayoutID(int layoutID) {
        this.layoutID = layoutID;
        return this;
    }

    public void showBottmeGuide(){
        dismiss();
        builder.setTargetView(view);
        MutiBottomComponent component = new MutiBottomComponent();
        dealGuide(component);
    }

    private void dealGuide(MutiComponent component) {
        if (layoutID>0){
            component.setLayoutID(layoutID);
        }
        component.setTargView(view);
        component.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nextGuide!=null) {
                    nextGuide.showGuide();
                }
                SharedPreferencesUtils.getInstance().putBoolean(sharedPreferencesKey,true);
                dismiss();
            }
        });
        component.setInfor(msg);
        builder.addComponent(component);
        guide = builder.createGuide();
        guide.setShouldCheckLocInWindow(true);
        if ( !SharedPreferencesUtils.getInstance().getBoolean(sharedPreferencesKey)) {
            if (view!=null){
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        guide.show(activity);

                    }
                });
            }
        }else {
            if (nextGuide!=null) {
                nextGuide.showBottmeGuide();
            }
        }
    }

    public void showGuide(){
        if (showTop){
            showTopGuide();
        }else {
            showBottmeGuide();
        }
    }

    public BaseGuide setShowTop(boolean showTop) {
        this.showTop = showTop;
        return this;
    }

    public void showTopGuide() {
        dismiss();
        builder.setTargetView(view);
        MutiComponent component = new MutiComponent();
        dealGuide(component);
    }


    public BaseGuide setView(View view) {
        this.view = view;
        return this;
    }

    public BaseGuide setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public BaseGuide setNextGuide(BaseGuide nextGuide) {
        this.nextGuide = nextGuide;
        return this;
    }

    public BaseGuide setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    /**
     * 设置高亮区域的形状 0矩形 1圆形
     *
     * @param style
     * @return
     */
    public BaseGuide setHighTargetGraphStyle(int style) {
        builder.setHighTargetGraphStyle(style);
        return this;
    }

    /**
     * 设置高亮的padding
     *
     * @param padding
     * @return
     */
    public BaseGuide setHighTargetPadding(int padding) {
        builder.setHighTargetPadding(padding);
        return this;
    }

    public BaseGuide setOnVisibilityChangedListener(GuideBuilder.OnVisibilityChangedListener listener) {
        builder.setOnVisibilityChangedListener(listener);
        return this;
    }

    public BaseGuide setOnClickListener(View.OnClickListener listener) {
        this.clickListener = listener;
        return this;
    }

    public void dismiss() {
        if (guide != null) {
            guide.dismiss();
        }
    }

    /**
     * 设置是否显示当前导航的key
     * @param sharedPreferencesKey
     * @return
     */
    public BaseGuide setSharedPreferencesKey(String sharedPreferencesKey) {
        this.sharedPreferencesKey = sharedPreferencesKey;
        return this;
    }
}
