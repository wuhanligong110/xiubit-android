package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jungly.gridpasswordview.Util
import com.toobei.common.entity.CustomerCfpmemberInfoEntity
import com.toobei.common.entity.CustomerCfpmemberPageEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.CustomerAndCfgMemberApi
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.CfgMemberListAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_cfg_member.*
import org.xsl781.utils.SystemTool

class CfgMemberActivity : NetBaseActivity() {


    private lateinit var phoneAddressEntry: View
    private lateinit var noInvestCfgEntry: View
    private lateinit var iAttentetCfgEntry: View
    private lateinit var noInvestCfgNumTv: TextView
    private lateinit var iAttentetCfgTv: TextView
    private lateinit var myDirCfgTitleTv: TextView
    private lateinit var mySecCfgTitleTv: TextView
    private lateinit var myThirdCfgTitleTv: TextView
    private var pageIndex: Int = 1
    private var nameOrMobile: String? = ""
    private lateinit var adapter: CfgMemberListAdapter

    override fun getContentLayout(): Int {
        return R.layout.activity_cfg_member
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getNetData()
    }


    private fun initView() {
        headerLayout.showTitle("理财师团队成员")

        //head
        val headView = LayoutInflater.from(ctx).inflate(R.layout.activity_cfg_member_header, null, true)
        phoneAddressEntry = headView.findViewById(R.id.phoneAddressEntry)
        noInvestCfgEntry = headView.findViewById(R.id.noInvestCfgEntry)
        iAttentetCfgEntry = headView.findViewById(R.id.iAttentetCfgEntry)

        myDirCfgTitleTv = headView.findViewById<TextView>(R.id.myDirCfgTitleTv)
        mySecCfgTitleTv = headView.findViewById<TextView>(R.id.mySecCfgTitleTv)
        myThirdCfgTitleTv = headView.findViewById<TextView>(R.id.myThirdCfgTitleTv)

        noInvestCfgNumTv = headView.findViewById<TextView>(R.id.noInvestCfgNumTv)
        iAttentetCfgTv = headView.findViewById<TextView>(R.id.iAttentetCfgTv)

        cfgRv.layoutManager = LinearLayoutManager(ctx)

        adapter = CfgMemberListAdapter(CfgMemberListAdapter.TYPE_ALL)
        adapter.addHeaderView(headView)
        cfgRv.adapter = adapter

        //刷新
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }
        //加载更多
        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getMemberList()
        }, cfgRv)

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                nameOrMobile = s.toString()
                pageIndex = 1
                getMemberList()

            }

        })
        //onClick
        var intent: Intent
        //通讯录
        phoneAddressEntry.setOnClickListener {
            intent = Intent(this, InviteRecommendContacts::class.java)
            intent.putExtra("orgNumber", false)
            intent.putExtra("isRecommendCfp", true)
            startActivity(intent)
        }
        //未出单的直推理财师
        noInvestCfgEntry.setOnClickListener {
            intent = Intent(this, CfgTypeActivity::class.java)
            intent.putExtra("type", "3")
            startActivity(intent)
        }
        //我关注的直推理财师
        iAttentetCfgEntry.setOnClickListener {
            intent = Intent(this, CfgTypeActivity::class.java)
            intent.putExtra("type", "4")
            startActivity(intent)
        }

        headView.post(Runnable {
            val height = MyApp.displayHeight - headView.height - SystemTool.getStatusBarHeight(ctx) - Util.dp2px(ctx, 44) //顶部标题栏

            var emptyView = EmptyView(ctx).setHeight(height).showEmpty(R.drawable.img_no_data_blank,
                    "当前没有理财师团队成员")
            adapter.emptyView = emptyView
            adapter.setHeaderAndEmpty(true)
        })


    }


    private fun getNetData() {
        getMemeberInfo()
        getMemberList()
    }


    private fun getMemberList() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .customerCfpmemberPage(ParamesHelp()
                        .put("type", "1")
                        .put("pageIndex", "$pageIndex")
                        .put("pageSize", "20")
                        .put("nameOrMobile", nameOrMobile ?: "")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerCfpmemberPageEntity>() {
                    override fun bindViewWithDate(response: CustomerCfpmemberPageEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)

                        bindLoadMoreView(response.data, adapter)
                        pageIndex++
                    }

                })
    }

    private fun getMemeberInfo() {
        RetrofitHelper.getInstance().retrofit.create(CustomerAndCfgMemberApi::class.java)
                .customerCfpmemberInfo(ParamesHelp().put("type", "1").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerCfpmemberInfoEntity>() {
                    override fun bindViewWithDate(response: CustomerCfpmemberInfoEntity) {
                        noInvestCfgNumTv.text = "(${response.data.noInvest}人)"
                        iAttentetCfgTv.text = "(${response.data.myAttention}人)"
                        myDirCfgTitleTv.text = "直推理财师 (${response.data.directRecomNum}人)"

                        TextDecorator.decorate(mySecCfgTitleTv, "二级(${response.data.secondLevelNum}人)")
                                .setTextColor(R.color.text_black_common, "${response.data.secondLevelNum}")
                                .build()

                        TextDecorator.decorate(myThirdCfgTitleTv, "三级(${response.data.threeLevelNum}人)")
                                .setTextColor(R.color.text_black_common, "${response.data.threeLevelNum}")
                                .build()
                    }

                })
    }
}
