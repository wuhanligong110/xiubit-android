package com.v5ent.xiubit.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.toobei.common.TopApp
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.BundBankcardData
import com.toobei.common.entity.OrgUserCenterUrlData
import com.toobei.common.entity.jiuhuOrgEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.toobei.common.network.httpapi.ThirdPartApi
import com.toobei.common.network.httpapi.jiuhuOrgApi
import com.toobei.common.view.dialog.PromptDialogCommon
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.CardManagerAdd
import com.v5ent.xiubit.activity.LoginActivity
import com.v5ent.xiubit.activity.ThirdOrgWebActivity
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.xsl781.utils.Logger

/**
 * Created by yangLin on 2018/3/20.
 */
public class JumpJiuhuService(var skipNeedCardBinded: Boolean? = false, var url: String?, val thirdProductId: String?, val orgName: String, val ctx: Context) {
    //productId 返回格式：180208101339744_1   productId 是前半段180208101339744  issuePeriod是后面的1  中间用‘_'  拼接的
    private var realProductId: String = ""
    private var orgNum: String = "OPEN_JIUFUQINGZHOU_WEB"
    private var issuePeriod: String = ""
    private var tag: Int = 0  //1 :跳转产品详情  2: 跳转个人中心

    init {
        if (thirdProductId?.contains("_") == true) {
            val split = thirdProductId.split("_")
            if (split.size == 2) {
                realProductId = split[0]
                issuePeriod = split[1]
            } else {
                Logger.e("玖富productId格式错误:productId == $thirdProductId")
            }
        }
    }

    //跳转详情页
    public fun skipDetialPage() {
        tag = 1
        run()
    }

    public fun skipPersonCenter() {
        tag = 2
        run()
    }

    private fun run() {
        if (!TextUtil.isEmpty(MyApp.getInstance().loginService.token) ) {
            if (skipNeedCardBinded!!) {
                checkBoundCard()
            }else{
                if (tag == 2) {
                    getJiuhuCenterUrl()
                } else {
                    getJiuhuParmas()
                }
            }
        } else {
            TopApp.loginAndStay = true
            ctx.startActivity(Intent(ctx, LoginActivity::class.java))
        }
    }

    private fun checkBoundCard() {
        RetrofitHelper.getInstance().retrofit.create(CardBindApi::class.java)
                .cardBindState(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BundBankcardData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BundBankcardData>) {
                        if (response.data.isBundBankcard) {
                            if (tag == 2) {
                                getJiuhuCenterUrl()
                            } else {
                                getJiuhuParmas()
                            }
                        } else {  //未绑卡
                            val promptDialogCommon = PromptDialogCommon(ctx as Activity?, ctx.getString(R.string.dialog_title_boundCard_remind), ctx.getString(R.string.dialog_content_boundCard_remind), "确定")
                            promptDialogCommon.setBtnPositiveClickListener {
                                //点击绑定,跳转绑卡页面
                                ctx.startActivity(Intent(ctx, CardManagerAdd::class.java))
                            }
                            promptDialogCommon.show()
                        }
                    }


                })
    }

    private fun getJiuhuParmas() {
        RetrofitHelper.getInstance().retrofit.create(jiuhuOrgApi::class.java)
                .getJiuHuToken(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<jiuhuOrgEntity>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<jiuhuOrgEntity>) {
                        val token = response.data.token
                        var intent = Intent(ctx, ThirdOrgWebActivity::class.java)
                        var jumpUrl = ""
                        when (tag) {
                            1 -> {
                                jumpUrl = "${url}?productId=${realProductId}&issuePeriod=${issuePeriod}&token=${token}"
                            }
                            2 -> {
                                jumpUrl = "${url}&token=${token}"
                                intent.putExtra("orgName", orgName)
                            }
                            else -> "${url}"
                        }

                        intent.putExtra("url", jumpUrl)
                        ctx.startActivity(intent)
                    }
                })
    }


    /**
     * 获取玖富的个人中心
     */
    fun getJiuhuCenterUrl() {

        RetrofitHelper.getInstance().retrofit.create(ThirdPartApi::class.java)
                .getOrgUserCenterUrl(ParamesHelp().put("orgNo", orgNum).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<OrgUserCenterUrlData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<OrgUserCenterUrlData>) {
                        url = response.data.orgUsercenterUrl
                        getJiuhuParmas()
                    }

                })


    }

}