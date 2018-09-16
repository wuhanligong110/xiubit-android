package com.v5ent.xiubit.data;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ShareContent;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.PlatformDetail;

import java.util.List;

/**
 * Created by Administrator on 2016/10/18 0018.
 */

public class OrginfoDetailBannerAdapter extends PagerAdapter {


    private  String orgName;
    Context context;

    public OrginfoDetailBannerAdapter(Context context, List<PlatformDetail.OrgActivitysBean> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    /**
     *
     * @param context ..
     * @param bannerList ..
     * @param orgName ..用于友盟统计
     */
    public OrginfoDetailBannerAdapter(Context context, List<PlatformDetail.OrgActivitysBean> bannerList, String orgName) {
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
        if (bannerList == null ||bannerList.size() == 0 ) return 0;
        return Integer.MAX_VALUE/2;
    }

    public List<PlatformDetail.OrgActivitysBean> getItemList() {
        return bannerList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View rootView = View.inflate(context,R.layout.item_banner_viewpager,null);
        ImageView iv = (ImageView) rootView.findViewById(R.id.iv);
        String orgActivityAdvertise = bannerList.get(position % bannerList.size()).getPlatformImg();
        Glide.with(context).load(MyApp.getInstance().getHttpService().getImageUrlFormMd5(orgActivityAdvertise)).into(iv);
//        if (!orgActivityAdvertise.contains("http")) {
//            orgActivityAdvertise = MyApp.getInstance().getHttpService().getImageServerBaseUrl() + orgActivityAdvertise;
//        }
//        iv.setImageUri(ImageDefault.RECTANGLE_LAND_BANER, orgActivityAdvertise);

//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        item.addView(iv, params);
        iv.setOnClickListener(new MyOnClickListener(context, bannerList.get(position%bannerList.size()), position%bannerList.size() + 1)); //点击的bannner位置
        container.addView(rootView);
        return rootView;
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

            Intent intent = new Intent(context, WebActivityCommon.class);
            intent.putExtra("url", banner.getShareLink());
            intent.putExtra("title", banner.getShareTitle());
            intent.putExtra("shareContent", new ShareContent(banner.getShareTitle(), banner.getShareDesc(), banner.getShareLink(), banner.getShareIcon()));
            context.startActivity(intent);
          //  WebActivityCommon.showThisActivity(context, banner.getLinkUrl(), "");

        }
    }
};