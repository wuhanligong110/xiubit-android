package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
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
interface EventUpApi {

    /**
     * 后台事件埋点
     * appversion	app版本号	string
     * deviceDetail	设备详情	string
     * deviceId	设备id	string
     * deviceResolution	设备分辨率	string
     * requestUrl	请求路径	string	必需
     * requestUrlRemark	请求路径说明	string	必需
     * systemVersion	手机系统版本号	string
     * token	登录令牌	string	和userId 二选一
     * userId	用户编码	string	和token 二选一
     */
    @FormUrlEncoded
    @POST("trace/add")
    fun eventUp(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>



}