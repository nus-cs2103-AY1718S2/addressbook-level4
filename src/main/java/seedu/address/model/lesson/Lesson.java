package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueKey;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Lesson implements Comparable<Lesson> {

    private Student student;
    private final UniqueKey uniqueKey;
    private final Day day;
    private final Time startTime;
    private final Time endTime;

    /**
     * Every field must be present and not null.
     */
    public Lesson(UniqueKey uniqueKey, Day day, Time startTime, Time endTime) {
        requireAllNonNull(uniqueKey, day, startTime, endTime);

        this.uniqueKey = uniqueKey;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Student getStudent() {
        return student;
    }

    public UniqueKey getUniqueKey() {
        return uniqueKey;
    }

    public Day getDay() {
        return day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    /**
     * To check if a lesson will clash with another lesson on the same day
     *
     * @return true/false
     */
    public boolean clashesWith(Lesson other) {
        return this.getDay().compareTo(other.getDay()) == 0
                ? ((this.getStartTime().compareTo(other.getStartTime()) >= 0    //Same day
                && this.getStartTime().compareTo(other.getEndTime()) <= 0)
                || (this.getEndTime().compareTo(other.getStartTime()) >= 0
                && this.getEndTime().compareTo(other.getEndTime()) <= 0))
                : this.getDay().compareTo(other.getDay()) == 0; //Different day
    }

    public Lesson getLesson() {
        return this;
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
        return otherLesson.getUniqueKey().equals(this.getUniqueKey())
                && otherLesson.getDay().equals(this.getDay())
                && otherLesson.getStartTime().equals(this.getEndTime())
                && otherLesson.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(uniqueKey, day, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Day: ")
                .append(getDay().fullDayName())
                .append(" Time: ")
                .append(getStartTime() + " - " + getEndTime());
        return builder.toString();
    }

    @Override
    public int compareTo(Lesson other) {
        return this.getDay().intValue() - other.getDay().intValue() != 0
                ? this.getDay().intValue() - other.getDay().intValue()
                : this.getStartTime().compareTo(other.getStartTime());
    }
}
