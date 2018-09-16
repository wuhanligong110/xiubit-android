package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.CustomerCfpmemberPageData
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.CfgMemberDetialActivity
import com.v5ent.xiubit.util.C

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/17
 */

class CfgMemberListAdapter : BaseQuickAdapter<CustomerCfpmemberPageData, BaseViewHolder> {
    companion object {
        val TYPE_ALL = 1 //所有
        val TYPE_NOINVEST = 2  //未出单
        val TYPE_ATTENT = 3  //我关注
    }

    var type = 0

    constructor(type: Int) : super(R.layout.item_cfg_member_list) {
        this.type = type
    }


    override fun convert(helper: BaseViewHolder, item: CustomerCfpmemberPageData) {
        helper.setText(R.id.nameTv, item.userName)
                .setText(R.id.levelTv, item.grade)
                .setText(R.id.lastInvestTimeTv
                        , if (type == 2) "注册时间：${item.registTime}"
                else if (item.recentTranDate.isNullOrBlank()) "还未出单" else "最近出单：${item.recentTranDate}")
        var headIv: ImageView = helper.getView(R.id.headIv)
        PhotoUtil.loadImageByGlide(mContext, item.headImage, headIv, C.DefaultResId.HEADER_IMG)

        helper.getView<View>(R.id.rootView).setOnClickListener {
            var intent = Intent(mContext, CfgMemberDetialActivity::class.java)
            intent.putExtra("userId", item.userId)
            mContext.startActivity(intent)
        }

    }

}