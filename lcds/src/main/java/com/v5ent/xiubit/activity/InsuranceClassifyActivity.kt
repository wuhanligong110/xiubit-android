package com.v5ent.xiubit.activity

import android.os.Bundle
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.InsuranceCategoryEntiy
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.ThirdPartApi
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.data.viewpageadapter.InsuranceClassifyPageAdapter
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.way.util.DisplayUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.insurance_classify_activity.*

/**
 * Created by hasee-pc on 2017/12/26.
 */
class InsuranceClassifyActivity : NetBaseActivity() {
    private var initType = "0" //初始显示类型
    companion object {
        var categoryList = arrayListOf<InsuranceCategoryEntiy.InsuranceCategoryBean>()

        fun initCategoryList(callBack: CallBack? = null) {
            RetrofitHelper.getInstance().retrofit.create(ThirdPartApi::class.java)
                    .insuranceCategory(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<BaseResponseEntity<InsuranceCategoryEntiy>>(){
                        override fun bindViewWithDate(response: BaseResponseEntity<InsuranceCategoryEntiy>) {
                            categoryList = response.data.datas as ArrayList<InsuranceCategoryEntiy.InsuranceCategoryBean>
                            callBack?.onSuccess()
                        }

                        override fun onNetSystemException(error: String?) {
                            super.onNetSystemException(error)
                            callBack?.onError()
                        }
                    })

        }

        interface CallBack{
            fun onSuccess()
            fun onError()
        }
    }

    var mAdapter: InsuranceClassifyPageAdapter? = null
    override fun getContentLayout(): Int = R.layout.insurance_classify_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initType = intent.getStringExtra("type")?:"0"
        initView()
    }


    private fun initView() {
        headerLayout.showTitle("保险产品")

        fun initVpage(){
            var tabWidth = (MyApp.displayWidth - DisplayUtil.dip2px(ctx, 30f)).toFloat() / 5
            tabLayout.tabWidth = DisplayUtil.px2dip(ctx, tabWidth).toFloat()

            mAdapter = InsuranceClassifyPageAdapter(ctx, supportFragmentManager, categoryList)
            vPager.adapter = mAdapter
            tabLayout.setViewPager(vPager)

            categoryList.forEachIndexed{ index,it ->
                if (it.equals(initType)) vPager.currentItem = index
            }
            categoryList.forEachIndexed {index,it ->
                if (it.category == initType) vPager.currentItem = index
            }
        }

        if (categoryList != null && categoryList.size >0) initVpage()
        else initCategoryList(object : CallBack{
            override fun onSuccess() {
                initVpage()
            }

            override fun onError() {
                showLoadFail()
            }
        })


    }


    override fun onLoadFailRefresh() {
        super.onLoadFailRefresh()
        initView()
    }


}