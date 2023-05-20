package juno.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class Dates {

    private Dates() {
    }
    
    /**
     * Convierte un string en una fecha
     * @param source "2023-04-30 19:10:02"
     * @param format "yyyy-MM-dd"
     * @param locale
     * @return
     * @throws ParseException 
     */
    public static Date parseDate(String source, String format, Locale locale) throws ParseException {
        if (source == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format, locale);
        return dateFormat.parse(source);
    }
    
    public static Date parseDate(String source, String format) throws ParseException {
        return parseDate(source, format, Locale.getDefault());
    }
    
    /**
     * Convierte un string en una fecha
     * @param source "2023-04-30 19:10:02"
     * @param format "yyyy-MM-dd"
     * @param locale
     * @return
     * @throws ParseException 
     */
    public static Calendar parseCalendar(String source, String format, Locale locale) throws ParseException {
        if (source == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance(locale);
        DateFormat dateFormat = new SimpleDateFormat(format, locale);
        cal.setTime(dateFormat.parse(source));
        return dateFormat.getCalendar();
    }
    
    public static Calendar parseCalendar(String source, String format) throws ParseException {
        return parseCalendar(source, format, Locale.getDefault());
    }
    
    /**
     * Convierte un string en una fecha
     * @param source "2023-04-30 19:10:02"
     * @return
     * @throws ParseException 
     */
    public static Date toDate(String source) throws ParseException {
        return parseDate(source, "yyyy-MM-dd");
    }
    
    /**
     * Convierte un string en una fecha y hora
     * @param source "2023-04-30 19:10:02"
     * @return
     * @throws ParseException 
     */
    public static Date toDateTime(String source) throws ParseException {
        return parseDate(source, "yyyy-MM-dd HH:mm:ss");
    }
    
    
    public static String format(String format, Date date) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    
    public static String format(String format, long date) {
        return format(format, new Date(date));
    }
    
    public static String format(String format, Calendar date) {
        return format(format, date.getTime());
    }
    
    public static String format(String format) {
        return format(format, new Date());
    }
    
    
    public static String dateFormat(Date date) {
        return format("yyyy-MM-dd", date);
    }
    
    public static String dateFormat(long date) {
        return format("yyyy-MM-dd", date);
    }
    
    public static String dateFormat(Calendar date) {
        return format("yyyy-MM-dd", date);
    }
    
    public static String dateFormat() {
        return format("yyyy-MM-dd", new Date());
    }
    
    
    public static String dateTimeFormat(Date date) {
        return format("yyyy-MM-dd HH:mm:ss", date);
    }
    
    public static String dateTimeFormat(long date) {
        return format("yyyy-MM-dd HH:mm:ss", date);
    }
    
    public static String dateTimeFormat(Calendar date) {
        return format("yyyy-MM-dd HH:mm:ss", date);
    }
    
    public static String dateTimeFormat() {
        return format("yyyy-MM-dd HH:mm:ss", new Date());
    }
    
    
    /**
     * Elimina las horas minitos segundos y millis de una fecha
     */
    public static void removeTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
    
    public static Calendar calendarWithTime() {
        return Calendar.getInstance();
    }

    public static Calendar calendarWithoutTime() {
        Calendar cal = Calendar.getInstance();
        Dates.removeTime(cal);
        return cal;
    }
    
    public static Date dateWithTime() {
        return new Date();
    }
    
    public static Date dateWithoutTime() {
        return calendarWithoutTime().getTime();
    }
    
    public static void main(String[] args) throws Exception {
        String sDate = "2023-04-30 19:10:02";
        
        Calendar date = Dates.parseCalendar(sDate, "yyyy-MM-dd");
        System.out.println(Dates.dateTimeFormat(date)); // 2023-04-30 00:00:00
        
        Date dateTime = Dates.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
        System.out.println(Dates.dateTimeFormat(dateTime)); // 2023-04-30 19:10:02

        
        System.out.println(Dates.dateFormat(new Date())); // 2023-05-03
        System.out.println(Dates.dateTimeFormat(new Date())); // 2023-05-03 12:31:47
        System.out.println(Dates.format("yyyy-MM-dd HH:mm:ss", new Date())); // 2023-05-03 12:31:47
        
        
        Calendar cDate = Dates.calendarWithoutTime(); // get date without time
        System.out.println(Dates.dateTimeFormat(cDate)); // 2023-05-03 00:00:00
        
        Calendar cDateTime = Dates.calendarWithTime(); // get date and time
        System.out.println(Dates.dateTimeFormat(cDateTime)); // 2023-05-03 12:31:47
    }
}
