package com.v5ent.xiubit.data

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.AwardMoneyRecordDetial

/**
 * Created by yangLin on 2018/4/10.
 */
class AwardMoneyRecordAdapter : BaseQuickAdapter<AwardMoneyRecordDetial, BaseViewHolder>((R.layout.item_award_money_record)) {

    override fun convert(helper: BaseViewHolder, item: AwardMoneyRecordDetial) {
        helper.setText(R.id.nameTv,item.typeName)
                .setText(R.id.dateTv,item.createTime)
                .setText(R.id.moneyTv,"${
                //金额类型 1：“+” 0：“-”
                when(item.amountType){
                    "1" -> "+"
                    "0" -> "-"
                    else -> ""
                }
                }${item.amount}")
    }
}