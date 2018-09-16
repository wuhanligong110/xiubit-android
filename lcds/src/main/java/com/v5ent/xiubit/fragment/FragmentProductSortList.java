package com.v5ent.xiubit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetworkUtils;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recylerapter.ProductListRecycleAdapter;
import com.v5ent.xiubit.entity.ProductPageListStatisticsEntity;
import com.v5ent.xiubit.view.EmptyView;
import com.v5ent.xiubit.view.ProductListSortHeadLayout;
import com.v5ent.xiubit.view.loadstatue.LoadStatueView;
import com.v5ent.xiubit.view.popupwindow.ProductFilterPopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/21
 */

public class FragmentProductSortList extends FragmentBase implements ProductFilterPopupWindow.OnBtnClickListener {

    @BindView(R.id.headSorTab)
    ProductListSortHeadLayout mHeadSorTab;
    @BindView(R.id.recyclerView)
    RecyclerView mProductListRv;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.backToTopIBtn)
    ImageButton backToTopIBtn;
    private View mRootView;
    private Unbinder mBind;
    private ProductListRecycleAdapter mProductListAdapter;
    //dLa=不限 dLb=1个月内 dLc=1-3个月 dLd=3-6个月 dLe=6-12个月 dLf=12个月以上
    private String[] deadLineArrs = {"dLa", "dLb", "dLc", "dLd", "dLe", "dLf"};
    private int deadLineArrsIndex;
    //fRa=不限 fRb=8%以下 fRc=8%-12% fRd=12%以上
    private String[] flowRateArrs = {"fRa", "fRb", "fRc", "fRd"};
    private int flowRateArrsIndex;
    //ifRookie	用户类型	number	0-不限 1-新手用户
    private String[] ifRookieArrs = {"0", "1"};
    private int ifRookieIndex;
    //order	顺序	number	0-升序 1-降序
    private int order = 1;
    //sort 1-年化收益 2-产品期限
    private int sort = 1;
    private String orgCode = null;
    private int pageIndex = 1;
    private int pageSize = 10;
    private ProductFilterPopupWindow mProductFilterPopupWindow;
    private LinearLayoutManager mLm;
    private LoadStatueView mLoadStatueView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_product_sort_list, null);
        mBind = ButterKnife.bind(this, mRootView);
        mLoadStatueView = new LoadStatueView(ctx, mRootView, new LoadStatueView.ClickCallBack() {
            @Override
            public void onRefreshNetClick() {
                refreshData();
            }
        });
        pageIndex = 1;
        initView();
       
        getSelectProductData();
        return mLoadStatueView.getRootView();
    }

    private void initView() {
       

        mHeadSorTab.setListener(new ProductListSortHeadLayout.OnHeadTitleClickListener() {
            @Override
            public void headTitleClicked(int index, boolean isDown) {
                switch (index) {
                    case 1:
                        order = 1;  //年化收益只做降序排序
                        sort = 1;
                        refreshData();
                        break;
                    case 2:
                        MobclickAgent.onEvent(ctx,"T_2_5");
                        order = isDown ? 1 : 0;
                        sort = 2;
                        refreshData();
                        break;

                    case 3:
                        MobclickAgent.onEvent(ctx,"T_2_3");
//                        ToastUtil.showCustomToast("筛选");
                        if (mProductFilterPopupWindow == null) {
                            mProductFilterPopupWindow = new ProductFilterPopupWindow(ctx);
                        }
                        ProductFilterPopupWindow.PopFilterSelectBean popFilterSelectBean = new ProductFilterPopupWindow.PopFilterSelectBean(deadLineArrsIndex, flowRateArrsIndex, ifRookieIndex);
                        mProductFilterPopupWindow.showPopupWindow(mHeadSorTab);
                        mProductFilterPopupWindow.setOnBtnClickListener(FragmentProductSortList.this);
                        mProductFilterPopupWindow.refreshView(popFilterSelectBean);
                        getProductPageListStatistics(popFilterSelectBean);
                        break;

                }

            }
        });
        mHeadSorTab.initHeadView(1, true);
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshData();
                mRefreshView.finishRefresh();
            }
        });
        //条目显示的缩放特效
        mProductListAdapter = new ProductListRecycleAdapter(ctx);
        mProductListAdapter.setFromTag(ProductListRecycleAdapter.FROM_INVEST);
