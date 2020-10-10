package com.hongniu.freight.ui;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.BindBlankParams;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.QueryBlankInforsBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.widget.DialogComment;

import java.util.List;


/**
 * @data 2019/3/3
 * @Author PING
 * @Description 绑定银行卡操作
 */
@Route(path = ArouterParamApp.activity_bind_blank_card)
public class BindBlankCardActivity extends CompanyBaseActivity implements View.OnClickListener, OnOptionsSelectListener {
    ItemTextView itemName;//姓名
    ItemTextView itemPhone;//手机号
    ItemTextView itemBlankCardNum;//银行卡号
    ItemTextView itemIDCard;//身份证号
    TextView tvHuaAleart;//身份证号
    private Button btSum;
    private ItemTextView itemBlank;
    private List<QueryBlankInforsBean> blanksInfors;//银行卡信息
    private OptionsPickerView<QueryBlankInforsBean> pickDialog;
    private String blankID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_bind_blank_card);
        setWhitToolBar("绑定银行卡");
        initView();
        initData();
        initListener();
        queryBlank();
    }

    @Override
    protected void initView() {
        super.initView();
        itemName = findViewById(R.id.item_name);//姓名
        itemBlank = findViewById(R.id.item_blank);
        tvHuaAleart = findViewById(R.id.tv_hua_aleart);

        itemPhone = findViewById(R.id.item_phone);//手机号
        itemBlankCardNum = findViewById(R.id.item_blank_card_num);//银行卡号
        itemIDCard = findViewById(R.id.item_id_card_num);//身份证号
        btSum = findViewById(R.id.bt_sum);
        pickDialog = PickerDialogUtils.creatPickerDialog(mContext, this)
                .setTitleText("请选择银行")
                .setSubmitColor(Color.parseColor("#48BAF3"))
                .build();

    }

    @Override
    protected void initData() {
        super.initData();
        PersonInfor personInfor = InfoUtils.getMyInfo();
        itemName.setTextCenter(personInfor.getContact());
        itemPhone.setTextCenter(personInfor.getMobile());
        itemIDCard.setTextCenter(personInfor.getIdnumber());
        tvHuaAleart.setVisibility(View.GONE);
        itemName.setEnabled(false);
        itemName.setEditable(false);
        itemPhone.setEnabled(false);
        itemPhone.setEditable(false);
        itemIDCard.setEnabled(false);
        itemIDCard.setEditable(false);

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        itemBlank.setOnClickListener(this);
    }

    private void queryBlank() {
        HttpAppFactory.queryBlanks()
                .subscribe(new NetObserver<List<QueryBlankInforsBean>>(this) {
                    @Override
                    public void doOnSuccess(List<QueryBlankInforsBean> data) {
                        blanksInfors = data;
                        pickDialog.setPicker(blanksInfors);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        DeviceUtils.closeSoft(this);
        if (v.getId() == R.id.bt_sum) {
            if (check()) {
                BindBlankParams params = new BindBlankParams();
                params.setType("1");
                params.setBankId(blankID);
                params.setCardNo(itemBlankCardNum.getTextCenter());
                params.setAccountName(itemName.getTextCenter());
                params.setMobile(itemPhone.getTextCenter());
                params.setIdnumber(itemIDCard.getTextCenter());
                params.setLinkAccountType("0");
                HttpAppFactory.bindBlanks(params)

                        .subscribe(new NetObserver<String>(this) {
                    @Override
                    public void doOnSuccess(String data) {
                        if (tvHuaAleart.getVisibility()==View.VISIBLE) {

                            new DialogComment.Builder()
                                    .setBtLeft("下次再看")
                                    .setBtRight("查看流程")
                                    .setDialogTitle("签约提醒")
                                    .setDialogContent("银行卡已绑定，因华夏银行要求，需完成签约后，方可进行充值及提现操作")
                                    .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                                        @Override
                                        public void onRightClick(View view, Dialog dialog) {
                                            dialog.dismiss();
                                            H5Config h5Config = new H5Config("签约流程", Param.HUAXIA, true);
                                            ArouterUtils.getInstance().builder(ArouterParamApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                        }
                                    })
                                    .setLeftClickListener(new DialogComment.OnButtonLeftClickListener() {
                                        @Override
                                        public void onLeftClick(View view, Dialog dialog) {
                                            dialog.dismiss();
                                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                        }
                                    })
                                    .creatDialog(mContext)
                                    .show();

                        }else {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                            finish();

                        }

                    }
                });
            }
        } else if (v.getId() == R.id.item_blank) {
            if (blanksInfors != null) {
                pickDialog.show();
            } else {
                queryBlank();
            }
        }
    }

    private boolean check() {
        if (TextUtils.isEmpty(itemName.getTextCenter())) {
            ToastUtils.getInstance().show(itemName.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemPhone.getTextCenter())) {
             ToastUtils.getInstance().show(itemPhone.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemBlankCardNum.getTextCenter())) {
             ToastUtils.getInstance().show(itemBlankCardNum.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemIDCard.getTextCenter())) {
             ToastUtils.getInstance().show(itemIDCard.getTextCenterHide());
            return false;
        }
        if (TextUtils.isEmpty(itemBlank.getTextCenter())) {
             ToastUtils.getInstance().show(itemBlank.getTextCenterHide());
            return false;
        }
        return true;
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        String name = blanksInfors.get(options1).getDescription();
        itemBlank.setTextCenter(name);
        blankID = blanksInfors.get(options1).getId();
        tvHuaAleart.setVisibility(name.contains("华夏") ? View.VISIBLE : View.GONE);
    }
}
