package com.hongniu.modulecargoodsmatch.ui.activiry;

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
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.UpLoadImageUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.widget.adapter.OnItemDeletedClickListener;
import com.hongniu.baselibrary.widget.adapter.PicAdapter;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchEntryArriveParams;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.dialog.CenterAlertDialog;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.util.ArrayList;
import java.util.List;

/**
 * @data 2019/11/3
 * @Author PING
 * @Description 确认送达
 */
@Route(path = ArouterParamsMatch.activity_match_entry_arrive)
public class MatchEntryArriveActivity extends BaseActivity implements OnItemDeletedClickListener<LocalMedia>, UpLoadImageUtils.OnUpLoadListener, View.OnClickListener {

    private RecyclerView recycler;
    private EditText etRemark;
    private Button btSum;
    private ArrayList<LocalMedia> pics;
    private PicAdapter adapter;

    UpLoadImageUtils imageUtils = new UpLoadImageUtils(Param.NEWMATCH);
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_entry_arrive);
        id=getIntent().getStringExtra(Param.TRAN);
        setToolbarTitle("确认送达");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        recycler = findViewById(R.id.recycle);
        btSum = findViewById(R.id.bt_sum);
        etRemark = findViewById(R.id.et_remark);
    }

    @Override
    protected void initData() {
        super.initData();
        pics = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        pics = new ArrayList<>();
        adapter = new PicAdapter(mContext, pics);
        adapter.setDeletedClickListener(this);
        adapter.addFoot(new PeakHolder(mContext, recycler, R.layout.order_item_creat_order_img_foot) {
            @Override
            public void initView(int position) {
                super.initView(position);
                TextView tv= itemView.findViewById(R.id.tv);
                tv.setText("添加图片");
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
        recycler.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        imageUtils.setOnUpLoadListener(this);
        btSum.setOnClickListener(this);
    }

    /**
     * 条目被点击
     *
     * @param position
     * @param localMedia
     */
    @Override
    public void onItemDeletedClick(int position, LocalMedia localMedia) {
        pics.remove(position);
        adapter.notifyItemDeleted(position);
        imageUtils.upList(pics);
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
    public void onUpLoadFail(int failCount) {
        Utils.creatDialog(mContext, "图片上传失败", "有" + failCount + "张图片上传失败，是否重新上传？", "放弃上传", "重新上传")
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){

            if (TextUtils.isEmpty(etRemark.getText().toString().trim())){
                ToastUtils.getInstance().show("请输入备注");
                return;
            }

            if (imageUtils.isFinish()) {
                // 如果没有更改过图片，则不上传
                showAlert();
            } else {
                Utils.creatDialog(mContext,imageUtils.unFinishCount() + "张图片上传中", "是否放弃上传？", "确定放弃", "继续上传")
                        .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                            @Override
                            public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                                dialog.dismiss();
                                showAlert();
                            }
                        })
                        .creatDialog(new CenterAlertDialog(mContext))
                        .show();
            }


        }
    }

    private void showAlert() {

        CenterAlertBuilder builder = Utils.creatDialog(mContext, "确定订单已经送达？", "完成送达后，运费将在3个工作日内到账", "还未到达", "确认送达");
        builder
                .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, DialogControl.ICenterDialog cdialog) {
                        cdialog.dismiss();
                        submit();
                    }
                })
                .creatDialog(new CenterAlertDialog(mContext))
                .show();


    }

    private void submit() {
        MatchEntryArriveParams params=new MatchEntryArriveParams();
        params.setId(id);
        List<String> result = imageUtils.getResult();
        if (result.size() == 0 && CommonUtils.isEmptyCollection(pics)) {
            result = null;
        }
        params.setImageUrls(result);
        params.setDeliveryMark(etRemark.getText().toString().trim());
        HttpMatchFactory.matchEntryArrive(params)
                .subscribe(new NetObserver<Object>(this) {
                    @Override
                    public void doOnSuccess(Object data) {
                        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                        finish();
                    }
                })
        ;
    }


}
