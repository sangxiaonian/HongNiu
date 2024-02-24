package com.hongniu.freight.ui.fragment;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.permission.PermissionUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.DialogComment;
import com.sang.thirdlibrary.picture.PictureClient;
import com.sang.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * 作者：  on 2020/2/10.
 * 个人中心
 */
@Route(path = ArouterParamMNLM.fragment_person_center)
public class PersonCenterFragment extends CompanyBaseFragment implements View.OnClickListener {

    private ImageView img_heard;//头像
    private ImageView icon_eyes;//隐藏余额
    private TextView tv_name;//账号
    private TextView tv_role;//角色认证
    private TextView tv_count;//余额
    private ViewGroup ll_identification;//身份认证
    private ViewGroup ll_car;//我的车辆
    private ViewGroup ll_feedback;//意见反馈
    private ViewGroup ll_service;//联系客服
    private ViewGroup ll_about_us;//关于我们
    private ViewGroup ll_quit;//推出账号
    private ViewGroup ll_order_center;//接单中心
    private ViewGroup shadow;//我的金库

    private boolean hideBalance = true;//是否隐藏余额
    private PersonInfor personInfor;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_person_center, null);
        img_heard = inflate.findViewById(R.id.img_heard);
        icon_eyes = inflate.findViewById(R.id.icon_eyes);
        tv_name = inflate.findViewById(R.id.tv_name);
        tv_role = inflate.findViewById(R.id.tv_role);
        tv_count = inflate.findViewById(R.id.tv_count);
        ll_identification = inflate.findViewById(R.id.ll_identification);
        ll_car = inflate.findViewById(R.id.ll_car);
        ll_feedback = inflate.findViewById(R.id.ll_feedback);
        ll_service = inflate.findViewById(R.id.ll_service);
        ll_about_us = inflate.findViewById(R.id.ll_about_us);
        ll_quit = inflate.findViewById(R.id.ll_quit);
        ll_order_center = inflate.findViewById(R.id.ll_order_center);
        shadow = inflate.findViewById(R.id.shadow);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        initInfo(InfoUtils.getMyInfo());
        switchBalance(hideBalance);


    }

    @Override
    public void onStart() {
        super.onStart();
        queryInfo();
    }

    private boolean isFirst = true;

    @Override
    public void onTaskStart(Disposable d) {
        if (isFirst) {
            super.onTaskStart(d);
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        icon_eyes.setOnClickListener(this);
        img_heard.setOnClickListener(this);
        ll_identification.setOnClickListener(this);
        ll_car.setOnClickListener(this);
        ll_feedback.setOnClickListener(this);
        ll_service.setOnClickListener(this);
        ll_about_us.setOnClickListener(this);
        ll_quit.setOnClickListener(this);
        ll_order_center.setOnClickListener(this);
        shadow.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            queryInfo();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.icon_eyes) {
            switchBalance(!hideBalance);
        } else if (v.getId() == R.id.ll_identification) {

            if (InfoUtils.isShowAlert()) {
                Utils.dialogAttes(mContext, new DialogComment.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        PersonInfor personInfo = InfoUtils.getMyInfo();
                        if (personInfo == null) {
                            return;
                        }
                        Utils.jump2Attestation(mContext, personInfo);
                    }
                }).show();
            } else {
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_attestation_role_activity)
                        .withSerializable(Param.TRAN, InfoUtils.getRole(InfoUtils.getMyInfo()))
                        .withBoolean(Param.TYPE, false)
                        .navigation(mContext);
            }
        } else if (v.getId() == R.id.ll_car) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_my_car_list).navigation(mContext);
        } else if (v.getId() == R.id.img_heard) {
            PermissionUtils.applyCamera(getActivity(), new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission() {
                    new PictureClient()
                            .startPhoto(PersonCenterFragment.this, 1, null, new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(List<LocalMedia> result) {
                                    if (!CollectionUtils.isEmpty(result)) {
                                        final String path = PicUtils.getPath(result.get(0));
                                        HttpAppFactory.upImage(4, path, null)
                                                .subscribe(new BaseObserver<UpImgData>(PersonCenterFragment.this) {
                                                    @Override
                                                    public void onNext(UpImgData result) {
                                                        super.onNext(result);
                                                        HttpAppFactory.upDateLogo(result.getPath())
                                                                .subscribe(new BaseObserver<ResponseBody>(PersonCenterFragment.this) {
                                                                    @Override
                                                                    public void onNext(ResponseBody result) {
                                                                        super.onNext(result);
                                                                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                                                        ImageLoader.getLoader().load(mContext, img_heard, path);

                                                                    }

                                                                });
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                }

                @Override
                public void noPermission() {

                }
            });
        } else if (v.getId() == R.id.ll_feedback) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_face_back).navigation(mContext);
        } else if (v.getId() == R.id.ll_service) {
            new DialogComment.Builder()
                    .setBtLeft("取消")
                    .setBtRight("拨打")
                    .setDialogTitle(getString(R.string.service))
                    .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, Dialog dialog) {
                            CommonUtils.call(mContext, getString(R.string.service));
                            dialog.dismiss();
                        }
                    })
                    .creatDialog(mContext)
                    .show();

        } else if (v.getId() == R.id.ll_about_us) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_about_us).navigation(mContext);

        } else if (v.getId() == R.id.ll_quit) {
            new DialogComment.Builder()
                    .setBtLeft("取消")
                    .setBtRight("确定")
                    .setDialogTitle("确定要退出登录吗？")
                    .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                        @Override
                        public void onRightClick(View view, Dialog dialog) {
                            dialog.dismiss();
                            InfoUtils.loginOut();
                        }
                    })
                    .creatDialog(mContext)
                    .show();
        } else if (v.getId() == R.id.ll_order_center) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamMNLM.activity_order_receiving)
                    .withSerializable(Param.TRAN, RoleOrder.PLATFORM)
                    .navigation(mContext);

        } else if (v.getId() == R.id.shadow) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamMNLM.activity_my_coffers)
                    .navigation(mContext);

        }
    }

    private void queryInfo() {
        HttpAppFactory.queryMyInfo()
                .subscribe(new NetObserver<PersonInfor>(isFirst ? this : null) {
                    @Override
                    public void doOnSuccess(PersonInfor personInfor) {
                        super.doOnSuccess(personInfor);
                        initInfo(personInfor);
                        isFirst = false;
                    }


                });
    }

    private void initInfo(PersonInfor personInfor) {
        if (personInfor == null) {
            return;
        }
        this.personInfor = personInfor;
        ImageLoader.getLoader().loadHeaed(mContext, img_heard, personInfor.getLogoPath());
        String name = TextUtils.isEmpty(personInfor.getContact()) ? "" : personInfor.getContact();
        name = TextUtils.isEmpty(name) ? personInfor.getMobile() : name;
        CommonUtils.setText(tv_name, name);
        Role role = InfoUtils.getRole(personInfor);

        String roleName = role.getName();

        String stateName = InfoUtils.getStateName(InfoUtils.getState(personInfor));
        if (!TextUtils.isEmpty(stateName)) {
            roleName += "(" + stateName + ")";
        }
        CommonUtils.setText(tv_role, roleName);
        switchBalance(hideBalance);

        //非平台员工隐藏接单中心
        ll_order_center.setVisibility(role == Role.PLATFORM ? View.VISIBLE : View.GONE);
        ll_car.setVisibility((role == Role.PLATFORM || role == Role.CARRIER_COMPANY || role == Role.CARRIER_PERSONAL) ? View.VISIBLE : View.GONE);
        ll_identification.setVisibility((role == Role.PLATFORM ? View.GONE : View.VISIBLE));
    }

    private void switchBalance(boolean hideBalance) {
        this.hideBalance = hideBalance;
        tv_count.setText(hideBalance ? "******" : personInfor == null || TextUtils.isEmpty(personInfor.getTotalBalance()) ? "0" : personInfor.getTotalBalance());
        icon_eyes.setImageResource(hideBalance ? R.mipmap.attention_forbid : R.mipmap.attention);
    }
}
