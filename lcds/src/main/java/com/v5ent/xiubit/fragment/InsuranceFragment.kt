package com.v5ent.xiubit.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import com.github.florent37.viewanimator.ViewAnimator
import com.toobei.common.entity.*
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.ThirdPartApi
import com.toobei.common.utils.NetworkUtils
import com.toobei.common.utils.PhotoUtil
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.InsuranceClassifyActivity
import com.v5ent.xiubit.activity.InsuranceTestActivity
import com.v5ent.xiubit.activity.LoginActivity
import com.v5ent.xiubit.activity.WebActivityCommon
import com.v5ent.xiubit.data.SmartTestRecomendAdapter
import com.v5ent.xiubit.data.recyclerDecoration.HorizontalSpacItemDecoration
import com.v5ent.xiubit.data.recylerapter.InsuranceAdapter
import com.v5ent.xiubit.entity.SmartTestResultData
import com.v5ent.xiubit.event.SmartInsuranceTestResultRefreshEvent
import com.v5ent.xiubit.network.httpapi.BannerApi
import com.v5ent.xiubit.network.httpapi.SmartInsuranceTestApi
import com.v5ent.xiubit.service.LoginService
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.NestedScrollLinearLayoutManager
import com.v5ent.xiubit.view.loadstatue.LoadStatueView
import com.umeng.analytics.MobclickAgent
import com.youth.banner.loader.ImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_insurance.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/13
 */

class InsuranceFragment : FragmentBase() {

