package com.hongniu.freight.ui

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
import com.fy.companylibrary.config.ArouterParamMNLM
import com.fy.companylibrary.config.Param
import com.fy.companylibrary.ui.CompanyBaseActivity
import com.fy.companylibrary.widget.ItemTextView
import com.hongniu.baselibrary.arouter.ArouterUtils
import com.hongniu.freight.R
import com.hongniu.freight.databinding.MnlmActivityAppPolicyBinding
import com.hongniu.freight.entity.H5Config
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
        initInfo()
    }

    private fun initInfo() {
        if (model.params?.goodPrice == null) {
            return
        }
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
            if (check()) {
                model.caculatePolicyInfo(null)
            }
        }

        bind.btSave.setOnClickListener {
            if (!check()) {
                return@setOnClickListener
            }
            model.params?.let { bean ->
                setResult(102, Intent().also { it.putExtra(Param.TRAN, bean) })
                finish()
            }
        }
        bind.imgPolicy.setOnClickListener {
            bind.imgPolicy.isSelected = !bind.imgPolicy.isSelected
            bind.imgPolicy.setImageResource(if (bind.imgPolicy.isSelected) R.mipmap.icon_xz_36 else R.mipmap.icon_wxz_36)
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
        val span = ForegroundColorSpan(context.resources.getColor(R.color.color_of_333333))
        var end = builder.length
        val clickStart = end
        builder.setSpan(span, 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        //点击保险条款
        builder.append(context.getString(R.string.order_insruance_police))
        val driverClick: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5)
                    .withSerializable(
                        Param.TRAN, H5Config(
                            getString(R.string.order_insruance_police),
                            Param.h_insurance_polic,
                            true
                        )
                    ).navigation(this@AppMnlmPolicyActivity)
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
        val clickEnd = end
        val clickSpan = ForegroundColorSpan(context.resources.getColor(R.color.color_of_999999))
        builder.setSpan(clickSpan, clickStart, clickEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val spanEnd = ForegroundColorSpan(context.resources.getColor(R.color.color_of_333333))
        start = builder.length
        builder.append(context.getString(R.string.order_insruance_police_end))
        builder.setSpan(spanEnd, start, builder.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return builder
    }
}