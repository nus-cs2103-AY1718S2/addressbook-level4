package seedu.address.testutil;
//@@author SuxianAlicia

import java.time.LocalDate;

import seedu.address.commons.util.DateUtil;

/**
 * A utility class containing a list of {@code Preference} Objects to be used in tests.
 */
public class TypicalLocalDates {
    public static final String LEAP_YEAR_DATE_STRING = "29-02-2016";
    public static final String NORMAL_DATE_STRING = "06-06-1990";

    // Conversion to LocalDate from strings should not fail.
    public static final LocalDate LEAP_YEAR_DATE = DateUtil.convertStringToDate(LEAP_YEAR_DATE_STRING);
    public static final LocalDate NORMAL_DATE = DateUtil.convertStringToDate(NORMAL_DATE_STRING);

    private TypicalLocalDates() {}

}
