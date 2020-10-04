package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.R;
import com.hongniu.moduleorder.control.OrderEvent;
import com.hongniu.moduleorder.ui.adapter.SimpleFragmentAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.fy.androidlibrary.event.BusFactory;
import com.hongniu.baselibrary.entity.CommonBean;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查看回单、大图，主要是对图片进行预览
 * <p>
 * 查看回单，查看货单 type 0 单纯的查看回单 1预览回单，有删除功能 2查看货单，有重新上传功能
 */
public class OrderScanReceiptActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private final static String previewList = "previewSelectList";
    private final static String index = "position";

    private PreviewViewPager viewPager;
    private TextView tvIndex;

    private List<String> lists = new ArrayList<>();
    private int currentPosition;
    private int type;
    private SimpleFragmentAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check_receipt);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        viewPager = findViewById(R.id.preview_pager);
        tvIndex = findViewById(R.id.tv_index);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra(index, 0);
        ArrayList<String> extra = intent.getStringArrayListExtra(previewList);
        if (!CollectionUtils.isEmpty(extra)) {
            lists.addAll(extra);
        }
        changeIndex(currentPosition);

        pagerAdapter = new SimpleFragmentAdapter(mContext, lists);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPosition);
        type = intent.getIntExtra(Param.TRAN, 0);

        if (type == 0) {//查看回单
            setToolbarDarkTitle("查看回单");
        } else if (type == 1) {
            setToolbarDarkTitle("查看回单");
            setToolbarSrcRight("删除");
            tvToolbarRight.setTextColor(getResources().getColor(R.color.tool_right));
            setToolbarRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentPosition >= 0 && currentPosition < lists.size()) {
                        BusFactory.getBus().post(new OrderEvent.DeletedPic(currentPosition,lists.get(currentPosition)));
                        lists.remove(currentPosition);
                        currentPosition = currentPosition >= lists.size() ? lists.size() - 1 : currentPosition;
                        pagerAdapter.notifyDataSetChanged();
                        changeIndex(currentPosition);
                    }
                }
            });
        } else if (type == 2) {
//            setToolbarSrcRight("重新上传");
            setToolbarDarkTitle("查看货单");
//            setToolbarRightClick(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    creatDialog()
//                            .setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
//                                @Override
//                                public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
//                                    dialog.dismiss();
//                                    finish();
//                                }
//                            })
//                            .setRightClickListener(new DialogControl.OnButtonRightClickListener() {
//                                @Override
//                                public void onRightClick(View view, DialogControl.ICenterDialog dialog) {
//                                    dialog.dismiss();
//                                    PictureSelectorUtils.showPicture((Activity) mContext,null);
//                                }
//                            })
//                    ;
//                }
//            });
        }


    }

    private void changeIndex(int index) {
        tvIndex.setText((index+1) + "/" + lists.size());
    }

    /**
     * 进入货单等的查看页面
     *
     * @param activity
     * @param type     0 查看回单 1预览回单 2 上传货单
     * @param position
     * @param medias
     */
    public static void launchActivity(Activity activity, int type, int position, List<String> medias) {
        Intent intent = new Intent(activity, OrderScanReceiptActivity.class);
        intent.putExtra(previewList, (Serializable) medias);
        intent.putExtra(index, position);
        intent.putExtra(Param.TRAN, type);
        activity.startActivity(intent);

    }

    protected CenterAlertBuilder creatDialog() {
        return new CenterAlertBuilder()
                .setDialogTitle("确认要重新上传货单？")
                .setDialogContent("确定后将全部重新选择上传")
                .setBtLeft("返回订单")
                .setBtRight("取消订单")
                .setBtLeftColor(getResources().getColor(R.color.color_title_dark))
                .setBtRightColor(getResources().getColor(R.color.color_white))
                .setBtRightBgRes(R.drawable.shape_e83e15);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        changeIndex(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

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
                    lists.clear();
                    for (LocalMedia localMedia : selectList) {
                        lists.add(localMedia.getPath());
                    }
                    pagerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
