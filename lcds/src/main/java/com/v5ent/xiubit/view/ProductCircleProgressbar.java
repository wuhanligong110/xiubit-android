package com.v5ent.xiubit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.v5ent.xiubit.R;

import org.xsl781.utils.PixelUtil;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author qingyechen
 * @time 2017/2/9 0009 上午 10:40
 */
public class ProductCircleProgressbar extends View {

    private Context context;
    private Paint paint;
    private RectF rect;
    private int progress = 25; //进度
    private boolean isRecommend; //是否推荐
    private String color_border_def;  //圆圈边缘默认颜色
    private String color_border_progress;  //圆圈边缘进度颜色
    private String color_inside;  //圆圈内部颜色
    private String text_inside;   //圆圈内部文字


    public ProductCircleProgressbar(Context context) {
        super(context);
    }

    public ProductCircleProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
        rect = new RectF();

    }

    public ProductCircleProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
        rect = new RectF();
    }

    /**
     *  销售进度
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();

    }

    /**
     * @param recommendState 推荐了与否
     */
    public void setIsRecommend(boolean recommendState) {
        this.isRecommend = recommendState;
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setAntiAlias(true);// 设置是否抗锯齿
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿

        // 画圆
//        paint.setColor(Color.parseColor("#BEE7F6"));// 设置画笔
        paint.setColor(ContextCompat.getColor(context,R.color.red_color_circle_defult));// 设置画笔
        paint.setStrokeWidth(PixelUtil.dip2px(getContext(),2));// 宽度为setStrokeWidth：6
        paint.setStyle(Paint.Style.STROKE);// 设置中空的样式
        canvas.drawCircle(PixelUtil.dip2px(getContext(),30), PixelUtil.dip2px(getContext(),30), PixelUtil.dip2px(getContext(),25), paint);// 在中心为（90,90）的地方画个半径为75的圆，


        //画弧
//        paint.setColor(Color.parseColor("#25AFE0"));// 设置画笔为绿色
        paint.setColor(ContextCompat.getColor(context,R.color.text_blue_common));// 设置画笔为绿色
        rect.set(PixelUtil.dip2px(getContext(),5), PixelUtil.dip2px(getContext(),5), PixelUtil.dip2px(getContext(),55), PixelUtil.dip2px(getContext(),55));// 设置类似于左上角坐标（45,45），右下角坐标（155,155），这样也就保证了半径为55
        canvas.drawArc(rect, -90, 3.6f * progress, false, paint);// 画圆弧，第二个参数为：起始角度，第三个为跨的角度，第四个为true的时候是实心，false的时候为空心
        paint.reset();// 将画笔重置
        paint.setTextSize(PixelUtil.dip2px(getContext(),13));// 设置文字的大小
        paint.setAntiAlias(true);
//        Typeface fontPingFang = Typeface.createFromAsset(getContext().getAssets(), "fonts/pingfang_medium.ttf");
//        paint.setTypeface(fontPingFang);
//        paint.setColor(Color.parseColor("#25AFE0"));// 设置画笔颜色
//        paint.setColor(ContextCompat.getColor(context,R.color.text_blue_common));// 设置画笔颜色

//        // 推荐与否的实心效果
//        if (isRecommend) {  //已经推荐
//            paint.reset();
//            paint.setColor(Color.parseColor("#67c6f7"));
//            canvas.drawCircle(PixelUtil.dip2px(getContext(),30), PixelUtil.dip2px(getContext(),30), PixelUtil.dip2px(getContext(),24), paint);// 在中心为（90,90）的地方画个半径为72的圆，
//            paint.reset();
//            paint.setTextSize(PixelUtil.dip2px(getContext(),13));// 设置文字的大小
//            paint.setAntiAlias(true);
//            paint.setColor(Color.WHITE);
//            canvas.drawText("再次", PixelUtil.dip2px(getContext(),17), PixelUtil.dip2px(getContext(),27), paint);
//            canvas.drawText("推荐", PixelUtil.dip2px(getContext(),17), PixelUtil.dip2px(getContext(),43), paint);
//        } else { //没有推荐
//            canvas.drawText("推荐", PixelUtil.dip2px(getContext(),17), PixelUtil.dip2px(getContext(),35), paint);
//        }


    }
}
