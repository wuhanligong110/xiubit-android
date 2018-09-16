package com.v5ent.xiubit.view.popupwindow

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.toobei.common.utils.ToastUtil
import com.toobei.common.view.flowlayout.FlowLayout
import com.toobei.common.view.flowlayout.TagAdapter
import com.toobei.common.view.flowlayout.TagFlowLayout
import com.v5ent.xiubit.R

/**
 * Created by hasee-pc on 2017/12/19.
 */
class InsuranceTestSelectPopu(val ctx: Context
                              , val strArrs: ArrayList<String>
                              , val isSingleSelect: Boolean  //是否单选
                              , val mNoCoExistStr: String?    //这个字符串对应的选项，它跟其他选项不能共存
                              , val mForeverChecked: HashSet<Int>? = HashSet<Int>()
                              , val listener: OnEventListener? = null

) : PopupWindow(ctx) {

    var selectedTags: HashSet<Int> = HashSet()
     var adapter: SelectTagAdapter? = null

    init {
        initWindow()
        initView()
    }

    private fun initView() {

        val contantFl = contentView.findViewById<TagFlowLayout>(R.id.contantFl)
        val confirmBtn = contentView.findViewById<View>(R.id.confirmBtn)
        confirmBtn.visibility = if (isSingleSelect) View.GONE else View.VISIBLE
        contantFl.setMaxSelectCount(if (isSingleSelect) 1 else -1)
        adapter = SelectTagAdapter(strArrs, ctx)
        contantFl.setAdapter(adapter, { selectPosSet ->
            selectedTags = selectPosSet as HashSet<Int>
            if (isSingleSelect) confirmSelect()
        })

        confirmBtn.setOnClickListener {
            confirmSelect()
        }

    }

    private fun confirmSelect() {
        if (selectedTags.isNotEmpty()) {
            val arrList =  ArrayList<String>()
            selectedTags.forEach { arrList.add(adapter!!.getData()[it]) }
            listener?.onConfirmListener(selectedTags,arrList)
            dismiss()
        } else {
            ToastUtil.showCustomToast("您还未做选择")
        }
    }

    private fun initWindow() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_insurance_test_popu, null)
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.isFocusable = false

        // 刷新状态
        this.update()
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(ContextCompat.getColor(ctx, R.color.Color_0))
        this.setBackgroundDrawable(dw)// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.isOutsideTouchable = false
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.anim_popup_share
    }


    inner class SelectTagAdapter(val strArrs: ArrayList<String>, val ctx: Context) : TagAdapter<String>(strArrs) {

        override fun getView(parent: FlowLayout?, position: Int, str: String?): View {
            val itemView = LayoutInflater.from(ctx).inflate(R.layout.item_insurance_test_popu, null)
            val tv = itemView.findViewById<TextView>(R.id.tv)
            tv.text = str
            return itemView
        }

        override fun onSelected(position: Int, view: View?) {
            super.onSelected(position, view)
            val tv = view?.findViewById<TextView>(R.id.tv)
            tv?.isSelected = true
        }

        override fun unSelected(position: Int, view: View?) {
            super.unSelected(position, view)
            val tv = view?.findViewById<TextView>(R.id.tv)
            tv?.isSelected = false
        }

        override fun getForeverChecked(): HashSet<Int> {
            return if (mForeverChecked == null) HashSet<Int>() else mForeverChecked
        }

        override fun getNoCoExistStr(): String? {
            return mNoCoExistStr
        }

        fun getData() : ArrayList<String> = mTagDatas as ArrayList<String>


    }

    interface OnEventListener {
        fun onConfirmListener(selectedTagsPos: HashSet<Int>,selectedTagsStrArr : ArrayList<String>)
    }

    /**
     * 显示popupWindow
     * @param parent
     */
    fun showPopupWindow(parent: View) {
        try {
            if (ctx is Activity && !ctx.isFinishing) {
                showAtLocation(parent, Gravity.BOTTOM, 0, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}