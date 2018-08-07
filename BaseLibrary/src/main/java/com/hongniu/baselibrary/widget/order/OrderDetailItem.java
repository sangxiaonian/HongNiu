package com.hongniu.baselibrary.widget.order;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.JLog;
import com.sang.common.widget.CenteredImageSpan;

/**
 * 作者： ${PING} on 2018/8/7.
 * 订单详情
 */
public class OrderDetailItem extends FrameLayout {

    private TextView tvIdentity;
    private TextView tv_order;
    private TextView tv_state;
    private TextView tv_time;
    private TextView tv_start_loaction;
    private TextView tv_end_loaction;
    private TextView tv_price;
    private TextView bt_left;
    private TextView bt_right;
    private TextView tv_order_detail;

    //当前角色 货主、车主、司机
    private RoleState roleState;
    //订单状态
    private OrderState orderState;


    public OrderDetailItem(@NonNull Context context) {
        this(context, null, 0);
    }

    public OrderDetailItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderDetailItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item, this, false);
        tvIdentity = itemView.findViewById(R.id.tv_identity);//身份角色
        tv_order = itemView.findViewById(R.id.tv_order);//订单号
        tv_state = itemView.findViewById(R.id.tv_state);//待发车状态
        tv_time = itemView.findViewById(R.id.tv_time);//发车时间
        tv_start_loaction = itemView.findViewById(R.id.tv_start_loaction);//发车地点
        tv_end_loaction = itemView.findViewById(R.id.tv_end_loaction);//到达地点
        tv_price = itemView.findViewById(R.id.tv_price);//运费
        bt_left = itemView.findViewById(R.id.bt_left);//左侧按钮
        bt_right = itemView.findViewById(R.id.bt_right);//右侧按钮
        tv_order_detail = itemView.findViewById(R.id.tv_order_detail);//右侧按钮
        addView(itemView);
    }


    public void setDebug() {

        tvIdentity.setText("货主");
        tv_order.setText("80080018000");
        tv_state.setText("运输中");
        tv_time.setText("发车时间：2017-08-05");
        tv_start_loaction.setText("上海虹桥机场国际物流中心");
        tv_end_loaction.setText("青岛市国际物流中心");
        tv_price.setText("1520");


        tv_order_detail.setText(getContent("2831929482", "沪A666888",
                "李先生", "17602150486",
                "医疗器材", "杨先生", "13795244936"));
        tv_order_detail.setMovementMethod(LinkMovementMethod.getInstance());
    }


    /**
     * 设置当前角色
     *
     * @param roleState 角色 货主、车主、司机
     */
    public void setIdentity(RoleState roleState) {
        this.roleState = roleState;
        tvIdentity.setText(OrderUtils.getRoleState(roleState));
        JLog.i(OrderUtils.getRoleState(roleState));
    }

    /**
     * 设置订单号
     *
     * @param order
     */
    public void setOrder(String order) {
        tv_order.setText(order == null ? "" : order);
    }

    /**
     * 设置发车时间
     *
     * @param time
     */
    public void setTiem(String time) {
        tv_time.setText("发车时间：" + (time == null ? "" : time));
    }

    /**
     * 设置发车地点
     *
     * @param startLocation
     */
    public void setStartLocation(String startLocation) {
        tv_start_loaction.setText((startLocation == null ? "" : startLocation));
    }

    /**
     * 设置发车地点
     *
     * @param endLoaction
     */
    public void setEndLocation(String endLoaction) {
        tv_end_loaction.setText((endLoaction == null ? "" : endLoaction));
    }

    /**
     * 设置价格
     *
     * @param price
     */
    public void setPrice(String price) {
        tv_price.setText((price == null ? "" : price));
    }

    /**
     * 当前订单状态
     * WAITE_PAY,//待支付
     * WAITE_START_NO_INSURANCE,//待发车(未购买保险)
     * WAITE_START,//待发车(已买保险)
     * IN_TRANSIT,//运输中
     * HAS_ARRIVED,//已到达
     * RECEIPT,//已收货
     *
     * @param orderState
     */
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }


    /**
     * 获取中间内容
     *
     * @param startNum      发车变化
     * @param carNum        车牌号
     * @param carOwnerName  车主姓名
     * @param carOwnerPhone 车主电话
     * @param cargo         货物
     * @param driverName    司机姓名
     * @param driverPhone   司机电话
     */
    private SpannableStringBuilder getContent(String startNum, String carNum, String carOwnerName,
                                              final String carOwnerPhone, String cargo,
                                              String driverName, final String driverPhone) {


        int firstPoint = -1;
        int secondPoint = -1;

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder
                .append("发车编号：")
                .append(startNum == null ? "" : startNum).append("\n")
                .append("车牌号：")
                .append(carNum == null ? "" : carNum).append("\n")
                .append("车主：")
                .append(carOwnerName == null ? "" : carOwnerName)
                .append(" ")
        ;


        if (!TextUtils.isEmpty(carOwnerPhone)) {//如果司机电话不为空，则拼接司机电话
            builder.append(carOwnerPhone).append(" ").append(" ");
            firstPoint = builder.toString().length();
        }

        builder.append("\n")
                .append("货物：")
                .append(cargo == null ? "" : cargo).append("\n")
                .append("司机：")
                .append(driverName == null ? "" : driverName).append(" ")
                .append(driverPhone == null ? "" : driverPhone)
                .append(" ")
                .append(" ")
        ;

        if (!TextUtils.isEmpty(driverPhone)) {
            secondPoint = builder.toString().length();
        }

        if (firstPoint > 0) {
            ImageSpan imageSpan = new CenteredImageSpan(getContext(), R.mipmap.icon_call_30);
            builder.setSpan(imageSpan, firstPoint - 1, firstPoint, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            ClickableSpan carOwnerClick = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    CommonUtils.toDial(getContext(), carOwnerPhone);
                }
            };
            builder.setSpan(carOwnerClick, firstPoint - 1, firstPoint, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        }
        if (secondPoint > 0) {
            ImageSpan imageSpan2 = new CenteredImageSpan(getContext(), R.mipmap.icon_call_30);
            builder.setSpan(imageSpan2, secondPoint - 1, secondPoint, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            ClickableSpan driverClick = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    CommonUtils.toDial(getContext(), driverPhone);
                }
            };
            builder.setSpan(driverClick, secondPoint - 1, secondPoint, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        }

        return builder;

    }


}
