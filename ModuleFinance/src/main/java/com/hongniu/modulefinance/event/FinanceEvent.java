package com.hongniu.modulefinance.event;

import com.fy.androidlibrary.event.IBus;

import java.util.Date;

/**
 * 作者： ${PING} on 2018/8/15.
 */
public class FinanceEvent {
    public static class SelectMonthEvent implements IBus.IEvent{
        public Date date;

        public SelectMonthEvent(Date data) {
            this.date=data;
        }
    }


}
