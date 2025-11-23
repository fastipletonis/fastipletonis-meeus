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
package eu.fastipletonis.astro.temporal;

import java.math.BigDecimal;
import java.time.temporal.TemporalQuery;

/**
 * Temporal queries used for retrieving time.
 */
public class Queries {

    // Prevent instantiation
    private Queries() {}

    /**
     * Query for returning the Astronomical Julian Day as a double. Returns
     * null if the temporal object does not support Julian Days.
     */
    public static final TemporalQuery<Double> JULIAN_DAY = (temporal) -> {
        if (JulianDayHelper.isSupported(temporal)) {
            return JulianDayHelper.getDoubleFrom(temporal);
        } else return null;
    };
    
    /**
     * Query for returning the Astronomical Julian Day as a high-precision
     * BigDecimal. Returns null if the temporal object does not support Julian
     * Days.
     */
    public static final TemporalQuery<BigDecimal> HP_JULIAN_DAY = (temporal) -> {
        if (JulianDayHelper.isSupported(temporal)) {
            return JulianDayHelper.getBigDecimalFrom(temporal);
        } else return null;
    };
}
