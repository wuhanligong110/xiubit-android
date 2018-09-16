package com.toobei.common.network.httpapi

import com.toobei.common.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * 公司: tophlc
 * 类说明: 上传身份证或者银行卡图片到服务器识别
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface CardUpRecoApi {


    /**
     *上传身份证识别
     *
     */
    @Multipart
    @POST("kareco/idcardReco")
    fun uploadIdcardReco(@Part imgs: MultipartBody.Part): Observable<BaseResponseEntity<IdcardRecoData>>


    /**
     *上传银行卡识别
     *
     */
    @Multipart
    @POST("kareco/bankcardReco")
    fun uploadBankcardReco(@Part imgs: MultipartBody.Part): Observable<BaseResponseEntity<BankcardRecoData>>

}