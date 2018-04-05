package seedu.address.model.person;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.timetable.Lesson;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.person.timetable.TimetableDay;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TimetableBuilder;

//@author AzuraAiR
public class TimetableTest {

    private static final String INVALID_WEEK = "Third Week";
    private static final String INVALID_DAY = "Everyday";
    private static final int INVALID_TIMESLOT = 25;

    private static final String VALID_WEEK = "Odd Week";
    private static final String VALID_DAY = "Wednesday";
    private static final int VALID_TIMESLOT = 11;

    private static final String EMPTY_URL = " ";
    private static final String VALID_URL = "http://modsn.us/kqUAK";
    private static final String INVALID_URL = "http://google.com";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Timetable(null));
    }

    @Test
    public void constructor_invalidUrl_throwsIllegalArgumentException() {
        String invalidUrl = "http://google.com";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Timetable(invalidUrl));
    }

    @Test
    public void constructor_emptyUrl_returnsEmptyTimetable() {
        assertEquals(new Timetable(EMPTY_URL), new Timetable (""));
    }

    @Test
    public void isValidTimetable() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Timetable.isValidUrl(null));

        // invalid urls
        assertFalse(Timetable.isValidUrl("  ")); // empty string
        assertFalse(Timetable.isValidUrl(INVALID_URL)); // not NUSMods
        assertFalse(Timetable.isValidUrl("https://nusmods.com/timetable/sem-2/share?CS2103T=TUT:T6"));
        // long url

        // valid addresses
        assertTrue(Address.isValidAddress(VALID_URL));
    }

    @Test
    public void isValidTimeslot() {
        // out of range timeslots
        Assert.assertThrows(IllegalValueException.class, () -> TimetableDay.parseStartEndTime("2400"));
        Assert.assertThrows(IllegalValueException.class, () -> TimetableDay.parseStartEndTime("-0100"));

        // valid timeslots
        try {
            assertEquals(0, TimetableDay.parseStartEndTime("0000"));
            assertEquals(23, TimetableDay.parseStartEndTime("2300"));
            assertEquals(12, TimetableDay.parseStartEndTime("1240"));
        } catch (IllegalValueException ie) {
            // Should never go here
        }
    }

    @Test
    public void getTimeslot_validValues() {
        Timetable timetable = new TimetableBuilder().getDummy(0);
        try {
            assertEquals("CS2103T", timetable.getLessonFromSlot(VALID_WEEK,
                VALID_DAY, VALID_TIMESLOT).getModuleCode());
        } catch (IllegalValueException ie) {
            // should never go here
        }
    }

    @Test
    public void getTimeslot_invalidValues_throwsIllegalValueException() {
        Timetable timetable = new TimetableBuilder().getDummy(0);

        Assert.assertThrows(IllegalValueException.class, () -> timetable.getLessonFromSlot(INVALID_WEEK,
                VALID_DAY, VALID_TIMESLOT));
        Assert.assertThrows(IllegalValueException.class, () -> timetable.getLessonFromSlot(VALID_WEEK,
                INVALID_DAY, VALID_TIMESLOT));
        Assert.assertThrows(IllegalValueException.class, () -> timetable.getLessonFromSlot(INVALID_WEEK,
                VALID_DAY, INVALID_TIMESLOT));
    }

    @Test
    public void addTimeslot_validValues() {
        Timetable timetable = new Timetable(EMPTY_URL);
        Timetable timetableToExpect = new TimetableBuilder().getDummy(0);
        Lesson lessonToAdd = new Lesson("CS2103T", "T6", "Tutorial", "Every Week",
                "Wednesday", "1100", "1200");

        try {
            assertEquals(timetable, new Timetable(EMPTY_URL));
            timetable.addLessonToSlot(lessonToAdd);
            assertEquals(timetable.getLessonFromSlot(VALID_WEEK, VALID_DAY, VALID_TIMESLOT).getModuleCode(),
                    timetableToExpect.getLessonFromSlot(VALID_WEEK, VALID_DAY, VALID_TIMESLOT).getModuleCode());
        } catch (IllegalValueException ie) {
            // should never go here
        }
    }

    @Test
    public void addTimeslot_invalidValues() {
        Timetable timetable = new Timetable(EMPTY_URL);
        Lesson lessonWithInvalidWeek = new Lesson("CS2103T", "T6", "Tutorial", INVALID_WEEK,
                VALID_DAY, "1100", "1200");
        Lesson lessonWithInvalidDay = new Lesson("CS2103T", "T6", "Tutorial", VALID_WEEK,
                INVALID_DAY, "1100", "1200");
        Lesson lessonWithInvalidTime = new Lesson("CS2103T", "T6", "Tutorial", VALID_WEEK,
                VALID_DAY, "2300", "2400");

        Assert.assertThrows(IllegalValueException.class, () -> timetable.addLessonToSlot(lessonWithInvalidWeek));
        Assert.assertThrows(IllegalValueException.class, () -> timetable.addLessonToSlot(lessonWithInvalidDay));
        Assert.assertThrows(IllegalValueException.class, () -> timetable.addLessonToSlot(lessonWithInvalidTime));
    }

}

