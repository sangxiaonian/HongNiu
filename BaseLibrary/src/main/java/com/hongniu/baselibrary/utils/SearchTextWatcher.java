package com.hongniu.baselibrary.utils;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * 作者：  on 2019/11/2.
 */
public class SearchTextWatcher implements TextWatcher {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            removeMessages(0);
            if (searchTextChangeListener!=null){
                searchTextChangeListener.onSearchTextChange((String) msg.obj);
            }

        }
    };
    SearchTextChangeListener searchTextChangeListener;

    public SearchTextWatcher(SearchTextChangeListener searchTextChangeListener) {
        this.searchTextChangeListener = searchTextChangeListener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        handler.removeMessages(0);
        Message obtain = Message.obtain();
        obtain.obj=s.toString().trim();
        handler.sendMessageDelayed(obtain,200);
    }

    public  interface SearchTextChangeListener{
        void onSearchTextChange(String msg);
    }
}
