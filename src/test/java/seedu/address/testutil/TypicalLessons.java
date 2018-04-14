package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

//@@author demitycho
/**
 * A utility class containing a list of {@code Milestone} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson ALICE_MON_10_12 = new LessonBuilder().withKey("c5daab").withDay("mon")
            .withStartTime("10:00").withEndTime("12:00").build();
    public static final Lesson ALICE_WED_14_16 = new LessonBuilder().withKey("c5daab").withDay("wed")
            .withStartTime("14:00").withEndTime("16:00").build();
    public static final Lesson CARL_THU_11_13 = new LessonBuilder().withKey("8e90ba").withDay("thu")
            .withStartTime("11:00").withEndTime("13:00").build();
    public static final Lesson RANDOM_THU_11_13 = new LessonBuilder().withKey("c0ffee").withDay("thu")
            .withStartTime("11:00").withEndTime("13:00").build();

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(ALICE_MON_10_12, ALICE_WED_14_16, CARL_THU_11_13));
    }
}
