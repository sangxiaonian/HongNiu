package com.sang.common.widget.orderitem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sang.common.R;
import com.sang.common.widget.orderitem.helper.IOrderItemHelper;
import com.sang.common.widget.orderitem.helper.OrderItemHelper;

/**
 * 作者： ${PING} on 2018/8/2.
 */
public class OrderItemView extends FrameLayout {
    TextView tvIdentity; //身份角色
    TextView tv_order; //订单号
    TextView tv_state; //待发车状态
    TextView tv_time; //发车时间
    TextView tv_start_loaction; //发车地点
    TextView tv_end_loaction; //到达地点
    TextView tv_price; //运费
    TextView bt_left; //左侧按钮
    TextView bt_right; //右侧按钮
    TextView tv_order_detail;//订单详情


    public OrderItemView(@NonNull Context context) {
        this(context, null, 0);
    }

    public OrderItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);

    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item, this, false);
        tvIdentity = itemView.findViewById(R.id.tv_identity);          //身份角色
        tv_order = itemView.findViewById(R.id.tv_order);             //订单号
        tv_state = itemView.findViewById(R.id.tv_state);             //待发车状态
        tv_time = itemView.findViewById(R.id.tv_time);              //发车时间
        tv_start_loaction = itemView.findViewById(R.id.tv_start_loaction);     //发车地点
        tv_end_loaction = itemView.findViewById(R.id.tv_end_loaction); //到达地点
        tv_price = itemView.findViewById(R.id.tv_price);             //运费
        bt_left = itemView.findViewById(R.id.bt_left);              //左侧按钮
        bt_right = itemView.findViewById(R.id.bt_right);             //右侧按钮
        tv_order_detail = itemView.findViewById(R.id.tv_order_detail);//右侧按钮
        addView(itemView);
    }


    IOrderItemHelper helper;
    public void setData(OrderDetailBean bean) {


        tvIdentity.setText("货主");
        tv_order.setText("80080018000");
        tv_state.setText("运输中");
        tv_time.setText("发车时间：2017-08-05");
        tv_start_loaction.setText("上海虹桥机场国际物流中心");
        tv_end_loaction.setText("青岛市国际物流中心");
        tv_price.setText("1520");

        if (bean == null) {
            return;
        }

        helper=new OrderItemHelper(bean.state,bean.roleState);

        setText(tvIdentity, OrderUtils.getRoleState(bean.roleState));
        setText(tv_order, bean.orderNum);
        setText(tv_time, bean.startTime);
        setText(tv_start_loaction, bean.startLoaction);
        setText(tv_end_loaction, bean.endtLoaction);
        setText(tv_price, bean.price);
        setText(tv_state, getState(bean.state));

        bt_left.setText(TextUtils.isEmpty(helper.getBtLeftInfor())?"":helper.getBtLeftInfor());
        bt_right.setText(TextUtils.isEmpty(helper.getBtRightInfor())?"":helper.getBtRightInfor());

        bt_left.setVisibility(helper.getLeftVisibility());
        bt_right.setVisibility(helper.getRightVisibility());


    }


    private void setText(TextView view, String date) {
        if (view != null && date != null) {
            view.setText(date);
        }
    }


    private String getState(OrderState state) {
        String stateMsg;
        switch (state) {

            case WAITE_PAY://待支付
                stateMsg = "待支付";
                break;
            case WAITE_START_NO_INSURANCE://待发车(未购买保险)
                stateMsg = "待发车";
                break;
            case WAITE_START://待发车(已买保险)
                stateMsg = "待发车";
                break;
            case IN_TRANSIT://运输中
                stateMsg = "运输中";
                break;
            case HAS_ARRIVED://已到达
                stateMsg = "已到达";
                break;
            case RECEIPT://已收货
                stateMsg = "已收货";
                break;
            default:
                stateMsg = "无效状态";
                break;
        }
        return stateMsg;
    }


    public static class OrderDetailBean {
        public OrderState state;//当前状态
        public String role;//角色
        public RoleState roleState;//角色
        public String orderNum;//订单号
        public String startTime;//发车时间
        public String startLoaction;//发车地点
        public String endtLoaction;//到达地点
        public String price;//运费
        public String detail;//详情
        public int progress;

    }

    //当前所处的支付状态
    public enum OrderState {
        WAITE_PAY,//待支付
        WAITE_START_NO_INSURANCE,//待发车(未购买保险)
        WAITE_START,//待发车(已买保险)
        IN_TRANSIT,//运输中
        HAS_ARRIVED,//已到达
        RECEIPT,//已收货

    }

    //当前角色
    public enum RoleState {
        CAR_OWNER,//车主
        CARGO_OWNER,//货主
        DRIVER;//司机

    }


}
