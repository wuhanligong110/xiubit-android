package com.toobei.common.network;


import com.toobei.common.TopApp;

import org.xsl781.utils.Logger;
import org.xsl781.utils.SystemTool;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/9/5
 */

public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Logger.e("intercept");
        Request request = chain.request();
        if (!SystemTool.checkNet(TopApp.getInstance())) {
            Logger.e("request-FORCE_CACHE");
            request = request
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)  //无网络强制读取缓存
                    .build();
        }
        Response response = chain.proceed(request);

        if (SystemTool.checkNet(TopApp.getInstance())) {
            int maxAge = 60; // read from cache for 1 minute   //有网络情况下读取一分钟内的缓存
            Logger.e("response, max-age=" + maxAge);
            response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60; // tolerate 1 hour stale  //无网络下，只要缓存不超过一小时内，即读取缓存
            Logger.e("response, public, only-if-cached, max-stale=" + maxStale);
            response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        return response;
    }


       
}
