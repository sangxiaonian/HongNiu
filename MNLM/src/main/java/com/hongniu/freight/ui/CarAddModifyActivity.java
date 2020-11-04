package com.hongniu.freight.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.fy.androidlibrary.toast.ToastUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.fy.companylibrary.config.Param;
import com.fy.companylibrary.ui.CompanyBaseActivity;
import com.fy.companylibrary.widget.ItemTextView;
import com.hongniu.freight.R;
import com.hongniu.freight.control.CarAddModifyControl;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.presenter.CarAddModifyPresenter;
import com.hongniu.freight.utils.PickerDialogUtils;
import com.hongniu.freight.utils.Utils;
import com.hongniu.freight.widget.DialogComment;
import com.hongniu.freight.widget.ImageInforView;
import com.sang.thirdlibrary.picture.PictureClient;
import com.sang.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

/**
 * @data 2020/3/3
 * @Author PING
 * @Description 新增修改车辆信息
 */
@Route(path = ArouterParamMNLM.activity_car_add_modify)
public class CarAddModifyActivity extends CompanyBaseActivity implements View.OnClickListener, CarAddModifyControl.ICarAddModifyView, OnOptionsSelectListener, ItemTextView.OnCenterChangeListener {

    private TextView btSum;
    private ImageInforView img_positive;//驾驶证正面

    private ImageInforView img_minus;//驾驶证副面
    private ItemTextView item_car_type;//车辆类型
    private ItemTextView item_car_band;//车辆品牌
    private ItemTextView item_car_number;//车牌号
    private ItemTextView item_car_number_color;//车牌号
    private ItemTextView item_car_name;//姓名
    private ItemTextView item_car_phone;//手机号

