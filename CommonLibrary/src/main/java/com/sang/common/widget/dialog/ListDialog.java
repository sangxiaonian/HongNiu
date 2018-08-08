package com.sang.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sang.common.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.PeakHolder;

import java.util.List;

/**
 * 门店dialog
 */
public class ListDialog<T> {

    protected Context context;
    protected Dialog dialog;
    protected RecyclerView rv;
    protected TextView txt_title;
    protected LinearLayout topView;

    protected Display display;
    protected String textLeft;
    protected String textRight;
    protected String title;
    protected OnBackClickListener backListener;
    /**
     * 设置数据
     */
    protected List<T> datas1;


    protected XAdapter<T> adapter;

    protected TextView tvLeft;
    protected TextView tvRight;


    protected View.OnClickListener leftListener;
    protected View.OnClickListener bottomListener;
    protected onEntryListener<T> rightListener;
    protected View targView;


    public ListDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        builder();
    }

    /**
     * 设置底部点击监听
     *
     * @param bottomListener
     * @return
     */
    public ListDialog<T> setBottomListener(View.OnClickListener bottomListener) {
        this.bottomListener = bottomListener;
        return this;
    }

    /**
     * 初始化布局
     *
     * @return
     */
    public ListDialog<T> builder() {
        // 获取Dialog布局
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_list, null);

        txt_title = (TextView) view.findViewById(R.id.tv_title);
        topView = (LinearLayout) view.findViewById(R.id.rl_title);
        tvLeft = (TextView) view.findViewById(R.id.tv_left);
        tvRight = (TextView) view.findViewById(R.id.tv_right);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        // 定义Dialog布局和参数
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(view);
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (leftListener != null) {
                    leftListener.onClick(v);
                }
            }
        });

        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightListener != null) {
                    rightListener.entryListener();
                }
            }
        });


        setBottomLayout(display.getWidth(), display.getWidth() * 5 / 4);
        return this;
    }

    public ListDialog<T> setShowTitle(boolean showTitle) {
        topView.setVisibility(showTitle ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置左侧按钮点击监听
     *
     * @param leftListener
     * @return
     */
    public ListDialog<T> setLeftListener(View.OnClickListener leftListener) {
        this.leftListener = leftListener;
        return this;
    }

    /**
     * 设置右侧按钮点击监听
     *
     * @param rightListener
     * @return
     */
    public ListDialog<T> setRightListener(onEntryListener<T> rightListener) {
        this.rightListener = rightListener;
        return this;
    }

    /**
     * 设置 dialog 位于屏幕底部，并且设置出入动画
     */
    protected void setBottomLayout(int width, int height) {
        Window win = dialog.getWindow();
        if (win != null) {
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = width;
            lp.height = height;
            win.setAttributes(lp);
            // dialog 布局位于底部
            win.setGravity(Gravity.BOTTOM);
            // 设置进出场动画
        }
    }


    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public ListDialog<T> setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 点击返回键监听
     *
     * @param listener
     * @return
     */
    public ListDialog setBackClickListener(OnBackClickListener listener) {
        this.backListener = listener;
        return this;
    }

    public ListDialog setBtleft(String cancle) {
        this.textLeft = cancle;
        return this;
    }


    public ListDialog<T> setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ListDialog<T> setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置数据
     *
     * @param datas
     * @return
     */
    public ListDialog<T> setDatas(List<T> datas) {
        this.datas1 = datas;
        return this;
    }

    public void show(final View view) {
        this.targView = view;
        if (!TextUtils.isEmpty(title)) {
            txt_title.setText(title);
        }
        if (!TextUtils.isEmpty(textLeft)) {
            tvLeft.setText(textLeft);
        }
        if (!TextUtils.isEmpty(textRight)) {
            tvRight.setText(textRight);
        }



        initRecyclView();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.ACTION_UP == event.getAction()) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (backListener != null) {
                            backListener.onBackPress();
                        }
                    }
                }
                return false;
            }
        });
        dialog.show();


        //重新定义高度
        topView.post(new Runnable() {
            @Override
            public void run() {
                List<PeakHolder> foots = adapter.getFoots();
                int totleHeight = 0;


                if (topView.getVisibility()!=View.GONE){
                    totleHeight+=topView.getHeight();
                }


                if (!foots.isEmpty()) {
                    for (PeakHolder foot : foots) {
                        totleHeight += foot.getItemView().getHeight();
                    }
                }
                List<PeakHolder> heads = adapter.getHeads();
                if (!heads.isEmpty()) {
                    for (PeakHolder foot : heads) {
                        totleHeight += foot.getItemView().getHeight();
                    }
                }
                View view = rv.getChildAt(0);
                if (view!=null){
                   totleHeight+= ( adapter.getItemCount()-heads.size()-foots.size())*view.getHeight();
                }

                Window win = dialog.getWindow();
                if (win != null) {
                    win.getDecorView().setPadding(0, 0, 0, 0);
                    WindowManager.LayoutParams lp = win.getAttributes();
                    lp.height = lp.height > totleHeight ? totleHeight : lp.height;
                    win.setAttributes(lp);
                }
            }
        });


    }


    protected void initRecyclView() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = initAdapter(context, datas1);
        rv.setAdapter(adapter);
    }


    protected XAdapter<T> initAdapter(Context mContext, List<T> datas1) {
        return null;
    }


    public void dimiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();

        }
    }



    public interface OnBackClickListener {
        void onBackPress();
    }

    public interface onEntryListener<T> {
        void entryListener();
    }
}