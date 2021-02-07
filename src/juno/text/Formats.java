package juno.text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author jesus
 */
public final class Formats {
    
    private static final HashMap<String, SimpleDateFormat> CACHE =
            new HashMap<String, SimpleDateFormat>();
    
    public static SimpleDateFormat f(String format) {
        SimpleDateFormat dateFormat = CACHE.get(format);
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(format);
            CACHE.put(format, dateFormat);
        }
        return dateFormat;
    }
    
    public static String date(String format, Date date) {
        return f(format).format(date);
    }
    public static String date(String format, long date) {
        return f(format).format(new Date(date));
    }
    public static String date(String format, Calendar date) {
        return f(format).format(date.getTime());
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
