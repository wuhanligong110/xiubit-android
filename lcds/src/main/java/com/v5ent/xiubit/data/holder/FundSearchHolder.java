package com.v5ent.xiubit.data.holder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jungly.gridpasswordview.Util;
import com.toobei.common.entity.FundSiftEntiy;
import com.toobei.common.network.NetworkObserver;
import com.toobei.common.network.httpapi.ThirdPartApi;
import com.toobei.common.utils.NetworkUtils;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.data.recylerapter.FundListAdapter;
import com.v5ent.xiubit.util.ParamesHelp;
import com.toobei.common.network.RetrofitHelper;
import com.v5ent.xiubit.view.loadstatue.LoadStatueView;

import org.xsl781.utils.SystemTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/21
 */

public class FundSearchHolder {

    private String periodType = "year1";
    private String periodTypeText = "一年收益率";
    @BindView(R.id.editSearch)
    EditText mEditSearch;
    @BindView(R.id.cancelTv)
    TextView mCancelBtnTv;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Context ctx;
    public View mRootView;
    private int pageIndex = 1;
    private FundListAdapter mAdapter;
    private String searchText;
    private OnCancelListener onCancelListener;
    private int mFundExceptionViewheight;
    private FundExceptionHolder mFundExceptionHolder;
    private LoadStatueView mLoadStatueView;

    public FundSearchHolder(Context context, String periodType, String periodTypeText) {
        this.ctx = context;
        this.periodType = periodType;
        this.periodTypeText = periodTypeText;
        initView();
    }

    private void initView() {
        View contantView = LayoutInflater.from(ctx).inflate(R.layout.page_fund_search, null, true);
        ButterKnife.bind(this, contantView);
        mLoadStatueView = new LoadStatueView(ctx, contantView, new LoadStatueView.ClickCallBack() {
            @Override
            public void onRefreshNetClick() {
                if (NetworkUtils.isConnected()){
                    mLoadStatueView.showContantView();
                    if (!TextUtil.isEmpty(mEditSearch.getText().toString())){
                        getFunListData();
                    }
                }else {
                    mLoadStatueView.showFailView();
                    return;
                }
            }
        });
        mRootView = mLoadStatueView.getRootView();
        mAdapter = new FundListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getFunListData();

            }
        },mRecyclerView);

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pageIndex = 1;
                searchText = editable.toString();
//                if (TextUtil.isEmpty(searchText)) return;
                getFunListData();
            }
        });
      
        //异常视图
        mFundExceptionViewheight = MyApp.displayHeight   //屏幕高度
                - SystemTool.getStatusBarHeight(MyApp.getInstance().getApplicationContext())  //状态栏
                - Util.dp2px(ctx, 43)   //顶部导航栏
                - Util.dp2px(ctx, 57)  //搜索框
                - Util.dp2px(ctx, 49); //底部导航栏
        mFundExceptionHolder = new FundExceptionHolder(ctx, mFundExceptionViewheight);
        mAdapter.setEmptyView(mFundExceptionHolder.mRootView);
        mFundExceptionHolder.mRootView.setVisibility(View.GONE);
    }

    public void getFunListData() {
        if (NetworkUtils.isConnected()){
            mLoadStatueView.showContantView();
        }else {
            mLoadStatueView.showFailView();
            return;
        }
        mFundExceptionHolder.mRootView.setVisibility(View.GONE);
        //产品列表
        RetrofitHelper.getInstance().getRetrofit().create(ThirdPartApi.class)
                .fundList(new ParamesHelp()
                        .put("pageIndex", pageIndex + "")
                        .put("pageSize", "10")
                        .put("sort", "DESC")
                        .put("queryCodeOrName", searchText)
                        .build(true))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<FundSiftEntiy>() {
                    @Override
                    public void onNetSystemException(String errorCode) {
                        super.onNetSystemException(errorCode);
                        if (pageIndex == 1 && errorCode.equals("-999999")){
                            mFundExceptionHolder.mRootView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void bindViewWithDate(FundSiftEntiy response) {
                        if (response.getData().getPageIndex() == 1) {
                            mAdapter.setDateType(periodType, periodTypeText);
                            mAdapter.setNewData(response.getData().getDatas());
                        } else {
                            mAdapter.addData(response.getData().getDatas());
                        }
                        bindLoadMoreView(response.getData(),mAdapter);
                        pageIndex++;

                    }
                });
    }

    public void refreshParams(String periodType, String periodTypeText) {
        pageIndex = 1;
        this.periodType = periodType;
        this.periodTypeText = periodTypeText;
        mEditSearch.setText("");
        mAdapter.setNewData(null);
    }

    @OnClick(R.id.cancelTv)
    public void onViewClicked() {
        if (onCancelListener != null) {
            onCancelListener.onCancel();
        }
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public void setOnCancelListener(OnCancelListener listener) {
        this.onCancelListener = listener;
    }
    
}
