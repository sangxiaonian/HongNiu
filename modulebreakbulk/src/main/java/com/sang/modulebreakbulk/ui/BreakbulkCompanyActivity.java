package com.sang.modulebreakbulk.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.recycleview.RecycleViewSupportEmpty;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;
import com.sang.modulebreakbulk.net.HttpBreakFactory;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/9/22
 * @Author PING
 * @Description 零担发货选择物流公司页面
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_company)
public class BreakbulkCompanyActivity extends RefrushActivity<BreakbulkCompanyInfoBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_company);
        setToolbarTitle("选择物流公司");
        setToolbarSrcRight("发货记录");
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance()
                        .builder(ArouterParamsBreakbulk.activity_breakbulk_consignment_record)
                        .navigation(mContext);
            }
        });
        initView();
        initData();
        initListener();
        queryData(true);
        if (rv instanceof RecycleViewSupportEmpty) {
            ((RecycleViewSupportEmpty) rv).setEmptyView(0,"该城市正在对接中，敬请期待...");
        }
        refresh.hideLoadFinish();
    }

    @Override
    protected Observable<CommonBean<PageBean<BreakbulkCompanyInfoBean>>> getListDatas() {
        BreakbulkCompanyInfoParam breakbulkCompanyInfoParam = new BreakbulkCompanyInfoParam();
        return HttpBreakFactory.queryBreakbulkCompany(breakbulkCompanyInfoParam);
    }

    @Override
    protected XAdapter<BreakbulkCompanyInfoBean> getAdapter(List<BreakbulkCompanyInfoBean> datas) {
        return new XAdapter<BreakbulkCompanyInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<BreakbulkCompanyInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BreakbulkCompanyInfoBean>(mContext, parent, R.layout.item_breakbulk_company) {
                    @Override
                    public void initView(View itemView, int position, final BreakbulkCompanyInfoBean data) {
                        super.initView(itemView, position, data);
                        ImageView img = itemView.findViewById(R.id.img);
                        ImageView img_phone = itemView.findViewById(R.id.img_phone);
                        ImageView img_chat = itemView.findViewById(R.id.img_chat);
                        TextView tv_point = itemView.findViewById(R.id.tv_point);
                        TextView tv_title = itemView.findViewById(R.id.tv_title);
                        TextView tv_address = itemView.findViewById(R.id.tv_address);

                        tv_title.setText(data.getCompanyname() == null ? "" : data.getCompanyname());
                        tv_address.setText(data.getWorkaddress() == null ? "" : data.getWorkaddress());
                        tv_point.setText(data.getTransportLine() == null ? "" : data.getTransportLine());
                        ImageLoader.getLoader().load(mContext, img, data.getRef1());
                        img_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CommonUtils.toDial(mContext, data.getContactPhone());
                            }
                        });
                        img_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChactHelper.getHelper().startPriver(mContext, data.getId(), data.getContact());
                            }
                        });
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamsBreakbulk.activity_breakbulk_company_detail)
                                        .withParcelable(Param.TRAN,data)
                                        .navigation(mContext);
                            }
                        });

                    }
                };
            }
        };
    }
}
