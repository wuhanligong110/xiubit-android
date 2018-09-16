package com.toobei.common.network.httpapi

import com.toobei.common.entity.AddFeeCouponEntity
import com.toobei.common.entity.CouponCountEntity
import com.toobei.common.entity.JobGradeVoucherEntity
import com.toobei.common.entity.QueryRedPacketEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
interface CouponApi {

    /**
     * 红包列表
     * pageIndex	第几页 >=1,默认为1	number
     * pageSize	页面记录数，默认为10	number
     * token	用户令牌	string
     */
    @FormUrlEncoded
    @POST("redPacket/queryRedPacket/4.5.0")
    fun queryRedPacket(@FieldMap map: Map<String, String>): Observable<QueryRedPacketEntity>

    /**
     * 可用红包
     * deadline	产品最小期限 （type=2 必须）	number
     * model	平台收费模式 1=cpa|2 =cps	number
     * patform	平台编号	string	必须
     * productId	产品编号（type=2 必须）	string
     * token	token	string	必须
     * type	可用红包类型 : 1=平台|2=产品	number	必须
     */
    @FormUrlEncoded
    @POST("redPacket/queryAvailableRedPacket")
    fun queryAvailableRedPacket(@FieldMap map: Map<String, String>): Observable<QueryRedPacketEntity>

    /**
     * 个人加佣券列表4.6.0
     *
     */
    @FormUrlEncoded
    @POST("personaddfeeticket/myaddfeeticket")
    fun addFeeCoupon(@FieldMap map: Map<String, String>): Observable<AddFeeCouponEntity>

    /**
     * 职级体验券列表
     */
    @FormUrlEncoded
    @POST("personcenter/partner/jobGradeVoucherPage")
    fun jobGradeVoucherPage(@FieldMap map: Map<String, String>): Observable<JobGradeVoucherEntity>

    /**
     * 可用优惠券数量-v4.5.0
     *
     */
    @FormUrlEncoded
    @POST("redPacket/queryCouponCount/4.5.0")
    fun queryCouponCount(@FieldMap map: Map<String, String>): Observable<CouponCountEntity>

    /**
     * 4.5.3 产品可用红包
     *buyTotal	产品购买金额	number	非必需 默认为最大值
     *deadLineValue	产品购买天数	number	非必需
     *productId	产品id	string	必需
     *token	登录令牌	string	必需
     */
    @FormUrlEncoded
    @POST("product/productRedPacket")
    fun availableProductRedPacket(@FieldMap map: Map<String, String>): Observable<QueryRedPacketEntity>

    /**
     * 接口名称 4.6.0 产品可用加佣券
     *请求类型 get
     *请求Url  api/product/productAddfeeTicket
     * buyTotal	购买金额	number	非必需 默认购买最大金额
     * deadLineValue	产品购买期限	number	非必需 默认产品最小期限
     * productId	产品ID	string	必需
     * token	登录令牌	string	必需
     */
    @FormUrlEncoded
    @POST("product/productAddfeeTicket")
    fun availableProductAddfeeTicket(@FieldMap map: Map<String, String>): Observable<AddFeeCouponEntity>
}