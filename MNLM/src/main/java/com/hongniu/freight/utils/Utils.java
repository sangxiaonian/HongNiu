package com.hongniu.freight.utils;

import android.content.Context;
import android.view.View;

import com.amap.api.services.core.PoiItem;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.fy.androidlibrary.utils.ConvertUtils;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.fy.companylibrary.config.ArouterParamMNLM;
import com.hongniu.baselibrary.entity.CommonBean;
import com.fy.companylibrary.entity.PageBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongniu.freight.config.OrderButtonConfig;
import com.hongniu.freight.config.Role;
import com.hongniu.freight.config.RoleOrder;
import com.hongniu.freight.entity.AreaBean;
import com.hongniu.freight.entity.Citys;
import com.hongniu.freight.entity.NewAreaBean;
import com.hongniu.freight.entity.OrderInfoBean;
import com.hongniu.freight.entity.PersonInfor;
import com.hongniu.freight.widget.DialogComment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者：  on 2020/2/7.
 */
public class Utils {

    public static Observable<CommonBean<PageBean<OrderInfoBean>>> createDemoOrderInfo() {
        CommonBean<PageBean<OrderInfoBean>> common = new CommonBean<>();
        common.setCode(200);
        PageBean<OrderInfoBean> pageBean = new PageBean<>();
        common.setData(pageBean);
        List<OrderInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderInfoBean orderInfoBean = new OrderInfoBean();
            orderInfoBean.setStatus(i);
            list.add(orderInfoBean);
        }
        pageBean.setList(list);
        return Observable.just(common);
    }


    /**
     * 获取托运人类别
     *
     * @return 0 个人 1平台
     */
    public static int isStaff() {
        return InfoUtils.getMyInfo() != null ? InfoUtils.getMyInfo().getIsStaff() : 0;
    }

    public static void setButton(View button, boolean enable) {
        if (button == null) {
            return;
        }
        if (enable) {
            button.setAlpha(1f);
        } else {
            button.setAlpha(0.3f);

        }
    }

    /**
     * 对地址显示进行处理
     *
     * @param data
     */
    public static String dealPioPlace(PoiItem data) {
        String placeInfor = "";
        if (data != null && data.getProvinceName() != null) {
            if (data.getProvinceName().equals(data.getCityName())) {
                placeInfor = data.getProvinceName() + data.getAdName()
                        + data.getSnippet();
            } else {
                placeInfor = data.getProvinceName() + data.getCityName() + data.getAdName()
                        + data.getSnippet();
            }
        }
        return placeInfor;
    }


    public static String getTitleTime() {
        int time = ConvertUtils.getTime();
        String current = "";
        if (time == 0) {
            current = "凌晨";
        } else if (time == 1) {
            current = "上午";
        } else if (time == 2) {
            current = "中午";
        } else if (time == 3) {
            current = "下午";
        } else if (time == 4) {
            current = "晚上";
        }
        return current;

    }

    public static Observable<Citys> getNewAreas(final Context context) {
        return Observable.just(context)
                .map(new Function<Context, String>() {
                    @Override
                    public String apply(Context context) throws Exception {
                        InputStream is = context.getAssets().open("city.json");
                        return readTextFromSDcard(is).trim();
                    }
                })
                .map(new Function<String, List<AreaBean>>() {
                    @Override
                    public List<AreaBean> apply(String json) throws Exception {
                        Type type = new TypeToken<ArrayList<AreaBean>>() {
                        }.getType();
                        List<AreaBean> o = new Gson().fromJson(json, type);
                        return o;
                    }
                })
                .map(new Function<List<AreaBean>, Citys>() {
                    @Override
                    public Citys apply(List<AreaBean> provinces) throws Exception {

                        //省份
                        List<NewAreaBean> shengs = new ArrayList<>();
                        List<List<NewAreaBean>> shis = new ArrayList<>();
                        List<List<List<NewAreaBean>>> quyus = new ArrayList<>();

                        for (AreaBean province : provinces) {
                            NewAreaBean sheng = new NewAreaBean();
                            sheng.setId(province.getId());
                            sheng.setName(province.getName());
                            shengs.add(sheng);

                            //省下面的城市,对城市进行处理
                            List<AreaBean.CitysBean> citys = province.getCitys();
                            //如果当前没有城市选项，则自己添加一个城市进去，值就取用当前省份，区域同理
                            if (citys == null || citys.isEmpty()) {
                                ArrayList<NewAreaBean> newAreaBeans = new ArrayList<>();
                                NewAreaBean city = new NewAreaBean();
                                city.setId(province.getId());
                                city.setName(province.getName());
                                newAreaBeans.add(city);
                                shis.add(newAreaBeans);

                                ArrayList<List<NewAreaBean>> quyu1 = new ArrayList<>();
                                ArrayList<NewAreaBean> quyu2 = new ArrayList<>();
                                NewAreaBean qu = new NewAreaBean();
                                qu.setId(province.getId());
                                qu.setName(province.getName());
                                quyu2.add(qu);
                                quyu1.add(quyu2);
                                quyus.add(quyu1);
                            } else {
                                ArrayList<NewAreaBean> newAreaBeans = new ArrayList<>();
                                ArrayList<List<NewAreaBean>> quyu1 = new ArrayList<>();
                                for (AreaBean.CitysBean c : citys) {
                                    NewAreaBean city = new NewAreaBean();
                                    city.setId(c.getId());
                                    city.setName(c.getName());
                                    newAreaBeans.add(city);

                                    //城市处理完成之后，对城市内部的区域进行处理
                                    List<AreaBean.CitysBean.AreasBean> areas = c.getAreas();
                                    ArrayList<NewAreaBean> quyu2 = new ArrayList<>();
                                    if (areas == null || areas.isEmpty()) {
                                        NewAreaBean quyu = new NewAreaBean();
                                        quyu.setId(c.getId());
                                        quyu.setName(c.getName());
                                        quyu2.add(quyu);
                                    } else {
                                        for (AreaBean.CitysBean.AreasBean area : areas) {
                                            NewAreaBean quyu = new NewAreaBean();
                                            quyu.setId(area.getId());
                                            quyu.setName(area.getName());
                                            quyu2.add(quyu);
                                        }
                                    }
                                    quyu1.add(quyu2);


                                }
                                shis.add(newAreaBeans);
                                quyus.add(quyu1);
                            }
                        }
                        Citys citys = new Citys();
                        citys.setShengs(shengs);
                        citys.setShis(shis);
                        citys.setQuyus(quyus);
                        return citys;
                    }
                })
                ;


    }

    private static String readTextFromSDcard(InputStream fis) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    fis, "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }

    /**
     * 过滤掉评价数据
     *
     * @param role
     * @param data
     * @param buttons
     */
    public static void fliter(RoleOrder role, OrderInfoBean data, Map<String, Integer> buttons) {
        if (role == null || data == null || CollectionUtils.isEmpty(buttons)) {
            return;
        }
        boolean fliter = false;
        if (role == RoleOrder.DRIVER && data.getDriverEvaluateState() == 1) {
            fliter = true;
        }
        if (role == RoleOrder.CARRIER && data.getOwenrEvaluateState() == 1) {
            fliter = true;
        }
        if (role == RoleOrder.SHIPPER && data.getUserEvaluateState() == 1) {
            fliter = true;
        }
        if (fliter) {
            buttons.remove(OrderButtonConfig.EVALUATE);
        }
    }

    /**
     * 是否显示保险相关数据
     *
     * @return
     */
    public static boolean isShowInsurance() {
        return true;
    }

    public static DialogComment dialogAttes(Context mContext, DialogComment.OnButtonRightClickListener rightClickListener) {
        return new DialogComment.Builder()
                    .setBtLeft("暂不认证")
                    .setDialogTitle("未完成实名认证或实名认证")
                    .setBtRight("去认证")
                    .hideContent()
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .setRightClickListener(rightClickListener)
                    .creatDialog(mContext);



    }

    public static void jump2Attestation(Context mContext,PersonInfor personInfo) {
        if (InfoUtils.getRole(personInfo)== Role.UNKNOW||InfoUtils.getState(personInfo)==5) {
            //跳转到实名认证选角色
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_attestation_select_role)
                    .navigation(mContext);
        }  else if ( personInfo.getIsRealname() != 1) {
            ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_attestation_face)
                    .navigation(mContext);
        }

    }

    /**
     * 跳转到人脸识别页面，2020/11/22 去除人脸识别功能，直接跳转到首页
     * @param mContext
     */
    public static void jump2FaceAttestation(Context mContext) {
        ArouterUtils.getInstance().builder(ArouterParamMNLM.activity_main)
                .navigation(mContext);

    }
}
