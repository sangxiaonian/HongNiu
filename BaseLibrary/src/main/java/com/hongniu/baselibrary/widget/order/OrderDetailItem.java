package com.hongniu.baselibrary.widget.order;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.androidlibrary.utils.CommonUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.baselibrary.utils.clickevent.ClickEventParams;
import com.hongniu.baselibrary.utils.clickevent.ClickEventUtils;
import com.hongniu.baselibrary.widget.OrderProgress;
import com.hongniu.baselibrary.widget.order.helper.ButtonInforBean;
import com.hongniu.baselibrary.widget.order.helper.OrderItemHelper;
import com.fy.androidlibrary.utils.DeviceUtils;
import com.sang.common.widget.CenteredImageSpan;
import com.sang.thirdlibrary.chact.ChactHelper;
import com.sang.thirdlibrary.map.utils.MapConverUtils;

import java.util.List;

import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_BUY_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CANCLE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHANGE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHANGE_RECEIPT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_GOODS;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_INSURANCE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_PATH;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_RECEIPT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_CHECK_ROUT;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_AND_PAY_ORDER;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_ARRIVE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_ORDER;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_PAY;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_PAY_REFUSE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_START_CAR;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_TRUCK_GUIDE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_UP_RECEIPT;

/**
 * 作者： ${PING} on 2018/8/7.
 * 订单详情
 */
public class OrderDetailItem extends FrameLayout implements View.OnClickListener {

    private TextView tvIdentity;
    private TextView tv_order;
    private TextView tv_state;
    private TextView tv_time;
    private TextView tv_start_loaction;
    private TextView tv_end_loaction;
    private TextView tv_price;
    //    private TextView tv_instances;
    private TextView tv_order_detail;
    private ViewGroup llBottom;
    private View lineBottom;

    //当前角色 货主、车主、司机
    private OrderDetailItemControl.RoleState roleState;
    //订单状态
    private OrderDetailItemControl.OrderState orderState;
    private OrderDetailItemControl.OnOrderDetailBtClickListener listener;
    private OrderDetailBean orderBean;
    private OrderProgress progress;
    /**
     * true 隐藏保费信息，false显示保费信息 默认为false
     */
    private boolean hideInsurance;
    int spanSize;

    String gap = "正正";

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

        spanSize = DeviceUtils.dip2px(getContext(), 18);
        gap = "正正正";

        View itemView = LayoutInflater.from(context).inflate(R.layout.order_item, this, false);
        tvIdentity = itemView.findViewById(R.id.tv_identity);//身份角色
        tv_order = itemView.findViewById(R.id.tv_order);//订单号
        tv_state = itemView.findViewById(R.id.tv_state);//待发车状态
        tv_time = itemView.findViewById(R.id.tv_time);//发车时间
        tv_start_loaction = itemView.findViewById(R.id.tv_start_loaction);//发车地点
        tv_end_loaction = itemView.findViewById(R.id.tv_end_loaction);//到达地点
        tv_price = itemView.findViewById(R.id.tv_price);//运费
//        tv_instances = itemView.findViewById(R.id.tv_instances);//保费
        tv_order_detail = itemView.findViewById(R.id.tv_order_detail);//右侧按钮
        llBottom = itemView.findViewById(R.id.ll_bottom);//右侧按钮
        lineBottom = itemView.findViewById(R.id.line_bottom);//右侧按钮
        progress = itemView.findViewById(R.id.progress);//右侧按钮

