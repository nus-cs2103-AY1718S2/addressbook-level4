package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalLessons.ALICE_MON_10_12;
import static seedu.address.testutil.TypicalLessons.ALICE_WED_14_16;
import static seedu.address.testutil.TypicalLessons.ALICE_WED_15_17;
import static seedu.address.testutil.TypicalLessons.RANDOM_THU_11_13;
import static seedu.address.testutil.TypicalLessons.getTypicalSchedule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.Time;
import seedu.address.model.lesson.exceptions.LessonNotFoundException;
import seedu.address.testutil.ScheduleBuilder;

//@@author
public class ScheduleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Schedule schedule = new Schedule();
    private final Schedule scheduleWithAliceLessons = new ScheduleBuilder().withLesson(ALICE_MON_10_12)
            .withLesson(ALICE_WED_14_16).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), schedule.getSchedule());
        assertEquals(new Time("23:59"), schedule.getEarliestStartTime());
        assertEquals(new Time("00:00"), schedule.getLatestEndTime());
    }

    @Test
    public void resetData_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        schedule.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlySchedule_replacesData() throws Exception {
        Schedule newData = getTypicalSchedule();
        schedule.resetData(newData);
        assertEquals(newData, schedule);
    }

    @Test
    public void resetData_withDuplicateLessons_throwsInval() throws Exception {
        // Repeat Alice lesson twice
        List<Lesson> newLessons = Arrays.asList(ALICE_MON_10_12, ALICE_MON_10_12);
        ScheduleStub newData = new ScheduleStub(newLessons);

        thrown.expect(AssertionError.class);
        schedule.resetData(newData);
    }

    @Test
    public void resetData_withClashingLessons_throwsInval() throws Exception {
        // Have clashing time slots for Alice
        List<Lesson> newLessons = Arrays.asList(ALICE_WED_14_16, ALICE_WED_15_17);
        ScheduleStub newData = new ScheduleStub(newLessons);

        thrown.expect(AssertionError.class);
        schedule.resetData(newData);
    }

    @Test
    public void deleteLesson_nonExistentLesson_scheduleUnchanged() throws Exception {
        thrown.expect(LessonNotFoundException.class);
        scheduleWithAliceLessons.removeLesson(RANDOM_THU_11_13);

        Schedule expectedSchedule = new ScheduleBuilder().withLesson(ALICE_MON_10_12)
                .withLesson(ALICE_WED_14_16).build();
        assertEquals(expectedSchedule, scheduleWithAliceLessons);
    }

    @Test
    public void getSchedule_modifySchedule_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        schedule.getSchedule().remove(0);
    }

    /**
     * A stub Schedule whose lessons can violate interface constraints.
     */
    private static class ScheduleStub implements ReadOnlySchedule {
        private final ObservableList<Lesson> lessons = FXCollections.observableArrayList();

        ScheduleStub(Collection<Lesson> lessons) {
            this.lessons.setAll(lessons);
        }

        @Override
        public ObservableList<Lesson> getSchedule() {
            return lessons;
        }

        @Override
        public Time getLatestEndTime() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public Time getEarliestStartTime() {
            fail("This method should not be called.");
            return null;
        }

    }
}
