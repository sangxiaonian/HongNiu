package com.hongniu.freight.control;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.hongniu.baselibrary.entity.CommonBean;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.UpImgData;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import io.reactivex.Observable;

/**
 * 作者：  on 2020/3/3.
 */
public class CarAddModifyControl {
    public interface ICarAddModifyView {

        /**
         * 初始化信息
         *
         * @param infoBean
         * @param enable
         */
        void initInfo(CarInfoBean infoBean, boolean enable);

        /**
         * 根据新增修改改变头部信息
         *
         * @param isADd
         */
        void showTitle(boolean isADd);

        /**
         * 显示选择车辆类型
         *
         * @param carTypeBeans
         */
        void showCarTypesDialog(List<CarTypeBean> carTypeBeans);

        /**
         * 显示车辆类型
         *
         * @param carType
         */
        void showCarType(String carType);

        /**
         * 显示图片
         *
         * @param result
         * @param enable
         */
        void showMinus(UpImgData result, boolean enable);

        /**
         * 显示图片
         *
         * @param positivePic
         * @param enable
         */
        void showPositive(UpImgData positivePic, boolean enable);

        /**
         * 新增完成
         */
        void finishWithSuccess();


        /**
         * 显示车牌颜色
         *
         * @param carNumberColors
         */
        void showCarNumberColorsDialog(List<CargoTypeAndColorBeans> carNumberColors);

        void showCarNumberColor(String name);
    }

    public interface ICarAddModifyPresenter {
        /**
         * 储存外部传入的信息
         *
         * @param infoBean
         */
        void saveInfo(CarInfoBean infoBean);

        /**
         * 点击修改车辆类型
         *
         * @param listener
         */
        void queryCarTypes(TaskControl.OnTaskListener listener);

        /**
         * 切换车辆类型
         *
         * @param options1
         */
        void onSwitchCarType(int options1);

        /**
         * 查询车牌颜色
         *
         * @param listener
         */
        void queryCarNumberColors(TaskControl.OnTaskListener listener);

        /**
         * 上传行驶证主页
         *
         * @param localMedia
         * @param listener
         */
        void upPositive(LocalMedia localMedia, TaskControl.OnTaskListener listener);

        /**
         * 上传行驶证副业
         *
         * @param localMedia
         * @param listener
         */
        void upMinus(LocalMedia localMedia, TaskControl.OnTaskListener listener);

        /**
         * 检查是否又驾驶证主页
         *
         * @return
         */
        boolean checkPositive();

        /**
         * 检查是否又驾驶证副页
         *
         * @return
         */
        boolean checkMinus();

        /**
         * 新增修改车辆信息
         *
         * @param infoBean
         * @param listener
         */
        void createCar(CarInfoBean infoBean, TaskControl.OnTaskListener listener);


        void deleted(TaskControl.OnTaskListener listener);


        /**
         * 切换车牌颜色
         * * @param options1
         */
        void switchCargoColors(int options1);
    }

    public interface ICarAddModifyMode {
        void saveInfo(CarInfoBean infoBean);

        /**
         * 查询车辆类型
         *
         * @return
         */
        Observable<CommonBean<List<CarTypeBean>>> queryCarTypes();

        void saveCarTypes(List<CarTypeBean> carTypeBeans);

        /**
         * 切换车辆类型
         *
         * @param options1
         */
        void onSwitchCarType(int options1);

        List<CarTypeBean> getCarTypes();

        /**
         * 上传图片
         *
         * @param localMedia
         * @return
         */
        Observable<UpImgData> upImage(LocalMedia localMedia);

        /**
         * 储存驾驶证主页数据
         *
         * @param result
         */
        void savePositivePic(UpImgData result);

        /**
         * 储存驾驶证副页数据
         *
         * @param result
         */
        void saveMinusPic(UpImgData result);

        UpImgData getMinusPic();

        UpImgData getPositivePic();

        /**
         * 新增修改车辆信息
         *
         * @param infoBean
         * @return
         */
        Observable<CommonBean<Object>> createCar(CarInfoBean infoBean);

        boolean enable();

        /**
         * 删除车辆
         *
         * @return
         */
        Observable<CommonBean<Object>> deleted();

        /**
         * 获取车辆颜色数据
         *
         * @return
         */
        List<CargoTypeAndColorBeans> getCarNumberColors();

        /**
         * 查询车辆颜色
         *
         * @return
         */
        Observable<CommonBean<List<CargoTypeAndColorBeans>>> queryCarNumberColors();

        /**
         * 储存车辆颜色信息
         *
         * @param carTypeBeans
         */
        void saveCarNumbers(List<CargoTypeAndColorBeans> carTypeBeans);

        void switchCargoColors(CargoTypeAndColorBeans cargoTypeAndColorBeans);

    }
}
