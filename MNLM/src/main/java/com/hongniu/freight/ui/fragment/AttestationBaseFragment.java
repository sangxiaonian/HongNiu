package com.hongniu.freight.ui.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.net.NetObserver;
import com.fy.companylibrary.ui.CompanyBaseFragment;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.entity.VerifyIdNumIdentityBean;
import com.hongniu.freight.entity.VerifyInfoBean;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.freight.widget.ImageInforView;

/**
 * 作者：  on 2020/3/4.
 */
public abstract class AttestationBaseFragment extends CompanyBaseFragment {

    protected boolean canNext = true;
    protected TextView bt_sum;
    protected View root;

    @Override
    protected void initData() {
        super.initData();
        if (getBundle() != null) {
            canNext = getBundle().getBoolean(Param.TRAN);
        }
        if (bt_sum != null) {
            bt_sum.setVisibility(canNext ? View.VISIBLE : View.GONE);
        }

        if (!canNext) {
            query();
            if (root instanceof ViewGroup) {
                switchEnable((ViewGroup) root);
            }
        }
    }

    protected void query() {

        HttpAppFactory.queryVerifyCarrierPerson()
                .subscribe(new NetObserver<VerifyInfoBean>(this) {
                    @Override
                    public void doOnSuccess(VerifyInfoBean verifyInfoBean) {
                        super.doOnSuccess(verifyInfoBean);
                        initInfo(verifyInfoBean);

                    }
                });
    }

    //初始化数据
    protected abstract void initInfo(VerifyInfoBean verifyInfoBean);

    private void switchEnable(ViewGroup parent) {
        if (parent instanceof ItemTextView) {
            ((ItemTextView) parent).setEnabled(canNext);
        }else if (parent instanceof ImageInforView) {
            parent.setEnabled(canNext);
        } else {
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                if (child instanceof ItemTextView){
                    ((ItemTextView) child).setEnabled(canNext);
                }else if (child instanceof ImageView){
                    ( child).setEnabled(canNext);
                }else if (child instanceof ImageInforView) {
                    child.setEnabled(canNext);
                }else if (child instanceof ViewGroup){
                    switchEnable((ViewGroup) child);
                }
            }
        }
    }

    protected String getAlert(int state, String type) {
        String msg = type + "上传异常，请重试";
        if (state == 0) {
            msg = "请上传" + type;
        }
        if (state == 1) {
            msg = type + "上传中,请稍后";
        } else if (state == 2) {
            msg = type + "上传失败,请重试";

        }
        return msg;
    }
}
