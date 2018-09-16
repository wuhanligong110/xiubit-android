package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.InsuranceTestQuestionsData
import com.v5ent.xiubit.event.SmartInsuranceTestResultRefreshEvent
import com.v5ent.xiubit.network.httpapi.SmartInsuranceTestApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.popupwindow.InsuranceTestSelectPopu
import com.umeng.analytics.MobclickAgent
import com.way.util.DisplayUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_insurance_test.*
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.TimeUnit

/**
 * Created by hasee-pc on 2017/12/19.
 */
class InsuranceTestActivity : NetBaseActivity() {
    lateinit var adapter: ChatAdapter
    lateinit var questions: ArrayList<InsuranceTestQuestionsData.Companion.Question>
    val parameList = ArrayList<String>()
    var stepIndex: Int = 0
    var endStep = false
    var endView: View? = null
    var endBlankView: View? = null
    lateinit var mRecycleView: RecyclerView
    val questionsData = InsuranceTestQuestionsData()
    override fun getContentLayout(): Int = R.layout.activity_insurance_test

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobclickAgent.onEvent(ctx, "T_4_4_1")
        initView()
    }


    private fun initView() {
        headerLayout.showTitle("智能测评")
        mRecycleView = recyclerView
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = ChatAdapter()
        recyclerView.adapter = adapter
        questions = questionsData.questions
        headerLayout.postDelayed({
            startChat()
        }, 1000)
        endView = LayoutInflater.from(ctx).inflate(R.layout.insurance_end_view, null)
        endView?.findViewById<View>(R.id.endTv)?.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_4_2")
            val intent = Intent(ctx, WebActivityCommon::class.java)
            intent.putExtra("url", C.INSURANCE_TEST_RESULT_PAGE)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            ctx.startActivity(intent)
        }
        endView?.visibility = View.GONE
        adapter.addFooterView(endView)
        //底部间隔
        endBlankView = View(ctx)
        endBlankView?.minimumHeight = DisplayUtil.dip2px(ctx, 300f)
        adapter.addFooterView(endBlankView)
    }

    private var timerDisposable: Disposable? = null
    private fun startChat() {
        parameList.clear()
        //开场白
        val openTaik = InsuranceTestQuestionsData.openTaik
        timerDisposable = Observable.interval(0, 1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ stepIndex ->
                    adapter.addData(QuestBean(2, openTaik[stepIndex.toInt()], 0))
                    if (openTaik.size - 1 == stepIndex.toInt()) {
                        //提问开始
                        adapter.addData(QuestBean(0, questions[0].firstQus, 0))
                        stopOpenTalkTimer()
                    }

                })


    }

    override fun onDestroy() {
        super.onDestroy()
        if (!(timerDisposable?.isDisposed ?: false)) timerDisposable?.dispose()
    }

    private fun stopOpenTalkTimer() {
        if (!(timerDisposable?.isDisposed ?: false)) timerDisposable?.dispose()
    }

    override fun onLoadFailRefresh() {
        super.onLoadFailRefresh()
        upLoadResult()
    }

    private fun upLoadResult() {
        if (parameList.size != 6) return
        RetrofitHelper.getInstance().retrofit.create(SmartInsuranceTestApi::class.java)
                .upTestResult(ParamesHelp()
                        .put("sex", parameList[0])
                        .put("age", parameList[1])
                        .put("familyMember", parameList[2])
                        .put("yearIncome", parameList[3])
                        .put("familyLoan", parameList[4])
                        .put("familyEnsure", parameList[5])
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {

                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {}

                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        noMsgToast = true
                        super.onNext(response)
                        if (response.code == "0") {
                            showLoadContent()
                            EventBus.getDefault().post(SmartInsuranceTestResultRefreshEvent())
                            endView?.visibility = View.VISIBLE
                            endBlankView?.visibility = View.GONE
                        } else {
                            ToastUtil.showCustomToast("提交失败，请检查网络并重试")
                            showLoadFail()
                        }
                    }


                })
    }


    /**
     * type : 0 ： 机器人回复 ； 1 ： 用户回复
     * stepIndex 第几步问答 ，第一步为 0
     */
    data class QuestBean(val type: Int, val contant: String, val stepIndex: Int)

    inner class ChatAdapter : BaseQuickAdapter<QuestBean, BaseViewHolder> {
        val hasinitPostions: ArrayList<Int> = arrayListOf()

        constructor() : super(R.layout.item_insurance_test_chat)

        override fun addData(data: QuestBean) {
            val size = mData.size + 1
            super.addData(data)
            if (mRecycleView != null) {
                mRecycleView.postDelayed({ mRecycleView.smoothScrollToPosition(size) }, 1500)
            }
        }

        override fun convert(helper: BaseViewHolder, item: QuestBean) {
            val tv = helper.getView<TextView>(R.id.leftTv)
            when (item.type) {
                0 -> {
                    //机器回答
                    helper.getView<View>(R.id.headIv).visibility = View.VISIBLE
                    helper.getView<View>(R.id.leftTv).visibility = View.VISIBLE
                    helper.getView<View>(R.id.rightTv).visibility = View.GONE

                    if (hasinitPostions.contains(helper.adapterPosition)) {
                        //已经初始化过的条目直接显示内容，避免滑动复用过程中再次显示"正在输入......"
                        tv.text = item.contant
                        return
                    }
                    tv.text = "正在输入......"
                    hasinitPostions.add(helper.adapterPosition)
                    tv.postDelayed({
                        tv.text = item.contant
                        if (endStep) {
                            endStep = false
                            stepIndex++
                            if (questions.size >= stepIndex + 1) adapter.addData(QuestBean(0, questions[stepIndex].firstQus, stepIndex))
                            else upLoadResult() //测评结束
                        } else {
                            if (stepIndex + 1 > questions.size) return@postDelayed

                            var answers: ArrayList<String>
                            var noCoExistStr: String? = null
                            if (questions[stepIndex] is InsuranceTestQuestionsData.Companion.QuestionFamilySafeGuard) {
                                //如果是家庭保障问题
                                answers = questionsData.userFamilyMember
                                noCoExistStr = "都没有"

                            } else {
                                answers = questions[stepIndex].answers
                            }

                            InsuranceTestSelectPopu(ctx
                                    , answers
                                    , questions[stepIndex].isSingleAnswer
                                    , noCoExistStr
                                    , questions[stepIndex].getForeverChecked()
                                    , object : InsuranceTestSelectPopu.OnEventListener {
                                override fun onConfirmListener(selectedTagsPos: HashSet<Int>, selectedTagsStrArr: ArrayList<String>) {
                                    //如果是家庭成员，把用户选择的成员储存起来
                                    if (questions[stepIndex] is InsuranceTestQuestionsData.Companion.QuestionFamilyMember) {
                                        questionsData.userFamilyMember.clear()
                                        questionsData.userFamilyMember.addAll(selectedTagsStrArr)
                                    }
                                    var paramStr = ""
                                    //如果是家庭保障，参数拼接需要根据结果特殊处理
                                    if (questions[stepIndex] is InsuranceTestQuestionsData.Companion.QuestionFamilySafeGuard) {
                                        var other = 2
                                        selectedTagsPos.clear()
                                        selectedTagsStrArr.forEach {
                                            when (it) {
                                                "都没有" -> {
                                                    paramStr += "0,"
                                                    selectedTagsPos.add(0)
                                                }
                                                "本人" -> {
                                                    paramStr += "1,"
                                                    selectedTagsPos.add(1)
                                                }
                                                else -> {
                                                    paramStr += "${other++},"
                                                    selectedTagsPos.add(other)
                                                }
                                            }
                                        }
                                        if (paramStr.endsWith(",")) {
                                            paramStr = paramStr.substring(0, paramStr.lastIndex)
                                        }
                                    } else {
                                        selectedTagsPos.forEach {
                                            paramStr += "$it,"
                                        }
                                        if (paramStr.endsWith(",")) {
                                            paramStr = paramStr.substring(0, paramStr.lastIndex)
                                        }
                                    }
                                    parameList.add(paramStr)
                                    //回应
                                    adapter.addData(QuestBean(1, questions[stepIndex].getAnswerStr(selectedTagsStrArr), stepIndex))
                                    if (questions[stepIndex].getRespondsStr(selectedTagsPos).isNotEmpty()) {
                                        adapter.addData(QuestBean(0, questions[stepIndex].getRespondsStr(selectedTagsPos), stepIndex))
                                        endStep = true
                                    } else {
                                        stepIndex++
                                        if (questions.size >= stepIndex + 1) adapter.addData(QuestBean(0, questions[stepIndex].firstQus, stepIndex))
                                        else upLoadResult() //测评结束
                                    }


                                }

                            }).showPopupWindow(activityRootView)
                        }
                    }, 1000)

                }
                1 -> {
                    //用户回答
                    helper.getView<View>(R.id.headIv).visibility = View.GONE
                    helper.getView<View>(R.id.leftTv).visibility = View.GONE
                    helper.getView<View>(R.id.rightTv).visibility = View.VISIBLE
                    helper.setText(R.id.rightTv, item.contant)
                }

                2 -> {
                    helper.getView<View>(R.id.headIv).visibility = View.VISIBLE
                    helper.getView<View>(R.id.leftTv).visibility = View.VISIBLE
                    helper.getView<View>(R.id.rightTv).visibility = View.GONE
                    //开场白
                    tv.text = item.contant
                }

            }
        }

    }
}