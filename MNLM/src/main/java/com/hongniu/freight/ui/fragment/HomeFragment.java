package com.hongniu.freight.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.control.HomeControl;
import com.hongniu.freight.entity.Event;
import com.hongniu.freight.entity.H5Config;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.OrderNumberInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.presenter.HomeFramentPresent;
import com.hongniu.freight.ui.holder.ReceiveOrderHolder;
import com.hongniu.freight.ui.holder.order.OrderHolderBuider;
import com.hongniu.freight.ui.holder.order.XOrderButtonClick;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.DialogComment;

import java.util.List;

/**
 * 作者：  on 2020/2/5.
 */
@Route(path = ArouterParamMNLM.fragment_home)
public class HomeFragment extends CompanyBaseFragment implements HomeControl.IHomeFragmentView, View.OnClickListener, XOrderButtonClick.NextStepListener {
    private TextView tv_title;//标题
    private TextView tv_role_title;//标题
    private View search;//搜索
    private ImageView icon_eyes;//查看余额
    private TextView tv_count;//余额
    private TextView tv_count_tyr;//托运人订单数量
    private TextView tv_count_cyr;//承运人订单数量
    private TextView tv_count_driver;//司机订单数量
    private View view_chengyunren;//承运人
    private View view_tuoyunren;//托运人
    private View view_driver;//司机
    private View bt_learn_more;//了解更多
    private View shadow;//了解更多
    private View ll_more;//了解更多
    private ViewGroup ll_content;//了解更多
    private ViewGroup content;//了解更多


