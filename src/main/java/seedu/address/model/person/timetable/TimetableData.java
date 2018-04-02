package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.util.timetable.Lesson;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents the data of the timetable
 */
public class TimetableData {

    public static final int NUM_OF_WEEKS = 2;
    public static final int EVEN_WEEK_INDEX = 0;
    public static final int ODD_WEEK_INDEX = 1;
    public static final String EVEN_WEEK_IDENTIFIER = "Even Week";
    public static final String ODD_WEEK_IDENTIFIER = "Odd Week";
    public static final String EVERY_WEEK_IDENTIFIER = "Every Week";

    private TimetableWeek[] timetableWeeks;

    // Empty constructor
    public TimetableData() {
        constructEmptyData();
    }

    // Constructor with arraylist
    public TimetableData(ArrayList<Lesson> lessonsToAdd) throws ParseException {
        constructEmptyData();
        requireNonNull(lessonsToAdd);

        // Immediate adding of lessons
        for (Lesson lessonToAdd: lessonsToAdd) {
            if (lessonToAdd.getWeekType().equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)) {
                timetableWeeks[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            } else if (lessonToAdd.getWeekType().equalsIgnoreCase(ODD_WEEK_IDENTIFIER)) {
                timetableWeeks[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            } else if (lessonToAdd.getWeekType().equalsIgnoreCase(EVERY_WEEK_IDENTIFIER)){
                timetableWeeks[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
                timetableWeeks[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            } else {
                throw new ParseException("Invalid week data");
            }
        }
    }

    /**
     * Constructs a empty structure for Timetable
     */
    private void constructEmptyData() {
        timetableWeeks = new TimetableWeek[NUM_OF_WEEKS];

        for (int i = 0; i < NUM_OF_WEEKS; i++) {
            timetableWeeks[i] = new TimetableWeek();
        }
    }

}
