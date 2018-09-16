package com.toobei.common.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.toobei.common.R;
import com.toobei.common.TopApp;

import org.xsl781.ui.ActivityStack;
import org.xsl781.utils.PixelUtil;

import java.util.Timer;
import java.util.TimerTask;

public class ToastUtil {
    public static Toast toast;
    private static View toastView;
    private static TextView textTitle;
    private static TextView textContent;
    private static String contentText;
    private static long lastShowTime;

    public static void toastCancel() {
        if (toast != null) {
            toast.cancel();
        }
    }

    public static void showCustomToast(String title, String content) {
        long nowTime = System.currentTimeMillis();
        if (StringUtil.isEmpty(content)) {
            return;
        }
        try {
            Context ctx = TopApp.getInstance().getApplicationContext();
            
            if (toast == null || nowTime - lastShowTime > 2000) {
            toastView = LayoutInflater.from(ctx).inflate(R.layout.layout_toast_view, null);
            textTitle = (TextView) toastView.findViewById(R.id.text_toast_title);
            textContent = (TextView) toastView.findViewById(R.id.text_toast_content);
            textTitle.setText(title);
            textContent.setText(content);

            toast = new Toast(ctx);
            toast.setGravity(Gravity.BOTTOM, 0, PixelUtil.dip2px(ctx, 80));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastView);
            toast.show();
            lastShowTime = System.currentTimeMillis();
            } else {
                if (!content.equals(contentText)) {
                    contentText = content;
                    textContent.setText(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCustomToast(String content) {
        long nowTime = System.currentTimeMillis();
        if (StringUtil.isEmpty(content)) {
            return;
        }
        try {
            Context ctx = TopApp.getInstance().getApplicationContext();
            if (toast == null || nowTime - lastShowTime > 2000) {
                toastView = LayoutInflater.from(ctx).inflate(R.layout.layout_toast_view, null);
                textTitle = (TextView) toastView.findViewById(R.id.text_toast_title);
                textContent = (TextView) toastView.findViewById(R.id.text_toast_content);
                textTitle.setVisibility(View.GONE);
                textContent.setText(content);
                toast = new Toast(ctx);
                toast.setGravity(Gravity.BOTTOM, 0, PixelUtil.dip2px(ctx, 60));
                //		toast.setMargin(0, PixelUtil.dip2px(activity, 30));
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastView);
//                toast.show();
                setToasetTimeAndShow(toast,2000);
                lastShowTime = System.currentTimeMillis();
            } else {
                if (!content.equals(contentText)) {
                    contentText = content;
                    textContent.setText(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCustomToast(String content, int duration) {
        long nowTime = System.currentTimeMillis();
        if (StringUtil.isEmpty(content)) {
            return;
        }
        try {
            Context ctx = TopApp.getInstance().getApplicationContext();
            
//            toastCancel();
            if (toast == null || nowTime - lastShowTime > 2000) {
                View view = LayoutInflater.from(ctx).inflate(R.layout.layout_toast_view, null);
                TextView textTitle = (TextView) view.findViewById(R.id.text_toast_title);
                TextView textContent = (TextView) view.findViewById(R.id.text_toast_content);
                textTitle.setVisibility(View.GONE);
                textContent.setText(content);
                toast = new Toast(ctx.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, PixelUtil.dip2px(ctx, 60));
                //		toast.setMargin(0, PixelUtil.dip2px(activity, 30));
                toast.setDuration(duration);
                toast.setView(view);
                toast.show();
                lastShowTime = System.currentTimeMillis();
            } else {
                if (!content.equals(contentText)) {
                    contentText = content;
                    textContent.setText(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以精确控制时间的toast
     *
     * @param content  内容
     * @param stayTime Toast时间 毫秒值
     */
    public static void showCustomToastWithStayTime(String content, long stayTime) {
        long nowTime = System.currentTimeMillis();
        if (StringUtil.isEmpty(content)) {
            return;
        }
        try {
            Context ctx = TopApp.getInstance().getApplicationContext();
            
//            toastCancel();
            if (toast == null || nowTime - lastShowTime > 2000) {
                toastView = LayoutInflater.from(ctx).inflate(R.layout.layout_toast_view, null);
                textTitle = (TextView) toastView.findViewById(R.id.text_toast_title);
                textContent = (TextView) toastView.findViewById(R.id.text_toast_content);
                textTitle.setVisibility(View.GONE);
                textContent.setText(content);
                toast = new Toast(ctx.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, PixelUtil.dip2px(ctx, 60));
                //		toast.setMargin(0, PixelUtil.dip2px(activity, 30));
                //为了避免定时器周期过多的启动，这里默认设置最长的，也就是3.5秒 ，这样定时器的周期设置为3秒，消耗的资源就比较小
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastView);
                setToasetTimeAndShow(toast, stayTime);
                lastShowTime = System.currentTimeMillis();
            } else {
                if (!content.equals(contentText)) {
                    contentText = content;
                    textContent.setText(content);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCustomToast(Activity activity, String content) {
        long nowTime = System.currentTimeMillis();
        if (StringUtil.isEmpty(content)) {
            return;
        }
        Context ctx = TopApp.getInstance().getApplicationContext();
        try {
            if (toast == null || nowTime - lastShowTime > 2000) {
                toastView = LayoutInflater.from(ctx).inflate(R.layout.layout_toast_view, null);
                textTitle = (TextView) toastView.findViewById(R.id.text_toast_title);
                textContent = (TextView) toastView.findViewById(R.id.text_toast_content);
                textTitle.setVisibility(View.GONE);
                textContent.setText(content);
                toast = new Toast(ctx.getApplicationContext());
                toast.setGravity(Gravity.BOTTOM, 0, PixelUtil.dip2px(ctx, 60));
                //		toast.setMargin(0, PixelUtil.dip2px(activity, 30));
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(toastView);
//                toast.show();
                setToasetTimeAndShow(toast,1000);
                lastShowTime = System.currentTimeMillis();
            } else {
                if (!content.equals(contentText)) {
                    contentText = content;
                    textContent.setText(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //android.util.AndroidRuntimeException: requestFeature() must be called before adding content

    public static ProgressDialog showCustomDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);

        //	View toastView = LayoutInflater.from(activity).inflate(R.layout.load_progress, null);
        //	dialog.setView(toastView);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        // TODO: 2016/11/9 0009  防止在主题为Meterial时 dialog 的样式显示异常
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        if (!activity.isFinishing()) {
            dialog.show();
        }
        //	dialog.setContentView(R.layout.load_dialog_progress);
        dialog.setContentView(R.layout.load_progress);
        return dialog;
    }

    public static ProgressDialog showCustomDialog() {
        Activity activity = ActivityStack.getInstance().topActivity();
        return showCustomDialog(activity);
    }

    private static void setToasetTimeAndShow(final Toast toast, final long stayTime) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0,3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, stayTime);
    }
}