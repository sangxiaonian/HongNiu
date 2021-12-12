package com.fy.companylibrary.manager

import com.fy.androidlibrary.utils.SharedPreferencesUtils

/**
 *@data  2021/12/12$
 *@Author PING
 *@Description
 *
 *
 */
object PrivacyManger {
    private const val key = "RULE"

    /**
     * true 同意隐私协议
     */
    fun isAgreePrivacy(): Boolean {
        return SharedPreferencesUtils.getInstance().getBoolean("RULE")
    }

    /**
     * true 同意隐私协议
     */
    fun setAgreePrivacy(isAgree: Boolean) {
        SharedPreferencesUtils.getInstance().putBoolean("RULE", true)
    }
}