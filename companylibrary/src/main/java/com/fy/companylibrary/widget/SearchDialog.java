package com.fy.companylibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.StatusBarUtils;
import com.fy.companylibrary.R;

/**
 * 作者：  on 2019/11/22.
 */
public class SearchDialog extends Dialog implements TextWatcher, View.OnClickListener, TextView.OnEditorActionListener {

    private int sourHeight;

    private Point point;
    private View tragetView;
    private EditText etSearch;
    private ViewGroup viewClear;
    private View tvCancle;

    SearchListener listener;
    private String title;
    private View inflate;
    private int bgColor;

    private boolean showStatusBar = true;
    private ViewGroup ll_bg;


    public SearchDialog(@NonNull Context context) {
        this(context, R.style.dialogTransparent);
    }


    public SearchDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        point = DeviceUtils.getScreenSize(context);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的
        lp.width = point.x; // 宽度设置为屏幕的
        dialogWindow.setAttributes(lp);
        setCanceledOnTouchOutside(true);
        initView(context);

        bgColor = Color.WHITE;

    }


    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }

    void initView(Context context) {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        point = DeviceUtils.getScreenSize(context);
        lp.height = WindowManager.LayoutParams.MATCH_PARENT; // 高度设置为屏幕的
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(R.style.dialog_alpha); //设置窗口弹出动画

        inflate = LayoutInflater.from(context).inflate(R.layout.dialog_search, null);
        etSearch = inflate.findViewById(R.id.et_search);
        ll_bg = inflate.findViewById(R.id.ll_bg);
        viewClear = inflate.findViewById(R.id.fl_clear);
        tvCancle = inflate.findViewById(R.id.tv_cancel);
        etSearch.addTextChangedListener(this);
        tvCancle.setOnClickListener(this);
        viewClear.setOnClickListener(this);
        setContentView(inflate);
        etSearch.setOnEditorActionListener(this);
        View viewById = inflate.findViewById(R.id.view_out);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        viewById.setFocusable(true);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }


    public void show(final View view) {
        tragetView = view;
        if (sourHeight == 0) {
            inflate.setAlpha(0);
        }
        if (!isShowing()) {

            show();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        viewClear.setVisibility(etSearch.getText().toString().length() > 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cancel) {
            dismiss();
        } else if (v.getId() == R.id.fl_clear) {
            etSearch.setText("");
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (listener != null) {
                listener.onSearch(this, etSearch.getText().toString().trim());
            }
            return true;
        }
        return false;
    }

    @Override
    public void dismiss() {
        DeviceUtils.hideSoft(etSearch);
        super.dismiss();

    }

    public void setShowStatusBar(boolean showStatusBar) {
        this.showStatusBar = showStatusBar;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        long delay = 0;
        if (sourHeight == 0) {
            delay = 200;
            sourHeight = inflate.getHeight();
        }
        if (tragetView != null) {
            WindowManager.LayoutParams params = getWindow().getAttributes();
            int[] locations = new int[2];
            tragetView.getLocationInWindow(locations);
            params.y = locations[1] - (showStatusBar ? StatusBarUtils.getStatusBarHeight(getContext()) : 0);
            params.height = sourHeight - params.y;
            params.width = point.x;
            getWindow().setAttributes(params);
        }


        ll_bg.setBackgroundColor(bgColor);
        etSearch.setText(title == null ? "" : title);
        etSearch.requestFocus();
        etSearch.setSelection(etSearch.getText().toString().trim().length());
        etSearch.postDelayed(new Runnable() {
            @Override
            public void run() {
                inflate.setAlpha(1);
                DeviceUtils.openSoft(etSearch);

            }
        }, delay);
    }

    public interface SearchListener {
        void onSearch(Dialog searchDialog, String key);
    }


}
