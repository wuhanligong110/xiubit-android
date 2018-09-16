package com.toobei.tb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.entity.ProductClassifyPreferenceDetail;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.toobei.tb.R;
import com.toobei.tb.activity.MyCfpActivity;
import com.toobei.tb.activity.ProductInfoWebActivity;
import com.toobei.tb.activity.ProlductListActivity;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.utils.Logger;
import org.xsl781.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明: t呗 首页产品分类->产品列表
 *
 * @author qingyechen
 * @time 2016/12/27 0027 下午 3:40
 */
public class ProductClassifyAdapter extends BaseListAdapter {

    private long loadSysTime;   //拉取网络数据时候的手机系统时间
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProductClassifyPreferenceDetail> list = new ArrayList<>();
    private long timeDif=0; //获取一个服务器和系统时间的差值

    public ProductClassifyAdapter(Activity activity, List<ProductClassifyPreferenceDetail> list) {
        super(activity, list);
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @SuppressWarnings("unchecked")
    public ProductClassifyAdapter(Activity activity, List<ProductClassifyPreferenceDetail> list, long loadSysTime) {
        super(activity, list);
        this.list = list;
        this.activity = activity;
        this.loadSysTime = loadSysTime;
        inflater = LayoutInflater.from(activity);
    }

    private class ViewHolder {

        TextView ivFesshTag;// 首投
        LinearLayout vgRootView;
        TextView textYearTitle;
        TextView textDeadLineTitle;
        TextView textProductName;
        TextView textProductTag1;
        TextView textProductTag2;
        TextView textProductTag3;
        TextView textYearIncome;
        TextView textYearIncome1;
        TextView textMaxDeadline;
        TextView textMaxDeadlineType;
        TextView textProductStatus;
        TextView textProdcutTitle;
        RelativeLayout headRe;
        RelativeLayout progress_bar_rl;
        TextView textProgress;
        ProgressBar progressBar;
        TextView productDescTv;
        TextView textMinDeadlineType;
        TextView textMinDeadline;
        TextView classfyTV;
        ImageView classifyIV;
        LinearLayout productDescLL;
        View homeClassifyHead;//产品分类标题
        CountDownTimer timer;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_product_home_classify_list, null);
            viewHolder.vgRootView = (LinearLayout) convertView.findViewById(R.id.product_fix_list_item_root);
            viewHolder.homeClassifyHead = convertView.findViewById(R.id.home_classifyLL);
            viewHolder.textYearTitle = (TextView) convertView.findViewById(R.id.text_year_title);
            viewHolder.textDeadLineTitle = (TextView) convertView.findViewById(R.id.text_deadline_title);
            viewHolder.textProductName = (TextView) convertView.findViewById(R.id.text_product_name);
            viewHolder.ivFesshTag = (TextView) convertView.findViewById(R.id.iv_product_fresh_tag);
            viewHolder.textProductTag1 = (TextView) convertView.findViewById(R.id.text_product_tag1);
            viewHolder.textProductTag2 = (TextView) convertView.findViewById(R.id.text_product_tag2);
            viewHolder.textProductTag3 = (TextView) convertView.findViewById(R.id.text_product_tag3);
            viewHolder.textYearIncome = (TextView) convertView.findViewById(R.id.text_year_income);
            viewHolder.textYearIncome1 = (TextView) convertView.findViewById(R.id.text_year_income1);


            viewHolder.textMinDeadline = (TextView) convertView.findViewById(R.id.text_invest_min_deadline);//最小期限
            viewHolder.textMinDeadlineType = (TextView) convertView.findViewById(R.id.text_invest_min_deadline_uni); //最小期限单位
            viewHolder.textMaxDeadline = (TextView) convertView.findViewById(R.id.text_invest_max_deadline);//最大期限
            viewHolder.textMaxDeadlineType = (TextView) convertView.findViewById(R.id.text_invest_max_deadline_uni); //最大期限单位

            viewHolder.textProductStatus = (TextView) convertView.findViewById(R.id.text_product_status);
            viewHolder.textProdcutTitle = (TextView) convertView.findViewById(R.id.titleTv);
            //产品标签
            viewHolder.headRe = (RelativeLayout) convertView.findViewById(R.id.headRe);
            viewHolder.headRe.setVisibility(View.GONE);
            viewHolder.progress_bar_rl = (RelativeLayout) convertView.findViewById(R.id.progress_bar_rl);
            viewHolder.textProgress = (TextView) convertView.findViewById(R.id.text_invest_progress);
            viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.invest_progress_bar);
            viewHolder.productDescTv = (TextView) convertView.findViewById(R.id.productDescTv);

