package eu.fastipletonis.meeus.temporal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

public class AstronomicalJulianDayTest {
    private static final ZonedDateTime sputnikZDT = ZonedDateTime.of(1957, 10, 4, 19, 26, 24, 0, ZoneOffset.UTC);
    private static final AstronomicalJulianDay sputnikDay = new AstronomicalJulianDay(sputnikZDT.toInstant(), ZoneOffset.UTC);
    private static final BigDecimal jd = new BigDecimal("2436116.31");
    private static final long jdLong = jd.setScale(0, RoundingMode.DOWN).longValue();
    protected static final Collection<Arguments> getLongFields =
            Arrays.asList(
                arguments(TemporalFields.ASTRONOMICAL_JULIAN_DAY, jdLong),
                arguments(JulianFields.JULIAN_DAY, jdLong),
                arguments(ChronoField.INSTANT_SECONDS, sputnikZDT.toInstant().getEpochSecond()),
                arguments(ChronoField.NANO_OF_SECOND, sputnikZDT.toInstant().getNano()),
                arguments(ChronoField.AMPM_OF_DAY, sputnikZDT.get(ChronoField.AMPM_OF_DAY))
            );

    @Test
    void testGetBigDecimal() {
        final BigDecimal expected = jd;
        final BigDecimal actual = sputnikDay.getBigDecimal();
        assertEquals(expected, actual);
    }

    @Test
    void testGetDouble() {
        final double expected = jd.doubleValue();
        final double actual = sputnikDay.getDouble();
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name="testSuccessfulGetLong({0})")
    @FieldSource("getLongFields")
    void testGetLong(TemporalField field, long expected) {
        final long actual = sputnikDay.getLong(field);
        assertEquals(expected, actual);
    }

    @Test
    void testIsSupported() {

    }

    @Test
    void testNow() {

    }

    @Test
    void testOf() {

    }

    @Test
    void testOf2() {

    }

    @Test
    void testOf3() {

    }

    @Test
    void testOfUTCDecimalTime() {

    }

    @Test
    void testOfUTCDecimalTime2() {

    }

    @Test
    void testToString() {

    }
}
