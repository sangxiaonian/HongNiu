package com.hongniu.freight.presenter;

import com.fy.androidlibrary.net.listener.TaskControl;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.companylibrary.net.NetObserver;
import com.hongniu.freight.control.CarAddModifyControl;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.mode.CarAddModifyMod;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

/**
 * 作者：  on 2020/3/3.
 */
public class CarAddModifyPresenter implements CarAddModifyControl.ICarAddModifyPresenter {
    CarAddModifyControl.ICarAddModifyView view;
    CarAddModifyControl.ICarAddModifyMode mode;

    public CarAddModifyPresenter(CarAddModifyControl.ICarAddModifyView view) {
        this.view = view;
        mode=new CarAddModifyMod();
    }

    /**
     * 储存外部传入的信息
     *
     * @param infoBean
     */
    @Override
    public void saveInfo(CarInfoBean infoBean) {
        mode.saveInfo(infoBean);
        view.initInfo(infoBean,mode.enable());
        view.showMinus(mode.getMinusPic(),mode.enable());
        view.showPositive(mode.getPositivePic(),mode.enable());
        view.showTitle(infoBean==null);
    }

    /**
     * 点击修改车辆类型
     * @param listener
     */
    @Override
    public void queryCarTypes(TaskControl.OnTaskListener listener) {
        List<CarTypeBean> carTypes = mode.getCarTypes();
        if (CollectionUtils.isEmpty(carTypes)){
            mode.queryCarTypes()
                    .subscribe(new NetObserver<List<CarTypeBean>>(listener){
                        @Override
                        public void doOnSuccess(List<CarTypeBean> carTypeBeans) {
                            super.doOnSuccess(carTypeBeans);
                            mode.saveCarTypes(carTypeBeans);
                            view.showCarTypesDialog(carTypeBeans);
                        }
                    })
            ;
        }else {
            view.showCarTypesDialog(carTypes);
        }


    }

    /**
     * 切换车辆类型
     *
     * @param options1
     */
    @Override
    public void onSwitchCarType(int options1) {
        mode.onSwitchCarType(options1);
        List<CarTypeBean> carTypes = mode.getCarTypes();
        CarTypeBean typeBean = carTypes.get(options1);
        view.showCarType(typeBean.getCarType());
    }

    /**
     * 查询车牌颜色
     *
     * @param listener
     */
    @Override
    public void queryCarNumberColors(TaskControl.OnTaskListener listener) {
        List<CargoTypeAndColorBeans> carNumberColors = mode.getCarNumberColors();
        if (CollectionUtils.isEmpty(carNumberColors)){
            mode.queryCarNumberColors()
                    .subscribe(new NetObserver<List<CargoTypeAndColorBeans>>(listener){
                        @Override
                        public void doOnSuccess(List<CargoTypeAndColorBeans> carTypeBeans) {
                            super.doOnSuccess(carTypeBeans);
                            mode.saveCarNumbers(carTypeBeans);
                            view.showCarNumberColorsDialog(carTypeBeans);
                        }
                    })
            ;
        }else {
            view.showCarNumberColorsDialog(carNumberColors);
        }
    }

    /**
     * 上传行驶证主页
     *
     * @param localMedia
     * @param listener
     */
    @Override
    public void upPositive(LocalMedia localMedia, TaskControl.OnTaskListener listener) {
        mode.upImage(localMedia)
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.showPositive(mode.getPositivePic(), mode.enable());

                    }
                })
            .subscribe(new BaseObserver<UpImgData>(listener){
                @Override
                public void onSubscribe(Disposable d) {
                    super.onSubscribe(d);
                }

                @Override
                public void onNext(UpImgData result) {
                    super.onNext(result);
                    mode.savePositivePic(result);
                }
            })
        ;
    }

    /**
     * 上传行驶证副业
     *
     * @param localMedia
     * @param listener
     */
    @Override
    public void upMinus(LocalMedia localMedia, TaskControl.OnTaskListener listener) {
        mode.upImage(localMedia)
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        view.showMinus(mode.getMinusPic(), mode.enable());

                    }
                })
                .subscribe(new BaseObserver<UpImgData>(listener){
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        mode.saveMinusPic(null);
                    }
                    @Override
                    public void onNext(UpImgData result) {
                        super.onNext(result);
                        mode.saveMinusPic(result);
                    }
                })
        ;
    }

    /**
     * 检查是否又驾驶证主页
     * @return
     */
    @Override
    public boolean checkPositive() {
       return mode.getPositivePic()!=null;
    }

    /**
     * 检查是否又驾驶证副页
     * @return
     */
    @Override
    public boolean checkMinus() {
        return mode.getMinusPic()!=null;

    }

    /**
     * 新增修改车辆信息
     * @param infoBean
     * @param listener
     */
    @Override
    public void createCar(CarInfoBean infoBean, TaskControl.OnTaskListener listener) {
        mode.createCar(infoBean)
            .subscribe(new NetObserver<Object>(listener){
                @Override
                public void doOnSuccess(Object o) {
                    view.finishWithSuccess();
                }
            })
        ;
    }

    @Override
    public void deleted(TaskControl.OnTaskListener listener) {
        mode.deleted()
            .subscribe(new NetObserver<Object>(listener){
                @Override
                public void doOnSuccess(Object o) {
                    super.doOnSuccess(o);
                    view.finishWithSuccess();
                }
            })
        ;
    }

    /**
     * 切换车牌颜色
     * * @param options1
     *
     * @param options1
     */
    @Override
    public void switchCargoColors(int options1) {
        CargoTypeAndColorBeans cargoTypeAndColorBeans = mode.getCarNumberColors().get(options1);

        mode.switchCargoColors(cargoTypeAndColorBeans);
        view.showCarNumberColor(cargoTypeAndColorBeans.getName());
    }
}
