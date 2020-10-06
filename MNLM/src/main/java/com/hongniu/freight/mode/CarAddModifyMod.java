package com.hongniu.freight.mode;

import com.fy.companylibrary.entity.CommonBean;
import com.fy.baselibrary.interceptor.FileProgressRequestBody;
import com.hongniu.freight.control.CarAddModifyControl;
import com.hongniu.freight.entity.CarInfoBean;
import com.hongniu.freight.entity.CarTypeBean;
import com.hongniu.freight.entity.CargoTypeAndColorBeans;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;
import com.hongniu.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者：  on 2020/3/3.
 */
public class CarAddModifyMod implements CarAddModifyControl.ICarAddModifyMode {
    private List<CarTypeBean> carTypeBeans;
    private int carTypeIndex = -1;
    private UpImgData positivePic;
    private UpImgData minusPic;
    private CarInfoBean infoBean;//外部传入的信息
    private List<CargoTypeAndColorBeans> carNumbersColors;
    private CargoTypeAndColorBeans carNumberColor;

    public CarAddModifyMod() {
        this.carTypeBeans = new ArrayList<>();
        carNumbersColors =new ArrayList<>();
    }

    @Override
    public void saveInfo(CarInfoBean infoBean) {
        this.infoBean=infoBean;
        if (infoBean!=null){
            minusPic=new UpImgData();
            positivePic=new UpImgData();
            minusPic.setAbsolutePath(infoBean.getFullBackVImageUrl());
            minusPic.setPath(infoBean.getBackVImageUrl());
            positivePic.setAbsolutePath(infoBean.getFullFaceVImageUrl());
            positivePic.setPath(infoBean.getFaceVImageUrl());
        }
    }

    /**
     * 查询车辆类型
     *
     * @return
     */
    @Override
    public Observable<CommonBean<List<CarTypeBean>>> queryCarTypes() {
        return HttpAppFactory.queryCarTypeList()
                ;

    }

    @Override
    public void saveCarTypes(List<CarTypeBean> carTypeBeans) {
        this.carTypeBeans  .clear();
        if (carTypeBeans!=null){
            this.carTypeBeans.addAll(carTypeBeans);
        }
    }


    /**
     * 切换车辆类型
     *
     * @param options1
     */
    @Override
    public void onSwitchCarType(int options1) {
        this.carTypeIndex = options1;
    }

    @Override
    public List<CarTypeBean> getCarTypes() {
        return carTypeBeans;
    }

    /**
     * 上传图片
     *
     * @param localMedia
     * @return
     */
    @Override
    public Observable<UpImgData> upImage(LocalMedia localMedia) {
        return HttpAppFactory.upImage(12, PicUtils.getPath(localMedia), new FileProgressRequestBody.ProgressListener() {
            @Override
            public void transferred(long current, long totle) {

            }
        });
    }

    /**
     * 储存驾驶证主页数据
     *
     * @param result
     */
    @Override
    public void savePositivePic(UpImgData result) {
        this.positivePic = result;
    }

    /**
     * 储存驾驶证副页数据
     *
     * @param result
     */
    @Override
    public void saveMinusPic(UpImgData result) {
        this.minusPic = result;
    }

    @Override
    public UpImgData getMinusPic() {
        return minusPic;
    }

    @Override
    public UpImgData getPositivePic() {
        return positivePic;
    }

    /**
     * 新增修改车辆信息
     *
     * @param infoBean
     * @return
     */
    @Override
    public Observable<CommonBean<Object>> createCar(CarInfoBean infoBean) {
        if (carTypeBeans.size()>carTypeIndex&&carTypeIndex>=0) {
            infoBean.setCarTypeId(carTypeBeans.get(carTypeIndex).getId());
        }
        if (positivePic!=null){
            infoBean.setFaceVImageUrl(positivePic.getPath());
        }
        if (minusPic!=null){
            infoBean.setBackVImageUrl(minusPic.getPath());
        }
        if (this.infoBean!=null){
            infoBean.setId(this.infoBean.getId());
        }

        if (this.carNumberColor!=null){
            infoBean.setCarColorId(carNumberColor.getId());
        }

       return HttpAppFactory.createCar(infoBean);

    }

    @Override
    public boolean enable() {
        return infoBean == null || infoBean.getIdentityStatus() == 5;
    }

    /**
     * 删除车辆
     * @return
     */
    @Override
    public Observable<CommonBean<Object>> deleted() {
     return  HttpAppFactory.deletedCar(infoBean==null?"":infoBean.getId());
    }

    /**
     * 获取车辆颜色数据
     *
     * @return
     */
    @Override
    public List<CargoTypeAndColorBeans> getCarNumberColors() {
        return carNumbersColors;
    }

    /**
     * 查询车辆颜色
     * @return
     */
    @Override
    public Observable<CommonBean<List<CargoTypeAndColorBeans>>> queryCarNumberColors() {
        return HttpAppFactory.queryConfigInfoType(2)

                ;
    }

    /**
     * 储存车辆颜色信息
     *
     * @param carTypeBeans
     */
    @Override
    public void saveCarNumbers(List<CargoTypeAndColorBeans> carTypeBeans) {
        carNumbersColors.clear();
        if (carTypeBeans!=null){
            carNumbersColors.addAll(carTypeBeans);
        }
    }

    @Override
    public void switchCargoColors(CargoTypeAndColorBeans cargoTypeAndColorBeans) {
        this.carNumberColor=cargoTypeAndColorBeans;
    }
}
