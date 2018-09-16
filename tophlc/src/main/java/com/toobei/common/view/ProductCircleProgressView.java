package com.toobei.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.toobei.common.R;

public class ProductCircleProgressView extends android.support.v7.widget.AppCompatImageView {
	private Path mPath;
	private Path backPath;

	private int drawPercent = 0;
	private float SWEEP_INC = 9f;
	private float movePercent = 270f;
	private int radio = 0;
	private Paint p;
	private float center_X;
	private float center_Y;
	private float r;
	private int startAngle;
	private Paint backPaint;
	private Paint mPaint;
	private Context context;
	private int textX;
	private int textY;
	private Bitmap bitmap;
	private int left;
	private int top;
	private int right;
	private int bottom;
	private int width;
	private int high;
	private String percentageStr = "0%";
	//分情况显示：1.不限额 2.限额，正常转动 3.售馨
	private String ShowStyle = "1";

	public ProductCircleProgressView(Context context) {
		super(context);
		init(context);
	}

	public ProductCircleProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	private void init(Context context) {
		this.context = context;
		mPath = new Path();
		backPath = new Path();
		mPaint = new Paint();

		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setARGB(255, 255, 255, 255);
		mPaint.setTextSize(22);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setStrokeWidth(5);
		mPaint.setColor(ContextCompat.getColor(getContext(),R.color.WHITE));

		radio = dip2px(context, 130);
		center_X = radio;
		center_Y = radio;
		r = radio;
		startAngle = 270;

		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_fan_setpoint);
		width = bitmap.getWidth();
		high = bitmap.getHeight();
		left = radio - width / 2;
		top = 0;
		right = radio + width / 2;
		bottom = high;

		textX = dip2px(context, 130) - 10;
		textY = 25;

		setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ring_selected));
		setbackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ring_unselected));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(dip2px(context, 260), dip2px(context, 260));
	}

	//1.不限额  
	public void setNoLimitView(int drawable, int drawableBg, int smallCircle) {
		this.ShowStyle = "1";
		bitmap = BitmapFactory.decodeResource(getResources(), smallCircle);
		setDrawable(ContextCompat.getDrawable(getContext(),drawable));
		setbackgroundDrawable(ContextCompat.getDrawable(getContext(),drawableBg));
		invalidate();
	}

	/*2.限额，正常转动
	 * percent:产品百分比(0~100)
	 */
	public void setLimitView(int percent, int drawable, int drawableBg, int smallCircle) {
		this.ShowStyle = "2";
		this.percentageStr = percent + "%";
		drawPercent = 360 * percent / 100;

		bitmap = BitmapFactory.decodeResource(getResources(), smallCircle);
		setDrawable(ContextCompat.getDrawable(getContext(),drawable));
		setbackgroundDrawable(ContextCompat.getDrawable(getContext(),drawableBg));
		invalidate();
	}

	//3.售馨
	public void setSoldOutView(int drawable, int drawableBg) {
		this.ShowStyle = "3";
		this.percentageStr = "100%";
		drawPercent = 360;

		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_fan_setpoint_gray);
		setDrawable(ContextCompat.getDrawable(getContext(),drawable));
		setbackgroundDrawable(ContextCompat.getDrawable(getContext(),drawableBg));
		invalidate();
	}

	private void setDrawable(Drawable pDrawable) {
		BitmapDrawable b = (BitmapDrawable) pDrawable;
		Bitmap bb = Bitmap.createScaledBitmap(b.getBitmap(), radio * 2, radio * 2, true);
		BitmapShader s = new BitmapShader(bb, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		p = new Paint();
		p.setShader(s);
		p.setAntiAlias(true);
		pDrawable.setBounds(0, 0, getWidth(), getHeight());
	}

	private void setbackgroundDrawable(Drawable pDrawable) {
		BitmapDrawable b = (BitmapDrawable) pDrawable;
		Bitmap bb = Bitmap.createScaledBitmap(b.getBitmap(), radio * 2, radio * 2, true);
		BitmapShader s = new BitmapShader(bb, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		backPaint = new Paint();
		backPaint.setShader(s);
		backPaint.setAntiAlias(true);
		pDrawable.setBounds(0, 0, getWidth(), getHeight());
	}

	/**
	 * dipתpx
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(final Context context, final float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		backPath.moveTo(center_X, center_Y); // 圆心
		backPath.lineTo((float) (center_X + r * Math.cos(startAngle * Math.PI / 180)), // 起始点角度在圆上对应的横坐标
				(float) (center_Y + r * Math.sin(startAngle * Math.PI / 180))); // 起始点角度在圆上对应的纵坐标
		backPath.lineTo((float) (center_X + r * Math.cos(movePercent * Math.PI / 180)), // 终点角度在圆上对应的横坐�?
				(float) (center_Y + r * Math.sin(movePercent * Math.PI / 180))); // 终点点角度在圆上对应的纵坐标
		backPath.close();
		// //设置�?��正方形，内切�?
		RectF rectF1 = new RectF(center_X - r, center_Y - r, center_X + r, center_Y + r);
		// 下面是获得弧形剪裁区的方�?
		backPath.addArc(rectF1, startAngle, 360);
		canvas.drawPath(backPath, backPaint);

		mPath.moveTo(center_X, center_Y); // 圆心
		mPath.lineTo((float) (center_X + r * Math.cos(startAngle * Math.PI / 180)), // 起始点角度在圆上对应的横坐标
				(float) (center_Y + r * Math.sin(startAngle * Math.PI / 180))); // 起始点角度在圆上对应的纵坐标
		mPath.lineTo((float) (center_X + r * Math.cos(movePercent * Math.PI / 180)), // 终点角度在圆上对应的横坐�?
				(float) (center_Y + r * Math.sin(movePercent * Math.PI / 180))); // 终点点角度在圆上对应的纵坐标
		mPath.close();
		// //设置�?��正方形，内切�?
		RectF rectF = new RectF(center_X - r, center_Y - r, center_X + r, center_Y + r);
		// 下面是获得弧形剪裁区的方�?
		mPath.addArc(rectF, startAngle, movePercent - startAngle);
		canvas.drawPath(mPath, p);

		//1.不限额  
		if (ShowStyle.equals("1")) {

		} else {
			//2.限额，正常转动
			canvas.drawBitmap(bitmap, null, new RectF(left, top, right, bottom), mPaint);
			canvas.drawText(percentageStr, textX - 5, textY + 15, mPaint);
			if (movePercent < 270f + drawPercent) {
				float temp = 270f + drawPercent - movePercent;
				if (temp > SWEEP_INC) {
					movePercent += SWEEP_INC;
				} else {
					movePercent += temp;
				}
				if (movePercent == 270f) {
					//0%
					left = (int) r - width / 2;
					top = 0;
					right = (int) (r + width / 2);
					bottom = high;
				} else if (movePercent == 360f) {
					//25%
					left = (int) ((2 * r) - width);
					top = (int) (r - high / 2);
					right = (int) (2 * r);
					bottom = (int) (r + high / 2);
					textX = left + 15;
					textY = bottom - 40;
				} else if (movePercent == 450f) {
					//50%
					left = (int) (r - width / 2);
					top = (int) (2 * r - high);
					right = (int) (r + width / 2);
					bottom = (int) (2 * r);
					textX = left + 15;
					textY = bottom - 35;
				} else if (movePercent == 540f) {
					//75%
					left = 0;
					top = (int) (r - high / 2);
					right = width;
					bottom = (int) (r + high / 2);
					textX = left + 20;
					textY = bottom - 35;
				} else if (movePercent == 630f) {
					//100%
					left = (int) (r - width / 2);
					top = 0;
					right = (int) (r + width / 2);
					bottom = high;
					textX = left + 7;
					textY = bottom - 38;
				} else if (movePercent > 270f && movePercent < 360f) {
					left = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))) - width);
					top = (int) (radio - radio * Math.cos(2 * Math.PI / 360 * (movePercent - 270f)));
					right = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))));
					bottom = (int) (radio - radio
							* Math.cos(2 * Math.PI / 360 * (movePercent - 270f)) + high);
					textX = left + 20;
					textY = bottom - 35;
				} else if (movePercent > 360f && movePercent < 450f) {
					left = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))) - width);
					top = (int) (radio - radio * Math.cos(2 * Math.PI / 360 * (movePercent - 270f)) - high);
					right = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))));
					bottom = (int) (radio - radio
							* Math.cos(2 * Math.PI / 360 * (movePercent - 270f)));
					textX = left + 15;
					textY = bottom - 40;
				} else if (movePercent > 450f && movePercent < 540f) {
					left = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))));
					top = (int) (radio - radio * Math.cos(2 * Math.PI / 360 * (movePercent - 270f)) - high);
					right = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))) + width);
					bottom = (int) (radio - radio
							* Math.cos(2 * Math.PI / 360 * (movePercent - 270f)));
					textX = left + 15;
					textY = bottom - 35;
				} else if (movePercent > 540f && movePercent < 630f) {
					left = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))));
					top = (int) (radio - radio * Math.cos(2 * Math.PI / 360 * (movePercent - 270f)));
					right = (int) ((radio + radio
							* Math.sin(2 * Math.PI / 360 * (movePercent - 270f))) + width);
					bottom = (int) (radio - radio
							* Math.cos(2 * Math.PI / 360 * (movePercent - 270f)) + high);
					textX = left + 15;
					textY = bottom - 40;
				}
				invalidate();
			}
		}
	}
}
