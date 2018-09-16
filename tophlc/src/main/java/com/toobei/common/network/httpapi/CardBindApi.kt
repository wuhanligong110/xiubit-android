package com.toobei.common.network.httpapi

import com.toobei.common.entity.*
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 记账本相关
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface CardBindApi {


    /**
     * 绑卡状态
     * token
     */
    @FormUrlEncoded
    @POST("account/personcenter/setting")
    fun cardBindState(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BundBankcardData>>

    /**
     * 绑卡信息和身份信息
     * token
     */
    @FormUrlEncoded
    @POST("account/getUserBindCard")
    fun getUserBindCard(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BankCardInfo>>


    /**
     * 交易密码状态
     * token
     * 需要使用https
     */
    @FormUrlEncoded
    @POST("account/verifyPayPwdState")
    fun verifyPayPwdState(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<CheckResponseData>>


    /**
     * 校验交易密码
     * token
     * @param pwd
     */
    @FormUrlEncoded
    @POST("account/verifyPayPwd")
    fun verifyPayPwd(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<CheckResponseData>>

    /**
     * 发送验证码
     * token
     * @param token  登录态(登录状态下使用)
     * @param mobile 手机号(非登录状态下使用)
     * @param type   验证码类别:0，绑卡手机验证 1、注册 2、重置登录密码 3.重置支付密码
     */
    @FormUrlEncoded
    @POST("user/sendVcode")
    fun sendVcode(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 向账户绑定手机发送验证码
     *  token
     */
    @FormUrlEncoded
    @POST("account/sendVcode")
    fun sendAccountVcode(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>


    /**
     * 校验验证码
     * token
     * @param mobile 手机号码
     * @param token  登录态(登录状态下使用)
     * @param vcode  验证码
     */
    @FormUrlEncoded
    @POST("account/inputVcode")
    fun checkVcode(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<ResetPayPwdToken>>


    /**
     * 绑定银行卡
     * token
     * @param token  登录态(登录状态下使用)
     * @param vcode  验证码
     *bankCard	银行卡号	string	非空
     *bankCode	银行code	string  //可不传
     *bankId	银行ID	string //可不传
     *bankName	银行名称	string  //可不传
     *city	城市	string
     * idCard	身份证号码	string	非空
     *kaihuhang	开户行	string	开户行填的值是city城市
     *mobile	银行预留手机	string	非空
     * userName	用户名称	string	非空
     * token		string	非空
     */
    @FormUrlEncoded
    @POST("account/addBankCard")
    fun bindCard(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BankCardInfo>>

    /**
     * 接口名称 查询银行-已实现
     *请求类型 post
     *请求Url  account/queryAllBank
     */
    @FormUrlEncoded
    @POST("account/queryAllBank")
    fun queryAllbank(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<BankBean>>>

}