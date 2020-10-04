package com.hongniu.baselibrary.ui;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureExternalPreviewActivity;
import com.luck.picture.lib.R.anim;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public final class PicturePreview {
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private PicturePreview(Activity activity) {
        this(activity, (Fragment) null);
    }

    private PicturePreview(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private PicturePreview(Activity activity, Fragment fragment) {
        this.mActivity = new WeakReference(activity);
        this.mFragment = new WeakReference(fragment);
    }

    public static PicturePreview create(Activity activity) {
        return new PicturePreview(activity);
    }

    public static PicturePreview create(Fragment fragment) {
        return new PicturePreview(fragment);
    }

    public PicturePreviewModel openGallery(int mimeType) {
        return new PicturePreviewModel(this, mimeType);
    }

    public PicturePreviewModel openCamera(int mimeType) {
        return new PicturePreviewModel(this, mimeType, true);
    }

    public PicturePreviewModel themeStyle(int themeStyle) {
        return (new PicturePreviewModel(this, PictureMimeType.ofImage())).theme(themeStyle);
    }

    public static List<LocalMedia> obtainMultipleResult(Intent data) {
        List<LocalMedia> result = new ArrayList();
        if (data != null) {
            result = (List) data.getSerializableExtra("extra_result_media");
            if (result == null) {
                result = new ArrayList();
            }

            return (List) result;
        } else {
            return result;
        }
    }

    public static Intent putIntentResult(List<LocalMedia> data) {
        return (new Intent()).putExtra("extra_result_media", (Serializable) data);
    }

    public static List<LocalMedia> obtainSelectorList(Bundle bundle) {
        if (bundle != null) {
            List<LocalMedia> selectionMedias = (List) bundle.getSerializable("selectList");
            return selectionMedias;
        } else {
            List<LocalMedia> selectionMedias = new ArrayList();
            return selectionMedias;
        }
    }

    public static void saveSelectorList(Bundle outState, List<LocalMedia> selectedImages) {
        outState.putSerializable("selectList", (Serializable) selectedImages);
    }

    public void externalPicturePreview(int position, List<LocalMedia> medias) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(this.getActivity(), PictureExternalPreviewActivity.class);
            intent.putExtra("previewSelectList", (Serializable) medias);
            intent.putExtra("position", position);
            this.getActivity().startActivity(intent);
            this.getActivity().overridePendingTransition(anim.a5, 0);
        }

    }

    public void externalPicturePreview(int position, String directory_path, List<LocalMedia> medias) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(this.getActivity(), PictureExternalPreviewActivity.class);
            intent.putExtra("previewSelectList", (Serializable) medias);
            intent.putExtra("position", position);
            intent.putExtra("directory_path", directory_path);
            this.getActivity().startActivity(intent);
            this.getActivity().overridePendingTransition(anim.a5, 0);
        }

    }


    @Nullable
    Activity getActivity() {
        return (Activity) this.mActivity.get();
    }

    @Nullable
    Fragment getFragment() {
        return this.mFragment != null ? (Fragment) this.mFragment.get() : null;
    }
}
