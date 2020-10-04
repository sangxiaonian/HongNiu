package com.sang.common.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.sang.common.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.EmptyHolder;
import com.fy.androidlibrary.utils.JLog;

/**
 * 作者： ${桑小年} on 2018/8/18.
 * 努力，为梦长留
 * 支持空界面的recycyleView
 */
public class RecycleViewSupportEmpty extends RecyclerView {

    public EmptyHolder emptyHolder;
    private XAdapter adapter;
    private String msg;
    private int ImgSrc;
    private View emptyView;//由外部直接传入的数据为空时候的界面，优先于holder


    public RecycleViewSupportEmpty(Context context) {
        this(context, null, 0);
    }

    public RecycleViewSupportEmpty(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecycleViewSupportEmpty(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RecycleViewSupportEmpty);
            msg = typedArray.getString(R.styleable.RecycleViewSupportEmpty_empty_title);
            ImgSrc = typedArray.getResourceId(R.styleable.RecycleViewSupportEmpty_empty_img, 0);
            typedArray.recycle();
        }

    }


    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    /**
     * 创建一个观察者
     * 为什么要在onChanged里面写？
     * 因为每次notifyDataChanged的时候，系统都会调用这个观察者的onChange函数
     * 我们大可以在这个观察者这里判断我们的逻辑，就是显示隐藏
     */
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            //这种写发跟之前我们之前看到的ListView的是一样的，判断数据为空否，在进行显示或者隐藏
            if (adapter != null && emptyHolder != null) {
                if (emptyView==null&&emptyHolder!=null) {
                    emptyHolder.setEmptyInfor(ImgSrc, msg);
                    if (!adapter.getHeads().contains(emptyHolder) && adapter.getItemCount() - adapter.getHeads().size() - adapter.getFoots().size() == 0) {
                        if (ImgSrc!=0&&!TextUtils.isEmpty(msg)) {
                            adapter.addHeard(0, emptyHolder);
                        }
                    } else if (adapter.getHeads().contains(emptyHolder)){
                        if (adapter.getItemCount() - adapter.getHeads().size() - adapter.getFoots().size() != 0) {
                            adapter.removeHeard(emptyHolder);
                        }
                    }
                }else if (emptyView!=null){
                    if (adapter.getItemCount() - adapter.getHeads().size() - adapter.getFoots().size() == 0) {
                        emptyView.setVisibility(VISIBLE);
                        setVisibility(GONE);
                    } else {
                        emptyView.setVisibility(GONE);
                        setVisibility(VISIBLE);
                    }
                }

            }

        }
    };


    public void setEmptyView(int ImgSrc, String msg) {
        this.ImgSrc = ImgSrc;
        this.msg = msg;
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        //当setAdapter的时候也调一次
        if (adapter instanceof XAdapter) {
            this.adapter = (XAdapter) adapter;
        }
        emptyHolder = new EmptyHolder(getContext(), this, R.layout.holder_empty);
        if (adapter != null) {
            //这里用了观察者模式，同时把这个观察者添加进去，
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();

    }


}
