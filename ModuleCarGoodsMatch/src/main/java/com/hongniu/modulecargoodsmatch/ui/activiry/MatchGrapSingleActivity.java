package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarInforBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.entity.PayParam;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.widget.dialog.ListDialog;
import com.hongniu.baselibrary.widget.dialog.PayDialog;
import com.hongniu.baselibrary.widget.pay.PayWayView;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.GoodsOwnerInforBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.thirdlibrary.pay.entiy.PayBean;

import java.util.ArrayList;

import static com.hongniu.baselibrary.config.Param.isDebug;

/**
 * @data 2019/5/12
 * @Author PING
 * @Description 我要抢单页面
 */
@Route(path = ArouterParams.activity_match_grap_single)
public class MatchGrapSingleActivity extends BaseActivity implements View.OnClickListener, PayWayView.OnPayTypeChangeListener, PayDialog.OnClickPayListener {

    private TextView tvCarNum;
    private TextView tvCarType;
    private EditText etPrice;
    private Button btSubmit;
    private ViewGroup llCar;
    private ListDialog<CarInforBean> dialog;
    private XAdapter<CarInforBean> adapter;
    private ArrayList<CarInforBean> datas;
    private CarInforBean infor;//选择的车辆信息
    private String id;
    PayDialog payDialog;
    private String grapId;//抢单id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_grap_single);
        setToolbarTitle("XX的车货匹配");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        payDialog = new PayDialog();
        dialog = new ListDialog<>();
        dialog.setAdapter(adapter = new XAdapter<CarInforBean>(mContext, datas = new ArrayList<>()) {
            @Override
            public BaseHolder<CarInforBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CarInforBean>(mContext, parent, R.layout.match_item_grap_car_infor) {
                    @Override
                    public void initView(View itemView, int position, final CarInforBean data) {
                        super.initView(itemView, position, data);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                        TextView tvCarType = itemView.findViewById(R.id.tv_car_type);
                        TextView tvCarOwner = itemView.findViewById(R.id.tv_car_owner);
                        tvCarNum.setText(data.getCarNumber() == null ? "" : data.getCarNumber());
                        tvCarType.setText(data.getCartypename() == null ? "" : data.getCartypename());
                        String name = data.getContactName() == null ? "" : data.getContactName();
                        String phone = data.getContactMobile() == null ? "" : data.getContactMobile();
                        tvCarOwner.setText("车主：" + name + " " + phone);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                changeInfor(data);
                                dialog.dismiss();
                            }
                        });
                    }
                };
            }
        });
        tvCarNum = findViewById(R.id.tv_car_num);
        tvCarType = findViewById(R.id.tv_car_type);
        btSubmit = findViewById(R.id.bt_sum);
        etPrice = findViewById(R.id.et_price);
        llCar = findViewById(R.id.ll_car);


    }

    private void changeInfor(CarInforBean data) {
        if (data != null) {
            this.infor = data;
            tvCarNum.setText(data.getCarNumber() == null ? "" : data.getCarNumber());
            tvCarType.setText(data.getCartypename() == null ? "" : data.getCartypename());
        }
    }

    @Override
    protected void initData() {
        super.initData();
        changeInfor(null);
        payDialog.setTitle("支付抢单意向金");
        dialog.setTitle("选择抢单车辆");
        dialog.setDescribe(null);
        String title = getIntent().getStringExtra(Param.TITLE);
        id = getIntent().getStringExtra(Param.TRAN);
        setToolbarTitle(title + "的车货匹配");
        queryCarInfor();

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSubmit.setOnClickListener(this);
        llCar.setOnClickListener(this);
        payDialog.setChangeListener(this);
        payDialog.setPayListener(this);
    }


    private void queryCarInfor() {
        HttpAppFactory.getCarList(1)
                .subscribe(new NetObserver<PageBean<CarInforBean>>(this) {
                    @Override
                    public void doOnSuccess(PageBean<CarInforBean> data) {
                        datas.clear();
                        if (data != null && data.getList() != null) {
                            datas.addAll(data.getList());
                        }
                        adapter.notifyDataSetChanged();
                        if (datas.size() == 0) {
                            //添加车辆
                            ArouterUtils.getInstance()
                                    .builder(ArouterParamLogin.activity_car_infor)
                                    .navigation((Activity) mContext, 1);

                        } else if (datas.size() == 1) {
                            changeInfor(datas.get(0));
                        } else {
                            dialog.show(getSupportFragmentManager(), "");
                        }

                    }
                });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (infor == null) {
                ToastUtils.getInstance().show("请选择车辆");
            } else if (TextUtils.isEmpty(etPrice.getText().toString().trim())) {
                ToastUtils.getInstance().show("请输入意向金");

            } else {
                String price = etPrice.getText().toString().trim();
                payDialog.setDescribe("意向金 " + price + "元");
                HttpMatchFactory
                        .grapMatch(id, infor.getId(), price)
                        .subscribe(new NetObserver<GoodsOwnerInforBean>(this) {
                            @Override
                            public void doOnSuccess(GoodsOwnerInforBean data) {
                                grapId=data.id;
                                payDialog.show(getSupportFragmentManager(), "");
                            }
                        });
            }


        } else if (v.getId() == R.id.ll_car) {
            dialog.show(getSupportFragmentManager(), "");
        }
    }

    /**
     * 支付方式更改监听
     *
     * @param payType
     * @param yueWay  余额支付方式更改监听 0 企业支付 1余额支付
     */
    @Override
    public void onPayTypeChang(int payType, int yueWay) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            int payResult = data.getIntExtra("payResult", 0);
            String msg = "";

            switch (payResult) {
                case 1000://成功
                    msg = "支付成功！";
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(msg);
                    finish();
                    break;
                case 2000://失败
                    msg = "支付失败！";
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(msg);
                    break;
                case 3000://取消
                    msg = "取消支付";
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(msg);
                    break;
            }
        }else if (requestCode==2){
            queryCarInfor();
        }
    }

    /**
     * 点击支付
     *
     * @param amount  支付金额
     * @param payType 1 余额 2微信 3支付宝 4银联
     * @param yueWay  余额支付方式更改监听 0 企业支付 1余额支付
     */
    @Override
    public void onClickPay(String amount, final int payType, int yueWay) {
        final PayParam payParam = new PayParam();
        payParam.setPaybusiness(3);
        payParam.setMatchingId(grapId);//抢单ID
//        0微信支付 1银联支付 2线下支付3 支付宝支付 4余额支付 5企业支付
        int type = -1;
        switch (payType) {
            case 1:
                if (yueWay==0){
                    type=5;
                }else {
                    type=4;
                }
                break;
            case 2:
                type=0;
                break;
            case 3:
                type=3;
                break;
            case 4:
                type=1;
                break;
        }
        payParam.setPayType(type);
        final int finalType = type;
        HttpAppFactory.pay(payParam)
                .subscribe(new NetObserver<PayBean>(this) {
                    @Override
                    public void doOnSuccess(PayBean data) {
                        ArouterUtils.getInstance()
                                .builder(ArouterParamOrder.activity_waite_pay)
                                .withInt("payType", finalType)
                                .withParcelable("payInfor",data)
                                .withBoolean("ISDEUBG", isDebug)
                                .withString("ORDERID",grapId)
                                .withBoolean("havePolicy",false)
                                .withInt("queryType",1)
                                .navigation((Activity) mContext,1);
                    }
                });


    }
}
