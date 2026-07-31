package juno.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilidades de fecha/hora compatibles con Android antiguo y Java.
 * - Evita patrones 'X'/'XXX' (no soportados por SimpleDateFormat en APIs viejas).
 * - Normaliza ISO-8601/RFC3339 a patrones con 'Z' antes de parsear.
 * - Ofrece sobrecargas con Locale/TimeZone y parsers tolerantes.
 */
public final class Dates {

    // ========= Regex helpers para normalización ISO =========
    private static final Pattern TZ_COLON_AT_END = Pattern.compile("([+-]\\d{2}):(\\d{2})$");
    private static final Pattern ONLY_HOURS_TZ_AT_END = Pattern.compile("([+-]\\d{2})$");
    private static final Pattern TRAILING_Z = Pattern.compile("Z$");
    private static final Pattern MILLIS_GROUP = Pattern.compile("(\\.\\d{1,9})");
    private static final Pattern DATE_T_SEP = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2})[ T](\\d{2}:\\d{2}:\\d{2}(?:\\.\\d{1,9})?)(.*)$");

    private Dates() {}

    // ==========================
    // Factories y utilidades SDF
    // ==========================
    private static SimpleDateFormat sdf(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
        df.setLenient(false);
        return df;
    }

    private static SimpleDateFormat sdf(String pattern, Locale locale, TimeZone tz) {
        SimpleDateFormat df = new SimpleDateFormat(pattern, locale == null ? Locale.US : locale);
        df.setLenient(false);
        if (tz != null) df.setTimeZone(tz);
        return df;
    }

    // ==========================
    // Normalización ISO-8601
    // ==========================
    private static String normalizeIso8601(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return s;

        // Permitir espacio en lugar de 'T'
        Matcher sep = DATE_T_SEP.matcher(s);
        if (sep.find()) {
            s = sep.group(1) + "T" + sep.group(2) + sep.group(3);
        }

        // "Z" -> "+0000"
        s = TRAILING_Z.matcher(s).replaceFirst("+0000");

        // "+hh:mm" -> "+hhmm"
        Matcher m = TZ_COLON_AT_END.matcher(s);
        if (m.find()) {
            s = m.replaceFirst(m.group(1) + m.group(2));
        }

        // "+hh" -> "+hh00"
        m = ONLY_HOURS_TZ_AT_END.matcher(s);
        if (m.find()) {
            s = s + "00";
        }

        // Si no trae zona horaria, asumir UTC
        if (!s.matches(".*([+-]\\d{4})$")) {
            s = s + "+0000";
        }

        // Normalizar milisegundos a exactamente 3 dígitos
        m = MILLIS_GROUP.matcher(s);
        if (m.find()) {
            String frac = m.group(1);    // p.ej. ".5", ".53", ".5321"
            String digits = frac.substring(1);
            if (digits.length() < 3) {
                StringBuilder sb = new StringBuilder(digits);
                while (sb.length() < 3) sb.append('0'); // pad derecha
                s = s.replace(frac, "." + sb);
            } else if (digits.length() > 3) {
                s = s.replace(frac, "." + digits.substring(0, 3)); // trunc
            }
        }
        return s;
    }

    // ==========================
    // Parsers "básicos" existentes
    // ==========================
    /** Parse estricto con patrón dado. */
    public static Date parseDate(String source, String format, Locale locale) throws ParseException {
        if (source == null) return null;
        DateFormat dateFormat = new SimpleDateFormat(format, locale);
        ((SimpleDateFormat)dateFormat).setLenient(false);
        return dateFormat.parse(source);
    }

    public static Date parseDate(String source, String format) throws ParseException {
        return parseDate(source, format, Locale.getDefault());
    }

    /**
     * Convierte un string en una fecha (compat con versiones anteriores a 1.0.6).
     * @param source "2023-04-30"
     */
    public static Date toDate(String source) throws ParseException {
        return parseDate(source, "yyyy-MM-dd");
    }

    /**
     * Convierte un string en una fecha y hora (compat con versiones anteriores a 1.0.6).
     * @param source "2023-04-30 19:10:02"
     */
    public static Date toDateTime(String source) throws ParseException {
        return parseDate(source, "yyyy-MM-dd HH:mm:ss");
    }

    /** Devuelve Calendar parseado (bugfix: retornamos el Calendar que seteamos). */
    public static Calendar parseCalendar(String source, String format, Locale locale) throws ParseException {
        if (source == null) return null;
        SimpleDateFormat df = new SimpleDateFormat(format, locale);
        df.setLenient(false);
        Calendar cal = Calendar.getInstance(locale);
        cal.setTime(df.parse(source));
        return cal;
    }

    public static Calendar parseCalendar(String source, String format) throws ParseException {
        return parseCalendar(source, format, Locale.getDefault());
    }

    // ==========================
    // Parsers ISO-8601/RFC3339 robustos
    // ==========================
    /**
     * Parser ISO 8601 compatible con Android antiguo.
     * Acepta Z, +hh, +hhmm, +hh:mm, con/sin milis (1..9). Asume UTC si falta offset.
     */
    public static Date parseIso8601Compat(String source) throws ParseException {
        if (source == null) return null;
        String fixed = normalizeIso8601(source);
        ParseException last = null;
        for (String pattern : new String[] {
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
                "yyyy-MM-dd'T'HH:mm:ssZ"
        }) {
            try {
                SimpleDateFormat df = sdf(pattern, Locale.US, TimeZone.getTimeZone("UTC"));
                return df.parse(fixed);
            } catch (ParseException e) {
                last = e;
            }
        }
        throw (last != null ? last : new ParseException("Unparseable date: " + source, 0));
    }

    /** Igual que parseIso8601Compat, pero devuelve null en lugar de lanzar excepción. */
    public static Date tryParseIso8601(String source) {
        try {
            return parseIso8601Compat(source);
        } catch (ParseException e) {
            return null;
        }
    }

    // ==========================
    // Parsers flexibles y epoch
    // ==========================
    /**
     * Intenta parsear con varios patrones (estricto). Lanza ParseException si todos fallan.
     * Útil para endpoints con formatos variados pero conocidos.
     */
    public static Date parseFlexible(String source, String... patterns) throws ParseException {
        if (source == null) return null;
        ParseException last = null;
        for (String p : patterns) {
            try {
                return sdf(p).parse(source);
            } catch (ParseException e) {
                last = e;
            }
        }
        throw (last != null ? last : new ParseException("Unparseable date (flexible): " + source, 0));
    }

    /** Variante segura de parseFlexible que devuelve null en lugar de excepción. */
    public static Date tryParseFlexible(String source, String... patterns) {
        try {
            return parseFlexible(source, patterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Detecta y parsea epoch en milisegundos o segundos (string o número en string).
     * Regla: si el valor tiene 13 dígitos => ms; si 10 dígitos => s.
     */
    public static Date parseEpochAuto(String numeric) throws ParseException {
        if (numeric == null) return null;
        String s = numeric.trim();
        if (!s.matches("^-?\\d+$")) {
            throw new ParseException("Not a numeric epoch: " + numeric, 0);
        }
        boolean negative = s.startsWith("-");
        int len = (negative ? s.length() - 1 : s.length());
        long v = Long.parseLong(s);
        if (len >= 13) {
            return new Date(v); // milisegundos
        } else if (len == 10) {
            return new Date(v * 1000L); // segundos
        } else {
            // Heurística: si < 10 dígitos, asumir segundos
            return new Date(v * 1000L);
        }
    }

    /** Variante segura de parseEpochAuto. */
    public static Date tryParseEpochAuto(String numeric) {
        try {
            return parseEpochAuto(numeric);
        } catch (Exception e) {
            return null;
        }
    }

    // ==========================
    // Formateo
    // ==========================
    public static String format(String format, Date date) {
        if (date == null) return null;
        return sdf(format).format(date);
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

    public static String format(String format, Date date, Locale locale, TimeZone tz) {
        if (date == null) return null;
        return sdf(format, locale == null ? Locale.US : locale, tz).format(date);
    }

    public static String dateFormat(Date date) { return format("yyyy-MM-dd", date); }
    public static String dateFormat(long date) { return format("yyyy-MM-dd", date); }
    public static String dateFormat(Calendar date) { return format("yyyy-MM-dd", date); }
    public static String dateFormat() { return format("yyyy-MM-dd", new Date()); }

    public static String dateTimeFormat(Date date) { return format("yyyy-MM-dd HH:mm:ss", date); }
    public static String dateTimeFormat(long date) { return format("yyyy-MM-dd HH:mm:ss", date); }
    public static String dateTimeFormat(Calendar date) { return format("yyyy-MM-dd HH:mm:ss", date); }
    public static String dateTimeFormat() { return format("yyyy-MM-dd HH:mm:ss", new Date()); }

    /**
     * Formatea a ISO-8601 con o sin milis y con offset con dos puntos (RFC 3339).
     * Internamente se forma "Z" sin ":" y luego se inserta el ":" en el offset.
     */
    public static String toIso8601(Date date, boolean withMillis) {
        if (date == null) return null;
        String pat = withMillis ? "yyyy-MM-dd'T'HH:mm:ss.SSSZ" : "yyyy-MM-dd'T'HH:mm:ssZ";
        String raw = sdf(pat, Locale.US, TimeZone.getTimeZone("UTC")).format(date);
        // raw: 2025-10-01T12:34:56.789+0000 -> convertir a +00:00
        return insertColonInOffset(raw);
    }

    /** Formatea a ISO-8601 usando timezone indicado (por defecto usa tz del sistema). */
    public static String toIso8601(Date date, boolean withMillis, TimeZone tz) {
        if (date == null) return null;
        String pat = withMillis ? "yyyy-MM-dd'T'HH:mm:ss.SSSZ" : "yyyy-MM-dd'T'HH:mm:ssZ";
        String raw = sdf(pat, Locale.US, tz == null ? TimeZone.getDefault() : tz).format(date);
        return insertColonInOffset(raw);
    }

    private static String insertColonInOffset(String s) {
        // Convierte "+hhmm" al final en "+hh:mm"
        if (s == null) return null;
        if (s.endsWith("+0000")) return s.substring(0, s.length() - 5) + "+00:00";
        if (s.endsWith("-0000")) return s.substring(0, s.length() - 5) + "-00:00";
        Matcher m = Pattern.compile("([+-]\\d{2})(\\d{2})$").matcher(s);
        if (m.find()) {
            return s.substring(0, m.start()) + m.group(1) + ":" + m.group(2);
        }
        return s;
    }

    // ==========================
    // Utilidades de calendario
    // ==========================
    /** Elimina horas/min/seg/millis. */
    public static void removeTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    public static Calendar calendarWithTime() { return Calendar.getInstance(); }

    public static Calendar calendarWithoutTime() {
        Calendar cal = Calendar.getInstance();
        removeTime(cal);
        return cal;
    }

    public static Date dateWithTime() { return new Date(); }

    public static Date dateWithoutTime() { return calendarWithoutTime().getTime(); }

    /** Inicio de día en TZ (00:00:00.000). */
    public static Date startOfDay(Date date, TimeZone tz) {
        Calendar c = Calendar.getInstance(tz == null ? TimeZone.getDefault() : tz);
        c.setTime(date == null ? new Date() : date);
        removeTime(c);
        return c.getTime();
    }

    /** Fin de día en TZ (23:59:59.999). */
    public static Date endOfDay(Date date, TimeZone tz) {
        Calendar c = Calendar.getInstance(tz == null ? TimeZone.getDefault() : tz);
        c.setTime(date == null ? new Date() : date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    /** Suma días (negativos para restar). */
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date == null ? new Date() : date);
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }

    /** Trunca (floor) por unidad (Calendar.FIELD). */
    public static Date floor(Date date, int calendarField) {
        Calendar c = Calendar.getInstance();
        c.setTime(date == null ? new Date() : date);
        switch (calendarField) {
            case Calendar.YEAR:
                c.set(Calendar.MONTH, 0);
            case Calendar.MONTH:
                c.set(Calendar.DAY_OF_MONTH, 1);
            case Calendar.DAY_OF_MONTH:
                c.set(Calendar.HOUR_OF_DAY, 0);
            case Calendar.HOUR_OF_DAY:
                c.set(Calendar.MINUTE, 0);
            case Calendar.MINUTE:
                c.set(Calendar.SECOND, 0);
            case Calendar.SECOND:
                c.set(Calendar.MILLISECOND, 0);
                break;
            default:
                // Si no reconocemos el field, solo quitamos millis
                c.set(Calendar.MILLISECOND, 0);
        }
        return c.getTime();
    }

    // ==========================
    // Atajos de alto nivel
    // ==========================
    /** Intenta: ISO-8601 robusto -> epoch auto -> patrones comunes; lanza si todos fallan. */
    public static Date parseSmart(String source) throws ParseException {
        if (source == null) return null;
        // 1) ISO robusto
        try { return parseIso8601Compat(source); } catch (ParseException ignore) {}
        // 2) Epoch auto
        try { return parseEpochAuto(source); } catch (Exception ignore) {}
        // 3) Patrones comunes (sin zona)
        return parseFlexible(source,
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd",
                "dd/MM/yyyy HH:mm:ss",
                "dd/MM/yyyy"
        );
    }

    /** Variante segura de parseSmart. */
    public static Date tryParseSmart(String source) {
        try {
            return parseSmart(source);
        } catch (ParseException e) {
            return null;
        }
    }

    
//    public static void main(String[] args) throws Exception {
//        String sDate = "2023-04-30 19:10:02";
//        
//        Calendar date = Dates.parseCalendar(sDate, "yyyy-MM-dd");
//        System.out.println(Dates.dateTimeFormat(date)); // 2023-04-30 00:00:00
//        
//        Date dateTime = Dates.parseDate(sDate, "yyyy-MM-dd HH:mm:ss");
//        System.out.println(Dates.dateTimeFormat(dateTime)); // 2023-04-30 19:10:02
//
//        
//        System.out.println(Dates.dateFormat(new Date())); // 2023-05-03
//        System.out.println(Dates.dateTimeFormat(new Date())); // 2023-05-03 12:31:47
//        System.out.println(Dates.format("yyyy-MM-dd HH:mm:ss", new Date())); // 2023-05-03 12:31:47
//        
//        
//        Calendar cDate = Dates.calendarWithoutTime(); // get date without time
//        System.out.println(Dates.dateTimeFormat(cDate)); // 2023-05-03 00:00:00
//        
//        Calendar cDateTime = Dates.calendarWithTime(); // get date and time
//        System.out.println(Dates.dateTimeFormat(cDateTime)); // 2023-05-03 12:31:47
//    }
}
