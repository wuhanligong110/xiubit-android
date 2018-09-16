package com.v5ent.xiubit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jungly.gridpasswordview.Util;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.entity.FundBaseDefinedData;
import com.toobei.common.entity.FundBaseDefinedEntiy;
import com.toobei.common.entity.FundSiftEntiy;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.toobei.common.utils.KeyboardUtil;
import com.toobei.common.utils.NetworkUtils;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.data.holder.FundExceptionHolder;
import com.v5ent.xiubit.data.holder.FundSearchHolder;
import com.v5ent.xiubit.data.recylerapter.FundListAdapter;
import com.v5ent.xiubit.event.FundFilterRefreshEvent;
import com.v5ent.xiubit.event.FundListBackRefreshEvent;
import com.v5ent.xiubit.event.OpenFundSearchEvent;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;
import com.v5ent.xiubit.view.loadstatue.LoadStatueView;
import com.v5ent.xiubit.view.popupwindow.FundFilterPopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/18
 */

public class FundListFragment extends FragmentBase {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.searchFl)
    FrameLayout mSearchFl;

    private View mRootView;
    private int pageIndex = 1;
    private FundListAdapter mAdapter;
    private List<FundBaseDefinedData.FundTypeListBean> mFundTypeList = new ArrayList<>();
    private String fundType = "MM";
    private String periodTypeText = "一年收益率";
    private List<FundBaseDefinedData.FundTypeListBean> mPeriodList = new ArrayList<>();
    private String period = "year1";
    private FundFilterPopupWindow mFundFilterLeftPopupWindow;
    private FundFilterPopupWindow mFundFilterRightPopupWindow;
    private HeadViewHolder mHeadViewHolder;
    private FundSearchHolder mFundSearchHolder;
    private FundExceptionHolder mFundExceptionHolder;
    private int mFundExceptionViewheight;
    private boolean hasCreatView;
    private boolean notRefreshFilter;
    private boolean doRefresh;
    private boolean isSearchShow;
    private LoadStatueView mLoadStatueView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        initBus();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_fund_list, null);
        pageIndex = 1;
        ButterKnife.bind(this, mRootView);
        mLoadStatueView = new LoadStatueView(ctx, mRootView, new LoadStatueView.ClickCallBack() {
            @Override
            public void onRefreshNetClick() {
                onRefresh();
            }
        });
        initView();
        getNetData();
        hasCreatView = true;
        return mLoadStatueView.getRootView();
    }

   

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d("isVisibleToUser====>" + isVisibleToUser );
        if (isVisibleToUser == true && hasCreatView){
            resetFilter(new FundListBackRefreshEvent(true));
        }
    }

    private void initView() {

        mHeadViewHolder = new HeadViewHolder();
        mAdapter = new FundListAdapter();
        mAdapter.addHeaderView(mHeadViewHolder.mRootView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));

        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                FundListFragment.this.onRefresh();
                mRefreshView.finishRefresh();
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        },mRecyclerView);

        //异常视图显示
        mHeadViewHolder.mRootView.measure(0, 0);
        mFundExceptionViewheight = MyApp.displayHeight   //屏幕高度
                - SystemTool.getStatusBarHeight(mAppContext)  //状态栏
                - Util.dp2px(ctx, 43)   //顶部导航栏
                - mHeadViewHolder.mRootView.getMeasuredHeight() //基金页头部
                - Util.dp2px(ctx, 49);  //底部导航栏
        mFundExceptionHolder = new FundExceptionHolder(ctx, mFundExceptionViewheight);
        mAdapter.setHeaderAndEmpty(true);

        removeSearchView();

    }

    private void removeSearchView() {
        if (mFundSearchHolder !=null){
            mSearchFl.removeAllViews();
            mFundSearchHolder = null;
            isSearchShow = false;
        }
    }

    private void getNetData() {
        if (NetworkUtils.isConnected()){
            mLoadStatueView.showContantView();
        }else {
            mLoadStatueView.showFailView();
            return;
        }
        //获取产品类型定义
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .fundBaseDefined(new ParamesHelp().build(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundBaseDefinedEntiy>() {
                    @Override
                    public void onNext(@NonNull FundBaseDefinedEntiy response) {
                        noMsgToast = true;
                        super.onNext(response);
                    }

                    @Override
                    public void bindViewWithDate(FundBaseDefinedEntiy response) {
                        //缓存筛选项目
                        initTabView(response);
                        getFunListData();
                        MyApp.getInstance().getCurUserSp().setFundFilterItems(response);
                    }

                    @Override
                    public void onNetSystemException(String error) {
                        super.onNetSystemException(error);
                        if (pageIndex == 1) {
                            mAdapter.setEmptyView(mFundExceptionHolder.mRootView);
                        }
                        FundBaseDefinedEntiy response = MyApp.getInstance().getCurUserSp().getFundFilterItems();
                        if (response != null) { //读取缓存，加载页面
                            initTabView(response);
                            getFunListData();
                        }
                    }

                });

    }

    private void initTabView(FundBaseDefinedEntiy response) {
        fundType = response.getData().defaultFundType;
        period = response.getData().defaultPeriod;
        mFundTypeList = response.getData().fundTypeList;
        mPeriodList = response.getData().periodList;

        initTabView();
    }

    private void initTabView() {
        for (FundBaseDefinedData.FundTypeListBean bean : mPeriodList) {
            if (period.equals(bean.fundTypeKey)) {
                mHeadViewHolder.mFundSortSelectTV.setText(bean.fundTypeValue);
                periodTypeText = bean.fundTypeValue;
            }
        }
    }

    /**
     * 触发筛选
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshFilt(FundFilterRefreshEvent event) {
        switch (event.type) {
            case FundFilterPopupWindow.TYPE_FUNDTYPE:
                fundType = event.typeKeyString;
                break;
            case FundFilterPopupWindow.TYPE_PERIODTYPE:
                period = event.typeKeyString;
                break;
        }
        onRefresh();
    }

    /**
     * 打开基金模糊搜索页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openFundSearchPage(OpenFundSearchEvent event) {
        Logger.e("OpenFundSearchEvent-get");
        if (isSearchShow) return;
        mFundSearchHolder = new FundSearchHolder(ctx, period, periodTypeText);
        mFundSearchHolder.setOnCancelListener(new FundSearchHolder.OnCancelListener() {
            @Override
            public void onCancel() {
                removeSearchView();
                KeyboardUtil.hideKeyboard(ctx);
            }
        });
        mSearchFl.addView(mFundSearchHolder.mRootView);
        isSearchShow = true;
//        mFundSearchHolder.refreshParams(period, periodTypeText);
//        mFundSearchHolder.mRootView.setVisibility(View.VISIBLE);
    }

    /**
     * 重置页面（除基金产品详情返回时)
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetFilter(FundListBackRefreshEvent event) {
        if (event.doRefresh) {
            pageIndex = 1;
            mFundFilterLeftPopupWindow = null;
            mFundFilterRightPopupWindow = null;
            removeSearchView();
            getNetData();
        }
    }


    public void getFunListData() {
        //产品列表
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .fundList(new ParamesHelp()
                        .put("pageIndex", pageIndex + "")
                        .put("pageSize", "10")
                        .put("sort", "DESC")
                        .put("fundType", fundType)
                        .put("period", period)
                        .build(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundSiftEntiy>() {

                    @Override
                    public void onNext(@NonNull FundSiftEntiy response) {
                        noMsgToast = true;
                        super.onNext(response);
                    }

                    @Override
                    public void onNetSystemException(String errorCode) {
                        super.onNetSystemException(errorCode);
                        if (pageIndex != 1) return;
                        mAdapter.setEmptyView(mFundExceptionHolder.mRootView);
                        mAdapter.isUseEmpty(true);
                    }


                    @Override
                    public void bindViewWithDate(FundSiftEntiy response) {
                        mAdapter.isUseEmpty(false);
                        if (response.getData().getPageIndex() == 1) {
                            mAdapter.setDateType(period, periodTypeText);
                            mAdapter.setNewData(response.getData().getDatas());
                        } else {
                            mAdapter.addData(response.getData().getDatas());
                        }

                        bindLoadMoreView(response.getData(),mAdapter);
                        pageIndex ++;

                    }
                    
                    
                    
                });
    }


    private void onRefresh() {
        if (NetworkUtils.isConnected()){
            mLoadStatueView.showContantView();
        }else {
            mLoadStatueView.showFailView();
            return;
        }
        pageIndex = 1;
        if (mFundTypeList.size() == 0 || mPeriodList.size() == 0) {
            mFundFilterLeftPopupWindow = null;
            mFundFilterRightPopupWindow = null;
            getNetData();
        } else {
            initTabView();
            getFunListData();
        }
    }

    private void loadMore() {
        initTabView();
        getFunListData();
    }

    class HeadViewHolder {

        @BindView(R.id.fundTypeSelectTv)
        TextView mFundTypeSelectTv;
        @BindView(R.id.fundSortSelectTV)
        TextView mFundSortSelectTV;
        @BindView(R.id.rootView)
        LinearLayout mRootView;
        @BindView(R.id.bannerIv)
        ImageView mBannerIv;
        @BindView(R.id.fundTypeLl)
        LinearLayout mFundTypeLl;
        @BindView(R.id.fundSortLl)
        LinearLayout mFundSortLl;
        @BindView(R.id.lineBottom)
        View mLineBottom;

        HeadViewHolder() {
            View rootView = LayoutInflater.from(ctx).inflate(R.layout.head_fragment_fundlist, null);
            ButterKnife.bind(this, rootView);
            mBannerIv.getLayoutParams().height =MyApp.displayWidth * 250 / 750;
        }

        @OnClick({R.id.fundTypeLl, R.id.fundSortLl, R.id.bannerIv})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.fundTypeLl:
                    if (mFundFilterLeftPopupWindow == null) {
                        mFundFilterLeftPopupWindow = new FundFilterPopupWindow(ctx, mFundTypeList, fundType, FundFilterPopupWindow.TYPE_FUNDTYPE);
                    }
                    mFundFilterLeftPopupWindow.showAsDropDown(mLineBottom);
//                    mFundTypeSelectTv.setSelected(true);
                    break;
                case R.id.fundSortLl:
                    if (mFundFilterRightPopupWindow == null) {
                        mFundFilterRightPopupWindow = new FundFilterPopupWindow(ctx, mPeriodList, period, FundFilterPopupWindow.TYPE_PERIODTYPE);
                    }
                    mFundFilterRightPopupWindow.showAsDropDown(mLineBottom);
                    break;
                case R.id.bannerIv:
                    WebActivityCommon.showThisActivity((TopBaseActivity) ctx, C.FUND_INTRODUCE_PAGE, "");
                    break;
            }
        }


    }

}
