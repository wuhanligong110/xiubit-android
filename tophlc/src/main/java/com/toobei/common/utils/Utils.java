package com.toobei.common.utils;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;

import com.toobei.common.entity.Contacts;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.xsl781.utils.SystemTool;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static List<Contacts> getPhoneContacts(Context ctx) {

		List<Contacts> list = new ArrayList<Contacts>();
		ContentResolver resolver = ctx.getContentResolver();
		// 获取手机联系人  
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, new String[] { Phone.CONTACT_ID,
				Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String number = phoneCursor.getString(2);
				// 当手机号码为空的或者为空字段 跳过当前循环  
				if (TextUtils.isEmpty(number))
					continue;
				// 得到联系人名称
				Contacts contacts = new Contacts(phoneCursor.getString(0),
						phoneCursor.getString(1), phoneCursor.getString(2));
				list.add(contacts);
			}
			phoneCursor.close();
		}
		return list;
	}

	public static List<Contacts> getSIMContacts(Context ctx) {

		List<Contacts> list = new ArrayList<Contacts>();
		ContentResolver resolver = ctx.getContentResolver();
		// 获取Sims卡联系人    
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, new String[] { Phone.CONTACT_ID,
				Phone.DISPLAY_NAME, Phone.NUMBER }, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				String number = phoneCursor.getString(2);
				// 当手机号码为空的或者为空字段 跳过当前循环  
				if (TextUtils.isEmpty(number))
					continue;
				// 得到联系人名称
				Contacts contacts = new Contacts(phoneCursor.getString(0),
						phoneCursor.getString(1), phoneCursor.getString(2));
				list.add(contacts);
			}
			phoneCursor.close();
		}
		return list;
	}

	public static String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		if(StringUtil.isEmpty(src) || t1 == null )
			return "";

		// System.out.println(t1.length); 
		String[] t2 = new String[t1.length];
		// System.out.println(t2.length); 
		// 设置汉字拼音输出的格式  
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符  
				// System.out.println(t1[i]); 
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
					t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4后 
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后 
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return t4;
	}

	public static String getShortPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		// System.out.println(t1.length); 
		String[] t2 = new String[t1.length];
		// System.out.println(t2.length); 
		// 设置汉字拼音输出的格式  
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String t4 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断能否为汉字字符  
				// System.out.println(t1[i]); 
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);// 将汉字的几种全拼都存到t2数组中 
					t4 += t2[0].charAt(0);// 取出该汉字全拼的第一种读音并连接到字符串t4后 
				} else {
					// 如果不是汉字字符，间接取出字符并连接到字符串t4后 
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return t4;
	}

	/**
		 * 直接调用短信接口发短信
		 * @param phoneNumber
		 * @param message
		 */
	public static void sendSMS(String phoneNumber, String message, PendingIntent sentIntent,
			PendingIntent deliveryIntent) {
		//获取短信管理器 
		android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
		//拆分短信内容（手机短信长度限制）  
		List<String> divideContents = smsManager.divideMessage(message);
		for (String text : divideContents) {
			smsManager.sendTextMessage(phoneNumber, null, text, sentIntent, deliveryIntent);
		}
	}

	/**
	 * 调起系统发短信功能
	 * @param phoneNumber
	 * @param message
	 */
	public static void doSendSMSTo(Context ctx, String phoneNumber, String message) {
		//	if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
		intent.putExtra("sms_body", message);
		ctx.startActivity(intent);
		//	}
	}

	public static String getAppVersion(Context context) {
		String version = "1.0.0";
		int code = 0;
		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
			code = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException(SystemTool.class.getName() + "the application not found");
		}
		return version + "." + code;
	}
	public static String getAppVersionName(Context context) {
		String version = "1.0.0";
		try {
			version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
		}
		return version ;
	}

}
