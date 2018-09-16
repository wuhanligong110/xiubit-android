package com.v5ent.xiubit.data.recylerapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.ViewPagerActivity;
import com.v5ent.xiubit.entity.BrandPromotionData.PosterListBean;
import com.v5ent.xiubit.service.PopularPosterHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/5
 */

public class SelectImageAdapter extends BaseQuickAdapter<PosterListBean,BaseViewHolder> {
    public SelectImageAdapter() {
        super(R.layout.item_select_image_list);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void convert(BaseViewHolder helper, PosterListBean bean) {
//        final String smallimageUrl = bean.getSmallImage();  //可能不带http
        final String imageUrl = bean.getImage();  //可能不带http
        final int position = helper.getAdapterPosition();
        String url = MyApp.getInstance().getHttpService().getImageUrlFormMd5(imageUrl)+"&h=197&w=111";
        final ImageView iv = helper.getView(R.id.imageIv);
        final ImageView selectIv = helper.getView(R.id.selectIv);
        PhotoUtil.loadImageByGlide(mContext,url,iv,R.drawable.poster_def_img);

        if (isSelected(imageUrl)){
            selectIv.setImageResource(R.drawable.select_image_box);
        }else {
            selectIv.setImageResource(R.drawable.unselect_image_box);
        }

        selectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synSelectState(imageUrl,!isSelected(imageUrl));

            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imgUrlstrings = new ArrayList<>();
                for (int i = 0; i < getData().size(); i++) {
                    imgUrlstrings.add(getData().get(i).getImage() + "?f=png");

                }
                Intent intent = new Intent(mContext, ViewPagerActivity.class);
                intent.putStringArrayListExtra("imgUrlstrings", imgUrlstrings);
                intent.putExtra("imgPosition", position);

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    iv.setTransitionName(imgUrlstrings.toString());
                    ActivityOptionsCompat qing = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,//
                            new Pair<View, String>( iv,
                                    mContext.getString(R.string.transition_scale_photoview)));
                    ActivityCompat.startActivity((Activity) mContext, intent, qing.toBundle());
                } else {
                    mContext.startActivity(intent);
                }

            }
        });
    }

    private boolean isSelected(String imgUrl){
        return  PopularPosterHelper.INSTANCE.isSelected(imgUrl);
    }

    private void synSelectState(String imgUrl , boolean isSelected) {
        PopularPosterHelper.INSTANCE.synSelectImgUrl(imgUrl,isSelected);
        EventBus.getDefault().post(PopularPosterHelper.INSTANCE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    public void refreshState(PopularPosterHelper event) {
        notifyDataSetChanged();
    }

}
