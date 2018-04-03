package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.timetable.Lesson;
import seedu.address.model.person.timetable.Timetable;

/**
 * A utility class to help build dummy timetables for Persons
 * This is to prevent the tests from trying to retrieve information from NUSMods API too many times
 */
public class TimetableBuilder {

    public static final int NUM_OF_DUMMY_TIMETABLE = 2;
    public static final String DUMMY_LINK_ONE = "http://modsn.us/aaaaa";
    public static final String DUMMY_LINK_TWO = "http://modsn.us/bbbbb";

    private Timetable[] dummyTimetables;

    public TimetableBuilder() {
        dummyTimetables = new Timetable[NUM_OF_DUMMY_TIMETABLE];
        dummyTimetables[0] = fillTimetableWithDummyValues(new Timetable(DUMMY_LINK_ONE));
        dummyTimetables[1] = fillTimetableWithDummyValues(new Timetable(DUMMY_LINK_TWO));

    }

    /**
     * Gets the dummy timetable from the array of dummy timetables
     * @param index
     * @return Timetable with hardcoded values
     */
    public Timetable getDummy(int index) {
        if (index >= 0 && index < NUM_OF_DUMMY_TIMETABLE) {
            return dummyTimetables[index];
        } else {
            return new Timetable("");   // Create empty timetable as failsafe
        }
    }

    /**
     * Helper method to fill the timetable with dummy values
     * @param timetable to be filled
     * @return dummy Timetable
     */
    private Timetable fillTimetableWithDummyValues(Timetable timetable) {

        ArrayList<Lesson> dummyLessons = new ArrayList<Lesson>();
        dummyLessons.add(new Lesson("CS2103T", "T6", "Tutorial", "Every Week",
                "Wednesday", "1100", "1200"));
        if (timetable.value.equalsIgnoreCase(DUMMY_LINK_TWO)) {   // To differentiate dummy timetable 1 and 2
            dummyLessons.add(new Lesson("CS2101", "6", "Sectional Teaching",
                    "Every Week", "Tuesday", "1000", "1200"));
            dummyLessons.add(new Lesson("CS2101", "6", "Sectional Teaching",
                    "Every Week", "Thursday", "1000", "1200"));
            dummyLessons.add(new Lesson("CS2101", "6", "Sectional Teaching",
                    "Every Week", "Friday", "1600", "1800"));
        }

        try {
            for (Lesson lessonToAdd : dummyLessons) {
                timetable.addLessonToSlot(lessonToAdd);
            }
        } catch (IllegalValueException ie) {
            return new Timetable(""); // Should never happen since dummy values are hardcoded
        }

        return timetable;
    }

}
