package com.hongniu.baselibrary.utils;

import android.app.Activity;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hongniu.baselibrary.arouter.ArouterParamOrder;
import com.hongniu.baselibrary.arouter.ArouterUtils;
import com.hongniu.baselibrary.config.Param;

import java.util.List;

/**
 * 作者： ${桑小年} on 2018/8/14.
 * 努力，为梦长留
 */
public class PermissionUtils {

    public interface onApplyPermission {
        void hasPermission(List<String> granted, boolean isAll);

        void noPermission(List<String> denied, boolean quick);
    }

    /**
     * 申请地图相关权限
     *
     * @param activity
     */
    public static void applyMap(Activity activity, final onApplyPermission permission) {
        XXPermissions.with(activity)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
//                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_COARSE_LOCATION)
                .permission(Permission.Group.STORAGE,Permission.Group.LOCATION,new String[]{Permission.READ_PHONE_STATE} ) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        permission.hasPermission(granted,isAll);

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        permission.noPermission(denied,quick);
                    }
                });

    }

    /**
     * 申请储存相关权限
     *
     * @param activity
     */
    public static void applyStorage(Activity activity, final onApplyPermission permission) {
        XXPermissions.with(activity)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.WRITE_EXTERNAL_STORAGE) //支持请求安装权限和悬浮窗权限
                .permission(Permission.Group.STORAGE) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        permission.hasPermission(granted,isAll);

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        permission.noPermission(denied,quick);
                    }
                });

    }


}
