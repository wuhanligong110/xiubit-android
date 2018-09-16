package com.v5ent.xiubit.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet

/**
 * 禁用对应RecyclerView方向的滑动，解决嵌套SrollView卡顿的问题
 * Created by hasee-pc on 2017/12/27.
 */
class NestedScrollLinearLayoutManager : LinearLayoutManager {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun canScrollVertically(): Boolean {
        return orientation != VERTICAL
    }

    override fun canScrollHorizontally(): Boolean {
        return orientation != HORIZONTAL
    }
}