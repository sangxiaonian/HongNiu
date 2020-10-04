package com.sang.modulebreakbulk.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.PayPasswordKeyBord;
import com.hongniu.baselibrary.widget.dialog.PayDialog;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.fy.androidlibrary.toast.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentInfoBean;
import com.sang.modulebreakbulk.net.HttpBreakFactory;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.List;

import io.reactivex.Observable;

import static com.hongniu.baselibrary.arouter.ArouterParamsApp.activity_way_bill;
import static com.hongniu.baselibrary.config.Param.isDebug;

/**
 * @data 2019/9/22
 * @Author PING
 * @Description 零担发货发货记录
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_consignment_record)
public class BreakbulkConsignmentRecordActivity extends RefrushActivity<BreakbulkConsignmentInfoBean> implements PayPasswordKeyBord.PayKeyBordListener, PayDialog.OnClickPayListener {

    private PayPasswordKeyBord payPasswordKeyBord;
    private PayDialog payDialog;
    private PayParam payParam;
    private BreakbulkConsignmentInfoBean info;//支付信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_consignment_record);
        setToolbarTitle("发货记录");
        initView();

        initData();
        initListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        payParam = new PayParam();
        payPasswordKeyBord = new PayPasswordKeyBord(this);

        payPasswordKeyBord.setPayDes("付款金额");

        payDialog = new PayDialog();
    }

    @Override
    protected void initListener() {
        super.initListener();
        payDialog.setPayListener(this);
        payPasswordKeyBord.setProgressListener(this);
        payPasswordKeyBord.sePaytListener(this);
    }

    @Override
    protected Observable<CommonBean<PageBean<BreakbulkConsignmentInfoBean>>> getListDatas() {
        return HttpBreakFactory.queryBreakbulkConsignmentRecord(currentPage);
    }

    @Override
    protected XAdapter<BreakbulkConsignmentInfoBean> getAdapter(List<BreakbulkConsignmentInfoBean> datas) {
        return new XAdapter<BreakbulkConsignmentInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<BreakbulkConsignmentInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BreakbulkConsignmentInfoBean>(mContext, parent, R.layout.item_breakbulk_record) {
                    @Override
                    public void initView(View itemView, int position, final BreakbulkConsignmentInfoBean data) {
                        super.initView(itemView, position, data);
                        TextView tv_company = itemView.findViewById(R.id.tv_company);
                        TextView tv_address = itemView.findViewById(R.id.tv_address);
                        ImageView img_call = itemView.findViewById(R.id.img_call);//收货人信息
                        ImageView img_phone = itemView.findViewById(R.id.img_phone);
                        ImageView img_chat = itemView.findViewById(R.id.img_chat);
                        TextView tv_cargo_name = itemView.findViewById(R.id.tv_cargo_name);
                        TextView tv_price = itemView.findViewById(R.id.tv_price);
                        TextView tv_order = itemView.findViewById(R.id.tv_order);
                        TextView tv_logistics_company = itemView.findViewById(R.id.tv_logistics_company);
                        TextView tv_state = itemView.findViewById(R.id.tv_state);
                        final TextView bt_pay = itemView.findViewById(R.id.bt_pay);


                        tv_company.setText(String.format("%s\t\t%s", CommonUtils.getText(data.getReceiptUserName()), CommonUtils.getText(data.getReceiptMobile())));
                        tv_address.setText(CommonUtils.getText(data.getDestinationInfo()));
                        tv_cargo_name.setText(String.format("%s\t\t%s\t\t%s", CommonUtils.getText(data.getGoodsName()), CommonUtils.getText(data.getGoodsWeight()), CommonUtils.getText(data.getGoodsVolume())));


                        String balanceFare = CommonUtils.getText(data.getBalanceFare());
                        balanceFare = TextUtils.isEmpty(balanceFare) ? "" : String.format("\t\t运费差额：%s", balanceFare);
                        tv_price.setText(String.format("预估运费：%s%s", CommonUtils.getText(data.getEstimateFare()), balanceFare));

                        tv_logistics_company.setText(String.format("物流公司：%s", CommonUtils.getText(data.getLogisticsCompanyName())));
                        String wayBillNumber = CommonUtils.getText(data.getWaybillNum());
                        if (TextUtils.isEmpty(wayBillNumber)) {
                            tv_order.setVisibility(View.GONE);
                        } else {
                            tv_order.setVisibility(View.VISIBLE);
                            tv_order.setText(String.format("运输单号：%s", wayBillNumber));
                        }

                        final int status = data.getLtlStatus();
                        tv_state.setText(getBtMsg(status));
                        if (status == 0) {
                            bt_pay.setText("支付运费");
                            bt_pay.setVisibility(View.VISIBLE);
                        } else if (status == 3) {
                            bt_pay.setText("支付运费差额");
                            bt_pay.setVisibility(View.VISIBLE);
                        } else if (status == 6 || status == 5) {
                            bt_pay.setText("查看运单状态");
                            bt_pay.setVisibility(View.VISIBLE);
                        } else {
                            bt_pay.setVisibility(View.GONE);
                        }


                        img_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommonUtils.call(mContext, data.getReceiptMobile());
                            }
                        });
                        img_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommonUtils.call(mContext, TextUtils.isEmpty(data.getLogisticsCompanyCP()) ? data.getLogisticsCompanyTel() : data.getLogisticsCompanyCP());
                            }
                        });
                        img_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, data.getUserId(), data.getLogisticsCompanyName());
//                                ChactHelper.getHelper().startPriver(mContext, data.getLogisticsCompanyId(), data.getLogisticsCompanyName());
                            }
                        });

                        bt_pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (status == 0 || status == 3) {
                                    showPayDialog(data, status, bt_pay.getText().toString());
                                } else if (status == 6 || status == 5) {
                                    //查询运单状态
                                    ArouterUtils.getInstance().builder(activity_way_bill).withString(Param.TRAN, data.getWaybillNum()).navigation(mContext);
                                }
                            }
                        });

                    }
                };
            }
        };
    }

    /**
     * 根据状态
     *
     * @param ltlStatus
     * @return
     */
    private String getBtMsg(int ltlStatus) {
//        (0待支付运费1 待接单 2待支付运费差额 3已接单 4运输中 5 已完成)
//        零担货物状态(0待支付运费1(已支付)待接单 2已接单 3 待支付运费差额 4差额已支付 5运输中 6已完成)
        String msg = "";
        switch (ltlStatus) {
            case 0:
                msg = "待支付运费";
                break;
            case 1:
                msg = "(已支付)待接单";
                break;
            case 2:
                msg = "已接单";
                break;
            case 3:
                msg = "待支付运费差额";
                break;
            case 4:
                msg = "差额已支付";
                break;
            case 5:
                msg = "运输中";
                break;
            case 6:
                msg = "已完成";
                break;
        }
        return msg;
    }

    private void showPayDialog(BreakbulkConsignmentInfoBean data, int finalType, String text) {
        this.info = data;
        String price;
        if (finalType == 0) {
            //预估运费未支付
            price = data.getEstimateFare();
        } else if (finalType == 3) {
            price = data.getBalanceFare();
        } else {
            price = "0";
        }
        payParam.setLtlGoodsId(data.getId());

        payDialog.setTitle(text);
        payDialog.setPayAmount(Float.parseFloat(price));
        HttpAppFactory.queryAccountdetails()
                .subscribe(new NetObserver<WalletDetail>(this) {
                    @Override
                    public void doOnSuccess(WalletDetail data) {
                        payDialog.setShowCompany(data.getType());
                        payDialog.setWalletDetaile(data);
                        payDialog.show(getSupportFragmentManager(), "");
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
        ToastUtils.getInstance().show("支付已取消");
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

        if (!TextUtils.isEmpty(passWord)) {
            payParam.setPayPassword(ConvertUtils.MD5(passWord));
        }
        showPayDialog();
    }

    /**
     * 忘记密码
     *
     * @param dialog
     */
    @Override
    public void onForgetPassowrd(DialogControl.IDialog dialog) {
        dialog.dismiss();
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
        Utils.creatDialog(mContext, "使用余额支付前，必须设置泓牛支付密码", null, "取消", "去设置")
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
     * 点击支付
     *
     * @param amount  支付金额
     * @param payType 1 余额 2微信 3支付宝 4银联
     * @param yueWay  余额支付方式更改监听 0 企业支付 1余额支付 2 申请支付
     */
    @Override
    public void onClickPay(float amount, int payType, int yueWay) {
        payDialog.dismiss();
        payParam.setPaybusiness(4);
        payParam.setPayPassword(null);
//        0微信支付 1银联支付 2线下支付3 支付宝支付 4余额支付 5企业支付
        int type = -1;
        switch (payType) {
            case 1:
                if (yueWay == 0 || yueWay == 2) {
                    type = 5;
                } else {
                    type = 4;
                }
                break;
            case 2:
                type = 0;
                break;
            case 3:
                type = 3;
                break;
            case 4:
                type = 1;
                break;
        }
        payParam.setPayType(type);
        if (payType != 1) {
            showPayDialog();
        } else {
            if (Utils.querySetPassword()) {//设置过支付密码
                if (yueWay == 2) {
                    showPayDialog();
                } else {
                    try {
                        payPasswordKeyBord.setPayCount(ConvertUtils.changeFloat(amount, 2));
                        payPasswordKeyBord.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

                Utils.creatDialog(mContext, "使用余额支付前，必须设置泓牛支付密码", null, "取消", "去设置")
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
        }

    }

    private void showPayDialog() {
        HttpAppFactory.pay(payParam)
                .subscribe(new NetObserver<PayBean>(this) {
                    @Override
                    public void doOnSuccess(PayBean data) {
                        int type = 3;
                        if (info != null) {
                            if (info.getLtlStatus() == 0) {
                                //预估运费未支付
                                type = 3;
                            } else if (info.getLtlStatus() == 2) {
                                type = 4;
                            }
                        }
                        ArouterUtils.getInstance()
                                .builder(ArouterParamOrder.activity_waite_pay)
                                .withInt("payType", payParam.getPayType())
                                .withParcelable("payInfor", data)
                                .withBoolean("ISDEUBG", isDebug)
                                .withString("ORDERID", payParam.getLtlGoodsId())
                                .withBoolean("havePolicy", false)
                                .withInt("queryType", type)
                                .navigation((Activity) mContext, 1);
                    }
                });


    }
}
