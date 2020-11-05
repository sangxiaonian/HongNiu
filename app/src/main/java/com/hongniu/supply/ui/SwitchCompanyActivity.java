package com.hongniu.supply.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.utils.CommonUtils;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.freight.Config;
import com.hongniu.freight.entity.LoginInfo;
import com.hongniu.freight.ui.SplashActivity;
import com.hongniu.freight.utils.InfoUtils;
import com.hongniu.supply.R;
import com.hongniu.supply.entity.CompanyInfoBean;
import com.hongniu.supply.entity.CompanyTokenInfoBean;
import com.hongniu.supply.net.HttpMainFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 *@data  2020/10/31
 *@Author PING
 *@Description
 *
 *切换网络货运公司
 */

@Route(path = ArouterParamsApp.activity_switch_company)
public class SwitchCompanyActivity extends RefrushActivity<CompanyInfoBean>    {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_company);
        setToolbarTitle("请选择网络货运公司");
        initView();
        initData();
        initListener();
        refresh.setEnableLoadMore(false);
        queryData(true);

    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected Observable<CommonBean<PageBean<CompanyInfoBean>>> getListDatas() {
        return HttpMainFactory.queryCompanyInfo()
                    .map(new Function<CommonBean<List<CompanyInfoBean>>, CommonBean<PageBean<CompanyInfoBean>>>() {
                        @Override
                        public CommonBean<PageBean<CompanyInfoBean>> apply(@NonNull CommonBean<List<CompanyInfoBean>> listCommonBean) throws Exception {

                            CommonBean<PageBean<CompanyInfoBean>> commonBean=new CommonBean<>();
                            PageBean<CompanyInfoBean> pageBean=new PageBean<>();
                            pageBean.setList(listCommonBean.getData());
                            commonBean.setCode(listCommonBean.getCode());
                            commonBean.setData(pageBean);
                            commonBean.setMsg(listCommonBean.getMsg());
                            return commonBean;
                        }
                    })
                ;
    }

    @Override
    protected XAdapter<CompanyInfoBean> getAdapter(List<CompanyInfoBean> datas) {
        return new XAdapter<CompanyInfoBean>(mContext,datas) {
            @Override
            public BaseHolder<CompanyInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<CompanyInfoBean>(context,parent,R.layout.item_company_info){
                    @Override
                    public void initView(View itemView, int position, CompanyInfoBean data) {
                        super.initView(itemView, position, data);
                        ImageView img=itemView.findViewById(R.id.img);
                        TextView tv_title=itemView.findViewById(R.id.tv_title);

                        CommonUtils.setText(tv_title,data.getName());
                        ImageLoader.getLoader().load(context,img,data.getLogoUrl());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jump2ChoiseCompany(data ,data.getApiUrl());

                            }
                        });

                    }
                };
            }
        };
    }

    private void jump2ChoiseCompany(CompanyInfoBean data, String apiUrl) {
        HttpMainFactory.queryCompanyLoginInfo(data.getSubAppCode())
                .filter(new Predicate<CommonBean<CompanyTokenInfoBean>>() {
                    @Override
                    public boolean test(@NonNull CommonBean<CompanyTokenInfoBean> stringCommonBean) throws Exception {
                        return stringCommonBean.getCode()==200 ;
                    }
                })
                .flatMap(new Function<CommonBean<CompanyTokenInfoBean>, ObservableSource<CommonBean<LoginInfo>>>() {
                    @Override
                    public ObservableSource<CommonBean<LoginInfo>> apply(@NonNull CommonBean<CompanyTokenInfoBean> stringCommonBean) throws Exception {

                        Config.getInstance().intNetClient(data.getApiUrl(),data.getSubAppCode());
                        Config.getInstance().setCurrentPackageName(data.getAndroidPackage());
                        return HttpMainFactory.loginWithToken(stringCommonBean.getData().getToken(),stringCommonBean.getData().getMobile());
                    }
                })
                .subscribe(new NetObserver<LoginInfo>(this) {
                    @Override
                    public void doOnSuccess(LoginInfo data) {
                        InfoUtils.saveLoginInfo(data);

                        startActivity(new Intent(mContext, SplashActivity.class));
                    }
                });
    }
}