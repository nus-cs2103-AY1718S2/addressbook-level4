package seedu.address.model.building;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.building.exceptions.CorruptedVenueInformationException;

/**
 * Represents a Building in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidBuilding(String)}
 */
public class Building {

    public static final String MESSAGE_BUILDING_CONSTRAINTS =
            "Building names should only contain alphanumeric characters and it should not be blank";

    public static final String BUILDING_VALIDATION_REGEX = "\\p{Alnum}+";

    /**
     * Represents an array of Buildings in National University of Singapore
     */
    public static final String[] NUS_BUILDINGS = {
        "AS1", "AS2", "AS3", "AS4", "AS5", "AS6", "AS7", "AS8", "COM1", "COM2", "I3", "BIZ1", "BIZ2",
        "SDE", "S1", "S1A", "S2", "S3", "S4", "S4A", "S5", "S8", "S11", "S12", "S13", "S14", "S16",
        "S17", "E1", "E2", "E2A", "E3", "E3A", "E4", "E4A", "E5", "EA", "ERC", "UTSRC", "LT"
    };

    private static final Logger logger = LogsCenter.getLogger(Building.class);

    private static HashMap<String, ArrayList<String>> nusBuildingsAndRooms = null;

    private final String buildingName;

    private HashMap<String, ArrayList<String>> buildingsAndRooms;

    /**
     * Uses a private {@code Building} constructor for Jackson JSON API to instantiate an object
     */
    private Building() {
        buildingName = "";
    }

    /**
     * Constructs a {@code Building}.
     *
     * @param buildingName A valid building name.
     */
    public Building(String buildingName) {
        requireNonNull(buildingName);
        checkArgument(isValidBuilding(buildingName), MESSAGE_BUILDING_CONSTRAINTS);
        this.buildingName = buildingName;
    }

    /**
     * Returns true if a given string is a valid building name.
     */
    public static boolean isValidBuilding(String test) {
        return test.matches(BUILDING_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid building name.
     */
    public static boolean isValidBuilding(Building test) {
        for (String building : NUS_BUILDINGS) {
            if (building.equals(test.buildingName)) {
                return true;
            }
        }
        return false;
    }

    public static HashMap<String, ArrayList<String>> getNusBuildingsAndRooms() {
        return nusBuildingsAndRooms;
    }

    public static void setNusBuildingsAndRooms(HashMap<String, ArrayList<String>> nusBuildingsAndRooms) {
        Building.nusBuildingsAndRooms = nusBuildingsAndRooms;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public HashMap<String, ArrayList<String>> getBuildingsAndRooms() {
        return buildingsAndRooms;
    }

    public void setBuildingsAndRooms(HashMap<String, ArrayList<String>> buildingsAndRooms) {
        this.buildingsAndRooms = buildingsAndRooms;
    }

    /**
     * Retrieves weekday schedule of all {@code Room}s in the {@code Building} in an ArrayList of ArrayList
     *
     * @throws CorruptedVenueInformationException if the room schedule format is not as expected.
     */
    public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule() throws CorruptedVenueInformationException {
        ArrayList<ArrayList<String>> allRoomsSchedule = new ArrayList<>();
        ArrayList<String> allRoomsInBuilding = retrieveAllRoomsInBuilding();
        for (String roomName : allRoomsInBuilding) {
            Room room = new Room(roomName);
            ArrayList<String> weekDayRoomSchedule = room.retrieveWeekDaySchedule();
            allRoomsSchedule.add(weekDayRoomSchedule);
        }
        return allRoomsSchedule;
    }

    /**
     * Retrieves all {@code Room}s in the {@code Building} in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the NUS Buildings and Rooms format is not as expected.
     */
    public ArrayList<String> retrieveAllRoomsInBuilding() throws CorruptedVenueInformationException {
        checkArgument(isValidBuilding(this));
        if (nusBuildingsAndRooms == null) {
            logger.warning("NUS buildings and rooms is null, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        if (nusBuildingsAndRooms.get(buildingName) == null) {
            logger.warning("NUS buildings and rooms has some null data, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        return nusBuildingsAndRooms.get(buildingName);
    }

    @Override
    public String toString() {
        return buildingName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Building // instanceof handles nulls
                && buildingName.equals(((Building) other).buildingName)); // state check
    }

    @Override
    public int hashCode() {
        return buildingName.hashCode();
    }

}
