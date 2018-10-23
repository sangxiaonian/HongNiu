package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.baselibrary.utils.PictureSelectorUtils;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OnItemClickListener;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.entity.UpImgData;
import com.hongniu.moduleorder.net.HttpOrderFactory;
import com.hongniu.moduleorder.ui.adapter.PicAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.sang.common.recycleview.adapter.XAdapter;
import com.sang.common.recycleview.holder.PeakHolder;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.GridLayoutManager.*;

/**
 * @data 2018/10/12
 * @Author PING
 * @Description 上传/修改回单
 */
@Route(path = ArouterParamOrder.activity_order_up_receipt)
public class OrderUpReceiptActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener<LocalMedia> {

    private RecyclerView rv;
    private EditText etRemark;
    private List<LocalMedia> pics;
    private PicAdapter adapter;
    private Button btSum;

    public String orderID;


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

        manager.setSpanSizeLookup(new SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                if (position<=pics.size()){
                    return 1;
                }else {
                    return 4;
                }
            }
        });

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        pics = new ArrayList<>();
        adapter = new PicAdapter(mContext, pics);
        adapter.setOnItemClickListener(this);
        adapter.addFoot(new PeakHolder(mContext, rv, R.layout.order_item_up_receive_img_foot) {
            @Override
            public void initView(int position) {
                super.initView(position);
                getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PictureSelectorUtils.showPicture((Activity) mContext, pics);
                    }
                });
            }
        });
//        adapter.addFoot(new PeakHolder(mContext,rv,R.layout.item_remark){
//            @Override
//            public void initView(int position) {
//                super.initView(position);
//                etRemark=itemView.findViewById(R.id.et_remark);
//            }
//        });
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
        if (event != null && event.getPosition() >= 0 && event.getPosition() < pics.size()) {
            pics.remove(event.getPosition());
            adapter.notifyItemDeleted(event.getPosition());
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_sum) {
            List<String> list = new ArrayList<>();
            for (LocalMedia pic : pics) {
                list.add(pic.getPath());
            }
            HttpOrderFactory.upReceive(orderID, etRemark.getText().toString().trim(), list)
                    .subscribe(new NetObserver<String>(this) {
                        @Override
                        public void doOnSuccess(String data) {
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                            finish();
                        }
                    });
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
        OrderScanReceiptActivity.launchActivity(this, 0, 0, strings);
    }
}
