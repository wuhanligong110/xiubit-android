package com.v5ent.xiubit.activity;

import android.view.View.OnClickListener;

import com.toobei.common.activity.TopGestureSetActivity;
import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明：Activity-手势密码设置 界面
 *
 * @date 2016-3-15
 */
public class GestureSetActivity extends TopGestureSetActivity implements OnClickListener {

    @Override
    protected void skipMainActivity() {
        skipActivity(this, MainActivity.class);
    }

    @Override
    protected int setBackgroundColor() {
        return R.color.text_white_common;
    }

    protected int[] setLocusPassWordViewImage() {
        return new int[]{R.drawable.img_locus_round_click, R.drawable.img_locus_line};
    }
}