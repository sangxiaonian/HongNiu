package com.hongniu.supply.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.entity.WalletDetail;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.hongniu.supply.R;
import com.hongniu.supply.entity.HomeADBean;
import com.hongniu.supply.net.HttpMainFactory;
import com.hongniu.supply.ui.holder.HomeHeader;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.net.error.NetException;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;

/**
 * 作者： ${PING} on 2018/11/23.
 */
@Route(path = ArouterParamsApp.fragment_home_fragment)
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    private RecyclerView rv;
    List<HomeADBean> ads;
    private HomeHeader homeHeader;
    private XAdapter<HomeADBean> adapter;
    private View llSearch;

    @Override
    protected View initView(LayoutInflater inflater) {

        View inflate = inflater.inflate(R.layout.fragment_home_fragment, null);
        rv = inflate.findViewById(R.id.rv);
        llSearch = inflate.findViewById(R.id.ll_search);


        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_new_light), false);

        ads = new ArrayList<>();

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

                        ImageLoader.getLoader().load(getContext(),img,data.getPicture());
                        tvTitle.setText(data.getTitle()==null?"":data.getTitle());
                        tvContent.setText(data.getSubtitle()==null?"":data.getSubtitle());

                        tvTitle.setVisibility(TextUtils.isEmpty(tvTitle.getText().toString().trim())?View.GONE:View.VISIBLE);
                        tvContent.setVisibility(TextUtils.isEmpty(tvContent.getText().toString().trim())?View.GONE:View.VISIBLE);

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
        homeHeader = new HomeHeader(getContext(), rv);
        adapter.addHeard(homeHeader);
        rv.setAdapter(adapter);

        Observable.concat(HttpMainFactory
                        .queryActivity()

                        .map(new Function<CommonBean<List<HomeADBean>>, CommonBean<List<HomeADBean>>>() {
                            @Override
                            public CommonBean<List<HomeADBean>> apply(CommonBean<List<HomeADBean>> listCommonBean) throws Exception {
                                if (!CommonUtils.isEmptyCollection(listCommonBean.getData())) {
                                    ads.addAll(listCommonBean.getData());
                                    adapter.notifyDataSetChanged();
                                }

                                return listCommonBean;
                            }
                        })

                , HttpFinanceFactory.queryAccountdetails()
                        .map(new Function<CommonBean<WalletDetail>, CommonBean<WalletDetail>>() {
                            @Override
                            public CommonBean<WalletDetail> apply(CommonBean<WalletDetail> walletDetailCommonBean) throws Exception {
                                if (walletDetailCommonBean.getCode()==200) {
                                    homeHeader.setWalletInfor(walletDetailCommonBean.getData().getAvailableBalance());
                                    adapter.notifyDataSetChanged();
                                }
                                return walletDetailCommonBean;
                            }
                        })
        )
                .subscribe(new BaseObserver(this) {
                    @Override
                    public void onNext(Object result) {
                        super.onNext(result);
                        if (result instanceof CommonBean) {
                            CommonBean bean = (CommonBean) result;
                            if (bean.getCode() != 200) {
                                onError(new NetException(bean.getCode(), bean.getMsg()));
                            } else {

                            }
                        }


                    }
                });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_new_light), false);

        }
    }


    @Override
    protected void initListener() {
        super.initListener();
//        tv_balance.setOnClickListener(this);
        homeHeader.setOnClickListener(this);
        llSearch.setOnClickListener(this);
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
                        .builder(ArouterParamOrder.activity_order_search)
                        .withSerializable(Param.TRAN, CARGO_OWNER)
                        .navigation(getContext());
//                ToastUtils.getInstance().show("搜索");
                break;
            case R.id.ll_cargo:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_货主);
//                ToastUtils.getInstance().show("货主");
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_order)
                        .withInt(Param.TRAN,3)
                        .navigation(getContext());
                break;
            case R.id.ll_car:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_车主);
//                ToastUtils.getInstance().show("车主");
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_order)
                        .withInt(Param.TRAN,1)
                        .navigation(getContext());
                break;
            case R.id.ll_driver:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_司机);
//                ToastUtils.getInstance().show("司机");
                ArouterUtils.getInstance()
                        .builder(ArouterParamOrder.activity_order_order)
                        .withInt(Param.TRAN,2)
                        .navigation(getContext());
                break;
            case R.id.card_policy:
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_牛人保);
//                ToastUtils.getInstance().show("保险");
                H5Config h5Config = new H5Config("牛人保", Param.NIURENBAO, true);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(getContext());

                break;
            case R.id.card_yongjin:
                ArouterUtils.getInstance().builder(ArouterParamFestivity.activity_festivity_home).navigation(getContext());
                ClickEventUtils.getInstance().onClick(ClickEventParams.首页_邀请好友);

                break;
            case R.id.card_etc:
                ToastUtils.getInstance().show("近期上线");
                break;
        }
    }
}
