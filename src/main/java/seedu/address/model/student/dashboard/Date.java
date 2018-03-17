package seedu.address.model.student.dashboard;

import java.time.LocalDateTime;

/**
 * Represents a date in a Student's Dashboard
 * Guarantees: immutable.
 */
public class Date {

    private LocalDateTime value;

    public Date(LocalDateTime value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Date: " + value.getDayOfMonth() + " " + value.getMonth() + " " + value.getYear()
                + " Time: " + value.getHour() + ":" + value.getMinute();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Date
                && obj.equals(this));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
