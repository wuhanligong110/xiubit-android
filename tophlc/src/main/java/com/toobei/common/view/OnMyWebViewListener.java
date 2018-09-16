package com.toobei.common.view;

import android.webkit.WebView;

public interface OnMyWebViewListener {
        /**
         * 功能：收到的web中标题
         *
         * @param view
         * @param title
         */
        void onReceivedTitle(String title);

        /**
         * 重定向监听回调
         * @param isRedirectUsable
         * @param url
         */
        void onUrlRedirectCallBack(boolean isRedirectUsable, String url);

        /**
         * 功能：重定向url
         *
         * @param view
         * @param url
         */
        void onUrlLoading(boolean isRedirectUsable, String url);

        /**
         * 功能：页面加载完成后
         *
         * @param view
         * @param url
         */
        void onPageFinished(String url);

        void onPageStart(String url);

        void onLoadError(String url);
    }