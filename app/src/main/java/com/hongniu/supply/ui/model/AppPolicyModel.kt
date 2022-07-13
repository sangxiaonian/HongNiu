package com.hongniu.supply.ui.model

import com.fy.androidlibrary.net.listener.TaskControl
import com.fy.companylibrary.manager.PolicyManger
import com.fy.companylibrary.net.NetObserver

/**
 *@data  2022/7/13$
 *@Author PING
 *@Description
 *
 *
 */
class AppPolicyModel {

    fun queryPolicyInfo(listener: TaskControl.OnTaskListener) {
        PolicyManger.instance.queryPolicyInfo(listener)
    }

}