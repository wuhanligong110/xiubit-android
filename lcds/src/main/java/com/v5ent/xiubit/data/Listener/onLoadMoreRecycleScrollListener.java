package com.v5ent.xiubit.data.Listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.xsl781.utils.Logger;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/4/14
 */

public abstract class onLoadMoreRecycleScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向最后一个滑动
    boolean isSlidingToLast = false;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部，并且是向右滚动
            if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast ) {
                Logger.e("lastVisibleItem ==" + lastVisibleItem+"### totalItemCount == " +totalItemCount);
                //加载更多功能的代码
                onloadMore();
            }
        }
    }

    /**
     * 子类在这里具体实现加载更多
     */
    public abstract void onloadMore();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dy > 0) {
            //大于0表示正在向右滚动
            isSlidingToLast = true;
        } else {
            //小于等于0表示停止或向左滚动
            isSlidingToLast = false;
        }
    }

}
