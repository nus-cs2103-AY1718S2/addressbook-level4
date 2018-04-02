package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.util.timetable.Lesson;

/**
 * Represents the data of the timetable
 */
public class TimetableData {

    public static final int EVEN_WEEK_INDEX = 0;
    public static final int ODD_WEEK_INDEX = 1;
    public static final String EVEN_WEEK_IDENTIFIER = "Even Week";
    public static final String ODD_WEEK_IDENTIFIER = "Odd Week";

    private TimetableWeek[] evenOddWeek;

    // Empty constructor
    public TimetableData() {
        constructEmptyData();
    }

    // Constructor with arraylist
    public TimetableData(ArrayList<Lesson> lessonsToAdd) {
        constructEmptyData();
        requireNonNull(lessonsToAdd);

        // Immediate adding of lessons
        for (Lesson lessonToAdd: lessonsToAdd) {
            if (lessonToAdd.getWeekType().equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)) {
                evenOddWeek[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            } else if (lessonToAdd.getWeekType().equalsIgnoreCase(ODD_WEEK_IDENTIFIER)) {
                evenOddWeek[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            }
        }
    }

    /**
     * Constructs a empty structure for Timetable
     */
    private void constructEmptyData() {
        evenOddWeek = new TimetableWeek[2];
        for (TimetableWeek timetableWeek: evenOddWeek) {
            timetableWeek = new TimetableWeek();
        }
    }

}
