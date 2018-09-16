package org.xsl781.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utils {

	public static void alertDialog(Activity activity, String s) {
		new AlertDialog.Builder(activity).setMessage(s).show();
	}

	public static void alertDialog(Activity activity, int msgId) {
		new AlertDialog.Builder(activity).setMessage(activity.getString(msgId)).show();
	}

	/**
	 * 功能：得到方程式 "%d+%d=%d"
	 * @param finalNum  
	 * @param delta > 0 时  "%d+%d=%d" <0"%d-%d=%d"
	 * @return
	 */
	public static String getEquation(int finalNum, int delta) {
		String equation;
		int abs = Math.abs(delta);
		if (delta >= 0) {
			equation = String.format("%d+%d=%d", finalNum - delta, abs, finalNum);
		} else {
			equation = String.format("%d-%d=%d", finalNum - delta, abs, finalNum);
		}
		return equation;
	}

	public static Uri getCacheUri(String path, String url) {
		Uri uri = Uri.parse(url);
		uri = Uri.parse("cache:" + path + ":" + uri.toString());
		return uri;
	}

	@SuppressLint("NewApi")
	public static String getStringByFile(File f) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;
		while ((line = br.readLine()) != null) {
			builder.append(line);
		}
		br.close();
		return builder.toString();
	}

	public static String getShortUrl(String longUrl) throws IOException, JSONException {
		if (longUrl.startsWith("http") == false) {
			throw new IllegalArgumentException("longUrl must start with http");
		}
		String url = "https://api.weibo.com/2/short_url/shorten.json";
		HttpPost post = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("access_token", "2.00_hkjqBR1dbuCc632289355qerfeD"));
		params.add(new BasicNameValuePair("url_long", longUrl));
		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse res = new DefaultHttpClient().execute(post);
		if (res.getStatusLine().getStatusCode() == 200) {
			String str = EntityUtils.toString(res.getEntity());
			JSONObject json = new JSONObject(str);
			JSONArray arr = json.getJSONArray("urls");
			JSONObject urls = arr.getJSONObject(0);
			if (urls.getBoolean("result")) {
				return urls.getString("url_short");
			} else {
				return null;
			}
		}
		return null;
	}

	public static String getStrByRawId(Context ctx, int id) throws UnsupportedEncodingException {
		InputStream is = ctx.getResources().openRawResource(id);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String line;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}

	public static void openUrl(Context context, String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		context.startActivity(i);
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
		//return myUUID();
	}

	public static String myUUID() {
		StringBuilder sb = new StringBuilder();
		int start = 48, end = 58;
		appendChar(sb, start, end);
		appendChar(sb, 65, 90);
		appendChar(sb, 97, 123);
		String charSet = sb.toString();
		StringBuilder sb1 = new StringBuilder();
		for (int i = 0; i < 24; i++) {
			int len = charSet.length();
			int pos = new Random().nextInt(len);
			sb1.append(charSet.charAt(pos));
		}
		return sb1.toString();
	}

	public static void appendChar(StringBuilder sb, int start, int end) {
		int i;
		for (i = start; i < end; i++) {
			sb.append((char) i);
		}
	}

	public static boolean isListNotEmpty(Collection<?> collection) {
		return collection != null && collection.size() > 0;
	}

	public static void closeBoard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
		if (imm.isActive()) //一直是true
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 功能：显示虚拟键盘
	 * @param v
	 */
	public static void showSoftInputView(Activity ctx) {
		InputMethodManager manager = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View currentFocus = ctx.getCurrentFocus();
		if (currentFocus != null) {
			manager.showSoftInput(currentFocus, InputMethodManager.SHOW_FORCED);
		}
	}

	/**
	 * 功能：显示虚拟键盘
	 * @param ctx 
	 * @param view 接受键盘事件的视图
	 */
	public static void showSoftInputView(Activity ctx, View view) {
		InputMethodManager manager = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	public static void hideSoftInputView(Activity ctx) {
		InputMethodManager manager = (InputMethodManager) ctx
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View currentFocus = ctx.getCurrentFocus();
		if (currentFocus != null) {
			manager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
					InputMethodManager.RESULT_UNCHANGED_SHOWN);
		}
	}

	public static boolean doubleEqual(double a, double b) {
		return Math.abs(a - b) < 1E-8;
	}

	public static String exception2String(Throwable t) throws IOException {
		if (t == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			t.printStackTrace(new PrintStream(baos));
		} finally {
			baos.close();
		}
		return baos.toString();
	}

	/**
	 * 功能：按默认排序，用","拼接成字符串
	 * @param members
	 * @return
	 */
	public static String list2Str(List<String> members) {
		Collections.sort(members);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < members.size(); i++) {
			sb.append(members.get(i));
			if (i < members.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 功能：将字符串按","分割，组成list
	 * @param str
	 * @return
	 */
	public static List<String> str2List(String str) {
		String[] strs = str.split(",");
		List<String> list = new ArrayList<String>();
		for (String string : strs) {
			list.add(string);
		}
		return list;
	}

	/**
	* URL检查
	* @param pInput     要检查的字符串
	* @return boolean   返回检查结果
	*/
	public static boolean isUrl(String pInput) {
		if (pInput == null) {
			return false;
		}
		String regEx = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
				+ "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
				+ "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
				+ "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
				+ "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
				+ "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
				+ "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
				+ "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(pInput);
		return matcher.matches();
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

}