            viewHolder.classfyTV = (TextView) convertView.findViewById(R.id.text_home_classif_name);
            viewHolder.classifyIV = (ImageView) convertView.findViewById(R.id.img_home_classify);
            viewHolder.productDescLL = (LinearLayout) convertView.findViewById(R.id.productDescLl);//tag标签栏

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ProductClassifyPreferenceDetail classifyData = list.get(position);
        viewHolder.classfyTV.setText(classifyData.getCateName());


        //  显示产品分类图标和名字
        //2-新手产品 3-短期产品 4-高收益产品 5-稳健收益产品(中长期) 801-理财师推荐产品(all?)
        // 802-热推产品 多个一起查询的时候请使用,分开 如：2,3,4,5,801,802 不传时则查询所有的产品分类
        switch (StringUtils.toInt(classifyData.getCateId())) {
            case 2:
                viewHolder.classifyIV.setImageResource(R.drawable.img_home_classify_fresh_tag);
                break;
            case 3:
                viewHolder.classifyIV.setImageResource(R.drawable.img_home_classify_short_tag);
                break;
            case 4:
                viewHolder.classifyIV.setImageResource(R.drawable.img_home_classify_high_profit_tag);
                break;
            case 5:
                viewHolder.classifyIV.setImageResource(R.drawable.img_home_classify_long_tag);
                break;

            case 802:
            case 801:
                viewHolder.classifyIV.setImageResource(R.drawable.img_home_classify_recommend_tag);
                break;
        }
        ProductDetail product = classifyData.getProductPageListResponse();
        String orgName = product.getOrgName();// 机构名
        String saleStatus = product.getStatus(); //销售状态

        //利率
        String isFlow = product.getIsFlow(); // 1=固定利率|2=浮动利率
        String fixRate = product.getProductRateText();// 年化固定利率
        if (fixRate != null) {
            fixRate = fixRate.replace("%", "");
            fixRate = StringUtil.formatNumber(fixRate);
        }
        //年化
        String flowMaxRate = product.getFlowMaxRate();
        String flowMinRate = product.getFlowMinRate();
        // 取两位小数
        flowMaxRate = StringUtil.formatNumber(flowMaxRate);
        flowMinRate = StringUtil.formatNumber(flowMinRate);

        //  String feeRatio = classifyData.getFeeRatio();//佣金
        String isHaveProgress = product.getIsHaveProgress(); // 是否有进度条


        String deadLineValueText = product.getDeadLineValueText();// 第三方产品期限
        List<String> tagList = product.getTagList();//产品标签


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

        //点击事件
        ItemClickListener itemClickListener = new ItemClickListener(classifyData);
        convertView.setOnClickListener(itemClickListener);
        viewHolder.homeClassifyHead.setOnClickListener(itemClickListener);
        viewHolder.textProductStatus.setOnClickListener(itemClickListener);


        // 设置数据
        //viewHolder.textProdcutTitle.setBackgroundResource(resId);
        viewHolder.textProductName.setText(orgName + "-" + product.getProductName());

