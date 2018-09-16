package com.v5ent.xiubit.data.recylerapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.TopBaseActivity
import com.toobei.common.entity.RepamentCalendarData
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
 * @time 2017/10/18
 */
class PaymentRecordAdapter : BaseQuickAdapter<RepamentCalendarData, BaseViewHolder> {


    var titleList = ArrayList<String>()
    val titlePostion = ArrayList<Int>()

    var leveltags = listOf<Int>(
            R.drawable.ic_customer_tag
            , R.drawable.ic_direct_cfg_tag
            , R.drawable.ic_two_cfg_tag
            , R.drawable.ic_three_cfg_tag)

    constructor() : super(R.layout.item_payment_record)

    override fun convert(helper: BaseViewHolder, item: RepamentCalendarData) {
        val position = helper.adapterPosition
       helper.getView<View>(R.id.footDividView).visibility =  if (position == itemCount -1) View.VISIBLE else View.GONE

       if (!titleList.contains(item.endTimeStr)) {
            titleList.add(item.endTimeStr)
            titlePostion.add(position)
        }

        if (titlePostion.contains(position)) {
            helper.setText(R.id.dateTv, item.endTimeStr)
            helper.getView<View>(R.id.dateTv).visibility = View.VISIBLE
            helper.getView<View>(R.id.divideV).visibility = if (position == headerLayout?.childCount?:0) View.GONE else View.VISIBLE
        } else {
            helper.getView<View>(R.id.dateTv).visibility = View.GONE
            helper.getView<View>(R.id.divideV).visibility = View.GONE
        }

        PhotoUtil.loadImageByGlide(mContext, item.headImage, helper.getView<ImageView>(R.id.headIv), C.DefaultResId.HEADER_IMG)

        helper.setImageResource(R.id.cfgTypeTagIv, leveltags[item.repaymentUserType])


        helper.setText(R.id.nameTv, item.userName)
                .setText(R.id.platformNameTv, item.platformName)
                .setText(R.id.profitTv, item.profit)
                .setText(R.id.investAmtTv, item.investAmt)

        helper.getView<View>(R.id.rootView).setOnClickListener {
            MobclickAgent.onEvent(mContext, "W_3_1_1")
            WebActivityCommon.showThisActivity(mContext as TopBaseActivity?,"${C.INVEST_DETIAL}${item.investId}&canJumpNative=1","")
        }


    }

    override fun setNewData(data: MutableList<RepamentCalendarData>?) {
        titleList.clear()
        titlePostion.clear()
        super.setNewData(data)
    }


}