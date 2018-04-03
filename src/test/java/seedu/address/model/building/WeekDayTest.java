package seedu.address.model.building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.model.building.exceptions.InvalidWeekDayScheduleException;
import seedu.address.testutil.WeekDayBuilder;

//@@author Caijun7
public class WeekDayTest {

    private WeekDay weekDay = new WeekDay();
    private final WeekDay validWeekDay = new WeekDayBuilder().build();
    private final WeekDay standardWeekDay =
            new WeekDayBuilder().withWeekDay("Monday").withRoomName("COM2-0108").build();

    @Test
    public void retrieveWeekDaySchedule_nullWeekDaySchedule_throwsCorruptedVenueInformationException() {
        weekDay.setWeekDaySchedule(null);
        assertThrows(CorruptedVenueInformationException.class, () -> weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_invalidWeekDayScheduleFormat_throwsCorruptedVenueInformationException() {
        HashMap<String, String> invalidWeekDaySchedule = new HashMap<>();
        invalidWeekDaySchedule.put("800", "vacant");
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(CorruptedVenueInformationException.class, () -> weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_invalidWeekDayScheduleData_throwsCorruptedVenueInformationException() {
        HashMap<String, String> invalidWeekDaySchedule = createInvalidWeekDaySchedule();
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(CorruptedVenueInformationException.class, () -> weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_validWeekDaySchedule_success() throws Exception {
        HashMap<String, String> validWeekDaySchedule = validWeekDay.getWeekDaySchedule();
        weekDay.setWeekDaySchedule(validWeekDaySchedule);

        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, weekDay.retrieveWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_nullWeekDaySchedule_throwsInvalidWeekDayScheduleException() {
        weekDay.setWeekDaySchedule(null);
        assertThrows(InvalidWeekDayScheduleException.class, () -> weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_invalidWeekDayScheduleFormat_throwsInvalidWeekDayScheduleException() {
        HashMap<String, String> invalidWeekDaySchedule = new HashMap<>();
        invalidWeekDaySchedule.put("800", "vacant");
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(InvalidWeekDayScheduleException.class, () -> weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_invalidWeekDayScheduleData_throwsInvalidWeekDayScheduleException() {
        HashMap<String, String> invalidWeekDaySchedule = createInvalidWeekDaySchedule();
        weekDay.setWeekDaySchedule(invalidWeekDaySchedule);
        assertThrows(InvalidWeekDayScheduleException.class, () -> weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void isValidWeekDaySchedule_validWeekDaySchedule_success() throws Exception {
        HashMap<String, String> validWeekDaySchedule = validWeekDay.getWeekDaySchedule();
        weekDay.setWeekDaySchedule(validWeekDaySchedule);

        assertTrue(weekDay.isValidWeekDaySchedule());
    }

    @Test
    public void equals_sameValues_true() {
        WeekDay weekDayWithSameValues = new WeekDayBuilder().build();
        assertTrue(standardWeekDay.equals(weekDayWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardWeekDay.equals(standardWeekDay));
    }

    @Test
    public void equals_sameType_true() {
        WeekDay weekDay = new WeekDay();
        HashMap<String, String> testWeekDaySchedule = new HashMap<>();
        weekDay.setWeekDaySchedule(testWeekDaySchedule);
        WeekDay sameTypeWeekDay = new WeekDay();
        sameTypeWeekDay.setWeekDaySchedule(testWeekDaySchedule);
        assertTrue(weekDay.equals(sameTypeWeekDay));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardWeekDay.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardWeekDay.equals(new Week()));
    }

    /**
     * Creates an invalid {@code weekDaySchedule} HashMap with one incorrect data
     */
    private HashMap<String, String> createInvalidWeekDaySchedule() {
        HashMap<String, String> invalidWeekDaySchedule = new HashMap<>();
        invalidWeekDaySchedule.put("0800", "vacant");
        invalidWeekDaySchedule.put("0900", "vacant");
        invalidWeekDaySchedule.put("1000", "vacant");
        invalidWeekDaySchedule.put("1100", "vacant");
        invalidWeekDaySchedule.put("1200", "vacant");
        invalidWeekDaySchedule.put("1300", "vacant");
        invalidWeekDaySchedule.put("1400", "vacant");
        invalidWeekDaySchedule.put("1500", "vacant");
        invalidWeekDaySchedule.put("1600", "vacant");
        invalidWeekDaySchedule.put("1700", "vacant");
        invalidWeekDaySchedule.put("1800", "vacant");
        invalidWeekDaySchedule.put("1900", "vacant");
        invalidWeekDaySchedule.put("2000", "vacan");
        return invalidWeekDaySchedule;
    }

}