//        mProductListAdapter.openLoadAnimation(SCALEIN);
        mProductListAdapter.setPreLoadNumber(3);
        mProductListAdapter.setEnableLoadMore(true);
        mProductListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMoreData();
            }
        },mProductListRv);
        ////空视图设定
        EmptyView emptyView = new EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank,getString(R.string.blank_not_find_filter_result));
        mProductListAdapter.setEmptyView(emptyView);
        //recyclerView
        mLm = new LinearLayoutManager(ctx);
        mProductListRv.setLayoutManager(mLm);
       
        mProductListRv.setAdapter(mProductListAdapter);
        mProductListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mLm.findFirstVisibleItemPosition() > 10) {
                    backToTopIBtn.setVisibility(View.VISIBLE);
                } else {
                    backToTopIBtn.setVisibility(View.GONE);
    }
}
        });
        backToTopIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProductListRv.scrollToPosition(0);
            }
        });

    }


    private void getSelectProductData() {
        if (NetworkUtils.isConnected()){
            mLoadStatueView.showContantView();
        }else {
            mLoadStatueView.showFailView();
            return;
        }
        new MyNetAsyncTask(ctx, false) {
            public long loadSysTime;
            ProductDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getProductPageList(deadLineArrs[deadLineArrsIndex], flowRateArrs[flowRateArrsIndex], ifRookieArrs[ifRookieIndex], order + "", orgCode, pageIndex + "", pageSize + "", sort + "");
                loadSysTime = System.currentTimeMillis();
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData() != null) {
                        List<ProductDetail> datas = response.getData().getDatas();
                        if (datas == null) return;
                        pageIndex = response.getData().getPageIndex();
                        if (pageIndex == 1) {
                            mProductListAdapter.setNewData(datas,loadSysTime);
                        } else {
                            mProductListAdapter.addData(datas,loadSysTime);
                        }

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            mProductListAdapter.loadMoreEnd();
                        } else {
                            mProductListAdapter.loadMoreComplete();
                        }
                        pageIndex++;
                    } else {
                        if (isAdded()) {
                            ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        }
                    }
                } else {
                    if (isAdded()) {
                        ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                        mProductListAdapter.loadMoreFail();
                    }
                }
                
            }
        }.execute();
    }

    /**
     * 获取产品筛选统计数量
     */
    private void getProductPageListStatistics(final ProductFilterPopupWindow.PopFilterSelectBean bean) {
        new MyNetAsyncTask(ctx, false) {
            ProductPageListStatisticsEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getProductPageListStatistics(deadLineArrs[bean.deadlineValuePosition], flowRateArrs[bean.flowRatePosition], ifRookieArrs[bean.ifRookiePosition], orgCode);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData() != null) {
                        if (mProductFilterPopupWindow != null) {
                            mProductFilterPopupWindow.setFilterMatchNum(response.getData().getCount());
                        }
                    } else {
                        if (isAdded()) {
                            ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        }

                    }
                } else {
                    if (isAdded()) {
                        ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                    }

                }
               
            }
        }.execute();
    }

    /**
     * 数据刷新
     */
    private void refreshData() {
        if (NetworkUtils.isConnected()){
            mLoadStatueView.showContantView();
        }else {
            mLoadStatueView.showFailView();
            return;
        }
        pageIndex = 1;
        getSelectProductData();
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        getSelectProductData();
    }


    @Override
    public void onFilterBtnClick(ProductFilterPopupWindow.PopFilterSelectBean bean) {
        getProductPageListStatistics(bean);

    }

    @Override
    public void onReset() {
        mHeadSorTab.initHeadView(1, true);
        ifRookieIndex = 0;
        deadLineArrsIndex = 0;
        flowRateArrsIndex = 0;
        order = 1;
        sort = 1;
        refreshData();
        getProductPageListStatistics(new ProductFilterPopupWindow.PopFilterSelectBean());
    }

    @Override
    public void onComplete(ProductFilterPopupWindow.PopFilterSelectBean bean) {
        ifRookieIndex = bean.ifRookiePosition;
        deadLineArrsIndex = bean.deadlineValuePosition;
        flowRateArrsIndex = bean.flowRatePosition;
        if (ifRookieIndex == 0 && deadLineArrsIndex == 0 && flowRateArrsIndex == 0) {
            mHeadSorTab.setFilterBtnStatus(false);
        } else {
            mHeadSorTab.setFilterBtnStatus(true);
        }
        refreshData();
    }
}
