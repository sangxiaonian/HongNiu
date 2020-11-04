package com.hongniu.freight.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.fy.androidlibrary.widget.recycle.adapter.SelectAdapter;
import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.holder.PeakHolder;
import com.fy.androidlibrary.widget.recycle.utils.XLineDivider;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.fy.companylibrary.ui.RefrushActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.config.Status;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.QueryOrderListBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.ui.holder.EmptyHolder;
import com.hongniu.freight.ui.holder.order.OrderHolderBuider;
import com.hongniu.freight.ui.holder.order.XOrderButtonClick;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2020/2/10
 * @Author PING
 * @Description 接单中心
 */
@Route(path = ArouterParamMNLM.activity_order_receiving)
public class OrderReceivingCenterActivity extends RefrushActivity<OrderInfoBean> implements XOrderButtonClick.NextStepListener,SelectAdapter.SingleSelectedListener<Status> {

    private RoleOrder role;
    private RecyclerView rvTitle;
    private SelectAdapter<Status> selectAdapter;
    private ArrayList<Status> titles;
    private Status statu=Status.WAITE_RECEIVING_ORDER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_order_receiving);
        role = RoleOrder.PLATFORM;
        setWhitToolBar("");
        setToolbarSrcRight(R.drawable.icon_search_434445);
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_search_order)
                        .withSerializable(Param.TRAN, role)
                        .navigation(mContext);
            }
        });


        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        rvTitle = findViewById(R.id.rv_title);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        queryData(true);
    }

    @Override
    protected void initData() {
        super.initData();
        titles = new ArrayList<>(Arrays.asList(Status.values()));
        titles.remove(1);
        if (selectAdapter == null) {
            selectAdapter = new SelectAdapter<Status>(mContext, rvTitle) {
                @Override
                public void initView(View itemView, int position, Status s, boolean select) {
                    TextView tvTitle = itemView.findViewById(R.id.tv_title);
                    View flag = itemView.findViewById(R.id.view_flag);
                    tvTitle.setText(s.getName());
                    tvTitle.setTextColor(getResources().getColor(select ? R.color.color_of_040000 : R.color.color_of_999999));
                    tvTitle.setTypeface(Typeface.defaultFromStyle(select ? Typeface.BOLD : Typeface.NORMAL));
                    flag.setBackgroundResource(select ? R.drawable.ovl_2_e50000 : 0);
                }
            };
            selectAdapter.setCanEmpty(false);
            selectAdapter.setSingle(true);
            selectAdapter.setSingleSelectedListener(this);
            selectAdapter.setItemLayoutID(R.layout.mnlm_item_title);
            rvTitle.setAdapter(selectAdapter);
            XLineDivider tagLine = new XLineDivider()
                    .setmOrientation(LinearLayoutManager.HORIZONTAL)
                    .setmDividerWidth(DeviceUtils.dip2px(mContext, 20))
                    .setDividerColor(Color.TRANSPARENT)
                    .setHeadGap(DeviceUtils.dip2px(mContext, 25), DeviceUtils.dip2px(mContext, 1))
                    .hideLast(false);
            rvTitle.addItemDecoration(tagLine);
        }
        selectAdapter.notifyAllItem(titles, 0);

    }

    @Override
    protected void initListener() {
        super.initListener();
    }


    /**
     * 单选时候
     *
     * @param position
     * @param selected
     * @param check
     */
    @Override
    public void onSingleSelected(int position, Status selected, boolean check) {
        statu=selected;
        queryData(true,true);
    }

    /**
     * 获取空界面
     *
     * @param parent
     * @return
     */
    @Override
    protected PeakHolder getEmptyHolder(ViewGroup parent) {
        EmptyHolder emptyHolder = new EmptyHolder(mContext, rv);
        return emptyHolder;
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderInfoBean>>> getListDatas() {
        QueryOrderListBean bean=new QueryOrderListBean();
        bean.setPageSize(Param.PAGE_SIZE);
        bean.setPageNum(currentPage);
        bean.setStatus(statu.getStatus()+"");
        return HttpAppFactory.queryPlatformOrderList(bean);
    }

    @Override
    protected XAdapter<OrderInfoBean> getAdapter(List<OrderInfoBean> datas) {
        return new XAdapter<OrderInfoBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderInfoBean> initHolder(ViewGroup parent, int viewType) {
                XOrderButtonClick xOrderButtonClick = new XOrderButtonClick(mContext);
                xOrderButtonClick.setType(role);

                return new OrderHolderBuider(mContext)
                        .setParent(parent)
                        .setType(role)
                        .setOrderButtonClickListener(xOrderButtonClick)
                        .build();
            }
        };
    }

    /**
     * 再进行取消等操作完成之后,刷新界面
     */
    @Override
    public void doUpdate() {
        queryData(true,true);
    }
}
