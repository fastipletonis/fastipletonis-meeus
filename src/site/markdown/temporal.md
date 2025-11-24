# Usage of the temporal package

The package `eu.fastipletonis.astro.temporal` contains some utilities required
when manipulating time from an astronomical perspective.

## Julian day

The library allows to retrieve the Julian day from a supported temporal
accessor, such as `ZonedDateTime` or `LocalDateTime` in two different ways:

- using the methods of the `JulianDayHelper` class directly:
  ```java
    ZonedDateTime sputnikLaunch = ZonedDateTime.parse("1957-10-04T19:26:24Z");

    // Alternatively, we can use JulianDayHelper.getBigDecimalFrom for
    // a higher precision.
    double jd = JulianDayHelper.getDoubleFrom(sputnikLaunch);
  ```
- using the queries defined in the `Queries`class:
  ```java
    ZonedDateTime sputnikLaunch = ZonedDateTime.parse("1957-10-04T19:26:24Z");

    // Alternatively, we can use Queries.HP_JULIAN_DAY for a higher precision.
    double jd1 = sputnikLaunch.query(Queries.JULIAN_DAY);
  ```
