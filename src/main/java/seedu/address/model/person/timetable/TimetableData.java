package seedu.address.model.person.timetable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
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
    public static final String MESSAGE_INVALID_WEEK = "Week is invalid";

    private TimetableWeek[] timetableWeeks;

    // Constructs empty data
    public TimetableData() {
        constructEmptyData();
    }

    // Constructs with ArrayList of {@Lesson}
    public TimetableData(ArrayList<Lesson> lessonsToAdd) throws ParseException {
        constructEmptyData();
        requireNonNull(lessonsToAdd);

        // Immediate adding of lessons
        for (Lesson lessonToAdd: lessonsToAdd) {
            try {
                addLessonToSlot(lessonToAdd);
            } catch (IllegalValueException ie) {
                throw new ParseException(ie.getMessage());
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

    /**
     * Returns the lesson at the specified slot, null if slot is empty
     * @param week
     * @param day
     * @param timeSlot
     * @return Lesson at the specified week, day and slot, null if slot is empty
     * @throws IllegalValueException when week, day, timeslot are invalid values
     */
    public Lesson getLessonFromSlot(String week, String day, int timeSlot) throws IllegalValueException {
        if (week.equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)) {
            return timetableWeeks[EVEN_WEEK_INDEX].getLessonFromSlot(day, timeSlot);
        } else if (week.equalsIgnoreCase(ODD_WEEK_IDENTIFIER)) {
            return timetableWeeks[ODD_WEEK_INDEX].getLessonFromSlot(day, timeSlot);
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_WEEK);
        }
    }

    /**
     * Adds a lesson at its respective week
     * @param lessonToAdd lesson to be added
     * @throws IllegalValueException when week, day, timeslot are invalid values
     */
    public void addLessonToSlot(Lesson lessonToAdd) throws IllegalValueException {

        if (lessonToAdd.getWeekType().equalsIgnoreCase(EVEN_WEEK_IDENTIFIER)) {
            timetableWeeks[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
        } else if (lessonToAdd.getWeekType().equalsIgnoreCase(ODD_WEEK_IDENTIFIER)) {
            timetableWeeks[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
        } else if (lessonToAdd.getWeekType().equalsIgnoreCase(EVERY_WEEK_IDENTIFIER)) {
            timetableWeeks[EVEN_WEEK_INDEX].addLessonToWeek(lessonToAdd);
            timetableWeeks[ODD_WEEK_INDEX].addLessonToWeek(lessonToAdd);
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_WEEK);
        }
    }

    /**
     * Returns the Time Table
     * @return ArrayList with the  Time Table
     */
    public ArrayList<ArrayList<ArrayList<String>>> getTimeTable() {
        ArrayList<ArrayList<ArrayList<String>>> timetable = new ArrayList<>();
        for (TimetableWeek t : timetableWeeks) {
            timetable.add(t.getWeeklyTimeTable());
        }
        return timetable;
    }

}
