package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CreatInsuranceBean;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.entity.OrderInsuranceInforBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.PayPasswordKeyBord;
import com.hongniu.baselibrary.widget.dialog.AccountDialog;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OnItemClickListener;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.control.OrderPayControl;
import com.hongniu.moduleorder.present.OrderPayPresenter;
import com.hongniu.moduleorder.widget.PayAleartPop;
import com.hongniu.moduleorder.widget.dialog.BuyInsuranceDialog;
import com.hongniu.moduleorder.widget.dialog.InsuranceDialog;
import com.sang.common.event.BusFactory;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 订单支付界面
 * <p>
 * 此界面逻辑稍显复杂，在此处进行简单说明：
 * 共分为2大类：1 支付方式：线上、线下  2 是否购买保险
 * <p>
 * 线上支付：
 * 判断是否有购买保险，如果有，支付金额为保险和运费 无则仅仅支付运费
 * <p>
 * 线下支付：
 * 判断是否有购买保险，如果有， 保险，此时必须选择微信支付，支付金额为保险费用
 * 不购买保险，则直接显示完成订单
 */
@Route(path = ArouterParamOrder.activity_order_pay)
public class OrderPayActivity extends BaseActivity implements OrderPayControl.IOrderPayView, RadioGroup.OnCheckedChangeListener, View.OnClickListener, BuyInsuranceDialog.OnBuyInsuranceClickListener, PayPasswordKeyBord.PayKeyBordListener, AccountDialog.OnDialogClickListener<OrderInsuranceInforBean>, OnItemClickListener<OrderInsuranceInforBean> {

    private TextView tvOrder;//订单号
    private ViewGroup btBuy;//购买保险
    private TextView tvPrice;//运费总额
    private RadioGroup rg;//支付方式
    private RadioButton rbOnline;//线上支付
    private RadioButton rbOffline;//线下支付
    private RadioGroup rg1;//余额支付方式
    private RadioButton rbCompany;//企业支付
    private RadioButton rbPerson;//个人支付
    private ViewGroup rlWechact;//微信支付
    private ImageView cbWechat;//选择是否微信支付
    private View imgDai;//代收款提示
    private ViewGroup rlAli;//支付宝

    private ImageView cbAli;//选择是否支付宝支付
    private ViewGroup rlUnion;//银联支付
    private ImageView cbUnion;//选择是银联支付

    private ViewGroup rlYue;//余额支付
    private ImageView cbYue;//选择余额支付


    private Button btPay;//支付订单
    private TextView tvPayAll;//支付总额


    private ViewGroup conInsurance;//保险条目
    private ViewGroup payOnline;//线上支付的支付方式
    private ViewGroup rl_tran;//运费显示条目
    private TextView tv_insurance_price;//保险金额
    private TextView tv_cargo_price;//货物金额
    private TextView bt_cancle_insurance;//取消保险
    private TextView tv_change_cargo_price;//更改货物金额
    private TextView tv_des;//订单描述
    private TextView tv_instances_per_infor;//被保险人信息


    private BuyInsuranceDialog buyInsuranceDialog;


    private OrderPayControl.IOrderPayPresent payPresent;
    private PayAleartPop aleartPop;
    private PayPasswordKeyBord payPasswordKeyBord;
    InsuranceDialog insuranceDialog;//被保险人选择信息diaolog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay);
        setToolbarRedTitle(getString(R.string.order_pay));
        initView();
        initData();
        initListener();

