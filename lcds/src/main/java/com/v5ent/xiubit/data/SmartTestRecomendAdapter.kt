package com.v5ent.xiubit.data

import android.content.Intent
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.BaseEntity
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.InsuranceClassifyActivity
import com.v5ent.xiubit.entity.SmartTestResultData
import com.v5ent.xiubit.entity.SmartTestResultData.RecomListBean
import com.umeng.analytics.MobclickAgent

/**
 * Created by hasee-pc on 2017/12/28.
 */
class SmartTestRecomendAdapter : BaseQuickAdapter<RecomListBean, BaseViewHolder>(R.layout.item_smart_test_recom) {

    override fun convert(helper: BaseViewHolder?, item: RecomListBean?) {
        val iv = helper!!.getView<ImageView>(R.id.iv)
        PhotoUtil.loadImageByGlide(mContext,item?.categoryImage,iv)
        iv.setOnClickListener {
            MobclickAgent.onEvent(mContext, "T_4_1_6")
            val intent = Intent(mContext, InsuranceClassifyActivity::class.java)
            intent.putExtra("type",item?.recomCategory)
            mContext.startActivity(intent)
        }

    }

}