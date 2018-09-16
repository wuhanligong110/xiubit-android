package com.toobei.common

import android.os.Bundle
import android.view.View
import com.toobei.common.view.HeaderLayout
import kotlinx.android.synthetic.main.activity_container_network_base.*

abstract class TopNetBaseActivity : TopBaseActivity() {
    private var mContentView : View? = null
    private var mLoadProgressView: View? = null
    private var mLoadFailView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_net)
        
        headerLayout = findViewById<HeaderLayout>(R.id.headerLayout)

        headerLayout.showLeftBackButton()
        stub_content.layoutResource = getContentLayout()
        mContentView = stub_content.inflate()
    }

    override fun initStatusBarStyle() {
        super.initStatusBarStyle()
        headerLayout.setHeadViewCoverStateBar()
    }
    
    fun showLoadContent(){
        if (mContentView == null) mContentView = stub_content.inflate()
        mContentView?.visibility = View.VISIBLE
        mLoadProgressView?.visibility = View.GONE
        mLoadFailView?.visibility = View.GONE
    }
    
    fun showLoadProgress(isShowContant : Boolean = false){
        if (mLoadProgressView == null) mLoadProgressView = stub_progress.inflate()
        mContentView?.visibility = if (isShowContant) View.VISIBLE else View.GONE
        mLoadProgressView?.visibility = View.VISIBLE
        mLoadFailView?.visibility = View.GONE
    }


    fun showLoadFail(){
        if (mLoadFailView == null) mLoadFailView = stub_fail.inflate() 
        mContentView?.visibility = View.GONE
        mLoadProgressView?.visibility = View.GONE
        mLoadFailView?.visibility = View.VISIBLE
        mLoadFailView?.findViewById<View>(R.id.refreshNetTv)?.setOnClickListener { onLoadFailRefresh() }
    }

    /**
     * 无网络状态，点击刷新
     */
    open fun onLoadFailRefresh() {}

    abstract protected fun getContentLayout(): Int
}
