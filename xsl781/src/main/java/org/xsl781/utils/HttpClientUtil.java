package org.xsl781.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param param
	 *            请求参数
	 * @param readTimeout
	 *            数据读取时间（毫秒）
	 * @param connectTimeout
	 *            连接超时时间（毫秒）
	 * @return
	 * @throws Exception
	 */
	public static String sendGetRequest(String url, String param, int readTimeout,
			int connectTimeout) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();

		httpClient.getParams().setIntParameter("http.socket.timeout", readTimeout);
		httpClient.getParams().setIntParameter("http.connection.timeout", connectTimeout);

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
	 * @param url
	 *            请求地址
	 * @param mapparam
	 *            参数
	 * @param coding
	 *            编码
	 * @param readTimeout
	 *            数据读取时间（毫秒）
	 * @param connectTimeout
	 *            连接超时时间（毫秒）
	 * @return
	 * @throws Exception
	 */
	public static String sendPostRequest(String url, Map<String, String> mapparam, String coding,
			int readTimeout, int connectTimeout) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();
		
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
				return result;
			}
		} catch (Exception e) {
			throw new Exception("异常情况", e);
		} finally {
			httpPost.abort();
			//	EntityUtils.consume(httpEntity);
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static String sendPostRequest(String url, Map<String, String> map) throws Exception {
		return sendPostRequest(url, map, "utf-8", 5000, 60000);
	}

	public static String postMultiParams(String actionUrl, Map<String, String> params,
			Map<String, File> files) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
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

		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据 
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
						+ file.getKey() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
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
		// 得到响应码 
		int res = conn.getResponseCode();
		InputStream in = null;
		if (res == 200) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		outStream.close();
		conn.disconnect();
		if (in != null) {
			return in.toString();
		}
		return null;
	}

}
