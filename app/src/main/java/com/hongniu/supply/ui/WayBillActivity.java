package com.hongniu.supply.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.BaseUtils;
import com.hongniu.supply.R;
import com.hongniu.supply.entity.WayBillBean;
import com.hongniu.supply.net.HttpMainFactory;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @data 2019/8/24
 * @Author PING
 * @Description 运单状态界面
 */
@Route(path = ArouterParamsApp.activity_way_bill)
public class WayBillActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<WayBillBean> datas;
    private XAdapter<WayBillBean> adapter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_way_bill);
        setToolbarTitle("运单状态信息");
        initView();
        initData();
        initListener();
        id = getIntent().getStringExtra(Param.TRAN);
        queryWill();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = findViewById(R.id.rv);
    }

    @Override
    protected void initData() {
        super.initData();
        datas = new ArrayList<>();
        adapter = new XAdapter<WayBillBean>(mContext, datas) {
            @Override
            public BaseHolder<WayBillBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<WayBillBean>(mContext, parent, R.layout.item_waybill) {
                    @Override
                    public void initView(View itemView, int position, WayBillBean data) {
                        super.initView(itemView, position, data);
                        TextView tvTime = itemView.findViewById(R.id.tv_time);
                        TextView tv_time_h = itemView.findViewById(R.id.tv_time_h);
                        TextView tvState = itemView.findViewById(R.id.tv_state);
                        TextView tvDes = itemView.findViewById(R.id.tv_des);
                        ImageView img = itemView.findViewById(R.id.img);
                        View point = itemView.findViewById(R.id.point);
                        String createTime = data.getCreateTime();
                        if (TextUtils.isEmpty(createTime)) {
                            tvTime.setText("");
                            tv_time_h.setText("");
                        } else {
                            tvTime.setText(ConvertUtils.formatString(createTime, "yyyy-MM-dd HH:mm:ss", "MM-dd"));
                            tv_time_h.setText(ConvertUtils.formatString(createTime, "yyyy-MM-dd HH:mm:ss", "HH:mm"));

                        }
                        String des = TextUtils.isEmpty(data.getDescription()) ? "" : data.getDescription();
                        Pattern pattern = Pattern.compile("[(0-9)]{11}");
                        String[] split = TextUtils.split(des, pattern);
                        final SpannableStringBuilder builder = new SpannableStringBuilder(des);
                        int start = 0;
                        if (split.length > 1) {
                            for (int i = 0; i < split.length; i++) {
                                if (i < split.length - 1) {
                                    start += split[i].length();
                                    final int finalStart = start;
                                    builder.setSpan(new ClickableSpan() {
                                        @Override
                                        public void onClick(@NonNull View widget) {
                                            CommonUtils.call(mContext, builder.subSequence(finalStart, finalStart + 11).toString());
                                        }

                                        @Override
                                        public void updateDrawState(@NonNull TextPaint ds) {
                                            ds.setColor(getResources().getColor(R.color.color_light));
                                            ds.setUnderlineText(false);

                                        }
                                    }, start, start + 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

                                    start += 11;
                                }
                            }
                        }
                        tvDes.setMovementMethod(LinkMovementMethod.getInstance());
                        tvDes.setText(builder);
                        tvState.setText(TextUtils.isEmpty(data.getStatusDesc()) ? "" : data.getStatusDesc());
                        if (!TextUtils.isEmpty(data.getIcon())) {
                            ImageLoader.getLoader().load(mContext, img, data.getIcon());
                        }


                    }
                };
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void queryWill() {
        HttpMainFactory
                .queryWaybill(id)
                .subscribe(new NetObserver<List<WayBillBean>>(this) {
                    @Override
                    public void doOnSuccess(List<WayBillBean> data) {

                        datas.clear();
                        if (data != null && !data.isEmpty()) {
                            datas.addAll(data);
                        }
                        adapter.notifyDataSetChanged();
                        if (BaseUtils.isCollectionsEmpty(datas)){
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("暂无运单信息");
                        }
                    }


                });
    }

}
