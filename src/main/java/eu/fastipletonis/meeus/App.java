package eu.fastipletonis.meeus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalQuery;

import eu.fastipletonis.meeus.temporal.AstronomicalJulianDay;
import eu.fastipletonis.meeus.temporal.AstronomicalTemporalQueries;
import eu.fastipletonis.meeus.temporal.DecimalTime;

public class App {
    public static void main(String[] args) {
        LocalDate d = LocalDate.of(1957, 10, 4);
        double t = 0.81d;
        AstronomicalJulianDay adjd = AstronomicalJulianDay.ofUTCDecimalTime(d, t);
        LocalTime lt = DecimalTime.toLocalTime(t);
        TemporalQuery<Double> q = AstronomicalTemporalQueries.ASTRONOMICAL_JULIAN_DAY;
        q.queryFrom(adjd);
        System.out.println("(ASTRO JD) | Datetime: " + d + " (" + lt + ") | Julian day: " + adjd);
    }
}
