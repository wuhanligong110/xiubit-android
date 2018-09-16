package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.nex3z.flowlayout.FlowLayout
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.CustomerInvestRecordEntity
import com.toobei.common.entity.CustomerMemberDetailEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.CustomerAndCfgMemberApi
import com.toobei.common.utils.PhotoUtil
import com.toobei.common.utils.SystemFunctionUtil
import com.toobei.common.view.ExpandableLayout
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.MemberInvestRecordAdapter
import com.v5ent.xiubit.network.httpapi.AttientApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_customer_member_detial.*
import org.xsl781.utils.SystemTool

class CustomerMemberDetialActivity : NetBaseActivity() {
    private var pageIndex = 1
    lateinit var headIv: ImageView
    lateinit var phoneNumTv: TextView
    lateinit var nameTv: TextView
    lateinit var inventingAmountTv: TextView
    lateinit var monthContriIncomeTv: TextView
    lateinit var monthInvestAmountTv: TextView
    lateinit var lastStartTimeTv: TextView
    lateinit var totalContriIncomeTv: TextView
    lateinit var totalIssuingAmountTv: TextView
    lateinit var fristInvestTimeTv: TextView
    lateinit var registerTimeTv: TextView
    lateinit var investRecordNumTv: TextView
    lateinit var registOrgNumTv: TextView
    lateinit var userId: String
    var mobile: String? = ""
    lateinit var attentIv: ImageView
    lateinit var platformListRv: RecyclerView
    lateinit var platformlogoFl: FlowLayout
    private var isAttent = false


    override fun getContentLayout(): Int {
        return R.layout.activity_customer_member_detial
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.getStringExtra("userId") ?: ""
        initView()
        getNetData()
    }

    lateinit var investRecordAdapter: MemberInvestRecordAdapter

    private lateinit var headView : View
    private fun initView() {
        headerLayout.showTitle("客户详情")

        headView = LayoutInflater.from(ctx).inflate(R.layout.activity_customer_member_detial_header, null)
        headFindView(headView)

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getInvestRecord()
            refreshViewSrl.finishRefresh()
        }

        //投资记录
        investRecordRv.layoutManager = LinearLayoutManager(ctx)
        investRecordAdapter = MemberInvestRecordAdapter(MemberInvestRecordAdapter.MEMBERTYPE_CUSTOMER)
        investRecordAdapter.addHeaderView(headView)
        investRecordRv.adapter = investRecordAdapter

        investRecordAdapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getInvestRecord()
        }, investRecordRv)



    }

    private fun headFindView(headView: View) {
        headIv = headView.findViewById<ImageView>(R.id.headIv)
        nameTv = headView.findViewById<TextView>(R.id.nameTv)
        phoneNumTv = headView.findViewById<TextView>(R.id.phoneNumTv)
        inventingAmountTv = headView.findViewById<TextView>(R.id.inventingAmountTv)
        monthContriIncomeTv = headView.findViewById<TextView>(R.id.monthContriIncomeTv)
        monthInvestAmountTv = headView.findViewById<TextView>(R.id.monthInvestAmountTv)
        lastStartTimeTv = headView.findViewById<TextView>(R.id.lastStartTimeTv)
        totalContriIncomeTv = headView.findViewById<TextView>(R.id.totalContriIncomeTv)
        totalIssuingAmountTv = headView.findViewById<TextView>(R.id.totalIssuingAmountTv)
        fristInvestTimeTv = headView.findViewById<TextView>(R.id.fristInvestTimeTv)
        registerTimeTv = headView.findViewById<TextView>(R.id.registerTimeTv)
        investRecordNumTv = headView.findViewById<TextView>(R.id.investRecordNumTv)
        registOrgNumTv = headView.findViewById<TextView>(R.id.registOrgNumTv)
        attentIv = headView.findViewById<ImageView>(R.id.attentIv)
        platformlogoFl = headView.findViewById<FlowLayout>(R.id.platformlogoFl)

        val dataInfoEl = headView.findViewById<ExpandableLayout>(R.id.dataInfoEl)
        val platformListEl = headView.findViewById<ExpandableLayout>(R.id.platformListEl)
        val expandSwitchIv = headView.findViewById<ImageView>(R.id.expandSwitchIv)
        val regrestPlatformArrIv = headView.findViewById<ImageView>(R.id.regrestPlatformArrIv)
        val regrestPlatformSwitchV = headView.findViewById<View>(R.id.regrestPlatformSwitchV)
        val callPhoneEntry = headView.findViewById<View>(R.id.callPhoneEntry)

        //onclick
        //数据统计
        expandSwitchIv.setOnClickListener {
            if (dataInfoEl.isExpanded) {
                dataInfoEl.collapse()
                expandSwitchIv.setImageResource(R.drawable.img_arrow_down)
            } else {
                dataInfoEl.expand()
                expandSwitchIv.setImageResource(R.drawable.img_arrow_up)
            }
        }
        //注册平台
        regrestPlatformSwitchV.setOnClickListener {
            if (platformListEl.isExpanded) {
                platformListEl.collapse()
                regrestPlatformArrIv.setImageResource(R.drawable.img_arrow_down)
            } else {
                platformListEl.expand()
                regrestPlatformArrIv.setImageResource(R.drawable.img_arrow_up)
            }
        }
        //打电话
        callPhoneEntry.setOnClickListener {
            SystemFunctionUtil.CallNormerPhone(ctx, mobile)
        }
        //关注
        attentIv.setOnClickListener {
            if (isAttent) removeAttent()
            else addAttent()
        }

    }


    private fun getNetData() {
        getPersonInfo()
        getInvestRecord()

    }


    private fun getPersonInfo() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .customerMemberDetail(ParamesHelp()
                        .put("userId", "$userId")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerMemberDetailEntity>() {
                    override fun bindViewWithDate(response: CustomerMemberDetailEntity) {
                        var data = response.data
                        nameTv.text = data.userName
                        phoneNumTv.text = data.mobile
                        mobile = data.mobile
                        inventingAmountTv.text = data.currInvestAmt
                        monthContriIncomeTv.text = data.thisMonthProfit
                        monthInvestAmountTv.text = data.thisMonthInvestAmt
                        lastStartTimeTv.text = data.loginTime
                        totalContriIncomeTv.text = data.totalProfit
                        totalIssuingAmountTv.text = data.totalInvestAmt
                        fristInvestTimeTv.text = data.firstInvestTime
                        registerTimeTv.text = data.registTime
                        isAttent = data.follow
                        attentIv.setImageResource(if (data.follow) R.drawable.ic_cancel_attent_img else R.drawable.ic_attent_img)
                        PhotoUtil.loadImageByGlide(ctx, data.headImage, headIv, C.DefaultResId.HEADER_IMG)
                        registOrgNumTv.text = "已注册平台 (${data.registeredOrgList.size}个)"

                        val divide = (MyApp.displayWidth - com.jungly.gridpasswordview.Util.dp2px(ctx,30)
                                -  (ctx.resources.getDimension(R.dimen.w77) * 4)) / 3
                        platformlogoFl.childSpacing = divide.toInt()

                        platformlogoFl.removeAllViews()
                        data.registeredOrgList.forEach {
                            var iv = ImageView(ctx)
                            iv.scaleType = ImageView.ScaleType.FIT_XY
                            var lp = ViewGroup.LayoutParams(ctx.resources.getDimension(R.dimen.w77).toInt()
                                    ,ctx.resources.getDimension(R.dimen.w35).toInt())
                            iv.layoutParams = lp
                            PhotoUtil.loadImageByGlide(ctx, it.orgLogo, iv)
                            platformlogoFl.addView(iv)
                        }


                        if (data.registeredOrgList.isEmpty()) {
                            var emptyView = EmptyView(ctx).showEmpty("当前没有已注册平台")
                            platformlogoFl.addView(emptyView)
                        }

                    }

                })
    }

    var emptyView : EmptyView? = null
    private fun getInvestRecord() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .customerInvestRecord(ParamesHelp()
                        .put("type","2")
                        .put("userId", "$userId")
                        .put("pageIndex", "$pageIndex")
                        .put("pageSize", "20")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerInvestRecordEntity>() {
                    override fun bindViewWithDate(response: CustomerInvestRecordEntity) {
                        investRecordNumTv.text = "投资记录 (${response.data.totalCount}笔)"
                        if (response.data.pageIndex == 1) investRecordAdapter.setNewData(response.data.datas)
                        else investRecordAdapter.addData(response.data.datas)
                        bindLoadMoreView(response.data,investRecordAdapter)
                        pageIndex ++

                        if (investRecordAdapter.data.size == 0) {
                            headView.post(Runnable {
                                val height = MyApp.displayHeight - headView.height - SystemTool.getStatusBarHeight(ctx) - com.jungly.gridpasswordview.Util.dp2px(ctx, 44) //顶部标题栏
                                if (emptyView == null) {
                                    emptyView = EmptyView(ctx).setHeight(height).showEmpty("当前没有出单记录")
                                    emptyView!!.visibility = View.VISIBLE
                                    investRecordAdapter.setFooterView(emptyView)
                                    investRecordAdapter.setHeaderFooterEmpty(true, true)

                                }
                            })
                        }else {
                            emptyView?.visibility = View.GONE
                        }
                    }
                })
    }

    private fun addAttent() {
        RetrofitHelper.getInstance().retrofit.create(AttientApi::class.java)
                .addCustomerAttient(ParamesHelp()
                        .put("userId", "$userId")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {
                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        if (response.code == "0") {
                            isAttent = true
                            attentIv.setImageResource(R.drawable.ic_cancel_attent_img)

                        }
                        noMsgToast = true
                        super.onNext(response)

                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }

    private fun removeAttent() {
        RetrofitHelper.getInstance().retrofit.create(AttientApi::class.java)
                .removeCustomerAttient(ParamesHelp()
                        .put("userId", "$userId")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {
                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        if (response.code == "0") {
                            isAttent = false
                            attentIv.setImageResource(R.drawable.ic_attent_img)

                        }
                        noMsgToast = true
                        super.onNext(response)

                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
    }
}
