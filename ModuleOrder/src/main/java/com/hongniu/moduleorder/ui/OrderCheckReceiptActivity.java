package com.hongniu.moduleorder.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.moduleorder.R;
import com.luck.picture.lib.widget.PreviewViewPager;
import com.sang.common.utils.CommonUtils;
import com.sang.common.widget.dialog.builder.CenterAlertBuilder;
import com.sang.common.widget.dialog.inter.DialogControl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查看回单、大图
 * <p>
 * 查看回单，查看货单
 */
public class OrderCheckReceiptActivity extends BaseActivity {

    private final static String previewList = "previewSelectList";
    private final static String index = "position";

    private PreviewViewPager viewPager;
    private TextView tvIndex;

    private List<String> lists = new ArrayList<>();
    private int currentPosition;
    private int type;

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
        if (!CommonUtils.isEmptyCollection(extra)) {
            lists.addAll(extra);
        }
        changeIndex(currentPosition);

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
                    if (currentPosition>=0&&currentPosition<lists.size()){
                        lists.remove(currentPosition);
                        currentPosition=currentPosition>=lists.size()?lists.size()-1:currentPosition;
                    }
                }
            });
        } else if (type == 2) {
            setToolbarSrcRight("重新上传");
            setToolbarDarkTitle("查看货单");
            setToolbarRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    creatDialog().setLeftClickListener(new DialogControl.OnButtonLeftClickListener() {
                        @Override
                        public void onLeftClick(View view, DialogControl.ICenterDialog dialog) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                }
            });
        }


    }

    private void changeIndex(int index) {
        tvIndex.setText(index + "/" + lists.size());
    }

    /**
     * 进入货单等的查看页面
     *
     * @param activity
     * @param type     0 查看回单 1预览回单 2 上传货单
     * @param position
     * @param medias
     */
    public static void startActivity(Activity activity, int type, int position, List<String> medias) {
        Intent intent = new Intent(activity, OrderCheckReceiptActivity.class);
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
                .setBtRightBgRes(R.drawable.shape_f06f28);
    }
}
