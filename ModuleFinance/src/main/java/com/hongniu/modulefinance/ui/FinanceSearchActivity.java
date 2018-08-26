package com.hongniu.modulefinance.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.order.OrderDetailDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.QueryExpendBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.builder.BottomAlertBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.HTTP;

@Route(path = ArouterParamsFinance.activity_finance_search)
public class FinanceSearchActivity extends RefrushActivity<OrderDetailBean> implements View.OnClickListener {

    private EditText etSearch;
    private View imgClear;
    private View btCancle;
    private QueryExpendBean bean = new QueryExpendBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_search);
        setToolbarTitle("");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();

        etSearch = findViewById(R.id.et_search);
        imgClear = findViewById(R.id.img_clear);
        btCancle = findViewById(R.id.tv_cancel);


    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected Observable<CommonBean<PageBean<OrderDetailBean>>> getListDatas() {
        bean.setPageNum(currentPage);
//        bean.setFinanceType(2);
        return HttpFinanceFactory.queryFinance(bean);
    }

    @Override
    protected XAdapter<OrderDetailBean> getAdapter(List<OrderDetailBean> datas) {
        return new XAdapter<OrderDetailBean>(mContext, datas) {
            @Override
            public BaseHolder<OrderDetailBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<OrderDetailBean>(mContext, parent, R.layout.finance_item_finance) {
                    @Override
                    public void initView(View itemView, int position, final OrderDetailBean data) {
                        super.initView(itemView, position, data);
                        TextView tvOrder = itemView.findViewById(R.id.tv_order);
                        TextView tvCarNum = itemView.findViewById(R.id.tv_car_num);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tvPrice = itemView.findViewById(R.id.tv_price);

                        tvOrder.setText("订单号：" + (data.getOrderNum() == null ? "" : data.getOrderNum()));
                        tvCarNum.setText("车牌号码：" + (data.getCarnum() == null ? "" : data.getCarnum()));
                        tvTime.setText("付费时间：" + (data.getPayTime() == 0 ? "" : ConvertUtils.formatTime(data.getPayTime(), "yyyy-MM-dd HH:mm:ss")));
                        tvPrice.setText("1200.0");
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                OrderDetailDialog orderDetailDialog = new OrderDetailDialog(mContext);
                                orderDetailDialog.setOrdetail(data);
                                new BottomAlertBuilder()
                                        .setDialogTitle(getString(R.string.login_car_entry_deleted))
                                        .creatDialog(orderDetailDialog)
                                        .show();
                            }
                        });

                    }
                };
            }
        };
    }

    @Override
    protected void initListener() {
        super.initListener();
        imgClear.setOnClickListener(this);
        btCancle.setOnClickListener(this);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                    imgClear.setVisibility(View.GONE);
                } else {
                    imgClear.setVisibility(View.VISIBLE);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    bean.setCarNo(etSearch.getText().toString().trim());
                    queryData(true,true);

                    DeviceUtils.hideSoft(etSearch);
                }
                return false;
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.img_clear == id) {
            etSearch.setText("");
        } else if (R.id.tv_cancel == id) {
            finish();
        }

    }
}
