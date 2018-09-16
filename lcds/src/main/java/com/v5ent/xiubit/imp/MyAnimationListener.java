package com.v5ent.xiubit.imp;

import android.view.animation.Animation;

public abstract class MyAnimationListener implements Animation.AnimationListener {


        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public abstract void onAnimationEnd(Animation animation);

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }