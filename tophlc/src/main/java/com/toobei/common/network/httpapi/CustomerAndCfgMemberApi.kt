package com.toobei.common.network.httpapi

import com.toobei.common.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/17
 */
interface CustomerAndCfgMemberApi {
    /**
     * 4.5.0我的-客户成员/理财师团队成员 信息
     * token		string
     * type		string	1理财师团队成员2客户成员
     */
    @FormUrlEncoded
    @POST("personcenter/customerCfpmember")
    fun customerCfpmemberInfo(@FieldMap map: Map<String, String>): Observable<CustomerCfpmemberInfoEntity>

    /**
     * 4.5.0我的-客户成员/理财师团队成员分页
     * attenInvestType	关注或未投资列表	string	1未投资客户 2我关注的客户 3未出单的直推理财师 4我关注的直推理财师
     * nameOrMobile	搜索条件	string
     * 变量名	含义	类型	备注
     * pageIndex	第几页	string
     * pageSize	页面大小	string
     * token		string
     * type		string	非空1理财师团队成员2客户成员
     */
    @FormUrlEncoded
    @POST("personcenter/customerCfpmemberPage")
    fun customerCfpmemberPage(@FieldMap map: Map<String, String>): Observable<CustomerCfpmemberPageEntity>

    /**
     * 4.5.0我的-客户成员详情
     * userId
     */
    @FormUrlEncoded
    @POST("personcenter/customerDetail")
    fun customerMemberDetail(@FieldMap map: Map<String, String>): Observable<CustomerMemberDetailEntity>

    /**
     * 4.5.0我的-客户成员详情-投资记录
     * userId
     */
    @FormUrlEncoded
    @POST("personcenter/customerInvestRecord")
    fun customerInvestRecord(@FieldMap map: Map<String, String>): Observable<CustomerInvestRecordEntity>


    /**
     * 4.5.0我的-理财师团队成员详情
     * userId
     */
    @FormUrlEncoded
    @POST("personcenter/cfplannerDetail")
    fun cfgMemberDetail(@FieldMap map: Map<String, String>): Observable<CfgMemberDetailEntity>


}