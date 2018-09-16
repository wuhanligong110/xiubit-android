package org.xsl781.db.assit;

import java.util.Collection;
import java.util.Map;

/**
 * 辅助判断
 * 
 * 
 * @date 2013-6-10下午5:50:57
 */
public class Checker {

	public static boolean isEmpty(CharSequence str) {
		return isNull(str) || str.length() == 0;
	}

	public static boolean isEmpty(Object[] os) {
		return isNull(os) || os.length == 0;
	}

	public static boolean isEmpty(Collection<?> l) {
		return isNull(l) || l.isEmpty();
	}

	public static boolean isEmpty(Map<?, ?> m) {
		return isNull(m) || m.isEmpty();
	}

	public static boolean isNull(Object o) {
		return o == null;
	}
}
