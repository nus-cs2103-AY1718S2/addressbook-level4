package seedu.address.model.building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.testutil.RoomBuilder;
import seedu.address.testutil.WeekBuilder;

//@@author Caijun7
public class RoomTest {

    private Room room = new Room("COM2-0108");
    private final Room validRoom = new RoomBuilder().build();
    private final Room standardRoom = new RoomBuilder().withRoomName("COM2-0108").build();

    @Test
    public void retrieveWeekDaySchedule_nullNusVenues_throwsCorruptedVenueInformationException() {
        Room.setNusVenues(null);
        assertThrows(CorruptedVenueInformationException.class, () -> room.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_nonNullInvalidNusVenues_throwsCorruptedVenueInformationException() {
        HashMap<String, Week> invalidNusVenues = new HashMap<>();
        invalidNusVenues.put("COM2-0108", null);
        Room.setNusVenues(invalidNusVenues);
        assertThrows(CorruptedVenueInformationException.class, () -> room.retrieveWeekDaySchedule());
    }

    @Test
    public void retrieveWeekDaySchedule_validNusVenues_success() throws Exception {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(room.getRoomName());
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedList.add("vacant");
        }
        assertEquals(expectedList, validRoom.retrieveWeekDaySchedule());
    }

    @Test
    public void initializeWeek_nullNusVenues_throwsCorruptedVenueInformationException() {
        Room.setNusVenues(null);
        assertThrows(CorruptedVenueInformationException.class, () -> room.initializeWeek());
    }

    @Test
    public void initializeWeek_nonNullInvalidNusVenues_throwsCorruptedVenueInformationException() {
        HashMap<String, Week> invalidNusVenues = new HashMap<>();
        invalidNusVenues.put("COM2-0108", null);
        Room.setNusVenues(invalidNusVenues);
        assertThrows(CorruptedVenueInformationException.class, () -> room.initializeWeek());
    }

    @Test
    public void initializeWeek_validNusVenues_success() throws Exception {
        validRoom.initializeWeek();

        Week expectedWeek = new WeekBuilder().build();
        assertEquals(expectedWeek, validRoom.getWeek());
    }

    @Test
    public void equals_sameValues_true() {
        Room roomWithSameValues = new RoomBuilder(standardRoom).build();
        assertTrue(standardRoom.equals(roomWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardRoom.equals(standardRoom));
    }

    @Test
    public void equals_sameType_true() {
        Room room = new Room("COM2-0108");
        Room sameTypeRoom = new Room("COM2-0108");
        assertTrue(room.equals(sameTypeRoom));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardRoom.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardRoom.equals(new Week()));
    }

}
