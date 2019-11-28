package com.hongniu.modulecargoodsmatch.ui.activiry;

import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.model.AMapCarInfo;
import com.hedgehog.ratingbar.RatingBar;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.utils.BaseUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.control.MatchOrderDataControl;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderInfoBean;
import com.hongniu.modulecargoodsmatch.entity.MathDriverReceiveBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.presenter.MatchOrderDetaPresenter;
import com.hongniu.modulecargoodsmatch.widget.DriverDialog;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.net.error.NetException;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

import static com.hongniu.baselibrary.arouter.ArouterParamsApp.activity_img_previce;

/**
 * @data 2019/11/2
 * @Author PING
 * @Description 订单详情页
 * type 0 货主 1司机
 */
@Route(path = ArouterParamsMatch.activity_match_order_detail)
public class MatchOrderDetailActivity extends BaseActivity implements MatchOrderDataControl.IMatchOrderDataView, View.OnClickListener {

    private TextView tv_state;//订单送达状态
    private TextView tv_time;//预约取货时间
    private TextView tv_car_infor;//发货车辆信息时间
    private TextView tv_receive_time;//送达时间
    private TextView tv_start_address;//发货地点
    private TextView tv_start_info;//发货人信息
    private ViewGroup ll_start_call;//联系发货人

    private TextView tv_end_address;//收货地点
    private TextView tv_end_info;//收货人信息
    private ViewGroup ll_end_call;//联系收货人
    private Group group;//联系收货人


    private ViewGroup card_receive;//送达凭证
    private TextView tv_receive_remark;//送达备注信息
    private RecyclerView recycler_receive;//送达凭证 图片

    private ViewGroup card_estimate;//司机评价
    private TextView tv_estimate;//司机评价
    private RatingBar view_start;//司机评价星级

    private ViewGroup card_estimate_owner;//发货人评价
    private TextView tv_estimate_owner;//发货人评价
    private RatingBar view_start_owner;//发货人评价星级

    private TextView tv_price;//运费
    private TextView tv_price_des;//运费明细

    private ViewGroup ll_bottom;//
    private ImageView img_call;//
    private TextView tv_button;//
    DriverDialog dialog;

    private TextView tv_start_load;
    private TextView tv_end_load;


