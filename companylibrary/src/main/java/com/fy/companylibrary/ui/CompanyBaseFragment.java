package com.fy.companylibrary.ui;


import com.fy.androidlibrary.toast.ToastUtils;
import com.hongniu.baselibrary.ui.ModuleBaseFragment;

/**
 * 作者：  on 2019/10/29.
 * 所有相关业务的基类
 * 如需要公司业务相关逻辑代码，在此处实现
 */
public class CompanyBaseFragment extends ModuleBaseFragment {


    @Override
    protected void showErrorAlert(int code, String msg) {
        super.showErrorAlert(code, msg);
        ToastUtils.getInstance().show(msg);
    }


}
