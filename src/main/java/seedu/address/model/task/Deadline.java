package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents a Task's deadline in the address book.
 * Guarantees: immutable;
 */
public class Deadline {


    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be a valid date in the format dd/mm/yyyy.";
    public final LocalDate value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate deadlineDate = LocalDate.parse(deadline, formatter);
            this.value = deadlineDate;
        } catch (Exception e) {
            throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && this.value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

