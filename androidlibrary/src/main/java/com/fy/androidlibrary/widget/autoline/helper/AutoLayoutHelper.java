package com.fy.androidlibrary.widget.autoline.helper;

/**
 * 作者：  on 2019/10/31.
 */
public class AutoLayoutHelper {

    private int type;//0自适应宽高，自动换行 1 固定列数，固定宽 2 固定列数，不固定宽度
    private int column  ;//对于固定列数情况
    private int hGap , vGap ;//宽高间隔


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int gethGap() {
        return hGap;
    }

    public void sethGap(int hGap) {
        this.hGap = hGap;
    }

    public int getvGap() {
        return vGap;
    }

    public void setvGap(int vGap) {
        this.vGap = vGap;
    }
}
