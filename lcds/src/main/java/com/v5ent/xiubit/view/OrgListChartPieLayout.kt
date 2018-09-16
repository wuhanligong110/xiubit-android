package com.v5ent.xiubit.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayout
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.toobei.common.entity.InvestedPlatformData
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.layout_orglist_chart_pie.view.*
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue
import java.util.*

class OrgListChartPieLayout : RelativeLayout {
    private val colors = intArrayOf(R.color.chart_pie_color1
            , R.color.chart_pie_color2
            , R.color.chart_pie_color3
            , R.color.chart_pie_color4
            , R.color.chart_pie_color5
            , R.color.chart_pie_color6
            , R.color.chart_pie_color7
            , R.color.chart_pie_color8
            , R.color.chart_pie_color9
            , R.color.chart_pie_color10
            , R.color.chart_pie_color11
            , R.color.chart_pie_color12
            , R.color.chart_pie_color13
            , R.color.chart_pie_color14
            , R.color.chart_pie_color15
            , R.color.chart_pie_color16
            , R.color.chart_pie_color17
            , R.color.chart_pie_color18
            , R.color.chart_pie_color19
            )

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        var rootView = LayoutInflater.from(context).inflate(R.layout.layout_orglist_chart_pie, null, true) as LinearLayout
        addView(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gravity = CENTER_HORIZONTAL
        initChart()
    }

    private fun initChart() {
        chart.isClickable = false
        chart.isChartRotationEnabled = false
        chart.isValueTouchEnabled = false
    }


    /**
     *  设置饼状图
     * @param list
     * @param isGray 是否为灰色图表 即数据为空
     */
    fun setChartData(list: List<InvestedPlatformData.InvestingStatisticListBean>) {
        val values = ArrayList<SliceValue>()

        var notZeroNum = 0
        // 加入为零数据某些机型饼形图可能显示异常

        list?.forEachIndexed { index, orgChartPieBean ->
                    if (orgChartPieBean.totalPercent.toFloatOrNull() ?: 0.00f != 0f) {
                        notZeroNum++
                        val sliceValue = SliceValue(orgChartPieBean.totalPercent.toFloat(), ContextCompat.getColor(context, colors[index % colors.size]))
                        values.add(sliceValue)
                    }
                }

        if (list == null || notZeroNum == 0) { //没有符合条件的
            val sliceValue = SliceValue(1f, ContextCompat.getColor(context, R.color.chart_pie_no_data))
            values.add(sliceValue)
        }
        val data = PieChartData(values)
        data.setHasLabels(false)
        data.setHasLabelsOnlyForSelected(false)
        data.setHasLabelsOutside(false)
        data.setHasCenterCircle(true)
        chart.pieChartData = data

        refreshPartMarke(list)
    }

    /**
     * 设置标识信息统计显示
     */
    private fun refreshPartMarke(list: List<InvestedPlatformData.InvestingStatisticListBean>) {
        if (list == null) return
        orgInfoGl.removeAllViews()
        val rowNum = orgInfoGl.columnCount
        for (i in list.indices) {
            val sortItemView = LayoutInflater.from(context).inflate(R.layout.item_org_pic_info, null, true) as LinearLayout
            val colorTagIv = sortItemView.findViewById<ImageView>(R.id.colorTagIv)
            val orgNameTv = sortItemView.findViewById<TextView>(R.id.orgNameTv)
            val percentTv = sortItemView.findViewById<TextView>(R.id.percentTv)

            colorTagIv.setBackgroundColor(ContextCompat.getColor(context, colors[i % colors.size]))
            orgNameTv.text = list[i].orgName
            percentTv.text = "${list[i]?.totalPercent ?: 0.00}%"
            
            if (i%2 == 1) sortItemView.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
            //使用Spec定义子控件的位置和比重
            val rowSpec =GridLayout.spec(i / rowNum, 1f)
            val columnSpec = GridLayout.spec(i % rowNum, 1f)
            //将Spec传入GridLayout.LayoutParams并设置宽高为0，必须设置宽高，否则视图异常
            val lp = GridLayout.LayoutParams(rowSpec, columnSpec)
            lp.height = GridLayout.LayoutParams.WRAP_CONTENT
            lp.width = 0
            orgInfoGl.addView(sortItemView,lp)
        }
    }

    data class OrgChartPieBean(var name: String, var percent: String)

}
