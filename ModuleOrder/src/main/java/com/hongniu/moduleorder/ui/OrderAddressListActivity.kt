package com.hongniu.moduleorder.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.fy.androidlibrary.event.BusFactory
import com.fy.androidlibrary.event.IBus.IEvent
import com.fy.androidlibrary.utils.permission.PermissionUtils
import com.fy.androidlibrary.utils.permission.PermissionUtils.onApplyPermission
import com.fy.androidlibrary.widget.SearchTitleView
import com.hongniu.baselibrary.arouter.ArouterParamOrder
import com.hongniu.baselibrary.arouter.ArouterUtils
import com.hongniu.baselibrary.base.RefrushActivity
import com.hongniu.baselibrary.config.Param
import com.hongniu.baselibrary.entity.CommonBean
import com.hongniu.baselibrary.entity.PageBean
import com.hongniu.baselibrary.event.Event.EndLoactionEvent
import com.hongniu.baselibrary.event.Event.StartLoactionEvent
import com.hongniu.moduleorder.R
import com.hongniu.moduleorder.databinding.ActivityOrderAddressListBinding
import com.hongniu.moduleorder.databinding.ItemOrderAddressBinding
import com.hongniu.moduleorder.entity.OrderAddressListBean
import com.hongniu.moduleorder.entity.OrderAddressListParam
import com.hongniu.moduleorder.entity.OrderTranMapBean
import com.hongniu.moduleorder.net.HttpOrderFactory
import com.sang.common.recycleview.adapter.XAdapter
import com.sang.common.recycleview.holder.BaseHolder
import io.reactivex.Observable

/**
 *@data  2021/2/1
 *@Author PING
 *@Description
 * 地址列表
 *
 */
@Route(path = ArouterParamOrder.activity_order_address_list)
class OrderAddressListActivity : RefrushActivity<OrderAddressListBean>(), SearchTitleView.OnSearchClickListener, SearchTitleView.OnClearClickListener {

    var start: Boolean = false
    val param = OrderAddressListParam("start", "", 1)
    private val binding: ActivityOrderAddressListBinding by lazy {
        ActivityOrderAddressListBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initListener()
        setToolbarTitle(if (start) "请选择或新增发货信息" else "请选择或新增收货信息")
        queryData(true, true)
    }

    override fun initData() {
        super.initData()
        start = intent.getBooleanExtra(Param.TRAN, true)
        param.startOrEnd = if (start) "start" else "end"
        binding.tvAdd.text = if (start) "新增发货信息" else "新增收货信息"
    }

    override fun initListener() {
        super.initListener()
        binding.tvAdd.setOnClickListener {
            jump2Add()
        }
        binding.search.setOnSearchClickListener(this)
        binding.search.setOnClearClickListener(this)
        binding.search.setSearchTextChangeListener {
            param.searchText = if (it.isNullOrEmpty()) "" else it
            queryData(true)
        }
    }

    private fun jump2Add() {
        PermissionUtils.applyMap(this, object : onApplyPermission {
            override fun hasPermission() {
                ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_person_map_search)
                        .withBoolean(Param.TRAN, !start)
                        .navigation(mContext as Activity, 2)


            }

            override fun noPermission() {}
        })
    }


    var isJump = true
    override fun getListDatas(): Observable<CommonBean<PageBean<OrderAddressListBean>>> {
        param.pageNum = currentPage
        return HttpOrderFactory.queryAddressList(param)
                .doOnComplete {
                    if (isJump && datas.isEmpty()) {
                        isJump = false
                        jump2Add()
                    }
                }
    }

    override fun getAdapter(datas: MutableList<OrderAddressListBean>): XAdapter<OrderAddressListBean> {
        return object : XAdapter<OrderAddressListBean>(mContext, datas) {

            override fun initHolder(parent: ViewGroup, viewType: Int): BaseHolder<OrderAddressListBean> {
                return object : BaseHolder<OrderAddressListBean>(mContext, parent, R.layout.item_order_address) {
                    override fun initView(itemView: View, position: Int, data: OrderAddressListBean) {
                        super.initView(itemView, position, data)
                        val holderBinding: ItemOrderAddressBinding = ItemOrderAddressBinding.bind(itemView)


                        holderBinding.tvTitle.text = if (start) {
                            data.shipperName + "\t\t" + data.shipperMobile
                        } else {
                            data.receiverName + "\t\t" + data.receiverMobile
                        }
                        holderBinding.tvTitle.visibility = if ((start && data.shipperName.isNullOrEmpty() && data.shipperMobile.isNullOrEmpty())
                                || (!start && data.receiverMobile.isNullOrEmpty() && data.receiverMobile.isNullOrEmpty())

                        ) {
                            View.GONE
                        } else {
                            View.VISIBLE
                        }
                        holderBinding.tvAddress.text = if (start) {
                            data.startPlaceInfo
                        } else {
                            data.destinationInfo
                        }
                        holderBinding.root.setOnClickListener {

//                            val intent = Intent()
//                            intent.putExtra(Param.TRAN, data)
//                            setResult(Activity.RESULT_OK, intent)
//                            finish()


                            if (start) {
                                StartLoactionEvent(null).also {
                                    it.destinationLatitude = data.startPlaceLat.toString()
                                    it.destinationLongitude = data.startPlaceLon.toString()
                                    it.placeInfo = data.startPlaceInfo
                                }
                            } else {
                                EndLoactionEvent(null).also {
                                    it.destinationLatitude = data.destinationLat.toString()
                                    it.destinationLongitude = data.destinationLon.toString()
                                    it.placeInfo = data.destinationInfo
                                }
                            }.let {
                                onSelect(it)
                            }
                        }


                    }
                }
            }

        }
    }

    override fun onSearch(msg: String?) {
        param.searchText = if (msg.isNullOrEmpty()) "" else msg
        queryData(true, true)
    }

    override fun onClear() {
        param.searchText = ""
        binding.search.title = ""
        queryData(true, true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = data?.getParcelableExtra<OrderTranMapBean>(Param.TRAN)?.poiItem?.let {
            onSelect(if (start) {
                StartLoactionEvent(it)
            } else {
                EndLoactionEvent(it)
            })
        }
        if (result != null) {


//            val bean = OrderAddressListBean(
//                    "",
//                    "",
//                    result.addressDetail,
//                    result.poiItem.latLonPoint.longitude,
//                    result.poiItem.latLonPoint.latitude,
//                    result.addressDetail,
//                    result.poiItem.latLonPoint.longitude,
//                    result.poiItem.latLonPoint.latitude,
//                    "",
//                    result.name,
//                    result.phone,
//                    result.name,
//                    result.phone,
//
//                    )
//            val intent = Intent()
//            intent.putExtra(Param.TRAN, bean)
//            setResult(Activity.RESULT_OK, intent)
//            finish()


        }
    }

    private fun onSelect(event: IEvent) {


        BusFactory.getBus().post(event)
        finish()
    }


}
