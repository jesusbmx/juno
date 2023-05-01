package juno.text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author jesus
 */
public final class Formats {
    
//    private static final HashMap<String, DateFormat> CACHE =
//            new HashMap<String, DateFormat>();
//    
//    public static DateFormat datef(String format) {
//        DateFormat dateFormat = CACHE.get(format);
//        if (dateFormat == null) {
//            dateFormat = new SimpleDateFormat(format);
//            CACHE.put(format, dateFormat);
//        }
//        return dateFormat;
//    }
    
    public static DateFormat newDateFormat(String format) {
        return new SimpleDateFormat(format);
    }
    
    public static String dateF(String format, Date date) {
        return newDateFormat(format).format(date);
    }
    public static String dateF(String format, long date) {
        return newDateFormat(format).format(new Date(date));
    }
    public static String dateF(String format, Calendar date) {
        return newDateFormat(format).format(date.getTime());
    }
    
    public static String date(String format) {
        return dateF(format, new Date());
    }
    
    public static String date(Date date) {
        return dateF("yyyy-MM-dd", date);
    }
    public static String date(long date) {
        return dateF("yyyy-MM-dd", date);
    }
    public static String date(Calendar date) {
        return dateF("yyyy-MM-dd", date);
    }
    public static String date() {
        return date(new Date());
    }
    
    public static String datetime(Date date) {
        return dateF("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime(long date) {
        return dateF("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime(Calendar date) {
        return dateF("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime() {
        return datetime(new Date());
    }
    
}
