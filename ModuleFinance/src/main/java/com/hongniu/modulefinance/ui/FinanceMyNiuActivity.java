package com.hongniu.modulefinance.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsApp;
import com.hongniu.baselibrary.arouter.ArouterParamsFinance;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.H5Config;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.baselibrary.widget.BottomListDialog;
import com.hongniu.modulefinance.R;
import com.hongniu.modulefinance.entity.NiuOfAccountBean;
import com.hongniu.modulefinance.net.HttpFinanceFactory;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.widget.dialog.inter.DialogControl;
import com.sang.thirdlibrary.chact.ChactHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @data 2019/3/3
 * @Author PING
 * @Description 我的牛背页面
 */
@Route(path = ArouterParamsFinance.activity_finance_niu)
public class FinanceMyNiuActivity extends RefrushActivity<NiuOfAccountBean> {

    private TextView tvNiu;//我的牛贝
    private TextView tvEarning;//我的收益
    private TextView tvLastEarning;//昨日收益
    BottomListDialog bottomListDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_my_niu);
        initView();
        initData();
        initListener();
        queryData(true);
    }

    @Override
    protected void initView() {
        super.initView();
        bottomListDialog =new BottomListDialog(mContext);
        setToolbarRedTitle(getString(R.string.wallet_niu_title));
        setToolbarSrcRight("规则");
        tvEarning = findViewById(R.id.tv_earning);
        tvLastEarning = findViewById(R.id.tv_last_earning);
        tvNiu = findViewById(R.id.tv_niu_count);


    }

    @Override
    protected void initData() {
        super.initData();
        tvNiu.setText("10010.00");
        tvEarning.setText("1738.00");
        tvLastEarning.setText("89.00");
        List<String> list=new ArrayList<>();
        list.add("在线聊天");
        list.add("电话联系");
        bottomListDialog.setDatas(list);

    }

    @Override
    protected void initListener() {
        super.initListener();
        setToolbarRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H5Config h5Config = new H5Config("规则", Param.NIUBEI, true);
                ArouterUtils.getInstance().builder(ArouterParamsApp.activity_h5).withSerializable(Param.TRAN, h5Config).navigation(mContext);

            }
        });
    }

    @Override
    protected Observable<CommonBean<PageBean<NiuOfAccountBean>>> getListDatas() {
        return HttpFinanceFactory.gueryNiuList(currentPage, 2);
    }


    @Override
    protected XAdapter<NiuOfAccountBean> getAdapter(List<NiuOfAccountBean> datas) {
        return new XAdapter<NiuOfAccountBean>(mContext, datas) {
            @Override
            public BaseHolder<NiuOfAccountBean> initHolder(ViewGroup parent, int viewType) {
                return new BaseHolder<NiuOfAccountBean>(mContext, parent, R.layout.finance_item_my_niu) {
                    @Override
                    public void initView(View itemView, int position, final NiuOfAccountBean data) {
                        super.initView(itemView, position, data);
                        //名字
                        TextView tvName = itemView.findViewById(R.id.tv_name);
                        //收益
                        TextView tvEarning = itemView.findViewById(R.id.tv_earning);
                        //更多
                        final ImageView imgMore = itemView.findViewById(R.id.img_more);
                        final String name = !TextUtils.isEmpty(data.getName()) ? data.getName() : (TextUtils.isEmpty(data.getContact()) ? "" : data.getContact());
                        tvName.setText(name + "(+" + (ConvertUtils.getRandom(1, 10)) + "牛贝)");

                        StringBuffer buffer = new StringBuffer();
                        buffer.append("总收益")
                                .append(ConvertUtils.getRandom(100, 900))
                                .append("元")
                                .append(" ")
                                .append("昨日收益")
                                .append(ConvertUtils.getRandom(0, 100))
                                .append("元");
                        tvEarning.setText(buffer.toString());

                        imgMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomListDialog.setEntryClickListener(new DialogControl.OnEntryClickListener<String>() {
                                    @Override
                                    public void onEntryClick(Dialog dialog, int position, String item) {
                                        dialog.dismiss();
                                        if (position==0){
                                            ChactHelper.getHelper().startPriver(context, data.getId(), name);
                                        }else if (position==1){
                                            CommonUtils.toDial(context, data.getMobile());
                                        }
                                    }
                                })
                                .show(imgMore);

                            }
                        });
                    }
                };
            }
        };
    }
}
