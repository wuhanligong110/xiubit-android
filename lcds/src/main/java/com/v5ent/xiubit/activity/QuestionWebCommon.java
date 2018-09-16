package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.UserInfo;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;

import java.util.HashMap;

/**
 * 公司: tophlc
 * 类说明：Activity-常见问题
 *
 * @date 2016-4-15
 */
public class QuestionWebCommon extends WebActivityCommon implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headerLayout.showRightTextButton(R.string.question_service_mm, this);

    }

    public static void showThisActivity(TopBaseActivity ctx, String url, String title) {
        Intent intent = new Intent(ctx, QuestionWebCommon.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        ctx.showActivity(ctx, intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rightContainer:
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("click", "counts");

                UserInfo curUser = MyApp.getInstance().getLoginService().getCurUser();
                if (curUser!=null&&curUser.getCfpLevelInt() > 1) {
                    showActivity(this, MyCustomerService.class);
                } else {
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra("toChatUsername", MyApp.getInstance().getUserService().getCallServiceEMId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }
    }
}