        payPresent = new OrderPayPresenter(this, this);
        onSelectYuePay();
//        ;

    }

    @Override
    protected void initView() {
        super.initView();
        rg1 = findViewById(R.id.rg1);
        rbCompany = findViewById(R.id.rb_company);
        rbPerson = findViewById(R.id.rb_person);
        tvOrder = findViewById(R.id.tv_order);
        btBuy = findViewById(R.id.con_buy);
        rg = findViewById(R.id.rg);
        rbOnline = findViewById(R.id.rb_online);
        rbOffline = findViewById(R.id.rb_offline);
        rlWechact = findViewById(R.id.rl_wechact);
        cbWechat = findViewById(R.id.checkbox);
        btPay = findViewById(R.id.bt_pay);
        tvPayAll = findViewById(R.id.tv_pay_all);
        tvPrice = findViewById(R.id.tv_price);
        conInsurance = findViewById(R.id.con_insurance);
        tv_insurance_price = findViewById(R.id.tv_insurance_price);
        tv_cargo_price = findViewById(R.id.tv_cargo_price);
        bt_cancle_insurance = findViewById(R.id.bt_cancle_insurance);
        tv_change_cargo_price = findViewById(R.id.tv_change_cargo_price);
        rl_tran = findViewById(R.id.rl_tran);
        tv_des = findViewById(R.id.tv_des);
        rlAli = findViewById(R.id.rl_ali);
        cbAli = findViewById(R.id.ali_box);
        rlUnion = findViewById(R.id.rl_union);
        cbUnion = findViewById(R.id.union_box);
        rlYue = findViewById(R.id.rl_yue);
        cbYue = findViewById(R.id.cb_yue);
        payOnline = findViewById(R.id.pay_online);
        imgDai = findViewById(R.id.img_dai);
        tv_instances_per_infor = findViewById(R.id.tv_instances_per_infor);

        buyInsuranceDialog = new BuyInsuranceDialog(mContext);
        aleartPop = new PayAleartPop(this);

        payPasswordKeyBord = new PayPasswordKeyBord(this);
        payPasswordKeyBord.setProgressListener(this);
        payPasswordKeyBord.sePaytListener(this);
        payPasswordKeyBord.setPayDes("付款金额");


        insuranceDialog = new InsuranceDialog(mContext);


    }

    @Override
    protected void initData() {
        super.initData();
        tvOrder.setText("订单号");
        tvPrice.setText("￥" + 0);
        //默认情况下显示购买保险的条目
        conInsurance.setVisibility(View.GONE);
        btBuy.setVisibility(View.VISIBLE);

        tv_instances_per_infor.setText(String.format(getString(R.string.order_pay_insurance_infor), "", ""));

        //如果需要屏蔽保险信息
        if (!Utils.showInscance()){
            btBuy.setVisibility(View.GONE);
            conInsurance.setVisibility(View.GONE);
        }

    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.PayOrder event) {
        if (event != null) {
            //此处判断是否是购买保险
            payPresent.saveTranDate(event.insurance, event.money, event.orderID, event.orderNum, this);
            rbOnline.performClick();
        }
        BusFactory.getBus().removeStickyEvent(event);
    }


    @Override
    protected void initListener() {
        super.initListener();
        btBuy.setOnClickListener(this);
        btPay.setOnClickListener(this);
        rlWechact.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);
        bt_cancle_insurance.setOnClickListener(this);
        tv_change_cargo_price.setOnClickListener(this);
        buyInsuranceDialog.setListener(this);
        rlAli.setOnClickListener(this);
        rlUnion.setOnClickListener(this);
        rlYue.setOnClickListener(this);
        imgDai.setOnClickListener(this);
        tv_instances_per_infor.setOnClickListener(this);
        rg1.setOnCheckedChangeListener(this);

        insuranceDialog.setListener(this);
        insuranceDialog.setItemClickListener(this);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_online) {//线上支付
            payPresent.onLineClick();
        } else if (checkedId == R.id.rb_offline) {//线下支付
            payPresent.onOffLineClick();
        } else if (checkedId == R.id.rb_company) {//企业账号

            payPresent.onChoiceCompanyPay();
        } else if ((checkedId == R.id.rb_person)) {//个人账户
            payPresent.onChoicePersonPay();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.con_buy) {//购买保险
            if (Utils.checkInfor()) {
                buyInsuranceDialog.show();
            } else {
                realNameAuthentication("购买保险前，请先完善个人信息");

            }
        } else if (i == R.id.rl_wechact) {//选择微信支付
            changePayType(0);
            payPresent.setPayType(0);
        } else if (i == R.id.rl_ali) {//选择支付宝
            changePayType(3);
            payPresent.setPayType(3);
        } else if (i == R.id.rl_union) {//选择银联
            changePayType(1);
            payPresent.setPayType(1);
        } else if (i == R.id.rl_yue) {//余额支付
            payPresent.onChoiceYuePay();
        } else if (i == R.id.bt_pay) {//支付订单
            payPresent.pay(this);
        } else if (i == R.id.bt_cancle_insurance) {//取消保险
            payPresent.clearInsurance();
        } else if (i == R.id.tv_change_cargo_price) {//修改保险金额
            buyInsuranceDialog.show();
        } else if (i == R.id.img_dai) {
            aleartPop.setContent(getString(R.string.pay_aleart));
            aleartPop.show(v);
        } else if (i == R.id.tv_instances_per_infor) {
            payPresent.showInsurancDialog(this);
        }
    }

    //更改支付方式
    private void changePayType(int payType) {
        cbWechat.setImageResource(payType == 0 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbAli.setImageResource(payType == 3 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbUnion.setImageResource(payType == 1 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        cbYue.setImageResource(payType == 4 ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
    }

    @Override
    public void entryClick(Dialog dialog, boolean checked, String cargoPrice) {
        payPresent.saveCargoInfor(cargoPrice, this);
        dialog.dismiss();
    }

    @Override
    public void noticeClick(BuyInsuranceDialog buyInsuranceDialog, boolean checked, int i) {
        buyInsuranceDialog.dismiss();
        H5Config h5Config;
        if (i == 0) {
            h5Config = new H5Config(getString(R.string.order_insruance_police), Param.insurance_polic, true);
        } else {
            h5Config = new H5Config(getString(R.string.order_insruance_notify), Param.insurance_notify, true);
        }
        ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(this);

    }


    @Override
    public void onBackPressed() {
        creatDialog("确认要离开支付？", "您的订单已创建，离开后可在订单页继续支付", "确定离开", "继续支付")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String btRight) {
        return Utils.creatDialog(mContext, title, content, btleft, btRight);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == 100) {//新增修改
                JLog.i("修改");
                payPresent.queryInsurance(data.getStringExtra(Param.TRAN), this);
            } else if (resultCode == 101) {//删除
                JLog.i("删除");
                payPresent.deletedInsurance(data.getStringExtra(Param.TRAN), this);
            }

        } else if (resultCode == Activity.RESULT_OK) {
            int payResult = data.getIntExtra("payResult", 0);
            String msg = "";

            switch (payResult) {
                case 1000://成功
                    msg = "支付成功！";
                    payPresent.paySucccessed();

                    break;
                case 2000://失败
                    msg = "支付失败！";

                    break;
                case 3000://取消
                    msg = "取消支付";

                    break;

            }
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(msg);

        } else if (requestCode == 1) {
            payPresent.queryWallInfor(this);
        }


    }


    /**
     * 根据传入的数值初始化界面数据
     *
     * @param money    订单金额
     * @param orderID  订单id
     * @param orderNum 订单号
     */
    @Override
    public void setTranDate(float money, String orderID, String orderNum) {
        tvOrder.setText("订单号" + orderNum);
        tvPrice.setText("￥" + money);
        buyInsuranceDialog.setOrderID(orderID);
    }

    /**
     * 如果是单独购买保险功能，显示购买保险界面
     */
    @Override
    public void showBuyInsurance() {
        tv_des.setText(R.string.order_pay_insruance_buy_des);
        setToolbarRedTitle(getString(R.string.order_pay_insruance_pay));
        rl_tran.setVisibility(View.GONE);

    }

    /**
     * 如果是支付订单界面，显示支付订单界面
     */
    @Override
    public void showPayOrder() {
        setToolbarRedTitle(getString(R.string.order_pay));
        tv_des.setText(R.string.order_pay_success_driver_start);
        rl_tran.setVisibility(View.VISIBLE);
    }

    /**
     * 显示选择购买保险后货物价格和保费信息
     *
     * @param cargoPrice
     * @param data
     */
    @Override
    public void showCargoInfor(String cargoPrice, String data) {

        try {
            float v = Float.parseFloat(data.substring(1));
            if (v > 0) {
                conInsurance.setVisibility(View.VISIBLE);
                btBuy.setVisibility(View.GONE);
                tv_cargo_price.setText(cargoPrice);
                tv_insurance_price.setText(data);
            } else {
                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("保险金额不能为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 显示不购买保险时候的界面
     */
    @Override
    public void showNoInsurance() {
        conInsurance.setVisibility(View.GONE);
        btBuy.setVisibility(View.VISIBLE);
        tv_cargo_price.setText("￥0.00");
        tv_insurance_price.setText("￥0.00");
    }

    /**
     * 选择是否是线上支付方式
     *
     * @param onLinePay true 线上支付
     */
    @Override
    public void showOnLinePays(boolean onLinePay) {
        payOnline.setVisibility(onLinePay ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示金额
     *
     * @param builder
     */
    @Override
    public void showPayCount(SpannableStringBuilder builder) {
        tvPayAll.setText(builder);
    }

    /**
     * 开始支付并调往支付界面
     *
     * @param data
     * @param payType
     * @param buyInsurance
     * @param orderId
     */
    @Override
    public void jumpToPay(PayBean data, int payType, boolean buyInsurance, String orderId) {
        //如果是申请支付，则直接跳转到支付完成
        if (btPay.getText().toString().trim().equals("申请支付")) {
            finish();
        } else {
            WaitePayActivity.startPay(this, payType, data, orderId, buyInsurance, Param.isDebug);
        }
    }

    /**
     * 线下支付，且无保险，直接回到主界面
     */
    @Override
    public void jumpToMain() {
        ArouterUtils.getInstance().builder(ArouterParamsApp.activity_main)
                .navigation(this);
        finish();
    }

    /**
     * 支付完成，开始购买保险
     *
     * @param price
     * @param orderNum
     * @param orderID
     */
    @Override
    public void jumpToInsurance(String price, String orderNum, String orderID) {
        CreatInsuranceBean creatInsuranceBean = new CreatInsuranceBean();
        creatInsuranceBean.setGoodsValue(price);
        creatInsuranceBean.setOrderNum(orderNum);
        creatInsuranceBean.setOrderID(orderID);
        ArouterUtils.getInstance().builder(ArouterParamOrder.activity_order_query_insurance)
                .withParcelable(Param.TRAN, creatInsuranceBean)
                .navigation(this);
        finish();
    }

    /**
     * 单独购买保险却尚未选择保险
     *
     * @param title
     */
    @Override
    public void noChoiceInsurance(String title) {
        ToastUtils.getInstance().show(title);
    }

    /**
     * 是否默认选中微信
     *
     * @param isEnough
     * @param payType
     */
    @Override
    public void isHasEnoughBalance(boolean isEnough, int payType) {
        //如果余额不充足，并且当前选中的是余额就选中微信
        if (!isEnough && payType == 4) {
            rlWechact.performClick();
            showNoEnoughBalance();
        }

    }

    /**
     * 选中余额支付，但是余额不足
     */
    @Override
    public void showNoEnoughBalance() {
        ToastUtils.getInstance().show("余额不足");
    }

    /**
     * 选中余额支付
     */
    @Override
    public void onSelectYuePay() {
        changePayType(4);
        payPresent.setPayType(4);
    }

    /**
     * 显示支付密码键盘
     *
     * @param money
     */
    @Override
    public void showPasswordDialog(double money) {

        payPasswordKeyBord.setPayCount(ConvertUtils.changeFloat(money, 2));
        payPasswordKeyBord.show();

    }

    /**
     * 没置过支付密码
     */
    @Override
    public void hasNoSetPassword() {
        creatDialog("使用余额支付前，必须设置泓牛支付密码", null, "取消", "去设置")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        ArouterUtils.getInstance()
                                .builder(ArouterParamLogin.activity_login_forget_pass)
                                .withInt(Param.TRAN, 1)
                                .navigation(mContext);
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    /**
     * 初始化完成之后进行选择
     */
    @Override
    public void initPayWay() {
        onSelectYuePay();
        rbPerson.performClick();
    }

    /**
     * 显示被保险人信息
     *
     * @param name   个人名称或者公司名称
     * @param number 个人身份照明或者公司税号
     */
    @Override
    public void showInsruanceUserInfor(String name, String number) {
        tv_instances_per_infor.setText(String.format(getString(R.string.order_pay_insurance_infor), name, number));
    }

    /**
     * 显示被保险人信息列表
     *
     * @param data
     */
    @Override
    public void showInsruanceUserInforDialog(List<OrderInsuranceInforBean> data) {
        insuranceDialog.setData(data);
        insuranceDialog.show();
    }

    /**
     * 是否有企业支付权限
     *
     * @param companyPayPermission true 有
     */
    @Override
    public void showCompanyInfor(boolean companyPayPermission) {
        rg1.setVisibility(companyPayPermission ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示是否为申请支付
     *
     * @param showApplyCompanyPay true 是
     */
    @Override
    public void showHasCompanyApply(boolean showApplyCompanyPay) {
        btPay.setText(showApplyCompanyPay ? "申请支付" : "支付订单");
        //如果是申请支付，则给出提示
        if (showApplyCompanyPay) {
            aleartPop.setContent(getString(R.string.order_pay_company_promiss_aleart));
            aleartPop.show(findViewById(R.id.rb_company));
        }
    }

    /**
     * 调往实名认证界面进行实名认证
     *
     * @param s
     */
    @Override
    public void realNameAuthentication(String s) {
        showAleart(s, new DialogControl.OnButtonRightClickListener() {
            @Override
            public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(OrderPayActivity.this, 1);
            }
        });
    }


    /**
     * 取消支付
     *
     * @param dialog
     */
    @Override
    public void onCancle(DialogControl.IDialog dialog) {
        dialog.dismiss();
    }

    /**
     * 密码输入完成
     *
     * @param dialog
     * @param count    金额
     * @param passWord 密码
     */
    @Override
    public void onInputPassWordSuccess(DialogControl.IDialog dialog, String count, String passWord) {
        dialog.dismiss();
        payPresent.setPayPassoword(passWord, this);
    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(DialogControl.IDialog dialog) {
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_login_forget_pass)
                .navigation(mContext);
    }

    /**
     * 从未设置过密码
     *
     * @param dialog
     */
    @Override
    public void hasNoPassword(DialogControl.IDialog dialog) {
//        ArouterUtils.getInstance()
//                .builder(ArouterParamLogin.activity_login_forget_pass)
//                .withInt(Param.TRAN, 1)
//                .navigation(mContext);
    }

    @Override
    public void onChoice(DialogControl.IDialog dialog, int position, OrderInsuranceInforBean bean) {
        dialog.dismiss();
        payPresent.onSelectInsurancUserInfro(position, bean);
    }

    @Override
    public void onAddClick(DialogControl.IDialog dialog) {
        dialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_login_insured)
                .withInt(Param.TYPE, 0)
                .navigation((Activity) mContext, 100);
    }

    /**
     * 选择被保险人编辑按钮被点击，此时编辑被保险人数据
     *
     * @param position
     * @param orderInsuranceInforBean
     */
    @Override
    public void onItemClick(int position, OrderInsuranceInforBean orderInsuranceInforBean) {
        insuranceDialog.dismiss();
        ArouterUtils.getInstance()
                .builder(ArouterParamLogin.activity_login_insured)
                .withInt(Param.TYPE, 1)
                .withParcelable(Param.TRAN, orderInsuranceInforBean)
                .navigation((Activity) mContext, 100);

    }
}
