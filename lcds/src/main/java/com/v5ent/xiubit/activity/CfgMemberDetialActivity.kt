package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.florent37.viewanimator.AnimationListener
import com.github.florent37.viewanimator.ViewAnimator
import com.nex3z.flowlayout.FlowLayout
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.CfgMemberDetailEntity
import com.toobei.common.entity.CustomerInvestRecordEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.CustomerAndCfgMemberApi
import com.toobei.common.utils.PhotoUtil
import com.toobei.common.utils.SystemFunctionUtil
import com.toobei.common.utils.TextFormatUtil
import com.toobei.common.view.ExpandableLayout
import com.toobei.common.view.Util
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.MemberInvestRecordAdapter
import com.v5ent.xiubit.entity.JobGradeData
import com.v5ent.xiubit.network.httpapi.AttientApi
import com.v5ent.xiubit.network.httpapi.JobGradeApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cfg_member_detial.*
import org.xsl781.utils.Logger
import org.xsl781.utils.SystemTool
import java.lang.Float.parseFloat

class CfgMemberDetialActivity : NetBaseActivity() {
    private var pageIndex = 1
    private lateinit var headIv: ImageView
    private lateinit var phoneNumTv: TextView
    private lateinit var nameTv: TextView
    private lateinit var inventingAmountTv: TextView
    private lateinit var monthContriIncomeTv: TextView
    private lateinit var monthInvestAmountTv: TextView
    private lateinit var lastStartTimeTv: TextView
    private lateinit var totalContriIncomeTv: TextView
    private lateinit var totalIssuingAmountTv: TextView
    private lateinit var fristInvestTimeTv: TextView
    private lateinit var registerTimeTv: TextView
    private lateinit var investRecordNumTv: TextView
    private lateinit var dirCfgNumTv: TextView
    private lateinit var secondCfgNumTv: TextView
    private lateinit var userId: String
    private var mobile: String? = ""
    private lateinit var attentIv: ImageView
    private lateinit var platformlogoFl: FlowLayout
    private lateinit var levelTitleTv: TextView
    private lateinit var progressContant1: View
    private lateinit var curAmountTv: TextView
    private lateinit var tagetAmountTv: TextView
    private lateinit var progressView1: ProgressBar
    private lateinit var progressCircle1: ImageView
    private lateinit var progressContant2: View
    private lateinit var curCfgNumTv: TextView
    private lateinit var targetCfgNumTv: TextView
    private lateinit var registOrgNumTv: TextView
    private lateinit var progressView2: ProgressBar
    private lateinit var progressCircle2: ImageView
    private lateinit var gradeTv: TextView
    private lateinit var headView: View
    private var isAttent = false


    override fun getContentLayout(): Int {
        return R.layout.activity_cfg_member_detial
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = intent.getStringExtra("userId") ?: ""
        initView()
        getNetData()
    }

    lateinit var investRecordAdapter: MemberInvestRecordAdapter

    private fun initView() {
        headerLayout.showTitle("直推理财师详情")

        headView = LayoutInflater.from(ctx).inflate(R.layout.activity_cfg_member_detial_header, null)
        headFindView(headView)

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getInvestRecord()
            refreshViewSrl.finishRefresh()
        }

        //投资记录
        investRecordRv.layoutManager = LinearLayoutManager(ctx)
        investRecordAdapter = MemberInvestRecordAdapter(MemberInvestRecordAdapter.MEMBERTYPE_CFG)
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
        dirCfgNumTv = headView.findViewById<TextView>(R.id.dirCfgNumTv)
        secondCfgNumTv = headView.findViewById<TextView>(R.id.secondCfgNumTv)
        registOrgNumTv = headView.findViewById<TextView>(R.id.registOrgNumTv)
        gradeTv = headView.findViewById<TextView>(R.id.gradeTv)
        attentIv = headView.findViewById<ImageView>(R.id.attentIv)
        platformlogoFl = headView.findViewById<FlowLayout>(R.id.platformlogoFl)
        /***------- 职级进度----------------------------------------------------------------------------*/

        levelTitleTv = headView.findViewById<TextView>(R.id.levelTitleTv)
        progressContant1 = headView.findViewById(R.id.progressContant1)
        curAmountTv = headView.findViewById<TextView>(R.id.curAmountTv)
        tagetAmountTv = headView.findViewById<TextView>(R.id.tagetAmountTv)
        progressView1 = headView.findViewById<ProgressBar>(R.id.progressView1)
        progressCircle1 = headView.findViewById<ImageView>(R.id.progressCircle1)

