package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.BrandPromotionData
import com.v5ent.xiubit.entity.PopularPosterTypeData
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 推广海报
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
interface PopularPosterApi {

    /**
     * 推广海报
     *token
     * type
     */
    @FormUrlEncoded
    @POST("user/brandPosters")
    fun getPopularPoster(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BrandPromotionData>>


    /**
     * 推广海报类型
     */
    @FormUrlEncoded
    @POST("user/postersType")
    fun getPopularPosterType(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PopularPosterTypeData>>

}