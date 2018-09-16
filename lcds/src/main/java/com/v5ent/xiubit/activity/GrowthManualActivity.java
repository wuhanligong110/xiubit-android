package com.v5ent.xiubit.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recyclerDecoration.GridSpacingItemDecoration;
import com.v5ent.xiubit.data.recylerapter.GrowthManualAdapter;
import com.v5ent.xiubit.data.recylerapter.GrowthTopAdapter;
import com.v5ent.xiubit.entity.GrowthClassifyData;
import com.v5ent.xiubit.entity.GrowthHandbookEntity;
import com.v5ent.xiubit.entity.PersonalCustomizatioEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/7/31
 */

public class GrowthManualActivity extends MyNetworkBaseActivity<GrowthHandbookEntity> implements OnRefreshListener {
    @BindView(R.id.refreshView)
    SmartRefreshLayout mRefreshView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private GrowthManualAdapter mListAdapter;
    private RecyclerView mTopImgeList;
    private GrowthTopAdapter mTopAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        headerLayout.showLeftBackButton();
        headerLayout.showTitle("成长手册");
        //head
        View topHeadView = LayoutInflater.from(ctx).inflate(R.layout.growth_manual_top_layout, null);
        mTopImgeList = (RecyclerView) topHeadView.findViewById(R.id.recyclerView);
        mTopImgeList.setLayoutManager(new GridLayoutManager(ctx, 2));
        mTopImgeList.addItemDecoration(new GridSpacingItemDecoration(2, (int) getResources().getDimension(R.dimen.w18),0,false));
        mTopAdapter = new GrowthTopAdapter();
        mTopImgeList.setAdapter(mTopAdapter);
        //list
        mListAdapter = new GrowthManualAdapter();
        mListAdapter.addHeaderView(topHeadView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(mListAdapter);
        //refresh
        mRefreshView.setOnRefreshListener(this);
    }

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_growth_manual;
    }

    @Override
    protected GrowthHandbookEntity onLoadDataInBack() throws Exception {
        getListData();
        return MyApp.getInstance().getHttpService().getGrowthHandbook();
    }

    @Override
    protected void onRefreshDataView(GrowthHandbookEntity data) {
        mTopAdapter.setNewData(data.getData().getDatas());
    }

    private void getListData() {

        new MyNetAsyncTask(ctx, false) {
            PersonalCustomizatioEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance()
                        .getHttpService()
                        .getPersonalCustomizatio();
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0") && response.getData() != null) {
                        List<GrowthClassifyData> datas = response.getData().getDatas();
                        if (datas == null) return;
                        mListAdapter.setNewData(datas);

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
    public void onRefresh(RefreshLayout refreshlayout) {
        loadData(false);
        mRefreshView.finishRefresh();
    }
}
