package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.timetable.Lesson;

/**
 * Represents a slot in the timetable
 */
public class TimetableSlot {

    private Lesson lesson;
    private boolean isEmpty;

    // Empty Constructor
    public TimetableSlot() {
        isEmpty = true;
    }

    /**
     * Fills up the slot with the given lesson
     * @param lesson
     */
    public void addLessonToSlot(Lesson lesson) {
        requireNonNull(lesson);
        this.lesson = lesson;
        isEmpty = false;
    }

    public Lesson getLesson() {
        return lesson;
    }
}
