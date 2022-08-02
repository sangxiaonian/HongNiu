package com.hongniu.freight.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.fy.androidlibrary.widget.span.RoundedBackgroundSpan;
import com.fy.androidlibrary.widget.span.XClickableSpan;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.entity.PolicyCaculParam;
import com.hongniu.freight.R;
import com.hongniu.freight.control.OrderCreateControl;
import com.hongniu.freight.entity.AppAddressListBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.InsuranceInfoBean;
import com.hongniu.freight.entity.OrderCrateParams;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderSelectDriverInfoBean;
import com.hongniu.freight.entity.OrderSelectOwnerInfoBean;
import com.hongniu.freight.entity.TranMapBean;
import com.hongniu.freight.presenter.OrderCreatePresenter;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.dialog.InsuranceDialog;
import com.hongniu.freight.widget.dialog.inter.DialogControl;

import java.util.List;

/**
 * @data 2020/2/14
 * @Author PING
 * @Description 创建/修改订单
 */
@Route(path = ArouterParamMNLM.activity_order_create)
public class OrderCreateActivity extends CompanyBaseActivity implements View.OnClickListener, OrderCreateControl.IOrderCreateView, OnOptionsSelectListener, ItemTextView.OnCenterChangeListener, InsuranceDialog.OnInsuranceDialogListener {
    private View group_start;//选择发货信息
    private View group_end;//选择收货信息s
    private TextView tv_start;//发货地址
    private TextView tv_start_contact;//发货联系人
    private TextView tv_end;//收货地址
    private TextView tv_end_contact;//收货人
    private ItemTextView item_start_time;//发货时间
    private ItemTextView item_cargo;//货物名称
    private ItemTextView item_cargo_type;//货物分类
    private ItemTextView item_weight;//货物重量
    private ItemTextView item_size;//货物体积
    private ItemTextView item_price;//运费
    private ItemTextView item_pay_way;//支付类型
    private TextView tv_agreement;//鸿牛协议
    private TextView tv_agreement_insurance;//保险链协议
    private ImageView img_insurance;//是否购买保险
    private TextView bt_sum;//确认按钮
    private ItemTextView item_cargo_price;//货物价值
    private ItemTextView item_insurance_name;//被保险人信息
    private ViewGroup ll_insurance;//保险条例相关

    private ItemTextView item_owner;//承运人选填
    private ItemTextView item_driver;//司机选填


