package com.hongniu.baselibrary.utils;

import android.app.Activity;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

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
//                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .permission(new String[]{Permission.MANAGE_EXTERNAL_STORAGE,
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION,
                        Permission.READ_PHONE_STATE}) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        permission.hasPermission(permissions, all);
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        permission.noPermission(permissions, never);
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
                .permission(Permission.MANAGE_EXTERNAL_STORAGE) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        permission.hasPermission(permissions, all);
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        permission.noPermission(permissions, never);
                    }
                })
        ;

    }

    /**
     * 申请储存相关权限
     *
     * @param activity
     */
    public static void apply(Activity activity,String permission, final onApplyPermission applyPermission) {
        XXPermissions.with(activity)
                .permission(permission) //支持多个权限组进行请求，不指定则默以清单文件中的危险权限进行请求
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        applyPermission.hasPermission(permissions, all);
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        applyPermission.noPermission(permissions, never);
                    }
                })
        ;

    }


}
