package com.v5ent.xiubit.data.recylerapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.TopBaseActivity
import com.toobei.common.entity.CustomerInvestRecordData
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.WebActivityCommon
import com.v5ent.xiubit.util.C

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/18
 */
class MemberInvestRecordAdapter : BaseQuickAdapter<CustomerInvestRecordData, BaseViewHolder> {
    var memberType: Int = 0

    companion object {
        val MEMBERTYPE_CUSTOMER = 1
        val MEMBERTYPE_CFG = 2
    }

    var titleList = ArrayList<String>()
    val titlePostion = ArrayList<Int>()

    var leveltags = listOf<Int>(
            R.drawable.ic_customer_tag
            , R.drawable.ic_direct_cfg_tag
            , R.drawable.ic_two_cfg_tag
            , R.drawable.ic_three_cfg_tag)

    constructor(memberType: Int) : super(R.layout.item_member_invest) {
        this.memberType = memberType
    }

    override fun convert(helper: BaseViewHolder, item: CustomerInvestRecordData) {
        val position = helper.adapterPosition
        if (!titleList.contains(item.investTime)) {
            titleList.add(item.investTime)
            titlePostion.add(position)
        }

        if (titlePostion.contains(position)) {
            helper.setText(R.id.dateTv, item.investTime)
            helper.getView<View>(R.id.dateTv).visibility = View.VISIBLE
            helper.getView<View>(R.id.divideV).visibility = if (position == 1) View.GONE else View.VISIBLE
        } else {
            helper.getView<View>(R.id.dateTv).visibility = View.GONE
            helper.getView<View>(R.id.divideV).visibility = View.GONE
        }

        PhotoUtil.loadImageByGlide(mContext, item.headImage, helper.getView<ImageView>(R.id.headIv), C.DefaultResId.HEADER_IMG)

        if (memberType != MEMBERTYPE_CUSTOMER) {
            helper.setImageResource(R.id.cfgTypeTagIv, leveltags[item.userType])
        }


        var nameSuffix = when (item.userType) {
            0, 1 -> ""
            2 -> "的直推理财师"
            3 -> "的二级理财师"
            else -> ""
        }

        helper.setText(R.id.nameTv, item.userName)
                .setText(R.id.nameSuffixTv, nameSuffix)
                .setText(R.id.platformNameTv, item.orgName)
                .setText(R.id.issueAmountTv, item.investAmt)
                .setText(R.id.commissionTv, item.feeAmount)
                .setText(R.id.platformTypeTv, if (item.userType == 0) "投资平台" else "出单平台")
                .setText(R.id.issueTypeTv, if (item.userType == 0) "投资金额" else "出单金额(元)")

        helper.getView<View>(R.id.rootView).setOnClickListener {
            WebActivityCommon.showThisActivity(mContext as TopBaseActivity, "${C.INVEST_DETIAL}${item.investId}&canJumpNative=0", "")
        }


    }

    override fun setNewData(data: MutableList<CustomerInvestRecordData>?) {
        super.setNewData(data)
        titleList.clear()
        titlePostion.clear()
    }


}