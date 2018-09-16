package com.v5ent.xiubit.data.recylerapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.StringUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.ProductInfoWebActivity;
import com.v5ent.xiubit.util.C;
import com.umeng.analytics.MobclickAgent;

import java.util.List;


/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/22
 */

public class ProductListRecycleAdapter extends BaseQuickAdapter<ProductDetail, BaseViewHolder> {
    Context ctx;
    private long loadSysTime;
    private int fromTag;
    public static final int FROM_HOME_PAGE = 1;  //首页
    public static final int FROM_INVEST = 2;  //投资

    public ProductListRecycleAdapter(Context context) {
        super(R.layout.item_product_list);
        this.ctx = context;
    }

    public void setFromTag(int tag) {
        this.fromTag = tag;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ProductDetail item) {
        int position = helper.getAdapterPosition();
        final ProductDetail bean = item;
        String imageUrL = MyApp.getInstance().getHttpService().getImageUrlFormMd5(bean.getProductLogo());
        Glide.with(ctx).load(imageUrL).into((ImageView) helper.getView(R.id.orgLogoIv));
        helper.setText(R.id.productName, bean.getProductName())
                .setText(R.id.feeRatioTv, StringUtil.formatNumber(bean.getFeeRatio()));

        LinearLayout righTagContantLi = helper.getView(R.id.righTagContantLi);

        righTagContantLi.removeAllViews();
        if (item.getTagList() != null && item.getTagList().size() > 0) {
            for (String str : item.getTagList()) {
                TextView tv = (TextView) View.inflate(mContext, R.layout.text_product_detial_tager, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, (int) (mContext.getResources().getDimension(R.dimen.w9)), 0);//4个参数按顺序分别是左上右下
                tv.setLayoutParams(layoutParams);
                tv.setText(str);
                righTagContantLi.addView(tv);
            }
        }


        //年化收益率
        String isFlow = bean.getIsFlow(); // 1=固定利率|2=浮动利率
        String fixRate = bean.getProductRateText();// 年化固定利率
        if (fixRate != null) {
            fixRate = fixRate.replace("%", "");
            fixRate = StringUtil.formatNumber(fixRate);
        }
        //年化 取两位小数
        String flowMaxRate = bean.getFlowMaxRate();
        String flowMinRate = bean.getFlowMinRate();
        flowMaxRate = StringUtil.formatNumber(flowMaxRate);
        flowMinRate = StringUtil.formatNumber(flowMinRate);
        //4.5.6 浮动年化取最小值
        helper.setText(R.id.yearProfitTv, "1".equals(isFlow) ? fixRate : flowMinRate);// 年化利率


        //产品期限
        String deadLineValueStr = bean.getDeadLineValueText();
        if (deadLineValueStr != null) {

            String[] splitDatas = deadLineValueStr.split(",");//deadLineValueText=30,天,2,个月
            if (splitDatas[0] != null) {
                helper.setText(R.id.minDeadLineTv, splitDatas[0]);
            }
            if (splitDatas.length > 1 && splitDatas[1] != null) {
                helper.setText(R.id.minDeadLineTypeTv, splitDatas[1]);
            }

            if (splitDatas.length >= 4) {
                if (splitDatas[2] != null) {
                    helper.setText(R.id.maxDeadLineTv, "~" + splitDatas[2]);
                }
                if (splitDatas[3] != null) {
                    helper.setText(R.id.maxDeadLineTypeTv, splitDatas[3]);
                }
            } else {
                helper.setText(R.id.maxDeadLineTv, "");
                helper.setText(R.id.maxDeadLineTypeTv, "");
            }
        }

        //跳转产品详情
        final String productDetailUrl = bean.getProductDetailUrl();
        final String productId = bean.getProductId();
        helper.setOnClickListener(R.id.rootView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fromTag) {
                    case FROM_HOME_PAGE:
                        MobclickAgent.onEvent(mContext, "S_3_2");
                        break;
                    case FROM_INVEST:
                        MobclickAgent.onEvent(mContext, "T_2_2");
                        break;
                }
                MobclickAgent.onEvent(mContext, "T_2_2");
                Intent intent = new Intent(ctx, ProductInfoWebActivity.class);
//                if (("OPEN_JIUFUQINGZHOU_WEB").equals(bean.getOrgNumber())) {
//                    //跳转玖富产品详情页
//                    new JumpJiuhuService(bean.jfqzProductDetailUrl,bean.getThirdProductId(),"",ctx).skipDetialPage();
//                } else {
                    if (productDetailUrl != null) {
                        String urlconnect = productDetailUrl.contains("?") ? "&" : "?";
                        intent.putExtra("url", productDetailUrl + urlconnect + "productId=" + productId);
                    }
                    intent.putExtra("recommendType", C.RECOMMEND_TYPE_PRODUCT);
                    intent.putExtra("productId", bean.getProductId());
                    intent.putExtra("product", bean);
                    ctx.startActivity(intent);
//                }
            }

        });
        helper.setText(R.id.leftMoneyTv, formatToWan(""+ (Double.parseDouble(bean.getBuyTotalMoney())-Double.parseDouble(bean.getBuyedTotalMoney())) ));
        //募集进度 status; //saleStatus	产品状态	number	1-在售|2-售罄|3-募集失败