    private var pageIndex = 1
    private var mAdapter: InsuranceAdapter? = null
    private var mLoadStatueView: LoadStatueView? = null
    private var smartRecomendAdapter: SmartTestRecomendAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_insurance, null)
        mLoadStatueView = LoadStatueView(ctx, rootView, object : LoadStatueView.ClickCallBack {
            override fun onRefreshNetClick() {
                getData(true)
            }
        })
        return mLoadStatueView!!.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getData(true)
        InsuranceClassifyActivity.initCategoryList(null)
    }

    override fun refreshAfterLogin() {
        super.refreshAfterLogin()
        getData(true)
    }

    private fun initView() {
        refreshView.setOnRefreshListener {
            pageIndex = 1
            getData(true)
            refreshView!!.finishRefresh()
        }
        mAdapter = InsuranceAdapter(InsuranceAdapter.TYPE_SELECTED)
        recyclerView.layoutManager = NestedScrollLinearLayoutManager(ctx)
        recyclerView.adapter = mAdapter
        mAdapter?.setOnLoadMoreListener({ getData(false) }, recyclerView)
        initClickEvent()
        //为你推荐
        testResultRecomendRv.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
        testResultRecomendRv.addItemDecoration(HorizontalSpacItemDecoration(ctx.resources.getDimension(R.dimen.w8).toInt()
                , ctx.resources.getDimension(R.dimen.w15).toInt()))
        smartRecomendAdapter = SmartTestRecomendAdapter()
        testResultRecomendRv.adapter = smartRecomendAdapter

        //文字动画

        ViewAnimator.animate(questTv1)
                .translationX(-15f, 15f)
                .andAnimate(questTv2)
                .translationX(10f, -10f)
                .andAnimate(questTv3)
                .translationX(-20f, 20f)
                .repeatMode(Animation.REVERSE)
                .repeatCount(-1)
                .duration(1200)
                .start()

    }

    private fun initClickEvent() {

        insuranceTypeEntry1.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_1_2")
            skipInsuranceClassify("5")
        }
        insuranceTypeEntry2.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_1_2")
            skipInsuranceClassify("1")
        }
        insuranceTypeEntry3.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_1_2")
            skipInsuranceClassify("8")
        }
        insuranceTypeEntry4.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_1_2")
            skipInsuranceClassify("0")
        }
        //跳转智能评测
        smartTestEntry.setOnClickListener {
            if (LoginService.getInstance().isTokenExsit) {
                MobclickAgent.onEvent(ctx, "T_4_1_3")
                startActivity(Intent(ctx, InsuranceTestActivity::class.java))
            } else {
                startActivity(Intent(ctx, LoginActivity::class.java))
            }
        }
        //保险知识库
        insuranceKnowledgeEntry.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_1_4")
            val intent = Intent(ctx, WebActivityCommon::class.java)
            intent.putExtra("isRedirectUsable", false)
            intent.putExtra("url", C.INSURANCE_KNOW_LEDGE)
            startActivity(intent)
        }
        //智能评测结果
        smartTestResultEntry.setOnClickListener {
            MobclickAgent.onEvent(ctx, "T_4_4_2")
            val intent = Intent(ctx, WebActivityCommon::class.java)
            intent.putExtra("url", C.INSURANCE_TEST_RESULT_PAGE)
            startActivity(intent)
        }
    }

    fun skipInsuranceClassify(type: String) {
//            1-意外险  2-旅游险 3-家财险  4-医疗险 5-重疾险 6-年金险 7-寿险 8-少儿险 9-老年险
        val intent = Intent(ctx, InsuranceClassifyActivity::class.java)
        intent.putExtra("type", type)
        startActivity(intent)
    }

    private fun getData(isRefresh : Boolean) {
        if (NetworkUtils.isConnected()) {
            mLoadStatueView!!.showContantView()
        } else {
            mLoadStatueView!!.showFailView()
            return
        }
        if (isRefresh) pageIndex = 1
        loadbanner()
        loadTestResult()
        loadSelectInsuranceListData()
    }

    private fun loadTestResult() {
        if (!LoginService.getInstance().isTokenExsit) {
            showResultView(false)
            return
        }
        RetrofitHelper.getInstance().retrofit.create(SmartInsuranceTestApi::class.java)
                .getTestResult(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SmartTestResultData>>() {
                    override fun onNext(response: BaseResponseEntity<SmartTestResultData>) {
                        noMsgToast = true
                        super.onNext(response)
                    }
                    override fun bindViewWithDate(response: BaseResponseEntity<SmartTestResultData>) {
                        if (response.data.totalScore.isNullOrBlank() || response.data.totalScore == "0") {
                            showResultView(false)
                            return
                        }
                        showResultView(true)
                        resultTv1.text = "家庭保障指数${response.data.totalScore}分"
                        resultTv2.text = "${response.data.riskGrade}"
                        TextDecorator.decorate(resultTv1,"家庭保障指数${response.data.totalScore}分")
                                .setTextColor(R.color.text_blue_common,"${response.data.totalScore}")
                                .build()

                        val recomBannerData = response.data.recomList
                        smartRecomendAdapter?.setNewData(recomBannerData)
                        if (recomBannerData.size == 0) {
                            resultRecomendTitle.visibility = View.GONE
                            testResultRecomendRv.visibility = View.GONE
                        }
                    }

                    override fun onNetSystemException(error: String?) {
                        super.onNetSystemException(error)
                        showResultView(false)

                    }
                })

    }

    private fun showResultView(hasResult : Boolean){
        smartTestTab.visibility = if (hasResult) View.GONE else View.VISIBLE
        smartTestResultTab.visibility = if (hasResult) View.VISIBLE else View.GONE
        resultRecomendTitle.visibility = if (hasResult) View.VISIBLE else View.GONE
        testResultRecomendRv.visibility = if (hasResult) View.VISIBLE else View.GONE
    }

    private fun loadbanner() {
        RetrofitHelper.getInstance().retrofit.create(BannerApi::class.java)
                .getBanner(ParamesHelp().put("advPlacement", "insurance_banner")
                        .put("appType", "1").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<PageListBase<HomePagerBanners>>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<PageListBase<HomePagerBanners>>) {
                        val datas = response.data.datas
                        if (datas != null && datas.size > 0) {
                            banner.visibility = View.VISIBLE
                            initBanner(datas)
                        } else {
                            banner.visibility = View.GONE
                        }
                    }
                })
    }

    private fun initBanner(bannerDatas: List<HomePagerBanners>) {
        banner.setImages(bannerDatas).setImageLoader(object : ImageLoader() {
            override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                val data = path as HomePagerBanners
                PhotoUtil.loadImageByGlide(ctx, data.imgUrl, imageView, C.DefaultResId.BANNER_PLACT_HOLD_IMG_750x400)
                imageView.setOnClickListener {
                    MobclickAgent.onEvent(ctx, "T_4_1_1")

                    val intent = Intent(ctx, WebActivityCommon::class.java)
                    intent.putExtra("url", data.linkUrl)
                    intent.putExtra("title", data.shareTitle)
                    intent.putExtra("shareContent", ShareContent(data.shareTitle, data.shareDesc, data.linkUrl, data.shareIcon))
                    ctx.startActivity(intent)
                }
            }
        }).start()
    }

    private fun loadSelectInsuranceListData() {
        RetrofitHelper.getInstance().retrofit.create(ThirdPartApi::class.java)
                .insuranceSelectList(ParamesHelp().put("pageIndex", "$pageIndex").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<InsuranceListEntiy>() {
                    override fun bindViewWithDate(response: InsuranceListEntiy) {
                        if (pageIndex == 1) {
                            mAdapter!!.setNewData(response.data.datas)
                        } else {
                            mAdapter!!.addData(response.data.datas)
                        }

                        bindLoadMoreView(response.data, mAdapter, true)
                        pageIndex++
                    }
                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun refreshData(event: SmartInsuranceTestResultRefreshEvent) {
        getData(true)
    }

}
