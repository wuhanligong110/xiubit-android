package com.toobei.tb.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.RewardDetailFragmentPageAdapter;
import com.toobei.tb.entity.AccountBalanceEntity;
import com.toobei.tb.entity.TabTypeBean;
import com.toobei.tb.view.MyPagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * T呗奖励余额
 * Created by hasee-pc on 2017/1/4.
 */
public class MyAccountAmountActivity extends MyNetworkBaseActivity<AccountBalanceEntity> implements View.OnClickListener {
    @BindView(R.id.quesIv)
    ImageView quesIv;
    @BindView(R.id.linearlayout1)
    LinearLayout linearlayout1;
    @BindView(R.id.rewardBalanceTv)
    TextView rewardBalanceTv;
    @BindView(R.id.Withdraw)
    Button Withdraw;
    @BindView(R.id.rewardIncomeTv)
    TextView rewardIncomeTv;
    @BindView(R.id.rewardOutTv)
    TextView rewardOutTv;
    @BindView(R.id.pagerSlidingTabs)
    MyPagerSlidingTabStrip pagerSlidingTabs;
    @BindView(R.id.vPager)
    ViewPager vPager;
    private String typeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        //0=全部1=奖励收入明细|2=奖励支出明细
        typeValue = getIntent().getStringExtra("typeValue");

    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_tb_account_amount;
    }

    @Override

    protected AccountBalanceEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getAccountBalance(MyApp.getInstance().getLoginService().token);
    }


    @Override
    protected void onRefreshDataView(AccountBalanceEntity data) {
        if (data != null){
            rewardBalanceTv.setText(data.getData().getRewardBalance());
            rewardIncomeTv.setText(data.getData().getRewardIncome());
            rewardOutTv.setText(data.getData().getRewardOut());
        }
    }


    private void initView() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("T呗账户余额");

        quesIv.setOnClickListener(this);
        Withdraw.setOnClickListener(this);
        List<TabTypeBean> list = new ArrayList<>();
        list.add(new TabTypeBean("全部","0"));
        list.add(new TabTypeBean("收入","1"));
        list.add(new TabTypeBean("支出","2"));
        vPager.setAdapter(new RewardDetailFragmentPageAdapter(ctx,getSupportFragmentManager(),list));
        pagerSlidingTabs.setViewPager(vPager);
//        //1=奖励收入明细|2=奖励支出明细）
        if ("0".equals(typeValue)) {
            vPager.setCurrentItem(0);
        }
        if ("1".equals(typeValue)) {
            vPager.setCurrentItem(1);
        }
        if ("2".equals(typeValue)) {
            vPager.setCurrentItem(2);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quesIv:
                PromptDialogMsg PromptDialogMsg = new PromptDialogMsg(ctx, getString(R.string.account_amount_question_remind), "关闭");
                PromptDialogMsg.show();
                break;
//            case R.id.WithdrawLog:  //提现记录
//                showActivity(ctx, WithdrawList.class);
//                break;
            case R.id.Withdraw:  //提现

//                if ("false".equals(TopApp.getInstance().getCurUserSp().isBoundedBankCard())) {
//                    showActivity(ctx, CardManagerAdd.class);
//                } else {
//                    showActivity(ctx, Withdraw.class);
//                }
                MyApp.getInstance().getAccountService().checkBoundedCardAndInitPayAndSkip(this, new UpdateViewCallBack<Boolean>() {
                    @Override
                    public void updateView(Exception e, Boolean object) {
                        showActivity(ctx, Withdraw.class);
                    }
                });

                break;

        }
    }




}