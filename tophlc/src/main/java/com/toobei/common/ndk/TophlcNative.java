package com.toobei.common.ndk;

/**
 * Created by user on 2016/12/20.
 */

public class TophlcNative {

    static {
        System.loadLibrary("tophlc-native");
    }

    public native String getTbAppSecret();

    public native String getLcdsAppSecret();

}
