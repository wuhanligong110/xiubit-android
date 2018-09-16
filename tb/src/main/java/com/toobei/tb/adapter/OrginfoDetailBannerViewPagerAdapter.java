package com.toobei.tb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.view.RemoteImageView;
import com.toobei.common.view.RippleView;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.entity.PlatformDetail;

import java.util.List;


/**
 * Created by Administrator on 2016/10/18 0018.
 */

public class OrginfoDetailBannerViewPagerAdapter extends PagerAdapter {


    private  String orgName;
    Context context;

    public OrginfoDetailBannerViewPagerAdapter(Context context, List<PlatformDetail.OrgActivitysBean> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    /**
     *
     * @param context ..
     * @param bannerList ..
     * @param orgName ..用于友盟统计
     */
    public OrginfoDetailBannerViewPagerAdapter(Context context, List<PlatformDetail.OrgActivitysBean> bannerList, String orgName) {
        this.context = context;
        this.bannerList = bannerList;
        this.orgName=orgName;
    }

    private List<PlatformDetail.OrgActivitysBean> bannerList;




    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (bannerList == null) return 0;
        return bannerList.size();
    }

    public List<PlatformDetail.OrgActivitysBean> getItemList() {
        return bannerList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        RippleView item = new RippleView(context);
        RemoteImageView iv = new RemoteImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        // iv.setPostProcessor(new
        // CenterClipRoundImageProcessor(0,720f/250f));
        String imageUrl = bannerList.get(position).getPlatformImg();
        if (!imageUrl.contains("http")) {
            imageUrl = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + imageUrl;
        }
        iv.setImageUri(ImageDefault.RECTANGLE_LAND_BANER, imageUrl);
//        item.setOnRippleCompleteListener(new OnRippleCompleteListener() {
//
//            @Override
//            public void onComplete(View rippleView) {
//                if (onPagerItemClickListener != null)
//                    onPagerItemClickListener.onPagerItemClick(position);
//            }
//        });
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        item.addView(iv, params);
        iv.setOnClickListener(new MyOnClickListener(context, bannerList.get(position),position));
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
        PlatformDetail.OrgActivitysBean banner;
        TopBaseActivity context;

        public MyOnClickListener(Context context, PlatformDetail.OrgActivitysBean banner, int position) {
            this.banner = banner;
            this.bannerPosition=position+1;
            this.context = (TopBaseActivity) context;
        }

        @Override
        public void onClick(View v) {
//            HashMap<String, String> map = new HashMap<String, String>();
//            map.put(context.getString(R.string.umeng_institution_detail_key), context.getString(R.string.umeng_institution_detail_activitybanner)+ " : "+orgName+ ": "+ banner.getShareTitle());
//            MobclickAgent.onEvent(context, "platform_module", map);

            Intent intent = new Intent(context, WebActivityCommon.class);
            intent.putExtra("url", banner.getShareLink());
            intent.putExtra("title", banner.getShareTitle());
            intent.putExtra("shareContent", new ShareContent(banner.getShareTitle(), banner.getShareDesc(), banner.getShareLink(), banner.getShareIcon()));
            context.startActivity(intent);
          //  WebActivityCommon.showThisActivity(context, banner.getLinkUrl(), "");

        }
    }
};