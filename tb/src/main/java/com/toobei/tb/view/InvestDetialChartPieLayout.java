package com.toobei.tb.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.utils.StringUtil;
import com.toobei.tb.R;
import com.toobei.tb.entity.InvestProfitData;
import com.toobei.tb.entity.InvestProfitType;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;


/**
 * 投资明细饼状图
 */
public class InvestDetialChartPieLayout extends RelativeLayout {
    LayoutInflater mInflater;
    RelativeLayout rootView;
    private PieChartView mChart;
    private LinearLayout llSort;
    private int[] colors = {R.color.chart_pie_color1, R.color.chart_pie_color2, R.color.chart_pie_color3, R.color.chart_pie_color4, R.color.chart_pie_color5, R.color.chart_pie_color6};


    public InvestDetialChartPieLayout(Context context) {
        super(context);
        init();
    }

    public InvestDetialChartPieLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
        rootView = (RelativeLayout) mInflater.inflate(R.layout.invest_detial_chart_pie, null, false);
        addView(rootView);
        llSort = (LinearLayout) rootView.findViewById(R.id.ll_invest_detail_income_sort);
        initChart();
    }

    private void initChart() {
        mChart = (PieChartView) rootView.findViewById(R.id.chart1);
        mChart.setClickable(false);
        mChart.setChartRotationEnabled(false);
        mChart.setValueTouchEnabled(false);
    }

    public void setInvestProfitData(InvestProfitData data) {
        llSort.removeAllViews();
        List<InvestProfitType> list = new ArrayList<>();
        if (data != null) {
            list.add(new InvestProfitType("募集中的收益", data.getInvestAmt()));
            list.add(new InvestProfitType("回款中的收益", data.getPaymentAmt()));
            list.add(new InvestProfitType("已完成的收益", data.getPaymentDoneAmt()));
            Boolean b = 0 == Float.parseFloat(data.getInvestAmt())
                    && 0 == Float.parseFloat(data.getPaymentAmt())
                    && 0 == Float.parseFloat(data.getPaymentDoneAmt());
            setChartData(list, b);
        }
    }

    private void refreshPartMarke(List<InvestProfitType> list) {

        for (int i = 0; i < list.size(); i++) {
            View sortItemView = mInflater.inflate(R.layout.item_invest_detial_sort, null, false);
            ImageView img = (ImageView) sortItemView.findViewById(R.id.item_img_color);
            img.setBackgroundColor(ContextCompat.getColor(getContext(),colors[i]));

            TextView item_field = (TextView) sortItemView.findViewById(R.id.item_field);
            item_field.setText(list.get(i).getName() + "(元):");

            TextView item_value = (TextView) sortItemView.findViewById(R.id.item_value);
            item_value.setText(StringUtil.formatNumber(list.get(i).getValue()));
            llSort.addView(sortItemView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    /**
     * @param list
     * @param isGray 是否为灰色图表 即数据为空
     */
    public void setChartData(List<InvestProfitType> list, boolean isGray) {
        List<SliceValue> values = new ArrayList<SliceValue>();
        if (isGray) {
            SliceValue sliceValue = new SliceValue(1, ContextCompat.getColor(getContext(),R.color.chart_pie_gray));
            values.add(sliceValue);
        } else {
            for (int i = 0; i < list.size(); i++) {
                // TODO: 2017/1/9 0009  加入为零数据某些机型饼形图可能显示异常
                if (Float.parseFloat(list.get(i).getValue()) != 0) {
                    SliceValue sliceValue = new SliceValue(Float.parseFloat(list.get(i).getValue()), ContextCompat.getColor(getContext(),colors[i]));
                    values.add(sliceValue);
                }

            }
        }
        PieChartData data = new PieChartData(values);
        data.setHasLabels(false);
        data.setHasLabelsOnlyForSelected(false);
        data.setHasLabelsOutside(false);
        data.setHasCenterCircle(true);
        mChart.setPieChartData(data);

        refreshPartMarke(list);
    }

}
