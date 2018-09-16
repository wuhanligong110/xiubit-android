package com.v5ent.xiubit.data.recylerapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.Custom
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.CustomerMemberDetialActivity

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class SendCustomerAdapter : BaseQuickAdapter<Custom, BaseViewHolder> {
    private var checkedPosition: Int = -1
    private var headTextList: ArrayList<String> = ArrayList()
    private var headTextPostionList: ArrayList<Int> = ArrayList()

    constructor() : super(R.layout.item_send_customer_redpacket)


    override fun convert(helper: BaseViewHolder, item: Custom) {
        PhotoUtil.loadImageByGlide(mContext, item.headImage, helper.getView(R.id.avatar),R.drawable.img_customer_cus)
//选择框
        val checkBox = helper.getView<ImageView>(R.id.checkbox)

        helper.getView<View>(R.id.itemRL).setOnClickListener {
            val intent = Intent(mContext, CustomerMemberDetialActivity::class.java)
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


        // 自由客户标识 1-是|0-否
        helper.getView<View>(R.id.text_customer_type_tag).visibility = if (item.freecustomer == "1") View.VISIBLE else View.INVISIBLE

        helper.setText(R.id.text_customer_register, "${item.registerTime}注册")
                .setText(R.id.text_customer_name, item.userName)
                .setText(R.id.text_customer_phone, item.mobile)
                .setText(R.id.text_customer_near_invest, "最近投资：${item.nearInvestAmt}元")
                .setText(R.id.text_customer_invest_stroke_count, "投资笔数：${item.totalInvestCount}")
                .setText(R.id.text_customer_invest_total_money, "累计投资：${item.totalInvestAmt}元")
                .setText(R.id.text_customer_invest_enddate, if (item.nearEndDate.isNullOrBlank()) "无投资到期" else "${item.nearEndDate}有投资到期")
                .setVisible(R.id.text_customer_invest_enddate, !item.nearEndDate.isNullOrBlank())

        var imgImport = helper.getView<ImageView>(R.id.img_customer_import)
        imgImport.visibility = View.VISIBLE
        when {
            "true" == item.newRegist -> imgImport.setImageResource(R.drawable.img_customer_new_tag)
            "true" == item.important -> imgImport.setImageResource(R.drawable.img_import_icon)
            else -> imgImport.visibility = View.INVISIBLE
        }

    }

    fun getCheckedData(): Custom? {
        return if (checkedPosition in 0 until data.size) data[checkedPosition] else null

    }

    override fun setNewData(data: MutableList<Custom>?) {
        headTextList.clear()
        checkedPosition = -1
        super.setNewData(data)
    }


}