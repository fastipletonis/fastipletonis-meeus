package eu.fastipletonis.astro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import eu.fastipletonis.astro.temporal.DecimalTime;
import eu.fastipletonis.astro.temporal.Queries;

public class App {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(1957, 10, 4);
        LocalTime time = DecimalTime.toLocalTime(0.81d);
        LocalDateTime localDateTime = LocalDateTime.of(date, time);
        ZonedDateTime utcDateTime = ZonedDateTime.of(localDateTime, ZoneOffset.UTC);
        double dJulianDay = utcDateTime.query(Queries.JULIAN_DAY);
        BigDecimal bdJulianDay = utcDateTime.query(Queries.HP_JULIAN_DAY);
        System.out.println("Double     | Datetime: " + utcDateTime + " | Julian day: " + dJulianDay);
        System.out.println("BigDecimal | Datetime: " + utcDateTime + " | Julian day: " + bdJulianDay);
    }
}
