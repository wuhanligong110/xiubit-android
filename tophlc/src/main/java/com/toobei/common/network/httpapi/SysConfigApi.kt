package com.toobei.common.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.SysConfigData
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 系统配置
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface SysConfigApi {


    /**
     *接口名称 系统所有参数配置
     *请求类型 get
     *请求Url  /api/app/sysConfig/config
     *接口描述 系统所有参数配置 【具体参数查询请参考管理后台系统参数配置】
     *请求参数列表
     * appType	应用类别	number	0全局，1理财师，2投资者 非必需
     * configKey	键	string	必需
     * configType	类别	string	必需
     *
     */
    @FormUrlEncoded
    @POST("app/sysConfig/config")
    fun sysConfig(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SysConfigData>>


}