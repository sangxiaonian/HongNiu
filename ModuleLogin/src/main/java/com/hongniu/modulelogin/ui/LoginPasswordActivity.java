package com.hongniu.modulelogin.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hongniu.baselibrary.base.BaseActivity;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.modulelogin.R;
import com.sang.common.utils.ToastUtils;
import com.sang.common.widget.VericationView;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 支付密码修改、设置界面
 *
 * 此界面有多重功能，由参数type决定
 *
 * type 0：尚未设置过支付密码，用来设置密码
 *
 * type 1：修改密码 （流程：输入支付密码确认身份 --> 输入新密码 --》 二次输入确认密码 ）
 * 此界面有 设置密码
 *
 */
public class LoginPasswordActivity extends BaseActivity implements View.OnClickListener, VericationView.OnCompleteListener {

    private TextView tvTitle;
    private TextView tvDes;
    private VericationView vericationView;
    private Button btSum;
    private int type;
    private Statute state;//当前所处状态
    private LinkedList<Statute> task;
    private LinkedList<Statute> stack;


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
        tvTitle=findViewById(R.id.tv_title);
        tvDes=findViewById(R.id.tv_des);
        vericationView=findViewById(R.id.vercationview);
        btSum =findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();


       type= getIntent().getIntExtra(Param.TRAN,0);
//       changeState(type==0?Statute.SETPASSWORD:Statute.ENTRYIDENTITY);
    }

    /**
     *
     * @param statute
     * @param isFinish 是否点击返回按钮
     */
    private void changeState(Statute statute,boolean isFinish) {
        this.state=statute;
        switch (statute){
            case SETPASSWORD://设置密码

                break;
            case ENTRYIDENTITY://确认身份

                break;
            case NEWPASSWORD://输入新密码

                break;
            case NEWPASSWORDENTRY://二次输入密码

                break;
        }

        if (isFinish){
//            Stack
        }else {
            Statute poll = task.poll();
        }

    }

    @Override
    protected void initListener() {
        super.initListener();
        btSum.setOnClickListener(this);
        vericationView.setListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        ToastUtils.getInstance().show("完成按钮");
    }

    @Override
    public void onComplete(String content) {

    }


    private enum Statute{
        SETPASSWORD,    //设置密码
        ENTRYIDENTITY,  //输入支付密码，确认身份
        NEWPASSWORD,    //第一次输入密码
        NEWPASSWORDENTRY//第一次输入密码,确认密码
    }


}
