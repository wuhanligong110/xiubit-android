package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明: 图片放大预览
 *
 * @author hasee-pc
 * @time 2017/7/6
 */

public class PreviewImageActivity extends MyTitleBaseActivity {
    public final static String SHARED_ELEMENTS_IMG = "shared_elements_img";
    @BindView(R.id.photoView)
    PhotoView mPhotoView;
    private String mImageUrl;
    private String mTitle;
    private float mInitScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mImageUrl = getIntent().getStringExtra("imageUrl");
        mTitle = getIntent().getStringExtra("title");
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        headerLayout.setVisibility(View.GONE);
        if (!mImageUrl.contains("http")){
            mImageUrl = MyApp.getInstance().getHttpService().getImageServerBaseUrl()+ mImageUrl;
        }
        Glide.with(this).load(mImageUrl).into(mPhotoView);
        final PhotoViewAttacher attacher = mPhotoView.getAttacher();
        mInitScale = attacher.getScale();
        attacher.setZoomable(true);
        attacher.setScaleLevels(0.3f, mInitScale,1.5f);
        ViewCompat.setTransitionName(mPhotoView, SHARED_ELEMENTS_IMG);
        attacher.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                onBackPressed();
            }
        });
        
    }
    
    
    @Override
    protected int getContentLayout() {
        
        return R.layout.activity_preview_image;
    }
}
