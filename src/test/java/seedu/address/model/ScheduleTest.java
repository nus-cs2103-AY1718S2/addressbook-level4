package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalLessons.ALICE_MON_10_12;
import static seedu.address.testutil.TypicalLessons.ALICE_WED_14_16;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.lesson.Time;
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
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        schedule.resetData(null);
    }
}
