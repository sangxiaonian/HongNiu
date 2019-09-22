package com.sang.modulebreakbulk.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsBreakbulk;
import com.hongniu.baselibrary.base.RefrushActivity;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.entity.PageBean;
import com.hongniu.modulebreakbulk.R;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.modulebreakbulk.entity.BreakbulkCompanyInfoParam;
import com.sang.modulebreakbulk.entity.BreakbulkConsignmentRecordBean;
import com.sang.modulebreakbulk.net.HttpBreakFactory;

import java.util.List;

import io.reactivex.Observable;

/**
 *@data  2019/9/22
 *@Author PING
 *@Description
 *
 * 零担发货发货记录
 */
@Route(path = ArouterParamsBreakbulk.activity_breakbulk_consignment_record)
public class BreakbulkConsignmentRecordActivity extends RefrushActivity<BreakbulkConsignmentRecordBean> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakbulk_consignment_record);
    }

    @Override
    protected Observable<CommonBean<PageBean<BreakbulkConsignmentRecordBean>>> getListDatas() {
        return null ;
    }

    @Override
    protected XAdapter<BreakbulkConsignmentRecordBean> getAdapter(List<BreakbulkConsignmentRecordBean> datas) {
        return null;
    }
}
