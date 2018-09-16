package com.v5ent.xiubit.data;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.StringUtil;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.R.color;
import com.v5ent.xiubit.activity.ProductInfoWebActivity;
import com.v5ent.xiubit.activity.RecommendProductOrOrgActivity;
import com.v5ent.xiubit.activity.RecommendProductOrOrgNoBindActivity;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.ProductCircleProgressbar;

import org.xsl781.data.BaseListAdapter;

import java.util.List;


/**
 * 产品列表adapter
 */
public class ProductListAdapter extends BaseListAdapter<ProductDetail> {

    private boolean isHot;//是否是热推
    public Activity activity;
    private LayoutInflater inflater;
    //   private List<ProductDetail> list;
    public OnBtnRecommendClicked onBtnRecommendClicked;
    private String curRecommendProductName;
    private boolean isOrg = false; //是否是机构中显示的产品列表,是的话不拼接机构名直接显示产品名


    public interface OnBtnRecommendClicked {
        void onBtnRecommendClicked(String curRecommendProductName, ProductDetail product);
    }

    public ProductListAdapter(Activity activity) {
        super(activity);
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @SuppressWarnings("unchecked")
    public ProductListAdapter(Activity activity, List<ProductDetail> list) {
        super(activity, list);
        this.datas = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @SuppressWarnings("unchecked")
    public ProductListAdapter(Activity activity, List<ProductDetail> list, boolean isOrg) {
        super(activity, list);
        this.datas = list;
        this.activity = activity;
        this.isOrg = isOrg;
        inflater = LayoutInflater.from(activity);
    }

    public ProductListAdapter(Activity activity, List<ProductDetail> list, boolean isOrg, boolean isHot) {
        super(activity, list);
        this.datas = list;
        this.activity = activity;
        this.isOrg = isOrg;
        this.isHot = isHot;
        inflater = LayoutInflater.from(activity);
    }

    private class ViewHolder {

        TextView ivPresaleTag;// 首投 或者复投
        LinearLayout vgRootView;
        TextView textCommissionTitle;
        TextView textYearTitle;
     //   TextView textDeadLineTitle;
        TextView textProductName;
        TextView textProductTag1;
        TextView textProductTag2;
        TextView textProductTag3;
        TextView textYearIncome;
        TextView textYearIncome1;
        TextView textCommissionRate;
        TextView textCommissionRate1;
        TextView textMaxDeadline;
        TextView textMaxDeadlineType;
        TextView textProductStatus;
        TextView textProdcutTitle;
        RelativeLayout headRe;
        //   RelativeLayout progress_bar_rl;
        TextView textProgress;
        // ProgressBar progressBar;
        // TextView productDescTv;
        LinearLayout productDescLL;
        TextView textMinDeadlineType;
        TextView textMinDeadline;
        TextView textProductPosition;
        TextView ivRightFreshTag; //V2.0.2 新手专享
        ProductCircleProgressbar circleProgress;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_product_fix_list, null);


            viewHolder = new ViewHolder();
            viewHolder.vgRootView = (LinearLayout) convertView.findViewById(R.id.product_fix_list_item_root);
            viewHolder.textCommissionTitle = (TextView) convertView.findViewById(R.id.text_commission_title);
            viewHolder.textYearTitle = (TextView) convertView.findViewById(R.id.text_year_title);
          //  viewHolder.textDeadLineTitle = (TextView) convertView.findViewById(R.id.text_deadline_title);
            viewHolder.textProductName = (TextView) convertView.findViewById(R.id.text_product_name);
            viewHolder.textProductPosition = (TextView) convertView.findViewById(R.id.text_product_position);
            viewHolder.ivPresaleTag = (TextView) convertView.findViewById(R.id.iv_product_presale_tag);
            viewHolder.ivRightFreshTag = (TextView) convertView.findViewById(R.id.iv_product_fresh_tag);
            viewHolder.textProductTag1 = (TextView) convertView.findViewById(R.id.text_product_tag1);
            viewHolder.textProductTag2 = (TextView) convertView.findViewById(R.id.text_product_tag2);
            viewHolder.textProductTag3 = (TextView) convertView.findViewById(R.id.text_product_tag3);
            viewHolder.textYearIncome = (TextView) convertView.findViewById(R.id.text_year_income);
            viewHolder.textYearIncome1 = (TextView) convertView.findViewById(R.id.text_year_income1);
            viewHolder.textCommissionRate = (TextView) convertView.findViewById(R.id.text_commission_rate);
            viewHolder.textCommissionRate1 = (TextView) convertView.findViewById(R.id.text_commission_rate1);


            viewHolder.textMinDeadline = (TextView) convertView.findViewById(R.id.text_invest_min_deadline);//最小期限
            viewHolder.textMinDeadlineType = (TextView) convertView.findViewById(R.id.text_invest_min_deadline_uni); //最小期限单位
            viewHolder.textMaxDeadline = (TextView) convertView.findViewById(R.id.text_invest_max_deadline);//最大期限
            viewHolder.textMaxDeadlineType = (TextView) convertView.findViewById(R.id.text_invest_max_deadline_uni); //最大期限单位

            viewHolder.textProductStatus = (TextView) convertView.findViewById(R.id.text_product_status);
            viewHolder.textProdcutTitle = (TextView) convertView.findViewById(R.id.titleTv);

            viewHolder.headRe = (RelativeLayout) convertView.findViewById(R.id.headRe);  //产品标签
            viewHolder.headRe.setVisibility(View.GONE);
            //  viewHolder.progress_bar_rl = (RelativeLayout) convertView.findViewById(R.id.progress_bar_rl);
            viewHolder.circleProgress = (ProductCircleProgressbar) convertView.findViewById(R.id.circleProgress);
            viewHolder.textProgress = (TextView) convertView.findViewById(R.id.text_invest_progress);
            // viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.invest_progress_bar);
            //  viewHolder.productDescTv = (TextView) convertView.findViewById(R.id.productDescTv); //产品描述 (可赎回/转让)
            viewHolder.productDescLL = (LinearLayout) convertView.findViewById(R.id.productDescLl);//tag标签栏

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //------------------------------------------------------- 获取数据-------------------------------------//

        final ProductDetail product = datas.get(position);
        if (product == null) return convertView;

        String orgName = product.getOrgName();// 机构名
        String saleStatus = product.getStatus(); //销售状态

        //利率
        String isFlow = product.getIsFlow(); // 1=固定利率|2=浮动利率
        String fixRate = product.getProductRateText();// 年化固定利率
        if (fixRate != null) {
            fixRate = fixRate.replace("%", "");
            fixRate = StringUtil.formatNumber(fixRate);
        }
        //年化 取两位小数
        String flowMaxRate = product.getFlowMaxRate();
        String flowMinRate = product.getFlowMinRate();
        flowMaxRate = StringUtil.formatNumber(flowMaxRate);
        flowMinRate = StringUtil.formatNumber(flowMinRate);

        String feeRatio = product.getFeeRatio();//佣金
        String isHaveProgress = product.getIsHaveProgress(); // 是否有进度条
//        List<String> tagRightList = product.getTagListRight();//右上标签
        String tagListRight = "";
//        if (tagRightList.size() > 0) {
//            tagListRight = tagRightList.get(0);// 右上角首投
//        }
        String deadLineValueText = product.getDeadLineValueText();// 第三方产品期限
//
//        List<String> tagList = product.getTagList();//产品标签


        double buyTotalMoney = 0;
        double buyedTotalMoney = 0;
        String buyedTotalMoneyStr = product.getBuyedTotalMoney();//已募集总额(单位元)
        String buyTotalMoneyStr = product.getBuyTotalMoney();

        if (buyedTotalMoneyStr != null && buyedTotalMoneyStr.length() > 0) {
            buyedTotalMoney = Double.parseDouble(buyedTotalMoneyStr);
        }
        if (buyTotalMoneyStr != null && buyTotalMoneyStr.length() > 0) {
            buyTotalMoney = Double.parseDouble(product.getBuyTotalMoney());
        }
        String orgFeeType = product.getOrgFeeType();//机构收费类型 1:cpa-按投资人数量进行收费/首投   2:cps-按投资金额进行收费/复投

        //点击事件
        ItemClickListener itemClickListener = new ItemClickListener(product);
        convertView.setOnClickListener(itemClickListener);


        // --------------------------------------------------设置数据------------------------------------------------------//

        //viewHolder.textProdcutTitle.setBackgroundResource(resId);
        viewHolder.textProductName.setText(isOrg ? product.getProductName() : orgName + "-" + product.getProductName());
        viewHolder.textProductPosition.setText((position + 1) + "");
        viewHolder.textProductPosition.setVisibility(isHot ? View.VISIBLE : View.GONE);

        viewHolder.textYearIncome.setText("1".equals(isFlow) ? fixRate : flowMinRate + "~" + flowMaxRate);// 年化利率
        viewHolder.textCommissionRate.setText(StringUtil.formatNumber(feeRatio)); //佣金

        //产品期限
        if (deadLineValueText != null) {

            String[] splitDatas = deadLineValueText.split(",");//deadLineValueText=30,天,2,个月
            if (splitDatas[0] != null) {
                viewHolder.textMinDeadline.setText(splitDatas[0]);
            }
            if (splitDatas.length > 1 && splitDatas[1] != null) {
                viewHolder.textMinDeadlineType.setText(splitDatas[1]);
            }

            if (splitDatas.length >= 4) {
                if (splitDatas[2] != null) {
                    viewHolder.textMaxDeadline.setText("~" + splitDatas[2]);
                }
                if (splitDatas[3] != null) {
                    viewHolder.textMaxDeadlineType.setText(splitDatas[3]);
                }
            } else {
                viewHolder.textMaxDeadline.setText("");
                viewHolder.textMaxDeadlineType.setText("");
            }

        }

        //产品tag
        viewHolder.textProductTag1.setVisibility(View.GONE);
        viewHolder.textProductTag2.setVisibility(View.GONE);
        viewHolder.textProductTag3.setVisibility(View.GONE);
//        if (tagList != null && tagList.size() > 0) {
//            if (!TextUtil.isEmpty(tagList.get(0))) {
//                viewHolder.textProductTag1.setVisibility(View.VISIBLE);
//                viewHolder.productDescLL.setVisibility(View.VISIBLE);
//
//                viewHolder.textProductTag1.setText(tagList.get(0));
//            }
//            if (tagList.size() >= 2 && !TextUtil.isEmpty(tagList.get(1))) {
//                viewHolder.textProductTag2.setVisibility(View.VISIBLE);
//                viewHolder.textProductTag2.setText(tagList.get(1));
//
//            }
//            if (tagList.size() >= 3 && !TextUtil.isEmpty(tagList.get(2))) {
//                viewHolder.textProductTag3.setVisibility(View.VISIBLE);
//                viewHolder.textProductTag3.setText(tagList.get(2));
//
//            }
//        }


//        viewHolder.productDescLL.setVisibility(tagList.size() > 0 ? View.VISIBLE : View.GONE);//显示tag栏

        if ("1".equals(orgFeeType)) { // 首投
            viewHolder.ivPresaleTag.setText("首投");
            viewHolder.ivPresaleTag.setBackgroundResource(R.drawable.img_product_first_tender_tag);
            viewHolder.ivPresaleTag.setVisibility(View.VISIBLE);
        } else if ("2".equals(orgFeeType)) {

            viewHolder.ivPresaleTag.setText("复投");
            viewHolder.ivPresaleTag.setBackgroundResource(R.drawable.img_product_re_tender_tag);
            viewHolder.ivPresaleTag.setVisibility(View.VISIBLE);

        } else {
            viewHolder.ivPresaleTag.setVisibility(View.GONE);
        }

        //新手专享标签
//        List<String> tagListRightNewer = product.getTagListRightNewer();
//        if (tagListRightNewer != null && tagListRightNewer.size() > 0 && !TextUtils.isEmpty(tagListRightNewer.get(0))) {
//            viewHolder.ivRightFreshTag.setVisibility(View.VISIBLE);
//        } else {
//            viewHolder.ivRightFreshTag.setVisibility(View.GONE);
//        }

        //字体颜色
        int gray78 = ContextCompat.getColor(activity,R.color.text_gray_common_title);
        int gray96 = ContextCompat.getColor(activity,R.color.text_gray_common);
        int grayC8 = ContextCompat.getColor(activity,color.text_gray_common);
        int blueColor = ContextCompat.getColor(activity,color.text_blue_common);


        //销售状态  ---->> 在售
        if ("1".equals(saleStatus)) {

            // 进度条的显示

            if ("1".equals(isHaveProgress)) { // 在售   isHaveProgress是否拥有产品进度	0=有|1没有
                viewHolder.textProgress.setVisibility(View.GONE);// 不限额不显示进度
            } else { //进度条蓝色


                int rate = (int) (buyedTotalMoney * 100 / buyTotalMoney);

                viewHolder.textProgress.setText("已售" + rate + "%");
                if (rate > 0) {
                    viewHolder.textProgress.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.textProgress.setVisibility(View.GONE);
                }
                viewHolder.circleProgress.setProgress(rate);

                viewHolder.textProgress.setTextColor(gray96);
            }


            viewHolder.textProductTag1.setTextColor(blueColor);
            viewHolder.textProductTag2.setTextColor(blueColor);
            viewHolder.textProductTag3.setTextColor(blueColor);

            viewHolder.textProductTag1.setBackgroundResource(R.drawable.img_oval_tag_bg);
            viewHolder.textProductTag2.setBackgroundResource(R.drawable.img_oval_tag_bg);
            viewHolder.textProductTag3.setBackgroundResource(R.drawable.img_oval_tag_bg);

            viewHolder.textProductName.setTextColor(ContextCompat.getColor(activity,color.text_gray_commona2));

            viewHolder.textCommissionTitle.setTextColor(gray96);
            viewHolder.textYearTitle.setTextColor(gray96);
            //viewHolder.textDeadLineTitle.setTextColor(gray96);
            viewHolder.textCommissionRate.setTextColor(blueColor);
            viewHolder.textYearIncome.setTextColor(blueColor);
            viewHolder.textMaxDeadline.setTextColor(gray96);

            viewHolder.textCommissionRate1.setTextColor(blueColor);
            viewHolder.textYearIncome1.setTextColor(blueColor);

            viewHolder.textMaxDeadline.setTextColor(gray78);
            viewHolder.textMaxDeadlineType.setTextColor(gray78);
            viewHolder.textMinDeadline.setTextColor(gray78);
            viewHolder.textMinDeadlineType.setTextColor(gray78);
            viewHolder.vgRootView.setBackgroundResource(R.drawable.item_click_bg);

            // 判断是否被推荐
            if ("0".equals(product.getCfpRecommend())) {//1-已推荐 0-未推荐
//                viewHolder.textProductStatus.setBackgroundResource("11".equals(saleStatus) ? R.drawable.img_raise_false : R.drawable.img_not_recomend);
//                viewHolder.textProductStatus.setOnClickListener("11".equals(saleStatus) ? null : itemClickListener);
                viewHolder.circleProgress.setOnClickListener("11".equals(saleStatus) ? null : itemClickListener);
                viewHolder.circleProgress.setIsRecommend(false);
            } else {//募集完成 的狀態顯示 /沒有推薦的狀態
//                curRecommendProductName = product.getProductName();
//                viewHolder.textProductStatus.setBackgroundResource(R.drawable.img_recomend);
//                viewHolder.textProductStatus.setOnClickListener(itemClickListener);
                viewHolder.circleProgress.setOnClickListener(itemClickListener);
                viewHolder.circleProgress.setIsRecommend(true);
            }


            //销售状态  ---->> 售罄
        } else if ("2".equals(saleStatus) || "3".equals(saleStatus)) {//售馨 2   募集失败3


            int rate = (int) (buyedTotalMoney * 100 / buyTotalMoney);
            viewHolder.textProgress.setText("已售" + rate + "%");
            if (rate > 0) {
                viewHolder.textProgress.setVisibility(View.VISIBLE);
            } else {
                viewHolder.textProgress.setVisibility(View.GONE);
            }
            viewHolder.circleProgress.setProgress(rate);
            viewHolder.textProgress.setTextColor(ContextCompat.getColor(activity,color.text_gray_commonDC));

            viewHolder.textProductStatus.setBackgroundResource(saleStatus.equals("3") ? R.drawable.img_sold_out : R.drawable.img_raise_false);
            viewHolder.textProductTag1.setTextColor(gray78);
            viewHolder.textProductTag2.setTextColor(gray78);
            viewHolder.textProductTag3.setTextColor(gray78);

            viewHolder.textProductTag1.setBackgroundResource(R.drawable.img_gray_tag_bg);
            viewHolder.textProductTag2.setBackgroundResource(R.drawable.img_gray_tag_bg);
            viewHolder.textProductTag3.setBackgroundResource(R.drawable.img_gray_tag_bg);


            // 字体变灰

            viewHolder.textCommissionTitle.setTextColor(grayC8);
            viewHolder.textYearTitle.setTextColor(grayC8);
          //  viewHolder.textDeadLineTitle.setTextColor(grayC8);

            viewHolder.textProductName.setTextColor(grayC8);
            viewHolder.textCommissionRate.setTextColor(grayC8);
            viewHolder.textYearIncome.setTextColor(grayC8);
            viewHolder.textMaxDeadline.setTextColor(grayC8);

            viewHolder.textCommissionRate1.setTextColor(grayC8);
            viewHolder.textYearIncome1.setTextColor(grayC8);
            viewHolder.textMaxDeadlineType.setTextColor(grayC8);
            viewHolder.textMaxDeadline.setTextColor(grayC8);
            viewHolder.textMaxDeadlineType.setTextColor(grayC8);
            viewHolder.textMinDeadline.setTextColor(grayC8);
            viewHolder.textMinDeadlineType.setTextColor(grayC8);
            viewHolder.circleProgress.setOnClickListener(null);
        }

        return convertView;
    }

    private class ItemClickListener implements OnClickListener {
        private ProductDetail product;

        public ItemClickListener(ProductDetail product) {
            super();
            this.product = product;
        }

        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId()) {
                case R.id.product_fix_list_item_root:
//                    if (isOrg) {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(ctx.getString(R.string.umeng_institution_detail_key), ctx.getString(R.string.umeng_institution_detail_product_h5info_detail));
//                        MobclickAgent.onEvent(ctx, "platform_module", map);
//                    } else {
//                        HashMap<String, String> map = new HashMap<String, String>();
//                        map.put(ctx.getString(R.string.umeng_product_infodetail_key), ctx.getString(R.string.umeng_product_infodetail));
//                        MobclickAgent.onEvent(activity, "product_module", map);
//                    }


                    intent = new Intent(activity, ProductInfoWebActivity.class);
                    String detailOpenUrl = product.getProductDetailUrl();
                    if (detailOpenUrl != null && detailOpenUrl.contains("?")) {
                        intent.putExtra("url", detailOpenUrl + "&productId=" + product.getProductId());
                    } else {
                        intent.putExtra("url", detailOpenUrl + "?productId=" + product.getProductId());
                    }
                    intent.putExtra("recommendType", C.RECOMMEND_TYPE_PRODUCT);
                    intent.putExtra("productId", product.getProductId());
                    intent.putExtra("product", product);
                    activity.startActivity(intent);

                    break;
//                case R.id.text_product_status:
                case R.id.circleProgress:

                    if (isOrg) {
                        //平台列表-推荐
                    } else {
                        //产品列表_推荐
                    }

                    if (product.getOrgIsstaticproduct().equals("1")) {    //推荐产品 是否是虚拟机构 (是否绑定机构)   orgIsstaticproduct	0 对接 1 没有对接
                        intent = new Intent(activity, RecommendProductOrOrgNoBindActivity.class);

                    } else {

                        intent = new Intent(activity, RecommendProductOrOrgActivity.class);
                    }
                    intent.putExtra("recommendType", C.RECOMMEND_TYPE_PRODUCT);
                    intent.putExtra("productId", product.getProductId());
                    intent.putExtra("product", product);
                    activity.startActivity(intent);

                    break;

            }
        }
    }

}
