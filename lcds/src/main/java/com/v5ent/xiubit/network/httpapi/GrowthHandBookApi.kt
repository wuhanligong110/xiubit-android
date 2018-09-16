package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.PageListBase
import com.v5ent.xiubit.entity.NewsPageEntity
import com.v5ent.xiubit.entity.NewsTopEntity
import com.v5ent.xiubit.entity.PersonalCustomizatioEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 *
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface GrowthHandBookApi {


    /**
     *个人定制列表--4.1.1
     * token
     */
    @FormUrlEncoded
    @POST("growthHandbook/personalCustomization/4.1.1")
    fun personalCustomizatio(@FieldMap map: Map<String, String>): Observable<PersonalCustomizatioEntity>


    /**
     *接口名称 发现-置顶资讯-v4.6.0
     *请求类型 get
     */
    @FormUrlEncoded
    @POST("app/newsTop/4.6.0")
    fun newsTop(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<NewsTopEntity>>>

    /**
     *接口名称 资讯分页-侯小碧-已完成
     *请求类型 get
     *请求Url  /app/newsPageList
     *接口描述 资讯分页
     * pageIndex	第几页	number
     * pageSize	每页的记录数	number
     * typeCode	资讯类别	string	1：财经 2：投资 3：管理 4：观点 5：看一看 6：查一查
     */
    @FormUrlEncoded
    @POST("app/newsPageList")
    fun newsPageList(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<NewsPageEntity>>>


    /**
     * 接口名称 资讯详情-侯小碧-已完成
     *请求类型 get
     *请求Url  /app/pageList/detail
     *接口描述 资讯详情
     *newsId	资讯ID	string	必填
     * 这个接口只是为了通知后台已经读了这个资讯，方便标记阅读数
     */
    @FormUrlEncoded
    @POST("app/pageList/detail")
    fun newsDetail(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<BaseEntity>>>


}