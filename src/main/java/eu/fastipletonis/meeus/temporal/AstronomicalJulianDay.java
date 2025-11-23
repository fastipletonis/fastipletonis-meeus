/*
 *   Astronomical Julian Day.
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

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.YEAR;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalField;

/**
 * Accessor that implements a decimal Astronomical Julian Day.
 * <p>
 * The standard Java implementation for {@link JulianFields#JULIAN_DAY} only
 * implements long results. Astronomical calculations often require a Julian
 * Day able to handle the time of the day as well.
 */
public class AstronomicalJulianDay implements AstronomicalTemporalAccessor {
    // Math context for the required calculations.
    private static final MathContext MC = new MathContext(20, RoundingMode.DOWN);
    // Constants converted to big decimals.
    private static final BigDecimal C100 = new BigDecimal(100);
    private static final BigDecimal C2 = new BigDecimal(2);
    private static final BigDecimal C4 = new BigDecimal(4);
    private static final BigDecimal C365_25 = new BigDecimal("365.25");
    private static final BigDecimal C4716 = new BigDecimal(4716);
    private static final BigDecimal C30_6001 = new BigDecimal("30.6001");
    private static final BigDecimal C1524_5 = new BigDecimal("1524.5");
    private static final BigDecimal C1 = BigDecimal.ONE;

    // Used for compatibility.
    private final Instant instant;
    private final ZoneId zoneId;
    // The calculated value.
    private final BigDecimal value;

    /**
     * Builds an Astronomical Julian Day object from the current instant and
     * the system time zone.
     *
     * @return an instance of AstronomicalJulianDay
     */
    public static AstronomicalJulianDay now() {
        return new AstronomicalJulianDay(Instant.now(), ZoneOffset.systemDefault());
    }
    
    /**
     * Calculates the value of the Julian day from an instant.
     * 
     * @param instant the instant to calculate from
     * @return a BigDecimal value for the Julian Day.
     */
    private BigDecimal calcValue(Instant instant, ZoneId zoneId) {
        ZonedDateTime zdt = instant.atZone(zoneId);
        final boolean isJulian = zdt.isBefore(ZonedDateTime.of(1582, 10, 15, 0, 0, 0, 0, zoneId));
        final int y0 = zdt.get(YEAR);
        final int m0 = zdt.get(MONTH_OF_YEAR);
        final BigDecimal y = (m0 > 2)? new BigDecimal(y0) : new BigDecimal(y0 -1);
        final BigDecimal m = (m0 > 2)? new BigDecimal(m0) : new BigDecimal(m0 + 12);
        final BigDecimal dt = DecimalTime.asBigDecimal(instant);
        final BigDecimal d0 = new BigDecimal(zdt.get(DAY_OF_MONTH));
        final BigDecimal d = d0.add(dt);
        final BigDecimal a = y.divideToIntegralValue(C100, MC);
        final BigDecimal b = isJulian? BigDecimal.ZERO : C2.subtract(a).add(a.divideToIntegralValue(C4, MC));
        final BigDecimal jd =
                C365_25.multiply(y.add(C4716)).setScale(0, RoundingMode.DOWN)
                .add(C30_6001.multiply(m.add(C1)).setScale(0, RoundingMode.DOWN))
                .add(d).add(b).subtract(C1524_5);
        return jd;
    }

    /**
     * Returns an astronomical julian date built from an Instant a ZoneOffset.
     *
     * @param instant the instant to build from
     * @param zoneOffset the zone offset
     */

    public static AstronomicalJulianDay of(Instant instant, ZoneOffset zoneOffset) {
        return new AstronomicalJulianDay(instant, zoneOffset);
    }

    /**
     * Returns an astronomical julian date built from a LocalDateTime.
     * The {@link ZoneId} is the system default.
     *
     * @param dateTime the local date-time
     */
    public static AstronomicalJulianDay of(LocalDateTime dateTime) {
        final ZoneId zoneId = ZoneId.systemDefault();
        final ZoneOffset zoneOffset = zoneId.getRules().getOffset(dateTime);
        final Instant instant = dateTime.toInstant(zoneOffset);
        return new AstronomicalJulianDay(instant, zoneId);
    }

    /**
     * Returns an astronomical julian date built from a ZonedDateTime.
     *
     * @param dateTime the zoned date-time
     */
    public static AstronomicalJulianDay of(ZonedDateTime dateTime) {
        final ZoneId zoneId = dateTime.getZone();
        final Instant instant = dateTime.toInstant();
        return new AstronomicalJulianDay(instant, zoneId);
    }

    /**
     * Return an astronomical Julian date from a date and a dynamic time.
     * Please notice that in this case it will be an Ephemeral Julian Day.
     * The {@link ZoneOffset} is assumed as UTC.
     * 
     * @param date the date
     * @param dynamicTime time of day expressed as BigDecimal
     */
    public static AstronomicalJulianDay ofUTCDecimalTime(LocalDate date, BigDecimal decimalTime) {
        final ZoneId zoneId = ZoneId.of("UTC");
        final LocalTime time = DecimalTime.toLocalTime(decimalTime);
        final LocalDateTime dateTime = LocalDateTime.of(date, time);
        final Instant instant = dateTime.toInstant(ZoneOffset.UTC);
        return new AstronomicalJulianDay(instant, zoneId);
    }

    /**
     * Return an astronomical Julian date from a date and a dynamic time.
     * Please notice that in this case it will be an Ephemeral Julian Day.
     * The {@link ZoneOffset} is assumed as UTC.
     * 
     * @param date the local date
     * @param dt time of day expressed as double
     */
    public static AstronomicalJulianDay ofUTCDecimalTime(LocalDate date, double decimalTime) {
        final LocalTime time = DecimalTime.toLocalTime(decimalTime);
        final LocalDateTime dateTime = LocalDateTime.of(date, time);
        final Instant instant = dateTime.toInstant(ZoneOffset.UTC);
        return new AstronomicalJulianDay(instant, ZoneOffset.UTC);
    }

    /**
     * Builds an astronomical julian date from an Instant.
     *
     * @param instant the instant to build from
     * @param zoneId the time zone
     */
    public AstronomicalJulianDay(Instant instant, ZoneId zoneId) {
        this.instant = instant;
        this.zoneId = zoneId;
        value = calcValue(instant, zoneId);
    }

    @Override
    public BigDecimal getBigDecimal() {
        return value;
    }

    @Override
    public double getDouble() {
        return value.doubleValue();
    }

    @Override
    public boolean isSupported(TemporalField field) {
        // Support standard fields
        return field == TemporalFields.ASTRONOMICAL_JULIAN_DAY
            || field == JulianFields.JULIAN_DAY // Standard Java long-only field
            || instant.atZone(zoneId).isSupported(field); // For any other case we refer to the ZonedDateTime
    }

    @Override
    public long getLong(TemporalField field) {
        if (field == JulianFields.JULIAN_DAY || field == TemporalFields.ASTRONOMICAL_JULIAN_DAY) {
            // Return the integer part (noon-to-noon) required by Java standard
            return value.setScale(0, RoundingMode.DOWN).longValue();
        } else {
            return instant.atZone(zoneId).getLong(field);
        }
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }
}
