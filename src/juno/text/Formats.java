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
    
    public static DateFormat datef(String format) {
        return new SimpleDateFormat(format);
    }
    
    public static String format(String format, Date date) {
        return datef(format).format(date);
    }
    public static String format(String format, long date) {
        return datef(format).format(new Date(date));
    }
    public static String format(String format, Calendar date) {
        return datef(format).format(date.getTime());
    }
    
    public static String date(String format) {
        return format(format, new Date());
    }
    
    public static String date(Date date) {
        return format("yyyy-MM-dd", date);
    }
    public static String date(long date) {
        return format("yyyy-MM-dd", date);
    }
    public static String date(Calendar date) {
        return format("yyyy-MM-dd", date);
    }
    public static String date() {
        return date(new Date());
    }
    
    public static String datetime(Date date) {
        return format("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime(long date) {
        return format("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime(Calendar date) {
        return format("yyyy-MM-dd HH:mm:ss", date);
    }
    public static String datetime() {
        return datetime(new Date());
    }
    
}
