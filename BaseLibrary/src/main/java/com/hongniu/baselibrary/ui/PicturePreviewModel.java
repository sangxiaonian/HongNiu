//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hongniu.baselibrary.ui;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelectorActivity;
import com.luck.picture.lib.R.anim;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import java.util.ArrayList;
import java.util.List;

public class PicturePreviewModel {
    private PictureSelectionConfig selectionConfig;
    private PicturePreview selector;

    public PicturePreviewModel(PicturePreview selector, int mimeType) {
        this.selector = selector;
        this.selectionConfig = PictureSelectionConfig.getCleanInstance();
        this.selectionConfig.mimeType = mimeType;
    }

    public PicturePreviewModel(PicturePreview selector, int mimeType, boolean camera) {
        this.selector = selector;
        this.selectionConfig = PictureSelectionConfig.getCleanInstance();
        this.selectionConfig.camera = camera;
        this.selectionConfig.mimeType = mimeType;
    }

    public PicturePreviewModel theme(@StyleRes int themeStyleId) {
        this.selectionConfig.themeStyleId = themeStyleId;
        return this;
    }

    public PicturePreviewModel selectionMode(int selectionMode) {
        this.selectionConfig.selectionMode = selectionMode;
        return this;
    }

    public PicturePreviewModel enableCrop(boolean enableCrop) {
        this.selectionConfig.enableCrop = enableCrop;
        return this;
    }

    public PicturePreviewModel enablePreviewAudio(boolean enablePreviewAudio) {
        this.selectionConfig.enablePreviewAudio = enablePreviewAudio;
        return this;
    }

    public PicturePreviewModel freeStyleCropEnabled(boolean freeStyleCropEnabled) {
        this.selectionConfig.freeStyleCropEnabled = freeStyleCropEnabled;
        return this;
    }

    public PicturePreviewModel scaleEnabled(boolean scaleEnabled) {
        this.selectionConfig.scaleEnabled = scaleEnabled;
        return this;
    }

    public PicturePreviewModel rotateEnabled(boolean rotateEnabled) {
        this.selectionConfig.rotateEnabled = rotateEnabled;
        return this;
    }

    public PicturePreviewModel circleDimmedLayer(boolean circleDimmedLayer) {
        this.selectionConfig.circleDimmedLayer = circleDimmedLayer;
        return this;
    }

    public PicturePreviewModel showCropFrame(boolean showCropFrame) {
        this.selectionConfig.showCropFrame = showCropFrame;
        return this;
    }

    public PicturePreviewModel showCropGrid(boolean showCropGrid) {
        this.selectionConfig.showCropGrid = showCropGrid;
        return this;
    }

    public PicturePreviewModel hideBottomControls(boolean hideBottomControls) {
        this.selectionConfig.hideBottomControls = hideBottomControls;
        return this;
    }

    public PicturePreviewModel withAspectRatio(int aspect_ratio_x, int aspect_ratio_y) {
        this.selectionConfig.aspect_ratio_x = aspect_ratio_x;
        this.selectionConfig.aspect_ratio_y = aspect_ratio_y;
        return this;
    }

    public PicturePreviewModel maxSelectNum(int maxSelectNum) {
        this.selectionConfig.maxSelectNum = maxSelectNum;
        return this;
    }

    public PicturePreviewModel minSelectNum(int minSelectNum) {
        this.selectionConfig.minSelectNum = minSelectNum;
        return this;
    }

    public PicturePreviewModel videoQuality(int videoQuality) {
        this.selectionConfig.videoQuality = videoQuality;
        return this;
    }

    public PicturePreviewModel imageFormat(String suffixType) {
        this.selectionConfig.suffixType = suffixType;
        return this;
    }

    public PicturePreviewModel cropWH(int cropWidth, int cropHeight) {
        this.selectionConfig.cropWidth = cropWidth;
        this.selectionConfig.cropHeight = cropHeight;
        return this;
    }

    public PicturePreviewModel videoMaxSecond(int videoMaxSecond) {
        this.selectionConfig.videoMaxSecond = videoMaxSecond * 1000;
        return this;
    }

    public PicturePreviewModel videoMinSecond(int videoMinSecond) {
        this.selectionConfig.videoMinSecond = videoMinSecond * 1000;
        return this;
    }

