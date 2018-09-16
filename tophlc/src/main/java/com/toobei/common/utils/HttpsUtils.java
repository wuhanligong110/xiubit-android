package com.toobei.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.toobei.common.R;
import com.toobei.common.TopApp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.xsl781.utils.FileUtils;
import org.xsl781.utils.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpsUtils {

    public static HttpClient getNewHttpClient(Context context, boolean isHttps) {

        if (!isHttps || TopApp.getInstance().IS_HTTP_TEST) {
            return new DefaultHttpClient();
        }
        InputStream ins = TopApp.getInstance().getResources().openRawResource(R.raw.server);
        //	return new DefaultHttpClient();
        try {

            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509"); //问1
            Certificate cer = cerFactory.generateCertificate(ins);

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            trustStore.setCertificateEntry("trust", cer);

            //把咱的证书库作为信任证书库
            SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
            Scheme sch = new Scheme("https", socketFactory, 443);
            //完工
            HttpClient mHttpClient = new DefaultHttpClient();
            mHttpClient.getConnectionManager().getSchemeRegistry().register(sch);
            //    trustStore.load(ins, null);
            //  trustStore.load(ins, "hlc@xiaoniu.com".toCharArray());
            /*      SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
                  sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			      HttpParams params = new BasicHttpParams();  
			      HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);  
			      HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);  
			
			      SchemeRegistry registry = new SchemeRegistry();  
			      registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));  
			      registry.register(new Scheme("https", sf, 443));  
			
			      ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);  

			      HttpClient client = new DefaultHttpClient(ccm, params);*/

			/*	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (!wifiManager.isWifiEnabled()) {
					// 获取当前正在使用的APN接入点
					Uri uri = Uri.parse("content://telephony/carriers/preferapn");
					Cursor mCursor = context.getContentResolver().query(uri, null, null, null, null);
					if (mCursor != null && mCursor.moveToFirst()) {
						// 游标移至第一条记录，当然也只有一条
						String proxyStr = mCursor.getString(mCursor.getColumnIndex("proxy"));
						if (proxyStr != null && proxyStr.trim().length() > 0) {
							HttpHost proxy = new HttpHost(proxyStr, 80);
							client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
						}
						mCursor.close();
					}
					return  new DefaultHttpClient();
				}*/
            //No permission to write APN settings: Neither user 10509 nor current process has android.permission.WRITE_APN_SETTINGS.

            return mHttpClient;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HttpsUtils", "exception");
            return new DefaultHttpClient();
        }
    }

    /**
     * 发送get请求
     *
     * @param url            请求地址
     * @param param          请求参数
     * @param readTimeout    数据读取时间（毫秒）
     * @param connectTimeout 连接超时时间（毫秒）
     * @return
     * @throws Exception
     */
    public static String sendGetRequest(HttpClient httpClient, String url, String param, int readTimeout, int connectTimeout) throws Exception {
        HttpGet httpGet = new HttpGet(url + "?" + param);
        HttpEntity httpEntity = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception(httpResponse.getStatusLine().toString());
            } else {
                httpEntity = httpResponse.getEntity();
                String result = null;
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }
                return result;
            }
        } catch (Exception e) {
            throw new Exception("异常情况", e);
        } finally {
            httpGet.abort();
            //EntityUtils.consume(httpEntity);
            httpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * 发送post请求
     *
     * @param url            请求地址
     * @param mapparam       参数
     * @param coding         编码
     * @param readTimeout    数据读取时间（毫秒）
     * @param connectTimeout 连接超时时间（毫秒）
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(HttpClient httpClient, String url, Map<String, String> mapparam, String coding, int readTimeout, int connectTimeout) throws Exception {
        httpClient.getParams().setIntParameter("http.socket.timeout", readTimeout);
        httpClient.getParams().setIntParameter("http.connection.timeout", connectTimeout);
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (mapparam != null) {
            // 先迭代HashMap
            Iterator<Map.Entry<String, String>> it = mapparam.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                nvps.add(new BasicNameValuePair(key, entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, coding));
        }
        HttpResponse httpResponse = null;
        HttpEntity httpEntity = null;
        if (TopApp.getInstance().IS_DEBUG) {
            Log.d("sendPostRequest", "====request url=" + url + "\\n param=" +StringUtils.formatJson(JSON.toJSONString(mapparam)) );
        }

        try {
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception(httpResponse.getStatusLine().toString());
            } else {
                httpEntity = httpResponse.getEntity();
                String result = null;
                if (httpEntity != null) {
                    result = EntityUtils.toString(httpEntity);
                }
                if (TopApp.getInstance().IS_DEBUG) {
                    Log.d("sendPostRequest", "====response url=" + url + "\\n result=" + StringUtils.formatJson(result));
                }
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("异常情况", e);
        } finally {
            httpPost.abort();
            //EntityUtils.consume(httpEntity);
            httpClient.getConnectionManager().shutdown();
        }
    }

    public static String sendPostRequest(HttpClient httpClient, String url, Map<String, String> map) throws Exception {
        return sendPostRequest(httpClient, url, map, "utf-8", 20000, 8000);
    }

    public static String sendPostRequest(boolean isHttps, Map<String, String> map) throws Exception {
        return sendPostRequest(HttpsUtils.getNewHttpClient(TopApp.getInstance(), isHttps), TopApp.getInstance().getHttpService().getBaseUrl(isHttps), map, "utf-8", 20000, 8000);
    }

    public static String sendPostRequest(boolean isHttps, Map<String, String> map, String url) throws Exception {
        return sendPostRequest(HttpsUtils.getNewHttpClient(TopApp.getInstance(), isHttps), TopApp.getInstance().getHttpService().getBaseUrl(isHttps) + url, map, "utf-8", 20000, 8000);
    }



    /**
     * 金融超市 接口调用
     *
     * @param map
     * @param url
     * @return
     * @throws Exception
     */
    public static String sendPostRequest(Map<String, String> map, String url) throws Exception {
        return sendPostRequest(HttpsUtils.getNewHttpClient(TopApp.getInstance(), false), TopApp.getInstance().getHttpService().getBaseUrl(false) + url, map, "utf-8", 20000, 8000);
    }

    public static String sendPostRequest(boolean isHttps, Map<String, String> map, int readTimeout, int connectTimeout) throws Exception {
        return sendPostRequest(HttpsUtils.getNewHttpClient(TopApp.getInstance(), isHttps), TopApp.getInstance().getHttpService().getBaseUrl(isHttps), map, "utf-8", readTimeout, connectTimeout);
    }

    public static String sendPostRequest(Map<String, String> map) throws Exception {
        return sendPostRequest(false, map);
    }

    public static String postMultiParams(HttpClient httpClient, String actionUrl, Map<String, String> params, Map<String, File> files) throws IOException {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri;

        uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }
        }
        InputStream is = null;
        DataOutputStream outStream = null;
        try {
            outStream = new DataOutputStream(conn.getOutputStream());
            outStream.write(sb.toString().getBytes());
            // 发送文件数据
            if (files != null) for (Map.Entry<String, File> file : files.entrySet()) {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
                sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
                sb1.append(LINEND);
                outStream.write(sb1.toString().getBytes());

                is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINEND.getBytes());
            }

            //请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(is, outStream);
        }

        // 得到响应码
        int res;
        InputStream in = null;
        StringBuilder respond = new StringBuilder();
        BufferedReader reader = null;
        try {
            res = conn.getResponseCode();

            if (res == 200) {
                in = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                int len = 0;
                char[] buf = new char[1024];
                while ((len = reader.read(buf)) != -1) {
                    respond.append(buf, 0, len);
                }
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.closeIO(in, reader, outStream);
        }
        return respond.toString();
    }

    public static String uploadFile(HttpClient httpClient, String actionUrl, File file) {
        HttpResponse response;
        try {
            //准备上传图片数据
            byte[] buffer = null;
            InputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();

            //开始上传
            HttpPost httppost = new HttpPost(actionUrl);

            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(buffer);
            httppost.setEntity(byteArrayEntity);
            httppost.addHeader("Content-Type", "jpeg");

            System.out.println("executing request " + httppost.getRequestLine());
            //	Header[] headers = httppost.getAllHeaders();
            response = httpClient.execute(httppost);

            System.out.println(response.getStatusLine());
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                System.out.println("----------------Response-----------------------" + resp);
                return resp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;

    }

    /**
     * 图片压缩上传 100kb
     *
     * @param httpClient
     * @param actionUrl
     * @param file
     * @return
     */
    public static String uploadFileComPress(HttpClient httpClient, String actionUrl, File file) {

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        HttpResponse response;
        try {
            //准备上传图片数据
            byte[] buffer = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            bos.close();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            int options = 100;
            while (bos.toByteArray().length / 1024 > 100 && options > 0) {
                bos.reset();
                options -= 10;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
            }

            buffer = bos.toByteArray();

            //开始上传
            HttpPost httppost = new HttpPost(actionUrl);

            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(buffer);
            httppost.setEntity(byteArrayEntity);
            httppost.addHeader("Content-Type", "jpeg");

            System.out.println("executing request " + httppost.getRequestLine());
            //	Header[] headers = httppost.getAllHeaders();
            response = httpClient.execute(httppost);

            System.out.println(response.getStatusLine());
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                String resp = EntityUtils.toString(resEntity);
                System.out.println("----------------Response-----------------------" + resp);
                return resp;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;

    }

}
