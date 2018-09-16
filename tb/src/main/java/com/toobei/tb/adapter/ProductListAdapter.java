package com.toobei.tb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.toobei.tb.R;
import com.toobei.tb.activity.ProductInfoWebActivity;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 产品列表adapter
 */
public class ProductListAdapter extends BaseListAdapter {

    private boolean noOrgName;  //产品名称是否需要拼接机构名称
    private Activity activity;
    private LayoutInflater inflater;
    private List<ProductDetail> list;
    public OnBtnRecommendClicked onBtnRecommendClicked;
    private boolean isCfpRecommend = false; //是否是理财师热推



    private String timeServer=""; //服务器时间
    private long timeDif=0; //获取一个服务器和系统时间的差值
    private long loadSysTime;  //拉取服务器数据的时间

    public interface OnBtnRecommendClicked {
        void onBtnRecommendClicked(String curRecommendProductName, ProductDetail product);
    }

    @SuppressWarnings("unchecked")
    public ProductListAdapter(Activity activity, List<ProductDetail> list) {
        super(activity, list);
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }


    public ProductListAdapter(Activity activity, List<ProductDetail> list, boolean isCfpRecommend) {
        super(activity, list);
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        this.isCfpRecommend = isCfpRecommend;
    }

    /**
     * 设置产品名称是否需要带机构名称
     */
    public void setNoOrgName(boolean noOrgName){
        this.noOrgName = noOrgName;
    }

    private class ViewHolder {

        TextView ivFesshTag;// 新手标
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
        LinearLayout productDescLL;
        TextView textMinDeadlineType;
        TextView textMinDeadline;

        TextView textPosition;

         CountDownTimer timer;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        // 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_product_list, null);


            viewHolder = new ViewHolder();
            viewHolder.vgRootView = (LinearLayout) convertView.findViewById(R.id.product_fix_list_item_root);
            viewHolder.textYearTitle = (TextView) convertView.findViewById(R.id.text_year_title);
            viewHolder.textDeadLineTitle = (TextView) convertView.findViewById(R.id.text_deadline_title);
            viewHolder.textProductName = (TextView) convertView.findViewById(R.id.text_product_name);
            viewHolder.textPosition = (TextView) convertView.findViewById(R.id.text_product_position);
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
            viewHolder.productDescLL = (LinearLayout) convertView.findViewById(R.id.productDescLl);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textPosition.setVisibility(isCfpRecommend ? View.VISIBLE : View.GONE); //理财师热推时(802)显示排序
        viewHolder.textPosition.setText((position + 1) + "");
        final ProductDetail product = list.get(position);
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

        String isHaveProgress = product.getIsHaveProgress(); // 是否有进度条

        List<String> tagRightList = product.getTagListRightNewer();   //tagListRightNewer	新手专享标签	array<string>
        String tagListRight = "";

        if (tagRightList.size() > 0) {
            tagListRight = tagRightList.get(0);// 右上角首投
        }
        String deadLineValueText = product.getDeadLineValueText();// 第三方产品期限

        List<String> tagList = product.getTagList();// 产品底部标签

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
        ItemClickListener itemClickListener = new ItemClickListener(product);
        convertView.setOnClickListener(itemClickListener);


        // 设置数据
        if (noOrgName) {
            viewHolder.textProductName.setText(product.getProductName());
        }else {
            viewHolder.textProductName.setText(orgName + "-" + product.getProductName());
        }
        viewHolder.textYearIncome.setText("1".equals(isFlow) ? fixRate : flowMinRate + "~" +
                flowMaxRate);// 年化利率


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


            //销售状态  ---->> 售罄
        } else if ("2".equals(saleStatus) || "3".equals(saleStatus)) {//售馨 2   募集失败3


            viewHolder.progress_bar_rl.setVisibility(View.VISIBLE);
            int rate = (int) (buyedTotalMoney * 100 / buyTotalMoney);
            viewHolder.textProgress.setText("已售" + rate + "%");
            viewHolder.textProgress.setTextColor(ContextCompat.getColor(activity,R.color.text_gray_commonDC));
            viewHolder.progressBar.setProgress(rate);
            viewHolder.progressBar.setBackgroundColor(ContextCompat.getColor(activity,R.color.WHITE));
            viewHolder.progressBar.setProgressDrawable(ContextCompat.getDrawable(activity,R.drawable.invest_progressbar_color_gray));

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
            long serverTime = dateServer.getTime();  //服务器时间毫秒值

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
                final ViewHolder finalViewHolder = viewHolder;
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
            }else {
                viewHolder.textProductStatus.setBackgroundResource(R.drawable.img_bg_buy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ItemClickListener implements View.OnClickListener {
        private ProductDetail product;

        public ItemClickListener(ProductDetail product) {
            super();
            this.product = product;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.product_fix_list_item_root:
                    // 	if ("1".equals(product.getOpenType())) { //web 打开
                    Intent intent = new Intent(activity, ProductInfoWebActivity.class);
                    String detailOpenUrl = product.getProductDetailUrl();
                    if (detailOpenUrl != null && detailOpenUrl.contains("?")) {
                        intent.putExtra("url", detailOpenUrl + "&productId=" + product.getProductId());
                    } else {
                        intent.putExtra("url", detailOpenUrl + "?productId=" + product.getProductId());
                    }
                    intent.putExtra("productId", product.getProductId());
                    intent.putExtra("orgName", product.getOrgName());
                    intent.putExtra("product", product);
                    activity.startActivity(intent);
                    //    activity.startActivityForResult(intent, C.REQUEST_PRODUCTS_LIST);
//				} else {
//					Intent intent = new Intent(activity, ProductInfoActivity.class);
//					intent.putExtra("product", product);
//					activity.startActivityForResult(intent, C.REQUEST_PRODUCTS_LIST);
//				}
                    break;
                case R.id.text_product_status:

                    ToastUtil.showCustomToast("购买====");
                    if (onBtnRecommendClicked != null) {

                        onBtnRecommendClicked.onBtnRecommendClicked("", product);

                    }
                    break;

            }
        }
    }

    public void setOnBtnRecommendClicked(OnBtnRecommendClicked onBtnRecommendClicked) {
        this.onBtnRecommendClicked = onBtnRecommendClicked;
    }


    public void notifyDataSetChanged(long loadSysTime) {
        super.notifyDataSetChanged();
        this.loadSysTime = loadSysTime;
    }

    public void addAll(List subDatas,long loadSysTime) {
        super.addAll(subDatas);
        this.loadSysTime = loadSysTime;
    }
}
