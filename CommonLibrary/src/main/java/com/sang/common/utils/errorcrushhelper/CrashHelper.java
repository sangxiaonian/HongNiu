package com.sang.common.utils.errorcrushhelper;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.fy.androidlibrary.net.rx.RxUtils;
import com.fy.androidlibrary.utils.JLog;


import org.reactivestreams.Subscription;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * 作者： ${PING} on 2018/12/12.
 * 接口log日志的捕获类
 */
public class CrashHelper {

    private static String PATH_LOGCAT;
    private Subscription sub;

    private static class InnerClass {
        private static CrashHelper helper = new CrashHelper();
    }

    public static CrashHelper getInstance() {

        return InnerClass.helper;
    }

    private CrashHelper() {
    }

    /**
     * 初始化目录
     */
    public CrashHelper init(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            PATH_LOGCAT = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "crash";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
                    + File.separator + "crash";
        }
        File file = new File(PATH_LOGCAT);
        if (!file.exists()) {
            file.mkdirs();
        }
        return this;
    }

    /**
     * 储存异常信息到本地文件中
     *
     * @param throwable
     */
    public void saveCrashInfor(Throwable throwable) {
        Observable.just(throwable)
                .map(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) throws Exception {

                        StringBuilder builder = new StringBuilder();
                        builder.append(getSysytemInfor())
                                .append("\n")
                                .append("ErrorInformation:")
                                .append(throwable.getMessage());

                        StackTraceElement[] stackTrace = throwable.getStackTrace();
                        try {
                            for (int i = 0; i < stackTrace.length; i++) {
                                StackTraceElement stackTraceElement = stackTrace[i];
                                String trace = stackTraceElement.toString();
                                builder.append(trace).append("\n");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return builder.toString();
                    }
                })
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String s) throws Exception {
                        File file = new File(PATH_LOGCAT, System.currentTimeMillis() + ".txt");
                        writeToFile(file, s);
                        return file;
                    }
                })
                .compose(RxUtils.<File>getSchedulersObservableTransformer())
                .subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(File file) {
                        JLog.i("储存异常情况完成" + file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    //把错误信息填充进崩溃文件中
    public void writeToFile(File file, String crashInfor) {
        PrintWriter printer = null;
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, true));
            printer = new PrintWriter(out);
            printer.println(crashInfor);
            printer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printer != null) {
                printer.close();
            }
        }
    }

    public File[] getLogInforFiles() {
        if (PATH_LOGCAT != null && new File(PATH_LOGCAT).isDirectory()) {
            return new File(PATH_LOGCAT).listFiles();
        } else {
            return null;
        }
    }


    public String readFile(File file) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String readline = "";
            while ((readline = br.readLine()) != null) {
                sb.append(readline);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sb.toString();
    }

    //获取手机的一些设备参数
    private static String getSysytemInfor() {
        StringBuffer sb = new StringBuffer();
        sb.append("主板：" + Build.BOARD + "\n");
        sb.append("系统启动程序版本号：" + Build.BOOTLOADER + "\n");
        sb.append("系统定制商：" + Build.BRAND + "\n");
        sb.append("cpu指令集：" + Build.CPU_ABI + "\n");
        sb.append("cpu指令集2：" + Build.CPU_ABI2 + "\n");
        sb.append("设置参数：" + Build.DEVICE + "\n");
        sb.append("显示屏参数：" + Build.DISPLAY + "\n");
        sb.append("无线电固件版本：" + Build.getRadioVersion() + "\n");
        sb.append("硬件识别码：" + Build.FINGERPRINT + "\n");
        sb.append("硬件名称：" + Build.HARDWARE + "\n");
        sb.append("HOST:" + Build.HOST + "\n");
        sb.append("修订版本列表：" + Build.ID + "\n");
        sb.append("硬件制造商：" + Build.MANUFACTURER + "\n");
        sb.append("版本：" + Build.MODEL + "\n");
        sb.append("硬件序列号：" + Build.SERIAL + "\n");
        sb.append("手机制造商：" + Build.PRODUCT + "\n");
        sb.append("描述Build的标签：" + Build.TAGS + "\n");
        sb.append("TIME:" + Build.TIME + "\n");
        sb.append("builder类型：" + Build.TYPE + "\n");
        sb.append("USER:" + Build.USER + "\n");
        return sb.toString();
    }

}
