package com.toobei.common.network.httpapi

import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.SignPushStatueEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 上传身份证或者银行卡图片到服务器识别
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface SignPushStatusApi {



    /**
     *查询签到推送状态--v4.6.6
     *token
     */
    @FormUrlEncoded
    @POST("msg/querySignPushStatus/4.6.6")
    fun querySignPushStatus(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SignPushStatueEntity>>


    /**
     *接口名称 设置签到消息免打扰--v4.6.6
     * 请求类型 get
     * signPushStatus	是否开启推送签到消息：0-不开启 1-开启	number
     * token	登录令牌	string
     */
    @FormUrlEncoded
    @POST("msg/setSignPushStatus/4.6.6")
    fun setSignPushStatus(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>

}