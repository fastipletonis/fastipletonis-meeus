package eu.fastipletonis.meeus.temporal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DecimalTimeTest {
    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock ="""
        EXPECTED,   INPUT
        '0',        00:00:00    
        '0.5',      12:00:00
        '0.81',     19:26:24
    """)
    void testAsBigDecimal(String decimalTime, String time) {
        final BigDecimal expected = new BigDecimal(decimalTime);
        final LocalTime input = LocalTime.parse(time);
        final BigDecimal actual = DecimalTime.asBigDecimal(input);
        assertEquals(expected, actual);
    }
    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock ="""
        EXPECTED,   INPUT    
        '0.0',      00:00:00    
        '0.5',      12:00:00
        '0.81',     19:26:24
    """)
    void testAsDouble(String decimalTime, String time) {
        final double expected = Double.parseDouble(decimalTime);
        final LocalTime input = LocalTime.parse(time);
        final double actual = DecimalTime.asDouble(input);
        assertEquals(expected, actual, 0.0001);
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock ="""
        EXPECTED,   INPUT
        00:00:00,   '0.0'
        12:00:00,   '0.5'
        19:26:24,   '0.81'
    """)
    void testToLocalTime_double(String time, String decimalTime) {
        final LocalTime expected = LocalTime.parse(time);
        final double input = Double.parseDouble(decimalTime);
        final LocalTime actual = DecimalTime.toLocalTime(input);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock ="""
        EXPECTED,   INPUT
        00:00:00,   '0'
        19:26:24,   '0.81'
        12:00:00,   '0.5'
    """)
    void testToLocalTime_BigDecimal(String time, String decimalTime) {
        final LocalTime expected = LocalTime.parse(time);
        final BigDecimal input = new BigDecimal(decimalTime);
        final LocalTime actual = DecimalTime.toLocalTime(input);
        assertEquals(expected, actual);
    }
}
