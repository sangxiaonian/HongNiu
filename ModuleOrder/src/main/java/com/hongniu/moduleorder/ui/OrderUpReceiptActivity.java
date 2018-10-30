package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.net.HttpAppFactory;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.UpLoadImageUtils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OnItemClickListener;
import com.hongniu.moduleorder.control.OnItemDeletedClickListener;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.QueryReceiveBean;
import com.hongniu.baselibrary.entity.UpImgData;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.ui.adapter.PicAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.event.BusFactory;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @data 2018/10/12
 * @Author PING
 * @Description 上传/修改回单
 */
@Route(path = ArouterParamOrder.activity_order_up_receipt)
public class OrderUpReceiptActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener<LocalMedia>, OnItemDeletedClickListener<LocalMedia>,UpLoadImageUtils.OnUpLoadListener {

    private RecyclerView rv;
    private EditText etRemark;
    private List<LocalMedia> pics;
    private PicAdapter adapter;
    private Button btSum;

    public String orderID;
    UpLoadImageUtils imageUtils=new UpLoadImageUtils();
    //    private List<LocalMedia> urlImages=new ArrayList<>();
    private QueryReceiveBean bean;//传入的数据


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_up_receipt);
        setToolbarTitle(getString(R.string.order_up_receipt));
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
    protected void initData() {
        super.initData();
        orderID = getIntent().getStringExtra(Param.TRAN);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        pics = new ArrayList<>();
        adapter = new PicAdapter(mContext, pics);
        adapter.addFoot(new PeakHolder(mContext, rv, R.layout.order_item_up_receive_img_foot) {
            @Override
            public void initView(int position) {
                super.initView(position);
                getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pics.size() >= Param.IMAGECOUNT) {
                            ToastUtils.getInstance().show("已达到图片最大数量");
                        } else {
                            PictureSelectorUtils.showPicture((Activity) mContext, pics);
                        }
                    }
                });
            }
        });

        rv.setAdapter(adapter);
    }


    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        adapter.setOnItemClickListener(this);
        imageUtils.setOnUpLoadListener(this);
        adapter.setDeletedClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    pics.clear();
                    pics.addAll(selectList);
                    imageUtils.upList(pics);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }



    @Override
    protected boolean getUseEventBus() {
        return true;
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.UpReceiver event) {
        QueryReceiveBean bean = event.bean;
        if (bean != null) {
            this.bean = event.bean;
            etRemark.setText(bean.getRemark() == null ? "" : bean.getRemark());
            if (!CommonUtils.isEmptyCollection(bean.getImages())) {
                for (UpImgData imagesBean : bean.getImages()) {
                    LocalMedia media = new LocalMedia();
                    media.setPath(imagesBean.getAbsolutePath());
                    media.setRelativePath(imagesBean.getPath());
                    pics.add(media);
                }
            }
            adapter.notifyDataSetChanged();
        }
        BusFactory.getBus().removeStickyEvent(event);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            if (imageUtils.isFinish()){
                // 如果没有更改过图片，则不上传
                List<String> result = imageUtils.getResult();
                if (result.size()==0&&!CommonUtils.isEmptyCollection(pics)){
                    result=null;
                }
                //点击保存按钮
                HttpOrderFactory.upReceive(orderID, etRemark.getText().toString().trim(), result)
                        .subscribe(new NetObserver<String>(this) {
                            @Override
                            public void doOnSuccess(String data) {
                                ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                                finish();
                            }
                        });
            }else {
                ToastUtils.getInstance().show(imageUtils.unFinishCount()+"张图片上传中，请稍后");
            }

        }
    }

    /**
     * 条目被点击
     *
     * @param position
     * @param localMedia
     */
    @Override
    public void onItemClick(int position, LocalMedia localMedia) {
        List<String> strings = new ArrayList<>();
        for (LocalMedia pic : pics) {
            strings.add(pic.getPath());
        }
        OrderScanReceiptActivity.launchActivity(this, 0, position, strings);
    }

    /**
     * 条目被点击
     *
     * @param position
     * @param localMedia
     */
    @Override
    public void onItemDeletedClick(final int position, LocalMedia localMedia) {
        pics.remove(position);
        adapter.notifyItemDeleted(position);
    }

    @Override
    public void onUpLoadFail(int failCount) {
        creatDialog("图片上传失败", "有"+failCount+"张图片上传失败，是否重新上传？", "放弃上传", "重新上传")
                .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                    @Override
                    public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
                        dialog.dismiss();
                        imageUtils.reUpLoad();
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();
    }

    private CenterAlertBuilder creatDialog(String title, String content, String btleft, String btRight) {
        return new CenterAlertBuilder()
                .setDialogTitle(title)
                .setDialogContent(content)
                .setBtLeft(btleft)
                .setBtRight(btRight)
                .setBtLeftColor(getResources().getColor(R.color.color_title_dark))
                .setBtRightColor(getResources().getColor(R.color.color_white))
                .setBtRightBgRes(R.drawable.shape_f06f28);
    }
}
