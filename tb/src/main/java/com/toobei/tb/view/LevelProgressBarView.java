package com.toobei.tb.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.toobei.tb.R;

import org.xsl781.utils.PixelUtil;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：级别进度条  主要用于职级 任务 晋级情况
 * @date 2015-12-29
 */
public class LevelProgressBarView extends View {
	private List<String> topContents;//级别上方的文字
	private List<String> bottomContents;//级别 下方的文字
	private int pointLeftDefaultDrawableRes;//级别 最左边 的 默认的背景资源
	private int pointLeftLightDrawableRes;//级别 最左边 的 默认的背景资源
	private int pointMiddleDefaultDrawableRes;//级别 中间 的 默认的背景资源
	private int pointMiddleLightDrawableRes;//级别 中间 的 默认的背景资源
	private int pointRightDefaultDrawableRes;//级别 最右边 的 默认的背景资源
	private int pointRightLightDrawableRes;//级别 最右边 的 默认的背景资源
	private int progressDefaultDrawableRes;//进度条 默认的图片资源 
	private int progressLightDrawableRes;//进度条 点亮的图片资源 
	private int levelMax = 6;
	private int curLevel = 2;//当前级别
	private int curLevelProgress = 40;//当前级别进度  单位%

	private float levelPointHeight = PixelUtil.dip2px(getContext(), 22);//点的高度
	private float levelProgressHeight = PixelUtil.dip2px(getContext(), 5);//线的高度
	private float eachProgressLen;//每段进度条的长度 

	private int contentColorRes = R.color.WHITE;
	private int contentSize = PixelUtil.sp2px(getContext(), 12);
	private float width, height;
	private float margin = PixelUtil.dip2px(getContext(), 10);
	private float marginBottom = PixelUtil.dip2px(getContext(), 4);

	private Rect mSrcRect, mDestRect;

	public LevelProgressBarView(Context context) {
		super(context);
	}

