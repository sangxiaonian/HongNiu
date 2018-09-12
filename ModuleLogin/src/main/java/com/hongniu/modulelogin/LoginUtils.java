package com.hongniu.modulelogin;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongniu.modulelogin.entity.AreaBean;
import com.hongniu.modulelogin.entity.Citys;
import com.hongniu.modulelogin.entity.NewAreaBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 作者： ${桑小年} on 2018/8/19.
 * 努力，为梦长留
 */
public class LoginUtils {


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


//                        List<List<CityBean>> cityBean = new ArrayList<>();
//                        List<List<List<DistrictBean>>> district = new ArrayList<>();
//                        for (AreaBean provincesBean : provinces) {
//                            List<CityBean> citys = provincesBean.getCitys();
//                            cityBean.add(citys);
//                        }
//                        for (List<CityBean> cityBeans : cityBean) {
//                            List<List<DistrictBean>> child = new ArrayList<>();
//                            for (CityBean bean : cityBeans) {
//                                if (bean.getDistricts().size() == 0) {//城市没有分区
//                                    bean.getDistricts().add(new DistrictBean(bean.getInfor()));
//                                }
//                                child.add(bean.getDistricts());
//                            }
//                            district.add(child);
//                        }
//                        AreaBeans areaBeans = new AreaBeans(provinces, cityBean, district);
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
}
