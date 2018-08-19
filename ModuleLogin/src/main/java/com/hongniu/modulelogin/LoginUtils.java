package com.hongniu.modulelogin;

import android.content.Context;

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
                        InputStream is = context.getResources().openRawResource(R.raw.area);
                        return readTextFromSDcard(is);
                    }
                })
                .map(new Function<String, List<LoginAreaBean>>() {
                    @Override
                    public List<LoginAreaBean> apply(String json) throws Exception {
                        Type type = new TypeToken<ArrayList<LoginAreaBean>>() {
                        }.getType();
                        return new Gson().fromJson(json, type);
                    }
                })
                .map(new Function<List<LoginAreaBean>, AreaBeans>() {
                    @Override
                    public AreaBeans apply(List<LoginAreaBean> loginAreaBeans) throws Exception {

                        List<ProvincesBean> provinces = new ArrayList<>();

                        //解析所有的省份
                        for (LoginAreaBean loginAreaBean : loginAreaBeans) {
                            if (loginAreaBean.getLevel() == 1) {
                                provinces.add(new ProvincesBean(loginAreaBean));
                            }
                        }
                        //城市
                        for (ProvincesBean province : provinces) {
                            //解析所有的城市
                            province.setCitys(new ArrayList<CityBean>());
                            for (LoginAreaBean loginAreaBean : loginAreaBeans) {
                                if (loginAreaBean.getLevel() == 2 && province.getInfor().getAreaId() == loginAreaBean.getParentId()) {
                                    province.getCitys().add(new CityBean(loginAreaBean));
                                }
                            }
                        }
                        //区
                        for (ProvincesBean province : provinces) {
                            //解析所有的城市
                            List<CityBean> citys = province.getCitys();
                            for (CityBean city : citys) {
                                city.setDistricts(new ArrayList<DistrictBean>());
                                for (LoginAreaBean loginAreaBean : loginAreaBeans) {
                                    if (loginAreaBean.getLevel() == 3 && city.getInfor().getAreaId() == loginAreaBean.getParentId()) {
                                        city.getDistricts().add(new DistrictBean(loginAreaBean));
                                    }
                                    if (city.getDistricts().size() == 0) {
                                        city.getDistricts().add(new DistrictBean(city.getInfor()));
                                    }
                                }


                            }

                        }

                        String s = new Gson().toJson(provinces);
                        FileOutputStream fout = context.openFileOutput("city.json", MODE_PRIVATE);
                        OutputStreamWriter osw = new OutputStreamWriter(fout, "UTF-8");
                        osw.write(s);
                        osw.flush();
                        fout.flush();  //flush是为了输出缓冲区中所有的内容

                        osw.close();
                        fout.close();  //写入完成后，将两个输出关闭


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
        try {

            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            char[] input = new char[fis.available()];  //available()用于获取filename内容的长度
            isr.read(input);  //读取并存储到input中

            isr.close();
            fis.close();//读取完成后关闭

            String str = new String(input); //将读取并存放在input中的数据转换成String输出
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
