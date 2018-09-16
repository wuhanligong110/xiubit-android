package com.v5ent.xiubit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.entity.HomePagerBanners;
import com.toobei.common.entity.HomePagerBannersDatasDataEntity;
import com.toobei.common.entity.ProductDatasDataEntity;
import com.toobei.common.entity.ProductDetail;
import com.toobei.common.entity.ShareContent;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.PhotoUtil;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.activity.WebActivityCommon;
import com.v5ent.xiubit.data.recylerapter.ProductListRecycleAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/4
 */

public class SelectedProductedFragment extends FragmentBase {
    private static final String TYPE_PRODUCT_DAY = "product_day";  //日进斗金
    private static final String TYPE_PRODUCT_YEAR = "product_year";  //年年有余
    private String binnerType = TYPE_PRODUCT_DAY;
    private String productType = "1"; //	1-日进斗金 2-年年有余
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.backToTopIBtn)
    ImageButton backToTopIBtn;
    private View mRootView;
    private ProductListRecycleAdapter mAdapter;
    private ImageView mBanner;
    private int pageIndex = 1;
    private HomePagerBanners mBannerData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        productType = getArguments().getString("type");
        if ("1".equals(productType)){
            binnerType = TYPE_PRODUCT_DAY;
        }else {
            binnerType = TYPE_PRODUCT_YEAR;
        }
        mRootView = inflater.inflate(R.layout.fragment_selected_product, null);
        ButterKnife.bind(this, mRootView);
        initView();
        getNetData(false);
        return mRootView;
    }
    
    private void initView() {
        //banner
        mBanner = new ImageView(ctx);
        mBanner.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , (int) getResources().getDimension(R.dimen.w90));
        mBanner.setLayoutParams(layoutParams);
        mBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBannerData == null || TextUtil.isEmpty(mBannerData.getLinkUrl())) return;
                Intent intent = new Intent(ctx, WebActivityCommon.class);
                intent.putExtra("url", mBannerData.getLinkUrl());
                intent.putExtra("title", mBannerData.getShareTitle());
                intent.putExtra("shareContent", new ShareContent(mBannerData.getShareTitle(), mBannerData.getShareDesc(), mBannerData.getLinkUrl(), mBannerData.getShareIcon()));
                ctx.startActivity(intent);
            }
        });
        //recycler
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mAdapter = new ProductListRecycleAdapter(ctx);
        mAdapter.addHeaderView(mBanner);
//        platformAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(mAdapter);
        //refresh
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageIndex = 1;
                getNetData(true);
                mRefreshView.finishRefresh();
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getProductListData();
            }
        },mRecyclerView);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findFirstVisibleItemPosition() > 10) {
                    backToTopIBtn.setVisibility(View.VISIBLE);
                } else {
                    backToTopIBtn.setVisibility(View.GONE);
                }
            }
        });
        backToTopIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });

    }

    private void getNetData(boolean isRefresh) {
        getBannersDatas(isRefresh);
        getProductListData();
    }

    private void getProductListData() {
        new MyNetAsyncTask(ctx, false) {
            ProductDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getSelectedProducts(productType,pageIndex+"");
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData() != null) {
                        List<ProductDetail> datas = response.getData().getDatas();
                        if (datas == null) return;
                        pageIndex = response.getData().getPageIndex();
                        if (pageIndex == 1) {
                            mAdapter.setNewData(datas);
                        } else {
                            mAdapter.addData(datas);
                        }

                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.loadMoreComplete();
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
                        mAdapter.loadMoreFail();
                    }
                }

            }
        }.execute();
    }


    private void getBannersDatas(final boolean isRefresh) {
        new NetCachAsyncTask<HomePagerBannersDatasDataEntity>("selectedProductbanner"+binnerType, 60 * 24 * 1, false, isRefresh) {

            @Override
            protected HomePagerBannersDatasDataEntity getData() throws Exception {
                return MyApp.getInstance()
                        .getHttpService()
                        .homepageAdvs(MyApp.getInstance()
                                .getLoginService().token, binnerType, "1");

            }

            @Override
            protected void onPost(Exception e,
                                  HomePagerBannersDatasDataEntity response,
                                  boolean isDataFromServer) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<HomePagerBanners> homePagerBannerses = response.getData().getDatas();
                        if (homePagerBannerses != null && homePagerBannerses.size() > 0) {
                            mBannerData = homePagerBannerses.get(0);
                            PhotoUtil.loadImageByGlide(getContext(), mBannerData.getImgUrl(),mBanner );
                        }
                    } else {
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    ToastUtil.showCustomToast(MyApp.getInstance()
                            .getString(R.string.pleaseCheckNetwork));
                }
            }
        }.execute();
    }

    

}
