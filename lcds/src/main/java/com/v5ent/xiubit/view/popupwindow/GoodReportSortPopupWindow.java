package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.v5ent.xiubit.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/28
 */

public class GoodReportSortPopupWindow extends PopupWindow {

    private Context ctx;
    @BindView(R.id.sortWithAmountTv)
    TextView mSortWithAmountTv;
    @BindView(R.id.sortWithTimeTv)
    TextView mSortWithTimeTv;
    private View mRootView;
    

    public GoodReportSortPopupWindow(Context context) {
        super(context);
        this.ctx = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.good_report_sort_pop, null);
        setContentView(mRootView);
        ButterKnife.bind(this, mRootView);
        initView();
    }

    private void initView() {
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//         设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态  
        this.update();
        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx,R.color.backgroud_transparent)));
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
//        this.setAnimationStyle(R.style.anim_popup_category);
    }


    @OnClick({R.id.sortWithAmountTv, R.id.sortWithTimeTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sortWithAmountTv:
                EventBus.getDefault().post(new GoodReportSortEvent(1));
                dismiss();
                break;
            case R.id.sortWithTimeTv:
                EventBus.getDefault().post(new GoodReportSortEvent(2));
                dismiss();
                break;
        }
    }

    public static class GoodReportSortEvent {
        private int sortType; //1.按金额 2.按时间

        GoodReportSortEvent (int sortType){
            this.sortType = sortType; 
        }

        public int getSortType() {
            return sortType;
        }
    }
}
