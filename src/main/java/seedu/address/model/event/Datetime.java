package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author x3tsunayh

/**
 * Represents an Event's Datetime in the event book.
 * Ensures valid Datetime input and aids future implementations involving NLP, etc.
 */
public class Datetime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event datetime should be in the format: yyyy-mm-dd hhmm\n"
            + "Datetime values should also be logical (eg. hhmm should be between 0000 to 2359)";

    private static final int VALID_DATETIME_LENGTH = 15;

    public final String value;

    /**
     * Validates given datetime.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public Datetime(String datetime) throws IllegalValueException {
        requireNonNull(datetime);
        String trimmedDatetime = datetime.trim();

        if (!isValidDatetime(trimmedDatetime)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = trimmedDatetime;
    }

    /**
     * Returns true if a given string is a valid event datetime.
     */
    public static boolean isValidDatetime(String datetime) {
        Boolean validTime = false;
        Boolean validDate = false;
        Boolean validFormat = false;

        if (datetime.length() != VALID_DATETIME_LENGTH) {
            return false;
        }

        try {
            int year = Integer.parseInt(datetime.substring(0, 4));
            int month = Integer.parseInt(datetime.substring(5, 7));
            int day = Integer.parseInt(datetime.substring(8, 10));
            int hour = Integer.parseInt(datetime.substring(11, 13));
            int min = Integer.parseInt(datetime.substring(13, 15));

            String firstSeperator = datetime.substring(4, 5);
            String secondSeperator = datetime.substring(7, 8);
            String thirdSeperator = datetime.substring(10, 11);

            //Format Validation
            if (firstSeperator.equals("-") & secondSeperator.equals("-") & thirdSeperator.equals(" ")) {
                validFormat = true;
            }

            //Time Validation
            if (0 <= hour && hour <= 23) {
                if (0 <= min && min <= 59) {
                    validTime = true;
                }
            }

            //Date Validation
            if (day >= 1) {
                // For months with 30 days.
                if ((month == 4
                        || month == 6
                        || month == 9
                        || month == 11)
                        && day <= 30) {
                    validDate = true;
                }
                // For months with 31 days.
                if ((month == 1
                        || month == 3
                        || month == 5
                        || month == 7
                        || month == 8
                        || month == 10
                        || month == 12)
                        && day <= 31) {
                    validDate = true;
                }
                // For February.
                if (month == 2) {
                    if (day <= 28) {
                        validDate = true;
                    } else if (day == 29) {
                        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                            validDate = true;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return validTime && validDate && validFormat;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Datetime
                && this.value.equals(((Datetime) other).value));
    }

    @Override
    public String toString() {
        return value;
    }
}
