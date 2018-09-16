package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.TopApp;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.StringUtil;
import com.toobei.common.utils.TextFormatUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.dialog.PromptDialogMsg;
import com.toobei.common.view.popupwindow.WebPromptPopupWindow;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.IncomeDetailPagerAdapter;
import com.v5ent.xiubit.entity.AccountIncomeHomeData;
import com.v5ent.xiubit.entity.AccountIncomeHomeEntity;
import com.v5ent.xiubit.entity.IncomeType;
import com.v5ent.xiubit.entity.ProfixType;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.IncomeChartPieLayout;

import org.xsl781.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



/**
 * 公司: tophlc
 * 类说明：Activity-月度收益
 *
 * @date 2015-10-22
 */
public class MyIncomeDetailActivity extends MyNetworkBaseActivity<AccountIncomeHomeEntity> {

    //    @BindView(R.id.tabLayout)
//    com.v5ent.xiubit.view.MyPagerSlidingTabStrip tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.text_income_total)
    TextView textIncomeTotal;
    //    @BindView(R.id.text_income_grantDesc)
//    TextView textIncomeGrantDesc;
    @BindView(R.id.img_myaccount_detail_total_question)
    ImageView imgTotalQuestion;
    @BindView(R.id.img_myaccount_detail_combination_question)
    ImageView imgCombinationQuestion;
    @BindView(R.id.my_income_chart_pie)
    IncomeChartPieLayout chart;
    //已发放
    @BindView(R.id.grantedTextTv)
    TextView grantedTextTv;
    @BindView(R.id.grantedAmountTv)
    TextView grantedAmountTv;
    //待发放
    @BindView(R.id.waitGrantTextTv)
    TextView waitGrantTextTv;
    @BindView(R.id.waitGrantAmountTv)
    TextView waitGrantAmountTv;
    @BindView(R.id.waitGrantAmountQuestIv)
    ImageView waitGrantAmountQuestIv;

