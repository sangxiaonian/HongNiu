package com.hongniu.freight.entity

import com.fy.companylibrary.config.Param

/**
 * 作者：  on 2021/2/1.
 *
 */
data class AppAddressListParam(

        var startOrEnd:String?,//	true	String	地址状态 start是查出发地 end是查目的地
        var searchText:String?,//	false	string	搜索字段
        var pageNum:Int,//	true	number	页数 默认1
        var pageSize:Int=Param.PAGE_SIZE,//	true	number	每页条数

)
