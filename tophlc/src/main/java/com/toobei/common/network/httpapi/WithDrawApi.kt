package com.toobei.common.network.httpapi

import com.toobei.common.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by yangLin on 2018/5/10.
 */
public interface WithDrawApi {

    /**
     * 功能：我的账户
     */
    @FormUrlEncoded
    @POST("account/myaccount")
    abstract fun myaccount(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<AccountHomeData>>

    /**
     * 功能：获取提现银行卡
     */
    @FormUrlEncoded
    @POST("account/getWithdrawBankCard")
    abstract fun getWithdrawBankCard(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BankCardInfo>>


    /**
     * 功能：提现到银行卡
     * @param token
     * @param bankCard  银行卡号
     * @param bankName  银行名称
     * @param amount    提现金额,单位: 元
     * @param city      城市 可为null
     * @param kaihuhang 开户行 可为null
     */
    @FormUrlEncoded
    @POST("account/userWithdrawRequest")
    abstract fun userWithdrawRequest(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 功能：获取省份
     */
    @FormUrlEncoded
    @POST("account/queryAllProvince")
    abstract fun queryAllProvince(@FieldMap map: Map<String, String>): Observable<AddressProvinceDatasDataEntity>


    /**
     * 功能：获取城市
     *     * @param provinceId
     */
    @FormUrlEncoded
    @POST("account/queryCityByProvince")
    abstract fun queryCityByProvince(@FieldMap map: Map<String, String>): Observable<AddressCityDatasDataEntity>
}