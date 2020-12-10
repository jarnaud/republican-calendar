package com.github.jarnaud.republican;

import java.time.temporal.*;

/**
 * Fake class implementing Temporal field without being a Chrono field.
 * Used for tests to verify proper handling of non-chrono field.
 */
class FakeTemporalField implements TemporalField {

    @Override
    public TemporalUnit getBaseUnit() {
        return null;
    }

    @Override
    public TemporalUnit getRangeUnit() {
        return null;
    }

    @Override
    public ValueRange range() {
        return null;
    }

    @Override
    public boolean isDateBased() {
        return false;
    }

    @Override
    public boolean isTimeBased() {
        return false;
    }

    @Override
    public boolean isSupportedBy(TemporalAccessor temporal) {
        return false;
    }

    @Override
    public ValueRange rangeRefinedBy(TemporalAccessor temporal) {
        return null;
    }

    @Override
    public long getFrom(TemporalAccessor temporal) {
        return 0;
    }

    @Override
    public <R extends Temporal> R adjustInto(R temporal, long newValue) {
        return null;
    }
}
