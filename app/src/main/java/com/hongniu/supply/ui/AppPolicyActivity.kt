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
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.fy.androidlibrary.toast.ToastUtils
import com.fy.companylibrary.ui.CompanyBaseActivity
import com.fy.companylibrary.widget.ItemTextView
import com.hongniu.baselibrary.arouter.ArouterParamOrder
import com.hongniu.baselibrary.arouter.ArouterParamsApp
import com.hongniu.baselibrary.arouter.ArouterUtils
import com.hongniu.baselibrary.config.Param
import com.hongniu.baselibrary.entity.H5Config
import com.hongniu.freight.utils.PickerDialogUtils
import com.hongniu.supply.R
import com.hongniu.supply.databinding.ActivityAppPolicyBinding
import com.hongniu.supply.ui.model.AppPolicyModel

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


    val model by lazy {
        ViewModelProvider(this).get(AppPolicyModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        setWhitToolBar("购买保险")
        model.saveInfo(
            intent.getParcelableExtra(Param.TRAN),
            intent.getIntExtra(Param.TYPE, 0)
        )
        initView()
        initData()
        initListener()
        model.queryPolicyInfo(this)
    }

    override fun initData() {
        super.initData()
        bind.tvPolicy.movementMethod = LinkMovementMethod.getInstance()
        bind.tvPolicy.text = getSpannableStringBuilder(this)
        bind.tvPolicy.highlightColor = Color.TRANSPARENT
        initInfo()
    }

    private fun initInfo() {
        bind.itemPolicyType.textCenter = model.params?.policyType
        bind.itemLoadingType.textCenter = model.params?.loadingMethods
        bind.itemCargoType.textCenter = model.params?.goodsTypes
        bind.itemPackageType.textCenter = model.params?.packingMethods
        bind.itemTrainType.textCenter = model.params?.transportMethods
        bind.itemPrice.textCenter = model.params?.goodPrice
        bind.itemPrice.textRight = "保费${model.params?.policyPrice ?: "0.00"}元"
    }

    override fun initListener() {
        super.initListener()


        model.type.observe(this) {
            bind.btSave.text = if (it == 1) {
                "去下单"
            } else {
                "提交"
            }
        }
        model.policyResult.observe(this) {
            bind.itemPrice.textRight = "保费" + it.policyPrice + "元"

        }

        bind.itemPolicyType.setOnClickListener {
            model.policyInfo.value?.policyType?.let { policys ->
                showDialog(
                    bind.itemPolicyType,
                    policys
                ) {
                    model.params?.policyType = policys[it]
                    bind.itemPolicyType.textCenter = policys[it] ?: ""

                }
            }

        }
        bind.itemLoadingType.setOnClickListener {
            model.policyInfo.value?.loadingMethods?.map { it.displayName }?.toMutableList()
                ?.let { policys ->
                    showDialog(
                        bind.itemLoadingType,
                        policys
                    ) {
                        model.params?.loadingMethods =
                            model.policyInfo.value?.loadingMethods?.get(it)?.displayName ?: ""
                        bind.itemLoadingType.textCenter =
                            model.policyInfo.value?.loadingMethods?.get(it)?.displayName ?: ""
                    }
                }
        }
        bind.itemCargoType.setOnClickListener {
            model.policyInfo.value?.goodsTypes?.map { it.displayName }?.toMutableList()
                ?.let { policys ->
                    showDialog(
                        bind.itemCargoType,
                        policys
                    ) {
                        model.params?.goodsTypes =
                            model.policyInfo.value?.goodsTypes?.get(it)?.displayName ?: ""
                        bind.itemCargoType.textCenter =
                            model.policyInfo.value?.goodsTypes?.get(it)?.displayName ?: ""
                    }
                }
        }
        bind.itemPackageType.setOnClickListener {
            model.policyInfo.value?.packingMethods?.map { it.displayName }?.toMutableList()
                ?.let { policys ->
                    showDialog(
                        bind.itemPackageType,
                        policys
                    ) {
                        bind.itemPackageType.textCenter =
                            model.policyInfo.value?.packingMethods?.get(it)?.displayName ?: ""
                        model.params?.packingMethods =
                            model.policyInfo.value?.packingMethods?.get(it)?.displayName ?: ""
                    }
                }
        }
        bind.itemTrainType.setOnClickListener {
            model.policyInfo.value?.transportMethods?.map { it.displayName }?.toMutableList()
                ?.let { policys ->
                    showDialog(
                        bind.itemTrainType,
                        policys
                    ) {
                        model.params?.transportMethods =
                            model.policyInfo.value?.transportMethods?.get(it)?.displayName ?: ""
                        bind.itemTrainType.textCenter =
                            model.policyInfo.value?.transportMethods?.get(it)?.displayName ?: ""
                    }
                }
        }

        bind.itemPrice.setOnCenterChangeListener {
            model.params?.goodPrice = it
            if (model.policyInfo.value != null && check()) {
                model.caculatePolicyInfo(null)
            }
        }

        bind.btSave.setOnClickListener {
            if (!check()) {
                return@setOnClickListener
            }
            if (model.type.value == 1) {
                ArouterUtils.getInstance()
                    .builder(ArouterParamOrder.activity_order_create)
                    .withInt(Param.TYPE, 3)
                    .navigation(this)
                finish()
            } else {
                model.params?.let { bean ->
                    setResult(102, Intent().also { it.putExtra(Param.TRAN, bean) })
                    finish()
                }
            }

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
            ToastUtils.getInstance().show("请先同意保险条款")
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
//        builder.append("、")
//        start = builder.length
//        builder.append(context.getString(R.string.order_insruance_notify))
//        end = builder.length
//        //点击投保须知
//        val notifyClick: ClickableSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5)
//                    .withSerializable(
//                        Param.TRAN, H5Config(
//                            getString(R.string.order_insruance_notify),
//                            Param.insurance_notify,
//                            true
//                        )
//                    ).navigation(this@AppPolicyActivity)
//
//            }
//
//            //去除连接下划线
//            override fun updateDrawState(ds: TextPaint) {
//                ds.color = ds.linkColor
//                ds.isUnderlineText = false
//            }
//        }
//        builder.setSpan(notifyClick, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
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