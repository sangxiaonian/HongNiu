package com.hongniu.moduleorder.utils;

import com.amap.api.trace.TraceLocation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 作者： ${桑小年} on 2018/8/22.
 * 努力，为梦长留
 */
public class LoactionCellection {

    private static LoactionCellection cellection;

    public static LoactionCellection getInstance() {
        if (cellection == null) {
            cellection = new LoactionCellection();
        }
        return cellection;
    }

    private List<TraceLocation> list = new LinkedList<>();

    public List<TraceLocation> getList() {
        return list;
    }
}
