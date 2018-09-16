package com.v5ent.xiubit.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.igexin.sdk.GTServiceManager.context
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.OrgUrlSkipParameter
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.ThirdPartApi
import com.toobei.common.utils.PhotoUtil
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.OrgThirdDataDetail
import com.v5ent.xiubit.network.httpapi.OrgInfoApi
import com.v5ent.xiubit.service.JumpThirdPartyService
import com.v5ent.xiubit.service.JumpThirdPartyService.JUMP_TYPE_USER_CENTER
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_org_user.*
import org.xsl781.utils.Logger
import java.net.URLDecoder

/**
 * Created by yangLin on 2018/4/11.
 */
class OrgUserCenterActivity : NetBaseActivity() {
    var orgName: String = ""
    var orgNumber: String = ""
    var infoBean: OrgThirdDataDetail.CimOrginfoWebBean? = null
    var topHafeBean: List<OrgThirdDataDetail.NeedListBean>? = null
    var bottomHafeBean: List<OrgThirdDataDetail.ChooseListBean>? = null
    override fun getContentLayout(): Int = R.layout.activity_org_user

    override fun onCreate(savedInstanceState: Bundle?) {
        orgName = intent.getStringExtra("orgName") ?: ""
        orgNumber = intent.getStringExtra("orgNumber") ?: ""
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        headerLayout.showTitle(orgName)
        canInvestProductEntry.setOnClickListener {
            //可投项目，跳转平台详情页
            val intent = Intent(this, OrgInfoDetailActivity::class.java)
            intent.putExtra("orgName", orgName)
            intent.putExtra("orgNumber", orgNumber)
            ctx.showActivity(ctx, intent)
        }


        refreshViewSrl.setOnRefreshListener {
            initData()
            refreshViewSrl.finishRefresh()
        }


    }


    private fun initData() {
        RetrofitHelper.getInstance()
                .retrofit
                .create(OrgInfoApi::class.java)
                .orgThirdDataDetail(ParamesHelp().put("orgNo", orgNumber).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<OrgThirdDataDetail>>() {
                    override fun bindViewWithDate(@NonNull response: BaseResponseEntity<OrgThirdDataDetail>) {
                        infoBean = response.data.cimOrginfo
                        topHafeBean = response.data.needList
                        bottomHafeBean = response.data.chooseList
                        productNumTv.visibility = if (response.data.productCount.isNullOrBlank()) View.GONE else View.VISIBLE
                        productNumTv.text = response.data.productCount
                        totalMoneyTv.text = response.data.totalAssets

                        initOrgInfoView()
                        initTopListView()
                        initBottomListView()
                        initBottomBtn()
                    }
                })
    }


    private fun initBottomBtn() {
        //充值
        if (infoBean?.orgRechargeUrl.isNullOrBlank()) {
            rechargesEntry.visibility = View.GONE
        } else {
            rechargesEntry.setOnClickListener {
                skipThirdWeb(infoBean?.orgRechargeUrl)
            }
        }
        //提现
        if (infoBean?.orgWithdrawDepositUrl.isNullOrBlank()) {
            withdrawEntry.visibility = View.GONE
        } else {
            withdrawEntry.setOnClickListener {
                skipThirdWeb(infoBean?.orgWithdrawDepositUrl)
            }
        }

    }


    private fun initTopListView() {
        topHalfListLl.removeAllViews()
        topHafeBean?.forEach {
            var bean = it;
            var view = LayoutInflater.from(ctx).inflate(R.layout.item_org_usercenter, null)
            val nameTv = view.findViewById<TextView>(R.id.nameTv)
            val numTv = view.findViewById<TextView>(R.id.numTv)
            val arrowIv = view.findViewById<ImageView>(R.id.arrowIv)
            nameTv.text = bean.name
            if (!TextUtil.isEmpty(bean.skipUrl)) {
                view.setOnClickListener { skipThirdWeb(bean.skipUrl) }
            }
            if (!TextUtil.isEmpty(bean.data)) {
                arrowIv.visibility = View.VISIBLE
                numTv.text = "${bean.data}${bean.unit}"
                topHalfListLl.addView(view)
            }else{
                arrowIv.visibility = View.GONE
            }
        }
    }

