package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jungly.gridpasswordview.Util
import com.toobei.common.entity.RepamentCalendarEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.RecordApi
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.PaymentRecordAdapter
import com.v5ent.xiubit.event.PaymentNumEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_red_packets.*
import org.greenrobot.eventbus.EventBus
import org.xsl781.utils.SystemTool

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class PaymentTypeFragment : FragmentBase() {
    var pageIndex = 1
    lateinit var adapter: PaymentRecordAdapter
    var repamentType : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        repamentType = arguments?.getInt("repamentType",0)?:0
        return inflater?.inflate(R.layout.fragment_payment_type, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = PaymentRecordAdapter()
        recyclerView.adapter = adapter

        adapter.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            getNetData()
        }, recyclerView)

        val height = MyApp.displayHeight - SystemTool.getStatusBarHeight(ctx)- Util.dp2px(ctx, 44) //顶部标题栏

        var emptyView = EmptyView(ctx).setHeight(height).showEmpty(R.drawable.img_no_data_blank,
                if (repamentType == 0) "当前没有待回款" else "当前没有已回款")
        adapter.emptyView = emptyView
    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(RecordApi::class.java)
                .repamentCalendar(ParamesHelp()
                        .put("pageIndex","$pageIndex")
                        .put("pageSize","20")
                        .put("repamentType","$repamentType") //0-待回款 1-已回款
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<RepamentCalendarEntity>() {
                    override fun bindViewWithDate(response: RepamentCalendarEntity) {
                        if (response.data.pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)
                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                        EventBus.getDefault().post(PaymentNumEvent(repamentType,response.data.totalCount))
                    }

                })
    }

    fun refreshData(){
        pageIndex = 1
        getNetData()
    }

}