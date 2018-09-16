package com.toobei.tb.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.tb.MyApp;
import com.toobei.tb.R;
import com.toobei.tb.entity.HomeOrgInfoDetail;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;

import java.util.List;

/**
 * home页 优质平台
 */
public class HomeOrgAdapter extends BaseListAdapter<HomeOrgInfoDetail> {

    public HomeOrgAdapter(Context ctx, List<HomeOrgInfoDetail> datas) {
        super(ctx, datas);
        this.datas = datas;
    }

    public HomeOrgAdapter(Context ctx) {
        super(ctx);
    }

    @Override
    public HomeOrgInfoDetail getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final HomeOrgInfoDetail data = getItem(position);
        HomeOrgInfoDetail platFormDetail = datas.get(position);
        // 机构Logo
        String orgLogo = platFormDetail.getOrgLogo();
        //机构名
        String orgName = platFormDetail.getOrgName();
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_platform, parent, false);
        }

        TextView orgNameTV = ViewHolder.findViewById(convertView, R.id.orgNameTV);
        ImageView orgLogoIV = ViewHolder.findViewById(convertView, R.id.orgLogoIV);
        ImageLoader.getInstance().displayImage(MyApp.getInstance().getHttpService().getImageServerBaseUrl() + orgLogo + "?f=png", orgLogoIV, PhotoUtil.normalImageOptions);
        orgNameTV.setText(orgName);
        return convertView;

    }
}
