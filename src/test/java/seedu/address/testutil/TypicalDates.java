package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import seedu.address.model.person.DateAdded;

/**
 * A utility class containing a list of {@code DateAdded} objects to be used in tests.
 */
public class TypicalDates {
    public static final DateAdded DATE_FIRST_JAN = new DateAdded("01/01/2018");
    public static final DateAdded DATE_SECOND_FEB = new DateAdded("02/02/2018");
    public static final DateAdded DATE_THIRD_MAR = new DateAdded("03/03/2018");
    public static final String VALID_DATE_DESC = " " + PREFIX_DATE + "01/01/2018";
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "12/34";
}
