package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.HomeCfpBuyInfoData
import com.v5ent.xiubit.entity.LcdsAchievementData
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
interface LcdsDataApi {

    /**
     * 貅比特平台业绩--4.3.0
     * token		string	必填
     * userId		string	必填
     */
    @FormUrlEncoded
    @POST("homepage/lcs/achievement/4.3.0")
    fun getLcdsAchievement(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<LcdsAchievementData>>


    /**
     * 首页理财师发放佣金累计和出单-4.0.0
     * 接口名称 首页理财师发放佣金累计和出单-4.0.0
     * 请求类型 get
     * 请求Url  /homepage/cfp/sysInfo/4.0.0
     * 接口描述 出单按时间排序最近两个月200单
     */
    @FormUrlEncoded
    @POST("homepage/cfp/sysInfo/4.0.0")
    fun cfpBuyInfostatistics(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<HomeCfpBuyInfoData>>

}