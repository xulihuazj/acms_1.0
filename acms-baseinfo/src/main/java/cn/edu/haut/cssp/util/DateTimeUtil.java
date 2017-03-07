package cn.edu.haut.cssp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期时间工具类
 * Description:
 * @author: xulihua
 * @date: 2017年1月18日下午4:13:54
 * @note
 */
public class DateTimeUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);
    
	/**
	 * 一天的毫秒值
	 */
    public static final int ONE_DAY_TIME = 24 * 60 * 60 * 1000;
    
    /**
     * 方法描述：时间比较方法<br>
     * date1>date2时返回1， date1=date2时返回0， date1<date2时返回-1
     * @param date1 比较源时间
     * @param date2 被比较的时间
     * @return int型结果
     */
    public static int compare(Date date1, Date date2) {
    	long compareResult = date1.getTime() - date2.getTime();
    	
        if (compareResult > 0) {
            return 1;
        } else if (compareResult == 0) {
            return 0;
        } else {
            return -1;
        }
    }
    
    /**
     * 
     * @Description：获取当前日期的前一天，如当前是2014-5-30 15:28:33，则返回2014-5-29 15:28:33
     * @return
     */
    public static Date getPrevDate() {
    	return getPrevDate(DateTime.now().toDate());
    }
    
    /**
     * 
     * 获取指定日期的前一天，如传入2014-5-30 15:28:33，则返回2014-5-29 15:28:33
     * @param date
     * @return
     */
    public static Date getPrevDate(Date date) {
    	if (null == date) throw new IllegalArgumentException("参数date不能为null");
    	
    	return new DateTime(date).minusDays(1).toDate();
    }
    
    /**
     * 
     * @Description：获取当前日期的下一天，如当前是2014-5-30 15:28:33，则返回2014-5-31 15:28:33
     * @return
     */
    public static Date getNextDate() {
    	return getNextDate(DateTime.now().toDate());
    }
    
    /**
     * 
     * 获取指定日期的下一天，如传入2014-5-30 15:28:33，则返回2014-5-31 15:28:33
     * @param date
     * @return
     */
    public static Date getNextDate(Date date) {
    	if (null == date) throw new IllegalArgumentException("参数date不能为null");
    	
    	return new DateTime(date).plusDays(1).toDate();
    }

	/**
	 * 
	 * @Description：根据一个日期，返回是星期几的字符串
	 * @param date 日期
	 * @return 代表星期的字符串：星期一、星期二、...、星期日
	 */
	public static String getWeek(Date date) {
		return new SimpleDateFormat("EEEE", Locale.CHINESE).format(date);
	}

	/**
	 * 
	 * @Description：将短时间格式字符串（yyyy-MM-dd）转换为Date
	 * @param dateStr 日期字符串
	 * @return Date 格式化后的时间
	 */
	public static Date dateStrToDate(String dateStr) {
		return DateTime.parse(dateStr).toDate();
	}

	/**
	 * 
	 * 方法描述：转换毫秒值为日期型
	 * @param time
	 * @return
	 */
	public static Date longToDate(long time) {
		return new DateTime(time).toDate();
	}
	
	/**
	 * 
	 * 转换毫秒值为yyyy-MM-dd HH:mm:ss格式
	 * @param time
	 * @return
	 */
	public static String longToDateStr(long time) {
		return longToDateStr(time, null);
	}
	
	/**
	 * 
	 * @Description：转换毫秒值为pattern指定的格式
	 * @param time 毫秒值
	 * @param pattern 指定要转换为的目标格式，默认为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String longToDateStr(long time, String pattern) {
		if(time == 0) {
            return "－－";
        }
		
		return dateToStr(longToDate(time), pattern);
	}
	
	/**
	 * 
	 * 方法描述：格式化日期
	 * @param date
	 * @param pattern 指定要转换为的目标格式，默认为：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		return (new SimpleDateFormat(StringUtils.isNotBlank(pattern) ? pattern : "yyyy-MM-dd HH:mm:ss")).format(date);
	}
	
	/**
	 * 
	 * @Description：转换日期为yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
	    return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * 方法描述：转换字符串格式的时间值为长整型，该方法转换后的值不包含毫秒，所以结束时间需要加上999才能和数据库中的值正常比较
	 * @param dateTimeStr yyyy-MM-dd HH:mm:ss
	 * @param isEndTime 
	 * @return
	 */
	public static long dateTimeStrToLong(String dateTimeStr, boolean isEndTime) {
		long result = dateTimeStrToLong(dateTimeStr);
		return isEndTime ? result + 999 : result;
	}
	
	/**
	 * 
	 * 方法描述：转换字符串格式的时间值为长整型
	 * @param dateTimeStr yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static long dateTimeStrToLong(String dateTimeStr){
		return DateTime.parse(dateTimeStr, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis();
	}
	
	/**
	 * 
	 * 方法描述：获取开始时间
	 * @param startTime 开始时间，格式：yyyy-MM-dd
	 * @return
	 */
	public static long getStartTime(String startTime) {
	    return dateTimeStrToLong(startTime + " 00:00:00");
	}
	
	/**
	 * 
	 * 方法描述：获取结束时间
	 * @param endTime 结束时间，格式：yyyy-MM-dd
	 * @return
	 */
	public static long getEndTime(String endTime) {
        return dateTimeStrToLong(endTime + " 23:59:59", true);
    }

	/**
	 * 
	 * 方法描述：获得当前时间的毫秒值
	 * @return
	 */
	public static Long getCurrentTime() {
		return DateTimeUtils.currentTimeMillis();
	}

	/**
	 * 
	 * 方法描述：转换8位int（20110101）的时间值为日期型
	 * @return
	 */
	public static Date intToDate(int dateTime, String formatStr) {
		SimpleDateFormat simpledateformat = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = simpledateformat.parse(String.valueOf(dateTime));
		} catch (ParseException e) {
			logger.error("转换8位int（20110101）的时间值为日期型出错", e);
		}
		return date;
	}
}
