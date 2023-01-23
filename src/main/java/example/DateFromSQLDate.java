package example;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFromSQLDate {
    public static LocalDate get(String dateSQL) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(dateSQL, formatter);
    }
}
