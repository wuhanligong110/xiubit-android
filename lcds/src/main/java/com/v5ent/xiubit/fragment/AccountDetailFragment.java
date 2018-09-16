package com.v5ent.xiubit.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.ListBlankLayout;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recylerapter.AccountDetailRecycleAdapter;
import com.v5ent.xiubit.data.Listener.onLoadMoreRecycleScrollListener;
import com.v5ent.xiubit.entity.IncomeAndOutDetail;
import com.v5ent.xiubit.entity.IncomeAndOutDetailDatasDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee-pc on 2017/2/13.
 * Fragment-收入明细
 */
public class AccountDetailFragment extends FragmentBase{
    private String dataType; //收支类型(0=全部1=收入|2=支出)
    private ListBlankLayout blankLayout;
    private RecyclerView lv;
    private int pageIndex = 1;
    private int pageSize = 10;
    private List<IncomeAndOutDetail> incomeAndOutDetailList = new ArrayList<>();
    private AccountDetailRecycleAdapter adapter;
    private boolean loadingMoreEnabled;   //是否允许加载更多

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        this.dataType = getArguments().getString("DateType");
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.layout_listblank, null);
        blankLayout = (ListBlankLayout) rootView.findViewById(R.id.list_blank_layout);
        lv = (RecyclerView) blankLayout.initContentView(R.layout.list_blank_recylerview_layout);
        initView();
        getNetdata(false);
        return rootView;
    }

    /**
     * 获取数据
     *
     * @param b
     */
    private void getNetdata(boolean b) {
        new MyNetAsyncTask(ctx, b) {

            private IncomeAndOutDetailDatasDataEntity response;

            @Override
            protected void doInBack() throws Exception {
                response = MyApp.getInstance().getHttpService().queryIncomeAndOutDetail(MyApp.getInstance().getLoginService().token, pageIndex + "", pageSize + "", dataType);
            }

            @Override
            protected void onPost(Exception e) {
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        List<IncomeAndOutDetail> datas = response.getData().getDatas();
                        pageIndex = response.getData().getPageIndex();
                        if (pageIndex == 1) {
                            incomeAndOutDetailList.clear();
                            if (datas.size() == 0) {
                                if (isAdded()) {
                                    switch (dataType) {
                                        case "0":
                                            blankLayout.showBlankImageAndText(R.drawable.img_no_data_blank, getResources().getString(R.string.blank_list_text_no_data_account_detail));
                                            break;
                                        case "1":
                                            blankLayout.showBlankImageAndText(R.drawable.img_no_data_blank, getResources().getString(R.string.blank_list_text_no_data_account_detail_income));
                                            break;
                                        case "2":
                                            blankLayout.showBlankImageAndText(R.drawable.img_no_data_blank, getResources().getString(R.string.blank_list_text_no_data_account_detail_out));
                                            break;
                                        default:
                                            break;
                                    }

                                }
                                return;
                            }
                        }
                        incomeAndOutDetailList.addAll(datas);
                        adapter.notifyDataSetChanged();
                        if (response.getData().getPageCount() <= response.getData().getPageIndex() || response.getData().getPageCount() <= 1) {
                            setLoadingMoreEnabled(false);
                        } else {
                            setLoadingMoreEnabled(true);
                        }
                        pageIndex++;
                    } else {
                        if (isAdded()) ToastUtil.showCustomToast(response.getErrorsMsgStr());
                    }
                } else {
                    if (isAdded())
                        ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                }
            }

        }.execute();
    }

    private void setLoadingMoreEnabled(boolean b) {
        loadingMoreEnabled = b;
    }


    private void initView() {
        adapter = new AccountDetailRecycleAdapter(ctx, incomeAndOutDetailList, dataType);
        lv.setLayoutManager(new LinearLayoutManager(ctx));
        lv.setAdapter(adapter);
        lv.setNestedScrollingEnabled(true);
        lv.addOnScrollListener(new onLoadMoreRecycleScrollListener() {
            @Override
            public void onloadMore() {
                if (loadingMoreEnabled){
                    loadMore();
                }else {
                    ToastUtil.showCustomToast("没有更多数据");
                }
            }
        });

    }


    public void refresh() {
        pageIndex = 1;
        getNetdata(false);
    }

    public void loadMore() {
        getNetdata(false);
    }
}
