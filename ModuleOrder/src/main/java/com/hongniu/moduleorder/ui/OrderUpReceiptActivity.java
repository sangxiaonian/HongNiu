package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.entity.CloseActivityEvent;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.ui.adapter.PicAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.imgload.ImageLoader;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.BaseHolder;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @data 2018/10/12
 * @Author PING
 * @Description 上传回单
 */
@Route(path = ArouterParamOrder.activity_order_up_receipt)
public class OrderUpReceiptActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv;
    private EditText etRemark;
    private List<LocalMedia> pics;
    private XAdapter<LocalMedia> adapter;
    private Button btSum;

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
        btSum=findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(manager);
        pics = new ArrayList<>();

        adapter = new PicAdapter(mContext,pics);
        adapter.addFoot(new PeakHolder(mContext,rv,R.layout.order_item_up_receive_img_foot){
            @Override
            public void initView(int position) {
                super.initView(position);
                getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PictureSelectorUtils.showPicture((Activity) mContext,pics);
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
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    protected boolean getUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OrderEvent.DeletedPic event) {
        if (event!=null&&event.getPosition()>=0&&event.getPosition()<pics.size()){
            pics.remove(event.getPosition());
            adapter.notifyItemDeleted(event.getPosition());
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_sum){
            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
            finish();
        }
    }
}
