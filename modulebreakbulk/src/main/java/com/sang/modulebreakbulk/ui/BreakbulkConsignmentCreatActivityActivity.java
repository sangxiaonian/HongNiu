package com.sang.modulebreakbulk.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.ItemView;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;

/**
 * @data 2019/9/22
 * @Author PING
 * @Description 零担发货下单页面
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_consignment_creat_activity)
public class BreakbulkConsignmentCreatActivityActivity extends BaseActivity implements View.OnClickListener {


    BreakbulkCompanyInfoBean infoBean;
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
        btEntry=findViewById(R.id.bt_entry);
    }

    @Override
    protected void initData() {
        super.initData();

        infoBean = getIntent().getParcelableExtra(Param.TRAN);
        if (infoBean != null) {
            item_start_company.setTextCenter(infoBean.getCompanyname());
            item_start_phone.setTextCenter(infoBean.getContact());
            item_start_address.setTextCenter(infoBean.getWebaddress());
        }

        img_check.setImageResource(check?R.mipmap.icon_xz_36:R.mipmap.icon_wxz_36);

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
        if (v.getId()==R.id.bt_entry){
            ToastUtils.getInstance().show("登陆");
        }else if (v.getId()==R.id.img_check){
            check=!check;
            img_check.setImageResource(check?R.mipmap.icon_xz_36:R.mipmap.icon_wxz_36);
        }
    }
}
