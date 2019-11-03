package com.hongniu.modulecargoodsmatch.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hedgehog.ratingbar.RatingBar;
import com.hongniu.modulecargoodsmatch.R;
import com.sang.common.utils.ToastUtils;

/**
 * 作者：  on 2019/11/3.
 */
public class DriverDialog {
    protected Context context;
    protected Dialog dialog;
    protected Display display;

    protected EntryClickListener entryClickListener;


    protected View targView;
    RatingBar ratingBar;
    EditText etRemark;
    Button button;
    private String subTitleInfo;
    private TextView tv_describe;
    private View img_cancel;

    public DriverDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        builder();
    }


    public DriverDialog setEntryClickListener(EntryClickListener entryClickListener) {
        this.entryClickListener = entryClickListener;
        return this;
    }

    private int startCount;
    /**
     * 初始化布局
     *
     * @return
     */
    public DriverDialog builder() {
        // 获取Dialog布局
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_driver, null);
        ratingBar = view.findViewById(R.id.progress);
        etRemark = view.findViewById(R.id.et_remark);
        button = view.findViewById(R.id.bt_sum);
        tv_describe = view.findViewById(R.id.tv_describe);
        img_cancel = view.findViewById(R.id.img_cancel);


        img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimiss();
            }
        });

        setSubTitle(subTitleInfo);

        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                startCount= (int) RatingCount;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startCount<=0){
                    ToastUtils.getInstance().show("请先选择星级");
                    return;
                }
                dimiss();
                if (entryClickListener != null) {
                    entryClickListener.OnEntryClick(startCount, etRemark.getText().toString().trim());
                }
            }
        });

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


    public DriverDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public DriverDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }


    public void show(final View view) {
        this.targView = view;
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (KeyEvent.ACTION_UP == event.getAction()) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dimiss();
                    }
                }
                return false;
            }
        });
        dialog.show();

    }


    public void dimiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setSubTitle(String subTitleInfo) {
        this.subTitleInfo=subTitleInfo;
        if (tv_describe!=null&&subTitleInfo!=null){
            tv_describe.setText(subTitleInfo);
        }
    }

    public interface EntryClickListener {
        void OnEntryClick(int rating, String trim);
    }


}
