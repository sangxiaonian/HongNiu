package com.hongniu.freight.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fy.androidlibrary.event.BusFactory;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.sang.thirdlibrary.picture.ImageInforBean;
import com.sang.thirdlibrary.picture.helper.interceptor.FileProgressRequestBody;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.BaseImageUpActivity;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.QueryReceiveBean;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @data 2018/10/12
 * @Author PING
 * @Description 上传/修改回单
 */
@Route(path = ArouterParamMNLM.activity_order_up_receipt)
public class OrderUpReceiptActivity extends BaseImageUpActivity implements View.OnClickListener  {

    private RecyclerView rv;
    private EditText etRemark;
    private Button btSum;

    public String orderID;
    private QueryReceiveBean bean;//传入的数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_order_up_receipt);
        setWhitToolBar("上传回单");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        etRemark = findViewById(R.id.et_remark);
        rv = findViewById(R.id.rv);
        btSum = findViewById(R.id.bt_sum);
    }


    @Override
    public void initData() {
        super.initData();
        orderID = getIntent().getStringExtra(Param.TRAN);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);


    }


    @Override
    protected int getImageType() {
        return 2;
    }

    @Override
    protected Observable<ImageInforBean> upLoadClient(String path, final ImageInforBean bean, FileProgressRequestBody.ProgressListener listener) {
        return HttpAppFactory.upImage(getImageType(), bean.getPath(), listener)
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

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
    }

    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(QueryReceiveBean event) {
        if (event != null) {
            this.bean = event;
            etRemark.setText(bean.getRemark() == null ? "" : bean.getRemark());
            if (!CollectionUtils.isEmpty(bean.getImages())) {
                for (UpImgData imagesBean : bean.getImages()) {
                    ImageInforBean media = new ImageInforBean();
                    media.setPath(imagesBean.getAbsolutePath());
                    media.setPathOriginal(imagesBean.getAbsolutePath());
                    media.setPathUrl(imagesBean.getAbsolutePath());
                    media.setPathRelativeUrl(imagesBean.getPath());
                    datas.add(media);
                }
            }
            helper.upDates(datas);
//            imageUtils.upList(pics);
            adapter.notifyDataSetChanged();
            etRemark.setEnabled(false);
            etRemark.setFocusable(false);
            etRemark.setFocusableInTouchMode(false);
            btSum.setVisibility(View.GONE);
            adapter.hideAdd(true);
            setWhitToolBar("查看回单");

        }
        BusFactory.getBus().removeStickyEvent(event);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (isUpLoadSuccess()) {
                // 如果没有更改过图片，则不上传
                giveUp();
            }

        }
    }

    @Override
    protected boolean showProgress() {
        return true;
    }

    protected void giveUp() {
        QueryReceiveBean bean=new QueryReceiveBean();
        bean.setRemark(etRemark.getText().toString());
        List<ImageInforBean> images = helper.getImages();
        if (!CollectionUtils.isEmpty(images)) {
            List<String> reslut = new ArrayList<>();
            for (ImageInforBean image : images) {
                reslut.add(image.getPathRelativeUrl());
            }
            bean.setImageUrls(reslut);
        }
        bean.setOrderId(orderID);
        HttpAppFactory.saveReceiptInfo(bean)
                .subscribe(new NetObserver<String>(this){
                    @Override
                    public void doOnSuccess(String s) {
                        super.doOnSuccess(s);
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        finish();
                    }
                });


    }


    @Override
    public void onCancel() {

    }
}
