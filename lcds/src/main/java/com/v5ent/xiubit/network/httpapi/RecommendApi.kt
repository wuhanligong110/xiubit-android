package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 关推荐产品，机构给理财师或者客户
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface RecommendApi {

    /**
     * 4.5.1我的-推荐
     * idType	1=产品ID 2 =机构ID	string	非空
     * productOrgId	产品或机构ID	string	非空
     * token		string
     * type	1=我的直推理财师 2 =我的客户	string	非空
     * userId	推荐给直接理财师或客户的userId	string	非空
     */
    @FormUrlEncoded
    @POST("personcenter/recomProductOrg")
    fun recommendProductOrg(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<Any>>


}