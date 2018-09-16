package com.v5ent.xiubit.activity

import android.os.Bundle
import android.view.View
import com.toobei.common.entity.CouponCountEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CouponApi
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.CouponPageAdapter
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_coupon.*

class CouponActivity : NetBaseActivity() {
    lateinit var adapter: CouponPageAdapter
    var defIndex: Int = 0
    var title = arrayOf("红包", "职级券", "加佣券")

    override fun getContentLayout(): Int {
        return R.layout.activity_coupon
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        defIndex = intent.getIntExtra("type", 0)
        super.onCreate(savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {
        headerLayout.showTitle("我的优惠券")
        headerLayout.showRightTextButton("使用说明", View.OnClickListener {
            WebActivityCommon.showThisActivity(ctx, C.COUPON_USE_INTRODUCE,"")
        })

        adapter = CouponPageAdapter(ctx, supportFragmentManager)
        vPager.adapter = adapter
        vPager.offscreenPageLimit = 2

        tabLayout.setViewPager(vPager, title)
        tabLayout.currentTab = defIndex

//        TextDecorator.decorate(tabLayout.getTitleView(0),"${title[0]}(89)")
//                .setAbsoluteSize(13,true,"(89)").build()

    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(CouponApi::class.java)
                .queryCouponCount(ParamesHelp()
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CouponCountEntity>() {
                    override fun bindViewWithDate(response: CouponCountEntity) {
                        TextDecorator.decorate(tabLayout.getTitleView(0), "${title[0]}(${response.data.useableRedPacketCount ?: 0})")
                                .setAbsoluteSize(13, true, "(${response.data.useableRedPacketCount ?: 0})").build()

                        TextDecorator.decorate(tabLayout.getTitleView(1), "${title[1]}(${response.data.useableJobGradeCouponCount ?: 0})")
                                .setAbsoluteSize(13, true, "(${response.data.useableJobGradeCouponCount ?: 0})").build()

                        TextDecorator.decorate(tabLayout.getTitleView(2), "${title[2]}(${response.data.useablePersonAddfeeTicketCount ?: 0})")
                                .setAbsoluteSize(13, true, "(${response.data.useablePersonAddfeeTicketCount ?: 0})").build()
                    }
                })
    }
}