    OrderCreateControl.IOrderCreatePresenter presenter;
    private OptionsPickerView pickerDialog;
    private OptionsPickerView cargoTypeDialog;
    private OptionsPickerView pickerDialogPay;
    private InsuranceDialog insuranceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_order_create);
        setWhitToolBar("我要发货");
        initView();
        initData();
        initListener();
        presenter = new OrderCreatePresenter(this);
        OrderInfoBean orderInfoBean = getIntent().getParcelableExtra(Param.TRAN);
        presenter.saveInfo(orderInfoBean);


        //版本暂时隐藏,仅支持先付
        item_pay_way.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        super.initView();
        group_start = findViewById(R.id.group_start);
        group_end = findViewById(R.id.group_end);
        ll_insurance = findViewById(R.id.ll_insurance);
        tv_start = findViewById(R.id.tv_start);
        tv_start_contact = findViewById(R.id.tv_start_contact);
        tv_end = findViewById(R.id.tv_end);
        tv_end_contact = findViewById(R.id.tv_end_contact);
        item_start_time = findViewById(R.id.item_start_time);
        item_cargo = findViewById(R.id.item_cargo);
        item_cargo_type = findViewById(R.id.item_cargo_type);
        item_weight = findViewById(R.id.item_weight);
        item_size = findViewById(R.id.item_size);
        item_price = findViewById(R.id.item_price);
        item_pay_way = findViewById(R.id.item_pay_way);
        tv_agreement = findViewById(R.id.tv_agreement);
        tv_agreement_insurance = findViewById(R.id.tv_agreement_insurance);
        img_insurance = findViewById(R.id.img_insurance);
        bt_sum = findViewById(R.id.bt_sum);
        item_cargo_price = findViewById(R.id.item_cargo_price);
        item_insurance_name = findViewById(R.id.item_insurance_name);
        item_owner = findViewById(R.id.item_owner);
        item_driver = findViewById(R.id.item_driver);
    }


    @Override
    protected void initData() {
        super.initData();
        SpannableStringBuilder builder = new SpannableStringBuilder("购买即代表同意");
        int start = builder.length();
        builder.append("《保险条款》");
        int end = builder.length();
        XClickableSpan xClickableSpan = new XClickableSpan() {
            /**
             * Performs the click action associated with this span.
             *
             * @param widget
             */
            @Override
            public void onClick(@NonNull View widget) {
                H5Config h5Config = new H5Config("保险条款", Param.h_insurance_polic, true);
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

            }
        };
        xClickableSpan.setColor(getResources().getColor(R.color.color_of_3d59ae));
        builder.setSpan(xClickableSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append("、");
        start = builder.length();
        builder.append("《投保须知》");
        end = builder.length();
        XClickableSpan xClickableSpan1 = new XClickableSpan() {
            /**
             * Performs the click action associated with this span.
             *
             * @param widget
             */
            @Override
            public void onClick(@NonNull View widget) {
                H5Config h5Config = new H5Config("投保须知", Param.h_insurance_notify, true);
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

            }
        };
        xClickableSpan1.setColor(getResources().getColor(R.color.color_of_3d59ae));
        builder.setSpan(xClickableSpan1, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_agreement_insurance.setMovementMethod(LinkMovementMethod.getInstance());
        tv_agreement_insurance.setText(builder);
        ll_insurance.setVisibility(Utils.isShowInsurance() ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void initListener() {
        super.initListener();
        group_start.setOnClickListener(this);
        group_end.setOnClickListener(this);
        item_start_time.setOnClickListener(this);
        item_pay_way.setOnClickListener(this);
        img_insurance.setOnClickListener(this);
        tv_agreement_insurance.setOnClickListener(this);
        tv_agreement.setOnClickListener(this);
        item_insurance_name.setOnClickListener(this);
        item_cargo_type.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        item_driver.setOnClickListener(this);
        item_owner.setOnClickListener(this);

        item_start_time.setOnCenterChangeListener(this);
        item_cargo.setOnCenterChangeListener(this);
        item_weight.setOnCenterChangeListener(this);
        item_size.setOnCenterChangeListener(this);
        item_cargo_type.setOnCenterChangeListener(this);
        item_price.setOnCenterChangeListener(this);
        item_pay_way.setOnCenterChangeListener(this);
        item_cargo_price.setOnCenterChangeListener(this);
        item_insurance_name.setOnCenterChangeListener(this);
        item_driver.setOnCenterChangeListener(this);
        item_owner.setOnCenterChangeListener(this);
        item_cargo_price.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (R.id.group_start == v.getId()) {
            PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission() {
                    Intent intent = new Intent(mContext, AppAddressListActivity.class);
                    intent.putExtra(Param.TRAN, true);
                    startActivityForResult(intent, 1);
                }

                @Override
                public void noPermission() {

                }
            });
        } else if (R.id.group_end == v.getId()) {
            PermissionUtils.applyMap(this, new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission() {
                    Intent intent = new Intent(mContext, AppAddressListActivity.class);
                    intent.putExtra(Param.TRAN, false);
                    startActivityForResult(intent, 2);
                }

                @Override
                public void noPermission() {

                }
            });
        } else if (R.id.item_start_time == v.getId()) {
//            ToastUtils.getInstance().show("发货时间");
            presenter.showStartTime(this);
        } else if (R.id.item_pay_way == v.getId()) {
//            ToastUtils.getInstance().show("支付方式");
            presenter.showPayDialog();
        } else if (R.id.img_insurance == v.getId()) {
//            ToastUtils.getInstance().show("是否购买保险");
            presenter.onSwitchIsInsurance();
        } else if (R.id.tv_agreement == v.getId()) {
            H5Config h5Config = new H5Config("网络货运运输协议", Param.hongniu_agreement, true);
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

        } else if (R.id.item_insurance_name == v.getId()) {
//            ToastUtils.getInstance().show("选择被保险人");
            presenter.showInsuranceInfo(this);

        } else if (R.id.bt_sum == v.getId()) {
            if (check(true)) {
                presenter.createOrder(this);
            }
        } else if (R.id.item_cargo_type == v.getId()) {
//            ToastUtils.getInstance().show("货物分类");
            presenter.showCargoType(this);
        } else if (R.id.item_driver == v.getId()) {
            startActivityForResult(new Intent(this, OrderSelectDriverActivity.class), 3);
        } else if (R.id.item_owner == v.getId()) {
            Intent intent = new Intent(this, OrderSelectOwnerActivity.class);
            startActivityForResult(intent, 4);
        } else if (R.id.item_cargo_price == v.getId()) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_policy)
                    .withParcelable(Param.TRAN, presenter.getPolicyParam())
                    .navigation(this, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null && requestCode == 1) {
            //发货
            AppAddressListBean result = data.getParcelableExtra(Param.TRAN);
            presenter.saveStartInfo(result);
        } else if (data != null && requestCode == 2) {
            //收货
            AppAddressListBean result = data.getParcelableExtra(Param.TRAN);
            presenter.saveEndInfo(result);
        } else if (data != null && requestCode == 3) {
            //选择司机
            OrderSelectDriverInfoBean result = data.getParcelableExtra(Param.TRAN);
            presenter.saveDriverInfo(result);
        } else if (data != null && requestCode == 4) {
            //选择承运人
            OrderSelectOwnerInfoBean result = data.getParcelableExtra(Param.TRAN);
            presenter.saveOwnerInfo(result);
        } else if (data != null && requestCode == 100) {
            //保险信息
            PolicyCaculParam result = data.getParcelableExtra(Param.TRAN);
            presenter.savePolicyParam(result);
            showInsurancePrice(result);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * 修改订单时候，初始化定订单数据
     *
     * @param orderInfoBean 订单详情
     */
    @Override
    public void initOrderUIInfo(OrderInfoBean orderInfoBean) {
        setWhitToolBar("修改订单");
        bt_sum.setText("确认");
        item_cargo.setTextCenter(orderInfoBean.getGoodName());
        item_price.setTextCenter(ConvertUtils.changeFloat(orderInfoBean.getMoney(), 2));
        item_size.setTextCenter(orderInfoBean.getGoodVolume());
        item_weight.setTextCenter(orderInfoBean.getGoodWeight());

    }

    /**
     * 展示发货信息
     *
     * @param result
     */
    @Override
    public void showStartInfo(TranMapBean result) {
        if (result != null && result.getPoiItem() != null) {
            CommonUtils.setText(tv_start, result.getAddressDetail());
            CommonUtils.setText(tv_start_contact, String.format("%s %s", result.getName(), result.getPhone()));
        }
        check(false);

    }

    /**
     * 初始化收货信息
     *
     * @param result
     */
    @Override
    public void showEndInfo(TranMapBean result) {
        if (result != null && result.getPoiItem() != null) {
            CommonUtils.setText(tv_end, result.getAddressDetail());
            CommonUtils.setText(tv_end_contact, String.format("%s %s", result.getName(), result.getPhone()));
        }
        check(false);

    }

    /**
     * 显示发货日期
     *
     * @param days
     * @param hours
     * @param minutes
     */
    @Override
    public void showTimePicker(List<String> days, List<List<String>> hours, List<List<List<String>>> minutes) {
        if (pickerDialog == null) {
            pickerDialog = PickerDialogUtils.initPickerDialog(mContext, this);
            pickerDialog.setTitleText("选择取货时间");
            pickerDialog.setPicker(days, hours, minutes);
        }
        pickerDialog.show();
    }

    /**
     * 显示发货日期
     *
     * @param time
     */
    @Override
    public void showTime(String time) {
        item_start_time.setTextCenter(time);
    }

    /**
     * 切换当前是否购买保险
     *
     * @param isInsurance true 购买
     */
    @Override
    public void switchInsurance(boolean isInsurance) {
        img_insurance.setImageResource(isInsurance ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        item_cargo_price.setVisibility(isInsurance ? View.VISIBLE : View.GONE);
        item_insurance_name.setVisibility(isInsurance ? View.VISIBLE : View.GONE);
        tv_agreement_insurance.setVisibility(isInsurance ? View.VISIBLE : View.GONE);
        check(false);
    }

    /**
     * 显示付款方式
     *
     * @param payType
     * @param payWaysInfo
     */
    @Override
    public void showPayDialog(int payType, List<String> payWaysInfo) {
        if (pickerDialogPay == null) {
            pickerDialogPay = PickerDialogUtils.initPickerDialog(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    presenter.switchPayWay(options1);
                    check(false);
                }
            });
            pickerDialogPay.setTitleText("选择支付方式");
            pickerDialogPay.setPicker(payWaysInfo);
        }
        pickerDialogPay.show();
    }

    @Override
    public void showPayType(int payType, String currentPayType) {
        item_pay_way.setTextCenter(currentPayType);
    }

    /**
     * 显示选择被保险人信息弹窗
     *
     * @param inforBeans 被保险人信息
     */
    @Override
    public void showInsuranceDialog(List<InsuranceInfoBean> inforBeans) {
        if (insuranceDialog == null) {
            insuranceDialog = new InsuranceDialog(mContext);
            insuranceDialog.setItemClickListener(this);
        }
        insuranceDialog.setData(inforBeans);
        insuranceDialog.show();
    }

    /**
     * 获取所有参数
     *
     * @param params
     */
    @Override
    public void getParams(OrderCrateParams params) {
        params.setGoodName(item_cargo.getTextCenter());
        params.setGoodVolume(item_size.getTextCenter());
        params.setGoodWeight(item_weight.getTextCenter());
        params.setMoney(item_price.getTextCenter());
        if (item_cargo_price.isShown()) {
            params.setGoodPrice(item_cargo_price.getTextCenter());
        }

    }

    /**
     * 创建订单成功
     *
     * @param o
     */
    @Override
    public void finishSuccess(OrderInfoBean o) {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show("下单成功");
        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_pay)
                .withString(Param.TRAN, o.getId())
                .withInt(Param.TYPE, 1)
                .navigation((Activity) mContext, 1);
        finish();
    }

    @Override
    public void showInsurancePrice(PolicyCaculParam result) {
        item_cargo_price.setTextRight(String.format("保费%s元", result.getPolicyPrice()));
        item_cargo_price.setTextCenter(result.getGoodPrice());
    }

    /**
     * 初始化保险信息
     *
     * @param goodPrice
     * @param insureUsername
     */
    @Override
    public void initInsuranceInfo(String goodPrice, String insureUsername) {
        item_insurance_name.setTextCenter(insureUsername);
        item_cargo_price.setTextCenter(goodPrice);
    }

    /**
     * 显示货物种类
     *
     * @param cargoType
     */
    @Override
    public void showCargoTypes(List<CargoTypeAndColorBeans> cargoType) {
        if (cargoTypeDialog == null) {
            cargoTypeDialog = PickerDialogUtils.initPickerDialog(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    presenter.switchCargoType(options1);
                }
            });
            cargoTypeDialog.setTitleText("选择货物分类");
            cargoTypeDialog.setPicker(cargoType);
        }
        cargoTypeDialog.show();
    }

    @Override
    public void switchCargoType(CargoTypeAndColorBeans cargoTypeAndColorBeans) {
        item_cargo_type.setTextCenter(cargoTypeAndColorBeans.getName());
    }

    /**
     * 初始化承运人信息
     *
     * @param result
     */
    @Override
    public void initOwnerInfo(OrderSelectOwnerInfoBean result) {
        if (result == null) {
            item_owner.setSRCLeftShow(0);
            item_owner.setTextCenter("");
        } else {
            item_owner.setSRCLeftShow(2);
            RoundedBackgroundSpan span = new RoundedBackgroundSpan(mContext);
            span.setBordColor(getResources().getColor(R.color.color_of_dceeea));
            span.setTextColor(getResources().getColor(R.color.color_of_43bfa3));
            span.setCornerRadius(DeviceUtils.dip2px(mContext, 1.5f));
            span.setPadding(DeviceUtils.dip2px(mContext, 4f), DeviceUtils.dip2px(mContext, 1), DeviceUtils.dip2px(mContext, 4), DeviceUtils.dip2px(mContext, 1.5f));
            span.setMargin(DeviceUtils.dip2px(mContext, 5f), 0, 0, 0);
            SpannableStringBuilder builder = new SpannableStringBuilder(CommonUtils.getText(result.getCarNumber(), ""));
            int start = builder.length();
            builder.append(result.getOwnerName()).append("\t")
                    .append(result.getOwnerMobile());
            builder.setSpan(span, start, builder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            item_owner.setTextCenter(builder);
        }
    }

    /**
     * 初始化司机信息
     *
     * @param result
     */
    @Override
    public void initDriverInfo(OrderSelectDriverInfoBean result) {
        if (result == null) {
            item_driver.setSRCLeftShow(0);
            item_driver.setTextCenter("");
        } else {
            item_driver.setSRCLeftShow(2);
            item_driver.setTextCenter(result.getContact() + "  " + result.getMobile());
        }
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        presenter.onChangeTime(options1, options2, options3);
    }

    private boolean check(boolean show) {
        Utils.setButton(bt_sum, false);
        if (TextUtils.isEmpty(tv_start_contact.getText().toString())) {
            if (show) {
                ToastUtils.getInstance().show(tv_start_contact.getHint().toString());
            }
            return false;
        }
        if (TextUtils.isEmpty(tv_end_contact.getText().toString())) {
            if (show) {
                ToastUtils.getInstance().show(tv_end_contact.getHint().toString());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_start_time.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_start_time.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_cargo.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_cargo_type.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo_type.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_weight.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_weight.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_size.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_size.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_price.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_price.getTextCenterHide());
            }
            return false;
        }
//        if (TextUtils.isEmpty(item_pay_way.getTextCenter())) {
//            if (show) {
//                ToastUtils.getInstance().show(item_pay_way.getTextCenterHide());
//            }
//            return false;
//        }
        if (item_cargo_price.getVisibility() == View.VISIBLE && TextUtils.isEmpty(item_cargo_price.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo_price.getTextCenterHide());
            }
            return false;
        }
        if (item_insurance_name.getVisibility() == View.VISIBLE && TextUtils.isEmpty(item_insurance_name.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_insurance_name.getTextCenterHide());
            }
            return false;
        }
        Utils.setButton(bt_sum, true);
        return true;
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }

    /**
     * 编辑被保险人
     *
     * @param dialog
     * @param position
     * @param def
     */
    @Override
    public void onClickEdite(DialogControl.IDialog dialog, int position, InsuranceInfoBean def) {
        dialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamMNLM.activity_insured_info)
                .withInt(Param.TYPE, 1)
                .withParcelable(Param.TRAN, def)
                .navigation((Activity) mContext, 100);
    }

    /**
     * 点击添加新的被保险人
     *
     * @param dialog
     */
    @Override
    public void onClickAdd(DialogControl.IDialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamMNLM.activity_insured_info)
                .withInt(Param.TYPE, 0)
                .navigation((Activity) mContext, 100);

    }

    @Override
    public void onChoice(DialogControl.IDialog dialog, int position, InsuranceInfoBean def) {
        dialog.dismiss();
        presenter.onChangeInsuranceInfo(position, def);
        item_insurance_name.setTextCenter(def.getInsuredType() == 2 ? def.getCompanyName() : def.getUsername());
    }
}
