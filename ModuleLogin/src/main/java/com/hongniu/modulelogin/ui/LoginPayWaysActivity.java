package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.modulelogin.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.recycleview.touchhelper.DragSortHelper;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.CenteredImageSpan;

import java.util.ArrayList;

/**
 * 我的收款方式
 */
@Route(path = ArouterParamLogin.activity_pay_ways)
public class LoginPayWaysActivity extends BaseActivity {

    RecyclerView recyclerView;
    private ArrayList<String> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pay_ways);
        setToolbarTitle(getString(R.string.login_pay_title));

        initView();
        initData();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    protected void initData() {
        super.initData();
        datas = new ArrayList<>();

        final XAdapter<String> adapter = new XAdapter<String>(mContext, datas) {
            @Override
            public BaseHolder<String> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<String>(mContext, parent, R.layout.login_item_pay_ways) {
                    @Override
                    public void initView(View rootView, int position, final String data) {
                        super.initView(rootView, position, data);
                        itemView.animate().scaleY(1).scaleY(1).start();
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        if (data.length() == 11) {
                            builder.append(data);
                            String tag = "\t\t\t";
                            builder.insert(3, tag);
                            builder.insert(3 + tag.length() + 4, tag);
                            String cellTag = " ";
                            int start = 4 + tag.length();
                            for (int i = 0; i < 3; i++) {
                                builder.insert(start, cellTag);
                                start += 1 + cellTag.length();
                            }
                            start = 3 + tag.length();
                            for (int i = 0; i < 4; i++) {
                                ImageSpan imageSpan = new CenteredImageSpan(mContext, R.drawable.shape_circle_ffffff);
                                builder.setSpan(imageSpan, start, start + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                                start += 1 + cellTag.length();
                            }
                        }

                        TextView tvPhone = itemView.findViewById(R.id.tv_phone);
                        tvPhone.setText(builder);
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(data);
                            }
                        });

                    }
                };
            }


        };

        ItemTouchHelper helper = new ItemTouchHelper(new DragSortHelper(adapter));
        recyclerView.setAdapter(adapter);
        helper.attachToRecyclerView(recyclerView);

        adapter.addFoot(new PeakHolder(mContext,recyclerView,R.layout.login_item_foot_pay_ways){
            @Override
            public void initView(int position) {
                super.initView(position);

                getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datas.add("16666666666");
                        datas.add("15555555555");
                        datas.add("17777777777");
                        datas.add("19999999999");
                        datas.add("18888888888");
                        adapter.removeFoot(0);
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }
}