    private fun initBottomListView() {
        bottomHalfListLl.removeAllViews()
        bottomHafeBean?.forEach {
            var bean = it;
            var view = LayoutInflater.from(ctx).inflate(R.layout.item_org_usercenter, null)
            val nameTv = view.findViewById<TextView>(R.id.nameTv)
            val numTv = view.findViewById<TextView>(R.id.numTv)
            val arrowIv = view.findViewById<ImageView>(R.id.arrowIv)
            nameTv.text = bean.name

            if (!TextUtil.isEmpty(bean.skipUrl)) {
                view.setOnClickListener { skipThirdWeb(bean.skipUrl) }
                arrowIv.visibility = View.VISIBLE
                numTv.text = "${bean.data}${bean.unit}"
                bottomHalfListLl.addView(view)
            }else{
                arrowIv.visibility = View.GONE
            }

        }
    }

    private fun initOrgInfoView() {
        PhotoUtil.loadImageByGlide(ctx, infoBean?.platformIco, orgInfoIV)
        orgNameTv.text = infoBean?.name
//        orgIndroduceTv.text = infoBean?.orgProfile?.replace(",", " | ")//机构简介
        yearProfitTv.text = infoBean?.minProfit + "%~" + infoBean?.maxProfit + "%"
        orgFeeRatioTv.text = infoBean?.orgFeeRatio + "%"
        orgSafeLevelTv.text = when (infoBean?.grade) { //1-B,2-BB,3-BBB,4-A,5-AA,6-AAA
            "1" -> "B"
            "2" -> "BB"
            "3" -> "BBB"
            "4" -> "A"
            "5" -> "AA"
            "6" -> "AAA"
            else -> {
                ""
            }
        }

        val orgTag = infoBean?.orgTag
        orgTagContant.removeAllViews()
        if (orgTag != null) {
            val split = orgTag!!.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (str in split) {
                val tv = View.inflate(this, R.layout.text_org_detial_tager, null) as TextView
                val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                layoutParams.setMargins(0, 0, 27, 0)//4个参数按顺序分别是左上右下
                tv.layoutParams = layoutParams
                tv.setText(str)
                orgTagContant.addView(tv)
            }
        }
        var skipNeedCardBind = "0" != infoBean?.orgProductUrlSkipBindType

        if (infoBean?.orgUrlSkipIndex.isNullOrBlank()) {
            orgHomePageTv.text = "进入平台"
            orgHPEntry.setOnClickListener {
                val service = JumpThirdPartyService(
                        JUMP_TYPE_USER_CENTER, skipNeedCardBind,context as Activity, infoBean?.name, infoBean?.orgNumber, null)
                service.run()
            }

        } else {
            orgHomePageTv.text = "平台首页"
            orgHPEntry.setOnClickListener { skipThirdWeb(infoBean?.orgUrlSkipIndex) }
        }

        //我的投资
        if (infoBean?.orgUrlSkipProductBuyRecord.isNullOrBlank()) {
            myInvestEntryLl.visibility = View.VISIBLE
            myInvestEntry.setOnClickListener { skipThirdWeb(infoBean?.orgUsercenterUrl?:"") }
        } else {
            myInvestEntryLl.visibility = View.VISIBLE
            myInvestEntry.setOnClickListener { skipThirdWeb(infoBean?.orgUrlSkipProductBuyRecord) }
        }


    }

    private fun skipThirdWeb(url: String?) {
        RetrofitHelper.getInstance()
                .retrofit
                .create(ThirdPartApi::class.java)
                .orgUrlSkipParameter(ParamesHelp().put("orgNo", orgNumber).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<OrgUrlSkipParameter>>() {
                    override fun bindViewWithDate(@NonNull response: BaseResponseEntity<OrgUrlSkipParameter>) {
                        var paramStr = response.data.data   //获取到的是后台encode后的参数拼接字符串，需要进行decode解码
                        val decode = URLDecoder.decode(paramStr)
                        Logger.e("URLDecoder===>" + decode)
                        Logger.e("URL===>" + url)
                        ThirdOrgWebActivity.showThisActivityForPost(ctx, url, decode.toByteArray(), orgName)
//                        var urlEnd = url+"?"+ decode
//                        WebActivityCommon.showThisActivity(ctx, urlEnd, "")
                    }
                })
//        WebActivityCommon.showThisActivity(ctx, url, "")
    }


}