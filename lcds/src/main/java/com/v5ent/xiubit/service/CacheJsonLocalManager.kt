package com.v5ent.xiubit.service

import com.toobei.common.network.NetworkObserver
import com.toobei.common.utils.CacheUtils
import com.v5ent.xiubit.entity.CfgListDatasDataEntity
import com.v5ent.xiubit.entity.CustomerListDatasDataEntity
import com.v5ent.xiubit.network.httpapi.SendRepacketApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hasee-pc on 2017/11/15.
 */
object CacheJsonLocalManager {

    /**
     * 理财师列表 ：派发页面，推荐页面
     */
    fun saveAllCfgList(data: CfgListDatasDataEntity? = null,callback : CallBack<CfgListDatasDataEntity>? = null): CacheJsonLocalManager {
        if (data != null) {
            CacheUtils.getInstance().put(C.CacheKey.ALL_CFG_MEMBER, data)
            return this
        }

        RetrofitHelper.getInstance().retrofit.create(SendRepacketApi::class.java)
                .cfplannerMemberPage(ParamesHelp()
                        .put("pageIndex", "1")
                        .put("pageSize", "10000")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CfgListDatasDataEntity>() {
                    override fun bindViewWithDate(response: CfgListDatasDataEntity) {
                        callback?.onNext(response)
                        CacheUtils.getInstance().put(C.CacheKey.ALL_CFG_MEMBER, response)
                    }

                })
        return this
    }

    /**
     * @param callback 数据获取回调
     * @param isRefresh 是否刷新数据。true 从网络获取 fale 从本地缓存读取，没有再从网络获取
     */
    fun getAllCfgList(callback : CallBack<CfgListDatasDataEntity>,isRefresh : Boolean) {
        var data : CfgListDatasDataEntity? = null
        if (!isRefresh) data =  CacheUtils.getInstance().getSerializable(C.CacheKey.ALL_CFG_MEMBER) as? CfgListDatasDataEntity
        if (data != null) callback.onNext(data)
        else saveAllCfgList(null,callback)
    }

    /**
     * 客户列表 ：派发页面，客户页面
     */
    fun saveAllCustomerList(data : CustomerListDatasDataEntity? = null,callback : CallBack<CustomerListDatasDataEntity>? = null): CacheJsonLocalManager {
        if (data != null) {
            CacheUtils.getInstance().put(C.CacheKey.ALL_CUSTOMER_MEMBER, data)
            return this
        }
        RetrofitHelper.getInstance().retrofit.create(SendRepacketApi::class.java)
                .customerList(ParamesHelp()
                        .put("customerType", "")
                        .put("order", "")
                        .put("pageIndex", "1")
                        .put("sort", "2")
                        .put("pageSize", "10000")
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<CustomerListDatasDataEntity>() {
                    override fun bindViewWithDate(response: CustomerListDatasDataEntity) {
                        callback?.onNext(response)
                        CacheUtils.getInstance().put(C.CacheKey.ALL_CUSTOMER_MEMBER, response)
                    }

                })
        return this
    }


    fun getAllCustomerList(callback : CallBack<CustomerListDatasDataEntity>,isRefresh : Boolean){
        var data :CustomerListDatasDataEntity?  = null
        if (!isRefresh) data = CacheUtils.getInstance().getSerializable(C.CacheKey.ALL_CUSTOMER_MEMBER) as? CustomerListDatasDataEntity
        if (data != null) callback.onNext(data)
        else saveAllCustomerList(null,callback)
    }

    interface CallBack<T>{
        fun onNext(data : T)
    }

}