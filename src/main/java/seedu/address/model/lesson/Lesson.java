package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.student.Student;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson {


    private final Student student;
    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(Student student, Time startTime, Time endTime) {
        requireAllNonNull(student, startTime, endTime);
        this.student = student;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Every field must be present and not null.
     */

    public Student getStudent() {
        return student;
    }

    public Time getStartTime() {
        return startTime;
    }
    public Time getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return otherLesson.getStudent().equals(this.getStudent())
                && otherLesson.getStartTime().equals(this.getEndTime())
                && otherLesson.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(student, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getStudent().getName())
                .append(" programminglanguage: ")
                .append(getStudent().getProgrammingLanguage())
                .append(" Time: ")
                .append(getStartTime() + " - " + getEndTime());
        return builder.toString();
    }

}
