package com.v5ent.xiubit.data.recylerapter

import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.JobGradeVoucherData
import com.v5ent.xiubit.R

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class GradeExperCouponAdapter : BaseQuickAdapter<JobGradeVoucherData, BaseViewHolder> {
    constructor() : super(R.layout.item_grade_exper_coupon)


    override fun convert(helper: BaseViewHolder, item: JobGradeVoucherData) {
        helper.getView<RelativeLayout>(R.id.rootView).setPadding(getDimen(R.dimen.w10),
                if (helper.adapterPosition == 0) getDimen(R.dimen.w15) else 0,
                getDimen(R.dimen.w10),
                getDimen(R.dimen.w15))

        //颜色
//        1=未使用|2=已使用|3=已过期 | 4=已失效
        if (item.status == 1) {
            helper.setTextColor(R.id.voucherName, getColor(R.color.text_red_common))
                    .setTextColor(R.id.activityNameTv, getColor(R.color.text_gray_common_title))
                    .setTextColor(R.id.expertextTv, getColor(R.color.text_gray_common_title))
                    .setImageResource(R.id.gradeTagIv,
                            if (item.voucherType == "40") R.drawable.coupon_grade_level1_light
                            else R.drawable.coupon_grade_level2_light)
                    .setVisible(R.id.usedSignIv, false)
            setAllTextViewColor(helper.getView(R.id.contentInfoCantainer), R.color.text_gray_common_title)
        } else {
            setAllTextViewColor(helper.getView(R.id.rootView), R.color.text_redPacket_unUsed)
            helper.setImageResource(R.id.usedSignIv,
                    when (item.status) {
                        2 -> R.drawable.img_redpack_on_used
                        3 -> R.drawable.img_redpack_has_dead
                        else -> R.drawable.img_redpack_has_unwork

                    })
//            voucherType	券类型	string	30代表经理|40代表总监
                    .setImageResource(R.id.gradeTagIv,
                            if (item.voucherType == "40") R.drawable.coupon_grade_level1_gray
                            else R.drawable.coupon_grade_level2_gray)
                    .setVisible(R.id.usedSignIv, true)
        }

//
        //文字

        helper.setText(R.id.voucherName, item.voucherName)
                .setText(R.id.activityNameTv, item.activityAttr)
                .setText(R.id.jobGradeWelfare1Tv, item.jobGradeWelfare1)
                .setText(R.id.jobGradeWelfare2Tv, item.jobGradeWelfare2)
                .setText(R.id.validBeginTimeTv, "开始时间：${item.useTime}")
                .setText(R.id.validEndTimeTv, "结束时间：${item.expiresTime}")


    }

    private fun getDimen(dimenRes: Int): Int {
        return mContext.resources.getDimension(dimenRes).toInt()
    }

    private fun getColor(resId: Int) = ContextCompat.getColor(mContext, resId)

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