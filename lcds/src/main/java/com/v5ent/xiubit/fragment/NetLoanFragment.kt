package com.v5ent.xiubit.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.ProductClassifyStatisticsDatasData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.ProductApi
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.CommonFragmentActivity
import com.v5ent.xiubit.activity.ProductClassActivity
import com.v5ent.xiubit.activity.WebActivityCommon
import com.v5ent.xiubit.data.OrgInfoAdapter
import com.v5ent.xiubit.entity.OrgInfoDatasData
import com.v5ent.xiubit.network.httpapi.OrgInfoApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.NestedScrollGridLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_netloan.*
import java.text.DecimalFormat

/**
 * Created by yangLin on 2018/4/4.
 */
class NetLoanFragment : FragmentBase(), View.OnClickListener {
    lateinit var adapter : OrgInfoAdapter
    var pageIndex = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_netloan, null)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
//        loadData()
    }


    override fun refreshAfterLogin() {
        super.refreshAfterLogin()
        refreshData()
    }

    //在页面显示出来时刷新平台列表，避免售罄标识不实时的问题
    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) refreshData()
    }

    fun refreshData(){
        pageIndex = 1
        loadData()
    }



    private fun loadData() {
        getOrgList()


        RetrofitHelper.getInstance().retrofit.create(ProductApi::class.java)
                .productClassifyStatisticsNew(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver< BaseResponseEntity<ProductClassifyStatisticsDatasData>>() {
                    override fun bindViewWithDate(response:  BaseResponseEntity<ProductClassifyStatisticsDatasData>) {
                        var cateList = arrayListOf<String>()
                        response.data.datas.forEach {
                            val nf = DecimalFormat("#.##")
                            var str = nf.format(it.flowMinRateStatistics.toDouble()) + "%~" + nf.format(it.flowMaxRateStatistics.toDouble())+ "%"
                            when (it.cateId ){
                                //1-短期(3个月以内) 2-中期(4-6个月) 3-长期(7个月以上)
                                "1" -> shortRateTv.text = str
                                "2" -> midRateTv.text = str
                                "3" -> longRateTv.text = str
                            }
                            cateList.add(it.cateId )
                        }

                        shortProductLl.visibility = if (cateList.contains("1")) View.VISIBLE else View.GONE
                        midProductLl.visibility = if (cateList.contains("2")) View.VISIBLE else View.GONE
                        longProductLl.visibility = if (cateList.contains("3")) View.VISIBLE else View.GONE
                    }
                }
                )
    }

    private fun getOrgList() {
        RetrofitHelper.getInstance().retrofit.create(OrgInfoApi::class.java)
                .getOrgList(ParamesHelp().put("pageIndex", "$pageIndex").put("pageSize", "100").build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<OrgInfoDatasData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<OrgInfoDatasData>) {
                        if (response.data.pageIndex == 1) {
                            adapter.setNewData(response.data.datas)
                        } else {
                            adapter.addData(response.data.datas)
                        }

                        bindLoadMoreView(response.data, adapter, true)
                        pageIndex++;
                    }
                }
                )
    }


    private fun initView() {
        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            loadData()
            refreshViewSrl.finishRefresh()
        }

        orgRv.layoutManager = NestedScrollGridLayoutManager(ctx,2)
        adapter = OrgInfoAdapter(ctx)
        adapter.setOnLoadMoreListener({
            loadData()
        },orgRv)
        orgRv.adapter = adapter


        shortProductLl.setOnClickListener(this)
        midProductLl.setOnClickListener(this)
        longProductLl.setOnClickListener(this)
        allProductLl.setOnClickListener(this)
        safeProdectTv.setOnClickListener(this)
        bottomTabLl.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        var intent: Intent? = null;
        when (v?.id) {
            R.id.shortProductLl -> {
                intent = Intent(ctx, ProductClassActivity::class.java)
                intent.putExtra("cateId","1")
                startActivity(intent)
            }

            R.id.midProductLl -> {
                intent = Intent(ctx, ProductClassActivity::class.java)
                intent.putExtra("cateId","2")
                startActivity(intent)
            }

            R.id.longProductLl -> {
                intent = Intent(ctx, ProductClassActivity::class.java)
                intent.putExtra("cateId","3")
                startActivity(intent)
            }

            R.id.allProductLl -> {
                intent = Intent(ctx, CommonFragmentActivity::class.java)
                intent.putExtra(C.FragmentTag.KEY_TAG,C.FragmentTag.PRODUCT_LIST)
                startActivity(intent)
            }
            R.id.bottomTabLl ,
            R.id.safeProdectTv -> { //安全保障
                intent = Intent(ctx, WebActivityCommon::class.java)
                intent.putExtra("isRedirectUsable", false)
                intent.putExtra("url", C.SAFEPROTECT)
                startActivity(intent)
            }


        }
    }

}