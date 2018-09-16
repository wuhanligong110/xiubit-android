package com.v5ent.xiubit.data.recyclerDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hasee-pc on 2017/12/28.
 */

public class HorizontalSpacItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;
    private int edgeSpacing;
    private boolean includeEdge;


    public HorizontalSpacItemDecoration(int spacing, boolean includeEdge) {
        this.spacing = spacing;
        edgeSpacing = spacing;
        this.includeEdge = includeEdge;
    }

    public HorizontalSpacItemDecoration(int spacing, int edgeSpacing) {
        this.spacing = spacing;
        this.edgeSpacing = edgeSpacing;
        this.includeEdge = true;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int itemCount = parent.getAdapter().getItemCount();
        if (itemCount == 1) {
            outRect.left = includeEdge? edgeSpacing:0;
            outRect.right = includeEdge? edgeSpacing:0;
        }else if (position == 0) {
            outRect.left = includeEdge ? edgeSpacing : 0;
            outRect.right = spacing;
        } else if (position == itemCount - 1) {
            outRect.left = 0;
            outRect.right = includeEdge ? edgeSpacing : 0;
        } else {
            outRect.left = 0;
            outRect.right = spacing;
        }
    }
}
