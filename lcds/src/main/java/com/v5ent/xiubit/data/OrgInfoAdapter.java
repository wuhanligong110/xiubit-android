package com.v5ent.xiubit.data;

import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.OrgInfoDetailActivity;
import com.v5ent.xiubit.entity.OrgInfoDetail;
import com.umeng.analytics.MobclickAgent;

/**
 * 机构列表Adapter
 */
public class OrgInfoAdapter extends BaseQuickAdapter<OrgInfoDetail, BaseViewHolder> {


    public OrgInfoAdapter(Activity activity) {
        super(R.layout.item_orginfo);
        mContext = activity;
    }


    public SpannableString getSpannerString(String text) {
        SpannableString spannableStr = new SpannableString(text);
        spannableStr.setSpan(new TextAppearanceSpan(mContext, R.style.text_size10_style), text.length() - 1, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStr;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrgInfoDetail data) {
        int position = helper.getAdapterPosition();
//        Logger.e("position ==>" + position);
        final OrgInfoDetail bean = data;

        helper.setVisible(R.id.hotTagIv, !bean.getListRecommend().equals("0"))
                .setText(R.id.orgAdvantageTv, bean.getOrgAdvantage().replace(",", " | "))
                .setText(R.id.averageRateTv, getSpannerString(bean.getAverageRate() + "%"))
                .setText(R.id.gradeTv, bean.getGrade())
                .setText(R.id.orgFeeRatioTv, getSpannerString(bean.getOrgFeeRatio() + "%"))
                .setOnClickListener(R.id.item_orginfo_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MobclickAgent.onEvent(mContext,"T_1_3");
                        Intent intent = new Intent(mContext, OrgInfoDetailActivity.class);
                        intent.putExtra("orgName", bean.getName());
                        intent.putExtra("orgNumber", bean.getOrgNumber());

                        mContext.startActivity(intent);
                    }
                });
//        helper.getView(R.id.footBlankView).setVisibility(position == getItemCount() - 1 ? View.VISIBLE : View.GONE);
        helper.getView(R.id.hasSoldALlIv).setVisibility(bean.getUsableProductNums().equals("0")?View.VISIBLE:View.INVISIBLE);

        String imageUrl = MyApp.getInstance().getHttpService().getImageUrlFormMd5(bean.getPlatformlistIco());
        Glide.with(mContext).load(imageUrl).into((ImageView) helper.getView(R.id.orgLogoIV));
        //产品tag
        String orgTag = bean.getOrgTag();
        LinearLayout orgTagContantLi = helper.getView(R.id.orgTagContantLi);

        orgTagContantLi.removeAllViews();
        if (orgTag != null) {
            String[] split = orgTag.split(",");
            for (String str : split) {
                TextView tv = (TextView) View.inflate(mContext, R.layout.text_org_detial_tager, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, (int)(mContext.getResources().getDimension(R.dimen.w6)), 0);//4个参数按顺序分别是左上右下
                tv.setLayoutParams(layoutParams);
                tv.setText(str);
                orgTagContantLi.addView(tv);
            }
        }


    }


}
