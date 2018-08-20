package com.hongniu.modulelogin;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongniu.modulelogin.entity.AreaBeans;
import com.hongniu.modulelogin.entity.CityBean;
import com.hongniu.modulelogin.entity.DistrictBean;
import com.hongniu.modulelogin.entity.LoginAreaBean;
import com.hongniu.modulelogin.entity.ProvincesBean;
import com.sang.common.utils.JLog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

import static android.content.Context.MODE_PRIVATE;

/**
 * 作者： ${桑小年} on 2018/8/19.
 * 努力，为梦长留
 */
public class LoginUtils {

    public static Observable<AreaBeans> getAreas(final Context context) {
        return Observable.just(context)
                .map(new Function<Context, String>() {
                    @Override
                    public String apply(Context context) throws Exception {
                        InputStream is = context.getAssets().open("city.json");
                        return readTextFromSDcard(is).trim();
                    }
                })
                .map(new Function<String, List<ProvincesBean>>() {
                    @Override
                    public List<ProvincesBean> apply(String json) throws Exception {
                        Type type = new TypeToken<ArrayList<ProvincesBean>>() {
                        }.getType();
                        List<ProvincesBean> o = new Gson().fromJson(json, type);
                        return o;
                    }
                })
                .map(new Function<List<ProvincesBean>, AreaBeans>() {
                    @Override
                    public AreaBeans apply(List<ProvincesBean> provinces) throws Exception {
                        List<List<CityBean>> cityBean = new ArrayList<>();
                        List<List<List<DistrictBean>>> district = new ArrayList<>();
                        for (ProvincesBean provincesBean : provinces) {
                            List<CityBean> citys = provincesBean.getCitys();
                            cityBean.add(citys);
                        }
                        for (List<CityBean> cityBeans : cityBean) {
                            List<List<DistrictBean>> child = new ArrayList<>();
                            for (CityBean bean : cityBeans) {
                                if (bean.getDistricts().size() == 0) {//城市没有分区
                                    bean.getDistricts().add(new DistrictBean(bean.getInfor()));
                                }
                                child.add(bean.getDistricts());
                            }
                            district.add(child);
                        }
                        AreaBeans areaBeans = new AreaBeans(provinces, cityBean, district);
                        return areaBeans;
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
