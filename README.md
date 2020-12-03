[![master-build Actions Status](https://github.com/jarnaud/republican-calendar/workflows/master-build/badge.svg)](https://github.com/jarnaud/republican-calendar/actions)

# Republican calendar

The :fr: Republican calendar was created during the Revolution to remove all
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
    <version>1.1</version>
</dependency>
```

With SBT:

```sbt
libraryDependencies += "com.github.jarnaud" % "republican-calendar" % "1.1"
```

NB: To find the latest version, please refer to [Maven search](https://search.maven.org/artifact/com.github.jarnaud/republican-calendar).

### Quickstart

- Create a `GRConverter` and call `convert(..)` to convert from Gregorian to Republican dates.
- Create a `RGConverter` and call `convert(..)` to convert from Republican to Gregorian dates.


## Non-historical periods

The Republican calendar was used historically between 1791 and 1805.
However, we can use this calendar for other dates, based on following rules:

- Dates before 1792-22-01 are not defined.
- Dates after 1806-01-01 (end of Republican calendar) are estimated based on the commonly used Romme method.

## Links and references

More about the Republican calendar (including current date): 
https://en.wikipedia.org/wiki/French_Republican_calendar

**Note about online converters:**
A lot of online converters are incorrect for dates outside the period of real historical
usage of the Republican calendar, mostly due to improper handling of leap years.
