/*
 *   Decimal Time routines..
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

import static java.time.temporal.ChronoField.NANO_OF_DAY;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;

/**
 * Conversion routines for the decimal time.
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
