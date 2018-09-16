package com.toobei.common.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.TextDecorator;
import com.toobei.common.view.HeaderLayout;

/**
 * 公司: tophlc
 * 类说明：提现成功界面 基类
 *
 * @date 2016-4-8
 */
public abstract class TopWithdrawSuccess extends TopBaseActivity{

    private TextView textWithdrawMoney, textBankCardInfo, textToAccountTime, textCanWithdrawCost,
            textBankBranchInfo, infoTv;
    private ViewGroup vgBankBranchInfoLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopApp.getInstance().getAccountService().setMineNeedRefresh(true);
        setContentView(R.layout.activity_withdraw_success);
        initView();
        hideSoftInputView();
    }

    protected abstract void skipWithdrawList(TopBaseActivity activity);

    public void initView() {
        textWithdrawMoney = (TextView) findViewById(R.id.text_withdraw_money);
        textBankCardInfo = (TextView) findViewById(R.id.text_bank_card_info);
        textToAccountTime = (TextView) findViewById(R.id.text_to_account_time);
        textCanWithdrawCost = (TextView) findViewById(R.id.text_can_withdraw_cost);
        vgBankBranchInfoLL = (ViewGroup) findViewById(R.id.bank_branch_info_rootll);
        infoTv = (TextView) findViewById(R.id.infoTv);

        textWithdrawMoney.setText(getIntent().getStringExtra("strMoney") + " 元");
        textBankCardInfo.setText(getIntent().getStringExtra("strBankCardInfo"));
        textToAccountTime.setText(getIntent().getStringExtra("strToAccountTime"));
        String withdrawalInfo = getIntent().getStringExtra("withdrawalInfo");
        String needInputBranck = getIntent().getStringExtra("needInputBranck");
        if (needInputBranck != null && needInputBranck.equals("true")) {
            vgBankBranchInfoLL.setVisibility(View.VISIBLE);
            String inputBranck = getIntent().getStringExtra("inputBranck");
            textBankBranchInfo = (TextView) findViewById(R.id.text_bank_branch_info);
            textBankBranchInfo.setText(inputBranck);
        }

        textCanWithdrawCost.setText(withdrawalInfo);
        //		if (strHasFee != null && strHasFee.equals("true")) {
        //			if (strCost != null && (strCost.equals("0.00") || strCost.equals("0"))) {
        //				textCanWithdrawCost.setText("本次免费");
        //			} else {
        //				textCanWithdrawCost.setText("本次提现手续费" + StringUtil.getIntStr(strCost) + " 元");
        //			}
        //		} else {
        //
        //		}

        String telstr = TopApp.getInstance().getDefaultSp().getServiceTelephone();
        if (!TextUtils.isEmpty(telstr)) {
            String info = ctx.getString(R.string.withdraw_prompt) + telstr;
            TextDecorator.decorate(infoTv, info).setTextColor(R.color.text_blue_common, telstr).build();
        }
        initTopView();
    }

    private void initTopView() {
        headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);
        headerLayout.showTitle(R.string.withdraw);
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(R.string.withdraw_record, new OnClickListener() {
            @Override
            public void onClick(View v) {
                skipWithdrawList(ctx);
            }
        });
        setTranslucentStatus(true);
        headerLayout.setHeadViewCoverStateBar();
    }

}
