package seedu.address.commons.core.appointment;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

//@@author trafalgarandre
/**
 * A utility class containing a list of calendars's component objects to be used in tests.
 */
public class TypicalCalendar {
    public static final LocalDate FIRST_DATE =  LocalDate.parse("2018-01-04");
    public static final LocalDate SECOND_DATE =  LocalDate.parse("2018-03-08");
    public static final Year FIRST_YEAR = Year.parse("2017");
    public static final Year SECOND_YEAR = Year.parse("2018");
    public static final int FIRST_WEEK = Integer.parseInt("05");
    public static final int SECOND_WEEK = Integer.parseInt("30");
    public static final YearMonth FIRST_YEAR_MONTH = YearMonth.parse("2018-03");
    public static final YearMonth SECOND_YEAR_MONTH = YearMonth.parse("2018-04");
}
