package com.toobei.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.HeaderLayout;
import com.toobei.common.view.locus.LocusPassWordViewWhite;
import com.toobei.common.view.locus.LocusPassWordViewWhite.OnCompleteListener;

import org.xsl781.utils.MD5Util;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 公司: tophlc
 * 类说明：手势密码设置 界面
 *
 * @date 2015-9-28
 */
public abstract class TopGestureSetActivity extends TopBaseActivity implements OnClickListener {
    private LocusPassWordViewWhite lpwv;
    private TextView toastTv;
    private int inputNum = 1;
    private String mPasswd = "";
    private LinearLayout backBtn;
    private String whereFrom;
    private LinearLayout gestureLl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tranToLogin = false;
        whereFrom = getIntent().getStringExtra("whereFrom");
        setContentView(R.layout.activity_gesture_set);
        initView();
        setTranslucentStatus(true);
    }

    protected abstract void skipMainActivity();

    private void initView() {
        initTopTitle();
        gestureLl = (LinearLayout) findViewById(R.id.gestureLl);
        toastTv = (TextView) findViewById(R.id.set_passwd_toast);
        toastTv.setText(R.string.set_gesture_toast1);
        lpwv = (LocusPassWordViewWhite) this.findViewById(R.id.mLocusPassWordView_setpasswd);
        lpwv.setImgResourse(setLocusPassWordViewImage()[0], setLocusPassWordViewImage()[1]);
        lpwv.setOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(String password) {
                checkPasswd(password);
            }
        });
        gestureLl.setBackgroundResource(setBackgroundColor());
    }

    protected abstract int[] setLocusPassWordViewImage();

    private void initTopTitle() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(getString(R.string.set_gesture_passwd));
        backBtn = headerLayout.showLeftBackButton(this);
        backBtn.setVisibility(View.INVISIBLE);
        if (isReset()) {
            backBtn.setVisibility(View.VISIBLE);
            headerLayout.showTitle(getString(R.string.reset_gesture_passwd));
        }
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

    /**
     * 功能：是否为重设置，是为true
     *
     * @return
     */
    private boolean isReset() {
        //判断是否是主动重设手势密码 或者忘记手势密码
        return whereFrom != null && (whereFrom.equals("PwdManagerGestureVerify") || whereFrom.equals("GestureActivityForgetPWD"));
    }

    private void checkPasswd(String passwd) {
        if (inputNum == 1) {
            mPasswd = passwd;
            lpwv.clearPassword();
            toastTv.setText(R.string.set_gesture_toast2);
            inputNum = 2;
            backBtn.setVisibility(View.VISIBLE);
        } else if (inputNum == 2) {
            if (mPasswd.equals(passwd)) {
                ToastUtil.showCustomToast(this, getString(R.string.set_success));
                lpwv.clearPassword();
                // 2017/1/4 0004  设置手势密码
                TopApp.getInstance().getCurUserSp().setCurUserGestrue(MD5Util.MD5(mPasswd));
                if (TopApp.loginAndStay) {
                    TopApp.loginAndStay = false;
                    finish();
                } else {
                    if (isReset()) {
                        finish(); //如果是主动重设手势 结束当前Activity
                        //如果是忘记手势重设密码 重设后则跳入MainActivity
                        if (whereFrom.equals("GestureActivityForgetPWD")) {
                            skipMainActivity();
                        }
                    } else {
                        skipMainActivity();
                        //	skipActivity(this, getMainActivity());
                    }
                }
                
            } else {
                ToastUtil.showCustomToast(this, getString(R.string.set_gesture_toast3));
                resetPasswd();
            }
        }
    }


    private void resetPasswd() {
        toastTv.setText(R.string.set_gesture_toast1);
        if (!isReset()) {
            backBtn.setVisibility(View.INVISIBLE);
        }
        lpwv.clearPassword();
        mPasswd = "";
        inputNum = 1;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按下键盘上返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inputNum == 1 && !isReset()) {
                exitBy2Click();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private static boolean isExit = false;

    /**
     * 双击退出函数
     */
    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出  
            ToastUtil.showCustomToast(this, "再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出  
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务  

        } else {
            TopApp.getInstance().exitApp();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backBtn) {
            if (inputNum == 1 && isReset()) {
                finish();
            } else if (inputNum == 2) {
                resetPasswd();
            }
        }
        /*	
			switch (v.getId()) {
			case R.id.backBtn:
				if (inputNum == 1 && isReset()) {
					finish();
				} else if (inputNum == 2) {
					resetPasswd();
				}
				break;

			default:
				break;
			}*/
    }

    @Override
    protected Intent getGestureActivityIntent(TopBaseActivity activity) {
        return null;
    }


    protected abstract int setBackgroundColor();

}