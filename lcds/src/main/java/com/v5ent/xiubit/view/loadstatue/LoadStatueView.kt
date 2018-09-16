package com.v5ent.xiubit.view.loadstatue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import com.v5ent.xiubit.R

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/10/10
 */
class LoadStatueView(var ctx: Context,var contentView: View,var callback : ClickCallBack) {
    var rootView: FrameLayout? = null
    var mProgressStub: ViewStub? = null
    var mNoNetStub: ViewStub? = null
    var mNoNetView: View? = null

    init {
        rootView = LayoutInflater.from(ctx).inflate(R.layout.network_base_view, null) as FrameLayout
        mProgressStub = rootView?.findViewById<ViewStub>(R.id.stub_progress)
        mNoNetStub = rootView?.findViewById<ViewStub>(R.id.stub_no_net)
        rootView?.addView(contentView)
    }
    

    fun showFailView() {
        contentView?.visibility = View.GONE
        if (mNoNetView == null) {
            mNoNetView = mNoNetStub?.inflate()
        }else{
            mNoNetStub?.visibility = View.VISIBLE
        }
        mNoNetView?.findViewById<View>(R.id.refreshNetTv)?.setOnClickListener(View.OnClickListener {
            callback.onRefreshNetClick()
        })
        
    }
    
    fun showContantView(){
        mNoNetStub?.visibility = View.GONE
        contentView?.visibility = View.VISIBLE
    }
    
    interface ClickCallBack{
        fun onRefreshNetClick()
    }
}