package seedu.address.testutil;

import seedu.address.model.Schedule;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;

//@@author demitycho
/**
 * A utility class to help with building Schedule objects.
 * Example usage: <br>
 *     {@code Schedule sd = new ScheduleBuilder().withLesson.build();}
 */
public class ScheduleBuilder {

    private Schedule schedule;

    public ScheduleBuilder() {
        schedule = new Schedule();
    }

    public ScheduleBuilder(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Adds a new {@code Student} to the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withLesson(Lesson lesson) {
        try {
            schedule.addLesson(lesson);
        } catch (DuplicateLessonException dpe) {
            throw new IllegalArgumentException("lesson is expected to be unique.");
        } catch (InvalidLessonTimeSlotException iltse) {
            throw new IllegalArgumentException("Lesson is expected to be non-clashing");
        }
        return this;
    }

    public Schedule build() {
        return schedule;
    }
}
