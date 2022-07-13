package com.fy.companylibrary.net

import retrofit2.http.Body
import retrofit2.http.POST

/**
 *@data  2022/7/12$
 *@Author PING
 *@Description
 *
 *
 */
interface CompanyService {

    /**
     * 查询保险数据
     */
    @POST("api/login/getcheckcode")
    suspend fun queryPolicy(@Body body: Any):String

}