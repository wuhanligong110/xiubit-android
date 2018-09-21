package com.toobei.common.network.httpapi

import com.toobei.common.entity.ActivityListDatasDataEntity
import com.toobei.common.entity.ActivityPlatformPageListDatasDataEntity
import com.toobei.common.entity.MorningPaperEntity
import com.toobei.common.entity.SelectedRecomendNewsEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 活动和咨询
 *
 * @author hasee-pc
 * @time 2017/10/17
 */
interface ActivityAndNewsApi {

    /**
     * 每日早报
     */
    @FormUrlEncoded
    @POST("classroom/morningPaper/4.5.0")
    fun morningPaper(@FieldMap map: Map<String, String>): Observable<MorningPaperEntity>

    /**
     * 貅比特页精选推荐列表-v4.5.0
     */
    @FormUrlEncoded
    @POST("classroom/selectedRecomend/list/4.5.0")
    fun selectedRecomendNews(@FieldMap map: Map<String, String>): Observable<SelectedRecomendNewsEntity>

    /**
     * 平台活动列表（不分页） - 貅比特页活动banner 以及 各个平台活动
     *
     *  变量名	含义	类型	备注
     *  activityPlatform	平台名称	string	（为空返回所有平台的结果）
     */
    @FormUrlEncoded
    @POST("activity/platform/list")
    fun platformActivity(@FieldMap map: Map<String, String>): Observable<ActivityPlatformPageListDatasDataEntity>


    /**
     * 活动专区 貅比特页活动banner -更多
     * appType	活动类别:1理财师，2投资者	string	不传查所有
     * pageIndex	第几页 >=1,默认为1	number
     * pageSize	页面记录数，默认为10	number
     * token	登录令牌	string
     */
    @FormUrlEncoded
    @POST("activity/pageList")
    fun activityList(@FieldMap map: Map<String, String>): Observable<ActivityListDatasDataEntity>
}