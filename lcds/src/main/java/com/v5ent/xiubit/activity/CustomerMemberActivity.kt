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
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.CustomerMemberListAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_team_member.*
import org.xsl781.utils.SystemTool

class CustomerMemberActivity : NetBaseActivity() {
    private lateinit var phoneAddressEntry: View
    private lateinit var noInvestCustomerEntry: View
    private lateinit var iAttentetCustomerEntry: View
    private lateinit var myCustomerTitleTv: TextView
    private lateinit var noInvestCustomerNumTv: TextView
    private lateinit var iAttentetCustomerTv: TextView
    private var pageIndex: Int = 1
    private var nameOrMobile: String? = ""
    private lateinit var adapter: CustomerMemberListAdapter


    override fun getContentLayout(): Int {
        return R.layout.activity_team_member
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getNetData()
    }


    private fun initView() {
        headerLayout.showTitle("客户成员")

        //head
        val headView = LayoutInflater.from(ctx).inflate(R.layout.activity_team_member_header, null, true)
        phoneAddressEntry = headView.findViewById(R.id.phoneAddressEntry)
        noInvestCustomerEntry = headView.findViewById(R.id.noInvestCustomerEntry)
        iAttentetCustomerEntry = headView.findViewById(R.id.iAttentetCustomerEntry)
        myCustomerTitleTv = headView.findViewById<TextView>(R.id.myCustomerTitleTv)
        noInvestCustomerNumTv = headView.findViewById<TextView>(R.id.noInvestCustomerNumTv)
        iAttentetCustomerTv = headView.findViewById<TextView>(R.id.iAttentetCustomerTv)

        customerRv.layoutManager = LinearLayoutManager(ctx)

        adapter = CustomerMemberListAdapter(CustomerMemberListAdapter.TYPE_ALL)
        adapter.addHeaderView(headView)
        customerRv.adapter = adapter

        //刷新
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }
        //加载更多
        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getMemberList()
        }, customerRv)

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
            intent.putExtra("isRecommendCfp", false)
            startActivity(intent)
        }
        //未投资客户
        noInvestCustomerEntry.setOnClickListener {
            intent = Intent(this, CustomersTypeActivity::class.java)
            intent.putExtra("type", "1")
            startActivity(intent)
        }
        //我关注客户
        iAttentetCustomerEntry.setOnClickListener {
            intent = Intent(this, CustomersTypeActivity::class.java)
            intent.putExtra("type", "2")
            startActivity(intent)
        }

        headView.post(Runnable {
            val height = MyApp.displayHeight - headView.height - SystemTool.getStatusBarHeight(ctx) - Util.dp2px(ctx, 44) //顶部标题栏

            var emptyView = EmptyView(ctx).setHeight(height).showEmpty(R.drawable.img_no_data_blank,
                    "当前没有理财师成员")
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
                        .put("type", "2")
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
                .customerCfpmemberInfo(ParamesHelp().put("type", "2").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerCfpmemberInfoEntity>() {
                    override fun bindViewWithDate(response: CustomerCfpmemberInfoEntity) {
                        noInvestCustomerNumTv.text = "(${response.data.noInvest}人)"
                        iAttentetCustomerTv.text = "(${response.data.myAttention}人)"
                        myCustomerTitleTv.text = "我的客户 (${response.data.myCustomerNum}人)"
                    }

                })
    }
}
