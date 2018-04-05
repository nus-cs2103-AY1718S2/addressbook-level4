package seedu.address.commons.core.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

//@@author trafalgarandre
/**
 * A utility class containing a list of calendars's component objects to be used in tests.
 */
public class TypicalCalendar {
    public static final LocalDate FIRST_DATE =  LocalDate.parse("2018-01-04");
    public static final LocalDate SECOND_DATE =  LocalDate.parse("2018-03-08");
    public static final LocalDateTime FIRST_DATE_TIME = parseDateTime("2018-01-04 12:00");
    public static final LocalDateTime SECOND_DATE_TIME = parseDateTime("2018-03-08 08:00");

    public static final Year FIRST_YEAR = Year.parse("2017");
    public static final Year SECOND_YEAR = Year.parse("2018");
    public static final int FIRST_WEEK = Integer.parseInt("05");
    public static final int SECOND_WEEK = Integer.parseInt("30");
    public static final YearMonth FIRST_YEAR_MONTH = YearMonth.parse("2018-03");
    public static final YearMonth SECOND_YEAR_MONTH = YearMonth.parse("2018-04");

    /**
     *
     * @param dateTime in YY-MM-DD HH-mm
     * @return LocalDateTime
     */
    private static LocalDateTime parseDateTime(String dateTime) {
        LocalDateTime result = null;
        try {
            result =  ParserUtil.parseDateTime(dateTime);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