        progressContant2 = headView.findViewById(R.id.progressContant2)
        curCfgNumTv = headView.findViewById<TextView>(R.id.curCfgNumTv)
        targetCfgNumTv = headView.findViewById<TextView>(R.id.targetCfgNumTv)
        progressView2 = headView.findViewById<ProgressBar>(R.id.progressView2)
        progressCircle2 = headView.findViewById<ImageView>(R.id.progressCircle2)
        /***-----------------------------------------------------------------------------------*/

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
        getJobGrade()
        getInvestRecord()

    }

    private fun getJobGrade() {
        RetrofitHelper.getInstance().retrofit.create(JobGradeApi::class.java)
                .directCfpJobGrade(ParamesHelp().
                        put("userId", userId).
                        build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<JobGradeData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<JobGradeData>) {
                        refreshLevelProgress(response.data)
                    }

                })
    }


    private fun getPersonInfo() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .cfgMemberDetail(ParamesHelp()
                        .put("userId", "$userId")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CfgMemberDetailEntity>() {
                    override fun bindViewWithDate(response: CfgMemberDetailEntity) {
                        var data = response.data
                        nameTv.text = data.userName
                        gradeTv.text = data.grade
                        phoneNumTv.text = data.mobile
                        mobile = data.mobile
                        dirCfgNumTv.text = "Ta的直推理财师：${data.directRecomCfp}人"
                        secondCfgNumTv.text = "Ta的二级理财师：${data.secondLevelCfp}人"
                        inventingAmountTv.text = data.currInvestAmt
                        monthContriIncomeTv.text = data.thisMonthProfit
                        monthInvestAmountTv.text = data.thisMonthIssueAmt
                        lastStartTimeTv.text = data.loginTime
                        totalContriIncomeTv.text = data.totalProfit
                        totalIssuingAmountTv.text = data.totalIssueAmt
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
                                    , ctx.resources.getDimension(R.dimen.w35).toInt())
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

    var emptyView: EmptyView? = null
    private fun getInvestRecord() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .customerInvestRecord(ParamesHelp()
                        .put("type", "1")
                        .put("userId", "$userId")
                        .put("pageIndex", "$pageIndex")
                        .put("pageSize", "20")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerInvestRecordEntity>() {
                    override fun bindViewWithDate(response: CustomerInvestRecordEntity) {
                        investRecordNumTv.text = "出单记录 (${response.data.totalCount}笔)"
                        if (response.data.pageIndex == 1) investRecordAdapter.setNewData(response.data.datas)
                        else investRecordAdapter.addData(response.data.datas)
                        bindLoadMoreView(response.data, investRecordAdapter)
                        pageIndex++

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


    private fun refreshLevelProgress(data: JobGradeData) {

        try {

            levelTitleTv.text = data.cfpLevelTitleNew

            //进度
            val curAmount = data.yearpurAmountActualNew.toFloat()
            val amountMax = parseFloat(data.yearpurAmountMaxNew).toInt()
            progressView1.max = amountMax * 100
            tagetAmountTv.text = "$amountMax"
            val ratio1 = curAmount / amountMax
            if (curAmount * 100 >= amountMax * 100) {
                progressView1.max = curAmount.toInt() * 100
            }
            setProgressAnimal(ratio1, curAmount, progressView1, progressCircle1, curAmountTv, false)

            if ("0" == data.lowerLevelCfpMaxNew || TextUtil.isEmpty(data.lowerLevelCfpMaxNew)) {
                progressContant2.visibility = View.GONE
            } else {
                val curCfgNum = data.lowerLevelCfpActualNew.toInt()
                val targetCfgNum = data.lowerLevelCfpMaxNew.toInt()
                targetCfgNumTv.text = "${targetCfgNum}名${data.lowerLevelCfp}"
                progressContant2.visibility = View.VISIBLE
                progressView2.max = targetCfgNum * 100
                val ratio2 = curCfgNum.toFloat() / targetCfgNum
                //当前值溢出时
                if (curCfgNum >= targetCfgNum) {
                    progressView2.max = curCfgNum * 100
                }
                setProgressAnimal(ratio2, curCfgNum.toFloat(), progressView2, progressCircle2, curCfgNumTv, true)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun setProgressAnimal(progressRatio: Float, progressValue: Float, progressBar: ProgressBar, progressCircle: ImageView, progressTv: TextView, textTypeIsInt: Boolean) {

        val progressWidth = progressBar.width
        val circleWith = progressCircle.width
        //进度文字
        progressTv.text = progressValue.toString() + ""
        progressTv.measure(0, 0)
        val textWidth = progressTv.measuredWidth
        Logger.e("textWidth ==>" + textWidth)
        var textX = progressWidth * progressRatio - textWidth / 2
        if (textX <= 0 || progressRatio == 0f) {
            textX = 0f
        } else if (progressRatio > 1) {
            textX = (progressWidth - textWidth / 2).toFloat()
        }
        //进度圆圈
        var circleX2 = progressWidth * progressRatio - circleWith / 2
        if (circleX2 < 0) {
            circleX2 = 0f
        } else if (circleX2 + (circleWith - Util.dip2px(ctx, 6f)) > progressWidth) {
            circleX2 = (progressWidth - (circleWith - Util.dip2px(ctx, 6f))).toFloat()
        }

        ViewAnimator.animate(progressBar).custom(AnimationListener.Update<ProgressBar> { view, value ->
            progressBar.progress = (value * 100).toInt()
            if (textTypeIsInt) {
                progressTv.text = "${value.toInt()}"
            } else {
                progressTv.text = TextFormatUtil.formatFloat2zero("${value.toString()}")
            }
        }, 0f, progressValue).duration(1000).andAnimate(progressCircle)
                .translationX(0f, circleX2)
                .duration(1000)
                .andAnimate(progressTv)
                .translationX(0f, textX)
                .duration(1000)
                .start()
    }


    private fun addAttent() {
        RetrofitHelper.getInstance().retrofit.create(AttientApi::class.java)
                .addCfgAttient(ParamesHelp()
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
                .removeCfgAttient(ParamesHelp()
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