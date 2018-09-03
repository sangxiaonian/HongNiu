package com.sang.common.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 限制空格的过滤
 */
public class SpaceFilter implements InputFilter {


    public SpaceFilter() {
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        JLog.i(">>>"+source.toString()+">>>");

        // 删除等特殊字符，直接返回
        if (" ".equals(source.toString())) {
            JLog.e(">>>"+source.toString()+">>>");
            return "";
        }else {
            return null;
        }

    }
}
