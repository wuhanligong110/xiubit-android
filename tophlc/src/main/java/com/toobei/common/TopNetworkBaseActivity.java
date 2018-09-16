package com.toobei.common;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.NetCachAsyncTask;
import com.toobei.common.utils.ToastUtil;

@SuppressWarnings("rawtypes")
public abstract class TopNetworkBaseActivity<T extends BaseResponseEntity> extends
        TopTitleBaseActivity {
    
    private View mLoadProgressView;
    private View mLoadFailView;
    private ViewStub mProgressStub;
    private ViewStub mFailStub;
    private ViewStub mContentStub;
    private View mContentView;
    private boolean isLoading = false;
    public boolean isCachData = false;//是否缓存
    public boolean isRefreshData = false;//是否刷新缓存
    protected boolean loadDataInStartEnable = true;

    private String key;
    private long cacheTime;

    @Override
    protected void onContentCreate(Bundle savedInstanceState, View content) {
        mContentStub = (ViewStub) content.findViewById(R.id.stub_content);
        mProgressStub = (ViewStub) content.findViewById(R.id.stub_progress);
        mFailStub = (ViewStub) content.findViewById(R.id.stub_fail);

        int rid = onGetContentViewLayout();
        if (rid <= 0) {
            throw new RuntimeException("network base activity has none valid data content layout");
        }

        mContentStub.setLayoutResource(rid);
        mContentView = mContentStub.inflate();
        onInitParamBeforeLoadData();
        if (loadDataInStartEnable) {
            loadData(true);
        }else {
            showContent();
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_container_network_base;
    }

    public View getDataContentView() {
        return mContentView;
    }

    protected void showContent() {
        if (isFinishing())
            return;
        if (mLoadProgressView != null) {
            mLoadProgressView.setVisibility(View.GONE);
        }
        if (mLoadFailView != null) {
            mLoadFailView.setVisibility(View.GONE);
        }
        mContentView.setVisibility(View.VISIBLE);
    }

    protected void showProgress() {
        if (isFinishing())
            return;
        if (mContentView != null) {
            mContentView.setVisibility(View.GONE);
        }
        if (mLoadFailView != null) {
            mLoadFailView.setVisibility(View.GONE);
        }
        if (mLoadProgressView == null) {
            View view = mProgressStub.inflate();
            mLoadProgressView = view;
        }
        mLoadProgressView.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (!isLoading) {
                loadData(true);
            }
            //loadRefresh();
        }
    };

    protected void showFail() {
        if (isFinishing())
            return;
        if (mLoadProgressView != null) {
            mLoadProgressView.setVisibility(View.GONE);
        }
        mContentView.setVisibility(View.GONE);
        if (mLoadFailView == null) {
            View view = mFailStub.inflate();
            view.findViewById(R.id.refreshNetTv).setOnClickListener(mClickListener);
            mLoadFailView = view;
        }
        mLoadFailView.setVisibility(View.VISIBLE);
    }

    protected void loadData(final boolean isShowProgress) {
        if (isShowProgress) {
            showProgress();
        }
        isLoading = true;
        if (isCachData) {
            //从缓存
            if (isRefreshData) {
                getNetCachAsyncTask(isShowProgress, true);
            } else {
                getNetCachAsyncTask(isShowProgress, false);
            }
        } else {
            //从网络
            getNetAsyncTask(isShowProgress);

        }

    }

    protected void loadDataFromNet(final boolean isShowProgress) {
        if (isShowProgress) {
            showProgress();
        }
        getNetAsyncTask(isShowProgress);

    }

    /**
     * 功能：     加载数据使用初始化缓存参数
     *
     * @param isShowProgress
     */
    protected void loadDataForceRefresh(final boolean isShowProgress) {
        loadDataForceRefresh(isShowProgress, key, cacheTime);

    }

    /**
     * 功能：  加载数据不使用初始化缓存参数
     *
     * @param isShowProgress
     * @param key            缓存的key
     * @param cacheTime      缓存时间:min
     */
    protected void loadDataForceRefresh(final boolean isShowProgress, String key, long cacheTime) {
        if (isShowProgress) {
            showProgress();
        }
        isLoading = true;
        if (isCachData) {
            getNetCachAsyncTask(isShowProgress, true, key, cacheTime);
        } else {
            getNetAsyncTask(isShowProgress);
        }

    }

    private void getNetAsyncTask(final boolean isShowProgress) {
        new MyNetAsyncTask(ctx, false) {
            T response;

            @Override
            protected void doInBack() throws Exception {
                response = onLoadDataInBack();
            }

            @Override
            protected void onPost(Exception e) {
                onRefreshDataViewStart();
                if (e == null && response != null) {
                    if (response.getCode().equals("0")) {
                        onRefreshDataView(response);
                        showContent();
                    }else {
                        onResponseMsgError();
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        if (isShowProgress) {
                            showFail();
                        }
                    }
                } else {
                    ToastUtil.showCustomToast(getString(R.string.pleaseCheckNetwork));
                    if (isShowProgress) {
                        showFail();
                    }
                }
                isLoading = false;
                onRefreshDataViewCompleted();
            }
        }.execute();
    }


    /**
     * 功能：    不管缓存有效期，均更新缓存和缓存时间
     *
     * @param isShowProgress
     * @param forceCache     强制刷新
     */
    private void getNetCachAsyncTask(final boolean isShowProgress, boolean forceCache) {
        new NetCachAsyncTask<T>(TopApp.getInstance().getLoginService().curPhone, key, cacheTime,
                false, forceCache) {

            @Override
            protected T getData() throws Exception {
                return onLoadDataInBack();
            }

            @Override
            protected void onPost(Exception e, T response, boolean isDataFromServer) {
                onRefreshDataViewStart();
                if (e == null && response != null) {
                    if (response.getCodeNoCheck().equals("0")) {
                        onRefreshDataView(response);
                        showContent();
                    } else {
                        onResponseMsgError();
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        if (isShowProgress) {
                            showFail();
                        }
                    }
                } else {
                    if (isShowProgress) {
                        showFail();
                    }
                }
                isLoading = false;
                onRefreshDataViewCompleted();
            }

        }.execute();
    }

    /**
     * 功能：    不管缓存有效期，均更新缓存和缓存时间
     *
     * @param isShowProgress
     * @param forceCache     强制刷新
     * @param key
     * @param cacheTime
     */
    private void getNetCachAsyncTask(final boolean isShowProgress, boolean forceCache, String key,
                                     long cacheTime) {

        new NetCachAsyncTask<T>(TopApp.getInstance().getLoginService().curPhone, key, cacheTime,
                false, forceCache) {

            @Override
            protected T getData() throws Exception {
                return onLoadDataInBack();
            }

            @Override
            protected void onPost(Exception e, T response, boolean isDataFromServer) {
                onRefreshDataViewStart();
                if (e == null && response != null) {
                    if (response.getCodeNoCheck().equals("0")) {
                        onRefreshDataView(response);
                        showContent();
                    }else {
                        onResponseMsgError();
                        ToastUtil.showCustomToast(response.getErrorsMsgStr());
                        if (isShowProgress) {
                            showFail();
                        }
                    }
                } else {
                    if (isShowProgress) {
                        showFail();
                    }
                }
                isLoading = false;
                onRefreshDataViewCompleted();
            }

        }.execute();
    }

    //暂时未完成
    private boolean loadRefresh() {
        boolean cached = hasCache();
        if (!cached) {
            showProgress();
            if (mContentView != null) {
                mContentView.setVisibility(View.GONE);
            }
        }
        loadData(false);
        return true;
    }

    private boolean hasCache() {
        return false;
    }

    /**
     * 功能：设置主视图布局
     *
     * @return
     */
    protected abstract int onGetContentViewLayout();

    protected void onInitParamBeforeLoadData() {

    }

    /**
     * 功能：后台加载数据
     *
     * @return
     * @throws Exception
     */
    protected abstract T onLoadDataInBack() throws Exception;

    /**
     * 功能：刷新界面数据
     *
     * @param data
     */
    protected abstract void onRefreshDataView(T data);

    /**
     * 功能：响应成功，消息体带错误信息
     */
    protected void onResponseMsgError() {

    }

    /**
     * 功能：界面刷新开始
     */
    protected void onRefreshDataViewStart() {

    }

    /**
     * 功能：界面刷新完成
     */
    protected void onRefreshDataViewCompleted() {

    }

    /**
     * 功能：子类在onInitParamBeforeLoadData里初始化缓存参数
     *
     * @param key
     * @param cacheTime
     */
    protected void initCachParams(String key, long cacheTime) {
        isCachData = true;
        this.key = key;
        this.cacheTime = cacheTime;
    }


   
}
