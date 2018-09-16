package com.v5ent.xiubit.view;

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
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.IncomeType;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class IncomeChartPieLayout extends RelativeLayout {
    LayoutInflater mInflater;
    RelativeLayout rootView;
    private PieChartView mChart;
    private LinearLayout llSort;
    private int[] colors = {R.color.chart_pie_color1, R.color.chart_pie_color2,
            R.color.chart_pie_color3, R.color.chart_pie_color4,
            R.color.chart_pie_color5, R.color.chart_pie_color6,R.color.chart_pie_color1};

    public IncomeChartPieLayout(Context context) {
        super(context);
        init();
    }

    public IncomeChartPieLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
        rootView = (RelativeLayout) mInflater.inflate(R.layout.layout_income_chart_pie, null, false);
        addView(rootView);
        llSort = (LinearLayout) rootView.findViewById(R.id.ll_myaccount_income_sort);
        initChart();
    }

    private void initChart() {
        mChart = (PieChartView) rootView.findViewById(com.v5ent.xiubit.R.id.chart1);
        mChart.setClickable(false);
        mChart.setChartRotationEnabled(false);
        mChart.setValueTouchEnabled(false);
    }


    /**
     *
     * @param list
     * @param isDataBlank 是否为灰色图表 即数据为空
     */
    public void setChartData(List<IncomeType> list, boolean isDataBlank) {
        List<SliceValue> values = new ArrayList<SliceValue>();
        if (isDataBlank) {
            SliceValue sliceValue = new SliceValue(1, ContextCompat.getColor(getContext(),R.color.chart_pie_no_data));
            values.add(sliceValue);
        } else {
            for (int i = 0; i < list.size(); i++) {
                // 加入为零数据某些机型饼形图可能显示异常
                if(Float.parseFloat(list.get(i).getAmount())!=0){
                    SliceValue sliceValue = new SliceValue(Float.parseFloat(list.get(i).getAmount()), ContextCompat.getColor(getContext(),colors[i]));
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

    private void refreshPartMarke(List<IncomeType> list) {
        llSort.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            View sortItemView = mInflater.inflate(R.layout.item_myaccount_income_sort, null, false);
            ImageView img = (ImageView) sortItemView.findViewById(R.id.item_img_color);
            img.setBackgroundColor(ContextCompat.getColor(getContext(),colors[i]));

            TextView item_field = (TextView) sortItemView.findViewById(R.id.item_field);
            item_field.setText(list.get(i).getProfixTypeName() + "(元):");

            TextView item_value = (TextView) sortItemView.findViewById(R.id.item_value);
            item_value.setText(StringUtil.formatNumber(list.get(i).getAmount()));
            llSort.addView(sortItemView, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

}
