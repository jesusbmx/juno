
import java.util.Date;
import juno.util.Dates;

public class TestDates {
  
  public static void main(String[] args) {
    // Formats
    System.out.println(Dates.dateFormat());
    System.out.println(Dates.dateTimeFormat());
    
    System.out.println(Dates.format("yyyy"));
    System.out.println(Dates.format("yyyy-MM", new Date()));
  }
}
