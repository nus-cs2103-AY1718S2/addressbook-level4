package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Schedule;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.exceptions.DuplicateLessonException;
import seedu.address.model.lesson.exceptions.InvalidLessonTimeSlotException;

//@@author demitycho
/**
 * A utility class containing a list of {@code Milestone} objects to be used in tests.
 */
public class TypicalLessons {

    //Variable names in terms of <Student>_<Day>_<StartTime>_<EndTime>
    public static final Lesson ALICE_MON_10_12 = new LessonBuilder().withKey("c5daab").withDay("mon")
            .withStartTime("10:00").withEndTime("12:00").build();
    public static final Lesson ALICE_WED_14_16 = new LessonBuilder().withKey("c5daab").withDay("wed")
            .withStartTime("14:00").withEndTime("16:00").build();
    public static final Lesson ALICE_WED_15_17 = new LessonBuilder().withKey("c5daab").withDay("wed")
            .withStartTime("15:00").withEndTime("17:00").build();
    public static final Lesson CARL_THU_11_13 = new LessonBuilder().withKey("8e90ba").withDay("thu")
            .withStartTime("11:00").withEndTime("13:00").build();
    public static final Lesson FIONA_SAT_15_17 = new LessonBuilder().withKey("9d2b20").withDay("sat")
            .withStartTime("15:00").withEndTime("17:00").build();
    public static final Lesson RANDOM_THU_11_13 = new LessonBuilder().withKey("c0ffee").withDay("thu")
            .withStartTime("11:00").withEndTime("13:00").build();

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(ALICE_MON_10_12, ALICE_WED_14_16, CARL_THU_11_13));
    }

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static Schedule getTypicalSchedule() {
        Schedule sch = new Schedule();
        for (Lesson lesson : getTypicalLessons()) {
            try {
                sch.addLesson(lesson);
            } catch (DuplicateLessonException e) {
                throw new AssertionError("not possible");
            } catch (InvalidLessonTimeSlotException iltse) {
                throw new AssertionError("cannot clash");
            }
        }
        return sch;
    }
}
