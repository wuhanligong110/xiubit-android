/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.toobei.common.view.loopviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自动滚动的viewPager
 */
public class AutoScrollViewPager extends ViewPager {


    private static final long ANTOSCRRLL_TIME = 3000; //自动滑动时间
    //自动滑动handler
    private AutoScrollHandler autoScrollHandler = new AutoScrollHandler();
/****自动计算控件高度，如果想自适应图片宽高就要使用这个，但是开启了这段代码，使用LayoutParams动态设置控件宽高是无用的***/
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int height = 0;
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height)
//                height = h;
//        }
//
//        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);


        //按下去的时候停止轮播
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        autoScrollHandler.pause = true;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        autoScrollHandler.pause = false;
                        break;
                }

                return false;
            }
        });
    }


    public AutoScrollViewPager(Context context) {
        super(context);

    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * 开始滚动
     */
    public void startloop() {
        if (getAdapter().getCount() > 1) {
            autoScrollHandler.startLoop();
        }
    }

    /**
     *
     */
    public void stopLoop() {
        autoScrollHandler.stopLoop();

    }

    /**
     * 自动滚动的handler
     */
    class AutoScrollHandler extends Handler {
        boolean pause = false;

        @Override
        public void handleMessage(Message msg) {
            if (!pause) {
                int item = getCurrentItem() + 1;
                setCurrentItem(item,true);
            }
            sendEmptyMessageDelayed(msg.what, ANTOSCRRLL_TIME);
        }

        void startLoop() {
            pause = false;
            removeCallbacksAndMessages(null);
            sendEmptyMessageDelayed(1, ANTOSCRRLL_TIME);
        }

        void stopLoop() {
            removeCallbacksAndMessages(null);
        }
    }


}
