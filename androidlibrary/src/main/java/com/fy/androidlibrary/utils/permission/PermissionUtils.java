package com.fy.androidlibrary.utils.permission;

import android.Manifest;
import android.app.Activity;

import androidx.fragment.app.FragmentActivity;

import com.fy.androidlibrary.utils.permission.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者： ${桑小年} on 2018/8/14.
 * 努力，为梦长留
 */
public class PermissionUtils {

    public interface onApplyPermission {
        void hasPermission();

        void noPermission();
    }

    /**
     * 申请地图相关权限
     *
     * @param activity
     */
    public static void applyMap(Activity activity, final onApplyPermission permission) {
        applyPermission(activity,
                permission,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        );


    }

    /**
     * 申请储存相关权限
     *
     * @param activity
     */
    public static void applyStorage(Activity activity, final onApplyPermission permission) {
        applyPermission(activity, permission
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
        );


    }

    /**
     * 申请储存相关权限
     *
     * @param activity
     */
    public static void applyCamera(Activity activity, final onApplyPermission permission) {
        applyPermission(activity, permission
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA
        );


    }

    /**
     * 申请储存相关权限
     *
     * @param activity
     */
    public static void applyPermission(Activity activity, final onApplyPermission permission, String... permissions) {

        new RxPermissions((FragmentActivity) activity)
                .request(permissions)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            if (permission != null) {
                                permission.hasPermission();
                            }
                        } else {
                            if (permission != null) {
                                permission.noPermission();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
