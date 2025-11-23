/*
 *   Test suite for JulianDayHelper.
 *
 *   Copyright (C) 2025 Marco Confalonieri <marco at marcoconfalonieri.it>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package eu.fastipletonis.meeus.temporal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.FieldSource;

public class JulianDayHelperTest {
    static final Collection<Arguments> isSupportedArgs = Arrays.asList(
        arguments(true, LocalDateTime.now()),
        arguments(true, ZonedDateTime.now()),
        arguments(false, LocalDate.now()),
        arguments(false, LocalTime.now()),
        arguments(false, Instant.now())
    );

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock ="""
        EXPECTED,       INPUT
        2436116.31,   1957-10-04T19:26:24    
        1842713.0,    0333-01-27T12:00:00
    """)
    void testGetBigDecimalFrom(String e, String dateTime) {
        final BigDecimal expected = new BigDecimal(e);
        final LocalDateTime input = LocalDateTime.parse(dateTime);
        final BigDecimal actual = JulianDayHelper.getBigDecimalFrom(input);
        assertEquals(expected, actual);
    }

    @Test
    void testGetDoubleFrom() {

    }

    @ParameterizedTest
    @FieldSource("isSupportedArgs")
    void testIsSupported(boolean expected, TemporalAccessor input) {
        final boolean actual = JulianDayHelper.isSupported(input);
        assertEquals(expected, actual);
    }
}
