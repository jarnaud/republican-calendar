# Republican calendar

The :fr: Republican calendar was created during the Revolution to remove all
religious and royalist references from the calendar. 
This initiative was also part of a larger movement towards decimalisation.
It was officially used between 1793 and 1805 in France.

<img src="https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/Calendrier-republicain-debucourt2.jpg/800px-Calendrier-republicain-debucourt2.jpg" alt="drawing" width="200"/>

This library allows easy conversion between Gregorian and Republican dates.

### Usage

With Maven, add to your dependencies:

```xml
<dependency>
    <groupId>com.github.jarnaud</groupId>
    <artifactId>republican-calendar</artifactId>
    <version>1.0</version>
</dependency>
```

With SBT:

```sbt
libraryDependencies += "com.github.jarnaud" % "republican-calendar" % "1.0"
```

### Usage

Create a `GRConverter` to convert from Gregorian to Republican dates.


### Non-historical period

The Republican calendar was used historically between 1791 and 1805.
However, we can use this calendar for other dates, based on following rules:

- Dates before 1792-22-01 are not defined.
- Dates after 1806-01-01 (end of Republican calendar) are estimated based on the commonly used Romme method.

### Links and references

More about the Republican calendar (including current date): 
https://en.wikipedia.org/wiki/French_Republican_calendar

**Note about online converters:**
A lot of online converters are incorrect for dates outside the period of real historical
usage of the Republican calendar, mostly due to improper handling of leap years.
