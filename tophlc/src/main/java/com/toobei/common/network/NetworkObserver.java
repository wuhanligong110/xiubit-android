package com.toobei.common.network;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.entity.BaseResponseEntity;
import com.toobei.common.entity.PageListBase;
import com.toobei.common.utils.ToastUtil;

import org.xsl781.utils.Logger;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/17
 */

public abstract class NetworkObserver<T extends BaseResponseEntity> implements Observer<T> {
    public boolean noMsgToast;

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T response) {

        try {
            if (response.getCode().equals("0") && response.getData() != null) {
//                Logger.json("net-json", new Gson().toJson(response));
                bindViewWithDate(response);
                
            } else if (response.getCode().equals("-999999")) {
                //奕丰基金系统异常 
                onNetSystemException("-999999");
            } else {
                if (!noMsgToast) {
                    ToastUtil.showCustomToast(response.getErrorsMsgStr());
                }
                String errorsCodeStr = response.getErrorsCodeStr();
                onNetSystemException(errorsCodeStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public abstract void bindViewWithDate(@NonNull T response);


    @Override
    public void onError(@NonNull Throwable e) {
        //无网络或者网络错误也会走这里
        try {
            Logger.e("okhttp" + e.toString());
            ToastUtil.showCustomToast(TopApp.getInstance().getString(R.string.pleaseCheckNetwork));
            onFinish();
            onNetSystemException(e.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //成功时才会调用
    @Override
    public void onComplete() {
//        Logger.e("onComplete");
        onFinish();
    }

    /**
     * 系统或者网络出错
     */
    protected void onNetSystemException(String error) {

    }

    /**
     * 网络请求结束，不论失败或者成功
     */
    protected void onFinish(){
        
    }

    protected void bindLoadMoreView(PageListBase pageListBase, BaseQuickAdapter adapter){
        if (pageListBase == null || adapter == null) return;
        if (pageListBase.getPageCount() <= pageListBase.getPageIndex() || pageListBase.getPageCount() <= 1) {

            adapter.loadMoreEnd(adapter.getData().size() < 5? true:false);
        } else {
            adapter.loadMoreComplete();
        }
    }

    protected void bindLoadMoreView(PageListBase pageListBase, BaseQuickAdapter adapter,boolean isHideLoadMoreView){
        if (pageListBase == null || adapter == null) return;
        if (pageListBase.getPageCount() <= pageListBase.getPageIndex() || pageListBase.getPageCount() <= 1) {

            adapter.loadMoreEnd(adapter.getData().size() < 5? true:isHideLoadMoreView);
        } else {
            adapter.loadMoreComplete();
        }
    }

}
