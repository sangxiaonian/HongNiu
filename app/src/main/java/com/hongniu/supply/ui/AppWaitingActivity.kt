package com.hongniu.supply.ui

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import com.fy.androidlibrary.imgload.ImageLoader
import com.fy.androidlibrary.utils.CommonUtils
import com.fy.androidlibrary.widget.span.XClickableSpan
import com.fy.companylibrary.config.Param
import com.fy.companylibrary.ui.CompanyBaseActivity
import com.hongniu.baselibrary.entity.CompanyInfoBean
import com.hongniu.supply.R
import com.hongniu.supply.databinding.ActivityAppWaitingBinding
import java.util.*

/**
 * @data  2021/3/24
 * @Author PING
 * @Description
 *
 * 等待开通货运方式
 */
class AppWaitingActivity : CompanyBaseActivity() {

    private val bind:ActivityAppWaitingBinding by lazy {
        ActivityAppWaitingBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        setWhitToolBar("")
        initView()
        initData()
    }

    override fun initData() {
        super.initData()
      var info: CompanyInfoBean ?= intent.getParcelableExtra(Param.TRAN);

        info?.let {
            ImageLoader.getLoader().load(this, bind.imgLogo, info.logoUrl)
            bind.tvCompanyName.text=info.companyName?:""

            val builder=SpannableStringBuilder("联系人：")
            builder.append(info.contacts ?: "").append("\t\t\t")

            val start=builder.length
            builder.append(info.contactsphone ?: "")
            val end=builder.length
            val clickSpan= object : XClickableSpan(){
                override fun onClick(widget: View) {
                     CommonUtils.call(mContext,info.contactsphone)
                }
            }
            clickSpan.setColor(R.color.color_of_3d5688)
            builder.setSpan(clickSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            bind.tvContactName.text=builder
            bind.tvContactName.movementMethod = LinkMovementMethod.getInstance();
        }
    }

}