    CarAddModifyControl.ICarAddModifyPresenter presenter;
    private OptionsPickerView<CarTypeBean> pickerView;
    private OptionsPickerView cargoNumberColorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnlm_activity_car_add_modify);
        CarInfoBean infoBean = getIntent().getParcelableExtra(Param.TRAN);
        if (infoBean == null) {
            setWhitToolBar("填写车辆信息");
        } else {
            setWhitToolBar("车辆信息");

        }
        initView();
        initData();
        initListener();
        presenter = new CarAddModifyPresenter(this);
        presenter.saveInfo(infoBean);
    }

    @Override
    protected void initView() {
        super.initView();
        img_positive = findViewById(R.id.img_positive);
        img_minus = findViewById(R.id.img_minus);
        item_car_type = findViewById(R.id.item_car_type);
        item_car_band = findViewById(R.id.item_car_band);
        item_car_number = findViewById(R.id.item_car_number);
        item_car_number_color = findViewById(R.id.item_car_number_color);
        item_car_name = findViewById(R.id.item_car_name);
        item_car_phone = findViewById(R.id.item_car_phone);
        btSum = findViewById(R.id.bt_sum);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initListener() {
        super.initListener();
        item_car_type.setOnClickListener(this);
        item_car_number_color.setOnClickListener(this);
        btSum.setOnClickListener(this);
        item_car_type.setOnCenterChangeListener(this);
        item_car_band.setOnCenterChangeListener(this);
        item_car_number.setOnCenterChangeListener(this);
        item_car_name.setOnCenterChangeListener(this);
        item_car_number_color.setOnCenterChangeListener(this);
        item_car_phone.setOnCenterChangeListener(this);
        img_positive.setOnClickListener(this);
        img_minus.setOnClickListener(this);
    }


    /**
     * 初始化信息
     *
     * @param infoBean
     * @param enable
     */
    @Override
    public void initInfo(CarInfoBean infoBean, boolean enable) {
        item_car_type.setEnabled(enable);
        item_car_band.setEnabled(enable);
        item_car_number.setEnabled(enable);
        item_car_name.setEnabled(enable);
        item_car_phone.setEnabled(enable);
        item_car_number_color.setEnabled(enable);
        img_minus.setEnabled(enable);
        img_positive.setEnabled(enable);
        btSum.setVisibility(enable ? View.VISIBLE : View.GONE);

        if (infoBean != null) {
            item_car_type.setTextCenter(infoBean.getCarType());
            item_car_band.setTextCenter(infoBean.getVehicleModel());
            item_car_number.setTextCenter(infoBean.getCarNumber());
            item_car_name.setTextCenter(infoBean.getName());
            item_car_phone.setTextCenter(infoBean.getMobile());
            item_car_number_color.setTextCenter(infoBean.getCarColor());
        }

    }

    /**
     * 根据新增修改改变头部信息
     *
     * @param isADd
     */
    @Override
    public void showTitle(boolean isADd) {
        if (isADd) {
            setWhitToolBar("填写车辆信息");
        } else {
            setWhitToolBar("车辆信息");
            setToolbarSrcRight(R.drawable.ic_delete);
            setToolbarRightClick(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showDeletedAlert();

                }
            });
        }
    }

    private void showDeletedAlert() {
        new DialogComment.Builder()
                .setBtLeft("取消")
                .setBtRight("确定")
                .setDialogTitle("确定要删除车辆？")
                .hideContent()
                .setRightClickListener(new DialogComment.OnButtonRightClickListener() {
                    @Override
                    public void onRightClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        presenter.deleted(CarAddModifyActivity.this);
                    }
                })
                .creatDialog(mContext)
                .show();
        ;

    }

    /**
     * 显示选择车辆类型
     *
     * @param carTypeBeans
     */
    @Override
    public void showCarTypesDialog(List<CarTypeBean> carTypeBeans) {
        if (pickerView == null) {
            pickerView = PickerDialogUtils.creatPickerDialog(mContext, this).build();
            pickerView.setTitleText("选择车辆类型");
            pickerView.setPicker(carTypeBeans);
        }
        pickerView.show();
    }

    /**
     * 显示车辆类型
     *
     * @param carType
     */
    @Override
    public void showCarType(String carType) {
        item_car_type.setTextCenter(carType);
    }

    /**
     * 显示图片
     *
     * @param result
     * @param enable
     */
    @Override
    public void showMinus(UpImgData result, boolean enable) {
        check(false);
        if (result != null) {
            img_minus.setImageInfo(result.getAbsolutePath());
        }
    }

    /**
     * 显示图片
     */
    @Override
    public void showPositive(UpImgData result, boolean enable) {
        check(false);
        if (result != null) {
            img_positive.setImageInfo(result.getAbsolutePath());
        }
    }

    /**
     * 新增完成
     */
    @Override
    public void finishWithSuccess() {
        ToastUtils.getInstance().makeToast(ToastUtils.ToastType.SUCCESS).show();
        finish();
    }

    /**
     * 显示车牌颜色
     *
     * @param carNumberColors
     */
    @Override
    public void showCarNumberColorsDialog(List<CargoTypeAndColorBeans> carNumberColors) {
        if (cargoNumberColorDialog == null) {
            cargoNumberColorDialog = PickerDialogUtils.initPickerDialog(mContext, new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    presenter.switchCargoColors(options1);
                }
            });
            cargoNumberColorDialog.setTitleText("选择车牌颜色");
            cargoNumberColorDialog.setPicker(carNumberColors);
        }
        cargoNumberColorDialog.show();
    }

    @Override
    public void showCarNumberColor(String name) {
        item_car_number_color.setTextCenter(name);
    }

    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
        presenter.onSwitchCarType(options1);
    }

    @Override
    public void onCenterChange(String msg) {
        check(false);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_positive) {
//            ToastUtils.getInstance().show("行驶证正面");
            new PictureClient().startPhoto(this, 1, null, new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    LocalMedia media = result.get(0);
                    img_positive.setImageInfo(PicUtils.getPath(media));
                    presenter.upPositive(media, CarAddModifyActivity.this);
                }
            });
        } else if (v.getId() == R.id.img_minus) {
            new PictureClient().startPhoto(this, 1, null, new OnResultCallbackListener() {
                @Override
                public void onResult(List<LocalMedia> result) {
                    LocalMedia media = result.get(0);
                    img_minus.setImageInfo(PicUtils.getPath(media));
                    presenter.upMinus(media, CarAddModifyActivity.this);
                }
            });
        } else if (v.getId() == R.id.item_car_type) {
//            ToastUtils.getInstance().show("车辆类型");
            presenter.queryCarTypes(this);
        } else if (v.getId() == R.id.item_car_number_color) {
//            ToastUtils.getInstance().show(车牌颜色);
            presenter.queryCarNumberColors(this);
        } else if (v.getId() == R.id.bt_sum) {
//            ToastUtils.getInstance().show("车辆类型");
            if (check(true)) {

                CarInfoBean infoBean = new CarInfoBean();
                infoBean.setCarNumber(item_car_number.getTextCenter());
                infoBean.setVehicleModel(item_car_band.getTextCenter());
                infoBean.setName(item_car_name.getTextCenter());
                infoBean.setMobile(item_car_phone.getTextCenter());
                presenter.createCar(infoBean, this);
            }
        }
    }

    private boolean check(boolean showAlert) {
        Utils.setButton(btSum, false);
        if (!presenter.checkPositive()) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传行驶证主页信息");
            }
            return false;
        }
        if (!presenter.checkMinus()) {
            if (showAlert) {
                ToastUtils.getInstance().show("请上传行驶证副页信息");
            }
            return false;
        }

        if (TextUtils.isEmpty(item_car_type.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_type.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_band.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_band.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_number.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_number.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_number_color.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_number_color.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_name.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_name.getTextCenterHide());
            }
            return false;
        }
        if (TextUtils.isEmpty(item_car_phone.getTextCenter())) {
            if (showAlert) {
                ToastUtils.getInstance().show(item_car_phone.getTextCenterHide());
            }
            return false;
        }
        Utils.setButton(btSum, true);
        return true;
    }


}
