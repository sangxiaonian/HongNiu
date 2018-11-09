package com.hongniu.moduleorder.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.order.OrderDetailItemControl;
import com.hongniu.baselibrary.widget.order.helper.CargoOwnerOrder;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.entity.OrderSearchBean;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.widget.OrderSearchPop;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.SwitchTextLayout;
import com.sang.common.widget.popu.BasePopu;
import com.sang.common.widget.popu.inter.OnPopuDismissListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CARGO_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.CAR_OWNER;
import static com.hongniu.baselibrary.widget.order.OrderDetailItemControl.RoleState.DRIVER;

/**
 * 订单搜索历史界面
 */
@Route(path = ArouterParamOrder.activity_order_search)
public class OrderSearchHistoryActivity extends RefrushActivity<OrderSearchBean> implements View.OnClickListener, SwitchTextLayout.OnSwitchListener, OrderSearchPop.OnPopuClickListener, OnPopuDismissListener {

    private EditText etSearch;
    private TextView btCancel;
    private SwitchTextLayout switchView;
    private OrderSearchPop<String> pop;
    List<String> rolas;
    private int position = 0;//
    private OrderDetailItemControl.RoleState roleState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);
        setToolbarTitle("");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        etSearch = findViewById(R.id.et_search);
        btCancel = findViewById(R.id.bt_cancle);
        switchView = findViewById(R.id.switch_left);
        pop = new OrderSearchPop<>(mContext);
        refresh.setEnableLoadMore(false);

    }


    @Override
    protected void initData() {
        super.initData();
        //货主是3 司机1 车主 1
        roleState= (OrderDetailItemControl.RoleState) getIntent().getSerializableExtra(Param.TRAN);
       roleState= roleState==null? CARGO_OWNER:roleState;
        switch (roleState) {
            case CARGO_OWNER:
                position = 1;
                break;
            case CAR_OWNER:
                position = 2;
                break;
            case DRIVER:
                position = 3;
                break;
        }

        rolas = new ArrayList<>();
        rolas.add("货主");
        rolas.add("车主");
        rolas.add("司机");

        switchView.setTitle(rolas.get(position - 1));

        pop.setSelectPosition(position - 1);
        pop.upDatas(rolas);
        queryData(true, true);

    }

    @Override
    protected void initListener() {
        super.initListener();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    DeviceUtils.hideSoft(etSearch);
//                    queryData(true, true);
                    ArouterUtils.getInstance()
                            .builder(ArouterParamOrder.activity_order_search_result)
                            .withSerializable(Param.TRAN, roleState)
                            .withString(Param.TITLE,etSearch.getText().toString().trim())
                            .navigation(mContext);

                }
                return false;
            }
        });
        btCancel.setOnClickListener(this);
        switchView.setListener(this);
        pop.setListener(this);
        pop.setOnDismissListener(this);
    }

    @Override
    protected Observable<CommonBean<PageBean<OrderSearchBean>>> getListDatas() {
        return HttpOrderFactory.searchOrder();
    }

    @Override
    protected XAdapter<OrderSearchBean> getAdapter(List<OrderSearchBean> datas) {
        return new XAdapter<OrderSearchBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderSearchBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderSearchBean>(mContext, parent, R.layout.order_item_order_search) {
                    @Override
                    public void initView(View itemView, final int position, final OrderSearchBean data) {
                        super.initView(itemView, position, data);
                        TextView tv = itemView.findViewById(R.id.tv_title);
                        tv.setText(data.getText()==null?"":data.getText());
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArouterUtils.getInstance()
                                        .builder(ArouterParamOrder.activity_order_search_result)
                                        .withSerializable(Param.TRAN, roleState)
                                        .withString(Param.TITLE,data.getText())
                                        .navigation(mContext);

                            }
                        });
                    }
                };
            }
        };
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_cancle) {
            finish();
        }
    }

    @Override
    public void onOpen(SwitchTextLayout switchTextLayout, View view) {
        pop.show(view);
    }

    @Override
    public void onClose(SwitchTextLayout switchTextLayout, View view) {
        pop.dismiss();
    }

    @Override
    public void onPopuClick(OrderSearchPop pop, View target, int position) {
        pop.dismiss();
        switchView.closeSwitch();
        this.position = position + 1;
        switch (this.position){
            case 1:
                roleState=CARGO_OWNER;
                break;case 2:
                roleState=CAR_OWNER;
                break;case 3:
                roleState=DRIVER;
                break;

        }
        switchView.setTitle(rolas.get(position));
    }

    /**
     * Popu dimiss 监听
     *
     * @param popu   当前popu
     * @param target 目标View
     */
    @Override
    public void onPopuDismsss(BasePopu popu, View target) {
        switchView.closeSwitch();
    }

    @Override
    public void onBackPressed() {
        if (pop.isShow()) {
            pop.dismiss();
        } else {
            super.onBackPressed();
        }
    }
}
