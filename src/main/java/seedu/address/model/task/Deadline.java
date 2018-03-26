package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Represents a Task's deadline in the address book.
 * Guarantees: immutable;
 */
public class Deadline {


    public static final String MESSAGE_DEADLINE_CONSTRAINTS =
            "Deadline should be a valid date in the format dd/mm/yyyy. Tasks cannot be scheduled in the past. "
                + "And tasks can only be scheduled at most 6 months in advance.";
    public final LocalDate value;
    public final long daysBetween;
    public final String day;
    public final String month;
    public final String year;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        try {
            String[] splitDeadline = deadline.split("/");
            day = splitDeadline[0];
            month = splitDeadline[1];
            year = splitDeadline[2];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate deadlineDate = LocalDate.parse(deadline, formatter);
            this.value = deadlineDate;
            Date dateNow = new Date();
            LocalDate now = dateNow.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            daysBetween = ChronoUnit.DAYS.between(now, deadlineDate);
            int diff;

            if (deadlineDate.getYear() < now.getYear() || (deadlineDate.getYear() - now.getYear() > 1)) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
            if (deadlineDate.getYear() == now.getYear()) {
                diff = deadlineDate.getMonthValue() - now.getMonthValue();
            } else {
                diff = (12 - now.getMonthValue()) + deadlineDate.getMonthValue();
            }
            if (diff < 0 || diff > 6) {
                throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate deadlineDate = LocalDate.parse(test, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException(MESSAGE_DEADLINE_CONSTRAINTS);
        }
        return true;
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

