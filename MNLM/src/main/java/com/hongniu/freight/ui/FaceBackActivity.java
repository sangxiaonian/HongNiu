package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.widget.editext.SearchTextWatcher;
import com.fy.androidlibrary.widget.recycle.utils.XAdapterDataObserver;
import com.fy.companylibrary.config.ArouterParamApp;
import com.fy.companylibrary.net.NetObserver;
import com.sang.thirdlibrary.picture.ImageInforBean;
import com.sang.thirdlibrary.picture.helper.interceptor.FileProgressRequestBody;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.FaceBackParams;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.utils.Utils;
import com.fy.companylibrary.ui.BaseImageUpActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @data 2020/3/3
 * @Author PING
 * @Description 意见反馈页面
 */
@Route(path = ArouterParamApp.activity_face_back)
public class FaceBackActivity extends BaseImageUpActivity implements SearchTextWatcher.SearchTextChangeListener, View.OnClickListener {

    private RecyclerView recyclerView;
    private TextView buSum;
    private TextView tv_count;
    private EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_back);
        setWhitToolBar("意见反馈");
        initView();
        initData();
        initListener();
        
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView=findViewById(R.id.rv);
        tv_count=findViewById(R.id.tv_count);
        buSum=findViewById(R.id.bt_sum);
        et=findViewById(R.id.et);
    }

    @Override
    public void initData() {
        super.initData();
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.registerAdapterDataObserver(new XAdapterDataObserver(){
            @Override
            public void onChanged() {
                tv_count.setText(String.format("已添加%d/%d张图片",datas.size(),getMaxCount()));

            }
        });
        recyclerView.setAdapter(adapter);

        Utils.setButton(buSum,false);
    }

    @Override
    public int getMaxCount() {
        return 4;
    }

    @Override
    protected void initListener() {
        buSum.setOnClickListener(this);
        et.addTextChangedListener(new SearchTextWatcher(this));
    }

    @Override
    protected int getImageType() {
        return 16;
    }

    @Override
    protected Observable<ImageInforBean> upLoadClient(String path, final ImageInforBean bean, FileProgressRequestBody.ProgressListener listener) {
        return HttpAppFactory.upImage(getImageType(),bean.getPath(),listener)
                    .map(new Function<UpImgData, ImageInforBean>() {
                        @Override
                        public ImageInforBean apply(UpImgData upImgData) throws Exception {
                            bean.setPathUrl(upImgData.getAbsolutePath());
                            bean.setPathRelativeUrl(upImgData.getPath());
                            return bean;
                        }
                    })
                ;
    }

    /**
     * 图片尚未上传完成时候，不在上传图片，仅仅保留当前图片
     */
    @Override
    protected void giveUp() {
        FaceBackParams params=new FaceBackParams();
        params.setContent(et.getText().toString());
        List<ImageInforBean> images = helper.getImages();
        if (!CollectionUtils.isEmpty(images)){
            List<String> reslut=new ArrayList<>();
            for (ImageInforBean image : images) {
                reslut.add(image.getPathRelativeUrl());
            }
            params.setImageUrls(reslut);
        }

        HttpAppFactory.faceBack(params)
                .subscribe(new NetObserver<Object>(this){
                    @Override
                    public void doOnSuccess(Object o) {
                        super.doOnSuccess(o);
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        finish();
                    }
                });

    }


    @Override
    public void onSearchTextChange(String msg) {
        Utils.setButton(buSum,msg.length()>10);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            if (et.getText().toString().length()>=10){
                if (isUpLoadSuccess()){
                    giveUp();
                }
            }else {
                ToastUtils.getInstance().show(et.getHint().toString());
            }
        }
    }
}
