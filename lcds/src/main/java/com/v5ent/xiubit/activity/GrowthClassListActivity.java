package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.utils.PhotoUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recylerapter.GrowthManualAdapter;
import com.v5ent.xiubit.entity.GrowthClassifyData;
import com.v5ent.xiubit.entity.GrowthClassifyListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/1
 */

public class GrowthClassListActivity extends MyNetworkBaseActivity<GrowthClassifyListEntity> {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    private int pageIndex = 1;
    private String typeCode;
    private GrowthManualAdapter mAdapter;
    private ImageView mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        typeCode = getIntent().getStringExtra("typeCode");
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerLayout.showTitle("成长手册");
        headerLayout.showLeftBackButton();
        //banner
        mBanner = new ImageView(ctx);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.w90));
        mBanner.setLayoutParams(lp);
        mBanner.setScaleType(ImageView.ScaleType.FIT_XY);
        //recycler
        mAdapter = new GrowthManualAdapter();
        mAdapter.addHeaderView(mBanner);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(mAdapter);
        //refreshView
        mRefreshView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(false);
                mRefreshView.finishRefresh();
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadData(false);
            }
        },mRecyclerView);
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_growth_class_list;
    }

    @Override
    protected GrowthClassifyListEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getGrowthClassifyList(pageIndex + "", typeCode);
    }

    @Override
    protected void onRefreshDataView(GrowthClassifyListEntity response) {
        try {
            String bannerImg = response.getData().getBannerImg();
            PhotoUtil.loadImageByGlide(appContext, bannerImg, mBanner);
            if (response.getData() == null) return;
            List<GrowthClassifyData> datas = response.getData().getGrowthHandbookList().getDatas();
            
            int pageIndex = response.getData().getGrowthHandbookList().getPageIndex();
            int pageCount = response.getData().getGrowthHandbookList().getPageCount();
            
            if (pageIndex == 1) {
                mAdapter.setNewData(datas);
            }else {
                mAdapter.addData(datas);
            }
            
            if (pageCount <= pageIndex || pageCount <= 1) {
                mAdapter.loadMoreEnd(mAdapter.getData().size() < 5);
            } else {
                mAdapter.loadMoreComplete();
            }
            this.pageIndex++;
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
