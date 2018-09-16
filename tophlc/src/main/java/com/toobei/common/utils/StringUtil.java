package com.toobei.common.utils;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtil {
	public enum RegexType {
		PHONE, EMAIL
	}

	private static final Pattern email = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private static final Pattern phone = Pattern
			.compile("^((13[0-9])|(15[^4,\\D])|(145)|(147)|(170)|(177)|(176)|(178)|(18[0-9]))\\d{8}$");

	public static boolean regex(String regex, RegexType type) {
		switch (type) {
		case EMAIL: {
			return email.matcher(regex).matches();
		}
		case PHONE: {
			return phone.matcher(regex).matches();
		}
		default:
			return false;
		}
	}

	/** 大写数字 */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/** 整数部分的单位 */
	private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿", "拾", "佰",
			"仟", "万", "拾", "佰", "仟" };
	/** 小数部分的单位 */
	private static final String[] DUNIT = { "角", "分", "厘" };

	/**
	 * 得到大写金额。
	 */
	public static String toChinese(String str) {
		if (str == null || str.equals("")) {
			return "";
		}

		if (str.equals("0.00")) {
			return "零元";
		}

		str = str.replaceAll(",", "");// 去掉","
		String integerStr;// 整数部分数字
		String decimalStr;// 小数部分数字

		// 初始化：分离整数部分和小数部分
		if (str.indexOf(".") > 0) {
			integerStr = str.substring(0, str.indexOf("."));
			decimalStr = str.substring(str.indexOf(".") + 1);
		} else if (str.indexOf(".") == 0) {
			integerStr = "";
			decimalStr = str.substring(1);
		} else {
			integerStr = str;
			decimalStr = "";
		}
		// integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)
		if (!integerStr.equals("")) {
			integerStr = Long.toString(Long.parseLong(integerStr));
			if (integerStr.equals("0")) {
				integerStr = "";
			}
		}
		// overflow超出处理能力，直接返回
		if (integerStr.length() > IUNIT.length) {
			System.out.println(str + ":超出处理能力");
			return str;
		}

		int[] integers = toArray(integerStr);// 整数部分数字
		boolean isMust5 = isMust5(integerStr);// 设置万单位
		int[] decimals = toArray(decimalStr);// 小数部分数字
		return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);
	}

	/**
	 * 整数部分和小数部分转换为数组，从高位至低位
	 */
	private static int[] toArray(String number) {
		int[] array = new int[number.length()];
		for (int i = 0; i < number.length(); i++) {
			array[i] = Integer.parseInt(number.substring(i, i + 1));
		}
		return array;
	}

	/**
	 * 功能：将数字字符分解为整数部分和小数部分 数组 0位为整数，1位为小数
	 * @param number
	 * @return
	 */
	public static String[] getIntAndFloat(String number) {
		String[] strs = number.split("\\.");
		if (strs == null || strs.length == 0) {
			strs = new String[1];
			strs[0] = number;
		}
		return strs;
	}

	/**
	 * 功能： 获得整数部分
	 * @param number
	 * @return
	 */
	public static String getIntStr(String number) {
		String[] strs = number.split("\\.");
		if (strs == null || strs.length == 0) {
			return number;
		}
		return strs[0];
	}

	/**
	 * 得到中文金额的整数部分。
	 */
	private static String getChineseInteger(int[] integers, boolean isMust5) {
		StringBuffer chineseInteger = new StringBuffer("");
		int length = integers.length;
		for (int i = 0; i < length; i++) {
			// 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)
			// 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)
			String key = "";
			if (integers[i] == 0) {
				if ((length - i) == 13)// 万(亿)(必填)
					key = IUNIT[4];
				else if ((length - i) == 9)// 亿(必填)
					key = IUNIT[8];
				else if ((length - i) == 5 && isMust5)// 万(不必填)
					key = IUNIT[4];
				else if ((length - i) == 1)// 元(必填)
					key = IUNIT[0];
				// 0遇非0时补零，不包含最后一位
				if ((length - i) > 1 && integers[i + 1] != 0)
					key += NUMBERS[0];
			}
			chineseInteger.append(integers[i] == 0 ? key : (NUMBERS[integers[i]] + IUNIT[length - i
					- 1]));
		}
		return chineseInteger.toString();
	}

	/**
	 * 得到中文金额的小数部分。
	 */
	private static String getChineseDecimal(int[] decimals) {
		StringBuffer chineseDecimal = new StringBuffer("");
		for (int i = 0; i < decimals.length; i++) {
			// 舍去3位小数之后的
			if (i == 3)
				break;
			chineseDecimal.append(decimals[i] == 0 ? "" : (NUMBERS[decimals[i]] + DUNIT[i]));
		}
		return chineseDecimal.toString();
	}

	/**
	 * 判断第5位数字的单位"万"是否应加。
	 */
	private static boolean isMust5(String integerStr) {
		int length = integerStr.length();
		if (length > 4) {
			String subInteger = "";
			if (length > 8) {
				// 取得从低位数，第5到第8位的字串
				subInteger = integerStr.substring(length - 8, length - 4);
			} else {
				subInteger = integerStr.substring(0, length - 4);
			}
			return Integer.parseInt(subInteger) > 0;
		} else {
			return false;
		}
	}

	private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

	public static String convert(String number) {
		double num = Double.parseDouble(number);
		FORMAT.setRoundingMode(RoundingMode.HALF_UP);
		return FORMAT.format(num);
	}

	public static String convert2(String number) {
		number = number.replaceAll(",", "");
		double num = Double.parseDouble(number);
		DecimalFormat format = new DecimalFormat("###,##0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		return format.format(num);
	}

	public static String convert3(String number) {
		number = number.replaceAll(",", "");
		BigDecimal num = new BigDecimal(number);
		num = num.abs();
		DecimalFormat format = new DecimalFormat("###,##0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		return format.format(num);
	}

	public static String convert2(String numbert, String number) {
		number = number.replaceAll(",", "");
		double num = Double.parseDouble(number);
		numbert = numbert.replaceAll(",", "");
		double numt = Double.parseDouble(numbert);
		double numl = numt - num;
		DecimalFormat format = new DecimalFormat("###,##0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		return format.format(numl);
	}

	public static String convert2(double number) {
		DecimalFormat format = new DecimalFormat("###,##0.00");
		format.setRoundingMode(RoundingMode.HALF_UP);
		return format.format(number);
	}

	public static String prettyCurrency(String str) {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		format.setRoundingMode(RoundingMode.DOWN);
		String result = format.format(new BigDecimal(str));
		return result.substring(1, result.length());
	}

	public static String formatTitlePhone(String phoneNumber) {
		if (phoneNumber == null || phoneNumber.length() < 11) {
			return "****";
		}
		return "(" + phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11) + ")";
	}

	public static String formatPhone(String phoneNumber) {
		if (phoneNumber == null)
			return "";
		if (phoneNumber.length() < 11) {
			return phoneNumber;
		}
		return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, 11);
	}

	public static String formatBankCard(String bankCard) {
		if (bankCard == null || bankCard.length() < 5) {
			return "****";
		}
//		return bankCard.substring(0, 4) + "***********"
//				+ bankCard.substring(bankCard.length() - 4, bankCard.length());
		return "***************"
				+ bankCard.substring(bankCard.length() - 4, bankCard.length());
	}

	public static String formatIdCard(String bankCard) {
		if (bankCard == null || bankCard.length() < 5) {
			return "****";
		}
//		return bankCard.substring(0, 4) + "**********"
//				+ bankCard.substring(bankCard.length() - 4, bankCard.length());
		return "**************"
				+ bankCard.substring(bankCard.length() - 4, bankCard.length());
	}

	public static String formatName(String name) {
		if (name == null || name.length() == 0) {
			return "";
		}
		if (name.length() <= 2) {
			return "*" + name.substring(name.length() - 1, name.length());
		} else if (name.length() == 3) {
			return "**" + name.substring(name.length() - 1, name.length());
		} else {
			return "***" + name.substring(name.length() - 1, name.length());
		}
	}

	/**
	 * 功能：空为0.00 大于100万，以万计
	 * 大于1亿时，以亿计算
	 * @param number
	 * @return
	 */
	public static String formatNumber(String number) {
		if (number == null || number.length() == 0) {
			return "0.00";
		}
		Double d = 0.00;
		try {
			d = Double.parseDouble(number);
		} catch (Exception e) {
			return number;
		}
		if (d >= 100000000) {
			return String.format("%.2f", d / 100000000) + "亿";
		} else if (d >= 1000000) {
			return String.format("%.2f", d / 10000) + "万";
		}else{
			return String.format("%.2f", d );
		}
	}
	/**
	 * 功能：空为0.00 大于100万，以万计 带符号
	 * @param number
	 * @return
	 */
	public static String formatNumberWithSign(String number) {
		if (number == null || number.length() == 0) {
			return "0.00";
		}
		Double d = 0.00;
		try {
			d = Double.parseDouble(number);
		} catch (Exception e) {
			return number;
		}
		if (d >= 1000000) {
			return "+"+String.format("%.2f", d / 10000) + "万";
		}else if(d>0){
			return "+"+String.format("%.2f", d );
		}else{
			return String.format("%.2f", d );
		}
	}

	/**
	 * 功能：转换长度为0的空字符串
	 * @param number
	 * @return
	 */
	public static String formatNumberOrLen0(String number) {
		if (number == null || number.length() == 0 || number.equalsIgnoreCase("null")) {
			return "0";
		}
		return number;
	}

	/**
	 * 功能：转换长度为0的空字符串到0.00
	 * @param number
	 * @return
	 */
	public static String formatNumberOrLen3(String number) {
		if (number == null || number.length() == 0) {
			return "0.00";
		}
		return number;
	}/**
	 * 功能：转换长度为0的空字符串到0.00
	 * @param number
	 * @return
	 */
	public static String formatNumberLen2(String number) {
		if(number.length()<2){
			number="0"+number;
		}
		return number;
	}

	/**
	 * 功能：空 时显示无
	 * @param number
	 * @return
	 */
	public static String formatStringWu(String number) {
		if (number == null || number.length() == 0) {
			return "无";
		}
		return number;
	}

	/**
	 * 功能：>0 时显示数字，否则显示无限制
	 * @param number
	 * @return
	 */
	public static String formatStringUnlimited(String number) {
		if (number == null || number.length() == 0 || Float.parseFloat(number) == 0) {
			return "无限制";
		}
		return number;
	}

	public static String prettyAdd(String num1, String num2) {
		BigDecimal bd1 = new BigDecimal(num1);
		BigDecimal bd2 = new BigDecimal(num2);
		BigDecimal result = bd1.add(bd2);
		String target = result.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
		return prettyCurrency(target);
	}

	public static String prettyAdd(String num1, String num2, String num3) {
		if (num1 == null || "".equals(num1)) {
			num1 = "0.00";
		}
		if (num2 == null || "".equals(num2)) {
			num2 = "0.00";
		}
		if (num3 == null || "".equals(num3)) {
			num3 = "0.00";
		}
		num1 = num1.replace(",", "");
		num2 = num2.replace(",", "");
		num3 = num3.replace(",", "");
		BigDecimal bd1 = new BigDecimal(num1);
		BigDecimal bd2 = new BigDecimal(num2);
		BigDecimal bd3 = new BigDecimal(num3);
		BigDecimal result = bd1.add(bd2).add(bd3);
		String target = result.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
		return prettyCurrency(target);
	}

	public static String prettySubtract(String num1, String num2) {
		BigDecimal bd1 = new BigDecimal(num1);
		BigDecimal bd2 = new BigDecimal(num2);
		BigDecimal result = bd1.subtract(bd2);
		String target = result.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
		return prettyCurrency(target);
	}

	public static String prettyMulty(String num1, String num2) {
		BigDecimal bd1 = new BigDecimal(num1);
		BigDecimal bd2 = new BigDecimal(num2);
		BigDecimal result = bd1.multiply(bd2);
		String target = result.setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
		return prettyCurrency(target);
	}

	/***
	 * 去掉字符串前后的空间，中间的空格保留
	 *
	 * @param str
	 * @return
	 */
	public static String trimOutterSpaceStr(String str) {
		str = str.trim();
		while (str.startsWith(" ")) {
			str = str.substring(1, str.length()).trim();
		}
		while (str.endsWith(" ")) {
			str = str.substring(0, str.length() - 1).trim();
		}
		return str;
	}

	// 字符串为空
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 || "null".equals(str);
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static Map<String, String> getParma(String url) {
		Map<String, String> params = new HashMap<String, String>();
		if (url.indexOf("?") < 0) {
			return params;
		}
		try {
			String str = url.substring(url.indexOf("?") + 1);
			String[] strArray = str.split("&");
			for (int i = 0; i < strArray.length; i++) {
				String param = strArray[i];
				String[] paramArray = param.split("=");
				if (paramArray.length == 2) {
					String value;
					value = URLDecoder.decode(paramArray[1], "utf-8");
					params.put(paramArray[0], value);
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}

	public static Map<String, String> stringToMap(String str) {
		Map<String, String> params = new HashMap<String, String>();
		String[] strArray = str.split("&");
		for (int i = 0; i < strArray.length; i++) {
			String param = strArray[i];
			String[] paramArray = param.split("=");
			params.put(paramArray[0], paramArray[1]);
		}
		return params;
	}

	/**
	 * 保留两位小数
	 */
	public static String doubleFormat(Double str) {
		if (str == 0) {
			return "0.00";
		}
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.DOWN);
		return df.format(str / 10000);
	}

	/**
	 * 通配字符
	 */
	public static String replace(int id, String str, Context context) {
		String sAgeFormat = context.getResources().getString(id);
		return String.format(sAgeFormat, str);
	}

	/*
		public static int getBankIcon(String fBankCode) {
			if ("boc".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_boc;
			} else if ("ccb".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_ccb;
			} else if ("icbc".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_icbc;
			} else if ("cmb".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_cmb;
			} else if ("bcom".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_bcom;
			} else if ("citic".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_citic;
			} else if ("spdb".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_spdb;
			} else if ("cib".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_cib;
			} else if ("gdb".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_gdb;
			} else if ("pab".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_pab;
			} else if ("hxb".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_hxb;
			} else if ("ceb".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_ceb;
			} else if ("psbc".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_post;
			} else if ("abc".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_abc;
			} else if ("cmbc".equalsIgnoreCase(fBankCode)) {
				return R.drawable.bank_cmbc;
			} else {
				return -1;
			}

		}
	*/
	public static boolean urlEquals(String str1, String str2) {
		if (str1 == null || str2 == null) {
			return false;
		} else {
			String urlOne, urlTwo;
			if (str1.indexOf("?") > 0) {
				urlOne = str1.substring(0, str1.indexOf("?"));
			} else {
				urlOne = str1;
			}

			if (str2.indexOf("?") > 0) {
				urlTwo = str2.substring(0, str2.indexOf("?"));
			} else {
				urlTwo = str2;
			}
			return urlTwo.equals(urlOne);
		}
	}

	public static boolean checkPasswd(String passwd) {
		/*Pattern p = Pattern.compile("[A-Z]+");
		Pattern q = Pattern.compile("[a-z]+");
		Pattern r = Pattern.compile("[0-9]+");
		Pattern s = Pattern.compile("\\p{Punct}+");
		Matcher m1 = p.matcher(passwd); // 判断是否含有大写字符
		Matcher m2 = q.matcher(passwd); // 判断是否含有小写字符
		Matcher m3 = r.matcher(passwd); // 判断是否含有数字
		Matcher m4 = s.matcher(passwd); // 判断是否含有特殊字符
		if ((!passwd.isEmpty()) && passwd.length() >= 6 && (!m4.find())) { // m1.find() && m2.find() && m3.find() ) {
			return true;
		}*/
		return (!passwd.isEmpty()) && passwd.length() >= 6;
	}

	public static boolean checkPhoneNum(String phone) {
		//由于输入框已经限制了只有数字才能输入,这里不需要对数字做判断,只判断长度
		if (phone != null && phone.length() == 11) {
			return true;
		}
		return false;
	}
}
