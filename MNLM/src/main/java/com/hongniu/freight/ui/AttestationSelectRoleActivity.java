package com.hongniu.freight.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.recycle.adapter.SelectAdapter;
import com.fy.androidlibrary.widget.recycle.utils.XLineDivider;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;

import java.util.ArrayList;
import java.util.List;

/**
 * @data 2020/2/24
 * @Author PING
 * @Description 选择认证的角色身份
 */
@Route(path = ArouterParamApp.activity_attestation_select_role)
public class AttestationSelectRoleActivity extends CompanyBaseActivity implements SelectAdapter.SingleSelectedListener<AttestationSelectRoleActivity.ItemInfo>, View.OnClickListener {

    private TextView btSum;
    private RecyclerView rv;
    private List<ItemInfo> itemInfos;
    private SelectAdapter<ItemInfo> selectAdapter;
    private Role role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_attestation_select_role);
        setWhitToolBar("");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        btSum = findViewById(R.id.bt_sum);
        rv = findViewById(R.id.rv);
    }

    @Override
    protected void initData() {
        super.initData();

        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);

        itemInfos = new ArrayList<>();
        itemInfos.add(new ItemInfo("公司托运人", "货物所有者/货物管理者", Role.SHIPPER_COMPANY));
        itemInfos.add(new ItemInfo("个人托运人", "货物所有者/货物管理者", Role.SHIPPER_PERSONAL));
        itemInfos.add(new ItemInfo("公司承运人", "货物运输过程中的实际承运人", Role.CARRIER_COMPANY));
        itemInfos.add(new ItemInfo("个人承运人", "货物运输过程中的实际承运人", Role.CARRIER_PERSONAL));
        itemInfos.add(new ItemInfo("司机", "货物运输过程中的驾驶员",  Role.DRIVER));
         selectAdapter = new SelectAdapter<ItemInfo>(mContext, rv) {
            @Override
            public void initView(View itemView, int position, ItemInfo itemInfo, boolean select) {
                TextView tvTitle = itemView.findViewById(R.id.tv_title);
                ImageView img_select = itemView.findViewById(R.id.img_select);
                TextView tv_describe = itemView.findViewById(R.id.tv_describe);
                tvTitle.setText(itemInfo.title);
                tv_describe.setText(itemInfo.describe);
                img_select.setImageResource(select ? R.mipmap.icon_xz_36 : R.mipmap.icon_wxz_36);
            }
        };
        selectAdapter.setCanEmpty(false);
        selectAdapter.setSingle(true);


        selectAdapter.setSingleSelectedListener(this);
        selectAdapter.setItemLayoutID(R.layout.mnlm_item_attesstaiton_role);
        rv.setAdapter(selectAdapter);
        XLineDivider tagLine = new XLineDivider()
                .setmOrientation(LinearLayoutManager.VERTICAL)
                .setmDividerHeight(DeviceUtils.dip2px(mContext, 10))
                .setDividerColor(Color.TRANSPARENT)
                .setHeadGap(DeviceUtils.dip2px(mContext, 20), DeviceUtils.dip2px(mContext, 1))
                .hideLast(false);
        rv.addItemDecoration(tagLine);
        selectAdapter.notifyAllItem(itemInfos);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
    }

    /**
     * 单选时候
     *
     * @param position
     * @param selected
     * @param check
     */
    @Override
    public void onSingleSelected(int position, ItemInfo selected, boolean check) {
          role = selected.role;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            if (role!=null) {
                ArouterUtils.getInstance().builder(ArouterParamApp.activity_attestation_role_activity)
                        .withSerializable(Param.TRAN,role)
                        .navigation(mContext);
            }else {
                ToastUtils.getInstance().show("请选择你的身份");
            }
        }
    }


    class ItemInfo {
        String title;
        String describe;
        Role role;

        public ItemInfo(String title, String describe, Role role) {
            this.title = title;
            this.describe = describe;
            this.role = role;
        }


    }
}
