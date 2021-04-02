package com.fy.androidlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fy.androidlibrary.R;
import com.fy.androidlibrary.utils.JLog;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;


/**
 * 作者：  on 2019/11/22.
 */
public class SearchTitleView extends FrameLayout implements SearchTextWatcher.SearchTextChangeListener, TextView.OnEditorActionListener, View.OnClickListener {

    private String titleHint;
    private final String hintTitleDefault = "请输入搜索内容";
    private EditText etSearch;
    private LinearLayout llSearch;
    private ImageView imgSearch;
    private ImageView imgClear;
    private int hintColor;
    private int titleColor;
    private String title;
    private int searchIcon;
    private int searchClearIcon;
    private int searchGravity;
    private int searchBackGround;
    private View search;
    private boolean searchEditable;
    SearchTextWatcher.SearchTextChangeListener searchTextChangeListener;
    private View view_shadow;
    SearchTextWatcher textWatcher;
    private OnClickListener clickListener;
    OnSearchClickListener onSearchClickListener;
    OnClearClickListener clearClickListener;
    public SearchTitleView(@NonNull Context context) {
        this(context, null);
    }

    public SearchTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }



    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        textWatcher =new SearchTextWatcher(this);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SearchTitleView);
            titleColor = ta.getColor(R.styleable.SearchTitleView_searchTitleColor, Color.parseColor("#333333"));
            hintColor = ta.getColor(R.styleable.SearchTitleView_searchTitleHindColor, Color.parseColor("#999999"));
            title = ta.getString(R.styleable.SearchTitleView_searchTitle);
            titleHint = ta.getString(R.styleable.SearchTitleView_searchTitleHind);
            searchIcon = ta.getResourceId(R.styleable.SearchTitleView_searchIcon, 0);
            searchClearIcon = ta.getResourceId(R.styleable.SearchTitleView_searchClearIcon, 0);
            searchBackGround = ta.getResourceId(R.styleable.SearchTitleView_searchBackGround, 0);
            searchGravity = ta.getInt(R.styleable.SearchTitleView_searchGravity, 0);
            searchEditable = ta.getBoolean(R.styleable.SearchTitleView_searchEditable, true);
            ta.recycle();
        }
        if (titleHint == null) {
            titleHint = hintTitleDefault;
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.search_title_child, this, false);
        etSearch = inflate.findViewById(R.id.tv_title);
        llSearch = inflate.findViewById(R.id.ll_search);
        search = inflate.findViewById(R.id.search);
        imgSearch = inflate.findViewById(R.id.img);
        imgClear = inflate.findViewById(R.id.img_clear);
        view_shadow = inflate.findViewById(R.id.view_shadow);
        addView(inflate);
        setSearchClearIcon(searchClearIcon);
        setSearchIcon(searchIcon);
        setSearchBackGround(searchBackGround);
        setSearchGravity(searchGravity);
        setHintColor(hintColor);
        setTitleHint(titleHint);
        setTitle(title);
        setTitleColor(titleColor);
        setSearchEditable(searchEditable);
        view_shadow.setOnClickListener(this);
        imgClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clearClickListener!=null) {
                    clearClickListener.onClear();
                }else {
                    setTitle("");
                }
            }
        });

    }

    public void setOnClearClickListener(OnClearClickListener clearClickListener) {
        this.clearClickListener = clearClickListener;
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    public void setSearchEditable(boolean searchEditable) {
        this.searchEditable = searchEditable;
        view_shadow.setVisibility(searchEditable?GONE:VISIBLE);
        if (searchEditable) {
            etSearch.addTextChangedListener(textWatcher);
            etSearch.setOnEditorActionListener(this);
        }else {
            etSearch.removeTextChangedListener(textWatcher);
            etSearch.setOnEditorActionListener(null);
        }
    }
    public void setTitleHint(String titleHint) {
        this.titleHint = titleHint;
        etSearch.setHint(titleHint);
    }


    public void setTitle(String title) {
        this.title = TextUtils.isEmpty(title) ? "" : title;
        etSearch.setText(this.title);
        imgClear.setVisibility(TextUtils.isEmpty(title)?GONE:VISIBLE);
    }

    public void setHintColor(int hintColor) {
        this.hintColor = hintColor;
        etSearch.setHintTextColor(hintColor);
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        etSearch.setTextColor(titleColor);
    }

    public void setSearchIcon(int searchIcon) {
        this.searchIcon = searchIcon;
        imgSearch.setImageResource(searchIcon);
        imgSearch.setVisibility(searchIcon == 0 ? GONE : VISIBLE);
    }

    public void setSearchClearIcon(int searchClearIcon) {
        this.searchClearIcon = searchClearIcon;
        imgClear.setImageResource(searchClearIcon);
        imgClear.setVisibility(searchClearIcon == 0 ? GONE : VISIBLE);
    }

    public void setSearchGravity(int searchGravity) {
        this.searchGravity = searchGravity;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) search.getLayoutParams();
        if (params==null){
            params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight=1;
        }
        if (searchGravity==0){
            params.gravity= Gravity.START|Gravity.CENTER_VERTICAL;
        }else {
            params.gravity= Gravity.CENTER;
        }
        search.setLayoutParams(params);
    }

    public void setSearchBackGround(int searchBackGround) {
        this.searchBackGround = searchBackGround;
        llSearch.setBackgroundResource(searchBackGround);
    }

    public void setSearchTextChangeListener(SearchTextWatcher.SearchTextChangeListener searchTextChangeListener) {
        this.searchTextChangeListener = searchTextChangeListener;
    }

    private void clear() {
        setTitle("");
    }

    public String getTitle() {
        return title;
    }

    public void getFocus(){

        etSearch.requestFocus();
    }

    @Override
    public void onSearchTextChange(String msg) {
        if (searchTextChangeListener!=null){
            searchTextChangeListener.onSearchTextChange(msg);
        }
        title=msg;
        imgClear.setVisibility(TextUtils.isEmpty(msg)?GONE:VISIBLE);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            if (onSearchClickListener!=null) {
                onSearchClickListener.onSearch(etSearch.getText().toString().trim());
            }
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) etSearch.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    etSearch.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.clickListener=l;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (clickListener!=null){
            clickListener.onClick(this);
        }
    }

    public interface OnSearchClickListener{
        void onSearch(String msg);
    }
    public interface OnClearClickListener{
        void onClear();
    }
}
