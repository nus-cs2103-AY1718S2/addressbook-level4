package seedu.address.model.building;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.testutil.BuildingBuilder;
import seedu.address.testutil.RoomBuilder;

//@@author Caijun7
public class BuildingTest {

    private Building building = new Building("COM2");
    private final String validBuildingName = "COM2";
    private final Building validBuilding = new BuildingBuilder().build();
    private final Building standardBuilding = new BuildingBuilder().withBuildingName("COM2").build();

    @Test
    public void isValidBuilding_validString_true() {
        assertTrue(Building.isValidBuilding(validBuildingName));
    }

    @Test
    public void isValidBuilding_invalidString_false() {
        String invalidString = "COM2!";
        assertFalse(Building.isValidBuilding(invalidString));
    }

    @Test
    public void isValidBuilding_buildingFoundInListOfNusBuildings_true() {
        assertTrue(Building.isValidBuilding(validBuilding));
    }

    @Test
    public void isValidBuilding_buildingNotFoundInListOfNusBuildings_false() {
        Building invalidBuilding = new Building("COM3");
        assertFalse(Building.isValidBuilding(invalidBuilding));
    }

    @Test
    public void retrieveAllRoomsSchedule_nullNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        Building.setNusBuildingsAndRooms(null);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsSchedule());
    }

    @Test
    public void retrieveAllRoomsSchedule_invalidNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        HashMap<String, ArrayList<String>> invalidNusBuildingsAndRooms = new HashMap<>();
        invalidNusBuildingsAndRooms.put("COM2", null);
        Building.setNusBuildingsAndRooms(invalidNusBuildingsAndRooms);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsSchedule());
    }

    @Test
    public void retrieveAllRoomsSchedule_validNusBuildingsAndRooms_success() throws Exception {
        ArrayList<ArrayList<String>> expectedList = new ArrayList<>();
        ArrayList<String> expectedSchedule = new ArrayList<>();
        Room room = new RoomBuilder().build();
        expectedSchedule.add(room.getRoomName());
        for (int i = 0; i < WeekDay.NUMBER_OF_CLASSES; i++) {
            expectedSchedule.add("vacant");
        }
        expectedList.add(expectedSchedule);
        assertEquals(expectedList, validBuilding.retrieveAllRoomsSchedule());
    }

    @Test
    public void retrieveAllRoomsInBuilding_nullNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        Building.setNusBuildingsAndRooms(null);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsInBuilding());
    }

    @Test
    public void retrieveAllRoomsInBuilding_invalidNusBuildingsAndRooms_throwsCorruptedVenueInformationException() {
        HashMap<String, ArrayList<String>> invalidNusBuildingsAndRooms = new HashMap<>();
        invalidNusBuildingsAndRooms.put("COM2", null);
        Building.setNusBuildingsAndRooms(invalidNusBuildingsAndRooms);
        assertThrows(CorruptedVenueInformationException.class, () -> building.retrieveAllRoomsInBuilding());
    }

    @Test
    public void retrieveAllRoomsInBuilding_invalidBuilding_throwsIllegalArgumentException() throws Exception {
        Building invalidBuilding = new Building("COM3");
        assertThrows(IllegalArgumentException.class, () -> invalidBuilding.retrieveAllRoomsInBuilding());
    }

    @Test
    public void retrieveAllRoomsInBuilding_validNusBuildingsAndRooms_success() throws Exception {
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add(RoomBuilder.DEFAULT_ROOMNAME);
        assertEquals(expectedList, validBuilding.retrieveAllRoomsInBuilding());
    }

    @Test
    public void equals_sameValues_true() {
        Building buildingWithSameValues = new BuildingBuilder(standardBuilding).build();
        assertTrue(standardBuilding.equals(buildingWithSameValues));
    }

    @Test
    public void equals_sameObject_true() {
        assertTrue(standardBuilding.equals(standardBuilding));
    }

    @Test
    public void equals_sameType_true() {
        Building building = new Building("COM2");
        Building sameTypeBuilding = new Building("COM2");
        assertTrue(building.equals(sameTypeBuilding));
    }

    @Test
    public void equals_nullInstance_false() {
        assertFalse(standardBuilding.equals(null));
    }

    @Test
    public void equals_differentTypes_false() {
        assertFalse(standardBuilding.equals(new Week()));
    }

}
