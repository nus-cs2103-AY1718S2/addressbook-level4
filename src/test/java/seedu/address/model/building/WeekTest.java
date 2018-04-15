package seedu.address.model.building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.model.building.exceptions.InvalidWeekScheduleException;
import seedu.address.testutil.WeekBuilder;
import seedu.address.testutil.WeekDayBuilder;

//@@author Caijun7
public class WeekTest {

    private Week week = new Week();
    private final Week validWeek = new WeekBuilder().build();
    private final Week standardWeek = new WeekBuilder().withDay(0).withRoomName("COM2-0108").build();

    @Test
    public void retrieveWeekDaySchedule_nullWeekSchedule_throwsCorruptedVenueInformationException() {
        week.setWeekSchedule(null);
        assertThrows(CorruptedVenueInformationException.class, () -> week.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_nonNullInvalidWeekDaySchedule_throwsCorruptedVenueInformationException() {
        ArrayList<WeekDay> invalidWeekSchedule = new ArrayList<>();
        invalidWeekSchedule.add(new WeekDayBuilder().build());
        week.setWeekSchedule(invalidWeekSchedule);
        assertThrows(CorruptedVenueInformationException.class, () -> week.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_validWeekDaySchedule_success() throws Exception {
        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, validWeek.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_sundayWeekDaySchedule_success() throws Exception {
        validWeek.setWeekday(Week.SUNDAY);

        ArrayList<String> expectedList = new ArrayList<>();
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, validWeek.retrieveWeekDaySchedule());
    }

    @Test
    public void isValidWeekSchedule_nullWeekSchedule_throwsInvalidWeekScheduleException() {
        week.setWeekSchedule(null);
        assertThrows(InvalidWeekScheduleException.class, () -> week.isValidWeekSchedule());
    }

    @Test
    public void isValidWeekSchedule_nonNullInvalidWeekDaySchedule_throwsInvalidWeekScheduleException() {
        ArrayList<WeekDay> invalidWeekSchedule = new ArrayList<>();
        invalidWeekSchedule.add(new WeekDayBuilder().build());
        week.setWeekSchedule(invalidWeekSchedule);
        assertThrows(InvalidWeekScheduleException.class, () -> week.isValidWeekSchedule());
    }

    @Test
    public void isValidWeekSchedule_validWeekDaySchedule_success() throws Exception {
        assertTrue(validWeek.isValidWeekSchedule());
    }

    @Test
    public void equals_sameValues_true() {
        Week weekWithSameValues = new WeekBuilder(standardWeek).build();
        assertTrue(standardWeek.equals(weekWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardWeek.equals(standardWeek));
    }

    @Test
    public void equals_sameType_true() {
        Week week = new Week();
        ArrayList<WeekDay> testWeekSchedule = new ArrayList<>();
        week.setWeekSchedule(testWeekSchedule);
        Week sameTypeWeek = new Week();
        sameTypeWeek.setWeekSchedule(testWeekSchedule);
        assertTrue(week.equals(sameTypeWeek));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardWeek.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardWeek.equals(new WeekDay()));
    }

}
