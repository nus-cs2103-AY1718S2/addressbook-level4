package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateUtil.convertStringToDate;
import static seedu.address.commons.util.DateUtil.isValidDate;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents Ending Date of a {@code CalendarEntry}.
 * Guarantees: immutable; is valid as declared in {@link seedu.address.commons.util.DateUtil#isValidDate(String)}
 */
//@@author SuxianAlicia
public class EndDate {
    public static final String MESSAGE_END_DATE_CONSTRAINTS =
            "End Date should be DD-MM-YYYY, and it should not be blank";

    private final String endDateString;
    private final LocalDate endDate;

    /**
     * Constructs {@code EndDate}.
     *
     * @param endDate Valid end date.
     */
    public EndDate(String endDate) {
        requireNonNull(endDate);
        checkArgument(isValidDate(endDate), MESSAGE_END_DATE_CONSTRAINTS);
        try {
            this.endDate = convertStringToDate(endDate);
            this.endDateString = endDate;
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given End date should be valid for conversion.");
        }
    }

    public LocalDate getLocalDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return endDateString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndDate // instanceof handles nulls
                && this.endDate.equals(((EndDate) other).endDate)); // state check
    }

    @Override
    public int hashCode() {
        return endDate.hashCode();
    }
}
