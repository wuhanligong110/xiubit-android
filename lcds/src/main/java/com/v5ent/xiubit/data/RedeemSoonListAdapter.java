package com.v5ent.xiubit.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.TimeUtils;
import com.toobei.common.view.listView.PinnedHeaderListView;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.CustomerTrade;

import org.xsl781.data.BaseListAdapter;
import org.xsl781.ui.ViewHolder;
import org.xsl781.utils.Logger;
import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.StringUtils;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：即将赎回
 *
 * @date 2016-5-19
 */
public class RedeemSoonListAdapter extends BaseListAdapter<CustomerTrade> implements AbsListView.OnScrollListener, PinnedHeaderListView.PinnedHeaderAdapter {

    private View backToTopBtn;


    public RedeemSoonListAdapter(Context ctx, List<CustomerTrade> datas) {
        super(ctx, datas);
        this.datas = datas;
    }

    public RedeemSoonListAdapter(Context ctx, View backToTopBtn) {
        super(ctx);
        this.backToTopBtn = backToTopBtn;

    }

    public RedeemSoonListAdapter(Context ctx) {
        super(ctx);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_redeem_soon_list, null);
        }
        final CustomerTrade data = getItem(position);
        ImageView avatar = ViewHolder.findViewById(convertView, R.id.avatar);
        TextView name = ViewHolder.findViewById(convertView, R.id.text_customer_name);
        TextView phone = ViewHolder.findViewById(convertView, R.id.text_customer_phone);
        TextView investDate = ViewHolder.findViewById(convertView, R.id.text_customer_invest_date);
        TextView investDateTime = ViewHolder.findViewById(convertView, R.id.text_customer_invest_date_time);
        TextView productName = ViewHolder.findViewById(convertView, R.id.text_customer_product_name);
        TextView productYearRate = ViewHolder.findViewById(convertView, R.id.text_customer_product_year_rate);
        TextView productCommissionRate = ViewHolder.findViewById(convertView, R.id.text_customer_product_commission_rate);
        TextView endDate = ViewHolder.findViewById(convertView, R.id.text_customer_invest_enddate);

        TextView endYear = ViewHolder.findViewById(convertView, R.id.text_customer_invest_enddate_year);
        TextView investMoney = ViewHolder.findViewById(convertView, R.id.text_customer_invest_money);
        TextView customerIncome = ViewHolder.findViewById(convertView, R.id.text_customer_income);
