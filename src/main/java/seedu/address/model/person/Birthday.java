package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

//@@author AzuraAiR
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday date can only contain numbers, and should follow the DDMMYYYY format";
    public static final String MESSAGE_INVALID_BIRTHDAY =
            "Birthday day is invalid";
    public static final String MESSAGE_INVALID_BIRTHMONTH =
            "Birthday month is invalid";
    public static final String MESSAGE_FUTURE_BIRTHDAY =
            "Birthday is set in the future";
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

        if (isValidBirthday(birthday)) {
            this.value = birthday;
            this.day = parseDay(birthday);
            this.month = parseMonth(birthday);
            this.year = parseYear(birthday);
        } else {
            this.value = null;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) throws IllegalArgumentException {
        LocalDate today = LocalDate.now();
        int testDay;
        int testMonth;
        int testYear;

        // Check for DDMMYYYY format
        if (test.matches(BIRTHDAY_VALIDATION_REGEX)) {
            testDay = parseDay(test);
            testMonth = parseMonth(test);
            testYear = parseYear(test);
        } else {
            throw new IllegalArgumentException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        // Check for valid year
        if (today.getYear() < testYear) {
            throw new IllegalArgumentException(MESSAGE_FUTURE_BIRTHDAY);
        }
        // Check for valid day
        if (testDay == 0) {
            throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
        }

        // Check for valid month and day
        switch (testMonth) {
        case 1:     // Jan
        case 3:     // Mar
        case 5:     // May
        case 7:     // Jul
        case 8:     // Aug
        case 10:    // Oct
        case 12:    // Dec
            if (testDay > 31) {
                throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
            }
            break;
        case 4: // Apr
        case 6: // Jun
        case 9: // Sep
        case 11: // Nov
            if (testDay > 30) {
                throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
            }
            break;
        case 2: // Feb
            if (testDay > 28) {
                throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHDAY);
            }
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_BIRTHMONTH);
        }

        // Check for future date
        if (today.getYear() == testYear) {
            if (today.getMonthValue() < testMonth) {
                throw new IllegalArgumentException(MESSAGE_FUTURE_BIRTHDAY);
            } else if (today.getMonthValue() == testMonth && today.getDayOfMonth() < testDay) {
                throw new IllegalArgumentException(MESSAGE_FUTURE_BIRTHDAY);
            }
        }

        return true;
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