    HomeControl.IHomeFragmentPresent present;

    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.mnlm_fragment_home, null);
        tv_title = inflate.findViewById(R.id.tv_title);
        tv_role_title = inflate.findViewById(R.id.tv_role_title);
        ll_content = inflate.findViewById(R.id.ll_content);
        content = inflate.findViewById(R.id.content);
        search = inflate.findViewById(R.id.search);
        icon_eyes = inflate.findViewById(R.id.icon_eyes);
        tv_count = inflate.findViewById(R.id.tv_count);
        tv_count_tyr = inflate.findViewById(R.id.tv_count_tyr);
        view_tuoyunren = inflate.findViewById(R.id.view_tuoyunren);
        tv_count_cyr = inflate.findViewById(R.id.tv_count_cyr);
        view_chengyunren = inflate.findViewById(R.id.view_chengyunren);
        tv_count_driver = inflate.findViewById(R.id.tv_count_driver);
        view_driver = inflate.findViewById(R.id.view_driver);
        bt_learn_more = inflate.findViewById(R.id.bt_learn_more);
        ll_more = inflate.findViewById(R.id.ll_more);
        shadow = inflate.findViewById(R.id.shadow);
        present = new HomeFramentPresent(this);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        content.setVisibility(View.GONE);
        if (getBundle() != null) {
            boolean isLogin = getBundle().getBoolean(Param.TRAN, false);
            present.saveInfo(isLogin);
        }
        present.queryInfo(this);


    }

    @Override
    protected void initListener() {
        super.initListener();
        bt_learn_more.setOnClickListener(this);
        view_chengyunren.setOnClickListener(this);
        view_tuoyunren.setOnClickListener(this);
        view_driver.setOnClickListener(this);
        shadow.setOnClickListener(this);
        icon_eyes.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        search.setOnClickListener(this);


    }


    private SpannableStringBuilder getSpanner(String s) {
        SpannableStringBuilder builder = new SpannableStringBuilder(s);
        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(13, true);
        builder.setSpan(sizeSpan, builder.length() - 1, builder.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        return builder;
    }


    /**
     * 显示订单信息
     *
     * @param infoBeans
     * @param type
     */
    @Override
    public void showOrderInfo(List<OrderInfoBean> infoBeans, RoleOrder type) {
        content.setVisibility(CollectionUtils.isEmpty(infoBeans) ? View.GONE : View.VISIBLE);
        if (tv_role_title != null) {
            if (type == RoleOrder.PLATFORM || type == RoleOrder.SHIPPER) {
                tv_role_title.setText("我的发货");
            } else if (type == RoleOrder.CARRIER) {
                tv_role_title.setText("我要接单");
            } else {
                tv_role_title.setText("我的送货");
            }
        }

        ll_content.removeAllViews();
        if (!CollectionUtils.isEmpty(infoBeans)) {
            for (int i = 0; i < infoBeans.size(); i++) {
                OrderInfoBean infoBean = infoBeans.get(i);
                BaseHolder<OrderInfoBean> holder;
                if (type == RoleOrder.CARRIER) {
                    holder = new ReceiveOrderHolder(mContext, ll_content, type);
                    holder.initView(holder.getItemView(), i, infoBean);
                } else {
                    XOrderButtonClick xOrderButtonClick = new XOrderButtonClick(mContext);
                    xOrderButtonClick.setType(type);
                    xOrderButtonClick.setNextStepListener(this);
                    holder = new OrderHolderBuider(mContext)
                            .setParent(ll_content)
                            .setType(type)
                            .setOrderButtonClickListener(xOrderButtonClick)
                            .build()
                    ;
                    holder.initView(holder.getItemView(), i, infoBean);
                }
                ll_content.addView(holder.getItemView());
            }
        }
    }

    /**
     * 储存订单数量
     *
     * @param data
     */
    @Override
    public void showOrderNum(OrderNumberInfoBean data) {
        if (data == null) {
            tv_count_tyr.setText(getSpanner("0单"));
            tv_count_cyr.setText(getSpanner("0单"));
            tv_count_driver.setText(getSpanner("0单"));
        } else {
            tv_count_tyr.setText(getSpanner(data.getUserOrderNum() + "单"));
            tv_count_cyr.setText(getSpanner(data.getOwnerOrderNum() + "单"));
            tv_count_driver.setText(getSpanner(data.getDriverOrderNum() + "单"));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        present.queryInfo(null);
    }

    /**
     * 展示个人信息
     *
     * @param personInfo
     */
    @Override
    public void showPersonInfo(PersonInfor personInfo) {
        //
//        if (personInfo != null) {
//            String name = TextUtils.isEmpty(personInfo.getContact()) ? "" : personInfo.getContact();
//            name = TextUtils.isEmpty(name) ? personInfo.getMobile() : name;
//            tv_title.setText(String.format("%s好，%s", Utils.getTitleTime(), name));
//        }

    }

    /**
     * 显示余额数据
     *
     * @param showBalance  true 显示
     * @param totalBalance 余额
     */
    @Override
    public void showBalance(boolean showBalance, String totalBalance) {
        tv_count.setText(showBalance ? totalBalance : "******");
        icon_eyes.setImageResource(showBalance ? R.mipmap.attention : R.mipmap.attention_forbid);
    }

    /**
     * 跳转到查看认证信息页面
     *
     * @param role
     * @param personInfo
     */
    @Override
    public void jump2CheckState(Role role, PersonInfor personInfo) {

        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_attestation_role_activity)
                .withSerializable(Param.TRAN, role)
                .withBoolean(Param.TYPE, InfoUtils.getState(personInfo) == 5 || InfoUtils.getState(personInfo) == 0)
                .navigation(mContext);
    }

    @Override
    public void clickMore(RoleOrder roleOrder) {
        if (roleOrder == RoleOrder.CARRIER) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamMNLM.activity_order_center)
                    .navigation(mContext);
        } else {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_my_order)
                    .withSerializable(Param.TRAN, roleOrder)
                    .navigation(mContext);
        }
    }

    /**
     * 查询到有正在运输中的订单,开始定位i上传位置
     *
     * @param bean
     */
    @Override
    public void startLoaction(OrderInfoBean bean) {
        Event.UpLoactionEvent upLoactionEvent = new Event.UpLoactionEvent();
        upLoactionEvent.start = true;
        upLoactionEvent.orderID = bean.getId();
        upLoactionEvent.cardID = bean.getCarId();
        upLoactionEvent.destinationLatitude = bean.getDestinationLat();
        upLoactionEvent.destinationLongitude = bean.getDestinationLon();
        BusFactory.getBus().post(upLoactionEvent);
    }

    /**
     * 无正在运输中的订单,停止上传位置
     */
    @Override
    public void stopLocation() {
        Event.UpLoactionEvent upLoactionEvent = new Event.UpLoactionEvent();
        upLoactionEvent.start = false;
        BusFactory.getBus().post(upLoactionEvent);
    }

    /**
     * 跳转到选角色实名认证
     */
    @Override
    public void jump2SelectRole() {
        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_attestation_select_role)
                .navigation(mContext);
    }

    boolean isFirst = true;

    /**
     * 弹出去实名认证的提示
     *
     * @param myInfo
     */
    @Override
    public void showAttestationAlert(PersonInfor myInfo) {


        if (isFirst) {
            isFirst = false;
            Utils.dialogAttes(mContext, new DialogComment.OnButtonRightClickListener() {
                @Override
                public void onRightClick(View view, Dialog dialog) {
                    dialog.dismiss();
                    present.jump2Attestion();
                }
            }).show();

        }

    }


    /**
     * 跳转到实名认证
     *
     * @param personInfo
     */
    @Override
    public void jump2Attestion(PersonInfor personInfo) {
        Utils.jump2Attestation(mContext, personInfo);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_learn_more) {
            H5Config h5Config = new H5Config("网络货运", Param.insurance_polic, true);
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_h5)
                    .withSerializable(Param.TRAN, h5Config)
                    .navigation(mContext);
        } else if (v.getId() == R.id.view_chengyunren) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_my_order)
                    .withSerializable(Param.TRAN, RoleOrder.CARRIER)
                    .navigation(mContext);
        } else if (v.getId() == R.id.view_tuoyunren) {
            //托运人
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_my_order)
                    .withSerializable(Param.TRAN, RoleOrder.SHIPPER)
                    .navigation(mContext);
        } else if (v.getId() == R.id.view_driver) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_my_order)
                    .withSerializable(Param.TRAN, RoleOrder.DRIVER)
                    .navigation(mContext);
        } else if (v.getId() == R.id.icon_eyes) {
            present.switchBalance();
        } else if (v.getId() == R.id.shadow) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamMNLM.activity_my_coffers)
                    .navigation(mContext);
        } else if (v.getId() == R.id.ll_more) {
            present.clickMore();

        } else if (v.getId() == R.id.search) {
            ArouterUtils.getInstance()
                    .builder(ArouterParamMNLM.activity_qrcode)
                    .navigation(getContext());

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            present.queryInfo(null);
        }
    }

    /**
     * 再进行取消等操作完成之后,刷新界面
     */
    @Override
    public void doUpdate() {
        present.queryInfo(this);
    }
}
