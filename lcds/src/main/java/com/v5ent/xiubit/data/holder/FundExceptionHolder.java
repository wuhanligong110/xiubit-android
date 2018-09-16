package com.v5ent.xiubit.data.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/21
 */

public class FundExceptionHolder {

    private Context ctx;
    public View mRootView;

    public FundExceptionHolder(Context context, int height) {
        this.ctx = context;
        mRootView = LayoutInflater.from(ctx).inflate(R.layout.fund_exception_layout, null, true);
        if (height > 0) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mRootView.setLayoutParams(lp);
        }
    }
}