        viewHolder.textYearIncome.setText("1".equals(isFlow) ? fixRate : flowMinRate + "~" +
                flowMaxRate);// 年化利率
//        viewHolder.textCommissionRate.setText(StringUtil.formatNumber(feeRatio)); //佣金

        /*-----------------------------------------产品期限------------------------------------------------*/
        String[] splitDatas = deadLineValueText.split(",");//deadLineValueText=30,天,2,个月
        if (splitDatas[0] != null) {
            viewHolder.textMinDeadline.setText(splitDatas[0].trim());
        }
        if (splitDatas[1] != null) {
            viewHolder.textMinDeadlineType.setText(splitDatas[1].trim());
        }

        if (splitDatas.length >= 4) {
            if (splitDatas[2] != null) {
                viewHolder.textMaxDeadline.setText("~" + splitDatas[2].trim());
            }
            if (splitDatas[3] != null) {
                viewHolder.textMaxDeadlineType.setText(splitDatas[3].trim());
            }
        } else {
            viewHolder.textMaxDeadline.setText("");
            viewHolder.textMaxDeadlineType.setText("");
        }

        /*-----------------------------------------产品tag------------------------------------------------*/
        //产品tag
        viewHolder.textProductTag1.setVisibility(View.GONE);
        viewHolder.textProductTag2.setVisibility(View.GONE);
        viewHolder.textProductTag3.setVisibility(View.GONE);
        if (tagList != null && tagList.size() > 0) {
            if (!TextUtil.isEmpty(tagList.get(0))) {
                viewHolder.textProductTag1.setVisibility(View.VISIBLE);
                viewHolder.productDescLL.setVisibility(View.VISIBLE);

                viewHolder.textProductTag1.setText(tagList.get(0));
            }
            if (tagList.size() >= 2 && !TextUtil.isEmpty(tagList.get(1))) {
                viewHolder.textProductTag2.setVisibility(View.VISIBLE);
                viewHolder.textProductTag2.setText(tagList.get(1));

            }
            if (tagList.size() >= 3 && !TextUtil.isEmpty(tagList.get(2))) {
                viewHolder.textProductTag3.setVisibility(View.VISIBLE);
                viewHolder.textProductTag3.setText(tagList.get(2));

            }
        }

        viewHolder.productDescLL.setVisibility(tagList.size() > 0 ? View.VISIBLE : View.GONE);//显示tag栏

        List<String> tagListRightNewer = product.getTagListRightNewer();
        if (tagListRightNewer != null && tagListRightNewer.size() > 0) {
            viewHolder.ivFesshTag.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivFesshTag.setVisibility(View.GONE);
        }

        //销售状态  ---->> 在售
        if ("1".equals(saleStatus)) {

            // 进度条的显示
            if ("1".equals(isHaveProgress)) { // 在售   isHaveProgress是否拥有产品进度	0=有|1没有
                viewHolder.progress_bar_rl.setVisibility(View.GONE);// 不限额不显示进度
            } else { //进度条蓝色

                viewHolder.progress_bar_rl.setVisibility(View.VISIBLE);
                int rate = (int) (buyedTotalMoney * 100 / buyTotalMoney);
                viewHolder.textProgress.setText("已售" + rate + "%");
                viewHolder.textProgress.setTextColor(ContextCompat.getColor(activity,R.color.text_gray_common));
                viewHolder.progressBar.setProgress(rate);
                viewHolder.progressBar.setBackgroundColor(ContextCompat.getColor(activity,android.R.color.white));
                viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(activity,R.drawable.invest_progressbar_color));
            }

