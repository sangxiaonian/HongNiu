package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParams;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CarInforBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.widget.dialog.ListDialog;
import com.hongniu.baselibrary.widget.dialog.PayDialog;
import com.hongniu.baselibrary.widget.pay.PayWayView;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;

import java.util.ArrayList;

/**
 * @data 2019/5/12
 * @Author PING
 * @Description 我要抢单页面
 */
@Route(path = ArouterParams.activity_match_grap_single)
public class MatchGrapSingleActivity extends BaseActivity implements View.OnClickListener, PayWayView.OnPayTypeChangeListener {

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
        payDialog=new PayDialog();
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
        setToolbarTitle(title+"的车货匹配");
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSubmit.setOnClickListener(this);
        llCar.setOnClickListener(this);
        payDialog.setChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            ToastUtils.getInstance().show("提交");
            if (infor==null){
                ToastUtils.getInstance().show("请选择车辆");
            }else if (TextUtils.isEmpty(etPrice.getText().toString().trim())){
                ToastUtils.getInstance().show("请输入意向金");

            }else {
                String price = etPrice.getText().toString().trim();
                payDialog.setDescribe("意向金 "+price+"元");
                payDialog.show(getSupportFragmentManager(),"");

//                HttpMatchFactory
//                        .grapMatch(id,infor.getId(),price)
//                        .subscribe(new NetObserver<Object>(this) {
//                            @Override
//                            public void doOnSuccess(Object data) {
//                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
//                                finish();
//                            }
//                        });
                ;
            }



        } else if (v.getId() == R.id.ll_car) {
            HttpAppFactory.getCarList(1)
                    .subscribe(new NetObserver<PageBean<CarInforBean>>(this) {
                        @Override
                        public void doOnSuccess(PageBean<CarInforBean> data) {
                            datas.clear();
                            if (data != null && data.getList() != null) {
                                datas.addAll(data.getList());
                            }
                            adapter.notifyDataSetChanged();
                            dialog.show(getSupportFragmentManager(), "");

                        }
                    });
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
        if (payType==2){
            ToastUtils.getInstance().show("微信支付");
        }else if (payType==4){
            ToastUtils.getInstance().show("银联支付");
        }else if (payType==3){
            ToastUtils.getInstance().show("支付宝支付");
        }else if (payType==1){
            if (yueWay==1){
                ToastUtils.getInstance().show("个人支付");
            }else {
                ToastUtils.getInstance().show("公司账户");
            }
        }


    }
}
