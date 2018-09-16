package com.toobei.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.toobei.common.R;
import com.toobei.common.utils.PhotoUtil;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * @author LuDaiqian
 */
@SuppressLint({"NewApi", "AppCompatCustomView"})
public class RemoteImageView extends ImageView {

    private Context context;
    /**
     * 网络图片url
     */
    private String mUrl;
    protected DisplayImageOptions mOptions;
    protected ImageLoader mImageLoader;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private Integer mDefaultImage = R.drawable.img_empty_default;
    private BitmapProcessor preProcessor;
    private BitmapProcessor postProcessor;
    // private boolean cacheEnable = true;
    static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

    private class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                // System.out.println("width:"+loadedImage.getWidth()+",height:"+loadedImage.getHeight());
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
                if (imageLoadedListener != null)
                    imageLoadedListener.onImageLoaded(loadedImage);

            }

        }
    }

    public interface ImageLoadedListener {
        void onImageLoaded(Bitmap bitmap);
    }

    private ImageLoadedListener imageLoadedListener;

    public void setImageLoadedListener(ImageLoadedListener imageLoadedListener) {
        this.imageLoadedListener = imageLoadedListener;
    }

    public String getImageUri() {
        return mUrl;
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RemoteImageView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    @SuppressLint("NewApi")
    public void init() {
        mImageLoader = ImageLoader.getInstance();
        if (context != null) {
            File cacheDir = com.toobei.common.utils.PathUtils.getCacheFileDir();
            ImageLoaderConfiguration config = PhotoUtil.getImageLoaderConfig(context, cacheDir);
            mImageLoader.init(config);
        }
        buildingOptions();
    }

    private void buildingOptions() {
        // if (cacheEnable) {
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        builder.showImageOnFail(mDefaultImage).showImageOnLoading(mDefaultImage).showImageForEmptyUri(mDefaultImage)
                .cacheInMemory(true).cacheOnDisk(true);
        builder.preProcessor(preProcessor);
        builder.postProcessor(postProcessor);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        builder.resetViewBeforeLoading(true);
        mOptions = builder.build();
        // } else {
        // mOptions = new
        // DisplayImageOptions.Builder().showImageOnLoading(mDefaultImage).showImageForEmptyUri(mDefaultImage)
        // .showImageOnFail(mDefaultImage).bitmapConfig(Bitmap.Config.RGB_565).preProcessor(preProcessor)
        // .postProcessor(postProcessor).resetViewBeforeLoading(true).build();
        // }
    }

    /**
     * 加载网络图片
     *
     * @param url eg. http://yourwebsite.com/abz.jpg
     */
    public void setImageUri(String url) {
        mUrl = url;
        if (!TextUtils.isEmpty(url)) {
            // setImageResource(mDefaultImage);
            mImageLoader.displayImage(url, this, mOptions, animateFirstListener);
        } else {
            setImageResource(mDefaultImage);

        }
    }

    /**
     * 加载网络图片
     *
     * @param url eg. http://yourwebsite.com/abz.jpg
     */
    public void setImageUri(String url, final int maxWidth, final int maxHeight) {
        mUrl = url;
        if (!TextUtils.isEmpty(url)) {
            // setImageResource(mDefaultImage);
            ImageViewAware aware = new ImageViewAware(this) {
                @Override
                public int getHeight() {
                    int height = super.getHeight();
                    if (height <= 0)
                        return maxHeight;
                    return Math.min(maxHeight, height);
                }

                @Override
                public int getWidth() {
                    int width = super.getWidth();
                    if (width <= 0)
                        return maxWidth;
                    return Math.min(maxWidth, width);
                }
            };
            mImageLoader.displayImage(url, aware, mOptions, animateFirstListener);
        } else {
            setImageResource(mDefaultImage);

        }
    }

    // public void setDefaultImageResource(Integer resId, boolean cacheEnable) {
    // this.cacheEnable = cacheEnable;
    // mDefaultImage = resId;
    // super.setImageResource(resId);
    // buildingOptions();
    // }

    /**
     * 设置默认图片资源id
     *
     * @param resid
     */
    public void setDefaultImageResource(Integer resId) {
        mDefaultImage = resId;
        super.setImageResource(resId);
        buildingOptions();
    }

    public void setImageUri(Integer defResId, String url) {
        if (mUrl == null || !this.mUrl.equals(url)) {
            setDefaultImageResource(defResId);
            setImageUri(url);
        }
    }

    // @Override
    // public void setImageResource(int resId) {
    // super.setImageResource(resId);
    // mDefaultImage = resId;
    // buildingOptions();
    // }

    public void setPreProcessor(BitmapProcessor preProcessor) {
        this.preProcessor = preProcessor;
        buildingOptions();
    }

    public void setPostProcessor(BitmapProcessor postProcessor) {
        this.postProcessor = postProcessor;
        buildingOptions();
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
