package co.nz.simpleryo.shop.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huanghao on 2016-6-27.
 */
public class DateUtil {
    public static Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    public static Date addSeconds(Date date, int seconds) {
        return DateUtils.addSeconds(date, seconds);
    }

    public static Date addMinutes(Date date, int minutes) {
        return DateUtils.addMinutes(date, minutes);
    }

    public static Date addHours(Date date, int hours) {
        return DateUtils.addHours(date, hours);
    }

    public static Long getCurrentTime() {
        return getCurrentDate().getTime();
    }
    
    public static String getYYYYMMDDHHMISS () {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	String retVal = sdf.format(DateUtil.getCurrentDate());
    	return retVal;
    }
}