    MatchOrderDataControl.IMatchOrderDataPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_order_detail);
        setToolbarRedTitle("订单号");
        MatchOrderInfoBean infoBean = getIntent().getParcelableExtra(Param.TRAN);
        int type = getIntent().getIntExtra(Param.TYPE, 0);
        initView();
        initData();
        initListener();
        if (infoBean == null) {
            finish();
            return;
        }
        presenter = new MatchOrderDetaPresenter(this);
        presenter.saveInfor(type, infoBean, this);
    }

    @Override
    protected void initView() {
        super.initView();

        tv_start_load = findViewById(R.id.tv_start_load);
        tv_end_load = findViewById(R.id.tv_end_load);

        tv_state = findViewById(R.id.tv_state);
        tv_time = findViewById(R.id.tv_time);
        tv_car_infor = findViewById(R.id.tv_car_infor);
        tv_receive_time = findViewById(R.id.tv_receive_time);
        tv_start_address = findViewById(R.id.tv_start_address);
        tv_start_info = findViewById(R.id.tv_start_info);
        ll_start_call = findViewById(R.id.ll_start_call);
        tv_end_address = findViewById(R.id.tv_end_address);
        tv_end_info = findViewById(R.id.tv_end_info);
        ll_end_call = findViewById(R.id.ll_end_call);
        card_receive = findViewById(R.id.card_receive);
        recycler_receive = findViewById(R.id.recycler_receive);
        card_estimate = findViewById(R.id.card_estimate);
        tv_estimate = findViewById(R.id.tv_estimate);
        view_start = findViewById(R.id.view_start);
        tv_price = findViewById(R.id.tv_price);
        tv_price_des = findViewById(R.id.tv_price_des);
        group = findViewById(R.id.group);
        ll_bottom = findViewById(R.id.ll_bottom);
        img_call = findViewById(R.id.img_call);
        tv_button = findViewById(R.id.tv_button);
        tv_receive_remark = findViewById(R.id.tv_receive_remark);

        card_estimate_owner = findViewById(R.id.card_owner_estimate);
        tv_estimate_owner = findViewById(R.id.tv_estimate_owner);
        view_start_owner = findViewById(R.id.view_start_woner);

        dialog = new DriverDialog(mContext);
    }

    @Override
    protected void initData() {
        super.initData();


    }

    @Override
    protected void initListener() {
        super.initListener();
        ll_end_call.setOnClickListener(this);
        ll_start_call.setOnClickListener(this);
        ll_bottom.setOnClickListener(this);

        tv_start_load.setOnClickListener(this);
        tv_end_load.setOnClickListener(this);


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.queryDetailInfo(this);
    }

    /**
     * 显示标题
     *
     * @param title
     */
    @Override
    public void showTitle(String title) {
        setToolbarRedTitle(title);
    }

    /**
     * 显示订单状态
     *
     * @param orderState
     */
    @Override
    public void showOrderState(String orderState) {
        tv_state.setText(orderState);
    }

    /**
     * 预约取货时间
     *
     * @param pickupTime
     */
    @Override
    public void showPickupTime(String pickupTime) {
        tv_time.setText(pickupTime);
    }

    /**
     * 配送车辆
     *
     * @param carInfo
     */
    @Override
    public void showCarInfo(String carInfo) {
        tv_car_infor.setText(carInfo);
    }

    @Override
    public void showArriveTime(String arriveTime) {
        tv_receive_time.setText(arriveTime);
        tv_receive_time.setVisibility(TextUtils.isEmpty(arriveTime) ? View.GONE : View.VISIBLE);
    }

    /**
     * 显示发货人、收货人信息
     *
     * @param info
     * @param showContact 是否显示联系人信息
     */
    @Override
    public void showPlaceInfo(MatchOrderInfoBean info, boolean showContact) {
        tv_start_address.setText(TextUtils.isEmpty(info.getStartPlaceInfo()) ? "" : info.getStartPlaceInfo());
        tv_end_address.setText(TextUtils.isEmpty(info.getDestinationInfo()) ? "" : info.getDestinationInfo());
        tv_start_info.setText(String.format("%s（%s）", info.getShipperName(), info.getShipperMobile()));
        tv_end_info.setText(String.format("%s（%s）", info.getReceiverName(), info.getReceiverMobile()));
        group.setVisibility(showContact ? View.VISIBLE : View.GONE);
        ll_start_call.setVisibility(showContact ? View.VISIBLE : View.GONE);
        ll_end_call.setVisibility(showContact ? View.VISIBLE : View.GONE);
    }

    /**
     * 送达凭证
     *
     * @param arriveVoucherImages 图片
     * @param showArriveVoucher   是否显示送达凭证 true 显示
     * @param remark
     */
    @Override
    public void showArriveVoucher(final List<String> arriveVoucherImages, boolean showArriveVoucher, String remark) {
        card_receive.setVisibility(showArriveVoucher ? View.VISIBLE : View.GONE);
        tv_receive_remark.setText(remark);
        tv_receive_remark.setVisibility(TextUtils.isEmpty(remark) ? View.GONE : View.VISIBLE);
        if (showArriveVoucher && !BaseUtils.isCollectionsEmpty(arriveVoucherImages)) {
            XAdapter<String> adapter = new XAdapter<String>(mContext, arriveVoucherImages) {
                @Override
                public BaseHolder<String> initHolder(final ViewGroup parent, int viewType) {
                    return new BaseHolder<String>(mContext, parent, R.layout.item_match_img) {
                        @Override
                        public void initView(View itemView, final int position, String data) {
                            super.initView(itemView, position, data);
                            ImageView img = itemView.findViewById(R.id.img);
                            ImageLoader.getLoader().load(mContext, img, data);
                            img.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //查看大图
                                    ArouterUtils.getInstance().builder(activity_img_previce)
                                            .withInt(Param.TYPE, position)
                                            .withStringArrayList(Param.TRAN, (ArrayList<String>) arriveVoucherImages)
                                            .navigation(mContext)
                                    ;
                                }
                            });
                        }
                    };
                }
            };
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recycler_receive.setLayoutManager(manager);
            recycler_receive.setAdapter(adapter);
        }
    }

    /**
     * 显示司机评价模块
     *
     * @param estimate        星级
     * @param estimateContent 评价内容
     * @param showEstimate    是否显示
     */
    @Override
    public void showEstimate(int estimate, String estimateContent, boolean showEstimate) {
        card_estimate.setVisibility(showEstimate ? View.VISIBLE : View.GONE);
        tv_estimate.setText(TextUtils.isEmpty(estimateContent) ? "" : estimateContent);
        view_start.setStar(estimate);
    }

    /**
     * 显示价格
     *
     * @param price
     * @param priceDetail
     */
    @Override
    public void showPrice(String price, String priceDetail) {
        tv_price.setText(price);
        tv_price_des.setText(priceDetail);
    }

    /**
     * 拨打电话
     *
     * @param phone
     */
    @Override
    public void call(String phone) {
        CommonUtils.call(mContext, phone);
    }

    /**
     * 展示底部按钮
     *
     * @param buttonInfo
     * @param showButton
     * @param showBtCall
     */
    @Override
    public void showButton(String buttonInfo, boolean showButton, boolean showBtCall) {
        ll_bottom.setVisibility(showButton ? View.VISIBLE : View.GONE);
        tv_button.setText(TextUtils.isEmpty(buttonInfo) ? "" : buttonInfo);
        img_call.setVisibility(showBtCall ? View.VISIBLE : View.GONE);
    }

    /**
     * 确认到达
     *
     * @param id
     */
    @Override
    public void jumpToEntryArrive(String id) {
        ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_entry_arrive)
                .withString(Param.TRAN, id)
                .navigation(mContext);
    }

    /**
     * 我要接单
     *
     * @param id
     */
    @Override
    public void showReceiveOrder(final String id) {
        CenterAlertBuilder builder = Utils.creatDialog(mContext, "确定接单？", "接单后，即可与货主取得联系", "放弃接单", "确定接单");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory
                                .receiverOrder(id)
                                .subscribe(new NetObserver<MathDriverReceiveBean>(MatchOrderDetailActivity.this) {
                                    @Override
                                    public void onNext(CommonBean<MathDriverReceiveBean> result) {
                                        if (result.getCode()==500&&result.getData()!=null&&result.getData().getDriverStatus()==0){
                                             toVer();
                                        }else {
                                            if (result.getCode()!=200){
                                                onError(new NetException(result.getCode(),result.getMsg()));
                                            }else {
                                                doOnSuccess(result.getData());
                                            }
                                        }

                                    }


                                    @Override
                                    public void doOnSuccess(MathDriverReceiveBean data) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        super.onComplete();
                                        presenter.queryDetailInfo(MatchOrderDetailActivity.this);

                                    }
                                });

                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    //未认证司机进行认证
    private void toVer(){
        CenterAlertBuilder builder = Utils.creatDialog(mContext, "需要完成认证司机才能接单", "", "放弃", "去认证");
        builder
                .hideContent()
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        ArouterUtils.getInstance()
                                .builder(ArouterParamLogin.activity_person_infor)
                                .withInt(Param.TYPE, 1)
                                .navigation(mContext);

                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }


    /**
     * 评价司机
     *  @param id
     * @param title
     * @param driverName
     * @param driverMobile
     * @param hide
     */
    @Override
    public void appraiseDriver(String id, String title, String driverName, String driverMobile, String hide) {
        //评价司机
        dialog.setTitle(title);
        dialog.setHide(hide);
        dialog.setSubTitle(String.format("%s(%s)", driverName, driverMobile));
        dialog.setEntryClickListener(new DriverDialog.EntryClickListener() {
            @Override
            public void OnEntryClick(int rating, String trim) {

                presenter.appraiseDrive(rating, trim, MatchOrderDetailActivity.this);
            }
        });
        dialog.builder().show(null);
    }


    @Override
    public void showSuccess(String msg) {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show(msg);
        presenter.queryDetailInfo(this);
    }

    /**
     * 跳转到发货人导航
     *
     * @param startPoi
     * @param endPoi
     * @param guideCarInfo
     */
    @Override
    public void carLoad(Poi startPoi, Poi endPoi, AMapCarInfo guideCarInfo) {
        AmapNaviParams naviParams = new AmapNaviParams(startPoi, null, endPoi, AmapNaviType.DRIVER);
        naviParams.setCarInfo(guideCarInfo);
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), naviParams, null);

    }

    /**
     * 查看路线
     *
     * @param startPoi
     * @param endPoi
     */
    @Override
    public void showRoute(Poi startPoi, Poi endPoi) {

        //查看路线
        AmapNaviParams amapNaviParams = new AmapNaviParams(startPoi, null, endPoi, AmapNaviType.DRIVER);
        amapNaviParams.setUseInnerVoice(true);

        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(),
                amapNaviParams, null);
    }

    @Override
    public void guideInfo(String showRoute) {
        tv_start_load.setText(showRoute == null ? "" : showRoute);
        tv_end_load.setText(showRoute == null ? "" : showRoute);
    }

    /**
     * 确认收货
     *
     * @param id
     */
    @Override
    public void entryReceive(final String id) {
        CenterAlertBuilder builder = Utils.creatDialog(mContext, "确认收货", "确认收货后，运费将立即转入司机账户", "我再想想", "确认收货");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        HttpMatchFactory.matchEntryReceive(id)
                                .subscribe(new NetObserver<Object>(null) {
                                    @Override
                                    public void doOnSuccess(Object data) {
                                        presenter.queryDetailInfo(MatchOrderDetailActivity.this);
                                    }
                                });

                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    /**
     * 是否显示发货人评价
     *
     * @param estimate
     * @param estimateContent
     * @param showEstimate
     */
    @Override
    public void showOwnerEstimate(int estimate, String estimateContent, boolean showEstimate) {
        card_estimate_owner.setVisibility(showEstimate ? View.VISIBLE : View.GONE);
        tv_estimate_owner.setText(TextUtils.isEmpty(estimateContent) ? "" : estimateContent);
        tv_estimate_owner.setVisibility(TextUtils.isEmpty(estimateContent) ? View.GONE : View.VISIBLE);
        view_start_owner.setStar(estimate);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_end_call) {
            presenter.contactEnd();
        } else if (v.getId() == R.id.ll_start_call) {
            presenter.contactStart();

        } else if (v.getId() == R.id.ll_bottom) {

            presenter.clickBt();
        } else if (v.getId() == R.id.tv_start_load) {

            presenter.startLoad();
        } else if (v.getId() == R.id.tv_end_load) {

            presenter.endLoad();
        }
    }
}
