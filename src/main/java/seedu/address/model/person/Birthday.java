package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author AzuraAiR
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday date can only contain numbers, and should follow the DDMMYYYY format";
    public static final String BIRTHDAY_VALIDATION_REGEX = "\\d{8,8}";
    public final String value;

    private int day;
    private int month;
    private int year;

    /**
     * Constructs a {@code Birthday}.
     *  @param birthday A valid birthday number.
     *
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        this.value = birthday;
        this.day = parseDay(birthday);
        this.month = parseMonth(birthday);
        this.year = parseYear(birthday);
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        int testDay = 0;
        int testMonth = 0;

        // Initial check for DDMMYYYY format
        if (test.matches(BIRTHDAY_VALIDATION_REGEX)) {
            testDay = parseDay(test);
            testMonth = parseMonth(test);
        } else {
            return false;
        }

        // Secondary check for valid day and month
        return (testDay <= 31) && (testDay != 0) && (testMonth <= 12) && (testMonth != 0);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Static method to parse Day from Birthday string
     * isValidBirthday() should be called before this method
     * @param birthday assumed to be of format DDMMYYYY
     * @return integer Day
     */
    private static int parseDay(String birthday) {
        return Integer.parseInt(birthday.substring(0, 2));
    }

    /**
     * Static method to parse Month from Birthday string
     * isValidBirthday() should be called before this method
     * @param birthday assumed to be of format DDMMYYYY
     * @return integer Month
     */
    private static int parseMonth(String birthday) {
        return Integer.parseInt(birthday.substring(2, 4));
    }

    /**
     * Static method to parse Year from Birthday string
     * isValidBirthday() should be called before this method
     * @param birthday assumed to be of format DDMMYYYY
     * @return integer Year
     */
    private static int parseYear(String birthday) {
        return Integer.parseInt(birthday.substring(4, 8));
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}

