
import java.util.Calendar;
import java.util.Date;
import juno.util.Dates;

public class TestDates {
  
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
        
        // ISO_8601_24H_FULL_FORMAT
        Date date_iso_8601 = Dates.parseDate("2023-06-20T19:18:11.000Z", 
                "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        
        System.out.println(Dates.dateTimeFormat(date_iso_8601)); // 2023-06-20 13:18:11
  }
}
