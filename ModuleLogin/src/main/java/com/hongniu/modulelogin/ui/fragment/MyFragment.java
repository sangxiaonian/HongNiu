package com.hongniu.modulelogin.ui.fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.LoginBean;
import com.hongniu.baselibrary.entity.LoginPersonInfor;
import com.hongniu.baselibrary.event.Event;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.SharedPreferencesUtils;
import com.sang.common.widget.dialog.BottomAlertDialog;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.functions.Action;

/**
 * 作者： ${PING} on 2018/11/23.
 * 个人中心
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
    private ViewGroup card;//收款方式
    private TextView tvName, tvPhone;
    private ImageView imgHeard;
    private TextView tvNiu;

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
        card = inflate.findViewById(R.id.card);
        imgHeard = inflate.findViewById(R.id.img_heard);
        tvNiu = inflate.findViewById(R.id.tv_niu);


        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

        SpannableStringBuilder builder=new SpannableStringBuilder("邀请好友  玩转牛贝");
        ForegroundColorSpan span=new ForegroundColorSpan( getResources().getColor(R.color.color_new_light));
        builder.setSpan(span,5,builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvNiu.setText(builder);

        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);
        upPerson();


    }


    /**
     * @data 2019/3/3
     * @Author PING
     * @Description 更新个人资料
     */
    private void upPerson() {
            LoginPersonInfor personInfor = Utils.getPersonInfor();


        if (personInfor!=null) {
            String companyName = personInfor.getCompany() == null ? "" : personInfor.getCompany();
            String phone = TextUtils.isEmpty(personInfor.getMobile()) ? "" : personInfor.getMobile();
            String name = personInfor.getContact() == null ? "待完善" : personInfor.getContact();
            tvName.setText(name);
            tvPhone.setText(TextUtils.isEmpty(companyName) ? phone : companyName);
            ImageLoader.getLoader().loadHeaed(getContext(), imgHeard, personInfor.getLogoPath());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.white), true);
        }
    }


    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpPersonInfor(Event.UpPerson event) {
        if (event != null) {
            upPerson();
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
        card.setOnClickListener(this);
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
                            loginOut();
                        }

                    })
                    .setBottomClickListener(new DialogControl.OnButtonBottomClickListener() {
                        @Override
                        public void onBottomClick(View view, DialogControl.IBottomDialog dialog) {
                            dialog.dismiss();

                        }

                    }).creatDialog(new BottomAlertDialog(getContext())).show();

            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_退出账户);

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
                            CommonUtils.call(getContext(), getString(R.string.login_contact_phone));

                        }
                    }).creatDialog(new CenterAlertDialog(getContext())).show();

            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_联系客服);
        } else if (i == R.id.ll_about_us) {

            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_about_us).navigation(getContext());
            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_关于我们);
        } else if (i == R.id.ll_my_car) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_car_list).navigation(getContext());
            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_我的车辆);
        } else if (i == R.id.ll_person_infor) {
            ArouterUtils.getInstance().builder(ArouterParamLogin.activity_person_infor).navigation(getContext());

            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_我的资料);
        } else if (i == R.id.ll_wallet) {
            ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_wallet).navigation(getContext());
            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_我的钱包);

        } else if (i == R.id.ll_niu) {
            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_我的牛贝);
            ArouterUtils.getInstance()
                    .builder(ArouterParamsFinance.activity_finance_niu)
                    .navigation(getContext());
        } else if (i == R.id.card) {

            ClickEventUtils.getInstance().onClick(ClickEventParams.我的_邀请好友);
            ArouterUtils.getInstance()
                    .builder(ArouterParamFestivity.activity_festivity_home)
                    .navigation(getContext());
        }
    }

    private void loginOut() {

        //退出登录接口
        HttpLoginFactory.loginOut()
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        SharedPreferencesUtils.getInstance().remove(Param.LOGIN_ONFOR);
                        SharedPreferencesUtils.getInstance().remove(Param.PERSON_ONFOR);
                        ArouterUtils.getInstance().builder(ArouterParamLogin.activity_login).navigation(getContext());
                        //断开融云连接
                        ChactHelper.getHelper().disConnect();
                        getActivity().finish();
                    }
                })
                .subscribe(new NetObserver<LoginBean>(null) {
                    @Override
                    public void doOnSuccess(LoginBean data) {
                    }

                });


    }
}
