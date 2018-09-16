package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.toobei.common.TopBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.ProductFilterGridAdapter;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 公司: tophlc
 * 类说明:产品列表选择Popupwindow
 *
 * @author yangLin
 * @time 2017/6/23
 */

public class ProductFilterPopupWindow extends PopupWindow implements ProductFilterGridAdapter.OnItemSelectedCallBack {

    private final View mContentView;
    private final TopBaseActivity ctx;
    @BindView(R.id.flowRateGv)
    GridView mFlowRateGv;
    @BindView(R.id.deadlineGv)
    GridView mDeadlineGv;
    @BindView(R.id.userTypeGv)
    GridView mUserTypeGv;
    @BindView(R.id.filterMatchNumTv)
    TextView mFilterMatchNumTv;
    @BindView(R.id.resetBtn)
    Button mResetBtn;
    @BindView(R.id.completeBtn)
    Button mCompleteBtn;
    private Unbinder mBind;
    //期限 ：dLa=不限 dLb=1个月内 dLc=1-3个月 dLd=3-6个月 dLe=6-12个月 dLf=12个月以上
    public final String TYPE_DEADLINE = "deadlineValue";
    private String[] deadLineArrs = {"不限", "1个月以内", "1~3个月", "3~6个月", "6~12个月", "12个月以上"};
    //年化收益率 ：fRa=不限 fRb=8%以下 fRc=8%-12% fRd=12%以上
    public final String TYPE_YEARRATE = "flowRate";
    private String[] flowRateArrs = {"不限", "8%以下","8%~12%","12%以上" };
    //ifRookie	用户类型	number	0-不限 1-新手用户
    public final String TYPE_USERTYPE = "ifRookie";
    private String[] ifRookieArrs = {"不限", "新手专享"};
    private PopFilterSelectBean selectBean = new PopFilterSelectBean();
    private OnBtnClickListener mOnBtnClickListener;


    public ProductFilterPopupWindow(Context ctx) {
        this.ctx = (TopBaseActivity) ctx;
        mContentView = LayoutInflater.from(ctx).inflate(R.layout.layout_product_filter_popu_window, null);
        mBind = ButterKnife.bind(this, mContentView);
        // 设置SelectPicPopupWindow的View  
        initView();
        setContentView(mContentView);
    }

    private void initView() {
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高  
        this.setHeight(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态  
        this.update();
        // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(ctx,R.color.Color_0));
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作  
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
        this.setAnimationStyle(R.style.anim_popup_right);

        initSeclectGridView();


    }

    private void initSeclectGridView() {
        mDeadlineGv.setAdapter(new ProductFilterGridAdapter(ctx, deadLineArrs, TYPE_DEADLINE,selectBean.deadlineValuePosition,this));
        mFlowRateGv.setAdapter(new ProductFilterGridAdapter(ctx, flowRateArrs, TYPE_YEARRATE,selectBean.flowRatePosition, this));
        mUserTypeGv.setAdapter(new ProductFilterGridAdapter(ctx, ifRookieArrs, TYPE_USERTYPE,selectBean.ifRookiePosition, this));
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        showAtLocation(parent, Gravity.RIGHT, 0, 0);
        backgroundAlpha(0.5f);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ctx.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ctx.getWindow().setAttributes(lp);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        backgroundAlpha(1f);
        if (mBind != null) {
            mBind = null;
        }
    }

    @Override
    public void onItemSelected(String type, int position) {
        switch (type) {
            case TYPE_DEADLINE:
                selectBean.deadlineValuePosition = position;
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onFilterBtnClick(selectBean);
                }
                break;
            case TYPE_USERTYPE:
                selectBean.ifRookiePosition = position;
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onFilterBtnClick(selectBean);
                }
                break;
            case TYPE_YEARRATE:
                selectBean.flowRatePosition = position;
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onFilterBtnClick(selectBean);
                }
                break;
        }
    }

    @OnClick({R.id.resetBtn, R.id.completeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.resetBtn:
                selectBean = new PopFilterSelectBean();
                initSeclectGridView();
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onReset();
                }
                break;
            case R.id.completeBtn:
                MobclickAgent.onEvent(ctx,"T_2_4");
                if (mOnBtnClickListener != null) {
                    mOnBtnClickListener.onComplete(selectBean);
                }
                dismiss();
                break;
        }
    }

    public void setOnBtnClickListener(OnBtnClickListener listener) {
        this.mOnBtnClickListener = listener;
    }

    public void setFilterMatchNum(String num) {
        if (TextUtils.isEmpty(num)) {
            mFilterMatchNumTv.setText("0");
        } else {
            mFilterMatchNumTv.setText(num);
        }
    }

    public void refreshView(PopFilterSelectBean popFilterSelectBean) {
        selectBean = popFilterSelectBean;
        initSeclectGridView();
    }

    public interface OnBtnClickListener {
        void onFilterBtnClick(PopFilterSelectBean bean);

        void onReset();

        void onComplete(PopFilterSelectBean bean);
    }

    public static class PopFilterSelectBean {

        public int deadlineValuePosition;
        public int flowRatePosition;
        public int ifRookiePosition;

        public PopFilterSelectBean() {

        }
        
        public PopFilterSelectBean(int deadlineValuePosition,int flowRatePosition,int ifRookiePosition){
            this.deadlineValuePosition = deadlineValuePosition;
            this.flowRatePosition = flowRatePosition;
            this.ifRookiePosition = ifRookiePosition;
        }
    }
}
