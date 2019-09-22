package com.sang.modulebreakbulk.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ToastUtils;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoBean;

import java.util.ArrayList;
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
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected Observable<CommonBean<PageBean<BreakbulkCompanyInfoBean>>> getListDatas() {
        CommonBean<PageBean<BreakbulkCompanyInfoBean>> pageBeanCommonBean = new CommonBean<PageBean<BreakbulkCompanyInfoBean>>();
        pageBeanCommonBean.setCode(200);
        PageBean<BreakbulkCompanyInfoBean> pageBean = new PageBean<>();
        ArrayList<BreakbulkCompanyInfoBean> infoBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            infoBeans.add(new BreakbulkCompanyInfoBean());
        }
        pageBean.setList(infoBeans);
        pageBeanCommonBean.setData(pageBean);
        return Observable.just(pageBeanCommonBean);
    }

    @Override
    protected XAdapter<BreakbulkCompanyInfoBean> getAdapter(List<BreakbulkCompanyInfoBean> datas) {
        return new XAdapter<BreakbulkCompanyInfoBean>(mContext,datas) {
            @Override
            public BaseHolder<BreakbulkCompanyInfoBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<BreakbulkCompanyInfoBean>(mContext,parent,R.layout.item_breakbulk_company){
                    @Override
                    public void initView(View itemView, int position, BreakbulkCompanyInfoBean data) {
                        super.initView(itemView, position, data);
                        ImageView img=itemView.findViewById(R.id.img);
                        ImageView img_phone=itemView.findViewById(R.id.img_phone);
                        ImageView img_chat=itemView.findViewById(R.id.img_chat);
                        TextView tv_point=itemView.findViewById(R.id.tv_point);
                        TextView tv_title=itemView.findViewById(R.id.tv_title);
                        TextView tv_address=itemView.findViewById(R.id.tv_address);
                        tv_title.setText("测试有限公司");
                        tv_address.setText("宇宙中心");
                        tv_point.setText("我心里<------->你心里");
                        ImageLoader.getLoader().load(mContext,img,null);
                        img_phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().show("打电话");
                            }
                        });
                        img_chat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().show("聊天");
                            }
                        });

                    }
                };
            }
        };
    }
}
