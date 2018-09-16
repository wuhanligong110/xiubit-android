package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 关注和取消关注
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface AttientApi {

    /**
     * 理财师添加关注
     * token		string	必填
     * userId		string	必填
     */
    @FormUrlEncoded
    @POST("crm/cfpcommon/important/add")
    fun addCfgAttient(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 理财师取消关注
     * token		string	必填
     * userId		string	必填
     */
    @FormUrlEncoded
    @POST("crm/cfpcommon/important/remove")
    fun removeCfgAttient(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 客户添加关注
     * token		string	必填
     * userId		string	必填
     */
    @FormUrlEncoded
    @POST("customer/important/add")
    fun addCustomerAttient(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 客户取消关注
     * token		string	必填
     * userId		string	必填
     */
    @FormUrlEncoded
    @POST("customer/important/remove")
    fun removeCustomerAttient(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>
}