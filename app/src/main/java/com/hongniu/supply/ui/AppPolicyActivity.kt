package com.hongniu.supply.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.fy.androidlibrary.toast.ToastUtils
import com.fy.companylibrary.net.NetObserver
import com.fy.companylibrary.ui.CompanyBaseActivity
import com.fy.companylibrary.widget.ItemTextView
import com.hongniu.baselibrary.arouter.ArouterParamsApp
import com.hongniu.baselibrary.arouter.ArouterUtils
import com.hongniu.baselibrary.config.Param
import com.hongniu.baselibrary.entity.H5Config
import com.hongniu.baselibrary.entity.PolicyCaculParam
import com.hongniu.baselibrary.entity.PolicyInfoBean
import com.hongniu.baselibrary.net.HttpAppFactory
import com.hongniu.freight.utils.PickerDialogUtils
import com.hongniu.supply.R
import com.hongniu.supply.databinding.ActivityAppPolicyBinding

/**
 * @data  2021/3/24
 * @Author PING
 * @Description
 *
 * 等待开通货运方式
 */
@Route(path = ArouterParamsApp.activity_policy)
class AppPolicyActivity : CompanyBaseActivity() {

    private val bind by lazy {
        ActivityAppPolicyBinding.inflate(layoutInflater)
    }

    var policyInfo: PolicyInfoBean? = null

    val params by lazy {
        intent.getParcelableExtra(Param.TRAN)?:PolicyCaculParam()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        setWhitToolBar("购买保险")

        initView()
        initData()
        initListener()
        query()
    }

    override fun initData() {
        super.initData()
        bind.tvPolicy.movementMethod = LinkMovementMethod.getInstance()
        bind.tvPolicy.text = getSpannableStringBuilder(this)
        bind.tvPolicy.highlightColor=Color.TRANSPARENT


    }

    fun query() {
        HttpAppFactory.queryPolicyInfo()
            .subscribe(object : NetObserver<PolicyInfoBean>(this) {
                override fun doOnSuccess(data: PolicyInfoBean?) {
                    super.doOnSuccess(data)
                    policyInfo = data
                    initInfo(policyInfo)
                }
            })
    }

    private fun initInfo(policyInfo: PolicyInfoBean?) {
        bind.itemPolicyType.textCenter=params.policyType
        bind.itemLoadingType.textCenter=policyInfo?.loadingMethods?.find { it.id==params.loadingMethods }?.displayName
        bind.itemCargoType.textCenter=policyInfo?.goodsTypes?.find { it.id==params.goodTypes }?.displayName
        bind.itemPackageType.textCenter=policyInfo?.packingMethods?.find { it.id==params.packingMethods }?.displayName
        bind.itemTrainType.textCenter=policyInfo?.transportMethods?.find { it.id==params.transportMethods }?.displayName
        bind.itemPrice.textCenter=params.goodPrice
    }

    override fun initListener() {
        super.initListener()
        bind.itemPolicyType.setOnClickListener {
            policyInfo?.policyType?.let { policys ->
                showDialog(
                    bind.itemPolicyType,
                    policys
                ) {
                    params.policyType = policys[it]
                    bind.itemPolicyType.textCenter = policys[it] ?: ""

                }
            }

        }
        bind.itemLoadingType.setOnClickListener {
            policyInfo?.loadingMethods?.map { it.displayName }?.toMutableList()?.let { policys ->
                showDialog(
                    bind.itemLoadingType,
                    policys
                ) {
                    params.loadingMethods = policyInfo?.loadingMethods?.get(it)?.id ?: ""
                    bind.itemLoadingType.textCenter =
                        policyInfo?.loadingMethods?.get(it)?.displayName ?: ""
                }
            }
        }
        bind.itemCargoType.setOnClickListener {
            policyInfo?.goodsTypes?.map { it.displayName }?.toMutableList()?.let { policys ->
                showDialog(
                    bind.itemCargoType,
                    policys
                ) {
                    params.goodTypes = policyInfo?.goodsTypes?.get(it)?.id ?: ""
                    bind.itemCargoType.textCenter =
                        policyInfo?.goodsTypes?.get(it)?.displayName ?: ""
                }
            }
        }
        bind.itemPackageType.setOnClickListener {
            policyInfo?.packingMethods?.map { it.displayName }?.toMutableList()?.let { policys ->
                showDialog(
                    bind.itemPackageType,
                    policys
                ) {
                    bind.itemPackageType.textCenter =
                        policyInfo?.packingMethods?.get(it)?.displayName ?: ""
                    params.packingMethods = policyInfo?.packingMethods?.get(it)?.id ?: ""
                }
            }
        }
        bind.itemTrainType.setOnClickListener {
            policyInfo?.transportMethods?.map { it.displayName }?.toMutableList()?.let { policys ->
                showDialog(
                    bind.itemTrainType,
                    policys
                ) {
                    params.transportMethods = policyInfo?.transportMethods?.get(it)?.id ?: ""
                    bind.itemTrainType.textCenter =
                        policyInfo?.transportMethods?.get(it)?.displayName ?: ""
                }
            }
        }

        bind.itemPrice.setOnCenterChangeListener {
            params.goodPrice = it
        }

        bind.btSave.setOnClickListener {
            if (!check()) {
                return@setOnClickListener
            }
            HttpAppFactory.calculatePolicyInfo(params)
                .subscribe(object : NetObserver<String>(this) {
                    override fun doOnSuccess(data: String?) {
                        super.doOnSuccess(data)
                        params.policyPrice = data
                        setResult(102, Intent().also { it.putExtra(Param.TRAN,params) })
                        finish()
                    }
                })
        }
        bind.imgPolicy.setOnClickListener {
            bind.imgPolicy.isSelected = !bind.imgPolicy.isSelected
            bind.imgPolicy.setImageResource(if (bind.imgPolicy.isSelected) R.drawable.icon_xz_36 else R.drawable.icon_wxz_36)
        }
    }

