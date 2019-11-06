package com.hongniu.modulecargoodsmatch.ui.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterParamsMatch;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.base.BaseFragment;
import com.hongniu.baselibrary.base.NetObserver;
import com.hongniu.baselibrary.config.Param;
import com.hongniu.baselibrary.utils.PermissionUtils;
import com.hongniu.baselibrary.utils.PickerDialogUtils;
import com.hongniu.baselibrary.utils.Utils;
import com.hongniu.modulecargoodsmatch.R;
import com.hongniu.modulecargoodsmatch.entity.MatchCarTypeInfoBean;
import com.hongniu.modulecargoodsmatch.entity.MatchCountFareBean;
import com.hongniu.modulecargoodsmatch.entity.MatchOrderTranMapBean;
import com.hongniu.modulecargoodsmatch.entity.MatchQueryCountFareParam;
import com.hongniu.modulecargoodsmatch.entity.TranMapBean;
import com.hongniu.modulecargoodsmatch.net.HttpMatchFactory;
import com.hongniu.modulecargoodsmatch.ui.activiry.MatchCreatOrderActivity;
import com.hongniu.modulecargoodsmatch.ui.adapter.CarPageAdapter;
import com.hongniu.modulecargoodsmatch.ui.activiry.MatchMapActivity;
import com.sang.common.net.rx.BaseObserver;
import com.sang.common.net.rx.RxUtils;
import com.sang.common.utils.CommonUtils;
import com.sang.common.utils.ConvertUtils;
import com.sang.common.utils.JLog;
import com.sang.common.utils.ToastUtils;
import com.sangxiaonian.xcalendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @data 2019/10/27
 * @Author PING
 * @Description 货主找车
 */
@Route(path = ArouterParamsMatch.fragment_match_owner_find_car)
public class MatchOwnerFindCarFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, View.OnClickListener, OnOptionsSelectListener {
    private ViewPager pager;
    private RadioGroup rg;
    private View img_next;
    private View img_last;
    private TextView tv_start_address;
    private TextView tv_start_address_dess;
    private TextView tv_end_address;
    private TextView tv_end_address_dess;
    private TextView tv_time;
    private TextView tv_price;
    private ViewGroup ll_start_address;
    private ViewGroup ll_end_address;
    private ViewGroup ll_time;
    private View bt_sum;
    private List<MatchCarTypeInfoBean> carInfo;
    private OptionsPickerView<String> pickerView;
    MatchOrderTranMapBean bean = new MatchOrderTranMapBean();
    @Override
    protected View initView(LayoutInflater inflater) {
        View inflate = inflater.inflate(R.layout.fragment_match_owner_find_car, null, false);
        pager = inflate.findViewById(R.id.pager);
        rg = inflate.findViewById(R.id.rg);
        img_last = inflate.findViewById(R.id.img_last);
        img_next = inflate.findViewById(R.id.img_next);
        tv_start_address = inflate.findViewById(R.id.tv_start_address);
        tv_start_address_dess = inflate.findViewById(R.id.tv_start_address_dess);
        tv_end_address = inflate.findViewById(R.id.tv_end_address);
        tv_end_address_dess = inflate.findViewById(R.id.tv_end_address_dess);
        tv_time = inflate.findViewById(R.id.tv_time);
        bt_sum = inflate.findViewById(R.id.bt_sum);
        ll_start_address = inflate.findViewById(R.id.ll_start_address);
        ll_end_address = inflate.findViewById(R.id.ll_end_address);
        ll_time = inflate.findViewById(R.id.ll_time);
        tv_price = inflate.findViewById(R.id.tv_price);
        return inflate;
    }

    @Override
    protected void initData() {
        super.initData();

        initPickDialog();

        //查询车辆信息
        HttpMatchFactory.queryCarTypeInfo()
                .subscribe(new NetObserver<List<MatchCarTypeInfoBean>>(this) {
                    @Override
                    public void doOnSuccess(List<MatchCarTypeInfoBean> data) {
                        initTags(data);
                        carInfo = data;
                        pager.setAdapter(new CarPageAdapter(data, getContext()));
                    }
                })
        ;

    }

    List<String> days;
    List<List<String>> hours = new ArrayList<>();
    List<List<List<String>>> minutes = new ArrayList<>();

