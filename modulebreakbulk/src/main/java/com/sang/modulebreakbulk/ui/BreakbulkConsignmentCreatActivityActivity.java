package com.sang.modulebreakbulk.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentCreateParams;
import com.sang.modulebreakbulk.net.HttpBreakFactory;

/**
 * @data 2019/9/22
 * @Author PING
 * @Description 零担发货下单页面
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_consignment_creat_activity)
public class BreakbulkConsignmentCreatActivityActivity extends BaseActivity implements View.OnClickListener {


    String id;
    private boolean check;

    private ItemView item_start_company;
    private ItemView item_start_phone;
    private ItemView item_start_address;
    private ItemView item_cargo;
    private ItemView item_cargo_weight;
    private ItemView item_cargo_size;
    private ItemView item_consignee_company;
    private ItemView item_consignee_phone;
    private ItemView item_consignee_address;
    private ImageView img_check;
    private ItemView item_cargo_price;
    private EditText et_remark;
    private Button btEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_consignment_creat_activity);
        setToolbarTitle("零担发货");
        id = getIntent().getStringExtra(Param.TRAN);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        item_start_company = findViewById(R.id.item_start_company);
        item_start_phone = findViewById(R.id.item_start_phone);
        item_start_address = findViewById(R.id.item_start_address);
        item_cargo = findViewById(R.id.item_cargo);
        item_cargo_weight = findViewById(R.id.item_cargo_weight);
        item_cargo_size = findViewById(R.id.item_cargo_size);
        item_consignee_company = findViewById(R.id.item_consignee_company);
        item_consignee_phone = findViewById(R.id.item_consignee_phone);
        item_consignee_address = findViewById(R.id.item_consignee_address);
        img_check = findViewById(R.id.img_check);
        item_cargo_price = findViewById(R.id.item_cargo_price);
        et_remark = findViewById(R.id.et_remark);
        btEntry = findViewById(R.id.bt_entry);
    }

    @Override
    protected void initData() {
        super.initData();
        img_check.setImageResource(check ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btEntry.setOnClickListener(this);
        img_check.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_entry) {
            if (check(true)) {
                HttpBreakFactory.creatBreakbulk(getParams())
                        .subscribe(new NetObserver<Object>(this) {
                            @Override
                            public void doOnSuccess(Object data) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamsBreakbulk.activity_breakbulk_consignment_record)
                                        .navigation(mContext);
                                finish();
                            }
                        })
                ;

            }
        } else if (v.getId() == R.id.img_check) {
            check = !check;
            img_check.setImageResource(check ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
        }
    }

    private BreakbulkConsignmentCreateParams getParams() {
        BreakbulkConsignmentCreateParams params = new BreakbulkConsignmentCreateParams();
        params.setLogisticsCompanyId(id);
        params.setSendUserName(item_start_company.getTextCenter());
        params.setSendMobile(item_start_phone.getTextCenter());
        params.setStartPlaceInfo(item_start_address.getTextCenter());
        params.setReceiptUserName(item_consignee_company.getTextCenter());
        params.setReceiptCompanyName(item_consignee_company.getTextCenter());
        params.setReceiptMobile(item_consignee_phone.getTextCenter());
        params.setDestinationInfo(item_consignee_address.getTextCenter());
        params.setGoodsName(item_cargo.getTextCenter());
        params.setGoodsVolume(item_cargo_size.getTextCenter());
        params.setGoodsWeight(item_cargo_weight.getTextCenter());
        params.setIsToDoor(check ? 1 : 0);
        params.setEstimateFare(item_cargo_price.getTextCenter());
        params.setRemark(et_remark.getText().toString());
        return params;

    }


    private boolean check(boolean show) {
        if (TextUtils.isEmpty(item_start_company.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_start_company.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_start_phone.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_start_phone.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_start_address.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_start_address.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_cargo.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_cargo_weight.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo_weight.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_cargo_size.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo_size.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_consignee_company.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_consignee_company.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_consignee_phone.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_consignee_phone.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_consignee_address.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_consignee_address.getTextCenterHide());
            }
            return false;
        }

        if (TextUtils.isEmpty(item_cargo_price.getTextCenter())) {
            if (show) {
                ToastUtils.getInstance().show(item_cargo_price.getTextCenterHide());
            }
            return false;
        }
        return true;
    }
}
