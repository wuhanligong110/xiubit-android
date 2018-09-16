package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.TopApp;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.UpdateViewCallBack;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.common.view.popupwindow.WebPromptPopupWindow;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.AccountIncomeAdapter;
import com.v5ent.xiubit.entity.AccountIncome;
import com.v5ent.xiubit.entity.AccountIncomeDatasDataEntity;
import com.v5ent.xiubit.entity.AccountIncomeHomeData;
import com.v5ent.xiubit.entity.AccountIncomeHomeEntity;
import com.v5ent.xiubit.view.MyTextView;

import java.util.List;

/**
 * 公司: tophlc
 * 类说明：Activity-余额
 *
 * @date 2016-11-22
 */
public class MyAccountIncomeActivity extends MyNetworkBaseActivity<AccountIncomeDatasDataEntity> implements View.OnClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private AccountIncomeAdapter adapter;
    private MyTextView textBalance;
    private int pageIndex = 1;
    private String balance = "0.00";//余额
    private WebPromptPopupWindow webPromptPopupWindow;
    private RecyclerView recyclerView;
    private SmartRefreshLayout mRefreshView;
    private boolean loadingMoreEnabled;
    private View mHeadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        initTopTitle();
        pageIndex = 1;
        TopApp.getInstance().getAccountService().accountGetUserBindCard(this, null);
        getHeadData();
        loadData(true);
    }

    private void findView() {
        mHeadView = LayoutInflater.from(ctx).inflate(R.layout.myaccount_income_list_headview,null,true);
        textBalance = (MyTextView) mHeadView.findViewById(R.id.text_account_balance);
        mHeadView.findViewById(R.id.btn_withdraw).setOnClickListener(this);
        mHeadView.findViewById(R.id.btn_withdraw_list).setOnClickListener(this);
        mHeadView.findViewById(R.id.goto_account_detial_ll).setOnClickListener(this);
        mHeadView.findViewById(R.id.myaccountQuestionTv).setOnClickListener(this);

        initRefreshView();
        initRecycleView();
    }

    private void initRefreshView() {
        mRefreshView = (SmartRefreshLayout) findViewById(R.id.refreshView);
        mRefreshView.setOnRefreshListener(this);
    }


    private void initTopTitle() {
        headerLayout.showTitle("累计收益");
        headerLayout.showRightTextButton("资金明细", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivity(ctx, AccountDetialActivity.class);
            }
        });
        headerLayout.showLeftBackButton();
    }

    private void initRecycleView() {
        
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);

        adapter = new AccountIncomeAdapter(this);
        LinearLayoutManager lm = new LinearLayoutManager(ctx);
        adapter.addHeaderView(mHeadView);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(true);
        adapter.setPreLoadNumber(3);
        adapter.setOnLoadMoreListener(this,recyclerView);
    }


    private void getHeadData() {

        new MyNetAsyncTask(ctx, false) {
            AccountIncomeHomeEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().accountMyAccountIncomeHome(MyApp.getInstance().getLoginService().token);
            }


            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        balance = StringUtil.formatNumber(response.getData().getAccountBalance());//
                        refreshHeadView(response.getData());
                    } else {
                        com.toobei.common.utils.ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    com.toobei.common.utils.ToastUtil.showCustomToast(getString(com.toobei.common.R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    private void refreshHeadView(AccountIncomeHomeData data) {
        textBalance.setText(balance);
    }


    public void loadMore() {
        loadDataFromNet(false);
    }


    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_myaccount_income;
    }

    @Override
    protected AccountIncomeDatasDataEntity onLoadDataInBack() throws Exception {
        AccountIncomeDatasDataEntity response = MyApp.getInstance().getHttpService().accountMyAccountProfixTotalList(TopApp.getInstance().getLoginService().token, pageIndex + "");
        return response;
    }

    @Override
    protected void onRefreshDataView(AccountIncomeDatasDataEntity response) {
        List<AccountIncome> datas = response.getData().getDatas();
        pageIndex = response.getData().getPageIndex();
        if (pageIndex == 1) {
            adapter.setNewData(datas);
        } else {
            adapter.addData(datas);
        }
        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
        pageIndex++;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_withdraw:
                MyApp.getInstance().getAccountService().checkBoundedCardAndInitPayAndSkip(this, new UpdateViewCallBack<Boolean>() {
                    @Override
                    public void updateView(Exception e, Boolean object) {
                        if (object != null && object) {
                            Intent intent = new Intent(MyAccountIncomeActivity.this, Withdraw.class);
                            intent.putExtra("accountBalance", Float.parseFloat(balance));
                            showActivity(MyAccountIncomeActivity.this, intent);
                        }
                    }
                });
                break;
            case R.id.btn_withdraw_list:
                showActivity(this, WithdrawList.class);
                break;

            case R.id.goto_account_detial_ll:
                showActivity(ctx, AccountDetialActivity.class);
                break;
            case R.id.myaccountQuestionTv:
                PromptDialogMsg promptDialogMsg03 = new PromptDialogMsg(ctx, getResources().getString(R.string.myaccount_income_accout), "知道了");
                promptDialogMsg03.show();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pageIndex = 1;
        loadDataFromNet(false);
        getHeadData();
        mRefreshView.finishRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }
}
