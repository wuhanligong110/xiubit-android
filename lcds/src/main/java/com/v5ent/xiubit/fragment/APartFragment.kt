package com.v5ent.xiubit.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import com.alibaba.fastjson.JSON
import com.toobei.common.TopApp
import com.toobei.common.TopBaseActivity
import com.toobei.common.service.TopAppObject
import com.toobei.common.utils.NetworkUtils
import com.toobei.common.utils.ToastUtil
import com.toobei.common.view.OnMyWebViewListener
import com.v5ent.xiubit.BuildConfig
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.*
import com.v5ent.xiubit.service.JumpInsuranceService
import com.v5ent.xiubit.util.DynamicSkipManage
import kotlinx.android.synthetic.main.apart_fragment.*
import org.xsl781.utils.Logger

/**
 * Created by yangLin on 2018/5/8.
 */
class APartFragment : FragmentBase() {
    private var url_pre = "http://10.16.0.128:12003/"
    private var url_rel = "http://declare.bethye.top/"
    private var url = url_rel
    private var hasInit = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!BuildConfig.FLAVOR.equals("rel")) {
            url = url_pre
        }
        return inflater?.inflate(R.layout.apart_fragment, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()

    }

    override fun onResume() {
        super.onResume()

        if (hasInit) {
            Logger.d("aPartReload")
            webView.reload()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (hasInit && isVisibleToUser) {
            webView.reload()
            Logger.d("aPartReload")
        }
    }


    private fun showLoadingV() {
        loadingV.visibility = View.VISIBLE
        noNetView.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }

    private fun showNoNetView() {
        loadingV.visibility = View.GONE
        webView.visibility = View.GONE
        noNetView.visibility = View.VISIBLE
        noNetView.findViewById<View>(R.id.refreshNetTv).setOnClickListener {
            webView.reload()
        }
    }

    private fun showContant() {
        loadingV.visibility = View.GONE
        noNetView.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }


    override fun refreshAfterLogin() {
        super.refreshAfterLogin()
        webView.reload()
    }

    private fun initWebView() {
        webView.setRedirectUsable(false)  //跳转全部新起页面
        if (url != null && !url.contains("http")) {  //如果只是一个没有域名的url则拼接域名
            url = TopApp.getInstance().httpService.baseH5urlByAppkind + url
        }
        webView.setOnMyWebViewListener(object : OnMyWebViewListener {

            override fun onReceivedTitle(title: String?) {}

            override fun onUrlRedirectCallBack(isRedirectUsable: Boolean, redirectUrl: String?) {
                try {
                    var urlEquals = false
                    if (url != null) {
                        urlEquals = url.replace("https", "http") == redirectUrl?.replace("https", "http") //防止https和http不同导致新打开一个activity
                    }
                    if (!isRedirectUsable && !urlEquals) {
                        WebActivityCommon.showThisActivity(ctx as TopBaseActivity?, redirectUrl, null)
                    }
                } catch (e: Exception) {
                    Logger.e(e.toString())
                }
            }

            override fun onUrlLoading(isRedirectUsable: Boolean, url: String?) {}

            override fun onPageStart(url: String?) {
                showLoadingV()
            }

            override fun onPageFinished(url: String?) {
                if (!NetworkUtils.isConnected()) {
                    showNoNetView()
                } else {
                    showContant()
                }
            }

            override fun onLoadError(url: String?) {
                showNoNetView()
            }

        })
        webView.setAppObject(MyTopAppObject())
        webView.loadUrl(url)
        hasInit = true
    }


    inner class MyTopAppObject : TopAppObject() {


        /**
         * token失效
         */
        @JavascriptInterface
        fun tokenExpired() {
            TopApp.loginAndStay = true
            TopApp.getInstance().logOutEndNoSikp()
            ctx.startActivity(Intent(ctx, LoginActivity::class.java))
        }


        @JavascriptInterface
        override fun showAppPrompt(titleJson: String) {
            ctx.runOnUiThread(Runnable {
                println("showAppPrompt========>" + titleJson)

                var title: String? = null
                try {
                    title = JSON.parseObject(titleJson)["title"] as String
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (title != null && title.length > 0) {
                    ToastUtil.showCustomToast(title)
                }
            })
        }

        /**
         * V2.0.3
         * 新手攻略->邀请用户
         */
        @JavascriptInterface
        override fun invitedCustomer() {
            skipToinvitedCustomer()

        }


        //        新手攻略->邀请理财师
        @JavascriptInterface
        fun invitedOperation() {
            skipToInviteCfpQr()
        }


        /**
         * 绑卡认证接口
         */
        @JavascriptInterface
        override fun bindCardAuthenticate() {
            Logger.e("bindCardAuthenticate")
            if (!TopApp.getInstance().loginService.isCachPhoneExist) {
                skipToLoginActivity()
            } else {
                skipToCardAdd()
            }
        }


        @JavascriptInterface
        fun jumpCfgLevelCalculate() {  //跳转职级收入计算器接口
            skipToCfgLevelCalculate()
        }

        /**
         * 跳转机构平台
         *
         * @param orgNoJson {"orgNo":"平台id"}
         */
        @JavascriptInterface
        override fun getAppPlatfromDetail(orgNoJson: String) {
            skipToPlatfromDetail(orgNoJson)

        }


        @JavascriptInterface
        fun jumpToNativePage(json: String) {
            skipToActivity(json)
        }

        @JavascriptInterface
        override fun jumpThirdInsurancePage(json: String) {
            val caseCode = JSON.parseObject(json)["caseCode"] as String
            val tag = "" + JSON.parseObject(json)["tag"]
            jumpToThirdInsurancePage(caseCode, tag)

        }

    }

    /**
     * 跳转邀请客户页面
     */
    protected fun skipToinvitedCustomer() {
        ctx.startActivity(Intent(ctx, InviteCustomerQr::class.java))
    }

    /**
     * 跳转推荐理财师页面
     */
    protected fun skipToInviteCfpQr() {
        ctx.startActivity(Intent(ctx, InviteCfpQr::class.java))
    }

    protected fun skipToLoginActivity() {
        ctx.startActivity(Intent(ctx, LoginActivity::class.java))
    }

    protected fun skipToCardAdd() {
        ctx.startActivity(Intent(ctx, CardManagerAdd::class.java))
    }

    protected fun skipToCfgLevelCalculate() {
        ctx.startActivity(Intent(ctx, CfgLevelCalculateActivity::class.java))
    }

    protected fun skipToPlatfromDetail(json: String) {
        val orgNo = JSON.parseObject(json)["orgNo"] as String
        val intent = Intent(ctx, OrgInfoDetailActivity::class.java)
        intent.putExtra("orgNumber", orgNo)
        ctx.startActivity(intent)
    }

    protected fun skipToActivity(json: String) {
        val str = String(Base64.decode(json.toByteArray(), Base64.DEFAULT))
        DynamicSkipManage.skipActivity(ctx as TopBaseActivity?, str)
    }

    protected fun jumpToThirdInsurancePage(caseCode: String, tag: String) {
        JumpInsuranceService(ctx, caseCode, tag).run()
    }


}