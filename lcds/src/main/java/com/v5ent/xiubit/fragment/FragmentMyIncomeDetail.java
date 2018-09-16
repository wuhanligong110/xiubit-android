package com.v5ent.xiubit.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.view.ListBlankLayout;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.IncomeDetailRecylerAdpter;
import com.v5ent.xiubit.entity.IncomeDetailDatasData;
import com.v5ent.xiubit.network.httpapi.AccountMonthProfixApi;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Fragment-我的收益->收益分类详情
 */
public class FragmentMyIncomeDetail extends FragmentBase {

    private RecyclerView mRecyclerView;
    private ListBlankLayout blankLayout;
    private IncomeDetailRecylerAdpter adapter;
    private String profitType;
    private String monthDate;
    private int pageIndex = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        profitType = getArguments().getString("profixType");
        monthDate = getArguments().getString("monthDate");
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.layout_listblank, null);
        blankLayout = (ListBlankLayout) rootView.findViewById(R.id.list_blank_layout);
        mRecyclerView = (RecyclerView) blankLayout.initContentView(R.layout.list_blank_recylerview_layout);
        initView();
        getdata(false);
        return rootView;
    }


    @SuppressWarnings("unchecked")
    private void initView() {

        adapter = new IncomeDetailRecylerAdpter(profitType);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setNestedScrollingEnabled(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);

    }


    /**
     * 获取我的收益详情
     *
     * @param openDialog openDialog
     */
    private void getdata(boolean openDialog) {

        RetrofitHelper.getInstance().getRetrofit().create(AccountMonthProfixApi.class)
                .getAccountMonthProfix(new ParamesHelp()
                        .put("month", monthDate)
                        .put("profixType", profitType)
                        .put("pageIndex", "" + pageIndex)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseResponseEntity<IncomeDetailDatasData>>() {

                    @Override
                    public void bindViewWithDate(BaseResponseEntity<IncomeDetailDatasData> response) {
                        if (pageIndex == 1) {
                            if (response.getData().getDatas().size() == 0) {
                                if (isAdded())
                                    switch (profitType) {
                                        case "1":
                                            blankLayout.showBlankImageAndText(R.drawable.img_no_data_blank, "暂无待发放记录", true);
                                            break;
                                        case "2":
                                            blankLayout.showBlankImageAndText(R.drawable.img_no_data_blank, "暂无已发放记录", true);
                                            break;
                                        default:
                                            break;
                                    }
                            }else {
                                adapter.setNewData(response.getData().getDatas());
                            }
                        } else adapter.addData(response.getData().getDatas());
                        bindLoadMoreView(response.getData(), adapter);
                        pageIndex ++;
                    }

                });

    }



    public void refresh() {
        pageIndex = 1;
        getdata(false);
    }

    public void loadMore() {
        getdata(false);
    }
}
