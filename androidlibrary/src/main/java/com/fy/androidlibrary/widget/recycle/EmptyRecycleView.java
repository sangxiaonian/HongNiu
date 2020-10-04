package com.fy.androidlibrary.widget.recycle;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.recycle.utils.XAdapterDataObserver;

/**
 * 作者：  on 2019/11/26.
 */
public class EmptyRecycleView extends RecyclerView {
    public EmptyRecycleView(@NonNull Context context) {
        this(context,null);
    }

    public EmptyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs, defStyle);
    }

    private void initView(Context context, AttributeSet attrs, int defStyle) {

    }

    private PeakHolder emptyHolder;

    @Override
    public void setAdapter(@Nullable final Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof XAdapter) {
            adapter.registerAdapterDataObserver(new XAdapterDataObserver(){
                @Override
                public void onChanged() {
                    super.onChanged();
                    if (emptyHolder !=null ) {
                        if (((XAdapter) adapter).getItemSize()>0){
                            ((XAdapter) adapter).removeFoot(emptyHolder);
                        }else if (!((XAdapter) adapter).getFoots().contains(emptyHolder)){
                            ((XAdapter) adapter).addFoot(emptyHolder);
                        }
                    }
                }
            });
        }
    }

    public void setEmptyHolder(PeakHolder emptyHolder) {
        this.emptyHolder = emptyHolder;
    }
}
