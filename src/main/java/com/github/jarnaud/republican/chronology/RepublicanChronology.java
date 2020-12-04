package com.github.jarnaud.republican.chronology;

import com.github.jarnaud.republican.GRConverter;
import com.github.jarnaud.republican.RDate;

import java.time.chrono.AbstractChronology;
import java.time.chrono.Era;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.ValueRange;
import java.util.List;

import static com.github.jarnaud.republican.chronology.RepublicanEra.FRENCH;

/**
 * The Republican chronology.
 */
public final class RepublicanChronology
//        extends AbstractChronology
{

    /**
     * Singleton instance for Republican chronology.
     */
    public static final RepublicanChronology INSTANCE = new RepublicanChronology();

    private RepublicanChronology() {
    }

//    @Override
//    public String getId() {
//        return "Republican";
//    }
//
//    @Override
//    public String getCalendarType() {
//        return "republican";
//    }
//
//    @Override
//    public RDate date(Era era, int yearOfEra, int month, int dayOfMonth) {
//        if (!(era instanceof RepublicanEra)) {
//            throw new ClassCastException("Era must be RepublicanEra");
//        }
//        if (era != FRENCH) {
//            throw new RuntimeException("Era must be FRENCH");
//        }
//        return RDate.of(yearOfEra, month, dayOfMonth);
//    }
//
//    @Override
//    public RDate date(int prolepticYear, int month, int dayOfMonth) {
//        GRConverter grConverter=new GRConverter();
//
//        return new RDate();
//    }
//
//    @Override
//    public RDate dateYearDay(int prolepticYear, int dayOfYear) {
//        return null;
//    }
//
//    @Override
//    public RDate dateEpochDay(long epochDay) {
//        return null;
//    }
//
//    @Override
//    public RDate date(TemporalAccessor temporal) {
//        return null;
//    }
//
//    @Override
//    public boolean isLeapYear(long prolepticYear) {
//        return false;
//    }
//
//    @Override
//    public int prolepticYear(Era era, int yearOfEra) {
//        return 0;
//    }
//
//    @Override
//    public Era eraOf(int eraValue) {
//        return null;
//    }
//
//    @Override
//    public List<Era> eras() {
//        return null;
//    }
//
//    @Override
//    public ValueRange range(ChronoField field) {
//        return null;
//    }
}
