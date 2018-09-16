package com.toobei.tb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

import org.xsl781.utils.PixelUtil;

/**
 * 公司: tophlc
 * 类说明：收益视图 控件 3.8%-12.1%
 * @date 2015-9-30
 */
public class IncomeView extends View {

	private int textColor = Color.RED;
	private float minValue = -10, maxValue = -1;
	private int intSize = PixelUtil.sp2px(getContext(), 30), floatSize = PixelUtil.sp2px(
			getContext(), 12);

	public IncomeView(Context context) {
		super(context);
	}

	public IncomeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public IncomeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 无值时，传-1
	 * 单一数字时，传minValue
	 */
	public IncomeView(Context context, int minValue, int maxValue) {
		super(context);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	private String[] getParts(float f) {
		String str = String.format("%.1f", f);
		String arr[] = str.split("[.]");
		return arr;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setAntiAlias(true);// 设置画笔的锯齿效果  

	/*	//画日期
		p.setTextSize(intSize);
		p.setColor(textColor);
		float tYDate = (getHeight() - getFontHeight(p)) / 2 + getFontLeading(p);
		float dateStrLen = getFontlength(p, date);
		canvas.drawText(date, 10, tYDate, p);

		//画当前值
		p.setTextSize(scalaFonts(curValueSize));
		p.setColor(curValueColor);
		float tYCurValue = (getHeight() - getFontHeight(p)) / 2 + getFontLeading(p);
		String strCurValue = String.format("%.2f", curValue * 0.01f);
		float tXCurValue = getFontlength(p, strCurValue);
		//当前值的坐标 > 日期右边+40 ,则显示当前值
		if (progressX - tXCurValue - 10 > dateStrLen + 40) {
			canvas.drawText(strCurValue, progressX - tXCurValue - 10, tYCurValue, p);
		}*/
	}

	/**  
	 * 根据屏幕系数比例获取文字大小  
	 * @return  
	 */
	private static float scalaFonts(int size) {
		//暂未实现  
		return size;
	}

	/**  
	 * @return 返回指定笔和指定字符串的长度  
	 */
	public static float getFontlength(Paint paint, String str) {
		return paint.measureText(str);
	}

	/**  
	 * @return 返回指定笔的文字高度  
	 */
	public static float getFontHeight(Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		return fm.descent - fm.ascent;
	}

	/**  
	 * @return 返回指定笔离文字顶部的基准距离  
	 */
	public static float getFontLeading(Paint paint) {
		FontMetrics fm = paint.getFontMetrics();
		return fm.leading - fm.ascent;
	}

}
