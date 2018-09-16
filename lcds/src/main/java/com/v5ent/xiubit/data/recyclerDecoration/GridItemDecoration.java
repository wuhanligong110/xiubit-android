package com.v5ent.xiubit.data.recyclerDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int divideBottom;
    private int divideRight;
    private int divideTop;
    private int divideLeft;
    private int divide;  //间距
    private int lineNumber;  //行数
    private boolean rightDividEnable = true;

    public GridItemDecoration(int divide, int lineNumber) {
        this.divide = divide;
        this.divideLeft = divide;
        this.divideTop = divide;
        this.divideRight = divide;
        this.divideBottom = divide;
        this.lineNumber = lineNumber;
    }

    public GridItemDecoration(int divideLeft,int divideTop,int divideRight,int divideBottom, int lineNumber) {
        this.divideLeft = divideLeft;
        this.divideTop = divideTop;
        this.divideRight = divideRight;
        this.divideBottom = divideBottom;
        this.lineNumber = lineNumber;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //不是第一个的格子都设一个左边和底部的间距
        outRect.top = divideTop;
        outRect.right = divideRight;
        outRect.bottom = divideBottom;
        //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % lineNumber == 0) {
            outRect.left = 0;
        }else {
            outRect.left = divideLeft;
        }
        
        if (!rightDividEnable){
            if (parent.getChildLayoutPosition(view) % lineNumber == 2) {
                outRect.right = 0;
            }
        }
    }

}