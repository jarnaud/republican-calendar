[![master-build Actions Status](https://github.com/jarnaud/republican-calendar/workflows/master-build/badge.svg)](https://github.com/jarnaud/republican-calendar/actions)
[![codecov](https://codecov.io/gh/jarnaud/republican-calendar/branch/master/graph/badge.svg)](https://codecov.io/gh/jarnaud/republican-calendar)

# Republican calendar

The Republican calendar :fr: was created during the French Revolution to remove all
religious and royalist references from the calendar. 
This initiative was also part of a larger movement towards decimalisation.
It was officially used between 1793 and 1805 in France.

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Calendrier-republicain-debucourt2.jpg/800px-Calendrier-republicain-debucourt2.jpg" alt="drawing" width="200"/>

This library allows easy conversion between Gregorian and Republican dates.
It can be used for dates after 1805 (see details about non-historical periods below).

## Dependency

With Maven, add to your dependencies:

```xml
<dependency>
    <groupId>com.github.jarnaud</groupId>
    <artifactId>republican-calendar</artifactId>
    <version>1.3</version>
</dependency>
```

With SBT:

```sbt
libraryDependencies += "com.github.jarnaud" % "republican-calendar" % "1.3"
```

NB: To find the latest version, please refer to [Maven search](https://search.maven.org/artifact/com.github.jarnaud/republican-calendar).

## Quickstart

### Dates

- To convert a Gregorian local date into a Republican date:

```java
LocalDate date = LocalDate.of(1792, 9, 22);
RDate rDate = RDate.of(localDate);
```

- To convert a Republican date into a Gregorian date:

```java
RDate rDate = RDate.of(1, RMonth.Vendemiaire, 1);
LocalDate date = c.toLocalDate();
```

- `RDate` represents a Republican date and provides some utility methods:
`isBefore(RDate)`, `isSextile()`, `plusDays(int)`.

### Time

The Republican calendar uses the decimal time. Each day contains 10 hours, each hour contains 100 minutes
and each minute contains 100 seconds. You can convert between normal and Republican time to the nanosecond precision
that way:

- To convert a normal time into a Republican time:

```java
LocalTime localTime=LocalTime.of(16, 48, 0);
RTime rTime=RTime.of(localTime);
```

- To convert a Republican time into a normal time:

```java
RTime rTime=RTime.of(7, 0);
LocalTime localTime=rTime.toLocalTime();
```

### Date and time

We provide `RDateTime` which is an equivalent to `LocalDateTime` in the Republican calendar.
It combines `RDate` and `RTime`. You can use it in the same way:

- To convert a normal date and time into a Republican date and time:

```java
LocalDateTime dateTime=LocalDateTime.of(1818,12,12,11,7,58);
RDateTime rdt = RDateTime.of(dateTime);
```

- To convert a Republican date and time into a normal date and time:
```java
RDateTime rdt = RDateTime.of(27, RMonth.Frimaire, 21, 4, 63, 87);
LocalDateTime dateTime=rdt.toLocalDateTime();
```

*NB: for RTime and RDateTime, it may happen that converting to normal time then back doesn't
give the original second. It's because decimal seconds are not aligned with the normal seconds.
You can call `.roundSecond()` on both to round the time to the nearest second.*

## Non-historical periods

The Republican calendar was used historically between 1791 and 1805.
However, we can use this calendar for other dates, based on following rules:

- Dates before 1792-09-22 (first day of Republican calendar) are not defined.
- Dates after 1806-01-01 (end of Republican calendar) are estimated based on the commonly used Romme method.

## Links and references

More about the Republican calendar (including current date): 
https://en.wikipedia.org/wiki/French_Republican_calendar

More about the decimal time:
https://en.wikipedia.org/wiki/Decimal_time

**Note about online converters:**
A lot of online converters are incorrect for dates outside the period of real historical
usage of the Republican calendar, mostly due to improper handling of leap years.
