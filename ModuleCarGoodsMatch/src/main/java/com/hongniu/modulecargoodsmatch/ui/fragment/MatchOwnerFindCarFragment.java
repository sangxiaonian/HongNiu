package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.modulecargoodsmatch.R;

/**
 *@data  2019/10/27
 *@Author PING
 *@Description
 *
 * 货主找车
 */
@Route(path = ArouterParamsMatch.fragment_match_owner_find_car)
public class MatchOwnerFindCarFragment extends BaseFragment {
    @Override
    protected View initView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_match_owner_find_car, null, false);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initListener() {
        super.initListener();
    }
}