        addView(itemView);
    }


    public void setInfor(OrderDetailBean data) {
        this.orderBean = data;
        roleState = CommonOrderUtils.getRoleState(data.getUserType());
        setIdentity(roleState);
        setEndLocation(data.getDestinationInfo());
        setStartLocation(data.getStartPlaceInfo());
        setOrder(data.getOrderNum());
        if (data.getDeliveryDate() != null) {
            setTiem(ConvertUtils.formatString(data.getDeliveryDate(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
        }
        StringBuilder builder = new StringBuilder();
        String s = TextUtils.isEmpty(data.freightPayWayStr) ? "" : ("(" + data.freightPayWayStr + ")");
        if (data.getMoney()>0){
            builder.append("运费：").append(data.getMoney()).append(s).append("  ");
        }
        if (data.getPaymentAmount() > 0) {
            //代收货款金额大于0
            String policyPayWay = CommonOrderUtils.getPayWay(data.getPaymentPayWay());
            String s1 = TextUtils.isEmpty(policyPayWay) ? "" : ("(" + policyPayWay + ")");
            builder.append("代收货款：").append(data.getPaymentAmount()).append(s1).append("  ");
        }
        if (!hideInsurance && data.isInsurance()) {
            //如果不隐藏保费，并且购买了保险
            String policyPayWay = CommonOrderUtils.getPayWay(data.getPolicyPayWay());
            String s1 = TextUtils.isEmpty(policyPayWay) ? "" : ("(" + policyPayWay + ")");
            builder.append("保费：").append(data.getPolicyMoney()).append(s1);
        }
        setPrice(builder.toString());

        //订单状态进行设置
        if (data.getOrderState().getState()==4&&data.getUserType()==2&&data.getReplaceState()==1){//
//            已到达，如果是司机，并且代收货款
            if (data.freightStatus==1&&data.paymentStatus==1){
                setOrderState(OrderDetailItemControl.OrderState.ARRIVED_PAY);
            }else {
                setOrderState(OrderDetailItemControl.OrderState.ARRIVED_WAITE_PAY);
            }
        }else {
            setOrderState(data.getOrderState());
        }
        if (data.getOrderState() == OrderDetailItemControl.OrderState.IN_TRANSIT) {//正在运输中
            progress.showProgress(true);
            if (data.getLatitude() > 0 && data.getLongitude() > 0) {
                float totale = MapConverUtils.caculeDis(data.getStartLatitude(), data.getStartLongitude(), data.getDestinationLatitude(), data.getDestinationLongitude());
                float current = MapConverUtils.caculeDis(data.getStartLatitude(), data.getStartLongitude(), data.getLatitude(), data.getLongitude());
                progress.setCurent(current * 100 / (totale == 0 ? 1 : totale));
            } else {
                progress.setCurent(0);
            }
            progress.showLog(true);
            //已到达
        } else if (data.getOrderState() == OrderDetailItemControl.OrderState.HAS_ARRIVED) {
            progress.showProgress(true);
            progress.setCurent(100);
            progress.showLog(false);
            //已收货
        } else if (data.getOrderState() == OrderDetailItemControl.OrderState.RECEIPT) {
            progress.showProgress(true);
            progress.setCurent(100);
            progress.showLog(false);

        } else {
            progress.showProgress(false);
        }


        if (roleState != null) {
            switch (roleState) {
                case DRIVER:

                    setContent(data.getDepartNum(), data.getCarNum(), "货主：", data.getUserName(), data.getUserMobile(), data.getUserId()
                            , data.getGoodName(), "车主：", data.getOwnerName(), data.getOwnerMobile(), data.getOwnerId()

                    );
                    break;
                case CAR_OWNER:
                    setContent(data.getDepartNum(), data.getCarNum(), "货主：", data.getUserName(), data.getUserMobile(), data.getUserId()
                            , data.getGoodName(), "司机：", data.getDriverName(), data.getDriverMobile(), data.getDriverId()
                    );
                    break;
                case CARGO_OWNER:
                case CARGO_RECEIVE:
                    setContent(data.getDepartNum(), data.getCarNum(), "车主：", data.getOwnerName(), data.getOwnerMobile(), data.getOwnerId()
                            , data.getGoodName(), "司机：", data.getDriverName(), data.getDriverMobile(), data.getDriverId()
                    );
                    break;
                default:
                    setContent(data.getDepartNum(), data.getCarNum(), "车主：", data.getOwnerName(), data.getOwnerMobile(), data.getOwnerId()
                            , data.getGoodName(), "司机：", data.getDriverName(), data.getDriverMobile(), data.getDriverId()
                    );
                    break;
            }
        } else {
            setContent(data.getDepartNum(), data.getCarNum(), "车主：", data.getOwnerName(), data.getOwnerMobile(), data.getOwnerId()
                    , data.getGoodName(), "司机：", data.getDriverName(), data.getDriverMobile(), data.getDriverId()
            );
        }

        buildButton(data);

        //司机隐藏价格,保险控件
        if (tv_price.getVisibility() != GONE) {
            tv_price.setVisibility(hideInsurance || roleState == OrderDetailItemControl.RoleState.DRIVER ? GONE : VISIBLE);
        }


    }


    private boolean hideButton;


    public void hideButton(boolean hideButton) {
        this.hideButton = hideButton;
        buildButton(null);
    }

    /**
     * 设置当前角色
     *
     * @param roleState 角色 货主、车主、司机
     */
    public void setIdentity(OrderDetailItemControl.RoleState roleState) {
        this.roleState = roleState;
        tvIdentity.setText(CommonOrderUtils.getRoleState(roleState));
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
    public void setOrderState(OrderDetailItemControl.OrderState orderState) {
        this.orderState = orderState;
        tv_state.setText(CommonOrderUtils.getOrderStateDes(orderState));
//        tv_state.setText(orderState.getDes());
    }


    /**
     * 获取中间内容
     *
     * @param startNum      发车时间
     * @param carNum        车牌号
     * @param carOwnerName  车主姓名
     * @param carOwnerPhone 车主电话
     * @param ownerid       聊天Id
     * @param cargo         货物
     * @param driverName    司机姓名
     * @param driverPhone   司机电话
     * @param driverid      聊天ID
     */
    public void setContent(final String startNum, final String carNum, final String roleTop, final String carOwnerName,
                           final String carOwnerPhone, final String ownerid, final String cargo, final String roleBottom,
                           final String driverName, final String driverPhone, final String driverid) {
        tv_order_detail.setMovementMethod(LinkMovementMethod.getInstance());
        tv_order_detail.setText(getContent(startNum, carNum, roleTop, carOwnerName, carOwnerPhone, ownerid, cargo
                , roleBottom, driverName, driverPhone, driverid
        ));

    }

    /**
     * 获取中间内容
     *
     * @param startNum      发车编号
     * @param carNum        车牌号
     * @param roleTop       第一个角色名字
     * @param carOwnerName  车主姓名
     * @param carOwnerPhone 车主电话
     * @param cargo         货物
     * @param roleBottom    下边角色
     * @param driverName    司机姓名
     * @param driverPhone   司机电话
     */
    private SpannableStringBuilder getContent(String startNum, String carNum, String roleTop, final String carOwnerName,
                                              final String carOwnerPhone, final String ownerid, String cargo, String roleBottom,
                                              final String driverName, final String driverPhone, final String driverID) {


        int firstPoint = -1;
        int firstChat = -1;
        int secondPoint = -1;
        int secondChat = -1;

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder
                .append("发车编号：")
                .append(startNum == null ? "" : startNum).append("\n")
                .append("车牌号：")
                .append(carNum == null ? "" : carNum).append("\n");

        builder.append(roleTop)
                .append(carOwnerName == null ? "" : carOwnerName)
                .append(" ")
        ;

        if (!TextUtils.isEmpty(carOwnerPhone)) {//如果司机电话不为空，则拼接司机电话
            builder.append(carOwnerPhone)
            ;
            int start = builder.toString().length();
            builder.append(gap);
            firstPoint = builder.toString().length();
            builder.append(gap)
            ;
            firstChat = builder.toString().length();
            ForegroundColorSpan span = new ForegroundColorSpan(Color.TRANSPARENT);
            builder.setSpan(span, start, firstChat, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        builder.append("\n")
                .append("货物：")
                .append(cargo == null ? "" : cargo)
//                .append(hasInsurance ? ("（已支付" + insuranceMoney + "元保险费）") : "")
                .append("\n")
                .append(roleBottom)
                .append(driverName == null ? "" : driverName).append(" ")
                .append(driverPhone == null ? "" : driverPhone)

        ;

        if (!TextUtils.isEmpty(driverPhone)) {
            int start = builder.toString().length();
            builder.append(gap);
            secondPoint = builder.toString().length();
            builder.append(gap);
            secondChat = builder.toString().length();

            ForegroundColorSpan span = new ForegroundColorSpan(Color.TRANSPARENT);
            builder.setSpan(span, start, secondChat, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        if (firstPoint > 0) {
            creatPhoneSpan(firstPoint - 1, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {

                    switch (roleState) {
                        case DRIVER:

                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_拨打电话);
                            break;
                        case CAR_OWNER:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_拨打电话);

                            break;
                        case CARGO_OWNER:
                        case CARGO_RECEIVE:

                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_拨打电话);

                            break;
                    }
                    CommonUtils.call(getContext(), carOwnerPhone);
                }
            });
            creatChactSpan(firstChat - 1, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ChactHelper.getHelper().startPriver(getContext(), ownerid, carOwnerName);
                    switch (roleState) {
                        case DRIVER:

                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_聊天);
                            break;
                        case CAR_OWNER:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_聊天);

                            break;
                        case CARGO_OWNER:
                        case CARGO_RECEIVE:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_聊天);

                            break;
                    }
                }
            });

        }

        if (secondPoint > 0) {
            creatChactSpan(secondChat - 1, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ChactHelper.getHelper().startPriver(getContext(), driverID, driverName);
                    switch (roleState) {
                        case DRIVER:

                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_聊天);
                            break;
                        case CAR_OWNER:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_聊天);

                            break;
                        case CARGO_OWNER:
                        case CARGO_RECEIVE:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_聊天);

                            break;
                    }
                }
            });
            creatPhoneSpan(secondPoint - 1, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    CommonUtils.call(getContext(), driverPhone);
                    switch (roleState) {
                        case DRIVER:

                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_拨打电话);
                            break;
                        case CAR_OWNER:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_拨打电话);

                            break;
                        case CARGO_OWNER:
                        case CARGO_RECEIVE:
                            ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_拨打电话);

                            break;
                    }
                }
            });


        }

        return builder;

    }

    private void creatPhoneSpan(int startPoint, SpannableStringBuilder builder, ClickableSpan clickableSpan) {
        CenteredImageSpan imageSpan2 = new CenteredImageSpan(getContext(), R.mipmap.icon_call_30);
        imageSpan2.setSpanSize(spanSize, spanSize);
        builder.setSpan(imageSpan2, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(clickableSpan, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    private void creatChactSpan(int startPoint, SpannableStringBuilder builder, ClickableSpan clickableSpan) {
        CenteredImageSpan imageSpan2 = new CenteredImageSpan(getContext(), R.mipmap.icon_maessage_30);
        imageSpan2.setSpanSize(spanSize, spanSize);
        builder.setSpan(imageSpan2, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(clickableSpan, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }


    /**
     * @param data
     */
    public void buildButton(OrderDetailBean data) {
        llBottom.removeAllViews();
        if (hideButton || data == null) {
            llBottom.setVisibility(GONE);
            lineBottom.setVisibility(GONE);
            return;
        }
        final OrderDetailItemControl.IOrderItemHelper helper = new OrderItemHelper(orderState, roleState);


        helper.setInsurance(data.isInsurance())
                .setHasGoodsImage(data.isHasGoodsImage())
                .setHasReceiptImage(data.isHasReceiptImage());

        if (data.getUserType()==3){//发货人
            //        由 货主（发货人） 确认收货按钮 的情况：
//        1、	无收货人；
//        2、	有收货人，但是代收货款为0，且运费支付方式非到付。
            helper.setHasPay(TextUtils.isEmpty(data.getReceiptName())
                    || (data.getPaymentAmount() <= 0 && !TextUtils.isEmpty(data.getPayWay())&&!"2".equals(data.getPayWay())));
            ;
        }else if (data.getUserType()==4){//收货人
            helper.setHasPay((data.freightStatus==1&&data.paymentStatus==1)||(data.freightStatus==3&&data.paymentStatus==3));
        }else if (data.getUserType()==2){//对于司机
            helper.setHasPay((data.freightStatus==1&&data.paymentStatus==1)||(data.freightStatus==3&&data.paymentStatus==3));
        }

        List<ButtonInforBean> infors = helper.getButtonInfors();
        for (ButtonInforBean infor : infors) {
            //是否显示购买保险按钮
            if (!Utils.showInscance() &&
                    (ORDER_BUY_INSURANCE.equals(infor.getText())
                            || ORDER_CHECK_INSURANCE.equals(infor.getText())
                    )
            ) {
                //如果不显示保险信息，此处屏蔽
                continue;
            }

            TextView button = creatButton(infor);
            llBottom.addView(button);
            button.setOnClickListener(this);
        }
        llBottom.setVisibility(llBottom.getChildCount() == 0 ? GONE : VISIBLE);
        lineBottom.setVisibility(llBottom.getChildCount() == 0 ? GONE : VISIBLE);
    }

    public void setOnButtonClickListener(OrderDetailItemControl.OnOrderDetailBtClickListener listener) {
        this.listener = listener;
    }

    private void clickEvent(String msg) {
        switch (msg) {
            case ORDER_CANCLE://    = "取消订单";
                if (listener != null) {
                    listener.onOrderCancle(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("取消订单");
                }

                switch (roleState) {
                    case DRIVER:

                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_取消订单);

                        break;
                }


                break;
            case ORDER_PAY://    = "继续付款";
                if (listener != null) {
                    listener.onOrderPay(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("继续付款");
                }
                switch (roleState) {
                    case DRIVER:

                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_继续付款);

                        break;
                }
                break;
            case ORDER_BUY_INSURANCE://    = "购买保险";
                if (listener != null) {
                    listener.onOrderBuyInsurance(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("购买保险");
                }
                switch (roleState) {
                    case DRIVER:

                        break;
                    case CAR_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_购买保险);

                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_购买保险);

                        break;
                }
                break;
            case ORDER_CHECK_INSURANCE://    = "查看保单";
                if (listener != null) {
                    listener.onCheckInsruance(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看保单");
                }
                switch (roleState) {
                    case DRIVER:
                        break;
                    case CAR_OWNER:

                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_查看保单);
                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_查看保单);

                        break;
                }
                break;
            case ORDER_CHECK_PATH://    = "查看轨迹";
                if (listener != null) {
                    listener.onCheckPath(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看轨迹");
                }

                switch (roleState) {
                    case DRIVER:
                        break;
                    case CAR_OWNER:

                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_查看轨迹);
                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_查看轨迹);

                        break;
                }

                break;
            case ORDER_ENTRY_ORDER://    = "确认收货";
                if (listener != null) {
                    listener.onEntryOrder(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("确认收货");
                }

                switch (roleState) {
                    case DRIVER:
                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_确认收货);

                        break;
                }

                break;
            case ORDER_START_CAR://    = "开始发车";
                if (listener != null) {
                    listener.onStartCar(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("开始发车");
                }

                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_开始发车);

                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_开始发车);

                        break;
                }
                break;
            case ORDER_CHECK_ROUT://    = "查看路线";
                if (listener != null) {
                    listener.onCheckRout(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看路线");
                }
                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_查看路线);

                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:

                        break;
                }
                break;
            case ORDER_ENTRY_ARRIVE://    = "确认到达";
                if (listener != null) {
                    listener.onEntryArrive(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("确认到达");
                }
                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_确认到达);

                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:
                        break;
                }
                break;
            case ORDER_CHECK_RECEIPT://    = "查看回单";
                if (listener != null) {
                    listener.onCheckReceipt(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看回单");
                }
                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_查看回单);

                        break;
                    case CAR_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_查看回单);

                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_查看回单);
                        break;
                }
                break;
            case ORDER_UP_RECEIPT://    = "上传回单";
                if (listener != null) {
                    listener.onUpReceipt(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("上传回单");
                }
                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_上传回单);

                        break;
                    case CAR_OWNER:
                        break;
                    case CARGO_OWNER:
                        break;
                }
                break;
            case ORDER_CHANGE_RECEIPT://    = "修改回单";
                if (listener != null) {
                    listener.onChangeReceipt(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("修改回单");
                }
                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_修改回单);

                        break;
                    case CAR_OWNER:
                        break;
                    case CARGO_OWNER:
                        break;
                }
                break;
            case ORDER_CHANGE://修改订单
                if (listener != null) {
                    listener.onChangeOrder(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("修改订单");
                }
                switch (roleState) {
                    case DRIVER:

                        break;
                    case CAR_OWNER:
                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_修改订单);

                        break;
                }
                break;
            case ORDER_CHECK_GOODS://查看货单
                if (listener != null) {
                    listener.onCheckGoods(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看货单");
                }
                switch (roleState) {
                    case DRIVER:

                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_查看货单);
                        break;
                    case CAR_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是车主_查看货单);
                        break;
                    case CARGO_OWNER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是货主_查看货单);

                        break;
                }
                break;


            case ORDER_TRUCK_GUIDE://    = "货车导航";
                if (listener != null) {
                    listener.onTruchGuid(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("货车导航");
                }
                switch (roleState) {
                    case DRIVER:
                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_货车导航);

                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:

                        break;
                }
                break;
            case ORDER_PAY_REFUSE://    = "被拒原因";

                if (listener != null) {
                    listener.onPayRefuse(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("被拒原因");
                }
                switch (roleState) {
                    case DRIVER:
//                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_货车导航);
                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:

                        break;
                }
                break;
            case ORDER_ENTRY_AND_PAY_ORDER://确认收货,并支付订单;
                if (listener != null) {
                    listener.onEntryAndPay(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show(ORDER_ENTRY_AND_PAY_ORDER);
                }
                switch (roleState) {
                    case DRIVER:
//                        ClickEventUtils.getInstance().onClick(ClickEventParams.我是司机_货车导航);
                        break;
                    case CAR_OWNER:

                        break;
                    case CARGO_OWNER:

                        break;
                }
                break;
        }
    }


    /**
     * 隐藏最底部数据
     *
     * @param b
     */
    public void hideBottom(boolean b) {

        tv_price.setVisibility(b ? GONE : VISIBLE);
        hideButton(b);

    }

    /**
     * 设置是否隐藏保费信息
     *
     * @param hideInsurance true 隐藏保费信息，false显示保费信息
     */
    public void hideInsurance(boolean hideInsurance) {
        this.hideInsurance = hideInsurance;
    }


    public TextView creatButton(ButtonInforBean infor) {
        TextView button = new TextView(getContext());
        MarginLayoutParams params = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        button.setLayoutParams(params);
        params.setMargins(DeviceUtils.dip2px(getContext(), 10), 0, 0, 0);
        button.setTextColor(infor.getType() == 1 ? getResources().getColor(R.color.white) : getResources().getColor(R.color.color_title_dark));
        button.setBackgroundResource(infor.getType() == 1 ? R.drawable.shape_2_e83e15 : R.drawable.shape_2_stoke_dddddd);
        button.setGravity(Gravity.CENTER);
        button.setTextSize(13);
        button.setText(infor.getText() == null ? "" : infor.getText());
        return button;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {
            clickEvent(((TextView) v).getText().toString().trim());
        }
    }
}
