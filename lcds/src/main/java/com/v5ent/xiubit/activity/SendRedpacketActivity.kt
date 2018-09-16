package com.v5ent.xiubit.activity

import android.os.Bundle
import android.view.View
import com.toobei.common.entity.QueryRedPacketData
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.SendRedpacketPageAdapter
import kotlinx.android.synthetic.main.activity_send_redpacket.*

/**
 * 派送红包
 */
class SendRedpacketActivity : NetBaseActivity() {
    lateinit var adapter: SendRedpacketPageAdapter
    lateinit var redPacket: QueryRedPacketData
    var defIndex: Int = 0
    var title = arrayOf("理财师", "客户")

    override fun getContentLayout(): Int {
        return R.layout.activity_send_redpacket
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        defIndex = intent.getIntExtra("type", 0)
        redPacket = intent.getSerializableExtra("redPacket") as QueryRedPacketData
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        headerLayout.visibility = View.GONE
        setHeadViewCoverStateBar(statusBar)

        adapter = SendRedpacketPageAdapter(ctx, supportFragmentManager)
        vPager.adapter = adapter

        tabLayout.setViewPager(vPager, title)
        tabLayout.currentTab = defIndex

        backIv.setOnClickListener {
            finish()
        }

    }

}