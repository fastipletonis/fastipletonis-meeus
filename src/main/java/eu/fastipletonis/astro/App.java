package eu.fastipletonis.astro;

import java.time.ZonedDateTime;

import eu.fastipletonis.astro.temporal.JulianDayHelper;
import eu.fastipletonis.astro.temporal.Queries;

/**
 * Test App.
 */
public class App {
    // Prevents instantiation.
    private App() {}

    /**
     * Main.
     * @param args args
     */
    public static void main(String[] args) {
        ZonedDateTime sputnikLaunch = ZonedDateTime.parse("1957-10-04T19:26:24Z");
        double jd1 = sputnikLaunch.query(Queries.JULIAN_DAY);
        double jd2 = JulianDayHelper.getDoubleFrom(sputnikLaunch);
        System.out.println("jd1 | Datetime: " + sputnikLaunch + " | Julian day: " + jd1);
        System.out.println("jd2 | Datetime: " + sputnikLaunch + " | Julian day: " + jd2);
    }
}
