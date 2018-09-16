package com.toobei.tb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.tb.MyApp;
import com.toobei.tb.MyNetworkBaseActivity;
import com.toobei.tb.R;
import com.toobei.tb.adapter.AccountBindOrgListAdapter;
import com.toobei.tb.adapter.AccountUnBindOrgListAdapter;
import com.toobei.tb.entity.AccountManageDatasDataEntity;
import com.toobei.tb.entity.AccountManageDetail;
import com.toobei.tb.entity.AccountManagerStatisticsEntity;
import com.toobei.tb.entity.CurrInvestAmountEntity;
import com.toobei.tb.entity.CurrInvestAmountModel;

import org.xsl781.ui.xlist.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hasee-pc on 2016/12/29.
 * 我的投资
 */
public class MyInvestOrgActivity extends MyNetworkBaseActivity<AccountManagerStatisticsEntity> implements PullToRefreshBase.OnRefreshListener, View.OnClickListener {


    @BindView(R.id.totalInvestQuesIv)
    ImageView totalInvestQuesIv;
    @BindView(R.id.totalInvestTv)
    TextView totalInvestTv;
//    @BindView(R.id.totalProfitTv)
//    TextView totalProfitTv;
//    @BindView(R.id.totalProfitQuesIv)
//    ImageView totalProfitQuesIv;
    @BindView(R.id.invest_detail)
    LinearLayout investDetail;
    @BindView(R.id.bindOrgNumTv)
    TextView bindOrgNumTv;
    @BindView(R.id.remindHasNoBindTv)
    TextView remindHasNoBindTv;
    @BindView(R.id.bindOrgLv)
    ListViewForScrollView bindOrgLv;
    @BindView(R.id.unbindOrgNumTv)
    TextView unbindOrgNumTv;
    @BindView(R.id.unbindOrgLv)
    ListViewForScrollView unbindOrgLv;
    @BindView(R.id.remindHasAllBindTv)
    TextView remindHasAllBindTv;
    @BindView(R.id.pull_refresh_scrollview)
    PullToRefreshScrollView pullRefreshScrollview;

    String mToken;

    private PromptDialogMsg mPromptDialogMsg;
    private String pageIndex = "1";
    private String pageSize = "50";
    private String mBindOrgAccountCount;  //绑定账户数量
    private String mUnBindOrgAccountCount; //未绑定账户数量
    //账户是否绑定
    private String ACCOUNT_TYPE_BIND = "1";
    private String ACCOUNT_TYPE_UNBIND = "2";

