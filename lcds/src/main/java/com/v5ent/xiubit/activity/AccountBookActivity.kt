package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jungly.gridpasswordview.Util
import com.toobei.common.entity.AccountbookInvestingListEntity
import com.toobei.common.entity.AccountbookStatisticsEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.AccountbookApi
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.AccountBookAdapter
import com.v5ent.xiubit.event.AccountBookEditEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_account_book.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.xsl781.utils.SystemTool

class AccountBookActivity : NetBaseActivity() {


    lateinit var totalMoneyTv: TextView
    lateinit var investProfitTv: TextView
    lateinit var totalProfitTv: TextView
    lateinit var adapter: AccountBookAdapter
    var pageIndex = 1
    override fun getContentLayout(): Int {
        return R.layout.activity_account_book
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {
        headerLayout.showTitle("记账本")
        headerLayout.showRightImageButton(R.drawable.ic_account_bookadd, View.OnClickListener {
            startActivity(Intent(ctx,AccountBookEditActivity::class.java))
        })

        val headView = layoutInflater.inflate(R.layout.activity_account_book_header, null)
        totalMoneyTv = headView.findViewById(R.id.totalMoneyTv) as TextView
        investProfitTv = headView.findViewById(R.id.investProfitTv) as TextView
        totalProfitTv = headView.findViewById(R.id.totalProfitTv) as TextView

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            getNetData()
            refreshViewSrl.finishRefresh()
        }

        bookRv.layoutManager = LinearLayoutManager(ctx)
        adapter = AccountBookAdapter()
        adapter.addHeaderView(headView)
        bookRv.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getBookList()
        }, bookRv)

        headView.post(Runnable {
            val height = MyApp.displayHeight - headView.height - SystemTool.getStatusBarHeight(ctx)- Util.dp2px(ctx, 44) //顶部标题栏

            var emptyView = EmptyView(ctx).setHeight(height).showEmpty(R.drawable.img_no_data_blank,
                    "暂无在投项目")
            adapter.emptyView = emptyView
            adapter.setHeaderAndEmpty(true)
        })

    }

    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(AccountbookApi::class.java)
                .accountbookStatistics(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<AccountbookStatisticsEntity>() {
                    override fun bindViewWithDate(response: AccountbookStatisticsEntity) {
                        totalMoneyTv.text = response.data.investTotalAmt ?: "--"
                        investProfitTv.text = response.data.investTotalProfit ?: "--"
                        totalProfitTv.text = response.data.investTotal ?: "--"
                    }

                })
        getBookList()
    }

    private fun getBookList() {
        RetrofitHelper.getInstance().retrofit.create(AccountbookApi::class.java)
                .accountbookInvestingList(ParamesHelp()
                        .put("pageIndex", "$pageIndex")
                        .put("pageSize", "20")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<AccountbookInvestingListEntity>() {
                    override fun bindViewWithDate(response: AccountbookInvestingListEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)
                        bindLoadMoreView(response.data, adapter)
                        pageIndex++
                    }

                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshEvent(event: AccountBookEditEvent) {
        pageIndex = 1
        getNetData()
    }
}
