package com.fy.androidlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.fy.androidlibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 作者：  on 2019/11/1.
 */
public class SwitchTextLayoutParent extends LinearLayout implements SwitchTextLayout.OnSwitchListener {


    private int openColor;
    private int openIcon;
    private int closeIcon;
    private int closeColor;
    private float textSize;
    SwitchListener listener;
    //当前被手动选中的数据，这些数据会一直保持红色
    private List<SwitchTextLayout> checkPositions=new ArrayList<>();
    private Map<SwitchTextLayout,String> defaultTitls=new HashMap<>();



    public SwitchTextLayoutParent(Context context) {
        this(context,null);
    }

    public SwitchTextLayoutParent(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchTextLayoutParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchTextLayout);
            textSize = typedArray.getDimension(R.styleable.SwitchTextLayout_titleSize, 0);
            openColor = typedArray.getColor(R.styleable.SwitchTextLayout_openColor, openColor);
            closeColor = typedArray.getColor(R.styleable.SwitchTextLayout_closeColor, closeColor);
            closeIcon = typedArray.getResourceId(R.styleable.SwitchTextLayout_closeIcon, closeIcon);
            openIcon = typedArray.getResourceId(R.styleable.SwitchTextLayout_openIcon, openIcon);
            typedArray.recycle();
        }
    }

    public void setListener(SwitchListener listener) {
        this.listener = listener;
    }

    private void addSwitchChild(String title, int openColor, int closeColor, int openIcon, int closeIcon, float titleSize){
        SwitchTextLayout textLayout=new SwitchTextLayout(getContext());
        LinearLayout.LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        params.weight=1;
        params.width=0;
        textLayout.setLayoutParams(params);
        textLayout.setTitle(title==null?"":title);
        if (titleSize>0) {
            textLayout.setTextSize(titleSize);
        }
        textLayout.setOpenColor(openColor);
        textLayout.setCloseColor(closeColor);
        textLayout.setOpenIcon(openIcon);
        textLayout.setCloseIcon(closeIcon);
        textLayout.closeSwitch();
        textLayout.setCheck(false);
        textLayout.setListener(this);
        addView(textLayout);
        defaultTitls.put(textLayout,title);
    }


    public void addSwitchChild(String title){
        addSwitchChild(title,openColor,closeColor,openIcon,closeIcon,textSize);
    }

    public void setTitle(int index,String title){
        View child = getChildAt(index);
        if (child instanceof SwitchTextLayout){
            ((SwitchTextLayout) child).setTitle(title==null?"":title);
        }
    }

    public void setOpenColor(int openColor) {
        this.openColor = openColor;
    }

    public void setOpenIcon(int openIcon) {
        this.openIcon = openIcon;
    }

    public void setCloseIcon(int closeIcon) {
        this.closeIcon = closeIcon;
    }

    public void setCloseColor(int closeColor) {
        this.closeColor = closeColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof SwitchTextLayout){
                if (child!=switchTextLayout) {
                    boolean check = checkPositions.contains(child);
                   ((SwitchTextLayout) child).closeSwitch();
                    ((SwitchTextLayout) child).setCheck(check);
                }else {
                    ((SwitchTextLayout) child).openSwitch();
                    ((SwitchTextLayout) child).setCheck(true);
                    if (listener!=null){
                        listener.onSwitch((SwitchTextLayout) child,i,true);
                    }
                }
            }
        }
    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
            switchTextLayout.setCheck(checkPositions.contains(switchTextLayout));


        if (listener!=null){
            listener.onSwitch(switchTextLayout,checkPositions.indexOf(switchTextLayout),false);
        }
    }




    public void close(){
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof SwitchTextLayout&&((SwitchTextLayout) child).isOpen()){
                ((SwitchTextLayout) child).setCheck(checkPositions.contains(child));
                ((SwitchTextLayout) child).closeSwitch();
            }
        }
    }


    public void switchCheck(int index,String title){
        if (TextUtils.isEmpty(title)){
            remoceCheckPosition(index);
        }else {
            addCheckPosition(index,title);
        }
    }

    public void addCheckPosition(int index){
        addCheckPosition(index,null);
    }
    public void addCheckPosition(int index,String title){
        View child = getChildAt(index);
        if (child instanceof SwitchTextLayout) {
            ((SwitchTextLayout) child).setTitle(TextUtils.isEmpty(title)?defaultTitls.get(child):title);
            if (!checkPositions.contains(child)) {
                checkPositions.add((SwitchTextLayout) child);
            }
        }
        _upAllView();
    }

    public void remoceCheckPosition(int index){
        View child = getChildAt(index);
        if (child instanceof SwitchTextLayout) {
            ((SwitchTextLayout) child).setTitle(defaultTitls.get(child));

            if (checkPositions.contains(child)) {
                checkPositions.remove((SwitchTextLayout) child);
            }
        }
        _upAllView();
    }


    public void _upAllView(){
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof SwitchTextLayout){
                boolean check = checkPositions.contains(view);
                ((SwitchTextLayout) view).setCheck(check);
            }
        }
    }


    public interface SwitchListener{
        /**
         * 点击标签进行切换时候的动作监听，仅仅监听当前被点击主动改变的标签
         * @param textLayout 标签控件
         * @param position   下表
         * @param open      是否是打开 true 点击打开 false 点击关闭
         */
        void onSwitch(SwitchTextLayout textLayout,int position,boolean open);
    }



}
