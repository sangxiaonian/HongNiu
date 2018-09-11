package com.sang.thirdlibrary.map.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * -1	路径计算失败。	在导航过程中调用calculateDriveRoute方法导致的失败，导航过程中只能用reCalculate方法进行路径计算。
 * 1	路径计算成功。
 * 2	网络超时或网络失败,请检查网络是否通畅，稍候再试。
 * 3	路径规划起点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。
 * 4	协议解析错误,请稍后再试。
 * 6	路径规划终点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。
 * 7	算路服务端编码失败.
 * 10	起点附近没有找到可行道路,请对起点进行调整。
 * 11	终点附近没有找到可行道路,请对终点进行调整。
 * 12	途经点附近没有找到可行道路,请对途经点进行调整。
 * 13	key鉴权失败。	请仔细检查key绑定的sha1值与apk签名sha1值是否对应，或通过;高频问题查找相关解决办法。
 * 14	请求的服务不存在,	请稍后再试。
 * 15	请求服务响应错误,请检查网络状况，稍后再试。
 * 16	无权限访问此服务,请稍后再试。
 * 17	请求超出配额。
 * 18	请求参数非法,请检查传入参数是否符合要求。
 * 19	未知错误。
 **/
public class ErrorInfo {
    private static Map<Integer, String> list = new HashMap();
    private static Map<Integer, String> locationError = new HashMap();

    static {
        list.put(-1, "路径计算失败，在导航过程中调用calculateDriveRoute方法导致的失败，导航过程中只能用reCalculate方法进行路径计算。");
        list.put(1, "路径计算成功。");
        list.put(2, "网络超时或网络失败,请检查网络是否通畅，如网络没问题,查看Logcat输出是否出现鉴权错误信息，如有，说明SHA1与KEY不对应导致。");
        list.put(3, "路径规划起点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。");
        list.put(4, "协议解析错误,请稍后再试。");
        list.put(6, "路径规划终点经纬度不合法,请选择国内坐标点，确保经纬度格式正常。");
        list.put(7, "算路服务端编码失败.");
        list.put(10, "起点附近没有找到可行道路,请对起点进行调整。");
        list.put(11, "终点附近没有找到可行道路,请对终点进行调整。");
        list.put(12, "途经点附近没有找到可行道路,请对途经点进行调整。");
        list.put(13, "key鉴权失败，请仔细检查key绑定的sha1值与apk签名sha1值是否对应，或通过;高频问题查找相关解决办法。");
        list.put(14, "请求的服务不存在,请稍后再试。");
        list.put(15, "请求服务响应错误,请检查网络状况，稍后再试。");
        list.put(16, "无权限访问此服务,请稍后再试。");
        list.put(17, "请求超出配额。");
        list.put(18, "请求参数非法,请检查传入参数是否符合要求。");
        list.put(19, "未知错误。");
    }


    static {
        locationError.put(0,"定位成功");
        locationError.put(1,"重要参数为空");
        locationError.put(2,"请重新尝试，定位失败，近扫描到单个wifi，且没有基站信息");
        locationError.put(3,"获取到的请求参数为空，");
        locationError.put(4,"定位失败，请检查设备网络是否通畅");
        locationError.put(5,"定位失败，请求被恶意劫持，定位结果解析失败。");
        locationError.put(6,"定位失败，定位服务返回定位失败");
        locationError.put(7,"KEY鉴权失败");
        locationError.put(8,"Android exception常规错误");
        locationError.put(9,"定位初始化时出现异常，请重新启动定位" );
        locationError.put(10,"定位客户端启动失败" );
        locationError.put(11,"定位失败，请检查是否安装SIM卡，设备很有可能连入了伪基站网络。" );
        locationError.put(12,"定位失败，请在设备的设置中开启app的定位权限" );
        locationError.put(13,"定位失败，由于未获得WIFI列表和基站信息，且GPS当前不可用" );
        locationError.put(14,"GPS信号弱，请到开阔地带重新尝试" );
        locationError.put(18,"定位失败，由于手机WIFI功能被关闭同时设置为飞行模式" );
        locationError.put(19,"定位失败，由于手机没插sim卡且WIFI功能被关闭" );
    }

    public static String getError(int id) {
        return list.get(id);
    }

    public static String getLoactionError(int id){
        return locationError.get(id)==null?"定位失败":locationError.get(id);
    }
}