            viewHolder.textProductTag1.setTextColor(ContextCompat.getColor(activity,R.color.text_red_common));
            viewHolder.textProductTag2.setTextColor(ContextCompat.getColor(activity,R.color.text_red_common));
            viewHolder.textProductTag3.setTextColor(ContextCompat.getColor(activity,R.color.text_red_common));
            viewHolder.textProductTag1.setBackgroundResource(R.drawable.img_red_tag_bg);
            viewHolder.textProductTag2.setBackgroundResource(R.drawable.img_red_tag_bg);
            viewHolder.textProductTag3.setBackgroundResource(R.drawable.img_red_tag_bg);
            viewHolder.textProductName.setTextColor(ContextCompat.getColor(activity,R.color.text_black_common));
            // 字体颜色
            int redColor = ContextCompat.getColor(activity,R.color.text_red_common);
            int gray96 = ContextCompat.getColor(activity,R.color.text_gray_common);
            int gray64 = ContextCompat.getColor(activity,R.color.text_gray_common_title);


            viewHolder.textYearTitle.setTextColor(gray96);
            viewHolder.textDeadLineTitle.setTextColor(gray96);


            viewHolder.textYearIncome.setTextColor(redColor);
            viewHolder.textMaxDeadline.setTextColor(gray96);


            viewHolder.textYearIncome1.setTextColor(redColor);

