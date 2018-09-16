package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.OrgInfoDatasData
import com.v5ent.xiubit.entity.OrgThirdDataDetail
import com.v5ent.xiubit.entity.PlatformDetail
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
interface OrgInfoApi {

    /**
     * 平台详情
     */
    @FormUrlEncoded
    @POST("platfrom/detail")
    fun getPlatfromDetial(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PlatformDetail>>

    /**
     * 平台列表
     * "pageIndex", pageIndex
     *"pageSize", pageSize;
     */
    @FormUrlEncoded
    @POST("platfrom/orgPageList/4.0")
    fun getOrgList(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<OrgInfoDatasData>>

    /**
     * 接口名称 4.6.0 第三方机构数据详情
     * 请求类型 get
     * 请求Url  api/platfrom/orgThirdDataDetail
     * orgNo	平台编码	string
     * token	登录令牌	string
     */
    @FormUrlEncoded
    @POST("platfrom/orgThirdDataDetail")
    fun orgThirdDataDetail(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<OrgThirdDataDetail>>



}