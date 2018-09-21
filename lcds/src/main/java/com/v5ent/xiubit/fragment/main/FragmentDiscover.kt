package com.v5ent.xiubit.fragment.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.toobei.common.TopBaseActivity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.PageListBase
import com.toobei.common.entity.ShareContent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.*
import com.v5ent.xiubit.data.recylerapter.DiscoverNewsAdapter
import com.v5ent.xiubit.entity.CommonTabEntity
import com.v5ent.xiubit.entity.NewsPageEntity
import com.v5ent.xiubit.entity.NewsTopEntity
import com.v5ent.xiubit.entity.SignInfoData
import com.v5ent.xiubit.event.SignStatueEvent
import com.v5ent.xiubit.fragment.FragmentBase
import com.v5ent.xiubit.network.httpapi.GrowthHandBookApi
import com.v5ent.xiubit.network.httpapi.SignInApi
import com.v5ent.xiubit.service.LoginService
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.umeng.analytics.MobclickAgent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_discover.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by yangLin on 2018/4/9.
 */
class FragmentDiscover : FragmentBase() {
    lateinit var adapter: DiscoverNewsAdapter
    var topNewsLl: LinearLayout? = null
    var pageIndex = 1
    var typeCode = 5 //	1：财经 2：投资 3：管理 4：观点 5：看一看 6：查一查

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(ctx).inflate(R.layout.fragment_discover, null);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        checkSignStatu()
        initData()
    }

    override fun refreshAfterLogin() {
        super.refreshAfterLogin()
        pageIndex = 1
        initData()
    }

    private fun initView() {
        setHeadViewCoverStateBar(statusBar)

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            initData()
            refreshViewSrl.finishRefresh()
        }

        val topTab1 = initTopTab1()
        val topTab2 = initTopTab2()
        vPager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return 2
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = if (position == 0) {
                    topTab1
                } else {
                    topTab2
                }
                container.addView(view)
                return view
            }

        }

        pageIndicator.setViewPager(vPager)
        val tabTexts = arrayListOf<CustomTabEntity>()
        tabTexts.add(CommonTabEntity("看一看"))
        tabTexts.add(CommonTabEntity("查一查"))
        tabLayout.setTabData(tabTexts)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabReselect(position: Int) {}
            override fun onTabSelect(position: Int) {
                when (position) {
                    0 -> typeCode = 5
                    1 -> typeCode = 6
                }
                pageIndex = 1
                initData()
            }

        })

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = DiscoverNewsAdapter()
        adapter.setOnLoadMoreListener({
            initData()
        }, recyclerView)
        recyclerView.adapter = adapter

        topNewsLl = LayoutInflater.from(ctx).inflate(R.layout.discover_top_news, null) as LinearLayout?
        adapter.addHeaderView(topNewsLl)
    }

    private fun initData() {
        //置顶
        RetrofitHelper.getInstance().retrofit.create(GrowthHandBookApi::class.java)
                .newsTop(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<PageListBase<NewsTopEntity>>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<PageListBase<NewsTopEntity>>) {
                        topNewsLl?.removeAllViews()
                        response.data.datas.forEach { bean ->
                            var view = LayoutInflater.from(ctx).inflate(R.layout.item_top_news, null, true);
                            val tv = view.findViewById<TextView>(R.id.newsTv)
                            tv.text = bean.title
                            view.setOnClickListener {
                                val newsUrl = if (TextUtils.isEmpty(bean.linkUrl)) {
                                    C.URL_INFORMATIONDETAILURL + "?id="+bean.newsId +"&type="+bean.itemType
                                } else {
                                    bean.linkUrl
                                }

                                //itemType条目类型 1：每日财经早报 2:资讯
                                val url = if (bean.itemType == "1") C.MORNING_NEWS_PAPER else if (bean.linkUrl.isNullOrBlank()) newsUrl else bean.linkUrl

                                val intent = Intent(ctx, WebActivityCommon::class.java)
                                intent.putExtra("url", url)
                                intent.putExtra("shareContent", ShareContent(bean.title, bean.summary, url, bean.shareIcon))
                                ctx.startActivity(intent)
                            }
                            topNewsLl?.addView(view)
                        }
                    }
                })
        //咨询热读
        RetrofitHelper.getInstance().retrofit.create(GrowthHandBookApi::class.java)
                .newsPageList(ParamesHelp()
                        .put("pageIndex", "$pageIndex")
                        .put("pageSize", "10")
                        .put("typeCode", "$typeCode")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<PageListBase<NewsPageEntity>>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<PageListBase<NewsPageEntity>>) {
                        if (pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)

                        bindLoadMoreView(response.data, adapter)
                        pageIndex++
                    }

                })
    }

    /**
     * 检查是否签到状态，更新底部tab和发现页面标记
     */
    private fun checkSignStatu() {
        if (!LoginService.getInstance().isTokenExsit) {
            refreshSignStatueTag(SignStatueEvent(false))
            return
        }
        //检查是否签到
        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                .signInfo(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SignInfoData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SignInfoData>) {
                        refreshSignStatueTag(SignStatueEvent(response.data.hasSigned))
                    }
                })

    }


    var hasNoSignIv: ImageView? = null
    private fun initTopTab1(): View {
        val view = LayoutInflater.from(ctx).inflate(R.layout.fragment_discover_top_page1, null)
        hasNoSignIv = view.findViewById<ImageView>(R.id.hasNoSignIv)
        view.findViewById<View>(R.id.tab1).setOnClickListener {
            if (!LoginService.getInstance().isTokenExsit) {
                startActivity(Intent(ctx, LoginActivity::class.java))
            } else {
                startActivity(Intent(ctx, SignInActivity::class.java))
            }
        }  //签到
        view.findViewById<View>(R.id.tab2).setOnClickListener {
            //抽奖
            MobclickAgent.onEvent(ctx, "S_8_1")
            WebActivityCommon.showThisActivity(ctx as TopBaseActivity?, C.BOUNTY, "")
        }
        view.findViewById<View>(R.id.tab3).setOnClickListener {
            //活动中心
            MobclickAgent.onEvent(ctx, "L_1_7")
            startActivity(Intent(ctx, ActivitysPartActivity::class.java))
        }
        view.findViewById<View>(R.id.tab4).setOnClickListener {
            //推广海报
            if (LoginService.getInstance().isTokenExsit) {
                MobclickAgent.onEvent(ctx, "L_1_3")
                startActivity(Intent(ctx, PopularPosterActivity::class.java))
            } else {
                startActivity(Intent(ctx, LoginActivity::class.java))
            }
        }

        return view
    }

    private fun initTopTab2(): View {
        val view = LayoutInflater.from(ctx).inflate(R.layout.fragment_discover_top_page2, null)
        view.findViewById<View>(R.id.tab1).setOnClickListener {
            //出单喜报
            if (LoginService.getInstance().isTokenExsit) {
                MobclickAgent.onEvent(ctx, "L_1_5")
                startActivity(Intent(ctx, GoodReportActivity::class.java))
            } else startActivity(Intent(ctx, LoginActivity::class.java))
        }
        view.findViewById<View>(R.id.tab2).setOnClickListener {
            //个人名片
            if (LoginService.getInstance().isTokenExsit) {
                MobclickAgent.onEvent(ctx, "L_1_2")
                startActivity(Intent(ctx, PersonbusinessCardActivity::class.java))
            } else startActivity(Intent(ctx, LoginActivity::class.java))
        }
        view.findViewById<View>(R.id.tab3).setOnClickListener {
            //职级计算器
            MobclickAgent.onEvent(ctx, "L_1_6")
            startActivity(Intent(ctx, CfgLevelCalculateActivity::class.java))
        }
        view.findViewById<View>(R.id.tab4).setOnClickListener {
            //理财公益
            MobclickAgent.onEvent(ctx, "S_1_4");
//                ShareContent shareContent = new ShareContent("大爱公益"
//                        , "貅比特携手芒果v基金，为爱前行！"
//                        , TopApp.getInstance().getHttpService().getBaseH5urlByAppkind() + C.PUBLIC_FUND
//                        , "https://image.toobei.com/0366808ad267b591aabdd83d6640a625?f=png");
            var intent = Intent(ctx, WebActivityCommon::class.java)
            intent.putExtra("url", C.PUBLIC_FUND);
            intent.putExtra("title", "公益");
            intent.putExtra("AutoRefresh", true);
//                intent.putExtra("shareContent", shareContent);
            startActivity(intent);
        }

        return view
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 刷新签到标记
    fun refreshSignStatueTag(event: SignStatueEvent) {
        hasNoSignIv?.visibility = if (event.hasSign) View.GONE else View.VISIBLE
    }

}