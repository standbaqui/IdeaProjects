import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        int weekOfYear = today.get(WeekFields.of(Locale.getDefault()).weekOfYear());
        System.out.println("Current week of the year: " + weekOfYear);
    }
}