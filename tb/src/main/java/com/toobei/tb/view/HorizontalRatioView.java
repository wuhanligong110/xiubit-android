package com.toobei.tb.view;

import org.xsl781.utils.PixelUtil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.toobei.tb.R;

public class HorizontalRatioView extends View {

	private int backgroundColor = R.color.DARK_GRAYC8;
	private int progressColor = Color.WHITE;
	private int dateColor = Color.BLUE;
	private int curValueColor = Color.BLACK;
	private int minValue = 0, maxValue = 100, curValue = 0;
	private int roundX = PixelUtil.sp2px(getContext(), 6), roundY = PixelUtil
			.sp2px(getContext(), 4);//x,y的圆角半径
	private int dateSize = PixelUtil.sp2px(getContext(), 12), curValueSize = PixelUtil.sp2px(
			getContext(), 14);
	private String date;

	//private int width,height;

	public HorizontalRatioView(Context context) {
		super(context);
	}

	public HorizontalRatioView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public HorizontalRatioView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalRatioView(Context context, int maxValue, int curValue, String date) {
		super(context);
		this.curValue = curValue;
		this.maxValue = maxValue;
		this.date = date;
	}

	public void initParam(int maxValue, int curValue, String date) {
		this.maxValue = maxValue;
		this.curValue = curValue;
		this.date = date;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//	width = widthMeasureSpec;
		//	height = heightMeasureSpec;

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);//充满  
		p.setAntiAlias(true);// 设置画笔的锯齿效果  

		//画背景 圆角矩形  
		p.setColor(backgroundColor);
		RectF oval1 = new RectF(0, 0, getWidth(), getHeight());
		canvas.drawRoundRect(oval1, roundX, roundY, p);//第二个参数是x半径，第三个参数是y半径  

		//画进度 圆角矩形  
		p.setColor(progressColor);
		int progressX = getWidth() * (curValue - minValue) / (maxValue - minValue);
		RectF oval2 = new RectF(0, 0, progressX, getHeight());
		canvas.drawRoundRect(oval2, roundX, roundY, p);

		//画日期
		p.setTextSize(scalaFonts(dateSize));
		p.setColor(dateColor);
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
		}
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

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getProgressColor() {
		return progressColor;
	}

	public void setProgressColor(int progressColor) {
		this.progressColor = progressColor;
	}

	public int getDateColor() {
		return dateColor;
	}

	public void setDateColor(int dateColor) {
		this.dateColor = dateColor;
	}

	public int getCurValueColor() {
		return curValueColor;
	}

	public void setCurValueColor(int curValueColor) {
		this.curValueColor = curValueColor;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getCurValue() {
		return curValue;
	}

	public void setCurValue(int curValue) {
		this.curValue = curValue;
	}

	public int getRoundX() {
		return roundX;
	}

	public void setRoundX(int roundX) {
		this.roundX = roundX;
	}

	public int getRoundY() {
		return roundY;
	}

	public void setRoundY(int roundY) {
		this.roundY = roundY;
	}

	public int getDateSize() {
		return dateSize;
	}

	public void setDateSize(int dateSize) {
		this.dateSize = dateSize;
	}

	public int getCurValueSize() {
		return curValueSize;
	}

	public void setCurValueSize(int curValueSize) {
		this.curValueSize = curValueSize;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
