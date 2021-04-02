package com.hongniu.freight.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.fy.androidlibrary.utils.permission.PermissionUtils
import com.fy.androidlibrary.utils.permission.PermissionUtils.onApplyPermission
import com.fy.androidlibrary.widget.SearchTitleView
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder
import com.fy.companylibrary.config.ArouterParamMNLM
import com.fy.companylibrary.config.Param
import com.fy.companylibrary.entity.PageBean
import com.fy.companylibrary.ui.RefrushActivity
import com.hongniu.baselibrary.arouter.ArouterUtils
import com.hongniu.baselibrary.entity.CommonBean
import com.hongniu.freight.R
import com.hongniu.freight.databinding.ActivityAppAddressListBinding
import com.hongniu.freight.databinding.ItemAddressBinding
import com.hongniu.freight.entity.AppAddressListBean
import com.hongniu.freight.entity.AppAddressListParam
import com.hongniu.freight.entity.TranMapBean
import com.hongniu.freight.net.HttpAppFactory
import io.reactivex.Observable

/**
 *@data  2021/2/1
 *@Author PING
 *@Description
 * 地址列表
 *
 */
class AppAddressListActivity : RefrushActivity<AppAddressListBean>(), SearchTitleView.OnSearchClickListener, SearchTitleView.OnClearClickListener {

    var start: Boolean = false
    val param = AppAddressListParam("start", "", 1);
    private val binding: ActivityAppAddressListBinding by lazy {
        ActivityAppAddressListBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initData()
        initListener()
        setWhitToolBar(if (start) "请选择或新增发货信息" else "请选择或新增收货信息")
        queryData(true, true)
    }

    override fun initData() {
        super.initData()
        start = intent.getBooleanExtra(Param.TRAN, true)
        param.startOrEnd = if(start)"start" else "end"
        binding.tvAdd.text=if (start) "新增发货信息" else "新增收货信息"
    }

    override fun initListener() {
        super.initListener()
        binding.tvAdd.setOnClickListener {

            jump2Add()

        }
        binding.search.setOnSearchClickListener(this);
        binding.search.setOnClearClickListener(this);
        binding.search.setSearchTextChangeListener {
            param.searchText = if (it.isNullOrEmpty()) "" else it
            queryData(true)
        }
    }

    private fun jump2Add() {
        PermissionUtils.applyMap(this, object : onApplyPermission {
            override fun hasPermission() {
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_map_search)
                        .withBoolean(Param.TRAN, !start)
                        .navigation(mContext as Activity, 2)
            }

            override fun noPermission() {}
        })
    }


    /**
     * 获取空界面
     * @return
     * @param parent
     */
    override fun getEmptyHolder(parent: ViewGroup?): PeakHolder<*>? {
        return null
    }

    var isJump=true
    override fun getListDatas(): Observable<CommonBean<PageBean<AppAddressListBean>>> {
        param.pageNum = currentPage;
        return HttpAppFactory.queryAddressList(param)
                .doOnComplete{
                    if (isJump&&datas.isEmpty()){
                        isJump=false
                        jump2Add()
                    }
                }
    }

    override fun getAdapter(datas: MutableList<AppAddressListBean>?): XAdapter<AppAddressListBean> {
        return object : XAdapter<AppAddressListBean>(mContext, datas) {

            override fun initHolder(parent: ViewGroup, viewType: Int): BaseHolder<AppAddressListBean> {
                return object : BaseHolder<AppAddressListBean>(mContext, parent, R.layout.item_address) {
                    override fun initView(itemView: View, position: Int, data: AppAddressListBean) {
                        super.initView(itemView, position, data)
                        val holderBinding: ItemAddressBinding = ItemAddressBinding.bind(itemView)


                        holderBinding.tvTitle.text = if (start) {
                            data.shipperName + "\t\t" + data.shipperMobile
                        } else {
                            data.receiverName + "\t\t" + data.receiverMobile
                        }
                        holderBinding.tvAddress.text = if (start) {
                            data.startPlaceInfo
                        } else {
                            data.destinationInfo
                        }
                        holderBinding.root.setOnClickListener {

                            val intent = Intent()
                            intent.putExtra(Param.TRAN, data)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
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
        binding.search.title=""
        queryData(true, true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = data?.getParcelableExtra<TranMapBean>(Param.TRAN)
        if (result != null) {
            val bean = AppAddressListBean(
                    "",
                    "",
                    result.addressDetail,
                    result.poiItem.latLonPoint.longitude,
                    result.poiItem.latLonPoint.latitude,
                    result.addressDetail,
                    result.poiItem.latLonPoint.longitude,
                    result.poiItem.latLonPoint.latitude,
                    "",
                    result.name,
                    result.phone,
                    result.name,
                    result.phone,

                    )
            val intent = Intent()
            intent.putExtra(Param.TRAN, bean)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}