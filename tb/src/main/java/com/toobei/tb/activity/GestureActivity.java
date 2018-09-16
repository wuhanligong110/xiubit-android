package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.toobei.common.activity.TopGestureActivity;
import com.toobei.tb.R;

/**
 * 公司: tophlc
 * 类说明：手势登录界面
 *
 * @date 2015-9-28
 */
public class GestureActivity extends TopGestureActivity implements OnClickListener {
    public static GestureActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

    }

    @Override
    protected void skipLoginActivity() {
        skipActivity(this, LoginActivity.class);
    }

    @Override
    protected void skipMainActivity() {

        //手势通过后，跳转
        skipActivity(GestureActivity.this, MainActivity.class);
    }

    @Override
    protected void showPwdManagerGestureVerify() {
        showActivity(this, PwdManagerGestureVerify.class);
        Intent intent = new Intent(this, PwdManagerGestureVerify.class);
        intent.putExtra("whereFrom", "GestureActivityForgetPWD");
        startActivity(intent);
    }

    @Override
    protected void skipTopGestureSetActivity() {

    }

    @Override
    protected int setBackgroundColor() {
        return R.color.WHITE;
    }
    @Override
    protected int[] setLocusPassWordViewImage() {
        return new int[]{R.drawable.img_locus_round_click,R.drawable.img_locus_line};
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == com.toobei.common.R.id.login_forget) {
            showPwdManagerGestureVerify();
            //	showActivity(ctx, getPwdManagerGestureVerify());
        } else if (v.getId() == com.toobei.common.R.id.login_other) {
            showActivity(ctx, LoginActivity.class);
        }
    }
}