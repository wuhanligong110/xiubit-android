package com.v5ent.xiubit.data;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.TopApp;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.common.view.popupwindow.WebPromptPopupWindow;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.entity.IncomeDetail;
import com.v5ent.xiubit.util.C;

import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.StringUtils;

/**
 * 平台详情简介图片
 *
 * @author Administrator
 * @time 2016/11/8 0008 下午 4:38
 */
public class IncomeDetailRecylerAdpter extends BaseQuickAdapter<IncomeDetail,BaseViewHolder> {
    private String incomeType;


    public IncomeDetailRecylerAdpter(String urIncomeType) {
        super(R.layout.item_income_detail_list);
        this.incomeType = urIncomeType;
    }

    /**
     * name = (TextView) itemView.findViewById(R.id.text_customer_name);
     time = (TextView) itemView.findViewById(R.id.text_time);
     count = (TextView) itemView.findViewById(R.id.text_account_count);
     content = (TextView) itemView.findViewById(R.id.text_account_content);
     contentFee = (TextView) itemView.findViewById(R.id.text_account_fee);
     textFeeRate = (TextView) itemView.findViewById(R.id.text_feeRate);
     feeLL = (LinearLayout) itemView.findViewById(R.id.feeLL);
     textDeadline = (TextView) itemView.findViewById(R.id.text_deadline);
     questionIV = (ImageView) itemView.findViewById(R.id.img_question);
     typeSuffixTv = (TextView) itemView.findViewById(R.id.typeSuffixTv);
     * @param helper
     * @param item
     */

    @Override
    protected void convert(final BaseViewHolder helper, IncomeDetail item) {
        final IncomeDetail data = item;
        if (!TextUtils.isEmpty(data.getTypeSuffix())) {
            helper.setText(R.id.typeSuffixTv,"(" + data.getTypeSuffix() + ")");
        }
        helper.getView(R.id.text_account_fee).setVisibility(View.GONE);
        helper.setText(R.id.text_customer_name,data.getProfixTypeName());
        helper.setText(R.id.text_time,data.getTime());
        helper.setText(R.id.text_feeRate,"年化佣金 " + data.getFeeRate());
        helper.setText(R.id.text_deadline,"产品期限 " + data.getDeadline());
        helper.setText(R.id.text_account_count,StringUtil.formatNumberWithSign(data.getAmount()) + "元");

        //  final View finalConvertView = convertView;
        helper.getView(R.id.img_question).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                //  2016/11/3 0003  收益为0的原因
                if (StringUtils.toDouble(data.getAmount()) == 0) {
                    PromptDialogMsg promptDialogMsg = new PromptDialogMsg(mContext, data.getRemark() == null ? "" : data.getRemark(), "知道了");
                    promptDialogMsg.show();
                    return;
                }


                // String token = MyApp.getInstance().getLoginService().token;
                final String productType = data.getProductType();
                WebPromptPopupWindow webPromptPopupWindow = new WebPromptPopupWindow(mContext, TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.URL_BALANCE_RULE_QUESTION + "?productType=" + productType) {
                    @Override
                    public void onUrlLoading(boolean isRedirectUsable, String url) {
                        if (!isRedirectUsable) {
                            WebActivityCommon.showThisActivity(ctx, url, "", false);
                        }
                    }


                };

                // 产品类型：1首投，2首投可赎回，3复投，4复投可赎回
                switch (StringUtils.toInt(productType)) {
                    case 1:
                        webPromptPopupWindow.setWebHeight(PixelUtil.dip2px(mContext, 130));
                        break;
                    case 2:
                        webPromptPopupWindow.setWebHeight(PixelUtil.dip2px(mContext, 180));
                        break;
                    case 3:
                        webPromptPopupWindow.setWebHeight(PixelUtil.dip2px(mContext, 140));
                        break;
                    case 4:
                        webPromptPopupWindow.setWebHeight(PixelUtil.dip2px(mContext, 140));
                        break;
                }
                //  webPromptPopupWindow.setAnimationStyle(R.style.anim_popup_share);
                webPromptPopupWindow.showPopupWindow(helper.getView(R.id.rootView));

            }


        });
        helper.setText(R.id.text_account_content,data.getDescription()); //描述
        //       接口详情 (id: 293)
//        接口名称 月度收益统计-钟灵-已实现
//        请求类型 get
//        请求Url account/monthProfixStatistics
        //  profixType	收益类型：1销售佣金，2推荐津贴，3活动奖励，4团队leader奖励	string
        if ("1".equals(data.getProfixType())) {
            helper.getView(R.id.feeLL).setVisibility(View.VISIBLE);
            helper.getView(R.id.img_question).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.feeLL).setVisibility(View.GONE);
            helper.getView(R.id.img_question).setVisibility(View.INVISIBLE);
        }


    }



}
