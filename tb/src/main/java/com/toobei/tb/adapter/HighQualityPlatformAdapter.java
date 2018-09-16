package com.toobei.tb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.data.BaseRecycleViewAdapter;
import com.toobei.common.entity.HighQualityPlatformDetail;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.tb.activity.OrgInfoDetailActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18 0018.
 * 首页热门机构列表
 */
public class HighQualityPlatformAdapter extends BaseRecycleViewAdapter<HighQualityPlatformDetail> {


    private View rootview;

    public HighQualityPlatformAdapter(Context context, List<HighQualityPlatformDetail> datas) {
        super(context, datas);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        rootview = inflater.inflate(R.layout.item_platform, null);

        return new HomeHotInstitutionViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeHotInstitutionViewHolder viewHolder = (HomeHotInstitutionViewHolder) holder;

        final HighQualityPlatformDetail homeOrgInfoDetail = datas.get(position);
        final String orgName = homeOrgInfoDetail.getOrgName();
        String orgLogo = homeOrgInfoDetail.getOrgLogo();

        viewHolder.orgNameTV.setText(orgName);
        final String orgNumber = homeOrgInfoDetail.getOrgNumber();
        if (!TextUtils.isEmpty(orgLogo) && !orgLogo.startsWith("http")) {
            orgLogo = TopApp.getInstance().getHttpService().getImageServerBaseUrl() + orgLogo;

        }
        ImageLoader.getInstance().displayImage(orgLogo + "?f=png", viewHolder.orgLogoIV, PhotoUtil.normalImageOptions);
        //  2016/11/15 0015  点击机构不跳转
        rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, OrgInfoDetailActivity.class);
                intent.putExtra("orgName", orgName);
                intent.putExtra("orgNumber", orgNumber);
                context.startActivity(intent);
            }
        });
    }


    class HomeHotInstitutionViewHolder extends RecyclerView.ViewHolder {

        public TextView orgNameTV;
        public ImageView orgLogoIV;

        public HomeHotInstitutionViewHolder(View itemView) {
            super(itemView);
            orgNameTV = (TextView) itemView.findViewById(R.id.orgNameTV);
            orgLogoIV = (ImageView) itemView.findViewById(R.id.orgLogoIV);

        }
    }


}
