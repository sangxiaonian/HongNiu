package com.hongniu.freight.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.fy.androidlibrary.toast.ToastUtils
import com.fy.androidlibrary.widget.span.XClickableSpan
import com.fy.companylibrary.config.ArouterParamMNLM
import com.fy.companylibrary.net.NetObserver
import com.fy.companylibrary.ui.CompanyBaseActivity
import com.fy.companylibrary.widget.ItemTextView
import com.hongniu.baselibrary.arouter.ArouterUtils
import com.hongniu.baselibrary.config.Param
import com.hongniu.baselibrary.entity.PolicyInfoBean
import com.hongniu.freight.R
import com.hongniu.freight.databinding.MnlmActivityAppPolicyBinding
import com.hongniu.freight.mode.MnlmPolicyModel
import com.hongniu.freight.utils.PickerDialogUtils

/**
 * @data  2021/3/24
 * @Author PING
 * @Description
 *
 * 等待开通货运方式
 */
@Route(path = ArouterParamMNLM.activity_policy)
class AppMnlmPolicyActivity : CompanyBaseActivity() {

    private val bind by lazy {
        MnlmActivityAppPolicyBinding.inflate(layoutInflater)
    }


    val model by lazy {
        ViewModelProvider(this).get(MnlmPolicyModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)
        setWhitToolBar("购买保险")
        model.saveInfo(
            intent.getParcelableExtra(Param.TRAN),
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
    }

    private fun initInfo(policyInfo: PolicyInfoBean) {
        bind.itemPolicyType.textCenter = model.params?.policyType
        bind.itemLoadingType.textCenter =
            policyInfo.loadingMethods?.find { it.id == model.params?.loadingMethods }?.displayName
        bind.itemCargoType.textCenter =
            policyInfo.goodsTypes?.find { it.id == model.params?.goodsTypes }?.displayName
        bind.itemPackageType.textCenter =
            policyInfo.packingMethods?.find { it.id == model.params?.packingMethods }?.displayName
        bind.itemTrainType.textCenter =
            policyInfo.transportMethods?.find { it.id == model.params?.transportMethods }?.displayName
        bind.itemPrice.textCenter = model.params?.goodPrice
    }

    override fun initListener() {
        super.initListener()

        model.policyInfo.observe(this) {
            initInfo(it)
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
                            model.policyInfo.value?.loadingMethods?.get(it)?.id ?: ""
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
                            model.policyInfo.value?.goodsTypes?.get(it)?.id ?: ""
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
                            model.policyInfo.value?.packingMethods?.get(it)?.id ?: ""
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
                            model.policyInfo.value?.transportMethods?.get(it)?.id ?: ""
                        bind.itemTrainType.textCenter =
                            model.policyInfo.value?.transportMethods?.get(it)?.displayName ?: ""
                    }
                }
        }

        bind.itemPrice.setOnCenterChangeListener {
            model.params?.goodPrice = it
        }

        bind.btSave.setOnClickListener {
            if (!check()) {
                return@setOnClickListener
            }
            model.caculatePolicyInfo(this)
                .subscribe(object : NetObserver<String>(this) {
                    override fun doOnSuccess(data: String?) {
                        super.doOnSuccess(data)
                        model.params?.let { it ->
                            it.policyPrice = data

                        }
                        val intent = Intent()
                        intent.putExtra(Param.TRAN, model.params)
                        setResult(100, intent)
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
        val builder = SpannableStringBuilder("购买即代表同意")
        var start = builder.length
        builder.append("《保险条款》")
        var end = builder.length
        val xClickableSpan: XClickableSpan = object : XClickableSpan() {
            /**
             * Performs the click action associated with this span.
             *
             * @param widget
             */
            override fun onClick(widget: View) {
                val h5Config = com.hongniu.freight.entity.H5Config(
                    "保险条款",
                    com.fy.companylibrary.config.Param.h_insurance_polic,
                    true
                )
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5)
                    .withSerializable(com.fy.companylibrary.config.Param.TRAN, h5Config)
                    .navigation(mContext)
            }
        }
        xClickableSpan.setColor(resources.getColor(R.color.color_of_3d59ae))
        builder.setSpan(xClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        builder.append("、")
        start = builder.length
        builder.append("《投保须知》")
        end = builder.length
        val xClickableSpan1: XClickableSpan = object : XClickableSpan() {
            /**
             * Performs the click action associated with this span.
             *
             * @param widget
             */
            override fun onClick(widget: View) {
                val h5Config = com.hongniu.freight.entity.H5Config(
                    "投保须知",
                    com.fy.companylibrary.config.Param.h_insurance_notify,
                    true
                )
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5)
                    .withSerializable(com.fy.companylibrary.config.Param.TRAN, h5Config)
                    .navigation(mContext)
            }
        }
        xClickableSpan1.setColor(resources.getColor(R.color.color_of_3d59ae))
        builder.setSpan(xClickableSpan1, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        return builder
    }


}