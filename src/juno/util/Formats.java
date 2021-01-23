package juno.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author jesus
 */
public final class Formats {
    
    public static SimpleDateFormat dateFormat(String format) {
        return new SimpleDateFormat(format);
    }
    
    public static String date(String format, Date date) {
        return dateFormat(format).format(date);
    }
    public static String date(String format, long date) {
        return dateFormat(format).format(new Date(date));
    }
    public static String date(String format, Calendar date) {
        return dateFormat(format).format(date.getTime());
    }
    
    public static String date(String format) {
        return date(format, new Date());
    }
    
    public static String date(Date date) {
        return date("yyyy-MM-dd", date);
    }
    public static String date(long date) {
        return date("yyyy-MM-dd", date);
    }
    public static String date(Calendar date) {
        return date("yyyy-MM-dd", date);
    }
    public static String date() {
        return date(new Date());
    }
    
    public static String datetime(Date date) {
        return date("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime(long date) {
        return date("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime(Calendar date) {
        return date("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime() {
        return datetime(new Date());
    }
    
}