//        TextView myCommissionIncome = ViewHolder.findViewById(convertView, R.id.text_my_commission); //我的佣金
        TextView textRedeemState = ViewHolder.findViewById(convertView, R.id.text_redeem_state); //赎回状态
        LinearLayout headLL = ViewHolder.findViewById(convertView, R.id.redeem_list_head_ll);
        View head02LL = ViewHolder.findViewById(convertView, R.id.redeem_list_head02_ll);
        ImageView timeTagIV = ViewHolder.findViewById(convertView, R.id.img_redeem_time_tag);

        MyApp.getInstance().getUserService().displayUserFace(MyApp.getInstance().getHttpService().getImageServerBaseUrl() + data.getImage(), avatar, true);
        name.setText(data.getName());
        phone.setText(data.getMobile());

        try {
            investDate.setText(data.getStartDate().split(" ")[0]);
            investDateTime.setText(data.getStartDate().split(" ")[1]);
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        productName.setText(data.getPlatfrom() + "-" + data.getProductName());
        productYearRate.setText("预期年化: " + data.getRate());
        //productCommissionRate.setText("佣金: " + data.getFeeRate());
        productCommissionRate.setVisibility(View.GONE);
//        myCommissionIncome.setText("我的佣金(元): " + data.getFeeAmt());
        customerIncome.setText("客户收益(元): " + data.getProfit());
        String nearEndDate = data.getEndDate();

        int dataResID = position % 2 == 0 ? R.drawable.img_redeem_01 : R.drawable.img_redeem_02;
        timeTagIV.setImageResource(dataResID);

        //投资状态 0=到期|1=可赎回|2=可转让|3=可赎回且可转让
        switch (StringUtils.toInt(data.getInvestState())) {
            case 0:
                textRedeemState.setText("到期");
                break;
            case 1:
                textRedeemState.setText("可赎回");
                break;
            case 2:
                textRedeemState.setText("可转让");
                break;
            case 3:
                textRedeemState.setText("可赎回且可转让");
                break;
        }
        //显示head
        headLL.setVisibility(titleEqulse(position, head02LL) ? View.GONE : View.VISIBLE);
        if (headLL.getVisibility() == View.VISIBLE) {
            head02LL.getLayoutParams().height = PixelUtil.dip2px(ctx, 12);
        }

        try {

            String[] split = nearEndDate.split(" ")[0].split("-");
            endDate.setText(split[1] + "月" + split[2] + "日");
            endYear.setText(split[0] + "年");
            //int yearResId = StringUtils.toInt(split[0]) % 2 == 0 ? R.drawable.bg_redeem_year_yellow : R.drawable.bg_redeem_year_red;

            //  endYear.setBackgroundResource(yearResId);
        } catch (Exception e) {
        }


        investMoney.setText(StringUtil.formatNumber(data.getAmt()));
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean isMove(int position) {
        // 获取当前与下一项
        CustomerTrade currentEntity = getItem(position);
        CustomerTrade nextEntity = getItem(position + 1);
        if (null == currentEntity || null == nextEntity) {
            return false;
        }

        // 获取两项header内容
        try {
            String currentType = currentEntity.getEndDate().split(" ")[0].split("-")[0];
            String nextType = nextEntity.getEndDate().split(" ")[0].split("-")[0];
            if (null == currentType || null == nextType) {
                return false;
            }
            // 当前不等于下一项header，当前项需要移动了
            if (!currentType.equals(nextType)) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }


        return false;
    }

    /**
     * 判断两个item 之间的年份和月份是不是相同
     *
     * @param position
     * @param head02LL
     * @return
     */
    private boolean titleEqulse(int position, View head02LL) {
        try {
            String endDate = getItem(position - 1).getEndDate();
            String endDate01 = getItem(position).getEndDate();
            String year = endDate.split(" ")[0].split("-")[0];
            String month = endDate.split(" ")[0].split("-")[1];
            String year01 = endDate01.split(" ")[0].split("-")[0];
            String month01 = endDate01.split(" ")[0].split("-")[1];
            head02LL.getLayoutParams().height = PixelUtil.dip2px(ctx, 12);
            if (year.equals(year01)) {
                if (!month.equals(month01)) {
                    head02LL.getLayoutParams().height = PixelUtil.dip2px(ctx, 24); //设置不同月份间隔不同
                }
                return true;
            }
        } catch (Exception e) {

        }

        return false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (view instanceof PinnedHeaderListView) {
            ((PinnedHeaderListView) view).controlPinnedHeader(firstVisibleItem);
        }
        if (backToTopBtn != null) {
            backToTopBtn.setVisibility(firstVisibleItem <= 3 ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public int getPinnedHeaderState(int position) {
        if (getCount() == 0 || position < 0) {
            return PinnedHeaderListView.PinnedHeaderAdapter.PINNED_HEADER_GONE;
        }

        if (isMove(position) == true) {
            return PinnedHeaderListView.PinnedHeaderAdapter.PINNED_HEADER_PUSHED_UP;
        }
        return PinnedHeaderListView.PinnedHeaderAdapter.PINNED_HEADER_VISIBLE;
    }

    @Override
    public void configurePinnedHeader(View headerView, int position, int alpaha) {
        TextView textYear = (TextView) headerView.findViewById(R.id.text_customer_invest_enddate_year);
        String year = TimeUtils.getYear(datas.get(position).getEndDate());
        textYear.setText(year + "年");
//        //偶数年显示黄色 奇数年显示红色背景
//        textYear.setBackgroundResource(StringUtils.toInt(year) % 2 == 0 ? R.drawable.bg_redeem_year_yellow : R.drawable.bg_redeem_year_red);

    }

    /**
     * 点击头部固定栏的操作
     */
    @Override
    public void skipTo() {

    }
}
