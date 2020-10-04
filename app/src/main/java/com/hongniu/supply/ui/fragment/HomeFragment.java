package com.hongniu.supply.ui.fragment;

import android.graphics.Color;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hongniu.baselibrary.arouter.ArouterParamFestivity;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.supply.R;
import com.hongniu.supply.entity.HomeADBean;
import com.hongniu.supply.net.HttpMainFactory;
import com.hongniu.supply.ui.holder.HeadImageHolder;
import com.hongniu.supply.ui.holder.HomeNewHeader;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.sang.common.recycleview.RecycleViewScroll;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.common.widget.ColorImageView;
import com.sang.common.widget.DrawableCircle;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;

/**
 * 作者： ${PING} on 2018/11/23.
 */
@Route(path = ArouterParamsApp.fragment_home_fragment)
public class HomeFragment extends BaseFragment implements View.OnClickListener, RecycleViewScroll.OnScrollDisChangeListener {


    private RecycleViewScroll rv;
    List<HomeADBean> ads;
    ColorImageView imgSearch;
    private HomeNewHeader homeNewHeader;
    private View rlTitle;
    private XAdapter<HomeADBean> adapter;
    private View llSearch;
    private int currentColor;
    private TextView tvSearch;
    private DrawableCircle drawable;
    private HeadImageHolder headImageHolder;

    @Override
    protected View initView(LayoutInflater inflater) {

        View inflate = inflater.inflate(R.layout.fragment_home_fragment, null);
        rv = inflate.findViewById(R.id.rv);
        llSearch = inflate.findViewById(R.id.ll_search);
        rlTitle = inflate.findViewById(R.id.rl_title);
        imgSearch = inflate.findViewById(R.id.img_search);
        tvSearch = inflate.findViewById(R.id.tv_search);


        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        currentColor = getResources().getColor(R.color.color_new_light);
        drawable = new DrawableCircle();
        drawable.setColor(Color.parseColor("#ea492c"))
                .setRadius(DeviceUtils.dip2px(getContext(), 2))
                .flush();
        llSearch.post(new Runnable() {
            @Override
            public void run() {
                drawable.setSize(llSearch.getWidth(), llSearch.getHeight())
                        .flush();
                llSearch.setBackground(drawable);

            }
        });


        setToolBarColor(currentColor);
        ads = new ArrayList<>();
        rv.setMaxY(DeviceUtils.dip2px(getContext(), 140));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        adapter = new XAdapter<HomeADBean>(getContext(), ads) {
            @Override
            public BaseHolder<HomeADBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<HomeADBean>(getContext(), parent, R.layout.item_home_ad) {
                    @Override
                    public void initView(View itemView, int position, final HomeADBean data) {
                        super.initView(itemView, position, data);
                        ImageView img = itemView.findViewById(R.id.img_festivity);
                        TextView tvTitle = itemView.findViewById(R.id.tv_festivity);
                        TextView tvContent = itemView.findViewById(R.id.tv_festivity_des);

                        ImageLoader.getLoader().load(getContext(), img, data.getPicture());
                        tvTitle.setText(data.getTitle() == null ? "" : data.getTitle());
                        tvContent.setText(data.getSubtitle() == null ? "" : data.getSubtitle());

                        tvTitle.setVisibility(TextUtils.isEmpty(tvTitle.getText().toString().trim()) ? View.GONE : View.VISIBLE);
                        tvContent.setVisibility(TextUtils.isEmpty(tvContent.getText().toString().trim()) ? View.GONE : View.VISIBLE);

                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_活动);

                                H5Config h5Config = new H5Config(data.getTitle(), data.getLink(), true);
                                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

                            }
                        });
                    }
                };
            }
        };
        homeNewHeader = new HomeNewHeader(getContext(), rv);
        headImageHolder = new HeadImageHolder(getContext(), rv);
