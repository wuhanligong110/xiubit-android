package com.toobei.common.view;

import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

/**
 * 公司: tophlc
 * 类说明:WebChromeClient上传文件监听
 *
 * @author yangLin
 * @time 2017/9/25
 */

public interface WebViewUpDataListener {
    
    //Android < 5.0 用于 openFileChooser 方法回调
    void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType);
    //Android > 5.0 用于onShowFileChooser 方法回调
    void openFileChooserCallBack(ValueCallback<Uri[]> uploadMsg,WebChromeClient.FileChooserParams fileChooserParams);
}
