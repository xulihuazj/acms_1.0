package cn.edu.haut.cssp.acms.kit;

public class StrKit {
	
	// 首字母转换成小写字母
	public static String firstCharToLowerCase(String str) {
		// 获取第一个字符
		char firstChar = str.charAt(0);
		// 在 A ~ Z 之间
		if ((firstChar >= 'A') && (firstChar <= 'Z')) {
			char[] arr = str.toCharArray();
			int tmp25_24 = 0;
			char[] tmp25_23 = arr;
			tmp25_23[tmp25_24] = (char) (tmp25_23[tmp25_24] + ' ');
			return new String(arr);
		}
		return str;
	}
	
	// 首字母转换成大写字母
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if ((firstChar >= 'a') && (firstChar <= 'z')) {
			char[] arr = str.toCharArray();
			int tmp25_24 = 0;
			char[] tmp25_23 = arr;
			tmp25_23[tmp25_24] = (char) (tmp25_23[tmp25_24] - ' ');
			return new String(arr);
		}
		return str;
	}

	// 判空
	public static boolean isBlank(String str) {
		return ((str == null) || ("".equals(str.trim())));
	}
	
	// 判空
	public static boolean notBlank(String str) {
		return ((str != null) && (!("".equals(str.trim()))));
	}
	
	/**
	 * 数组不为空
	 * @author: xulihua
	 * @date: 2017年1月19日下午2:41:09
	 * @return: boolean
	 */
	public static boolean notBlank(String[] strings) {
		if (strings == null)
			return false;
		for (String str : strings)
			if ((str == null) || ("".equals(str.trim())))
				return false;
		return true;
	}

	/**
	 * 不为空
	 * @author: xulihua
	 * @date: 2017年1月19日下午2:41:02
	 * @return: boolean
	 */
	public static boolean notNull(Object[] paras) {
		if (paras == null)
			return false;
		for (Object obj : paras)
			if (obj == null)
				return false;
		return true;
	}
}