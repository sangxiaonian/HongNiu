package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.utils.PickerDialogUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @data 2020/2/7
 * @Author PING
 * @Description 我的订单页面
 */
@Route(path = ArouterParamApp.activity_my_order)
public class MyOrderActivity extends CompanyBaseActivity implements OnOptionsSelectListener, View.OnClickListener {

    private RoleOrder role;
    private TextView tvTitle;
    private TextView tvRole;
    private View ll_filtrate;
    private ArrayList<String> roles;
    private OptionsPickerView pickerView;

    private CompanyBaseFragment currentFragment,driverFragment,shipperFragment,carrierFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        role = (RoleOrder) getIntent().getSerializableExtra(Param.TRAN);
        if (role==null){
            role=RoleOrder.SHIPPER;
        }
        setWhitToolBar("");
        setToolbarSrcRight(R.drawable.icon_search_434445);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArouterUtils.getInstance().builder(ArouterParamApp.activity_search_order)
                       .withSerializable(Param.TRAN,role)
                       .navigation(mContext);
            }
        });
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        pickerView = PickerDialogUtils.initPickerDialog(mContext, this);
        tvTitle = findViewById(R.id.tv_title);
        tvRole = findViewById(R.id.tv_role);
        ll_filtrate = findViewById(R.id.ll_filtrate);


    }

    @Override
    protected void initData() {
        super.initData();
        roles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.roles)));
        pickerView.setPicker(roles);
        pickerView.setTitleText("选择身份类型");
        switchFragment(role);
        tvRole.setText(role.getName());

    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_filtrate.setOnClickListener(this);
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        role=getRole(options1);
        switchFragment(role);
        tvRole.setText(role.getName());
    }

    private RoleOrder getRole(int options1) {
        RoleOrder role=RoleOrder.SHIPPER;
        if (options1==0){
            role= RoleOrder.SHIPPER ;
        }else if (options1==1){
            role=RoleOrder.CARRIER ;
        }else if (options1==2){
            role=RoleOrder.DRIVER;
        }
        return role;
    }

    private void switchFragment(RoleOrder role) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment!=null){
            transaction.hide(currentFragment);
        }
        if (role==RoleOrder.SHIPPER ){
            if (shipperFragment==null){
                shipperFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Param.TRAN,role);
                shipperFragment.setBundle(bundle);
                transaction.add(R.id.content,shipperFragment);
            }else {
                transaction.show(shipperFragment);
            }
            currentFragment=shipperFragment;

        }else if (role==RoleOrder.CARRIER ){
            if (carrierFragment==null){
                carrierFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Param.TRAN,role);
                carrierFragment.setBundle(bundle);
                transaction.add(R.id.content,carrierFragment);
            }else {
                transaction.show(carrierFragment);
            }
            currentFragment=carrierFragment;
        }else if (role==RoleOrder.DRIVER){
            if (driverFragment==null){
                driverFragment= (CompanyBaseFragment) ArouterUtils.getInstance().builder(ArouterParamApp.fragment_my_order).navigation();
                Bundle bundle=new Bundle();
                bundle.putSerializable(Param.TRAN,role);
                driverFragment.setBundle(bundle);
                transaction.add(R.id.content,driverFragment);
            }else {
                transaction.show(driverFragment);
            }
            currentFragment=driverFragment;
        }
        transaction.commit();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ll_filtrate){
//            pickerView.setSelectOptions(role.getType());
            pickerView.show();
        }
    }
}