//        helper.setVisible(R.id.soldOutIv, item.getStatus().equals("1") ? false : true)
//                .setVisible(R.id.circleProgress, item.getStatus().equals("1") ? true : false)
//                .setVisible(R.id.progressTextLl, item.getStatus().equals("1") ? true : false);

//        double buyTotalMoney = 0;
//        double buyedTotalMoney = 0;
//        String buyedTotalMoneyStr = bean.getBuyedTotalMoney();//已募集总额(单位元)
//        String buyTotalMoneyStr = bean.getBuyTotalMoney();
//
//
//
//        if (buyedTotalMoneyStr != null && buyedTotalMoneyStr.length() > 0) {
//            buyedTotalMoney = Double.parseDouble(buyedTotalMoneyStr);
//        }
//        if (buyTotalMoneyStr != null && buyTotalMoneyStr.length() > 0) {
//            buyTotalMoney = Double.parseDouble(bean.getBuyTotalMoney());
//        }
//        int rate = (int) (buyedTotalMoney * 100 / buyTotalMoney);
//        ProductCircleProgressbar mCircleProgress = helper.getView(R.id.circleProgress);
//        mCircleProgress.setProgress(rate);
//        helper.setText(R.id.progressTv, rate + "");
//
//        //未开售显示倒计时 2017/1/3 0003
//        final TextView buyTimeTv = helper.getView(R.id.productBuyTimeTv);
//        CountDownTimer timer = (CountDownTimer) buyTimeTv.getTag();
//        long timeDif = 0;
//        try {
//
//            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
////            Date dateStart = simpleDateFormat.parse("2017-08-28 14:"+helper.getAdapterPosition()%60+":"+ helper.getAdapterPosition() * 2%60); //开始销售时间
//            Date dateStart = simpleDateFormat.parse(item.getSaleStartTime()); //开始销售时间
//            Date dateServer = simpleDateFormat.parse(item.getTimeNow()); //服务器时间
//            long serverTime = dateServer.getTime();  //服务器时间毫秒值
//
//            long nowSystem = System.currentTimeMillis();//现在时间
//            if (loadSysTime != 0) {
//                timeDif = nowSystem - loadSysTime; //从拉取数据到此条目绑定数据过了多少时间
//            }
//            long countDownTime = dateStart.getTime() - timeDif - serverTime; //倒计时毫秒
//            //募集进度 status; //saleStatus	产品状态	number	1-在售|2-售罄|3-募集失败
//            helper.setVisible(R.id.soldOutIv, countDownTime > 0 ? false : (item.getStatus().equals("1") ? false : true))
//                    .setVisible(R.id.circleProgress, item.getStatus().equals("1") ? true : false)
//                    .setVisible(R.id.progressTextLl, countDownTime > 0 ? false : (item.getStatus().equals("1") ? true : false));
//            if (countDownTime > 0) {
//
//                Logger.d("position==" + position + "   countDownTime====" + StringUtil.formatNumberLen2(countDownTime / 1000 / 60 / 60 + "") //
//                        + ":" + StringUtil.formatNumberLen2(countDownTime / 1000 / 60 % 60 + "") //
//                        + ":" + StringUtil.formatNumberLen2(countDownTime / 1000 % 60 + ""));
//                if (timer != null) {
//                    timer.cancel();
//                    timer = null;
//                }
//                timer = new CountDownTimer(countDownTime, 1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        buyTimeTv.setText("倒计时\n" + StringUtil.formatNumberLen2(millisUntilFinished / 1000 / 60 / 60 + "") //
//                                + ":" + StringUtil.formatNumberLen2(millisUntilFinished / 1000 / 60 % 60 + "") //
//                                + ":" + StringUtil.formatNumberLen2(millisUntilFinished / 1000 % 60 + ""));
//
//                    }
//
//                    @Override
//                    public void onFinish() {
//                        buyTimeTv.setVisibility(View.GONE);
//                        helper.setVisible(R.id.soldOutIv, item.getStatus().equals("1") ? false : true)
//                                .setVisible(R.id.circleProgress, item.getStatus().equals("1") ? true : false)
//                                .setVisible(R.id.progressTextLl, item.getStatus().equals("1") ? true : false);
//                    }
//                };
//
//                buyTimeTv.setTag(timer);
//                timer.start();
//                buyTimeTv.setVisibility(View.VISIBLE);
//            } else {
//                buyTimeTv.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    /**
     * B、剩余额度显示：

     1）、当剩余金额≥1万元时，剩余金额显示：以万为单位，保留小数点后一位，如：112.5万；

     2）、当剩余金额＜1万元时，剩余金额显示：以1为单位，数值取整，如：1125；
     * @param number
     * @return
     */
    public static String formatToWan(String number) {
        if (number == null || number.length() == 0) {
            return "0.00";
        }
        Double d = 0.00;
        try {
            d = Double.parseDouble(number);

            if (d >= 10000) {
                return String.format("%.1f", d / 10000) + "万";
            }else if(d>0){
                return String.format("%.0f", d );
            }else{
                return String.format("%.0f", d );
            }

        } catch (Exception e) {
            return number;
        }
    }


    public void setNewData(List<ProductDetail> datas, long loadSysTime) {
        this.loadSysTime = loadSysTime;
        super.setNewData(datas);
    }

    public void addData(List<ProductDetail> datas, long loadSysTime) {
        this.loadSysTime = loadSysTime;
        super.addData(datas);
    }
}
