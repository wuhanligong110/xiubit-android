package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.AccountbookInvestingData
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.AccountBookDetialActivity

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/19
 */
class AccountBookAdapter : BaseQuickAdapter<AccountbookInvestingData, BaseViewHolder> {
    constructor() : super(R.layout.item_account_book)

    override fun convert(helper: BaseViewHolder, item: AccountbookInvestingData) {
        helper.setText(R.id.titleTv, item.investDirection)
                .setText(R.id.waitGetbaseMoneyTv, if (item.investAmt.isNullOrBlank()) "--" else item.investAmt)
                .setText(R.id.waitGetProfitTv, if (item.profit.isNullOrBlank()) "--" else item.profit)

        helper.getView<View>(R.id.rootView).setOnClickListener {
            val intent = Intent(mContext, AccountBookDetialActivity::class.java)
            intent.putExtra("bookId",item.id)
            intent.putExtra("title",item.investDirection)
            mContext.startActivity(intent)
        }

    }


}