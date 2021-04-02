package com.hongniu.freight.ui

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fy.androidlibrary.utils.CommonUtils
import com.fy.androidlibrary.widget.SearchTitleView
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder
import com.fy.androidlibrary.widget.span.CenterAlignImageSpan
import com.fy.companylibrary.config.Param
import com.fy.companylibrary.entity.PageBean
import com.fy.companylibrary.ui.RefrushActivity
import com.hongniu.baselibrary.entity.CommonBean
import com.hongniu.freight.R
import com.hongniu.freight.entity.OrderSelectOwnerInfoBean
import com.hongniu.freight.net.HttpAppFactory
import io.reactivex.Observable

/**
 * 选择承运人
 */
class OrderSelectOwnerActivity : RefrushActivity<OrderSelectOwnerInfoBean>() {

    var searchText: String = ""
    lateinit var searchView: SearchTitleView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_select_owner)
        setWhitToolBar("请选择实际承运人")
        initView()
        initData()
        initListener()
        searchView.getFocus()
    }

    override fun initData() {
        super.initData()
        searchView.title=searchText;
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
     * @return
     * @param parent
     */
    override fun getEmptyHolder(parent: ViewGroup): PeakHolder<*>? {
        return null
    }

    override fun getListDatas(): Observable<CommonBean<PageBean<OrderSelectOwnerInfoBean>>> {
        return HttpAppFactory.querySelectOwnerInfo(searchText)
                .map {
                    var bean = CommonBean<PageBean<OrderSelectOwnerInfoBean>>();
                    bean.code = it.code
                    bean.msg = it.msg
                    bean.data = PageBean<OrderSelectOwnerInfoBean>()
                    bean.data.list = it.data
                    bean
                }
    }

    override fun getAdapter(datas: MutableList<OrderSelectOwnerInfoBean>): XAdapter<OrderSelectOwnerInfoBean> {
        return object : XAdapter<OrderSelectOwnerInfoBean>(mContext, datas) {

            override fun initHolder(parent: ViewGroup, viewType: Int): BaseHolder<OrderSelectOwnerInfoBean> {
                return object : BaseHolder<OrderSelectOwnerInfoBean>(mContext, parent, R.layout.item_select_owner) {
                    override fun initView(itemView: View, position: Int, data: OrderSelectOwnerInfoBean) {
                        super.initView(itemView, position, data)

                        var builder = SpannableStringBuilder("实际承运人：")
                        builder.append(data.ownerName).append(" ").append(data.ownerMobile)
                        builder.append("\t")
                        builder.append("\t")
                        var lineStart = builder.length
                        var lineEnd = builder.length + 1
                        builder.append("\t")
                        builder.append("\t")
                        builder.append("\t")
                        builder.setSpan(CenterAlignImageSpan(mContext, R.drawable.ovl_line_v), lineStart, lineEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


                        itemView.findViewById<TextView>(R.id.tv_title).text = data.carNumber
                        itemView.findViewById<TextView>(R.id.tv_name).text = builder

                        itemView.findViewById<TextView>(R.id.tv_car_type).text = "${data.model}（${data.vehicleType}）"
                        itemView.findViewById<TextView>(R.id.tv_call).setOnClickListener{
                            CommonUtils.call(mContext, data.ownerMobile)
                        }


                        itemView.setOnClickListener{
                            var intent= Intent()
                            intent.putExtra(Param.TRAN,data)
                            setResult(RESULT_OK,intent)
                            finish()
                        }

                    }
                }
            }

        }
    }
}