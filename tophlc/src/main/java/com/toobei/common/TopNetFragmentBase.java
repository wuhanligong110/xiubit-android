package com.toobei.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;

import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.utils.MyNetAsyncTask;
import com.toobei.common.utils.ToastUtil;

import org.xsl781.utils.Utils;

import butterknife.ButterKnife;

/**
 * 公司: tophlc
 * 类说明:  带网络请求的Fragment 的基类
 *
 * @author qingyechen
 * @time 2017/1/6 0006 下午 2:59
 */
public abstract class TopNetFragmentBase<T extends BaseResponseEntity> extends Fragment implements OnTouchListener {
    public Activity activity;
    private View mLoadProgressView;
    private ViewStub mContentStub;
    private ViewStub mProgressStub;
    private ViewStub mFailStub;
    public View mLoadSucessView;
    private boolean isLoading;
    private View mLoadFailView;
    public View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_container_network_base, container, false);

        mContentStub = (ViewStub) rootView.findViewById(R.id.stub_content);
        mProgressStub = (ViewStub) rootView.findViewById(R.id.stub_progress);
        mFailStub = (ViewStub) rootView.findViewById(R.id.stub_fail);

        int rid = onGetrootViewViewLayout();
        if (rid <= 0) {
            throw new RuntimeException("Fragment 填充布局错误");
        }

        mContentStub.setLayoutResource(rid);
        mLoadSucessView = mContentStub.inflate();
        ButterKnife.bind(this, mLoadSucessView);
        initSucessViewAndData(mLoadSucessView);
        loadData(true);
        return rootView;
    }

    protected abstract void initSucessViewAndData(View mLoadSucessView);

    protected void loadData(final boolean isShowProgress) {
        if (isShowProgress) {
            showProgress();
        }
        isLoading = true;
        getNetAsyncTask(isShowProgress);

    }

    /**
     * 加载网络请求成功视图
     *
     * @return 成功视图 id
     */
    protected abstract int onGetrootViewViewLayout();

    /**
     * 显示加载成功视图
     */
    protected void showSucessView() {

        if (mLoadProgressView != null) {
            mLoadProgressView.setVisibility(View.GONE);
            setbackageAlpha(1f);
        }
        if (mLoadFailView != null) {
            mLoadFailView.setVisibility(View.GONE);
        }
        mLoadSucessView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示加载过程中视图
     */
    protected void showProgress() {

        if (mLoadFailView != null) {
            mLoadFailView.setVisibility(View.GONE);
        }
        if (mLoadProgressView == null) {
            mLoadProgressView = mProgressStub.inflate();
        }
        mLoadProgressView.setVisibility(View.VISIBLE);
        setbackageAlpha(0.8f);
    }

    /**
     * 显示加载失败视图
     */
    protected void showFail() {

        if (mLoadProgressView != null) {
            mLoadProgressView.setVisibility(View.GONE);
            setbackageAlpha(1f);
        }
        mLoadSucessView.setVisibility(View.GONE);
        if (mLoadFailView == null) {
            View view = mFailStub.inflate();
            view.findViewById(R.id.refreshNetTv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isLoading) {
                        loadData(true);
                    }
                }
            });
            mLoadFailView = view;
        }
        mLoadFailView.setVisibility(View.VISIBLE);
    }


    /**
     * 网络请求
     *
     * @param isShowProgress 进度
     */
    private void getNetAsyncTask(final boolean isShowProgress) {
        new MyNetAsyncTask(activity, false) {
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
                        showSucessView();
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



    /**
     * 网络请求的实现
     *
     * @return t
     */
    protected abstract T onLoadDataInBack() throws Exception;

    /**
     * 网络请求错误
     */
    private void onResponseMsgError() {

    }

    /**
     * 网络请求成功后刷新数据视图
     *
     * @param response response
     */
    protected abstract void onRefreshDataView(T response);

    /**
     * 开始刷新视图
     */
    protected void onRefreshDataViewStart() {

    }

    /**
     * 加载视图完成
     */
    protected void onRefreshDataViewCompleted() {

    }


    @Override
    public void onResume() {
        Utils.hideSoftInputView(getActivity());
        super.onResume();
    }

    // onTouch事件 将上层的触摸事件拦截
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // 拦截触摸事件，防止泄露下去
        view.setOnTouchListener(this);
    }

    private void setbackageAlpha(float bgAlpha) {

        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.alpha = bgAlpha;
        activity.getWindow().setAttributes(attributes);

    }
}
