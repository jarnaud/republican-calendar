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

## Usage

### Dependency

With Maven, add to your dependencies:

```xml
<dependency>
    <groupId>com.github.jarnaud</groupId>
    <artifactId>republican-calendar</artifactId>
    <version>1.2</version>
</dependency>
```

With SBT:

```sbt
libraryDependencies += "com.github.jarnaud" % "republican-calendar" % "1.2"
```

NB: To find the latest version, please refer to [Maven search](https://search.maven.org/artifact/com.github.jarnaud/republican-calendar).

### Quickstart

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
