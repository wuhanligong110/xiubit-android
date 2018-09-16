package com.v5ent.xiubit.view.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jungly.gridpasswordview.Util;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.FundBaseDefinedData;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recylerapter.FilterOptionAdapter;
import com.v5ent.xiubit.event.FundFilterRefreshEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明：时间 筛选 器
 *
 * @date 2016-1-26
 */
public class FundFilterPopupWindow extends PopupWindow {
    private String defaultKey;
    private int mType;
    public final static int TYPE_FUNDTYPE = 1;    //基金类型
    public final static int TYPE_PERIODTYPE = 2; //基金周期
    private View mContentView;
    private TopBaseActivity ctx;
    private List<FundBaseDefinedData.FundTypeListBean> datas;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.rootViewRl)
    RelativeLayout mRootViewRl;
    private FilterOptionAdapter mAdapter;
    private int mWindowWidth;
    private int mWindowHeight;

    @SuppressWarnings("deprecation")
    public FundFilterPopupWindow(Context ctx, List<FundBaseDefinedData.FundTypeListBean> datas, String defaultKey, int type) {
        this.ctx = (TopBaseActivity) ctx;
        this.datas = datas;
        this.mType = type;
        this.defaultKey = defaultKey;
        mContentView = LayoutInflater.from(ctx).inflate(R.layout.layout_fund_filter_popu_window, null);
        ButterKnife.bind(this, mContentView);
        initView();
        setContentView(mContentView);
    }

    private void initView() {
        // 设置SelectPicPopupWindow弹出窗体的宽  
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高  
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击  
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态  
        this.update();
        // 刷新状态  
        this.update();
        //背景遮罩
        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx,R.color.backgroud_transparent_shape)));
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);  
        // 设置SelectPicPopupWindow弹出窗体动画效果  
//		this.setAnimationStyle(R.style.anim_popup_right);
        //列表宽度设置为屏幕的一半
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        mWindowWidth = wm.getDefaultDisplay().getWidth();
        mWindowHeight = wm.getDefaultDisplay().getHeight();
        //列表位置和宽度
        mRootViewRl.setGravity(mType == TYPE_FUNDTYPE ? Gravity.LEFT : Gravity.RIGHT);
        mRecyclerView.getLayoutParams().width = wm.getDefaultDisplay().getWidth() / 2;

        mAdapter = new FilterOptionAdapter(this, defaultKey, mType == TYPE_FUNDTYPE ? false : true);
        //筛选确认按钮
        if (mType == TYPE_FUNDTYPE) {
            TextView comfirmBtn = new TextView(ctx);
            comfirmBtn.setText("筛选");
            comfirmBtn.setGravity(Gravity.CENTER);
            comfirmBtn.setBackgroundColor(ContextCompat.getColor(ctx,R.color.text_blue_common_light));
            comfirmBtn.setTextColor(ContextCompat.getColor(ctx,R.color.text_white_common));
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(mRecyclerView.getLayoutParams().width, (int) ctx.getResources().getDimension(R.dimen.w44));
            comfirmBtn.setLayoutParams(lp);
            comfirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter != null) {
                        StringBuilder stringBuilder = new StringBuilder("");
                        FundFilterRefreshEvent event = new FundFilterRefreshEvent();
                        if (mAdapter.selectTypeKeyList.size() == 0) {
                            event.typeKeyString = "";
                        } else {
                            for (String str : mAdapter.selectTypeKeyList) {
                                stringBuilder.append(str + ",");
                            }
                            event.typeKeyString = stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString();
                        }
                        event.type = mType;
                        EventBus.getDefault().post(event);
                        dismiss();
                    }
                }
            });
            mAdapter.addFooterView(comfirmBtn);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(datas);
        mRootViewRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null && mType == TYPE_FUNDTYPE) {
                    mAdapter.refreshView(defaultKey);
                }
                dismiss();
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        int[] viewLocation = new int[2];
        anchor.getLocationInWindow(viewLocation);
        int viewX = viewLocation[0];
        int viewY = viewLocation[1];

        int appBottomGroupHeight = Util.dp2px(ctx, 49);  //底部导航栏高度
        int popuHeight = mWindowHeight - (viewY + anchor.getHeight() + appBottomGroupHeight);
        this.setHeight(popuHeight);
        super.showAsDropDown(anchor);
    }

}
