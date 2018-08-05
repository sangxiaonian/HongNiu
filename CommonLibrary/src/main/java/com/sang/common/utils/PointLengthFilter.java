package com.sang.common.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by jary on 2016/11/3.
 * 设置小数位数控制
 */
public class PointLengthFilter implements InputFilter {

    /**
     * 输入框小数的位数  示例保留一位小数
     */
    private static final int DECIMAL_DIGITS = 2;

    private int point=DECIMAL_DIGITS;

    public PointLengthFilter(int point) {
        this.point = point;
    }

    public PointLengthFilter() {
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        // 删除等特殊字符，直接返回
        if ("".equals(source.toString())) {
            return null;
        }
        String dValue = dest.toString();


        //过滤多个小数点的情况
        if (dValue.contains(".")&&".".equals(source.toString())){
            return source.subSequence(start, end - 1);
        }

        String[] splitArray = dValue.split("\\.");
        if (splitArray.length > 1) {
            String dotValue = splitArray[1];
            int diff = dotValue.length() + 1 - point;
            if (diff > 0) {
                return source.subSequence(start, end - diff);
            }
        }
        return null;
    }
}