    public PicturePreviewModel recordVideoSecond(int recordVideoSecond) {
        this.selectionConfig.recordVideoSecond = recordVideoSecond;
        return this;
    }

    public PicturePreviewModel glideOverride(@IntRange(from = 100L) int width, @IntRange(from = 100L) int height) {
        this.selectionConfig.overrideWidth = width;
        this.selectionConfig.overrideHeight = height;
        return this;
    }

    public PicturePreviewModel sizeMultiplier(@FloatRange(from = 0.10000000149011612D) float sizeMultiplier) {
        this.selectionConfig.sizeMultiplier = sizeMultiplier;
        return this;
    }

    public PicturePreviewModel imageSpanCount(int imageSpanCount) {
        this.selectionConfig.imageSpanCount = imageSpanCount;
        return this;
    }

    public PicturePreviewModel minimumCompressSize(int size) {
        this.selectionConfig.minimumCompressSize = size;
        return this;
    }

    public PicturePreviewModel cropCompressQuality(int compressQuality) {
        this.selectionConfig.cropCompressQuality = compressQuality;
        return this;
    }

    public PicturePreviewModel compress(boolean isCompress) {
        this.selectionConfig.isCompress = isCompress;
        return this;
    }

    public PicturePreviewModel synOrAsy(boolean synOrAsy) {
        this.selectionConfig.synOrAsy = synOrAsy;
        return this;
    }

    public PicturePreviewModel compressSavePath(String path) {
        this.selectionConfig.compressSavePath = path;
        return this;
    }

    public PicturePreviewModel isZoomAnim(boolean zoomAnim) {
        this.selectionConfig.zoomAnim = zoomAnim;
        return this;
    }

    public PicturePreviewModel previewEggs(boolean previewEggs) {
        this.selectionConfig.previewEggs = previewEggs;
        return this;
    }

    public PicturePreviewModel isCamera(boolean isCamera) {
        this.selectionConfig.isCamera = isCamera;
        return this;
    }

    public PicturePreviewModel setOutputCameraPath(String outputCameraPath) {
        this.selectionConfig.outputCameraPath = outputCameraPath;
        return this;
    }

    public PicturePreviewModel isGif(boolean isGif) {
        this.selectionConfig.isGif = isGif;
        return this;
    }

    public PicturePreviewModel previewImage(boolean enablePreview) {
        this.selectionConfig.enablePreview = enablePreview;
        return this;
    }

    public PicturePreviewModel previewVideo(boolean enPreviewVideo) {
        this.selectionConfig.enPreviewVideo = enPreviewVideo;
        return this;
    }

    public PicturePreviewModel openClickSound(boolean openClickSound) {
        this.selectionConfig.openClickSound = openClickSound;
        return this;
    }

    public PicturePreviewModel isDragFrame(boolean isDragFrame) {
        this.selectionConfig.isDragFrame = isDragFrame;
        return this;
    }

    public PicturePreviewModel selectionMedia(List<LocalMedia> selectionMedia) {
        if (selectionMedia == null) {
            selectionMedia = new ArrayList();
        }

        this.selectionConfig.selectionMedias = (List)selectionMedia;
        return this;
    }

    public void forResult(int requestCode) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Activity activity = this.selector.getActivity();
            if (activity == null) {
                return;
            }

            Intent intent = new Intent(activity, PictureSelectorActivity.class);
            Fragment fragment = this.selector.getFragment();
            if (fragment != null) {
                fragment.startActivityForResult(intent, requestCode);
            } else {
                activity.startActivityForResult(intent, requestCode);
            }

            activity.overridePendingTransition(anim.a5, 0);
        }

    }

    public void openExternalPreview(int position, List<LocalMedia> medias) {
        if (this.selector != null) {
            this.selector.externalPicturePreview(position, medias);
        } else {
            throw new NullPointerException("This PictureSelector is Null");
        }
    }

    public void openExternalPreview(int position, String directory_path, List<LocalMedia> medias) {
        if (this.selector != null) {
            this.selector.externalPicturePreview(position, directory_path, medias);
        } else {
            throw new NullPointerException("This PictureSelector is Null");
        }
    }
}