//        adapter.addHeard(homeHeader);
        adapter.addHeard(homeNewHeader);
        adapter.addHeard(headImageHolder);
        rv.setAdapter(adapter);


    }

    private void upData() {
        HttpMainFactory
                .queryActivity()
                .subscribe(new NetObserver<List<HomeADBean>>(isFirst ? this : null) {
                    @Override
                    public void doOnSuccess(List<HomeADBean> data) {
                        ads.clear();
                        if (data != null) {
                            ads.addAll(data);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private boolean isFirst = true;

    @Override
    public void onTaskStart(Disposable d) {
        isFirst = false;
        super.onTaskStart(d);

    }

    @Override
    public void onTaskFail(Throwable e, int code, String msg) {
        super.onTaskFail(e, code, msg);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setToolBarColor(currentColor);
            upData();

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        upData();

    }

    @Override
    protected void initListener() {
        super.initListener();
//        tv_balance.setOnClickListener(this);
        homeNewHeader.setOnClickListener(this);
        headImageHolder.setOnClickListener(this);
        llSearch.setOnClickListener(this);
        rv.setOnScrollDisChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_wallet:
                ArouterUtils.getInstance().builder(ArouterParamsFinance.activity_finance_wallet).navigation(getContext());
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_我的钱包);

                break;
            case R.id.ll_search:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_搜索);
                ArouterUtils.getInstance()
//                        .builder(ArouterParamOrder.activity_order_search)
                        .builder(ArouterParamsApp.activity_qrcode)
                        .withSerializable(Param.TRAN, CARGO_OWNER)
                        .navigation(getContext());
//                ToastUtils.getInstance().show("搜索");
                break;
            case R.id.ll_cargo:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_货主);
//                ToastUtils.getInstance().show("货主");
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_order)
                        .withInt(Param.TRAN, 3)
                        .navigation(getContext());
                break;
            case R.id.ll_car:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_车主);
//                ToastUtils.getInstance().show("车主");
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_order)
                        .withInt(Param.TRAN, 1)
                        .navigation(getContext());
                break;
            case R.id.ll_driver:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_司机);
//                ToastUtils.getInstance().show("司机");
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_order)
                        .withInt(Param.TRAN, 2)
                        .navigation(getContext());
                break;
            case R.id.card_policy:
            case R.id.card_match:

                ArouterUtils.getInstance().builder(ArouterParamsMatch.activity_match_estimate_order).navigation(getContext());
                break;
            case R.id.card_yongjin:
            case R.id.card_invite:
                ArouterUtils.getInstance().builder(ArouterParamFestivity.activity_festivity_home).navigation(getContext());
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_邀请好友);

                break;
            case R.id.card_left:
            case R.id.card_star:
                ArouterUtils.getInstance().builder(ArouterParamsBreakbulk.activity_breakbulk_company).navigation(getContext());
                break;
            case R.id.card_etc:
            case R.id.card_insurance:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_牛人保);
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_insurance_calculate)
                        .navigation(getContext());
                break;
            case R.id.card_goods_match:
                ArouterUtils.getInstance()
                        .builder(ArouterParamsMatch.activity_match_all_order_list)
                        .navigation(getContext());
                break;
            case R.id.view_more:
                H5Config h5Config = new H5Config("用户手册", Param.hongniu_user_guide, true);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(getContext());

                break;
        }
    }

    /**
     * View滑动监听
     *
     * @param disX     横向滑动距离
     * @param percentX 横向滑动的百分比
     * @param dixY     纵向滑动距离
     * @param percentY 纵向滑动的百分比
     */
    @Override
    public void onScrollDisChange(float disX, float percentX, float dixY, float percentY) {
        if (percentY > 1) {
            percentY = 1;
        }
        if (percentY >= 0 && percentY <= 1) {
            currentColor = ConvertUtils.evaluateColor(percentY, getResources().getColor(R.color.color_new_light), Color.WHITE);
            rlTitle.setBackgroundColor(currentColor);
            setToolBarColor(currentColor);
            final int contentColor = ConvertUtils.evaluateColor(percentY, getResources().getColor(R.color.color_home_search), getResources().getColor(R.color.color_et_hide));
            tvSearch.setTextColor(contentColor);
            imgSearch.setCurrentColor(contentColor);


            int searchBgColor = ConvertUtils.evaluateColor(percentY, Color.parseColor("#ea492c"), getResources().getColor(R.color.color_bg_dark));
            drawable.setColor(searchBgColor).flush();
//            llSearch.setBackgroundColor(searchBgColor);

        }
    }

    private void setToolBarColor(int color) {
        StatusBarCompat.setStatusBarColor(getActivity(), color, true);

    }


}
