package com.fy.companylibrary.manager

import com.fy.androidlibrary.net.listener.TaskControl
import com.fy.companylibrary.net.NetObserver
import com.hongniu.baselibrary.net.HttpAppFactory

/**
 *@data  2022/7/12$
 *@Author PING
 *@Description
 *
 *
 */
class PolicyManger {

    companion object {
        val instance by lazy {
            PolicyManger()
        }
    }

    /**
     * 查询保险信息
     */
    fun queryPolicyInfo(listener: TaskControl.OnTaskListener) {

    }


}