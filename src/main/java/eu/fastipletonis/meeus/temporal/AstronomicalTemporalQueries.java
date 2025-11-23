/*
 *   Astronomical temporal queries.
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

import java.math.BigDecimal;
import java.time.temporal.TemporalQuery;

/**
 * Temporal queries used for retrieving time.
 */
public class AstronomicalTemporalQueries {

    // Prevent instantiation
    private AstronomicalTemporalQueries() {}

    /**
     * Astronomical queries requiring double values.
     */
    static interface AstronomicalQuery extends TemporalQuery<Double> {}

    /**
     * High-precision Astronomical queries that require BigDecimal values.
     */
    static interface HPAstronomicalQuery extends TemporalQuery<BigDecimal> {}

    /**
     * Query for returning the Astronomical Julian Day as a double.
     * Returns null if the temporal object does not support Julian Days.
     */
    public static final AstronomicalQuery ASTRONOMICAL_JULIAN_DAY = (temporal) -> {
        Double result = null;
        if (temporal instanceof AstronomicalTemporalAccessor) {
            result = ((AstronomicalTemporalAccessor) temporal).getDouble();
        } else if (temporal.isSupported(java.time.temporal.ChronoField.EPOCH_DAY)) {
             long epochDay = temporal.getLong(java.time.temporal.ChronoField.EPOCH_DAY);
             long nano = temporal.getLong(java.time.temporal.ChronoField.NANO_OF_DAY);
             result = 2_440_587.5 + epochDay + (nano / 86_400_000_000_000.0);
        }
        return result;
    };
    
    /**
     * Query for returning the Astronomical Julian Day as a BigDecimal.
     * Returns null if the temporal object does not implement
     * {@link AstronomicalTemporalAccessor}.
     */
    public static final HPAstronomicalQuery HP_ASTRONOMICAL_JULIAN_DAY = (temporal) -> {
        BigDecimal result = null;
        if (temporal instanceof AstronomicalTemporalAccessor) {
            result = ((AstronomicalTemporalAccessor) temporal).getBigDecimal();
        }
        return result;
    };
}
