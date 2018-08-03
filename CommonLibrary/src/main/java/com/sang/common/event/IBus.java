package com.sang.common.event;

/**
 * Created by dale on 2017/7/31.
 */

public interface IBus {
    void register(Object object);

    void unregister(Object object);

    void post(IEvent event);

    void postSticky(IEvent event);

    void removeStickyEvent(IEvent event);
    void removeStickyEvent(Class event);


    interface IEvent {
//        int getTag();
    }
}
