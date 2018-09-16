package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.QueryRedPacketData
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.LoginActivity
import com.v5ent.xiubit.activity.MainActivity
import com.v5ent.xiubit.activity.SendRedpacketActivity


/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class RedPacketsAdapter : BaseQuickAdapter<QueryRedPacketData, BaseViewHolder> {
    var type = 0
    companion object {
        val TYPE_COUPON_REDPACKET = 0
        val TYPE_AVAILABLE_REDPACKET = 1
    }
    constructor(type:Int) : super(R.layout.item_redpackets){ this.type = type}


    override fun convert(helper: BaseViewHolder, item: QueryRedPacketData) {
        helper.getView<RelativeLayout>(R.id.rootView).setPadding(getDimen(R.dimen.w10),
                if (helper.adapterPosition == 0) getDimen(R.dimen.w15) else 0,
                getDimen(R.dimen.w10),
                getDimen(R.dimen.w15))
        //颜色
//        0=未使用|1=已使用|2=已过期
        if (item.useStatus == "0") {

            helper.setTextColor(R.id.amountTv, getColor(R.color.text_red_common))
                    .setTextColor(R.id.remarkTv, getColor(R.color.text_gray_common))
                    .setTextColor(R.id.nameTv, getColor(R.color.text_black_common))
                    .setImageResource(R.id.sendIv, R.drawable.tag_send_repaget_red)
                    .setImageResource(R.id.immediaUseIv, R.drawable.img_immediause_red)
            setAllTextViewColor(helper.getView(R.id.contentInfoCantainer), R.color.text_gray_common_title)
            helper.setVisible(R.id.usedSignIv,false)
        } else {
            setAllTextViewColor(helper.getView(R.id.rootView), R.color.text_redPacket_unUsed)
            helper.setImageResource(R.id.sendIv, R.drawable.tag_send_repaget_gray)
                    .setImageResource(R.id.immediaUseIv, R.drawable.img_immediause_gray)
                    .setImageResource(R.id.usedSignIv,
                            if (item.useStatus == "1")
                                R.drawable.img_redpack_has_use else
                                R.drawable.img_redpack_has_dead)
            helper.setVisible(R.id.usedSignIv,true)
        }

        //文字
        helper.setText(R.id.amountTv,"${item.redpacketMoney}")
//        TextDecorator.decorate(helper.getView(R.id.amountTv),"¥${item.redpacketMoney}")
//                .setAbsoluteSize(getDimen(R.dimen.w16),false,"¥").build()
        // amountLimit	金额限制	number	0=不限|1=大于|2=大于等于
        helper.setText(R.id.remarkTv, if (item.amountLimit == "0") "金额不限" else "${item.amount}元起")
                .setText(R.id.nameTv, item.name)
                //true=限制|false=不限制
                .setText(R.id.platformLimitTv, "适用平台：${if (item.platformLimit) item.platform else "网贷"}")
                //0=不限|1=限制产品编号|2=等于产品期限|3=大于等于产品期限
                .setText(R.id.productLimitTv, "适用产品：${when (item.productLimit) {
                    "0" -> "不限"
                    "1" -> item.platform
                    "2" -> "${item.deadline}天产品"
                    "3" -> "${item.deadline}天以上产品(含${item.deadline}天)"
                    else -> ""
                }
                }")
                //0=不限|1=用户首投|2=平台首投
                .setText(R.id.fristInvestLimitTv, "首投限制：${when (item.investLimit) {
                    "0" -> "不限"
                    "1" -> "用户首投"
                    "2" -> "平台首投"
                    else -> ""
                }
                }")
                .setText(R.id.deadDateTv, "过期时间：${item.expireTime}")

        //可用红包隐藏派发和使用
        if (type == TYPE_AVAILABLE_REDPACKET) {
            helper.getView<View>(R.id.sendIv).visibility = View.GONE
            helper.getView<View>(R.id.immediaUseIv).visibility = View.GONE
            return
        }

        //点击派发
        //派发是否可用
        if (item.useStatus == "0") {
            helper.getView<View>(R.id.sendIv).visibility = if (item.sendStatus == "1") View.VISIBLE else View.INVISIBLE
            helper.getView<View>(R.id.sendIv).isEnabled = true
        } else {
            helper.getView<View>(R.id.sendIv).isEnabled = false
            helper.getView<View>(R.id.sendIv).visibility = View.VISIBLE
        }


        helper.getView<View>(R.id.sendIv).setOnClickListener {
            if (MyApp.getInstance().loginService.isTokenExsit) {
                val intent = Intent(mContext, SendRedpacketActivity::class.java)
                intent.putExtra("redPacket", item)
                mContext.startActivity(intent)
            } else{
                mContext.startActivity(Intent(mContext,LoginActivity::class.java))
            }
        }

        //点击使用
        helper.getView<View>(R.id.immediaUseIv).isEnabled = item.useStatus == "0"
        helper.getView<View>(R.id.immediaUseIv).setOnClickListener {
            if (MyApp.getInstance().loginService.isTokenExsit) {
                val intent = Intent(mContext, MainActivity::class.java)
                intent.putExtra("skipTab", "p1t0")  //投资-平台
                mContext.startActivity(intent)
            } else{
                mContext.startActivity(Intent(mContext,LoginActivity::class.java))
            }
        }



    }

    private fun getColor(colorResId: Int) = ContextCompat.getColor(mContext, colorResId)
    private fun getDimen(dimenRes: Int): Int {
        return mContext.resources.getDimension(dimenRes).toInt()
    }

    private fun setAllTextViewColor(view: View, colorResId: Int) {
        if (view is TextView) view.setTextColor(ContextCompat.getColor(mContext, colorResId))

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setAllTextViewColor(innerView, colorResId)
            }
        }
    }


}