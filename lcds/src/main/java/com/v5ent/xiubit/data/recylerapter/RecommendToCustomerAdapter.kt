package com.v5ent.xiubit.data.recylerapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.toobei.common.entity.Custom
import com.toobei.common.utils.PhotoUtil
import com.v5ent.xiubit.R

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/20
 */
class RecommendToCustomerAdapter : BaseQuickAdapter<Custom, BaseViewHolder> {
    private var checkedPositionList: ArrayList<Int> = ArrayList()
    private var headTextList: ArrayList<String> = ArrayList()
    private var headTextPostionList: ArrayList<Int> = ArrayList()
    var onCheckedListener: OnCheckedListener? = null

    interface OnCheckedListener {
        fun onCheckedChange(checkedNum: Int)
    }

    constructor() : super(R.layout.item_recommend_to_cfg)


    override fun convert(helper: BaseViewHolder, item: Custom) {
        PhotoUtil.loadImageByGlide(mContext, item.headImage, helper.getView(R.id.avatar), R.drawable.img_customer_cus)
//选择框
        val position = helper.adapterPosition
        val checkBox = helper.getView<ImageView>(R.id.checkbox)

        checkBox.isSelected = checkedPositionList.contains(position)

        checkBox.setOnClickListener {
            checkBox.isSelected = !checkBox.isSelected
            if (checkBox.isSelected && !checkedPositionList.contains(position)) {
                checkedPositionList.add(position)
            }

            if (!checkBox.isSelected && checkedPositionList.contains(position)) {
                checkedPositionList.remove(position)
            }

            onCheckedListener?.onCheckedChange(checkedPositionList.size)
        }

        //标题头
        if (!headTextList.contains(item.firstLetter)) {
            headTextList.add(item.firstLetter)
            headTextPostionList.add(helper.adapterPosition)
        }
        helper.setText(R.id.alais_scort_letter, item.firstLetter)
                .setVisible(R.id.alais_scort_letter, headTextPostionList.contains(helper.adapterPosition))



        helper.setText(R.id.text_customer_name, "${if (item.userName.startsWith("1")) "未认证" else item.userName}")
                .setText(R.id.text_customer_phone, item.mobile)


    }

    fun getCheckedData(): List<Custom> {
        return data.filterIndexed { index, _ ->
            checkedPositionList.contains(index)
        }

    }

    override fun setNewData(data: MutableList<Custom>?) {
        headTextList.clear()
        checkedPositionList.clear()
        headTextPostionList.clear()
        super.setNewData(data)
    }

    fun checkedAll(checkedAll: Boolean) {
        checkedPositionList.clear()
        if (checkedAll) {
            for (i in 0 until data.size) {
                checkedPositionList.add(i)
            }
        }
        onCheckedListener?.onCheckedChange(checkedPositionList.size)
        notifyDataSetChanged()
    }


}