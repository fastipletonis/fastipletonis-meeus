/*
 *   Astronomical temporal field.
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
import java.time.DateTimeException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;

/**
 * Base class for the astronomical temporal fields.
 */
abstract class AstronomicalTemporalField implements TemporalField {
        private final TemporalUnit baseUnit;
        private final TemporalUnit rangeUnit;
        private final ValueRange range;
        private final boolean dateBased;
        private final boolean timeBased;

        AstronomicalTemporalField(
            TemporalUnit baseUnit,
            TemporalUnit rangeUnit,
            ValueRange range,
            boolean dateBased,
            boolean timeBased
        ) {
            this.baseUnit = baseUnit;
            this.rangeUnit = rangeUnit;
            this.range = range;
            this.timeBased = timeBased;
            this.dateBased = dateBased;
        }

        @Override
        public TemporalUnit getBaseUnit() {
            return baseUnit;
        }

        @Override
        public TemporalUnit getRangeUnit() {
            return rangeUnit;
        }

        @Override
        public ValueRange range() {
            return range;
        }

        @Override
        public boolean isDateBased() {
            return dateBased;
        }

        @Override
        public boolean isTimeBased() {
            return timeBased;
        }

        public double getFromDouble(TemporalAccessor temporal) {
            if (temporal instanceof AstronomicalTemporalAccessor) {
                return ((AstronomicalTemporalAccessor) temporal).getDouble();
            }
            throw new DateTimeException("Unsupported temporal accessor");
        }

        public BigDecimal getFromBigDecimal(TemporalAccessor temporal) {
            if (temporal instanceof AstronomicalTemporalAccessor) {
                return ((AstronomicalTemporalAccessor) temporal).getBigDecimal();
            }
            throw new DateTimeException("Unsupported temporal accessor");
        }
}
