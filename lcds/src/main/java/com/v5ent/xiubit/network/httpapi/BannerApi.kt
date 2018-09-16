package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.HomePagerBanners
import com.toobei.common.entity.PageListBase
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
interface BannerApi {

    /**
     * pc_广告\banner
     * @param advPlacement
     * pc首页页中：pc_idx_middle (必填)
     * ,pc端banner：pc_banner,
     * 平台banner:platform_banner,
     * 产品banner:product_banner,
     * 首页banner：app_home_page
     * ,理财banner:liecai_banner,
     * 日进斗金:product_day,
     * 年年有余:product_year,
     * 保险banner:insurance_banner
     * @param appType	端口	number	理财师1，投资端2 （必填）
     */
    @FormUrlEncoded
    @POST("homepage/advs")
    fun getBanner(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<HomePagerBanners>>>


}