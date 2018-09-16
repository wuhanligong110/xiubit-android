package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.JobGradeData
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 职级相关
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface JobGradeApi {


    /**
     *职级特权
     * token
     */
    @FormUrlEncoded
    @POST("personcenter/partner/jobGrade")
    fun jobGrade(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<JobGradeData>>

    /**
     * 4.5.0我的-理财师团队成员详情
     * token
     * userId	当前登录用户的直接下级理财师ID
     */
    @FormUrlEncoded
    @POST("personcenter/directCfpJobGrade")
    fun directCfpJobGrade(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<JobGradeData>>


}