    private void initPickDialog() {
        pickerView = PickerDialogUtils.creatPickerDialog(getContext(), this)
                .build();
        Observable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        days = CalendarUtils.getCurentMonthDays(90);
                        hours.clear();
                        minutes.clear();
                        days.remove(0);
                        days.add(0, "今天");
                        for (int i = 0; i < days.size(); i++) {

                            List<String> hour = new ArrayList<>();
                            hours.add(hour);

                            List<List<String>> minute = new ArrayList<>();
                            minutes.add(minute);
                            for (int j = 0; j < 24; j++) {
                                List<String> min = new ArrayList<>();

                                if (i == 0 && j == 0) {
                                    hour.add("立即取货");
                                    ArrayList<String> strings = new ArrayList<>();
                                    minute.add(strings);
                                    for (int k = 0; k < 60; k++) {
                                        strings.add("");
                                    }
                                }
                                for (int k = 0; k < 60; k++) {
                                    min.add(String.format(Locale.CHINESE, "%d分", (k)));
                                }
                                minute.add(min);
                                hour.add(String.format(Locale.CHINESE, "%d点", (j)));
                            }
                        }
                        pickerView.setPicker(days, hours, minutes);
                        return integer;
                    }
                })
                .compose(RxUtils.<Integer>getSchedulersObservableTransformer())
                .subscribe(new BaseObserver<Integer>(null));


    }


    @Override
    protected void initListener() {
        super.initListener();
        rg.setOnCheckedChangeListener(this);
        pager.addOnPageChangeListener(this);
        img_last.setOnClickListener(this);
        img_next.setOnClickListener(this);
        bt_sum.setOnClickListener(this);
        ll_start_address.setOnClickListener(this);
        ll_end_address.setOnClickListener(this);
        ll_time.setOnClickListener(this);
    }

    private void initTags(List<MatchCarTypeInfoBean> data) {
        rg.removeAllViews();
        for (MatchCarTypeInfoBean datum : data) {
            RadioButton tvButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_radio_button, rg, false);
            tvButton.setText(datum.getCarType() == null ? "" : datum.getCarType());
            rg.addView(tvButton);
        }
        rg.getChildAt(0).performClick();
    }

    TranMapBean startInfor;
    TranMapBean endInfor;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == 1) {
            //发货
            TranMapBean result = data.getParcelableExtra(Param.TRAN);
            if (result != null && result.getPoiItem() != null) {
                String placeInfor = Utils.dealPioPlace(result.getPoiItem());
                tv_start_address.setText(result.getPoiItem().getTitle());
                tv_start_address.setTextColor(getResources().getColor(R.color.color_title_dark));
                tv_start_address_dess.setText(placeInfor);
                tv_start_address_dess.setVisibility(View.VISIBLE);
                startInfor = result;
                if (endInfor!=null){
                    queryCardInfor();
                }
            }
        } else if (data != null && requestCode == 2) {
            //发货
            TranMapBean result = data.getParcelableExtra(Param.TRAN);
            if (result != null && result.getPoiItem() != null) {
                String placeInfor = Utils.dealPioPlace(result.getPoiItem());
                tv_end_address.setText(result.getPoiItem().getTitle());
                tv_end_address.setTextColor(getResources().getColor(R.color.color_title_dark));
                tv_end_address_dess.setText(placeInfor);
                tv_end_address_dess.setVisibility(View.VISIBLE);
                endInfor = result;
                if (startInfor!=null){
                    queryCardInfor();
                }
            }

        } else if (requestCode == 3) {


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            if (child.getId() == checkedId && pager.getCurrentItem() != i) {
                pager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (rg.getChildAt(i) != null) {
            rg.getChildAt(i).performClick();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_last) {

            if (pager.getCurrentItem() > 0) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        } else if (v.getId() == R.id.img_next) {
            if (pager.getAdapter() != null && pager.getCurrentItem() < pager.getAdapter().getCount() - 1) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        } else if (v.getId() == R.id.bt_sum) {

            if (carInfo == null) {
                ToastUtils.getInstance().show("请选择车辆信息");
                return;
            } else if (startInfor == null) {
                ToastUtils.getInstance().show("请选择发货地");
                return;
            } else if (endInfor == null) {
                ToastUtils.getInstance().show("请选择收货地");
                return;
            }
            bean.setStart(startInfor);
            bean.setEnd(endInfor);
            bean.setCarTypeInfoBean(carInfo.get(pager.getCurrentItem()));

            String s = tv_time.getText().toString();
            if (TextUtils.isEmpty(s) || s.contains("立刻取货")) {
                bean.setTime(null);
            } else if (s.contains("今天")) {
                String time = ConvertUtils.formatTime(System.currentTimeMillis(), "MM月dd日");
                bean.setTime(s.replace("今天", time));
            } else {
                bean.setTime(s);
            }


            bean.setTime(s);

            Intent intent = new Intent(getContext(), MatchCreatOrderActivity.class);
            intent.putExtra(Param.TRAN, bean);
            startActivityForResult(intent, 3);

        } else if (v.getId() == R.id.ll_start_address) {
            PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    Intent intent = new Intent(getContext(), MatchMapActivity.class);
                    intent.putExtra(Param.TRAN, false);

                    startActivityForResult(intent, 1);
                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                }
            });


        } else if (v.getId() == R.id.ll_end_address) {
            PermissionUtils.applyMap(getActivity(), new PermissionUtils.onApplyPermission() {
                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    Intent intent = new Intent(getContext(), MatchMapActivity.class);
                    intent.putExtra(Param.TRAN, true);

                    startActivityForResult(intent, 2);
                }

                @Override
                public void noPermission(List<String> denied, boolean quick) {
                }
            });


        }  else if (v.getId() == R.id.ll_time) {
            pickerView.show();

        }
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        if (options1 == 0 && options2 == 0) {
            tv_time.setText(hours.get(options1).get(options2));
        } else {
            tv_time.setText(String.format("%s%s%s", days.get(options1), hours.get(options1).get(options2), minutes.get(options1).get(options2).get(options3)));
        }


    }

    //查询预估运费
    private void queryCardInfor() {
        MatchQueryCountFareParam bean = new MatchQueryCountFareParam();
        bean.setCartypeId(carInfo.get(pager.getCurrentItem()).getId());
        bean.setStartPlaceLat(startInfor.getPoiItem().getLatLonPoint().getLatitude() + "");
        bean.setStartPlaceLon(startInfor.getPoiItem().getLatLonPoint().getLongitude() + "");
        bean.setDestinationLat(endInfor.getPoiItem().getLatLonPoint().getLatitude() + "");
        bean.setDestinationLon(endInfor.getPoiItem().getLatLonPoint().getLongitude() + "");
        HttpMatchFactory
                .queryCountFare(bean)
                .subscribe(new NetObserver<MatchCountFareBean>(null) {

                    @Override
                    public void doOnSuccess(MatchCountFareBean data) {
                        try {
                            SpannableStringBuilder builder=new SpannableStringBuilder("预估运费：￥"+data.getEstimateFare());
                            ForegroundColorSpan span=new ForegroundColorSpan( getResources().getColor(R.color.color_new_light));
                            builder.setSpan(span,5,builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            tv_price.setText(builder);
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                })
        ;
    }
}