	public void initParams(int pointLeftDefaultDrawableRes, int pointLeftLightDrawableRes,
			int pointMiddleDefaultDrawableRes, int pointMiddleLightDrawableRes,
			int pointRightDefaultDrawableRes, int pointRightLightDrawableRes,
			int progressDefaultDrawableRes, int progressLightDrawableRes, int levelMax,
			int curLevel, int curLevelProgress) {
		this.pointLeftDefaultDrawableRes = pointLeftDefaultDrawableRes;
		this.pointLeftLightDrawableRes = pointLeftLightDrawableRes;
		this.pointMiddleDefaultDrawableRes = pointMiddleDefaultDrawableRes;
		this.pointMiddleLightDrawableRes = pointMiddleLightDrawableRes;
		this.pointRightDefaultDrawableRes = pointRightDefaultDrawableRes;
		this.pointRightLightDrawableRes = pointRightLightDrawableRes;
		this.progressDefaultDrawableRes = progressDefaultDrawableRes;
		this.progressLightDrawableRes = progressLightDrawableRes;
		this.levelMax = levelMax;
		this.curLevel = curLevel;
		this.curLevelProgress = curLevelProgress;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	private void calcSize() {
		margin = (width / levelMax - levelPointHeight) / 2;
		eachProgressLen = (width - levelMax * levelPointHeight - margin * 2) / (levelMax - 1);
	}

	/**
	 * 功能：画 圆点
	 * @param canvas
	 * @param p
	 */
	private void drawLevelPoint(Canvas canvas, Paint p) {
		mSrcRect = new Rect(0, 0, (int) Math.ceil(levelPointHeight),
				(int) Math.ceil(levelPointHeight));
		Bitmap bitmap = null;
		int left, top, right, bottom;
		top = (int) (height / 2 - levelPointHeight / 2);
		bottom = (int) Math.ceil(top + levelPointHeight);
		for (int i = 1; i <= levelMax; i++) {
			//			System.out.println("=== drawLevelPoint  i = " + i + " , width = " + width
			//					+ ", height = " + height);
			left = (int) (margin + (levelPointHeight + eachProgressLen) * (i - 1));
			right = (int) Math.ceil(left + levelPointHeight);
			mDestRect = new Rect(left, top, right, bottom);
			if (i == 1) {
				//最左边，直接点亮
				bitmap = BitmapFactory.decodeResource(getResources(), pointLeftLightDrawableRes);
			} else if (i < levelMax) {
				//中间的 分两种
				if (curLevel < levelMax && i <= curLevel) {
					bitmap = BitmapFactory.decodeResource(getResources(),
							pointMiddleLightDrawableRes);
				} else {
					bitmap = BitmapFactory.decodeResource(getResources(),
							pointMiddleDefaultDrawableRes);
				}
			} else {
				//最右边，分两种
				if (curLevel < levelMax) {
					bitmap = BitmapFactory.decodeResource(getResources(),
							pointRightDefaultDrawableRes);
				} else {
					bitmap = BitmapFactory.decodeResource(getResources(),
							pointRightLightDrawableRes);
				}
			}
			canvas.drawBitmap(bitmap, mSrcRect, mDestRect, p);
		}
	}

	/**
	 * 功能：画 进度条
	 * @param canvas
	 * @param p
	 */
	private void drawLevelProgress(Canvas canvas, Paint p) {
		mSrcRect = new Rect(0, 0, (int) Math.ceil(eachProgressLen)
				+ PixelUtil.dip2px(getContext(), 1), (int) Math.ceil(levelProgressHeight));
		Bitmap bitmap = null;
		int left, top, right, bottom;
		top = (int) (height / 2 - levelProgressHeight / 2);
		bottom = (int) Math.ceil(top + levelProgressHeight);
		for (int i = 1; i < levelMax; i++) {
			if (i != curLevel) {
				left = (int) (margin + levelPointHeight + (levelPointHeight + eachProgressLen)
						* (i - 1))
						- PixelUtil.dip2px(getContext(), 1);
				//靠右边有间隙，长1dp
				right = (int) Math.ceil(left + eachProgressLen) + PixelUtil.dip2px(getContext(), 1);
				mDestRect = new Rect(left, top, right, bottom);
				if (i < curLevel) {
					//少于当前级别 全点亮
					bitmap = BitmapFactory.decodeResource(getResources(), progressLightDrawableRes);
				} else {
					//大于当前级别 的情况，全不亮
					bitmap = BitmapFactory.decodeResource(getResources(),
							progressDefaultDrawableRes);
				}
				canvas.drawBitmap(bitmap, mSrcRect, mDestRect, p);
			} else {
				//在当前级别时，按进度条分开
				//先画左边进度，点亮部分
				left = (int) (margin + levelPointHeight + (levelPointHeight + eachProgressLen)
						* (i - 1))
						- PixelUtil.dip2px(getContext(), 1);
				right = (int) Math.ceil(left + eachProgressLen * curLevelProgress / 100);
				mDestRect = new Rect(left, top, right, bottom);
				bitmap = BitmapFactory.decodeResource(getResources(), progressLightDrawableRes);
				canvas.drawBitmap(bitmap, mSrcRect, mDestRect, p);

				//再画右边进度，不亮部分
				left = right;
				//靠右边有间隙，长1dp
				right = (int) Math.ceil(left + eachProgressLen * (100 - curLevelProgress) / 100)
						+ PixelUtil.dip2px(getContext(), 1);
				mDestRect = new Rect(left, top, right, bottom);
				bitmap = BitmapFactory.decodeResource(getResources(), progressDefaultDrawableRes);
				canvas.drawBitmap(bitmap, mSrcRect, mDestRect, p);
			}
		}
	}

	private void drawBottomContents(Canvas canvas, Paint p) {
		if (bottomContents == null || bottomContents.size() == 0) {
			return;
		}
		int left, top, baseline;

		top = (int) ((height + levelPointHeight) / 2 + marginBottom);
		//算出文字的基准线
		baseline = (int) Math.ceil(top + getFontHeight(p) * 4 / 5);
		for (int i = 1; i <= bottomContents.size(); i++) {
			float eachLen = width / levelMax;
			float textLen = getFontlength(p, bottomContents.get(i - 1));
			left = (int) (eachLen / 2 - textLen / 2 + eachLen * (i - 1));

			//	right = (int) Math.ceil(left + len);
			canvas.drawText(bottomContents.get(i - 1), left, baseline, p);
		}
	}

	private void drawTopContents(Canvas canvas, Paint p) {
		if (topContents == null || topContents.size() == 0) {
			return;
		}
		int left, baseline;
		baseline = (int) ((height - levelPointHeight) / 2 - marginBottom - getFontHeight(p) / 5);
		//	bottom = (int) Math.ceil(top + getFontHeight(p));
		for (int i = 1; i <= topContents.size(); i++) {
			float eachLen = width / levelMax;
			float textLen = getFontlength(p, topContents.get(i - 1));
			left = (int) (eachLen / 2 - textLen / 2 + eachLen * (i - 1));
			//	right = (int) Math.ceil(left + len);
			canvas.drawText(topContents.get(i - 1), left, baseline, p);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		calcSize();
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		Paint p = new Paint();
		p.setAntiAlias(true);

		drawLevelPoint(canvas, p);
		drawLevelProgress(canvas, p);

		p.setTextSize(contentSize);
		p.setColor(ContextCompat.getColor(getContext(),contentColorRes));
		drawBottomContents(canvas, p);
		drawTopContents(canvas, p);
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

	public int getLevelMax() {
		return levelMax;
	}

	public void setLevelMax(int levelMax) {
		this.levelMax = levelMax;
	}

	public List<String> getTopContents() {
		return topContents;
	}

	public void setTopContents(List<String> topContents) {
		this.topContents = topContents;
	}

	public List<String> getBottomContents() {
		return bottomContents;
	}

	public void setBottomContents(List<String> bottomContents) {
		this.bottomContents = bottomContents;
	}

	public int getContentColorRes() {
		return contentColorRes;
	}

	public void setContentColorRes(int contentColorRes) {
		this.contentColorRes = contentColorRes;
	}

	public int getContentSize() {
		return contentSize;
	}

	public void setContentSize(int contentSize) {
		this.contentSize = contentSize;
	}

	public float getLevelPointHeight() {
		return levelPointHeight;
	}

	/**
	 * 功能：设置 点的高度 dp
	 * @param levelPointHeight
	 */
	public void setLevelPointHeight(int levelPointHeight) {
		this.levelPointHeight = PixelUtil.dip2px(getContext(), levelPointHeight);
		this.levelProgressHeight = levelPointHeight * 15 / 66;
	}

}
