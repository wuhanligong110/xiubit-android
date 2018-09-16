package com.toobei.tb.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.view.photoview.PhotoView;
import com.toobei.tb.R;

import java.util.ArrayList;

/**
 * 类说明: 用于在viewPager中显示图片的activity 支持图片缩放
 *
 * @author Administrator
 * @time 2016/11/9 0009 下午 5:49
 */
public class ViewPagerActivity extends Activity {

    private ViewPager mPager;
    private ArrayList<String> imgUrlstrings;
    private int imgPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imgUrlstrings = getIntent().getStringArrayListExtra("imgUrlstrings");
        imgPosition = getIntent().getIntExtra("imgPosition", 0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        int size = imgUrlstrings.size();
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (imgUrlstrings == null) return 0;
                return imgUrlstrings.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                PhotoView view = new PhotoView(ViewPagerActivity.this);
                view.enable();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && imgPosition == position) {
//                    view.setTransitionName(getString(R.string.transition_scale_photoview));
                    ViewCompat.setTransitionName(view, getString(R.string.transition_scale_photoview));
                }

                view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                String imgUrl = imgUrlstrings.get(position);

                //  PhotoUtil.loadImageByImageLoader(ViewPagerActivity.this,imgUrl,view);
                PhotoUtil.loadImageByGlide(ViewPagerActivity.this, imgUrl, view);
                container.addView(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2016/11/24  这里不能调用finish  不然转场动画不是设定的过度动画
                        onBackPressed();
                    }
                });
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        mPager.setCurrentItem(imgPosition);

    }
}
