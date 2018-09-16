package com.v5ent.xiubit.network.httpapi

import com.toobei.common.entity.BaseResponseEntity
import com.v5ent.xiubit.entity.CfgListDatasDataEntity
import com.v5ent.xiubit.entity.CustomerListDatasDataEntity
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公司: tophlc
 * 类说明: 红包派发相关
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
interface SendRepacketApi {


    /**
     * 客户列表
     *token
     * name         客户姓名、电话
     * customerType 客户类别 1:客户类别 1:投资客户 2:未投资客户 3::重要客户 为空表示全部
     *      *pageIndex    第几页 >=1,默认为1
     * sort         * sort	否	string	排序字段(1:投资额；2:注册时间3:投资时间；4:到期时间;5:重要客户)
     * order        排序方式: (1:降序，2:升序)
     */
    @FormUrlEncoded
    @POST("customer/mycustomers/pageList")
    fun customerList(@FieldMap map: Map<String, String>): Observable<CustomerListDatasDataEntity>

    /**
     * 理财师成员
     * 变量名	含义	类型	备注
     * pageIndex	第几页	string
     * pageSize	页面大小	string
     * token
     */
    @FormUrlEncoded
    @POST("personcenter/cfplannerMemberPage")
    fun cfplannerMemberPage(@FieldMap map: Map<String, String>): Observable<CfgListDatasDataEntity>

    /**
     * 红包派发给客户
     * token       参数名	    类型	          描述	       是否必须
     * rid         token 	   string 	用户的token 	是
     * userMobiles money	   string 	红包金额 	是
     * userMobiles    lists 	用户手机列表 	是
     * Exception
     */
    @FormUrlEncoded
    @POST("redPacket/sendRedPacket")
    fun redpaperSendRedpaper(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<Any>>


    /**
     * 给理财师派发红包-v4.5.0
     * 变量名	含义	类型	备注
     * rid	红包编号	string
     * token	用户的token	string
     * userMobiles	用户手机列表（多个手机号码以逗号链接）	string
     * 响应参数列表
     */
    @FormUrlEncoded
    @POST("redPacket/sendRedPacketToCfp/4.5.0")
    fun sendRedPacketToCfp(@FieldMap map: Map<String, String>): Observable<BaseResponseEntity<Any>>


}