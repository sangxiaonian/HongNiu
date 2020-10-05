package com.sang.thirdlibrary.picture.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fy.androidlibrary.widget.recycle.adapter.XAdapter;
import com.fy.androidlibrary.widget.recycle.control.RecycleControl;
import com.fy.androidlibrary.widget.recycle.holder.BaseHolder;
import com.fy.androidlibrary.widget.recycle.utils.XAdapterDataObserver;
import com.sang.thirdlibrary.picture.ImageInforBean;
import com.sang.thirdlibrary.picture.holder.ImageAddHolder;
import com.sang.thirdlibrary.picture.holder.ImageInforHolder;

import java.util.List;

/**
 * 作者： ${PING} on 2018/11/13.
 * <p>
 * 图片上传的Adapter
 */
public class ImageAdapter extends XAdapter<ImageInforBean> implements ImageAddHolder.OnAddPicturesListener {

    RecyclerView.AdapterDataObserver observer = new XAdapterDataObserver() {
        @Override
        public void onChanged() {
            if (imageAddHolder != null && list != null) {
                if (list.size() >= maxCount && getFoots().contains(imageAddHolder)) {
                    removeFoot(imageAddHolder);
                } else if (list.size() < maxCount && !getFoots().contains(imageAddHolder)) {
                    if (!isHideAdd) {
                        addFoot(imageAddHolder);
                    }
                }
            }

        }
    };


    RecycleControl.OnItemClickListener<ImageInforBean> itemClickListener;


    private boolean showLable = true;
    private ImageAddHolder imageAddHolder;
    private int maxCount;//最大图片数量
    private ImageAddHolder.OnAddPicturesListener addPicturesListener;
    DealFailImageListener dealFailImageListener;
    private boolean isHideAdd;


    public ImageAdapter(Context context, List<ImageInforBean> list) {
        super(context, list);
        registerAdapterDataObserver(observer);

    }
    public void hideAdd(boolean isHideAdd){
        this.isHideAdd=isHideAdd;
        if (isHideAdd) {
            if(getFoots().contains(imageAddHolder)){
                getFoots().remove(imageAddHolder);
            }
        }
    }

    public void setDealFailImageListener(DealFailImageListener dealFailImageListener) {
        this.dealFailImageListener = dealFailImageListener;
    }

    public void setonAddPicturesListener(ImageAddHolder.OnAddPicturesListener addPicturesListener) {
        this.addPicturesListener = addPicturesListener;
    }

    public void setShowLable(boolean showLable) {
        this.showLable = showLable;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        getFoots().clear();
        imageAddHolder = new ImageAddHolder(context, recyclerView);
        imageAddHolder.setonAddPicturesListener(this);
        if (!isHideAdd) {
            addFoot(imageAddHolder);
        }
    }

    @Override
    public BaseHolder<ImageInforBean> initHolder(ViewGroup parent, int viewType) {
        ImageInforHolder imageInforHolder = new ImageInforHolder(context, parent);
        imageInforHolder.setItemClickListener(itemClickListener);
        imageInforHolder.setDealFailImageListener(dealFailImageListener);
        imageInforHolder.setShowLable(showLable);
        return imageInforHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List payloads) {
        if (payloads.isEmpty()) {
            // payloads 为 空，说明是更新整个 ViewHolder
            onBindViewHolder(holder, position);
        } else {
            // payloads 不为空，这只更新需要更新的 View 即可。
            if (holder instanceof ImageInforHolder && payloads.get(0) instanceof ImageInforBean) {
                ImageInforHolder inforHolder = (ImageInforHolder) holder;
                inforHolder.setProgress(((ImageInforBean) payloads.get(0)));
            }
        }

    }


    public void setItemClickListener(RecycleControl.OnItemClickListener<ImageInforBean> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    /**
     * 设置最大数据
     *
     * @param maxCount
     */
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    @Override
    public void onClickAdd() {
        if (addPicturesListener!=null){
            addPicturesListener.onClickAdd();
        }
    }


    /**
     * 处理长传失败的数据
     */
    public interface DealFailImageListener {
        /**
         * 重新上传指定图片
         *
         * @param position
         * @param failBean 上传失败图片的信息
         */
        void upRepeat(int position, ImageInforBean failBean);

        /**
         * 取消指定图片
         *
         * @param position
         * @param failBean
         */
        void onClickCancle(int position, ImageInforBean failBean);


    }

}
