package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.ActivityDetail
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.MyApp
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.WebActivityCommon
import com.v5ent.xiubit.util.C

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/19
 */
class ActivityPartAdapter : BaseQuickAdapter<ActivityDetail, BaseViewHolder> {
    constructor() : super(R.layout.item_activiy_part)

    override fun convert(helper: BaseViewHolder, item: ActivityDetail) {
        val position = helper.adapterPosition
        val imgContainer = helper.getView<RelativeLayout>(R.id.imgContainer)
        imgContainer.layoutParams.height = (MyApp.displayWidth - mContext.resources.getDimension(R.dimen.w30)).toInt() * 400 / 750

        helper.setText(R.id.orgNameTv,item.activityPlatform)
                .setText(R.id.titleTv, item.activityName)
                .setText(R.id.startTimeTv, "活动开始时间：${item.startDate}")
                .setVisible(R.id.headDividV, position == 0)
                .setVisible(R.id.coverIv, item.status == 1)  //活动状态（-1：未开始；0：进行中；1：已结束）
                .setText(R.id.leftTimeTv,
                        when (item.status) {
                            -1 -> "活动未开始"
                            0 -> "剩余时间${item.leftDay}天"
                            1 -> "活动已结束"
                            else -> "剩余时间${item.leftDay}天"
                        })

        if (item.status == 1) {
            setAllTextViewColor(helper.getView<View>(R.id.rootView),R.color.text_redPacket_unUsed)
            helper.getView<TextView>(R.id.orgNameTv).setBackgroundResource(R.drawable.shape_org_top_tag_gray_bold)
        }else{
            helper.setTextColor(R.id.orgNameTv,getColor(R.color.text_blue_common))
                    .setTextColor(R.id.titleTv,getColor(R.color.text_black_common))
                    .setTextColor(R.id.startTimeTv,getColor(R.color.text_gray_common_title))
                    .setTextColor(R.id.leftTimeTv,getColor(R.color.text_gray_common_title))
            helper.getView<TextView>(R.id.orgNameTv).setBackgroundResource(R.drawable.shape_org_top_tag_bold)

        }

        PhotoUtil.loadImageByGlide(mContext,item.activityImg,helper.getView(R.id.iv), C.DefaultResId.BANNER_PLACT_HOLD_IMG_750x400)

        helper.getView<View>(R.id.rootView).setOnClickListener {
            val intent = Intent(mContext, WebActivityCommon::class.java)
            intent.putExtra("url", item.linkUrl)
            intent.putExtra("shareContent", item.shareContent)
            mContext.startActivity(intent)
        }

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

    private fun getColor(colorResId: Int) = ContextCompat.getColor(mContext, colorResId)

}