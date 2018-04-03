package seedu.address.model.person.timetable;

import java.util.ArrayList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.timetable.Lesson;

/**
 * Represents a day in the timetable
 */
public class TimetableDay {

    public static final int NUM_OF_SLOTS = 24;
    public static final String MESSAGE_INVALID_TIMESLOT = "Time slot is invalid";

    // Cut into 24-h slots. 0000 being timetableSlots[0] and 2300 being timetableSlots[23]
    private TimetableSlot[] timetableSlots;

    public TimetableDay() {
        timetableSlots = new TimetableSlot[NUM_OF_SLOTS];
        for (int i = 0; i < NUM_OF_SLOTS; i++) {
            timetableSlots[i] = new TimetableSlot();
        }
    }

    /**
     * Adds a lesson at its respective slot
     * @param lessonToAdd lesson to be added
     * @throws IllegalValueException when slot is invalid
     */
    public void addLessonToDay(Lesson lessonToAdd) throws IllegalValueException {
        int startTimeIndex = parseStartEndTime(lessonToAdd.getStartTime());
        int endTimeIndex = parseStartEndTime(lessonToAdd.getEndTime());

        for (int i = startTimeIndex; i < endTimeIndex; i++) {
            timetableSlots[i].addLessonToSlot(lessonToAdd);
        }
    }

    /**
     * Parses the start and time parsed from NUSMods to a index to be used for array of slots
     * @param time timing from NUSMods
     * @return index for slot array
     */
    private int parseStartEndTime(String time) throws IllegalValueException {
        int value = Integer.parseInt(time) / 100;

        if (isValidTimeSlot(value)) {
            return value;
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIMESLOT);
        }
    }

    /**
     * Returns the lesson at the specified slot, null if slot is empty
     * @param timeSlot
     * @return Lesson at the specified slot, null if slot is empty
     * @throws IllegalValueException when timeslot is invalid value
     */
    public Lesson getLessonFromSlot(int timeSlot) throws IllegalValueException {
        if (timeSlot > 0 && timeSlot <= 23) {
            return timetableSlots[timeSlot].getLesson();
        } else {
            throw new IllegalValueException(MESSAGE_INVALID_TIMESLOT);
        }
    }

    /**
     * Checks if the given index is valid
     * @param index
     * @return true or false
     */
    private boolean isValidTimeSlot(int index) {
        return (index < NUM_OF_SLOTS && index >= 0);
    }

    /**
     * Returns the Time Table for the day
     * @return ArrayList with the  Time Table
     */
    public ArrayList<String> getDailyTimeTable() {
        ArrayList<String> timetable = new ArrayList<>();
        for (int i = 8; i < 22; i++) {
            TimetableSlot t = timetableSlots[i];
            timetable.add(t.toString());
        }
        return timetable;
    }
}
