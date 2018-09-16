package com.toobei.common.view.popupwindow

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.toobei.common.R

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/7/7
 */

class SelectPhotoWayPopup(val ctx: Activity, val listener: ClickListener) : PopupWindow(ctx), View.OnClickListener {
    companion object {
        interface ClickListener {
            fun onTakePhoteClick()
            fun onPhotoAlbumClick()
        }
    }


    init {
        initView()
    }

    private fun initView() {
        contentView = LayoutInflater.from(ctx).inflate(R.layout.layout_select_photo_way_popu_window, null)
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.isFocusable = true
        this.isOutsideTouchable = true
        // 刷新状态
        this.update()
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(ContextCompat.getColor(ctx, R.color.Color_0))
        this.setBackgroundDrawable(dw)// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.anim_popup_share

        contentView.findViewById<View>(R.id.takePhoto).setOnClickListener {
            dismiss()
            listener.onTakePhoteClick()
        }
        contentView.findViewById<View>(R.id.photoAlbum).setOnClickListener {
            dismiss()
            listener.onPhotoAlbumClick()
        }
        contentView.findViewById<View>(R.id.cancel).setOnClickListener { dismiss() }

    }

    /**
     * 显示popupWindow
     * @param parent
     */
    fun showPopupWindow(parent: View) {
        backgroundAlpha(0.5f)
        showAtLocation(parent, Gravity.BOTTOM, 0, 0)
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    fun backgroundAlpha(bgAlpha: Float) {
        val lp = ctx.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        ctx.window.attributes = lp
    }

    override fun dismiss() {
        backgroundAlpha(1f)
        super.dismiss()
    }

    override fun onClick(v: View) {
        dismiss()
    }


}