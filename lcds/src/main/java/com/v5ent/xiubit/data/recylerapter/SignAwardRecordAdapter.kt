package com.v5ent.xiubit.data.recylerapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.utils.TextDecorator
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.SignRecordsPageData

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/19
 */
class SignAwardRecordAdapter : BaseQuickAdapter<SignRecordsPageData, BaseViewHolder> {
    constructor() : super(R.layout.item_sign_award_adapter)

    override fun convert(helper: BaseViewHolder, item: SignRecordsPageData) {
            helper.setText(R.id.signNameTv,"第${item.rownum}签")
                    .setText(R.id.signDateTv,"${item.signTime}")
            helper.setVisible(R.id.bottomLineView,helper.adapterPosition > 0)

        try {
            TextDecorator.decorate(helper.getView(R.id.signAwardTv),"${item.signAmount}元${if (item.timesAmount.isNullOrBlank()) "" else "+${item.timesAmount}元"}")
                    .setAbsoluteSize(mContext.resources.getDimension(R.dimen.w12).toInt(),"元")
                    .build()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}