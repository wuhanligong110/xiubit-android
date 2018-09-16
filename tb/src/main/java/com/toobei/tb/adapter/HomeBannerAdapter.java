package com.toobei.tb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.HomePagerBanners;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.view.RemoteImageView;
import com.toobei.common.view.RippleView;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.WebActivityCommon;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18 0018.
 */

public class HomeBannerAdapter extends PagerAdapter {


    int bannerType;
    Context context;

    public HomeBannerAdapter(Context context, List<HomePagerBanners> bannerList, int bannerType) {
        this.bannerType = bannerType;
        this.context = context;
        this.bannerList = bannerList;
    }

    private List<HomePagerBanners> bannerList;

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (bannerList == null) return 0;
       // return bannerList.size();
        return Integer.MAX_VALUE/2;
    }

    public List<HomePagerBanners> getItemList() {
        return bannerList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

         //点击水波纹
        RippleView item = new RippleView(context);
        RemoteImageView iv = new RemoteImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);

        String orgActivityAdvertise = bannerList.get(position%bannerList.size()).getImgUrl();
        if (!orgActivityAdvertise.contains("http")) {
            orgActivityAdvertise = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + orgActivityAdvertise;
        }
        iv.setImageUri(ImageDefault.RECTANGLE_LAND_BANER, orgActivityAdvertise);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        item.addView(iv, params);
        iv.setOnClickListener(new MyOnClickListener(context, bannerList.get(position%bannerList.size()), position%bannerList.size() + 1)); //点击的bannner位置
        container.addView(item);
        return item;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    interface ImageDefault {
        int RECTANGLE_DEFAULT = R.drawable.img_empty_default;
        int RECTANGLE_LAND_BANER = R.drawable.empty_photo;
    }

    class MyOnClickListener implements View.OnClickListener {
        int bannerPosition;
        HomePagerBanners banner;
        TopBaseActivity context;

        public MyOnClickListener(Context context, HomePagerBanners banner, int position) {
            this.banner = banner;
            this.context = (TopBaseActivity) context;
            this.bannerPosition = position;
        }

        @Override
        public void onClick(View v) {

            if (bannerType == BannerType.PRODUCT_BANNER_TYPE) {
//                HashMap<String, String> map0 = new HashMap<String, String>();
//                map0.put(context.getString(R.string.umeng_product_home_key), context.getString(R.string.umeng_product_banner) +bannerPosition);
//                MobclickAgent.onEvent(context, "product_module", map0);
            } else if(bannerType == BannerType.INSTITUTUON_BANNER_TYPE){
//                HashMap<String, String> map0 = new HashMap<String, String>();
//                map0.put(context.getString(R.string.umeng_institution_key), context.getString(R.string.umeng_institution_banner) +bannerPosition);
//                MobclickAgent.onEvent(context, "platform_module", map0);

            }


            Intent intent = new Intent(context, WebActivityCommon.class);
            intent.putExtra("url", banner.getLinkUrl());
            intent.putExtra("title", banner.getShareTitle());
            intent.putExtra("shareContent", new ShareContent(banner.getShareTitle(), banner.getShareDesc(), banner.getLinkUrl(), banner.getShareIcon()));
            context.showActivity(context, intent);
        }


    }

    public interface BannerType {
        int PRODUCT_BANNER_TYPE = 01;
        int INSTITUTUON_BANNER_TYPE = 02;
    }
};