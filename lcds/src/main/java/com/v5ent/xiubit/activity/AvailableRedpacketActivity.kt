package com.v5ent.xiubit.activity

import android.os.Bundle
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.AvailableCouponPageAdapter
import com.v5ent.xiubit.util.C
import kotlinx.android.synthetic.main.available_coupon_activity.*

/**
 * Created by yangLin on 2018/4/17.
 * 可用优惠券
 */
class AvailableRedpacketActivity : NetBaseActivity() {
    lateinit var adapter: AvailableCouponPageAdapter
    lateinit var redPacketNum: String
    lateinit var addFeeNum: String
    lateinit var patform: String
    lateinit var productId: String
    lateinit var type: String
    lateinit var bundle: Bundle
    var defIndex: Int = 0
    var title = arrayOf("红包","加佣券")

    override fun getContentLayout(): Int = R.layout.available_coupon_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defIndex = intent.getIntExtra("index", 0)
        redPacketNum = intent.getStringExtra("redPacketNum")?:"0"
        addFeeNum = intent.getStringExtra("addFeeNum")?:"0"
        bundle = Bundle()
        bundle.putString("deadline",intent.getStringExtra("deadline")?:"")
        bundle.putString("model",intent.getStringExtra("model")?:"")
        bundle.putString("patform",intent.getStringExtra("patform")?:"")
        bundle.putString("productId",intent.getStringExtra("productId")?:"")
        bundle.putString("type",intent.getStringExtra("type")?:"")
        initView()
    }

    fun initView(){
        headerLayout.showTitle("可用优惠券")
        headerLayout.showRightTextButton("使用说明",{
            WebActivityCommon.showThisActivity(ctx, C.AVAILABLE_REDPACKET_USE_INTRODUCE,"")
        })
        adapter = AvailableCouponPageAdapter(ctx, supportFragmentManager,bundle)
        vPager.adapter = adapter

        tabLayout.setViewPager(vPager, title)
        tabLayout.currentTab = defIndex

        //优惠券数量
        TextDecorator.decorate(tabLayout.getTitleView(0), "${title[0]}(${redPacketNum})")
                .setAbsoluteSize(13, true, "(${redPacketNum})").build()

        TextDecorator.decorate(tabLayout.getTitleView(1), "${title[1]}(${addFeeNum})")
                .setAbsoluteSize(13, true, "(${addFeeNum})").build()


    }
}