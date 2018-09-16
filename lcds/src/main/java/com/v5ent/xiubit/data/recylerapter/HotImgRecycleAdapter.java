package com.v5ent.xiubit.data.recylerapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jungly.gridpasswordview.Util;
import com.toobei.common.data.BaseRecycleViewAdapter;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.TextUrlUtils;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.HomeHotNewsDetial;
import com.v5ent.xiubit.util.C;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/23
 */

public class HotImgRecycleAdapter extends BaseRecycleViewAdapter<HomeHotNewsDetial> {


    public HotImgRecycleAdapter(Context context) {
        super(context);
    }

    public HotImgRecycleAdapter(Context context, List<HomeHotNewsDetial> datas) {
        super(context, datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contantView = inflater.inflate(R.layout.item_hot_news_homepager, null,false);

        return new ViewHolder(contantView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        HomeHotNewsDetial bean = datas.get(position);
        String imgUrl = bean.getImg();

        viewHolder.iv.getLayoutParams().height =  (MyApp.displayWidth - Util.dp2px(context,30)) * 280 / 690;

        PhotoUtil.loadImageByGlide(context,imgUrl,viewHolder.iv,C.DefaultResId.BANNER_PLACT_HOLD_IMG_690x280_);
        Glide.with(context).load(imgUrl).into(viewHolder.iv);
        final HomeHotNewsDetial finalBean = bean;
        viewHolder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(context,"S_6_1");
                Intent intent = new Intent(context, WebActivityCommon.class);

                String informationDetailUrl = MyApp.getInstance().getDefaultSp().getInformationDetailUrl();

                String newsUrl = TextUrlUtils.addUrlStr(informationDetailUrl, "type=") //
                        + C.INFOMATION_URL_TYPE_NEWS//
                        + "&id=" + finalBean.getNewsId()//
                        + "&t=" + MyApp.getInstance().getLoginService().token;
                intent.putExtra("url", TextUtils.isEmpty(finalBean.getLinkUrl())?newsUrl:finalBean.getLinkUrl());
                intent.putExtra("title", finalBean.getTitle());
                intent.putExtra("webActivityType", WebActivityCommon.WebActivityType.LIECAI_NEWS);
                intent.putExtra("shareContent", new ShareContent(finalBean.getTitle(), finalBean.getSummary(), newsUrl, finalBean
                        .getShareIcon()));
                context.startActivity(intent);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.hotNewsIv);
        }
    }


}
