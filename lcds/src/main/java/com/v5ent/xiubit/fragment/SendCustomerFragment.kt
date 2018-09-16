package com.v5ent.xiubit.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.Custom
import com.toobei.common.entity.QueryRedPacketData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.SendRedpacketActivity
import com.v5ent.xiubit.data.recylerapter.SendCustomerAdapter
import com.v5ent.xiubit.entity.CustomerListDatasDataEntity
import com.v5ent.xiubit.event.RedPackageSendSucessEvent
import com.v5ent.xiubit.network.httpapi.SendRepacketApi
import com.v5ent.xiubit.service.CacheJsonLocalManager
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_send_customer.*
import org.greenrobot.eventbus.EventBus

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/25
 */
class SendCustomerFragment : FragmentBase() {
    lateinit var adapter: SendCustomerAdapter
    private var searchStr: String? = ""
    private var checkedData: Custom? = null
    private var customerList  = ArrayList<Custom>()
    lateinit var mActivity: SendRedpacketActivity
    lateinit var redPacket: QueryRedPacketData


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_send_customer, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = ctx as SendRedpacketActivity
        redPacket = mActivity.redPacket
        initView()
        getMemberList(false)

    }

    private fun initView() {
        refreshViewSrl.setOnRefreshListener {
            getMemberList(true)
            refreshViewSrl.finishRefresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = SendCustomerAdapter()
        recyclerView.adapter = adapter

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().isNullOrBlank()) {
                    searchStr = s.toString()
                    if (customerList.isNotEmpty()) {

                        adapter.setNewData(customerList.filter {
                            it.userName.contains(searchStr ?: "") || it.mobile.contains(searchStr?:"")
                        }.toMutableList())
                    }
                }else {
                    adapter.setNewData(customerList)
                }
            }

            override fun afterTextChanged(s: Editable?) {


            }

        })

        sendTv.setOnClickListener {
            checkedData = adapter.getCheckedData()
            if (checkedData == null) ToastUtil.showCustomToast("请选择客户")
            else sendRedpacket()
        }

        setupUIListenerAndCloseKeyboard(refreshViewSrl)

    }



    private fun getMemberList(isRefresh: Boolean) {
        CacheJsonLocalManager.getAllCustomerList(object : CacheJsonLocalManager.CallBack<CustomerListDatasDataEntity>{
            override fun onNext(data: CustomerListDatasDataEntity) {
                if (data != null) {
                    customerList.clear()
                    customerList.addAll(data.data.datas)
                    customerList?.sortWith(Comparator { o1, o2 ->
                        if (o1.firstLetter == "#") 1
                        else o1.firstLetter.compareTo(o2.firstLetter)
                    })

                    adapter.setNewData(customerList)
                }
            }
        },isRefresh)


    }


    private fun sendRedpacket() {
        RetrofitHelper.getInstance().retrofit.create(SendRepacketApi::class.java)
                .redpaperSendRedpaper(ParamesHelp()
                        .put("rid", redPacket?.rid)
                        .put("userMobiles", checkedData?.mobile)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<Any>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<Any>) {
                    }

                    override fun onNext(response: BaseResponseEntity<Any>) {
                        noMsgToast = true
                        super.onNext(response)
                        if (response.code == "0") {
                            ToastUtil.showCustomToast("派发成功")
                            EventBus.getDefault().post(RedPackageSendSucessEvent())
                            mActivity?.finish()
                        }else{
                            ToastUtil.showCustomToast("派发失败，请重新派发")
                        }
                    }

                })
    }




}