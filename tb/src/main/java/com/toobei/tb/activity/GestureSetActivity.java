package com.toobei.tb.activity;

import android.view.View.OnClickListener;

import com.toobei.common.activity.TopGestureSetActivity;
import com.toobei.tb.R;

import org.xsl781.ui.ActivityStack;

/**
 * 公司: tophlc
 * 类说明：手势密码设置 界面
 * @date 2016-3-15
 */
public class GestureSetActivity extends TopGestureSetActivity implements OnClickListener {

	@Override
	protected void skipMainActivity() {
		/*//手势通过后，跳转
		MyApp.getInstance().getLoginService()
				.checkUserHasLcs(GestureSetActivity.this, true, new UpdateViewCallBack<String>() {

					@Override
					public void updateView(Exception e, String object) {
						if (object == null) {
							//异常数据
							ToastUtil.showCustomToast("登录失败，请重试！");
						} else if (object != null && object.equals("false")) {
							// 没有理财师 跳出问卷界面
							skipActivity(GestureSetActivity.this, MyQuestionnaireActivity.class);
						} else if (object != null && object.equals("true")) {
							skipActivity(GestureSetActivity.this, MainActivity.class);
						}
					}
				});*/
//        startActivity(new Intent(ctx, MainActivity.class));
//        MainActivity.activity.switchFragment(0);  //跳转到首页推荐页面
//        finish();
        ActivityStack.getInstance().finishOthersActivity(ActivityStack.getInstance().topActivity().getClass());
        skipActivity(GestureSetActivity.this, MainActivity.class);
    }

	protected int[] setLocusPassWordViewImage() {
		return new int[]{R.drawable.img_locus_round_click, R.drawable.img_locus_line};
	}

	@Override
	protected int setBackgroundColor() {
		return R.color.WHITE;
	}
}