            viewHolder.textMaxDeadline.setTextColor(gray64);
            viewHolder.textMaxDeadlineType.setTextColor(gray64);
            viewHolder.textMinDeadline.setTextColor(gray64);
            viewHolder.textMinDeadlineType.setTextColor(gray64);
            viewHolder.vgRootView.setBackgroundResource(R.drawable.item_click_bg);

        } else if ("2".equals(saleStatus) || "3".equals(saleStatus)) {//售馨 2   募集失败3

            viewHolder.progress_bar_rl.setVisibility(View.VISIBLE);
            int rate = (int) (buyedTotalMoney * 100 / buyTotalMoney);
            viewHolder.textProgress.setText("已售" + rate + "%");
            viewHolder.textProgress.setTextColor(ContextCompat.getColor(activity,R.color.text_gray_commonDC));
            viewHolder.progressBar.setProgress(rate);
            viewHolder.progressBar.setBackgroundColor(ContextCompat.getColor(activity,R.color.WHITE));
            viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(activity,R.drawable.invest_progressbar_color_gray));
            //     }

            viewHolder.textProductStatus.setBackgroundResource(saleStatus.equals("3") ? R.drawable.img_sold_out : R.drawable.img_raise_false);
            viewHolder.textProductTag1.setTextColor(ContextCompat.getColor(activity,R.color.text_gray_common_title));
            viewHolder.textProductTag2.setTextColor(ContextCompat.getColor(activity,R.color.text_gray_common_title));
            viewHolder.textProductTag3.setTextColor(ContextCompat.getColor(activity,R.color.text_gray_common_title));
            viewHolder.textProductTag1.setBackgroundResource(R.drawable.img_gray_tag_bg);
            viewHolder.textProductTag2.setBackgroundResource(R.drawable.img_gray_tag_bg);
            viewHolder.textProductTag3.setBackgroundResource(R.drawable.img_gray_tag_bg);

            // 字体变灰
            int grayC8 = ContextCompat.getColor(activity,R.color.text_gray_common);

            viewHolder.textYearTitle.setTextColor(grayC8);
            viewHolder.textDeadLineTitle.setTextColor(grayC8);

            viewHolder.textProductName.setTextColor(grayC8);

            viewHolder.textYearIncome.setTextColor(grayC8);
            viewHolder.textMaxDeadline.setTextColor(grayC8);


            viewHolder.textYearIncome1.setTextColor(grayC8);
            viewHolder.textMaxDeadlineType.setTextColor(grayC8);
            viewHolder.textMaxDeadline.setTextColor(grayC8);
            viewHolder.textMaxDeadlineType.setTextColor(grayC8);
            viewHolder.textMinDeadline.setTextColor(grayC8);
            viewHolder.textMinDeadlineType.setTextColor(grayC8);
            viewHolder.textProductStatus.setOnClickListener(null);
        }
        //未开售显示倒计时 2017/1/3 0003
        String saleStartTime = product.getSaleStartTime();
        String timeNow = product.getTimeNow();//服务器时间
        try {

            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dateStart = simpleDateFormat.parse(saleStartTime); //开始销售时间
            Date dateServer = simpleDateFormat.parse(timeNow); //服务器时间
            long serverTime = dateServer.getTime();

            long nowSystem = System.currentTimeMillis();//现在时间
            if (loadSysTime != 0) {
                timeDif = nowSystem - loadSysTime; //从拉取数据到此条目绑定数据过了多少时间
            }else {
                timeDif = 0;
            }

            long countDownTime = dateStart.getTime() - timeDif - serverTime; //倒计时毫秒
            if (countDownTime > 0) {
                Logger.d("position=="+position + "   countDownTime===="+StringUtil.formatNumberLen2(countDownTime / 1000 / 60 / 60 + "") //
                        + ":" + StringUtil.formatNumberLen2(countDownTime / 1000 / 60 % 60 + "") //
                        + ":" + StringUtil.formatNumberLen2(countDownTime / 1000 % 60 + ""));
                final ProductClassifyAdapter.ViewHolder finalViewHolder = viewHolder;
                finalViewHolder.textProductStatus.setBackgroundResource(R.drawable.img_bg_buy_presale);

                if (viewHolder.timer != null){
                    viewHolder.timer.cancel();
                }

                viewHolder.timer = new CountDownTimer(countDownTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        finalViewHolder.textProductStatus.setText("倒计时\n" + StringUtil.formatNumberLen2(millisUntilFinished / 1000 / 60 / 60 + "") //
                                + ":" + StringUtil.formatNumberLen2(millisUntilFinished / 1000 / 60 % 60 + "") //
                                + ":" + StringUtil.formatNumberLen2(millisUntilFinished / 1000 % 60 + ""));
                    }

                    @Override
                    public void onFinish() {
                        finalViewHolder.textProductStatus.setBackgroundResource(R.drawable.img_bg_buy);
                    }
                };
                viewHolder.timer.start();
            }else{
                viewHolder.textProductStatus.setBackgroundResource(R.drawable.img_bg_buy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    public void addAll(List subDatas,long loadSysTime) {
        super.addAll(subDatas);
        this.loadSysTime = loadSysTime;
    }

    private class ItemClickListener implements View.OnClickListener {
        private ProductClassifyPreferenceDetail classifyData;


        public ItemClickListener(ProductClassifyPreferenceDetail classifyData) {
            super();
            this.classifyData = classifyData;
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.product_fix_list_item_root:
                case R.id.text_product_status:

                    intent = new Intent(activity, ProductInfoWebActivity.class);
                    String detailOpenUrl = classifyData.getProductPageListResponse().getProductDetailUrl();
                    if (detailOpenUrl != null && detailOpenUrl.contains("?")) {
                        intent.putExtra("url", detailOpenUrl + "&productId=" + classifyData.getProductPageListResponse().getProductId());
                    } else {
                        intent.putExtra("url", detailOpenUrl + "?productId=" + classifyData.getProductPageListResponse().getProductId());
                    }
                    intent.putExtra("classifyData", classifyData.getProductPageListResponse());
                    intent.putExtra("productId", classifyData.getProductPageListResponse().getProductId());
                    intent.putExtra("orgName", classifyData.getProductPageListResponse().getOrgName());
                    activity.startActivity(intent);

                    break;
                case R.id.home_classifyLL:
                    if (801 == classifyData.getCateId()) { //我的理财师
                        activity.startActivity(new Intent(activity, MyCfpActivity.class));
                    } else {
                        intent = new Intent(activity, ProlductListActivity.class);
                        intent.putExtra("cateId", classifyData.getCateId() + "");
                        intent.putExtra("cateName", classifyData.getCateName());
                        activity.startActivity(intent);
                    }


                    break;


            }
        }
    }

}
