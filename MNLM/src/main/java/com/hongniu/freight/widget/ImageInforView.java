package com.hongniu.freight.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fy.androidlibrary.imgload.ImageLoader;
import com.fy.androidlibrary.net.rx.BaseObserver;
import com.fy.androidlibrary.utils.CollectionUtils;
import com.hongniu.freight.R;
import com.hongniu.freight.entity.UpImgData;
import com.hongniu.freight.net.HttpAppFactory;
import com.sang.thirdlibrary.picture.PictureClient;
import com.sang.thirdlibrary.picture.utils.PicUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者： PING
 * 日期： 2020/3/29.
 */
public class ImageInforView extends LinearLayout implements View.OnClickListener, OnResultCallbackListener {

    private ImageView img;
    private View bt_img;
    private OnClickListener listener;
    private UpLoadFinishListener upLoadFinishListener;

    private int state;//0 未上传图片 1正在上传 2上传失败 3 上传成功
    private int type;//图片类型
    private Fragment fragment;
    private Activity activity;


    public ImageInforView(Context context) {
        this(context, null);
    }

    public ImageInforView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageInforView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mnlm_item_image_camera, this, false);
        img = inflate.findViewById(R.id.img);
        bt_img = inflate.findViewById(R.id.bt_img);
        addView(inflate);
        bt_img.setOnClickListener(this);
        bt_img.setVisibility(GONE);
        setImageInfo(null);

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.listener = l;
    }

    public void setUpLoadFinishListener(UpLoadFinishListener upLoadFinishListener) {
        this.upLoadFinishListener = upLoadFinishListener;
    }

    /**
     * 设置图片
     *
     * @param image
     */
    public void setImageInfo(String image) {
        setImageInfo(image, false);
    }

    /**
     * 设置图片
     *
     * @param image
     */
    public void setImageInfo(String image, boolean upLoad) {
        if (image == null) {
            ImageLoader.getLoader().load(getContext(), this.img, new ColorDrawable(getResources().getColor(R.color.color_of_f6f5f8)));
        } else {
            ImageLoader.getLoader().load(getContext(), this.img, image);
            if (upLoad) {
                upLoadImage(image);
            }
        }
        bt_img.setVisibility(isEnabled() ? VISIBLE : GONE);
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 获取图片上传状态
     *
     * @return 0 未上传图片 1正在上传 2上传失败 3 上传成功
     */
    public int getState() {
        return state;
    }

    private Disposable disposable;
    private UpImgData imgData;

    public UpImgData getImgInfo() {
        return imgData;
    }

    private void upLoadImage(String path) {
        if (TextUtils.isEmpty(path) || path.startsWith("http")) {
            state = 0;
        } else {
            HttpAppFactory.upImage(type, path, null)
                    .subscribe(new BaseObserver<UpImgData>(null) {

                        @Override
                        public void onSubscribe(Disposable d) {
                            super.onSubscribe(d);
                            disposable = d;
                            state = 1;
                            imgData=null;
                        }

                        @Override
                        public void onNext(UpImgData result) {
                            super.onNext(result);
                            state = 3;
                            imgData=result;
                            if (upLoadFinishListener!=null){
                                upLoadFinishListener.onLoadFinish();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                            state = 2;
                            if (upLoadFinishListener!=null){
                                upLoadFinishListener.onLoadFinish();
                            }
                        }
                    });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            bt_img.setVisibility(GONE);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (isEnabled()) {
            if (listener != null) {
                listener.onClick(this);
            } else if (activity != null) {
                new PictureClient()
                        .startPhoto(activity, 1, null, this);
            } else if (fragment != null) {
                new PictureClient()
                        .startPhoto(fragment, 1, null, this);
            }
        }
    }


    public void setAttached(Activity activity) {
        this.activity = activity;

    }

    public void setAttached(Fragment fragment) {
        this.fragment = fragment;
    }


    /**
     * return LocalMedia result
     *
     * @param result
     */
    @Override
    public void onResult(List<LocalMedia> result) {
        if (!CollectionUtils.isEmpty(result)) {
            setImageInfo(PicUtils.getPath(result.get(0)), true);
        }
    }

    public interface UpLoadFinishListener{
        void onLoadFinish();
    }
}
