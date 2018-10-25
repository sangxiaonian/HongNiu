package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hongniu.baselibrary.arouter.ArouterParamLogin;
import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.entity.QueryPayPassword;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulelogin.R;
import com.hongniu.modulelogin.net.HttpLoginFactory;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.VericationView;

import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import java.util.UUID;

/**
 * 支付密码修改、设置界面
 * <p>
 * 此界面有多重功能，由参数type决定
 * <p>
 * type 0：尚未设置过支付密码，用来设置密码
 * <p>
 * type 1：修改密码 （流程：输入支付密码确认身份 --> 输入新密码 --》 二次输入确认密码 ）
 * 此界面有 设置密码
 */
@Route(path = ArouterParamLogin.activity_login_password)
public class LoginPasswordActivity extends BaseActivity implements View.OnClickListener, VericationView.OnCompleteListener, VericationView.OnContentChangeListener {

    private TextView tvTitle;
    private TextView tvDes;
    private VericationView vericationView;
    private Button btSum;
    private int type;
    private Statute state;//当前所处状态
    private Stack<Statute> stack;//返回栈
    private LinkedList<Statute> task;//要进行的任务




    private String passDebug = "888888";

    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_password);
        setToolbarTitle("");
        initView();
        initData();
        initListener();
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle = findViewById(R.id.tv_title);
        tvDes = findViewById(R.id.tv_des);
        vericationView = findViewById(R.id.vercationview);
        btSum = findViewById(R.id.bt_sum);

    }





    @Override
    protected void initData() {
        super.initData();
        vericationView.setType(1);
        task = new LinkedList<>();
        type = getIntent().getIntExtra(Param.TRAN, 0);
        if (type == 0) {
            task.add(Statute.SETPASSWORD);
        } else {
            task.add(Statute.ENTRYIDENTITY);
            task.add(Statute.NEWPASSWORD);
            task.add(Statute.NEWPASSWORDENTRY);
        }
        changeState(task.poll());
    }

    /**
     * @param statute
     */
    private void changeState(Statute statute) {
        if (statute == null) {
            return;
        }
        vericationView.clear();
        this.state = statute;
        boolean showBt = false;
        String title = "";
        String des = "";
        switch (statute) {
            case SETPASSWORD://设置密码
                showBt = true;
                title = "设置支付密码";
                des = "请设置泓牛支付密码，用于支付及提现验证";
                break;
            case ENTRYIDENTITY://确认身份
                showBt = false;
                title = "修改密码";
                des = "请输入支付密码，以验证身份";
                break;
            case NEWPASSWORD://输入新密码
                title = "设置新支付密码";
                des = "请设置泓牛支付密码，用于支付及提现验证";
                showBt = false;
                break;
            case NEWPASSWORDENTRY://二次输入密码
                showBt = true;
                title = "设置新支付密码";
                des = "请再次输入以确认";
                break;
        }

        btSum.setVisibility(showBt ? View.VISIBLE : View.GONE);
        tvTitle.setText(title);
        tvDes.setText(des);
        btSum.setEnabled(false);

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        vericationView.setListener(this);
        vericationView.setOnChangeListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (state) {
            //设置和确认密码时候，进行操作
            case SETPASSWORD:
                if (type==0){//设置密码
                    HttpLoginFactory.setPayPassword(ConvertUtils.MD5(vericationView.getContent()))
                    .subscribe(new NetObserver<QueryPayPassword>(this) {
                        @Override
                        public void doOnSuccess(QueryPayPassword data) {
                            Utils.setPassword(data.isSetPassWord());
                            ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show("密码设置成功");
                            finish();
                        }
                    });
                }else {//修改密码

                }

                break;
            case NEWPASSWORDENTRY:
                if (password != null && password.equals(vericationView.getContent())) {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
                    finish();
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show("两次密码不一致");

                }
                break;
            //修改密码和确认身份验证密码时候，此时按钮为隐藏状态，不会显示
            case ENTRYIDENTITY:
            case NEWPASSWORD:
                break;
        }
    }

    private void next() {
        Statute peek = task.poll();
        //将上一个状态，被隐藏的状态进行保存
        if (stack==null){//第一个不加入
            stack=new Stack<>();
        }else if (state!=null){
            stack.add(state);
        }
        changeState(peek);
    }

    private void goBack() {
        if (CommonUtils.isEmptyCollection(stack)) {
            super.onBackPressed();
        } else {
            Statute peek = stack.pop();
            if (state!=null) {
                task.addFirst(state);
            }
            changeState(peek);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public void onComplete(String content) {
        switch (state) {
            //设置和确认密码时候，仅仅保存此时的密码，等点击确认，然后执行下一步操作
            case SETPASSWORD:
            case NEWPASSWORDENTRY:
                break;
            //修改密码和确认身份验证密码时候，直接走下一步操作
            case ENTRYIDENTITY:
                if (content.equals(passDebug)) {
                    next();
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.CENTER).show(getString(R.string.login_password_error));
                }
                break;
            case NEWPASSWORD:
                password = content;
                next();
                break;
        }
    }

    @Override
    public void onContentChange(int mEditCount, String content) {
        btSum.setEnabled(content.length()==mEditCount);

    }


    private enum Statute {
        SETPASSWORD,    //设置密码
        ENTRYIDENTITY,  //输入支付密码，确认身份
        NEWPASSWORD,    //第一次输入密码
        NEWPASSWORDENTRY//第一次输入密码,确认密码
    }


}
