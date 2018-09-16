package com.v5ent.xiubit.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.ProductDatasData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.ProductApi
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.recylerapter.ProductListRecycleAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.v5ent.xiubit.view.EmptyView
import com.v5ent.xiubit.view.ProductListSortHeadLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_class.*

/**
 * Created by yangLin on 2018/4/10.
 */
class ProductClassActivity : NetBaseActivity() {
    private var cateId = "0"
    private var order = "1"  //顺序	number	0-升序 1-降序
    private var sort = "1" //1-年化收益 2-产品期限
    private var pageIndex = 1
    private var pageSize = 20
    private lateinit var adapter : ProductListRecycleAdapter
    override fun getContentLayout(): Int = R.layout.activity_product_class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cateId = intent.getStringExtra("cateId") ?: "0"
        initView()
        initData()
    }

    private fun initView() {
//        headerLayout.setWhiteTheme()
        headerLayout.showTitle(when (cateId) {
            "0" -> "全部产品"
            "1" -> "短期产品(3个月以内)"
            "2" -> "中期产品(4-6个月)"
            "3" -> "长期产品(7个月以上)"
            else -> ""

        })

        headSorTab.hideFilterBtn()
        headSorTab.initHeadView(1,true)
        headSorTab.setListener(object : ProductListSortHeadLayout.OnHeadTitleClickListener  {
            override fun headTitleClicked(index: Int, isDown: Boolean) {
                pageIndex = 1
                when (index){
                    1-> {
                        order = "1"  //年化收益只做降序排序
                        sort = "1"
                        initData()
                    }
                    2 ->{
                        order = if (isDown) "1" else "0"
                        sort = "2"
                        initData()
                    }

                }

            }
        })

        refreshViewSrl.setOnRefreshListener {
            pageIndex = 1
            initData()
            refreshViewSrl.finishRefresh()
        }
        adapter = ProductListRecycleAdapter(ctx)
        productListRv.layoutManager = LinearLayoutManager(ctx)
        productListRv.adapter = adapter
        adapter.setOnLoadMoreListener({
            initData()
        },productListRv)

        ////空视图设定
        val emptyView = EmptyView(ctx).showEmpty(R.drawable.img_no_data_blank, "没有找到此类产品")
        adapter.setEmptyView(emptyView)

    }


    private fun initData() {
        RetrofitHelper.getInstance().retrofit.create(ProductApi::class.java)
                .productClassifyPageListNew(ParamesHelp()
                        .put("cateId",cateId)
                        .put("order",order)
                        .put("sort",sort)
                        .put("pageIndex","$pageIndex")
                        .put("pageSize","$pageSize")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<ProductDatasData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<ProductDatasData>) {
                        if (pageIndex == 1) adapter.setNewData(response.data.datas)
                        else adapter.addData(response.data.datas)

                        bindLoadMoreView(response.data,adapter)
                        pageIndex ++
                    }
                }
                )
    }

}