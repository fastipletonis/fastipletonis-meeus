package eu.fastipletonis.meeus.temporal;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.FOREVER;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.time.temporal.ValueRange;

public class TemporalFields {
    /**
     * The math context usable in most operations.
     */
    static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(20, RoundingMode.HALF_UP);
    /**
     * The long offset of the Julian day related to the standard Java/UNIX epoch.
     */
    static final long ASTRONOMICAL_JULIAN_DAY_OFFSET = 2440588;

    /**
     * Implementation of the Astronomical Julian Day.
     * <p>
     * Java built-in Julian Day does not use the standard astronomical Julian
     * Day. Moreover, their conversion routine only use a long implementation,
     * leaving out the fractional part, therefore here is implemented the
     * missing bit.
     * <p>
     * This implementation relies heavily on the OpenJDK Julian Day
     * implementation.
     */
    public static final AstronomicalTemporalField ASTRONOMICAL_JULIAN_DAY = new AstronomicalJulianDayField();

    /**
     * Internal representation of the Astronomical Julian Day field.
     */
    private static class AstronomicalJulianDayField extends AstronomicalTemporalField {
        private static final long OFFSET = ASTRONOMICAL_JULIAN_DAY_OFFSET;
        private static final ValueRange RANGE = ValueRange.of(
                    -365243219162L + OFFSET,
                    365241780471L + OFFSET);

        private AstronomicalJulianDayField() {
            super(DAYS, FOREVER, RANGE, true, true);
        }

        @Override
        public String toString() {
            return "AstronomicalJulianDay";
        }

        @Override
        public boolean isSupportedBy(TemporalAccessor temporal) {
            return (temporal instanceof AstronomicalJulianDay);
        }

        @Override
        public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
            if (isSupportedBy(temporal) == false) {
                throw new DateTimeException("Unsupported field: " + this);
            }
            return range();
        }

        @Override
        public long getFrom(TemporalAccessor temporal) {
            double value = Math.floor(this.getFromDouble(temporal));
            return (long) value;
        }

        @Override
        public <R extends Temporal> R adjustInto(R temporal, long newValue) {
            throw new UnsupportedTemporalTypeException("Cannot set " + toString());
        }

        @Override
        public double getFromDouble(TemporalAccessor temporal) {
            if (temporal instanceof AstronomicalTemporalAccessor) {
                return ((AstronomicalTemporalAccessor) temporal).getDouble();
            } else if (temporal.isSupported(java.time.temporal.ChronoField.EPOCH_DAY)) {
                long epochDay = temporal.getLong(java.time.temporal.ChronoField.EPOCH_DAY);
                long nano = temporal.getLong(java.time.temporal.ChronoField.NANO_OF_DAY);
                return 2_440_587.5 + epochDay + (nano / 86_400_000_000_000.0);
            }
            throw new DateTimeException("Unsupported temporal accessor " + temporal);
        }

        @Override
        public BigDecimal getFromBigDecimal(TemporalAccessor temporal) {
            if (temporal instanceof AstronomicalTemporalAccessor) {
                return ((AstronomicalTemporalAccessor) temporal).getBigDecimal();
            } else {
                return BigDecimal.valueOf(getFromDouble(temporal));
            }
        }
    }
}
