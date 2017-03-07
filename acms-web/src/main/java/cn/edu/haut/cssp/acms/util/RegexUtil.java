package cn.edu.haut.cssp.acms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	public static boolean check(String reg, String value) {
		if (null == reg || null == value) {
			return false;
		}
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
}
