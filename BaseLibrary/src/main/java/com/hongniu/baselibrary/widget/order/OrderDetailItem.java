package com.hongniu.baselibrary.widget.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hongniu.baselibrary.R;
import com.hongniu.baselibrary.entity.OrderDetailBean;
import com.hongniu.baselibrary.widget.OrderProgress;
import com.hongniu.baselibrary.widget.order.helper.ButtonInforBean;
import com.hongniu.baselibrary.widget.order.helper.OrderItemHelper;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.DeviceUtils;
import com.sang.common.utils.ToastUtils;
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
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_ARRIVE;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_ENTRY_ORDER;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_PAY;
import static com.hongniu.baselibrary.widget.order.CommonOrderUtils.ORDER_START_CAR;
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
    private TextView tv_instances;
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
        tv_instances = itemView.findViewById(R.id.tv_instances);//保费
        tv_order_detail = itemView.findViewById(R.id.tv_order_detail);//右侧按钮
        llBottom = itemView.findViewById(R.id.ll_bottom);//右侧按钮
        lineBottom = itemView.findViewById(R.id.line_bottom);//右侧按钮
        progress = itemView.findViewById(R.id.progress);//右侧按钮

        addView(itemView);
    }


    public void setInfor(OrderDetailBean data) {
        this.orderBean = data;

        if (roleState == null) {
            roleState = CommonOrderUtils.getRoleState(data.getRoleType());
        }
        setIdentity(roleState);
        setEndLocation(data.getDestinationInfo());
        setStartLocation(data.getStartPlaceInfo());
        setOrder(data.getOrderNum());
        if (data.getDeliveryDate() != null) {
            setTiem(ConvertUtils.formatString(data.getDeliveryDate(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"));
        }

        //对于司机，隐藏保费
        hideInsurance(roleState == OrderDetailItemControl.RoleState.DRIVER);

        String money = data.getMoney();
        if (!TextUtils.isEmpty(money)) {
            String s = TextUtils.isEmpty(CommonOrderUtils.getPayWay(data.getPayWay())) ? "" : ("(" + CommonOrderUtils.getPayWay(data.getPayWay()) + ")");
            setPrice("运费：￥" + money + s);
        } else {
            setPrice("");
        }

        if (hideInsurance || !data.isInsurance()) {
            tv_instances.setVisibility(GONE);
        } else {
            tv_instances.setVisibility(VISIBLE);

        }

        String s = TextUtils.isEmpty(CommonOrderUtils.getPayWay(data.getPolicyPayWay())) ? "" : ("(" + CommonOrderUtils.getPayWay(data.getPolicyPayWay()) + ")");
        String plicy = "保费：" + data.getPolicyMoney() + "元" + s;
        setInsruancePrice((data.isInsurance() && data.getPolicyMoney() != null) ? plicy : "");
        setOrderState(data.getOrderState());

        if (data.getOrderState() == OrderDetailItemControl.OrderState.IN_TRANSIT) {//正在运输中
            progress.showProgress(true);
            if (data.getLatitude() > 0 && data.getLongitude() > 0) {
                float totale = MapConverUtils.caculeDis(data.getStartLatitude(), data.getStartLongitude(), data.getDestinationLatitude(), data.getDestinationLongitude());
                float current = MapConverUtils.caculeDis(data.getStartLatitude(), data.getStartLongitude(), data.getLatitude(), data.getLongitude());
                progress.setCurent(current * 100 / (totale == 0 ? 1 : totale));
            } else {
                progress.setCurent(0);
            }
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

        buildButton(data.isInsurance(), data.isHasGoodsImage(), data.isHasReceiptImage());

        //司机隐藏价格,保险控件
        if (tv_price.getVisibility() != GONE) {
            tv_price.setVisibility(roleState == OrderDetailItemControl.RoleState.DRIVER ? GONE : VISIBLE);
            tv_instances.setVisibility(roleState == OrderDetailItemControl.RoleState.DRIVER ? GONE : VISIBLE);
        }


    }

    /**
     * 设置保费
     */
    private void setInsruancePrice(String insruancePrice) {
        tv_instances.setText(insruancePrice == null ? "" : insruancePrice);

    }


    private boolean hideButton;


    public void hideButton(boolean hideButton) {
        this.hideButton = hideButton;
        buildButton(false, false, false);
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
    public void setContent(String startNum, String carNum, String roleTop, String carOwnerName,
                           final String carOwnerPhone, String ownerid, String cargo, String roleBottom,
                           String driverName, final String driverPhone, String driverid) {
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
        int secondPoint = -1;

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
                    .append(" ").append(" ").append(" ")
                    .append(" ").append(" ");
            firstPoint = builder.toString().length();
        }

        builder.append("\n")
                .append("货物：")
                .append(cargo == null ? "" : cargo)
//                .append(hasInsurance ? ("（已支付" + insuranceMoney + "元保险费）") : "")
                .append("\n")
                .append(roleBottom)
                .append(driverName == null ? "" : driverName).append(" ")
                .append(driverPhone == null ? "" : driverPhone)
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
                .append(" ")
        ;

        if (!TextUtils.isEmpty(driverPhone)) {
            secondPoint = builder.toString().length();
        }

        if (firstPoint > 0) {
            creatPhoneSpan(firstPoint - 3, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    CommonUtils.toDial(getContext(), carOwnerPhone);
                }
            });
            creatChactSpan(firstPoint - 1, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ChactHelper.getHelper().startPriver(getContext(), ownerid, carOwnerName);

                }
            });

        }

        if (secondPoint > 0) {

            creatPhoneSpan(secondPoint - 3, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    CommonUtils.toDial(getContext(), driverPhone);
                }
            });
            creatChactSpan(secondPoint - 1, builder, new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    ChactHelper.getHelper().startPriver(getContext(), driverID, driverName);
                }
            });

        }

        return builder;

    }

    private void creatPhoneSpan(int startPoint, SpannableStringBuilder builder, ClickableSpan clickableSpan) {
        final int size = DeviceUtils.dip2px(getContext(), 15);
        CenteredImageSpan imageSpan2 = new CenteredImageSpan(getContext(), R.mipmap.icon_call_30);
        imageSpan2.setSpanSize(size, size);
        builder.setSpan(imageSpan2, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(clickableSpan, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }

    private void creatChactSpan(int startPoint, SpannableStringBuilder builder, ClickableSpan clickableSpan) {
        final int size = DeviceUtils.dip2px(getContext(), 15);
        CenteredImageSpan imageSpan2 = new CenteredImageSpan(getContext(), R.mipmap.icon_maessage_30);
        imageSpan2.setSpanSize(size, size);
        builder.setSpan(imageSpan2, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.setSpan(clickableSpan, startPoint, startPoint + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    }


    /**
     * @param b               是否购买保险
     * @param hasGoodsImage   是否有回单，是否有货单
     * @param hasReceiptImage
     */
    public void buildButton(boolean b, boolean hasGoodsImage, boolean hasReceiptImage) {
        llBottom.removeAllViews();
        if (hideButton) {
            llBottom.setVisibility(GONE);
            lineBottom.setVisibility(GONE);
            return;
        }
        final OrderDetailItemControl.IOrderItemHelper helper = new OrderItemHelper(orderState, roleState);
        helper.setInsurance(b)
                .setHasGoodsImage(hasGoodsImage)
                .setHasReceiptImage(hasReceiptImage)
        ;
        List<ButtonInforBean> infors = helper.getButtonInfors();
        for (ButtonInforBean infor : infors) {
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
                break;
            case ORDER_PAY://    = "继续付款";
                if (listener != null) {
                    listener.onOrderPay(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("继续付款");
                }
                break;
            case ORDER_BUY_INSURANCE://    = "购买保险";
                if (listener != null) {
                    listener.onOrderBuyInsurance(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("购买保险");
                }
                break;
            case ORDER_CHECK_INSURANCE://    = "查看保单";
                if (listener != null) {
                    listener.onCheckInsruance(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看保单");
                }
                break;
            case ORDER_CHECK_PATH://    = "查看轨迹";
                if (listener != null) {
                    listener.onCheckPath(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看轨迹");
                }
                break;
            case ORDER_ENTRY_ORDER://    = "确认收货";
                if (listener != null) {
                    listener.onEntryOrder(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("确认收货");
                }
                break;
            case ORDER_START_CAR://    = "开始发车";
                if (listener != null) {
                    listener.onStartCar(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("开始发车");
                }
                break;
            case ORDER_CHECK_ROUT://    = "查看路线";
                if (listener != null) {
                    listener.onCheckRout(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看路线");
                }
                break;
            case ORDER_ENTRY_ARRIVE://    = "确认到达";
                if (listener != null) {
                    listener.onEntryArrive(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("确认到达");
                }
                break;
            case ORDER_CHECK_RECEIPT://    = "查看回单";
                if (listener != null) {
                    listener.onCheckReceipt(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看回单");
                }
                break;
            case ORDER_UP_RECEIPT://    = "上传回单";
                if (listener != null) {
                    listener.onUpReceipt(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("上传回单");
                }
                break;
            case ORDER_CHANGE_RECEIPT://    = "修改回单";
                if (listener != null) {
                    listener.onChangeReceipt(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("修改回单");
                }
                break;
            case ORDER_CHANGE://修改订单
                if (listener != null) {
                    listener.onChangeOrder(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("修改订单");
                }
                break;
            case ORDER_CHECK_GOODS://查看货单
                if (listener != null) {
                    listener.onCheckGoods(orderBean);
                } else {
                    ToastUtils.getInstance().makeToast(ToastUtils.ToastType.NORMAL).show("查看货单");
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
        tv_instances.setVisibility(b ? GONE : VISIBLE);
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
        TextView button = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.order_item_text, llBottom, false);
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
