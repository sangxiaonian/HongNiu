package com.hongniu.freight.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;

/**
 * @data 2020/2/24
 * @Author PING
 * @Description 身份认证页面
 */
@Route(path = ArouterParamApp.activity_attestation_role_activity)
public class AttestationRoleActivityActivity extends CompanyBaseActivity {

    private Role role;
    private boolean canNext;//是否可以进行下一步 true 可以,默认为true

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attestation_role_activity);
        role = (Role) getIntent().getSerializableExtra(Param.TRAN);
        canNext = getIntent().getBooleanExtra(Param.TYPE, true);
        setWhitToolBar(getTitle(role));
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        String route = null;
        if (role == Role.DRIVER) {
            route = ArouterParamApp.fragment_attestation_driver;
        } else if (role == Role.CARRIER_PERSONAL) {
            route = ArouterParamApp.fragment_attestation_carrier_personal;
//            route = "个人承运人身份认证";
        } else if (role == Role.CARRIER_COMPANY) {
//            route = "公司承运人身份认证";
            route = ArouterParamApp.fragment_attestation_carrier_company;
        } else if (role == Role.SHIPPER_COMPANY) {
//            route = "公司托运人身份认证";
            route = ArouterParamApp.fragment_attestation_shipper_company;
        } else if (role == Role.SHIPPER_PERSONAL) {
//            route = "个人托运人身份认证";
            route = ArouterParamApp.fragment_attestation_shipper_personal;

        }
        if (route != null) {
            CompanyBaseFragment fragment = (CompanyBaseFragment) ArouterUtils.getInstance().builder(route).navigation();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Param.TRAN, canNext);
            fragment.setBundle(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
        }
    }


    private String getTitle(Role role) {
        return role.getName() + "身份认证";
    }

}