    List<ProfixType> profixTypes = new ArrayList<>(); // 1: 已发放 2：待发放
    @BindView(R.id.grantedCursor)
    View grantedCursor;
    @BindView(R.id.waitGrantCursor)
    View waitGrantCursor;
    @BindView(R.id.img_close_top)
    ImageView imgCloseTop;
    @BindView(R.id.top_red_remind_ll)
    LinearLayout topRedRemindLl;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.refreshView)
    SmartRefreshLayout refreshView;


    private String date;
    private String monthDesc;
    private WebPromptPopupWindow webPromptPopupWindow;
    private String intentProfixType; //消息推送的收益
    private ProgressBar progressBar;
    private TextView mTextView;
    private ImageView mImageView;
    private IncomeDetailPagerAdapter mAdapter;
    private int mJumpIndex;
    private boolean isRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initIntent(Intent intent) {
        date = intent.getStringExtra("date");
        monthDesc = intent.getStringExtra("monthDesc");
        intentProfixType = intent.getStringExtra("intentProfixType");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApp.getInstance().getDefaultSp().getIncomeDetailRedTopAllowSee()) {
            topRedRemindLl.setVisibility(View.VISIBLE);
        } else {
            topRedRemindLl.setVisibility(View.GONE);
        }

    }

    private void initView() {
        headerLayout.showTitle(monthDesc);
        headerLayout.showLeftBackButton();

        profixTypes.add(new ProfixType("2", "已发放", null));
        profixTypes.add(new ProfixType("1", "待发放", null));

        if (mAdapter == null) {
            mAdapter = new IncomeDetailPagerAdapter(ctx, getSupportFragmentManager(), profixTypes, date);
            viewPager.setAdapter(mAdapter);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshTabView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                Logger.e("verticalOffset", "verticalOffset=====>" + Boolean.valueOf(verticalOffset >= 0));
                refreshView.setEnableRefresh(verticalOffset >= 0);


            }
        });
        refreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefreshing = true;
                getRefeshData(false);
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    public void refreshAfterLogin() {
        super.refreshAfterLogin();
        isRefreshing = true;
        getRefeshData(false);
    }

    @Override
    protected void onInitParamBeforeLoadData() {
        super.onInitParamBeforeLoadData();
        initIntent(getIntent());//初始化參數
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_my_income_detail;
    }

    @Override
    protected AccountIncomeHomeEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().accountMonthProfixStatistics(MyApp.getInstance().getLoginService().token, date);
    }

    @Override
    protected void onRefreshDataView(AccountIncomeHomeEntity response) {
        AccountIncomeHomeData data = response.getData();
        if (data != null && data.getProfixList() != null) {
            refreshView(data);
        }
    }


    /**
     * 刷新数据
     *
     * @param openDialog openDialog
     */
    private void getRefeshData(boolean openDialog) {

        new MyNetAsyncTask(ctx, openDialog) {

            private AccountIncomeHomeEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().accountMonthProfixStatistics(MyApp.getInstance().getLoginService().token, date);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        AccountIncomeHomeData data = response.getData();
                        if (data != null && data.getProfixList() != null) {
                            refreshView(data);
                            mAdapter.refresh(profixTypes);
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }

        }.execute();

    }


    private void refreshView(AccountIncomeHomeData data) {
        float totalProfix = Float.parseFloat(data.getTotalProfix());
        textIncomeTotal.setText(StringUtil.formatNumber(data.getTotalProfix()));
//        textIncomeGrantDesc.setText(totalProfix == 0 || StringUtils.isEmpty(response.getData().getGrantDesc()) ? "" : "(" + response.getData().getGrantDesc() + ")");
        List<IncomeType> incomeTypes = formatList(data.getProfixList());
        chart.setChartData(incomeTypes, totalProfix == 0);

        String grantedAmount = data.getGrantedAmount();
        String waitGrantAmount = data.getWaitGrantAmount();
        //跳转到第一个有数据的位置
        grantedAmountTv.setText("(" + TextFormatUtil.formatFloat2zero(grantedAmount) + "元)");
        waitGrantAmountTv.setText("(" + TextFormatUtil.formatFloat2zero(waitGrantAmount) + "元)");

        mJumpIndex = 0;
        //有消息推送按照消息推送跳转tab，没有按照跳到第一个有数据的tab
        if (intentProfixType != null) {
            for (int i = 0; i < profixTypes.size(); i++) {
                if (profixTypes.get(i).getProfixType().equals(intentProfixType)) {
                    mJumpIndex = i;
                    return;
                }
            }
        } else if (Float.parseFloat(grantedAmount) > 0) {
            mJumpIndex = 0;
        } else if (Float.parseFloat(waitGrantAmount) > 0) {
            mJumpIndex = 1;
        }

        refreshViewPager(profixTypes);

    }

    /**
     * 对list数据二次处理
     */
    private List<IncomeType> formatList(List<IncomeType> list) {
        List<IncomeType> newList = new ArrayList<>();
//        类型：1佣金，2推荐津贴，3活动奖励，4团队leader奖励，5投返红包，7直管津贴，8团管津贴
        for (IncomeType incomeType : list) {
            switch (incomeType.getProfixType()) {
                case "1":
                    incomeType.setProfixTypeName("销售佣金");
                    newList.add(incomeType);
                    break;
                case "2":
                    incomeType.setProfixTypeName("推荐奖励");
                    newList.add(incomeType);
                    break;
                case "7":
                    incomeType.setProfixTypeName("直接管理津贴");
                    incomeType.setProfixType("3");
                    newList.add(incomeType);
                    break;
                case "8":
                    incomeType.setProfixTypeName("团队管理津贴");
                    incomeType.setProfixType("4");
                    newList.add(incomeType);
                    break;
                case "3":
                    incomeType.setProfixTypeName("活动奖励");
                    incomeType.setProfixType("5");
                    newList.add(incomeType);
                    break;
                case "5":
                    incomeType.setProfixTypeName("投资返现红包");
                    incomeType.setProfixType("6");
                    newList.add(incomeType);
                    break;
            }
        }
        Collections.sort(newList, new Comparator<IncomeType>() {
            @Override
            public int compare(IncomeType lhs, IncomeType rhs) {
                return Integer.valueOf(lhs.getProfixType()) - Integer.valueOf(rhs.getProfixType());
            }
        });
        return newList;
    }


    /**
     * viewpager 选择跳转tab
     */
    private void refreshViewPager(List<ProfixType> profixTypes) {
        if (isRefreshing) {
            isRefreshing = false;
            return;
        }

        if (mAdapter == null) {
            mAdapter = new IncomeDetailPagerAdapter(ctx, getSupportFragmentManager(), profixTypes, date);
            viewPager.setAdapter(mAdapter);
        }

        if (viewPager.getCurrentItem() != mJumpIndex) {
            viewPager.setCurrentItem(mJumpIndex);
        }

        refreshTabView(mJumpIndex);
    }

    /**
     * 刷新tab视图
     *
     * @param position
     */
    private void refreshTabView(int position) {
        switch (position) {
            case 0:
                waitGrantTextTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common_title));
                grantedTextTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                waitGrantCursor.setVisibility(View.INVISIBLE);
                grantedCursor.setVisibility(View.VISIBLE);
                break;
            case 1:
                grantedTextTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_gray_common_title));
                waitGrantTextTv.setTextColor(ContextCompat.getColor(ctx,R.color.text_blue_common));
                grantedCursor.setVisibility(View.INVISIBLE);
                waitGrantCursor.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }


    @OnClick({R.id.img_close_top, R.id.grantedLi, R.id.waitGrantLi, R.id.waitGrantAmountQuestIv, R.id.img_myaccount_detail_total_question, R.id.img_myaccount_detail_combination_question})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_myaccount_detail_total_question:
                PromptDialogMsg promptDialogMsg = new PromptDialogMsg(ctx, getResources().getString(R.string.myaccount_income_combination_request), getString(R.string.i_know));
                promptDialogMsg.show();
                break;
            case R.id.img_myaccount_detail_combination_question:
                if (webPromptPopupWindow == null) {
                    webPromptPopupWindow = new WebPromptPopupWindow(this, TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.URL_BALANCE_RULE_QUESTION) {
                        @Override
                        public void onUrlLoading(boolean isRedirectUsable, String url) {
                            if (!isRedirectUsable) {
                                WebActivityCommon.showThisActivity(MyIncomeDetailActivity.this, url, "", false);
                            }
                        }
                    };
                    webPromptPopupWindow.setWebHeight(490);
                }
                webPromptPopupWindow.showPopupWindow(getDataContentView());
                break;
            case R.id.grantedLi:
                viewPager.setCurrentItem(0);
                break;
            case R.id.waitGrantLi:
                viewPager.setCurrentItem(1);
                break;
            case R.id.waitGrantAmountQuestIv:
                new PromptDialogMsg(ctx, getResources().getString(R.string.wait_grant_amount_request), getString(R.string.i_know)).show();
                break;
            case R.id.img_close_top:
                topRedRemindLl.setVisibility(View.GONE);
                MyApp.getInstance().getDefaultSp().setIncomeDetailRedTopAllowSee(false);
                break;
            default:
                break;
        }


    }

}
