package com.v5ent.xiubit.data

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.TopBaseActivity
import com.toobei.common.entity.InvestCalendarData
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.WebActivityCommon
import com.v5ent.xiubit.util.C
import com.umeng.analytics.MobclickAgent


/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/6/27
 */

class InvestRecordRecylerAdpter : BaseQuickAdapter<InvestCalendarData, BaseViewHolder> {

    private var type: Int

    constructor(type: Int) : super(R.layout.item_invest_record_recycle) {
        this.type = type
    }

    companion object {
        val TYPE_NETLOAN = 0 //网贷
        val TYPE_INSURE = 1 //保险
    }

    val titleList = ArrayList<String>()
    val titlePostion = ArrayList<Int>()

    var leveltags = listOf<Int>(
            R.drawable.ic_customer_tag
            , R.drawable.ic_direct_cfg_tag
            , R.drawable.ic_two_cfg_tag
            , R.drawable.ic_three_cfg_tag)

    override fun convert(helper: BaseViewHolder, item: InvestCalendarData) {
        val position = helper.adapterPosition
        if (!titleList.contains(item.startTimeStr)) {
            titleList.add(item.startTimeStr)
            titlePostion.add(position)
        }

        if (titlePostion.contains(position)) {
            helper.setText(R.id.dateTv, item.startTimeStr)
            helper.getView<View>(R.id.dateTv).visibility = View.VISIBLE
            helper.getView<View>(R.id.divideV).visibility = if (position == 0) View.GONE else View.VISIBLE
        } else {
            helper.getView<View>(R.id.dateTv).visibility = View.GONE
            helper.getView<View>(R.id.divideV).visibility = View.GONE
        }
//        0-待计算 1-计算成功 2-计算失败
        helper.getView<ImageView>(R.id.statueTypeTagIv).visibility = if (item.clearingStatus.isNullOrBlank()) View.GONE else View.VISIBLE
        when (item.clearingStatus) {
            "0" -> helper.getView<ImageView>(R.id.statueTypeTagIv).setImageResource(R.drawable.ic_clearingstatus_on)
            "1" -> helper.getView<ImageView>(R.id.statueTypeTagIv).visibility = View.GONE
            "2" -> helper.getView<ImageView>(R.id.statueTypeTagIv).setImageResource(R.drawable.ic_clearingstatusfail)
        }

        PhotoUtil.loadImageByGlide(mContext, item.headImage, helper.getView<ImageView>(R.id.headIv), C.DefaultResId.HEADER_IMG)
        helper.setImageResource(R.id.cfgTypeTagIv, leveltags[item.userType])
        var nameSuffix = when (item.userType) {
            0, 1 -> ""
            2 -> "的直推理财师"
            3 -> "的二级理财师"
            else -> ""
        }
        helper.setText(R.id.nameTv, item.userName)
                .setText(R.id.nameSuffixTv, nameSuffix)
                .setText(R.id.platformNameTv, item.platformName)
                .setText(R.id.issueAmountTv, item.investAmt)
                .setText(R.id.commissionTv, item.feeAmountSum)
                .setText(R.id.platformTypeTv, if (item.userType == 0) "投资平台" else "出单平台")
                .setText(R.id.issueTypeTv, if (item.userType == 0) "投资金额(元)" else "出单金额(元)")

        helper.getView<View>(R.id.rootView).setOnClickListener {
            MobclickAgent.onEvent(mContext, "W_3_1_1")
            WebActivityCommon.showThisActivity(mContext as TopBaseActivity?, "${C.INVEST_DETIAL}${item.investId}&canJumpNative=1${if (type == TYPE_INSURE) "&message=insurance" else ""}", "")
        }
    }

    override fun setNewData(data: MutableList<InvestCalendarData>?) {
        super.setNewData(data)
        titleList.clear()
        titlePostion.clear()
    }

}
