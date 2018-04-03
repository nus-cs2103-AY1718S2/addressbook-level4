package seedu.address.model.person.timetable;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.timetable.Lesson;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author AzuraAiR
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
    public static final String MESSAGE_INVALID_DAY = "Day is invalid";

    private TimetableDay[] timetableDays;

    public TimetableWeek() {
        timetableDays = new TimetableDay[NUM_OF_DAYS];
        for (int i = 0; i < NUM_OF_DAYS; i++) {
            timetableDays[i] = new TimetableDay();
        }
    }

    /**
     * Add lesson to its respective day
     * @param lesson Lesson to be added
     * @throws ParseException when Day is invalid
     */
    public void addLessonToWeek(Lesson lesson) throws ParseException {
        try {
            switch (lesson.getDay()) {
            case MONDAY_IDENTIFIER:
                timetableDays[MONDAY_INDEX].addLessonToDay(lesson);
                break;

            case TUESDAY_IDENTIFIER:
                timetableDays[TUESDAY_INDEX].addLessonToDay(lesson);
                break;

            case WEDNESDAY_IDENTIFIER:
                timetableDays[WEDNESDAY_INDEX].addLessonToDay(lesson);
                break;

            case THURSDAY_IDENTIFIER:
                timetableDays[THURSDAY_INDEX].addLessonToDay(lesson);
                break;

            case FRIDAY_IDENTIFIER:
                timetableDays[FRIDAY_INDEX].addLessonToDay(lesson);
                break;

            default:
                throw new ParseException(MESSAGE_INVALID_DAY);
            }
        } catch (IllegalValueException ie) {
            throw new ParseException(ie.getMessage());
        }
    }

    /**
     * Returns the lesson at the specified slot, null if slot is empty
     * @param day
     * @param timeSlot
     * @return the Lesson at the specified day and timeslot
     * @throws IllegalValueException when day, timeslot are invalid values
     */
    public Lesson getLessonFromSlot(String day, int timeSlot) throws IllegalValueException {
        switch (day) {
        case MONDAY_IDENTIFIER:
            return timetableDays[MONDAY_INDEX].getLessonFromSlot(timeSlot);

        case TUESDAY_IDENTIFIER:
            return timetableDays[TUESDAY_INDEX].getLessonFromSlot(timeSlot);

        case WEDNESDAY_IDENTIFIER:
            return timetableDays[WEDNESDAY_INDEX].getLessonFromSlot(timeSlot);

        case THURSDAY_IDENTIFIER:
            return timetableDays[THURSDAY_INDEX].getLessonFromSlot(timeSlot);

        case FRIDAY_IDENTIFIER:
            return timetableDays[FRIDAY_INDEX].getLessonFromSlot(timeSlot);

        default:
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    /**
     * Returns the Time Table for the week
     * @return ArrayList with the  Time Table
     */
    public ArrayList<ArrayList<String>> getWeeklyTimeTable() {
        ArrayList<ArrayList<String>> timetable = new ArrayList<>();
        for (int i = 0; i < timetableDays.length; i++) {
            TimetableDay t = timetableDays[i];
            ArrayList<String> dailyTimeTable = t.getDailyTimeTable();
            switch (i) {
            case 0:
                dailyTimeTable.add(0, MONDAY_IDENTIFIER);
                break;
            case 1:
                dailyTimeTable.add(0, TUESDAY_IDENTIFIER);
                break;
            case 2:
                dailyTimeTable.add(0, WEDNESDAY_IDENTIFIER);
                break;
            case 3:
                dailyTimeTable.add(0, THURSDAY_IDENTIFIER);
                break;
            case 4:
                dailyTimeTable.add(0, FRIDAY_IDENTIFIER);
                break;
            default:
                break;
            }
            timetable.add(dailyTimeTable);
        }
        return timetable;
    }
}
