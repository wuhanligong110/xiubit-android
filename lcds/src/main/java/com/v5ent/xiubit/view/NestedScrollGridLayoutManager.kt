package com.v5ent.xiubit.view

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet

/**
 * 禁用对应RecyclerView方向的滑动，解决嵌套SrollView卡顿的问题
 * Created by hasee-pc on 2017/12/27.
 */
class NestedScrollGridLayoutManager : GridLayoutManager {

    constructor(context: Context?, spanCount: Int) : super(context, spanCount)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, spanCount: Int, orientation: Int, reverseLayout: Boolean) : super(context, spanCount, orientation, reverseLayout)


    override fun canScrollVertically(): Boolean {
        return orientation != VERTICAL
    }

    override fun canScrollHorizontally(): Boolean {
        return orientation != HORIZONTAL
    }
}