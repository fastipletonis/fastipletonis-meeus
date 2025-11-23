package eu.fastipletonis.meeus.temporal;

import static java.time.temporal.ChronoField.NANO_OF_DAY;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * Conversion routines for getting the decimal time.
 */
public class DecimalTime {
    private static final MathContext MC = new MathContext(20, RoundingMode.HALF_UP);
    private static final long NANOS_PER_DAY_L = 86_400_000_000_000L;
    private static final BigDecimal NANOS_PER_DAY_BD = BigDecimal.valueOf(NANOS_PER_DAY_L);
    private static final double NANOS_PER_DAY_D = NANOS_PER_DAY_BD.doubleValue();

    /**
     * Returns a decimal time from a temporal accessor object as a double
     * value.
     * 
     * @param temporal temporal accessor that supports NANO_OF_DAY
     * 
     * @return a double value representing the decimal time.
     */
    public static double asDouble(TemporalAccessor temporal) {
        final double nanos = (double) temporal.getLong(NANO_OF_DAY);
        return nanos / NANOS_PER_DAY_D;
    }

    /**
     * Returns a decimal time from a temporal accessor object as a BigDecimal
     * value.
     * 
     * @param temporal temporal accessor that supports NANO_OF_DAY
     * 
     * @return a BigDecimal value representing the decimal time.
     */
    public static BigDecimal asBigDecimal(TemporalAccessor temporal) {
        final BigDecimal nanos = BigDecimal.valueOf(temporal.getLong(NANO_OF_DAY));
        return nanos.divide(NANOS_PER_DAY_BD, MC);
    }

    /**
     * Converts the given decimal time to a LocalTime object.
     * 
     * @param dt decimal time
     * 
     * @return the equivalent LocalTime.
     */
    public static LocalTime toLocalTime(double dt) {
        final double nanoOfDay = dt * NANOS_PER_DAY_D;
        return LocalTime.ofNanoOfDay((long) nanoOfDay);
    }

    /**
     * Converts the given decimal time to a LocalTime object.
     * 
     * @param dt decimal time
     * 
     * @return the equivalent LocalTime.
     */
    public static LocalTime toLocalTime(BigDecimal dt) {
        final BigDecimal nanoOfDay = dt.multiply(NANOS_PER_DAY_BD);
        return LocalTime.ofNanoOfDay(nanoOfDay.longValue());
    }
}
