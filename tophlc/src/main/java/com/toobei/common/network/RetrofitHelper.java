package com.toobei.common.network;

import com.toobei.common.BuildConfig;
import com.toobei.common.R;
import com.toobei.common.TopApp;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tech.linjiang.pandora.Pandora;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/8/16
 */


/**
 * 公司: tophlc
 * 类说明:  用于获取Retrofit对象
 *
 * @author hasee-pc
 * @time 2017/3/31
 */

public class    RetrofitHelper {

    private static RetrofitHelper service;
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit mHttpRetrofit;
    private Retrofit mHttpsRetrofit;
    private Retrofit mMockRetrofit;
    private Retrofit mNoAPiRetrofit;


    private RetrofitHelper() {
    }

    public static synchronized RetrofitHelper getInstance() {
        if (service == null) {
            service = new RetrofitHelper();
        }
        return service;
    }

    private boolean isLogOpen(){
        return TopApp.getInstance().isLoginOpen();
    }

    public Retrofit getMockRetrofit() {
        if (mMockRetrofit == null) {
            String baseUrl = "http://10.16.0.204:8080/mockjs/8/";
//        CommonInterceptor commonInterceptor = new CommonInterceptor(params);   //get的方式添加固定参数
            //网络请求日志
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(isLogOpen() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            //缓存策略仅仅对get请求有效
//        File cacheFile = new File(MyApp.getInstance().getCacheDir(),"httpCache");
//        if (!cacheFile.exists()) {
//            cacheFile.mkdirs();
//        }
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(Pandora.get().getInterceptor())
//                .addInterceptor(new HttpCacheInterceptor())
//                .cache(cache)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            // 使用RxJava作为回调适配器
            // 使用Gson作为数据转换器

            mMockRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                    .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                    .build();
        }
        return mMockRetrofit;
    }

    /**
     * 初始化Retrofit，传入BaseUrl，配置OkHttpClient
     * basUrl 中移除 api 字符串
     *
     * @return
     */
    public Retrofit getNoApiRetrofit() {
        if (mNoAPiRetrofit == null) {
            String baseUrl = TopApp.getInstance().getHttpService().getBaseUrl(false).replace("api", "");
            //网络请求日志
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(isLogOpen() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(Pandora.get().getInterceptor())
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            // 使用RxJava作为回调适配器
            // 使用Gson作为数据转换器

            mNoAPiRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                    .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                    .build();
        }
        return mNoAPiRetrofit;
    }


    /**
     * 初始化Retrofit，传入BaseUrl，配置OkHttpClient
     *
     * @return
     */
    public Retrofit getRetrofit() {
        if (mHttpRetrofit == null) {
            String baseUrl = TopApp.getInstance().getHttpService().getBaseUrl(false) + "/";
//        CommonInterceptor commonInterceptor = new CommonInterceptor(params);   //get的方式添加固定参数
            //网络请求日志
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(isLogOpen() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

            //缓存策略仅仅对get请求有效
//        File cacheFile = new File(MyApp.getInstance().getCacheDir(),"httpCache");
//        if (!cacheFile.exists()) {
//            cacheFile.mkdirs();
//        }
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
//                .addInterceptor(new HttpCacheInterceptor())
//                .cache(cache)
                    .addInterceptor(Pandora.get().getInterceptor())
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            // 使用RxJava作为回调适配器
            // 使用Gson作为数据转换器

            mHttpRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                    .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return mHttpRetrofit;
    }


    public Retrofit getRetrofit(boolean isHttps) {
        if (!isHttps || BuildConfig.DEBUG) return getRetrofit();
        String baseUrl = TopApp.getInstance().getHttpService().getBaseUrl(true) + "/";
//        CommonInterceptor commonInterceptor = new CommonInterceptor(params);   //get的方式添加固定参数
        //网络请求日志
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //证书
        InputStream ins = TopApp.getInstance().getResources().openRawResource(R.raw.server);
        try {

            if (mHttpsRetrofit == null) {
                CertificateFactory cerFactory = CertificateFactory.getInstance("X.509"); //问1
                Certificate cer = cerFactory.generateCertificate(ins);

                KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                trustStore.load(null, null);
                trustStore.setCertificateEntry("trust", cer);

                //把咱的证书库作为信任证书库
                SSLContext sslContext = SSLContext.getInstance("TLS");
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(trustStore);

                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:"
                            + Arrays.toString(trustManagers));
                }
                X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

                sslContext.init(null, new TrustManager[]{trustManager}, null);
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                //手动创建一个OkHttpClient并设置超时时间
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .addInterceptor(Pandora.get().getInterceptor())
                        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                        .sslSocketFactory(sslSocketFactory, trustManager)
                        .build();


                // 使用RxJava作为回调适配器
                // 使用Gson作为数据转换器

                mHttpsRetrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                        .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                        .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //以防万一，切换http方式
        if (mHttpsRetrofit == null) return getRetrofit();
        return mHttpsRetrofit;
    }


}
