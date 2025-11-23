/*
 *   Interface for astronomical temporal accessors.
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
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

import eu.fastipletonis.meeus.temporal.AstronomicalTemporalQueries.AstronomicalQuery;
import eu.fastipletonis.meeus.temporal.AstronomicalTemporalQueries.HPAstronomicalQuery;

/**
 * Interface for astronomical temporal accessors that requires queries to
 * double-precision fields.
 */
public interface AstronomicalTemporalAccessor extends TemporalAccessor, Comparable<AstronomicalTemporalAccessor> {
    public double getDouble();
    public BigDecimal getBigDecimal();

    @Override
    @SuppressWarnings("unchecked")
    default <R> R query(TemporalQuery<R> query) {
        if (query instanceof AstronomicalQuery) { 
            return (R) Double.valueOf(getDouble());
        } else if (query instanceof HPAstronomicalQuery) { 
            return (R) getBigDecimal();
        }   
        return TemporalAccessor.super.query(query);
    }

    @Override
    default int compareTo(AstronomicalTemporalAccessor other) {
        return getBigDecimal().compareTo(other.getBigDecimal());
    }
}
