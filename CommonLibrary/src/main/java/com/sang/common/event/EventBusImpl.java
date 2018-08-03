package com.sang.common.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dale on 2017/7/31.
 */

public class EventBusImpl implements IBus {

    @Override
    public void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }


    @Override
    public void unregister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    @Override
    public void post(IEvent event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void postSticky(IEvent event) {
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void removeStickyEvent(IEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void removeStickyEvent(Class event) {
        EventBus.getDefault().removeStickyEvent(event);
    }
}
