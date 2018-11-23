package com.hongniu.modulelogin.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 作者： ${PING} on 2018/11/23.
 */
@Route(path = ArouterParamLogin.fragment_login_my)
public class MyFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout llLoginOut;//退出登录
    private LinearLayout llContactService;//联系客服
    private LinearLayout llAboutUs;//关于我们
    private LinearLayout llMyCar;//我的车辆
    private LinearLayout llPersonInfor;//个人资料
    private LinearLayout llWallet;//收款方式
    private LinearLayout llNiu;//收款方式
    private TextView tvName, tvPhone;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_login_my, null);
        llLoginOut = inflate.findViewById(R.id.ll_login_out);
        llContactService = inflate.findViewById(R.id.ll_contact_service);
        llAboutUs = inflate.findViewById(R.id.ll_about_us);
        llMyCar = inflate.findViewById(R.id.ll_my_car);
        llPersonInfor = inflate.findViewById(R.id.ll_person_infor);
        llWallet = inflate.findViewById(R.id.ll_wallet);
        llNiu = inflate.findViewById(R.id.ll_niu);
        tvName = inflate.findViewById(R.id.tv_name);
        tvPhone = inflate.findViewById(R.id.tv_phone);


        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_new_light), false);

        if (Utils.getLoginInfor() != null) {
            if (Utils.checkInfor()) {
                tvName.setText(Utils.getPersonInfor().getContact() == null ? "待完善" : Utils.getPersonInfor().getContact());
            }
            tvPhone.setText(Utils.getLoginInfor().getMobile() == null ? "" : Utils.getLoginInfor().getMobile());
        }


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_new_light), false);
        }
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpPersonInfor(Event.UpPerson event) {
        if (event != null) {
            if (Utils.checkInfor()) {
                tvName.setText(Utils.getPersonInfor().getContact());
            }
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        llLoginOut.setClickable(true);
        llContactService.setClickable(true);
        llAboutUs.setClickable(true);
        llMyCar.setClickable(true);
        llPersonInfor.setClickable(true);


        llLoginOut.setOnClickListener(this);
        llContactService.setOnClickListener(this);
        llAboutUs.setOnClickListener(this);
        llMyCar.setOnClickListener(this);
        llPersonInfor.setOnClickListener(this);
        llWallet.setOnClickListener(this);
        llNiu.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ll_login_out) {
            new BottomAlertBuilder()
                    .setDialogTitle(getString(R.string.login_out_entry))
                    .setTopClickListener(new DialogControl.OnButtonTopClickListener() {
                        @Override
                        public void onTopClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();
                            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(getContext());
                            getActivity().finish();
                        }

                    })
                    .setBottomClickListener(new DialogControl.OnButtonBottomClickListener() {
                        @Override
                        public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();

                        }

                    }).creatDialog(new BottomAlertDialog(getContext())).show();


        } else if (i == R.id.ll_contact_service) {
            new CenterAlertBuilder()
                    .setDialogTitleSize(18)
                    .setDialogContentSize(15)
                    .setbtSize(18)
                    .setDialogSize(DeviceUtils.dip2px(getContext(), 300), DeviceUtils.dip2px(getContext(), 135))
                    .setDialogTitle(getString(R.string.login_contact_service))
                    .setDialogContent(getString(R.string.login_contact_phone))
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
                            CommonUtils.toDial(getContext(), getString(R.string.login_contact_phone));

                        }
                    }).creatDialog(new CenterAlertDialog(getContext())).show();

        } else if (i == R.id.ll_about_us) {

            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_about_us).navigation(getContext());
        } else if (i == R.id.ll_my_car) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_list).navigation(getContext());
        } else if (i == R.id.ll_person_infor) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(getContext());

        }  else if (i == R.id.ll_wallet) {
            ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_wallet).navigation(getContext());

        } else if (i == R.id.ll_niu) {

            ArouterUtils.getInstance()
                    .builder(ArouterParamsFinance.activity_finance_niu)
                    .navigation(getContext());
        }
    }
}
