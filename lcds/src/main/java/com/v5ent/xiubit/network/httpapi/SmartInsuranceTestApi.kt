package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseEntity
import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.SmartTestResultData
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
interface SmartInsuranceTestApi {

    /**
     * age	年龄	string
     * familyEnsure	家庭保障	string
     * familyLoan	家庭贷款	string
     * familyMember	家庭成员	string
     * sex	性别	string
     * token		string
     * yearIncome	年收入	string
     */
    @FormUrlEncoded
    @POST("insurance/qixin/questionSummary")
    fun upTestResult(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<BaseEntity>>

    /**
     * 首页-保险评测结果
     */
    @FormUrlEncoded
    @POST("insurance/qixin/queryQquestionResult")
    fun getTestResult(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<SmartTestResultData>>


}