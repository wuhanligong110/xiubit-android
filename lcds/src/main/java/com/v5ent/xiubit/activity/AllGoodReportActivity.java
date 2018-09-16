package com.v5ent.xiubit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.Util;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recyclerDecoration.GridSpacingItemDecoration;
import com.v5ent.xiubit.data.recylerapter.GoodReportAdapter;
import com.v5ent.xiubit.entity.GoodTransData;
import com.v5ent.xiubit.entity.OldGoodTransEntity;
import com.v5ent.xiubit.view.popupwindow.GoodReportSortPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/27
 */

public class AllGoodReportActivity extends MyNetworkBaseActivity<OldGoodTransEntity> implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.createGoodReportTv)
    TextView createGoodReportTv;
    private int pageIndex = 1;
    private int sort = 2;  //1.按金额 2.按时间
    private GoodReportAdapter mAdapter;
    private GoodReportSortPopupWindow mPopupWindow;
    private TextView mSortTv;
    private View mFootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerLayout.showTitle("理财喜报");
        headerLayout.showLeftBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showActivity(ctx,GoodReportActivity.class);
            }
        });
        mSortTv = headerLayout.showRightTextButton("排序", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow == null) {
                    mPopupWindow = new GoodReportSortPopupWindow(ctx);
                }
                if (mSortTv != null) {
                    mPopupWindow.showAsDropDown(mSortTv,Util.dip2px(ctx,-50),0);
                }
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(ctx, 2));
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2,Util.dip2px(ctx, 15),true));
        mAdapter = new GoodReportAdapter();
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mFootView = LayoutInflater.from(ctx).inflate(R.layout.foot_item_good_report, null, true);
        mFootView.setVisibility(View.GONE);
        mAdapter.addFooterView(mFootView);
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.all_good_report_activity;
    }

    @Override
    protected OldGoodTransEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getOldGoodTrans(pageIndex + "", sort + "");
    }

    @Override
    protected void onRefreshDataView(OldGoodTransEntity response) {
        List<GoodTransData> datas = response.getData().getDatas();
        if (datas == null || datas.size() == 0) return;
        if (response.getData().getPageIndex() == 1) {
            mAdapter.setNewData(datas);
        } else {
            mAdapter.addData(datas);
        }

        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
            mAdapter.loadMoreEnd();
            mFootView.setVisibility(View.VISIBLE);
        } else {
            mAdapter.loadMoreComplete();
        }
        pageIndex++;

    }

    @OnClick({R.id.createGoodReportTv})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.createGoodReportTv:
                GoodTransData selectBean = mAdapter.getSelectBean();
                if (selectBean == null){
                    ToastUtil.showCustomToast("请选择一张喜报");
                    return;
                }
                EventBus.getDefault().post(selectBean);
                showActivity(ctx, GoodReportActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshSort(GoodReportSortPopupWindow.GoodReportSortEvent event) {
        sort = event.getSortType();
        pageIndex = 1;
        mAdapter.currentSelectId = null;
        loadDataFromNet(false);
    }


    @Override
    public void onLoadMoreRequested() {
        loadDataFromNet(false);
    }

}
