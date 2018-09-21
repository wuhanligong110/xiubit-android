package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.toobei.common.utils.SystemFunctionUtil;
import com.v5ent.xiubit.MyTitleBaseActivity;
import com.v5ent.xiubit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/31
 */

public class PhoneAuthentActivity extends MyTitleBaseActivity {

    @BindView(R.id.phoneNumTv)
    TextView mPhoneNumTv;
    @BindView(R.id.callPhone)
    TextView callPhone;
    private String mServerNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        headerLayout.showTitle("手机号");
        headerLayout.showLeftBackButton();
        mPhoneNumTv.setText(getIntent().getStringExtra("phoneNum"));
        mServerNum = "0755-86725461";
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemFunctionUtil.INSTANCE.CallServicePhone(ctx,mServerNum);
            }
        });

    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_phone_authent;
    }


}
