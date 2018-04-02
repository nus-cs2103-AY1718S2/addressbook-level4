package seedu.address.model.person.timetable;

import seedu.address.commons.util.timetable.Lesson;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a week in the timetable
 */
public class TimetableWeek {

    public static final int NUM_OF_DAYS = 5;
    public static final int MONDAY_INDEX = 0;
    public static final int TUESDAY_INDEX = 1;
    public static final int WEDNESDAY_INDEX = 2;
    public static final int THURSDAY_INDEX = 3;
    public static final int FRIDAY_INDEX = 4;

    public static final String MONDAY_IDENTIFIER = "Monday";
    public static final String TUESDAY_IDENTIFIER = "Tuesday";
    public static final String WEDNESDAY_IDENTIFIER = "Wednesday";
    public static final String THURSDAY_IDENTIFIER = "Thursday";
    public static final String FRIDAY_IDENTIFIER = "Friday";

    private TimetableDay[] timetableDays;

    public TimetableWeek() {
        timetableDays = new TimetableDay[NUM_OF_DAYS];
        for (int i = 0; i < NUM_OF_DAYS; i++) {
            timetableDays[i] = new TimetableDay();
        }
    }

    /**
     * Add lesson to its respective day
     * @param lesson Lesson
     */
    public void addLessonToWeek(Lesson lesson) throws ParseException {
        switch (lesson.getDay()) {
        case MONDAY_IDENTIFIER:
            timetableDays[MONDAY_INDEX].addLessontoDay(lesson);
            break;

        case TUESDAY_IDENTIFIER:
            timetableDays[TUESDAY_INDEX].addLessontoDay(lesson);
            break;

        case WEDNESDAY_IDENTIFIER:
            timetableDays[WEDNESDAY_INDEX].addLessontoDay(lesson);
            break;

        case THURSDAY_IDENTIFIER:
            timetableDays[THURSDAY_INDEX].addLessontoDay(lesson);
            break;

        case FRIDAY_IDENTIFIER:
            timetableDays[FRIDAY_INDEX].addLessontoDay(lesson);
            break;

        default:
            throw new ParseException("Invalid day type");
        }
    }

}
