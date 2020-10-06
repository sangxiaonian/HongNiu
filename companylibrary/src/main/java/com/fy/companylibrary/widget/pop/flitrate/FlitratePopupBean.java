package com.fy.companylibrary.widget.pop.flitrate;

import java.util.List;

public class FlitratePopupBean<T> {
    private List<T> date;//数据
    private String title;//标题
    private boolean showTitle;//标题
    private List<Integer> checks;//被选中的数据
    private List<Integer> sourChecks;//最初始时候被选中数据，用来处理重置按钮，不可更改
    private int itemtype;//类型 0 选择 1输入框
    private int colum;//列数
    private boolean isSingle=true;//是否是单选


    public FlitratePopupBean() {
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public FlitratePopupBean(List<Integer> sourChecks) {
        this.sourChecks = sourChecks;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setSingle(boolean single) {
        isSingle = single;
    }

    public List<T> getDate() {
        return date;
    }

    public void setDate(List<T> date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getChecks() {
        return checks;
    }

    public void setChecks(List<Integer> checks) {
        this.checks = checks;
    }

    public List<Integer> getSourChecks() {
        return sourChecks;
    }


    public int getItemtype() {
        return itemtype;
    }

    public void setItemtype(int itemtype) {
        this.itemtype = itemtype;
    }

    public int getColum() {
        return colum;
    }

    public void setColum(int colum) {
        this.colum = colum;
    }
}