    List<AccountManageDetail> mBindDatas = new ArrayList<>();
    List<AccountManageDetail> mUnBindDatas = new ArrayList<>();
    private String mCurrInvestAmount; //在投资金
//    private String mtotalInvestAmount; //累计投资金额
//    private String mTotalProfitAmount; //累计收益金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mToken = MyApp.getInstance().getLoginService().token;
        initView();
    }

    private void initView() {
        headerLayout.showTitle("我的投资平台");
        headerLayout.showLeftBackButton();

        pullRefreshScrollview.setOnRefreshListener(this);
        totalInvestQuesIv.setOnClickListener(this);
//        totalProfitQuesIv.setOnClickListener(this);
        investDetail.setOnClickListener(this);

    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_my_invest;
    }

    @Override
    protected AccountManagerStatisticsEntity onLoadDataInBack() throws Exception {
        LoadData();
        return MyApp.getInstance().getHttpService().accountManagerStatistics(MyApp.getInstance().getLoginService().token);
    }

    @Override
    protected void onRefreshDataView(AccountManagerStatisticsEntity data) {
//        pullRefreshScrollview.onRefreshComplete();
        mBindOrgAccountCount = data.getData().getBindOrgAccountCount();
        mUnBindOrgAccountCount = data.getData().getUnBindOrgAccountCount();

        if ("0".equals(mBindOrgAccountCount)) {
            remindHasNoBindTv.setVisibility(View.VISIBLE);
        } else {
            remindHasNoBindTv.setVisibility(View.GONE);
        }
        if ("0".equals(mUnBindOrgAccountCount)) {
            remindHasAllBindTv.setVisibility(View.VISIBLE);
        } else {
            remindHasAllBindTv.setVisibility(View.GONE);
        }

        bindOrgNumTv.setText(getSpanRedText("已开通账户(" + mBindOrgAccountCount + ")"));
        unbindOrgNumTv.setText(getSpanRedText("未开通账户(" + mUnBindOrgAccountCount + ")"));
        //华为手机第一次进入的时候，会出现页面不在最顶端的情况，是ScrollView嵌套ListView导致的，先让它们失去焦点可以解决这个问题
        bindOrgLv.setFocusable(false);
        unbindOrgLv.setFocusable(false);
//        LoadData();
    }

    /**
     * 对字符串颜色处理
     *
     * @param str
     */
    public SpannableString getSpanRedText(String str) {
        SpannableString span = new SpannableString(str);
        span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(ctx,R.color.text_red_common)), str.indexOf("(") + 1, str.indexOf(")"), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return span;
    }

    /**
     * 请求网络数据
     */
    private void LoadData() {
        getInvestAmount(false);
        getAccountManagerList(ACCOUNT_TYPE_BIND, false);
        getAccountManagerList(ACCOUNT_TYPE_UNBIND, false);

    }

    /**
     * 获取账户管理列表
     *
     * @param type 绑定类型
     */
    public void getAccountManagerList(final String type, boolean flag) {

        new MyNetAsyncTask(ctx, flag) {
            AccountManageDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().accountManagerList(mToken, pageIndex, pageSize, type);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<AccountManageDetail> datas = response.getData().getDatas();
                        if (type == ACCOUNT_TYPE_BIND) {
                            mBindDatas.clear();
                            mBindDatas.addAll(datas);
                            RefreshBindUi();
                        } else if (type == ACCOUNT_TYPE_UNBIND) {
                            mUnBindDatas.clear();
                            mUnBindDatas.addAll(datas);
                            RefreshUnBindUi();
                        }


                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(ctx.getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();

    }

    private void RefreshBindUi() {
        bindOrgLv.setAdapter(new AccountBindOrgListAdapter(ctx, mBindDatas));
    }

    private void RefreshUnBindUi() {
        unbindOrgLv.setAdapter(new AccountUnBindOrgListAdapter(ctx, mUnBindDatas));
    }


    private void RefreshHeadUi() {
        totalInvestTv.setText(mCurrInvestAmount);
//        totalProfitTv.setText(mTotalProfitAmount);
    }

    /*
     * 获取在投资金
     */
    public void getInvestAmount(final boolean flag) {
        new MyNetAsyncTask(ctx, flag) {
            CurrInvestAmountEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().getCurrInvestAmount(MyApp.getInstance().getLoginService().token);
            }

            @Override
            protected void onPost(Exception e) {
                // 登录成功
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        CurrInvestAmountModel data = response.getData();
                        mCurrInvestAmount = data.getAmount();
                        RefreshHeadUi();
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        loadDataFromNet(false);
    }

    public void refreshUI() {
        loadDataFromNet(false);
    }

    @Override
    protected void onRefreshDataViewStart() {
        super.onRefreshDataViewCompleted();
        pullRefreshScrollview.onRefreshComplete();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.totalInvestQuesIv:
                mPromptDialogMsg = new PromptDialogMsg(ctx, getString(R.string.dialog_mine_totalInvestExplain), "关闭");
                mPromptDialogMsg.show();
                break;

//            case R.id.totalProfitQuesIv:
//                mPromptDialogMsg = new PromptDialogMsg(ctx, getString(R.string.dialog_mine_totalProfitExplain), "关闭");
//                mPromptDialogMsg.show();
//                break;

            case R.id.invest_detail:
                Intent intent = new Intent(this,MyInvestDetailActivity.class);
//                intent.putExtra("totalProfitAmount",mTotalProfitAmount);
                intent.putExtra("title","投资明细");
                startActivity(intent);
                break;
        }
    }
}
