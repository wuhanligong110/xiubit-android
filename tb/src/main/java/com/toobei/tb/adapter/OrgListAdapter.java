package com.toobei.tb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.view.AutoScrollTextView;
import com.toobei.common.view.MyShowTipsView;
import com.toobei.tb.R;
import com.toobei.tb.activity.OrgInfoDetailActivity;
import com.toobei.tb.activity.WebActivityCommon;
import com.toobei.tb.entity.OrgInfoDetail;

import org.xsl781.data.BaseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构列表Adapter
 */
public class OrgListAdapter extends BaseListAdapter<OrgInfoDetail> {


    private final Activity activity;
    private boolean isCfpRecommend = false; //是否是理财推荐平台的adapter 还是机构列表的adapter
    private LayoutInflater inflater;
    private MyShowTipsView myShowTipsView; //新手引导

    /**
     * 用于机构列表
     */
    public OrgListAdapter(Activity activity, List<OrgInfoDetail> datas) {
        super(activity, datas);
        this.datas = datas;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    /**
     * 用于 我的理财师->理财师推荐平台
     */
    public OrgListAdapter(Activity activity, List<OrgInfoDetail> datas, boolean isCfpRecommend) {
        super(activity, datas);
        this.datas = datas;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.isCfpRecommend = isCfpRecommend;
    }

    private class ViewHolder {

        LinearLayout vgRootView;
        TextView textAnnualTitle;
        TextView textDateLimitMin;
        TextView textTenderLimt;
        TextView textProductTag1;
        TextView textProductTag2;
        TextView textProductTag3;
        TextView textOrgInfoUniMax;
        ImageView infoIcon;
        //  TextView texOrgRank;
        TextView textOrgInfoUniMin;
        TextView textDateLimitMax;

        //V1.2.1
        TextView textOrgAdvantage;
        TextView textOrgTag;
        TextView textOrgName;
        AutoScrollTextView autoTextView;
        LinearLayout activityLL;
        TextView activityNameTV; //轮播的活动名字
        ImageView redpacketTagIv; //红包标示
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {


        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_orginfo, null);
            viewHolder = new ViewHolder();
            viewHolder.vgRootView = (LinearLayout) convertView.findViewById(R.id.item_orginfo_root);
            viewHolder.infoIcon = (ImageView) convertView.findViewById(R.id.orgLogoIV);//机构logo
            //  viewHolder.infoIcon.setTag(platformIco);

            //  viewHolder.texOrgRank = (TextView) convertView.findViewById(R.id.text_org_rank); //机构等级
            viewHolder.textAnnualTitle = (TextView) convertView.findViewById(R.id.text_annual_rate); //年化收益
            viewHolder.textDateLimitMin = (TextView) convertView.findViewById(R.id.text_orginfo_date_limt_min); // 产品期限
            viewHolder.textDateLimitMax = (TextView) convertView.findViewById(R.id.text_orginfo_date_limt_max); // 产品期限
            viewHolder.textOrgInfoUniMin = (TextView) convertView.findViewById(R.id.text_orginfo_date_limt_uni_min);// 期限单位
            viewHolder.textOrgInfoUniMax = (TextView) convertView.findViewById(R.id.text_orginfo_date_limt_uni_max);// 期限单位
            viewHolder.textTenderLimt = (TextView) convertView.findViewById(R.id.text_tender_count); //可投标数
            viewHolder.textProductTag1 = (TextView) convertView.findViewById(R.id.text_product_tag1);//tag标签
            viewHolder.textProductTag2 = (TextView) convertView.findViewById(R.id.text_product_tag2);
            viewHolder.textProductTag3 = (TextView) convertView.findViewById(R.id.text_product_tag3);
            viewHolder.textOrgAdvantage = (TextView) convertView.findViewById(R.id.text_orgAdvantage);
            viewHolder.textOrgTag = (TextView) convertView.findViewById(R.id.text_orgTag);
            viewHolder.textOrgName = (TextView) convertView.findViewById(R.id.text_org_name);
            viewHolder.activityLL = (LinearLayout) convertView.findViewById(R.id.activityLL);
            viewHolder.autoTextView = (AutoScrollTextView) convertView.findViewById(R.id.text_switcher);
            viewHolder.activityNameTV = (TextView) convertView.findViewById(R.id.text_activity_name);
            viewHolder.redpacketTagIv = (ImageView) convertView.findViewById(R.id.redpacketTagIv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 获取数据
        final OrgInfoDetail orginfo = datas.get(position);
        if (orginfo.getHashRedpacket()){
            viewHolder.redpacketTagIv.setVisibility(View.VISIBLE);
        }else {
            viewHolder.redpacketTagIv.setVisibility(View.GONE);
        }
        //机构活动
        ArrayList<String> titleList = new ArrayList<String>();
        final List<OrgInfoDetail.OrgActivitys> orgActivitys = orginfo.getOrgActivitys();
        if (orgActivitys != null && orgActivitys.size() > 0) {
            viewHolder.activityLL.setVisibility(View.VISIBLE); //显示机构活动
            for (int i = 0; i < orgActivitys.size(); i++) {
                final String activityName = orgActivitys.get(i).getActivityName();
                titleList.add(activityName + "");
                //打开点击的活动(可分享)
                viewHolder.autoTextView.setOnItemClickListener(new AutoScrollTextView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        WebActivityCommon.showThisActivity((TopBaseActivity) activity, orgActivitys.get(position).getLinkUrl(), orgActivitys.get(position).getLinkUrl());


                        Intent intent = new Intent(activity, WebActivityCommon.class);
                        OrgInfoDetail.OrgActivitys data = orgActivitys.get(position);
                        intent.putExtra("url", data.getLinkUrl());
                        intent.putExtra("title", data.getActivityName());
                        intent.putExtra("webActivityType", WebActivityCommon.WebActivityType.LIECAI_PLATFORM_ACTIVITIES);
                        intent.putExtra("shareContent", new ShareContent(data.getShareTitle(), data.getShareDesc(), data.getShareLink(), data.getShareIcon()));
                        activity.startActivity(intent);


                    }
                });
            }
            if (titleList.size() == 1) {
                viewHolder.autoTextView.setVisibility(View.GONE);
                viewHolder.activityNameTV.setVisibility(View.VISIBLE);
                String activityName = orgActivitys.get(0).getActivityName();


                SpannableString spannableString = new SpannableString(activityName);
                UnderlineSpan underlineSpan = new UnderlineSpan();
                spannableString.setSpan(underlineSpan, 0, activityName.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                viewHolder.activityNameTV.setText(spannableString);

//打开点击的活动(可分享)
                viewHolder.activityNameTV.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, WebActivityCommon.class);
                        OrgInfoDetail.OrgActivitys data = orgActivitys.get(0);
                        intent.putExtra("url", data.getLinkUrl());
                        intent.putExtra("title", data.getActivityName());
                        intent.putExtra("webActivityType", WebActivityCommon.WebActivityType.LIECAI_PLATFORM_ACTIVITIES);
                        intent.putExtra("shareContent", new ShareContent(data.getShareTitle(), data.getShareDesc(), data.getShareLink(), data.getShareIcon()));
                        activity.startActivity(intent);
                    }
                });

            } else {
                viewHolder.autoTextView.setVisibility(View.VISIBLE);
                viewHolder.activityNameTV.setVisibility(View.GONE);
                viewHolder.autoTextView.setTextList(titleList);
                viewHolder.autoTextView.startAutoScroll();


            }

        } else {
            viewHolder.activityLL.setVisibility(View.GONE); //隐藏活动
        }


        String minProfit = orginfo.getMinProfit();// 最小年化
        String maxProfit = orginfo.getMaxProfit();// 最大年化


        String deadLineValueText = orginfo.getDeadLineValueText();// 第三方产品期限
        viewHolder.vgRootView.setOnClickListener(new ItemClickListener(orginfo)); // 设置点击事件

        viewHolder.textAnnualTitle.setText(minProfit != null && minProfit.equals(maxProfit) ? minProfit : minProfit + "~" + maxProfit); //最大最小年化
        viewHolder.textOrgAdvantage.setText(orginfo.getOrgAdvantage());

        viewHolder.textOrgName.setText(isCfpRecommend ? orginfo.getOrgName() : orginfo.getName());
        String platformIco = isCfpRecommend ? orginfo.getOrgLogo() : orginfo.getPlatformIco();
        PhotoUtil.loadImageByGlide(ctx, platformIco + "?f=png", viewHolder.infoIcon);

        String orgTag = orginfo.getOrgTag();
        if (orgTag.endsWith(",")) {
            orgTag = orgTag.substring(0, orgTag.length() - 1);
        }
        viewHolder.textOrgTag.setText(TextUtils.isEmpty(orgTag) ? "安全等级: " + orginfo.getGrade() : orgTag.replace(",", "  |  ") + "  |  安全等级: " + orginfo.getGrade());

        //Tag

        //产品tag
        viewHolder.textProductTag1.setVisibility(View.GONE);
        viewHolder.textProductTag2.setVisibility(View.GONE);
        viewHolder.textProductTag3.setVisibility(View.GONE);
        String orgProductTag = orginfo.getOrgInvestTag();
        if (orgProductTag != null) {
            String[] split = orgProductTag.split(",");
            if (split != null && split.length > 0) {
                if (split != null && !TextUtils.isEmpty(split[0])) {
                    viewHolder.textProductTag1.setVisibility(View.VISIBLE);
                    viewHolder.textProductTag1.setText(split[0]);
                }
                if (split.length >= 2 && !TextUtils.isEmpty(split[1])) {
                    viewHolder.textProductTag2.setVisibility(View.VISIBLE);
                    viewHolder.textProductTag2.setText(split[1]);
                }
                if (split.length >= 3 && !TextUtils.isEmpty(split[2])) {
                    viewHolder.textProductTag3.setVisibility(View.VISIBLE);
                    viewHolder.textProductTag3.setText(split[2]);
                }
            }
        }


        //产品期限
        if (deadLineValueText != null) {
            if (!deadLineValueText.contains(",")) return convertView;
            String[] splitDatas = deadLineValueText.split(",");//deadLineValueText=30,天,2,个月
            if (splitDatas[0] != null) {
                viewHolder.textDateLimitMin.setText(splitDatas[0]);
            }
            if (splitDatas[1] != null) {
                viewHolder.textOrgInfoUniMin.setText(splitDatas[1]);
            }

            if (splitDatas.length >= 4) {
                if (splitDatas[2] != null) {
                    viewHolder.textDateLimitMax.setText("~" + splitDatas[2]);
                }
                if (splitDatas[3] != null) {
                    viewHolder.textOrgInfoUniMax.setText(splitDatas[3]);
                }
            } else {
                viewHolder.textDateLimitMax.setText("");
                viewHolder.textOrgInfoUniMax.setText("");
            }
        } else {
            viewHolder.textDateLimitMax.setText("");
            viewHolder.textOrgInfoUniMax.setText("");
            viewHolder.textDateLimitMin.setText("");
            viewHolder.textOrgInfoUniMin.setText("");

        }

        viewHolder.textTenderLimt.setText(orginfo.getUsableProductNums()); //可投标
        return convertView;
    }


    private class ItemClickListener implements OnClickListener {
        private OrgInfoDetail org;

        public ItemClickListener(OrgInfoDetail org) {
            super();
            this.org = org;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_orginfo_root:


                    Intent intent = new Intent(activity, OrgInfoDetailActivity.class);

                    String orgName = isCfpRecommend ? org.getOrgName() : org.getName();
                    intent.putExtra("orgName", orgName);
                    intent.putExtra("orgNumber", org.getOrgNumber());
                    activity.startActivity(intent);
                    break;
            }
        }
    }


}
