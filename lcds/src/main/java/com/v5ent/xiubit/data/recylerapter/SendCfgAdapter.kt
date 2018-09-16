package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.CfgMemberDetialActivity
import com.v5ent.xiubit.entity.CfgListData

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class SendCfgAdapter : BaseQuickAdapter<CfgListData, BaseViewHolder> {
    private var checkedPosition: Int = -1
    private var headTextList: ArrayList<String> = ArrayList()
    private var headTextPostionList: ArrayList<Int> = ArrayList()

    constructor() : super(R.layout.item_send_cfg_red_package)


    override fun convert(helper: BaseViewHolder, item: CfgListData) {
        PhotoUtil.loadImageByGlide(mContext, item.headImage, helper.getView(R.id.avatar), R.drawable.img_customer_cus)
//选择框
        val checkBox = helper.getView<ImageView>(R.id.checkbox)

      helper.getView<View>(R.id.itemRL).setOnClickListener {
            val intent = Intent(mContext, CfgMemberDetialActivity::class.java)
            intent.putExtra("userId", item.userId)
            mContext.startActivity(intent) //
        }

        checkBox.isSelected = checkedPosition == helper.adapterPosition

        checkBox.setOnClickListener {
            checkBox.isSelected = !checkBox.isSelected
            if (checkBox.isSelected && checkedPosition != helper.adapterPosition) {
                var oldCheckedPosition = checkedPosition
                checkedPosition = helper.adapterPosition
                if (oldCheckedPosition >= 0) notifyItemChanged(oldCheckedPosition)
            }

            if (!checkBox.isSelected && checkedPosition == helper.adapterPosition){
                checkedPosition = -1
            }
        }

        //标题头
        if (!headTextList.contains(item.firstLetter)) {
            headTextList.add(item.firstLetter)
            headTextPostionList.add(helper.adapterPosition)
        }
        helper.setText(R.id.alais_scort_letter,item.firstLetter)
                .setVisible(R.id.alais_scort_letter,headTextPostionList.contains(helper.adapterPosition))



        helper.setText(R.id.registTimeTv, "${item.registTime}")
                .setText(R.id.text_customer_name, "${if (item.userName.startsWith("1")) "未认证" else  item.userName}")
                .setText(R.id.text_customer_phone, item.mobile)
                .setText(R.id.cfgMemberCountTv, "直推理财师${item.teamMemberCount}人")


    }

    fun getCheckedData(): CfgListData? {
        return if (checkedPosition in 0 until data.size) data[checkedPosition] else null

    }

    override fun setNewData(data: MutableList<CfgListData>?) {
        headTextList.clear()
        checkedPosition = -1
        super.setNewData(data)
    }


}