package com.toobei.common.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.ProductClassifyStatisticsDatasData
import com.toobei.common.entity.ProductDatasData
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
interface ProductApi {

    /**
     * 获取产品类型
     * token
     *  @param token      投呗(801-理财师推荐产品 ) 猎才大师 必需
     * @param cateIdList 非必需 默认根据不同的app类型查询对应的所有分类信息 1-热门产品 2-新手产品 3-短期产品 4-高收益产品
     *                   5-稳健收益产品 801-理财师推荐产品 802-热推产品 901-首投标 902-复投标 多个一起查询的时候请使用,分开
     *                   如：1,2,3,4,5,801,901,902 不传时则查询所有的产品分类
     */
    @FormUrlEncoded
    @POST("product/productClassifyStatistics/2.0.1")
    fun productClassifyStatistics (@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<ProductClassifyStatisticsDatasData>>


    /**
     * 功能: 接口名称 3.4.8 产品分类列表 v2.0.1
     * 请求类型 get
     * 请求Url  /product/productClassifyPageList/2.0.1
     * <p/>
     * 理财-产品分类列表-分页排序
     * @param token     投呗(801-理财师推荐产品 ) 猎才大师 必需
     * @param cateId    产品分类id 	number  1-热门产品 2-新手产品 3-短期产品
     * @param orgCode   机构编码	  string	非必需 默认查询全部机构
     * @param order     0-升序 1-降序
     * @param pageIndex pageIndex
     * @param pageSize  pageSize
     * @param sort      1-默认排序 2-年化收益 3-产品期限
     * @return
     */
    @FormUrlEncoded
    @POST("product/productClassifyPageList/2.0.1")
    fun productClassifyPageList (@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<ProductDatasData>>

    /**
     * 接口名称 4.6.0 产品分类统计 v4.6.0
     *请求类型 get
     *请求Url  /product/productClassifyStatistics/4.6.0
     */
    @FormUrlEncoded
    @POST("product/productClassifyStatistics/4.6.0")
    fun productClassifyStatisticsNew (@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<ProductClassifyStatisticsDatasData>>

    /**
     * 接口名称 4.6.0 产品分类列表 V4.6.0
     * 请求类型 get
     * 请求Url  /product/productClassifyPageList/4.6.0
     * cateId	产品分类id	string
     * order	顺序	number	0-升序 1-降序
     * sort      1-默认排序 2-年化收益 3-产品期限
     * pageIndex	第几页 >=1,默认为1	number
     * pageSize	页面条数，默认为10	number
     * token		string
     */
    @FormUrlEncoded
    @POST("product/productClassifyPageList/4.6.0")
    fun productClassifyPageListNew (@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<ProductDatasData>>

}