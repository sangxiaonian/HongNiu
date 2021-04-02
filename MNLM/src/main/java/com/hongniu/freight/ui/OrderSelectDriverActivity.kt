package com.hongniu.freight.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fy.androidlibrary.utils.CommonUtils
import com.fy.androidlibrary.widget.SearchTitleView
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder
import com.fy.companylibrary.config.Param
import com.fy.companylibrary.entity.PageBean
import com.fy.companylibrary.ui.RefrushActivity
import com.hongniu.baselibrary.entity.CommonBean
import com.hongniu.freight.R
import com.hongniu.freight.entity.OrderSelectDriverInfoBean
import com.hongniu.freight.net.HttpAppFactory
import io.reactivex.Observable

/**
 * @data  2020/12/28
 * @Author PING
 * @Description
 *
 * 发货选择司机页面
 */
class OrderSelectDriverActivity : RefrushActivity<OrderSelectDriverInfoBean>() {

    var searchText: String = ""
    lateinit var searchView: SearchTitleView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_select_driver)
        setWhitToolBar("请选择司机")
        refresh.setEnableLoadMore(false)
        initView()
        initData()
        initListener()
        searchView.getFocus()
    }

    override fun initView() {
        super.initView()
        searchView = findViewById(R.id.search)
    }

    override fun initListener() {
        super.initListener()
        searchView.setOnSearchClickListener {
            searchText = it
            queryData(true, true)
        }
        searchView.setOnClearClickListener {
            searchText = ""
            queryData(true, true)
        }
    }

    /**
     * 获取空界面
     *
     * @param parent
     * @return
     */
    override fun getEmptyHolder(parent: ViewGroup): PeakHolder<*>? {
        return null
    }

    override fun queryData(isClear: Boolean) {
        super.queryData(isClear)

    }

    override fun getListDatas(): Observable<CommonBean<PageBean<OrderSelectDriverInfoBean>>> {
        return HttpAppFactory.querySelectDriverInfo(searchText)
                .map {
                    var bean = CommonBean<PageBean<OrderSelectDriverInfoBean>>();
                    bean.code = it.code
                    if ("必填参数不能为空" == it.msg) {
                        bean.msg = "请输入搜索内容"
                    } else {
                        bean.msg = it.msg
                    }

                    bean.data = PageBean<OrderSelectDriverInfoBean>()
                    bean.data.list = it.data
                    bean
                }
    }

    override fun getAdapter(datas: List<OrderSelectDriverInfoBean>): XAdapter<OrderSelectDriverInfoBean> {
        return object : XAdapter<OrderSelectDriverInfoBean>(mContext, datas) {
            /**
             * 初始化ViewHolder,[XAdapter.onCreateViewHolder]处,用于在非头布局\脚布局\刷新时候
             * 调用
             *
             * @param parent   父View,即为RecycleView
             * @param viewType holder类型,在[XAdapter.getItemViewType]处使用
             * @return BaseHolder或者其父类
             */
            override fun initHolder(parent: ViewGroup, viewType: Int): BaseHolder<OrderSelectDriverInfoBean> {
                return object : BaseHolder<OrderSelectDriverInfoBean>(context, parent, R.layout.item_select_driver) {
                    override fun initView(itemView: View, position: Int, data: OrderSelectDriverInfoBean) {
                        super.initView(itemView, position, data)
                        itemView.findViewById<TextView>(R.id.tv_title).text = "${data.contact}  ${data.mobile}"
                        itemView.findViewById<ImageView>(R.id.img_call).setOnClickListener() {
                            CommonUtils.call(mContext, data.mobile)
                        }

                        itemView.setOnClickListener {
                            var intent = Intent()
                            intent.putExtra(Param.TRAN, data)
                            setResult(RESULT_OK, intent)
                            finish()
                        }

                    }
                }
            }

        }
    }
}