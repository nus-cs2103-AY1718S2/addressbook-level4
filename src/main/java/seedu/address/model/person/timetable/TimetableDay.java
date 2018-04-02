package seedu.address.model.person.timetable;

import seedu.address.commons.util.timetable.Lesson;

/**
 * Represents a day in the timetable
 */
public class TimetableDay {

    public static final int NUM_OF_SLOTS = 24;

    // Cut into 24-h slots. 0000 being timetableSlots[0] and 2300 being timetableSlots[23]
    private TimetableSlot[] timetableSlots;

    public TimetableDay() {
        timetableSlots = new TimetableSlot[NUM_OF_SLOTS];
        for (int i = 0; i < NUM_OF_SLOTS; i++) {
            timetableSlots[i] = new TimetableSlot();
        }
    }

    /**
     * Add lesson to its specified slot
     * @param lesson Lesson
     */
    public void addLessontoDay(Lesson lesson) {
        int startTimeIndex = parseStartEndTime(lesson.getStartTime());
        int endTimeIndex = parseStartEndTime(lesson.getEndTime());

        for (int i = startTimeIndex; i < endTimeIndex; i++) {
            timetableSlots[i].addLessonToSlot(lesson);
        }
    }

    /**
     * Parses the start and time parsed from NUSMods to a index to be used for array of slots
     * @param time timing from NUSMods
     * @return index for slot array
     */
    private int parseStartEndTime(String time) {
        int value = Integer.parseInt(time);

        return value / 100;
    }
}
