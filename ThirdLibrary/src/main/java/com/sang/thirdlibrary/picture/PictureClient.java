package com.sang.thirdlibrary.picture;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

/**
 * 作者：  on 2020/3/2.
 */
public class PictureClient {

    private long maxSize = 5 * 1024;

    public PictureClient() {
    }




    /**
     * 打开相册
     *  @param list     已选图片
     * @param listener
     */
    public void startPhoto(Activity activity, int count, List<LocalMedia> list, OnResultCallbackListener listener) {
        getClient(activity)
                .maxSelectNum(count)// 最大图片选择数量 int
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .forResult(  listener);

        ;
    }

    /**
     * 打开相册
     *  @param list     已选图片
     * @param requestCode
     */
    public void startPhoto(Activity activity, int count, List<LocalMedia> list, int requestCode) {
        getClient(activity)
                .maxSelectNum(count)// 最大图片选择数量 int
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .forResult(  requestCode);

        ;
    }

    /**
     * 打开相册
     *
     * @param count 可选图片数量
     * @param list  已选图片
     */
    public void startPhoto(Fragment activity, int count, List<LocalMedia> list, OnResultCallbackListener listener) {
        getClient(activity)
                .maxSelectNum(count)// 最大图片选择数量 int
                .selectionMedia(list)// 是否传入已选图片 List<LocalMedia> list
                .forResult(  listener)
        ;
    }

    /**
     * 打开相机
     */
    public void startCamera(Fragment activity) {
        getClient(activity)
                .maxSelectNum(1)// 最大图片选择数量 int

        ;
    }

    /**
     * 打开相机
     */
    public void startCamera(Activity activity) {
        getClient(activity)
                .maxSelectNum(1)// 最大图片选择数量 int

        ;
    }


    private PictureSelectionModel getClient(Fragment fragment) {
        return config(PictureSelector.create(fragment));

    }

    private PictureSelectionModel getClient(Activity activity) {
        return config(PictureSelector.create(activity));
    }


    private PictureSelectionModel config(PictureSelector selector) {
        return selector.openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                .theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
//                .setPictureStyle()// 动态自定义相册主题  注意：此方法最好不要与.theme();同时存在， 二选一
//                .setPictureCropStyle()// 动态自定义裁剪主题
//                .setPictureWindowAnimationStyle()// 自定义相册启动退出动画
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项   参考Demo MainActivity中代码
//                .isWithVideoImage(false)// 图片和视频是否可以同选,只在ofAll模式下有效
                .isUseCustomCamera(false)// 是否使用自定义相机，5.0以下请不要使用，可能会出现兼容性问题
//                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果用户勾选了 压缩、裁剪功能将会失效
//                .isWeChatStyle()// 是否开启微信图片选择风格，此开关开启了才可使用微信主题！！！
                .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对 compress(false); && enableCrop(false);有效
//                .bindCustomPlayVideoCallback(callback)// 自定义播放回调控制，用户可以使用自己的视频播放界面
                .isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
                .isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
//                .setLanguage(language)// 设置语言，默认中文
                .minSelectNum(1)// 最小选择数量 int
//                .minVideoSelectNum(1)// 视频最小选择数量，如果没有单独设置的需求则可以不设置，同用minSelectNum字段
//                .maxVideoSelectNum(1) // 视频最大选择数量，如果没有单独设置的需求则可以不设置，同用maxSelectNum字段
                .imageSpanCount(4)// 每行显示个数 int
                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
//                .queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
//                .querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
//                .cameraFileName("test.png") // 重命名拍照文件名、注意这个只在使用相机时可以使用
//                .renameCompressFile("test.png")// 重命名压缩文件名、 注意这个不要重复，只适用于单张图压缩使用
//                .renameCropFileName("test.png")// 重命名裁剪文件名、 注意这个不要重复，只适用于单张图裁剪使用
                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
//                .setTitleBarBackgroundColor()//相册标题栏背景色
//                .isChangeStatusBarFontColor()// 是否关闭白色状态栏字体颜色
//                .setStatusBarColorPrimaryDark()// 状态栏背景色
//                .setUpArrowDrawable()// 设置标题栏右侧箭头图标
//                .setDownArrowDrawable()// 设置标题栏右侧箭头图标
                .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 相机 true or false
//                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
//                .glideOverride()// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .openClickSound()// 是否开启点击声音 true or false
                .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality(90)// 废弃 改用cutOutQuality()
//                .cutOutQuality(90)// 裁剪输出质量 默认100
//                .cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                ;
//                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        //.forResult(OnResultCallbackListener listener);//Callback回调方式
    }
}
