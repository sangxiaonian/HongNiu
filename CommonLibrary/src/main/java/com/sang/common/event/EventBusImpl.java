package com.sang.common.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by dale on 2017/7/31.
 */

public class EventBusImpl implements IBus {

    @Override
    public void register(Object object) {
        try {
            if (!EventBus.getDefault().isRegistered(object)) {
                EventBus.getDefault().register(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void unregister(Object object) {
        try {
            if (EventBus.getDefault().isRegistered(object)) {
                EventBus.getDefault().unregister(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void post(Object event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    @Override
    public void removeStickyEvent(Object event) {
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void removeStickyEvent(Class event) {
        EventBus.getDefault().removeStickyEvent(event);
    }
}
