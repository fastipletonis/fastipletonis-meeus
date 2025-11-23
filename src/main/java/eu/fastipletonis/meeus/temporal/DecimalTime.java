package eu.fastipletonis.meeus.temporal;

import static java.time.temporal.ChronoField.NANO_OF_DAY;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Conversion routines for getting the decimal time.
 */
public class DecimalTime {
    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final long NANOS_PER_DAY_L = 86_400_000_000_000L;
    private static final BigDecimal NANOS_PER_DAY_BD = BigDecimal.valueOf(NANOS_PER_DAY_L);
    private static final double NANOS_PER_DAY_D = NANOS_PER_DAY_BD.doubleValue();

    /**
     * Returns a decimal time from a {@link Instant} object as a double value.
     * The time zone is considered to be UTC.
     * 
     * @param t the local time
     * 
     * @return a double value representing the dynamic time.
     */
    public static double asDouble(Instant t) {
        final ZonedDateTime zdt = t.atZone(UTC);
        final double nanos = (double) zdt.getLong(NANO_OF_DAY);
        return nanos / NANOS_PER_DAY_D;
    }


    public static BigDecimal asBigDecimal(Instant t) {
        final MathContext mc = TemporalFields.DEFAULT_MATH_CONTEXT;
        final ZonedDateTime zdt = t.atZone(UTC);
        final BigDecimal nanos = BigDecimal.valueOf(zdt.getLong(NANO_OF_DAY));
        return nanos.divide(NANOS_PER_DAY_BD, mc);
    }

    /**
     * Builds a dynamic time from a ZonedDateTime object.
     * 
     * @param zdt the zoned date time
     * @return
     */
    public static BigDecimal asBigDecimal(ZonedDateTime zdt) {
        final MathContext mc = TemporalFields.DEFAULT_MATH_CONTEXT;
        final ZonedDateTime zdtUTC = zdt.withZoneSameInstant(UTC);
        final BigDecimal nanos = BigDecimal.valueOf(zdtUTC.getLong(NANO_OF_DAY));
        return nanos.divide(NANOS_PER_DAY_BD, mc);
    }

    public static LocalTime toLocalTime(double dt) {
        final double nanoOfDay = dt * NANOS_PER_DAY_D;
        return LocalTime.ofNanoOfDay((long) nanoOfDay);
    }

    public static LocalTime toLocalTime(BigDecimal dt) {
        final BigDecimal nanoOfDay = dt.multiply(NANOS_PER_DAY_BD);
        return LocalTime.ofNanoOfDay(nanoOfDay.longValue());
    }
}