    private fun check(): Boolean {
        if (bind.itemPolicyType.textCenter.isNullOrEmpty()) {
            ToastUtils.getInstance().show(bind.itemPolicyType.textCenterHide)
            return false
        }
        if (bind.itemLoadingType.textCenter.isNullOrEmpty()) {
            ToastUtils.getInstance().show(bind.itemLoadingType.textCenterHide)
            return false
        }
        if (bind.itemCargoType.textCenter.isNullOrEmpty()) {
            ToastUtils.getInstance().show(bind.itemCargoType.textCenterHide)
            return false
        }
        if (bind.itemPackageType.textCenter.isNullOrEmpty()) {
            ToastUtils.getInstance().show(bind.itemPackageType.textCenterHide)
            return false
        }
        if (bind.itemTrainType.textCenter.isNullOrEmpty()) {
            ToastUtils.getInstance().show(bind.itemTrainType.textCenterHide)
            return false
        }
        if (bind.itemPrice.textCenter.isNullOrEmpty()) {
            ToastUtils.getInstance().show(bind.itemPrice.textCenterHide)
            return false
        }
        if (!bind.imgPolicy.isSelected) {
            ToastUtils.getInstance().show("请确认保险信息")
            return false
        }

        return true
    }

    private fun showDialog(
        view: ItemTextView,
        picks: MutableList<String>,
        callBack: (index: Int) -> Unit
    ) {
        val index = picks.indexOf(view.textCenter)
        PickerDialogUtils.initPickerDialog(
            mContext
        ) { options1, options2, options3, v -> callBack.invoke(options1) }
            .also {
                it.setTitleText(view.textCenterHide)
                it.setPicker(picks)
                it.setSelectOptions(index)

            }
            .show()
    }

    private fun getSpannableStringBuilder(context: Context): SpannableStringBuilder {
        val builder =
            SpannableStringBuilder(context.getString(R.string.order_insruance_police_front))
        val span = ForegroundColorSpan(context.resources.getColor(R.color.color_content_light))
        var end = builder.length
        val clickStart = end
        builder.setSpan(span, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //点击保险条款
        builder.append(context.getString(R.string.order_insruance_police))
        val driverClick: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5)
                    .withSerializable(
                        Param.TRAN, H5Config(
                            getString(R.string.order_insruance_police),
                            Param.insurance_polic,
                            true
                        )
                    ).navigation(this@AppPolicyActivity)
            }

            //去除连接下划线
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = false
            }
        }
        var start = end
        end = builder.length
        builder.setSpan(driverClick, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.append("、")
        start = builder.length
        builder.append(context.getString(R.string.order_insruance_notify))
        end = builder.length
        //点击投保须知
        val notifyClick: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5)
                    .withSerializable(
                        Param.TRAN, H5Config(
                            getString(R.string.order_insruance_notify),
                            Param.insurance_notify,
                            true
                        )
                    ).navigation(this@AppPolicyActivity)

            }

            //去除连接下划线
            override fun updateDrawState(ds: TextPaint) {
                ds.color = ds.linkColor
                ds.isUnderlineText = false
            }
        }
        builder.setSpan(notifyClick, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        val clickEnd = end
        val clickSpan = ForegroundColorSpan(context.resources.getColor(R.color.color_title_dark))
        builder.setSpan(clickSpan, clickStart, clickEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spanEnd = ForegroundColorSpan(context.resources.getColor(R.color.color_content_light))
        start = builder.length
        builder.append(context.getString(R.string.order_insruance_police_end))
        builder.setSpan(spanEnd, start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder
    }
}