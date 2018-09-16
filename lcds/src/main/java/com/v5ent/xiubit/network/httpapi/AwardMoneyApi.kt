package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.PageListBase
import com.v5ent.xiubit.entity.AwardMoneyRecordDetial
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
interface AwardMoneyApi {

    /**
     * 接口名称 奖励金明细
     * 请求类型 get
     * 请求Url  /api/sign/bounty/detail/4.6.0
     * 类型： 1：签到 2：抽奖奖励金 3：转出到理财余额 4：抽奖消耗
     * 变量名	含义	类型	备注
     * pageIndex	页码	number
     * pageSize	页面大小	number
     * token	登录令牌	string
     */
    @FormUrlEncoded
    @POST("sign/bounty/detail/4.6.0")
    fun awardMoneyRecordDetial(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<PageListBase<AwardMoneyRecordDetial>>>



}