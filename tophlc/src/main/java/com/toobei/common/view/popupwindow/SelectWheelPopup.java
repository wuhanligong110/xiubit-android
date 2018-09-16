package com.toobei.common.view.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.view.SelectWheelAdapter;
import com.toobei.common.view.wheel.OnWheelChangedListener;
import com.toobei.common.view.wheel.WheelView;

import java.util.List;

public class SelectWheelPopup extends PopupWindow implements OnClickListener {

    private Activity activity;
    private SelectWheelAdapter adapter;
    private View rootView;
    private WheelView wheel;
    private Object mCurAddress;
    private List<Object> datas;

    private OnSelectWheelPopupCompletedListener onSelectWheelPopupCompletedListener;

    public interface OnSelectWheelPopupCompletedListener {
        void onSelectWheelPopupCompleted(SelectWheelPopup selectWheelPopup, Object mCurAddress);
    }

    public SelectWheelPopup(Activity activity, List datas, SelectWheelAdapter adapter) {
        super(activity);
        this.activity = activity;
        this.datas = datas;
        this.adapter = adapter;
        rootView = LayoutInflater.from(activity).inflate(R.layout.layout_address_select_wheel, null);
        rootView.findViewById(R.id.text_complete).setOnClickListener(this);
        if (datas == null) {
            return;
        }
        mCurAddress = datas.get(0);
        this.setContentView(rootView);
        initWheel();
        initPopuWindow();
    }

    public void setTitle(String title) {
        ((TextView) rootView.findViewById(R.id.text_title)).setText(title);
    }

    public void showLeftButtom() {
        rootView.findViewById(R.id.text_cancel).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.text_cancel).setOnClickListener(this);
    }

    private void initWheel() {
        wheel = (WheelView) rootView.findViewById(R.id.wheelview);
        //	wheel.setBackgroundDrawable(new ColorDrawable(0xFFFFFFFF));
        //	wheel.setShadowsColors(new int[] { 0, 0, 0 });
        //	wheel.setCenterDrawable(new ColorDrawable(0));
        //	adapter = new DateNumericAdapter(activity, 0, addresses.size() - 1, 0);
        OnWheelChangedListener listener = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mCurAddress = datas.get(newValue);
            }
        };

        wheel.setViewAdapter(adapter);
        wheel.addChangingListener(listener);

    }

    @SuppressWarnings("deprecation")
    private void initPopuWindow() {
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(false);
        setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_complete) {
            if (onSelectWheelPopupCompletedListener != null) {
                onSelectWheelPopupCompletedListener.onSelectWheelPopupCompleted(this, mCurAddress);
            }
        }
        this.dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        activity.getWindow().setAttributes(lp);
        //	mTextview.setText(mCurAddress.get);
    }

    public void setOnSelectWheelPopupCompletedListener(OnSelectWheelPopupCompletedListener onSelectWheelPopupCompletedListener) {
        this.onSelectWheelPopupCompletedListener = onSelectWheelPopupCompletedListener;
    }

    public void showAtLocation(View rootView) {
        showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = .5f;
        activity.getWindow().setAttributes(lp);
        setAnimationStyle(R.style.anim_popup_select_bottom);
    }

    public void setCurrentItem(int position) {
        wheel.setCurrentItem(position);
    }
}
