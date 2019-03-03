package com.sang.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sang.common.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.List;

/**
 *
 */
public abstract class ListBottomDialog<T> {

    protected Context context;
    protected Dialog dialog;
    protected RecyclerView rv;
    protected Display display;
    protected OnBackClickListener backListener;

    protected DialogControl.OnEntryClickListener<T> entryClickListener;
    protected View.OnClickListener bottomListener;

    /**
     * 设置数据
     */
    protected List<T> datas1;
    protected XAdapter<T> adapter;
    protected View targView;


    public ListBottomDialog(Context context) {
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
    public ListBottomDialog<T> setBottomListener(View.OnClickListener bottomListener) {
        this.bottomListener = bottomListener;
        return this;
    }

    public ListBottomDialog<T> setEntryClickListener(DialogControl.OnEntryClickListener<T> entryClickListener) {
        this.entryClickListener = entryClickListener;
        return this;
    }

    /**
     * 初始化布局
     *
     * @return
     */
    public ListBottomDialog<T> builder() {
        // 获取Dialog布局
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_list, null);

        rv = (RecyclerView) view.findViewById(R.id.rv);
        // 定义Dialog布局和参数
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setWindowAnimations(R.style.dialog_ani);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(view);
        setBottomLayout(display.getWidth(), display.getWidth() * 5 / 4);
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
     * 点击返回键监听
     *
     * @param listener
     * @return
     */
    public ListBottomDialog setBackClickListener(OnBackClickListener listener) {
        this.backListener = listener;
        return this;
    }




    public ListBottomDialog<T> setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ListBottomDialog<T> setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置数据
     *
     * @param datas
     * @return
     */
    public ListBottomDialog<T> setDatas(List<T> datas) {
        this.datas1 = datas;
        return this;
    }

    public void show(final View view) {
        this.targView = view;

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
        rv .post(new Runnable() {
            @Override
            public void run() {
                List<PeakHolder> foots = adapter.getFoots();
                int totleHeight = 0;
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
        adapter.addFoot(getCancleHolder(context,rv));
        rv.setAdapter(adapter);
    }


    protected XAdapter<T> initAdapter(Context mContext, List<T> datas1) {
        return new XAdapter<T>(mContext,datas1) {
            @Override
            public BaseHolder<T> initHolder(ViewGroup parent, int viewType) {
                return getBaseHolder(context,parent);
            }
        };
    }

    public abstract BaseHolder<T> getBaseHolder(Context context, ViewGroup parent);



    public void dimiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public abstract PeakHolder getCancleHolder(Context context, ViewGroup parent);


    public interface OnBackClickListener {
        void onBackPress();
    }


}