package com.v5ent.xiubit.data.recylerapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.AddFeeCouponData
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.R

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class AddFeeCouponAdapter : BaseQuickAdapter<AddFeeCouponData, BaseViewHolder> {
    constructor() : super(R.layout.item_add_fee_coupon)


    override fun convert(helper: BaseViewHolder, item: AddFeeCouponData) {

        helper.getView<RelativeLayout>(R.id.rootView).setPadding(getDimen(R.dimen.w10),
                if (helper.adapterPosition == 0) getDimen(R.dimen.w15) else 0,
                getDimen(R.dimen.w10),
                getDimen(R.dimen.w15))
        //颜色
//        1：未过期 2：已过期 3：已使用
        if (item.status == "1") {
            helper.setTextColor(R.id.rateTv, if (item.type == "1") getColor(R.color.text_red_common) else getColor(R.color.text_yellow_common))
                    .setTextColor(R.id.typeTv, getColor(R.color.text_gray_common))
                    .setTextColor(R.id.sourceTv, getColor(R.color.text_black_common))
            setAllTextViewColor(helper.getView(R.id.contentInfoCantainer), R.color.text_gray_common_title)
            helper.setVisible(R.id.usedSignIv,false)
        } else {
            setAllTextViewColor(helper.getView(R.id.rootView), R.color.text_redPacket_unUsed)

            helper.setImageResource(R.id.usedSignIv,
                            if (item.status == "3")
                                R.drawable.img_redpack_has_use else
                                R.drawable.img_redpack_has_dead)
            helper.setVisible(R.id.usedSignIv,true)
        }

        //文字
        TextDecorator.decorate(helper.getView(R.id.rateTv), "${item.rate}%")
                .setAbsoluteSize(getDimen(R.dimen.w16),false,"%")
                .build()


        helper.setText(R.id.sourceTv, item.name)
//        加拥券类型 1=加拥券|2=奖励券
                .setText(R.id.typeTv,item.addFeeLimitDayMsg)
                //true=限制|false=不限制
                .setText(R.id.platformLimitTv, "适用平台：${item.platformLimitMsg}")
                //0=不限|1=用户首投|2=平台首投
                .setText(R.id.fristInvestLimitTv, "首投限制：${item.investLimitMsg}")
                .setText(R.id.productLimitTv, "${item.productLimitMsg}")
                .setText(R.id.validEndTimeTv, "过期时间：${item.validEndTime}")


    }

    private fun getColor(resId:Int) = ContextCompat.getColor(mContext,resId)

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