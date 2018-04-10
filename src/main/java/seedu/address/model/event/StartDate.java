package seedu.address.model.event;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.convertStringToDate;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents Starting Date of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}
 */
public class StartDate {
    public static final String MESSAGE_START_DATE_CONSTRAINTS =
            "Start Date should be DD-MM-YYYY, and it should not be blank";

    private final String startDateString;
    private final LocalDate startDate;

    /**
     * Constructs {@code StartDate}.
     *
     * @param startDate Valid start date.
     */
    public StartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidDate(startDate), MESSAGE_START_DATE_CONSTRAINTS);
        try {
            this.startDate = convertStringToDate(startDate);
            this.startDateString = startDate;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given Start date should be valid for conversion.");
        }
    }

    public LocalDate getLocalDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return startDateString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startDate.equals(((StartDate) other).startDate)); // state check
    }

    @Override
    public int hashCode() {
        return startDate.hashCode();
    }
}
