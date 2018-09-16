package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.QueryRedPacketData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.RecommendToCfgAdapter
import com.v5ent.xiubit.entity.CfgListData
import com.v5ent.xiubit.entity.CfgListDatasDataEntity
import com.v5ent.xiubit.network.httpapi.RecommendApi
import com.v5ent.xiubit.service.CacheJsonLocalManager
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.EmptyView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_recommend_to_cfg.*
import org.xsl781.ui.view.EnLetterView

/**
 * 派送红包
 */
class RecommendToCfgActivity : NetBaseActivity() {
    lateinit var adapter: RecommendToCfgAdapter
    private var searchStr: String? = ""
    private var mCheckedDatas: List<CfgListData>? = null
    lateinit var redPacket: QueryRedPacketData
    var productOrgId: String? = null
    var recommendType: Int? = 0
    var cfgList = ArrayList<CfgListData>()
    private var isAllChecked = false

    override fun getContentLayout(): Int {
        return R.layout.activity_recommend_to_cfg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        recommendType = intent.getIntExtra("recommendType", 0)
        productOrgId = intent.getStringExtra("productOrgId")
        super.onCreate(savedInstanceState)
        initView()
        getMemberList(false)
    }

    private fun initView() {
        headerLayout.showTitle("我的直推理财师")
        headerLayout.showRightTextButton("全选", {
            isAllChecked = !isAllChecked
            adapter?.checkedAll(isAllChecked)

        })

        refreshViewSrl.setOnRefreshListener {
            sendBtn.text = "确定(0)"
            getMemberList(true)
            refreshViewSrl.finishRefresh()
        }

        recyclerView.layoutManager = LinearLayoutManager(ctx)
        adapter = RecommendToCfgAdapter()
        recyclerView.adapter = adapter

        adapter.onCheckedListener = object : RecommendToCfgAdapter.OnCheckedListener {
            override fun onCheckedChange(checkedNum: Int) {
                sendBtn.text = "确定($checkedNum)"
                sendBtn.isEnabled = checkedNum > 0
            }

        }

        adapter.emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank, "您暂时还没有直推理财师")

        editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().isNullOrBlank()) {
                    searchStr = s.toString()
                    if (cfgList.isNotEmpty()) {

                        adapter.setNewData(cfgList.filter {
                            it.userName.contains(searchStr ?: "") || it.mobile.contains(searchStr ?: "")
                        }.toMutableList())
                    }
                } else {
                    adapter.setNewData(cfgList)
                }
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        //派发
        sendBtn.setOnClickListener {
            fun formatUserIds(list: List<CfgListData>): String {
                var str = StringBuilder()
                list.forEach {
                    str.append("${it.userId},")
                }
                return str.deleteCharAt(str.length - 1).toString()
            }

            val userIdsStr = formatUserIds(adapter?.getCheckedData())
            recommendToCfg(userIdsStr)
        }

        setupUIListenerAndCloseKeyboard(refreshViewSrl)
        letterBarEv.setTextView(textDialogTv)
        letterBarEv.setOnTouchingLetterChangedListener(object : EnLetterView.OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(s: String?) {
                adapter.data.forEachIndexed { index, cfgListData ->
                    if (cfgListData.firstLetter == s) recyclerView.scrollToPosition(index)
                }
            }

        })

    }


    private fun recommendToCfg(userIdsStr: String) {
        RetrofitHelper.getInstance().retrofit.create(RecommendApi::class.java)
                .recommendProductOrg(ParamesHelp()
                        .put("idType", "${if (recommendType == C.RECOMMEND_TYPE_PRODUCT) 1 else 2}")
                        .put("type", "1")  //1=我的直推理财师 2 =我的客户
                        .put("productOrgId", productOrgId)
                        .put("userId", userIdsStr)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<Any>>() {

                    override fun onNext(response: BaseResponseEntity<Any>) {
                        noMsgToast = true
                        if (response.code == "0") {
                            ToastUtil.showCustomToast("${if (recommendType == C.RECOMMEND_TYPE_PRODUCT) "产品" else "平台"}已成功推荐给理财师")
                        } else {
                            ToastUtil.showCustomToast("推荐失败，请重试")
                        }
                        super.onNext(response)

                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<Any>) {

                    }

                })
    }

    private fun getMemberList(isRefresh: Boolean) {
        CacheJsonLocalManager.getAllCfgList(object : CacheJsonLocalManager.CallBack<CfgListDatasDataEntity> {
            override fun onNext(data: CfgListDatasDataEntity) {
                if (data != null) {
                    cfgList.clear()
                    cfgList.addAll(data.data?.datas ?: ArrayList<CfgListData>())
                    cfgList?.sortWith(Comparator { o1, o2 ->
                        if (o1.firstLetter == "#") 1
                        else o1.firstLetter.compareTo(o2.firstLetter)
                    })
                    adapter.setNewData(cfgList)
                }
            }
        }, isRefresh)

    }

}