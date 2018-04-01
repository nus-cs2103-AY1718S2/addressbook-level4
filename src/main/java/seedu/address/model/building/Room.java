package seedu.address.model.building;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.building.exceptions.CorruptedVenueInformationException;

/**
 * Represents a Room in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Room names can take any values, and it should not be blank";

    public static final String ROOM_VALIDATION_REGEX = "[^\\s].*";

    private static final Logger logger = LogsCenter.getLogger(Room.class);

    /**
     * Represents all rooms in National University of Singapore
     */
    private static HashMap<String, Week> nusVenues = null;

    private final String roomName;

    private HashMap<String, Week> nusRooms;
    private Week week = null;

    /**
     * Uses a private {@code Room} constructor for Jackson JSON API to instantiate an object
     */
    private Room() {
        roomName = "";
    }

    /**
     * Constructs a {@code Room}.
     *
     * @param roomName A valid room name.
     */
    public Room(String roomName) {
        requireNonNull(roomName);
        checkArgument(isValidRoom(roomName), MESSAGE_ROOM_CONSTRAINTS);
        this.roomName = roomName;
    }

    /**
     * Returns true if a given string is a valid room name.
     */
    private static boolean isValidRoom(String test) {
        return test.matches(ROOM_VALIDATION_REGEX);
    }

    public static HashMap<String, Week> getNusVenues() {
        return nusVenues;
    }

    public static void setNusVenues(HashMap<String, Week> nusVenues) {
        Room.nusVenues = nusVenues;
    }

    public HashMap<String, Week> getNusRooms() {
        return nusRooms;
    }

    public void setNusRooms(HashMap<String, Week> nusRooms) {
        this.nusRooms = nusRooms;
    }

    public String getRoomName() {
        return roomName;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     *
     * @throws CorruptedVenueInformationException if the room schedule format is not as expected.
     */
    public ArrayList<String> retrieveWeekDaySchedule() throws CorruptedVenueInformationException {
        initializeWeek();
        ArrayList<String> schedules = week.retrieveWeekDaySchedule();
        schedules.add(0, roomName);
        return schedules;
    }

    /**
     * Initialize the {@code Week} schedule of the room
     *
     * @throws CorruptedVenueInformationException if the NUS Venues format is not as expected.
     */
    public void initializeWeek() throws CorruptedVenueInformationException {
        if (nusVenues == null) {
            logger.warning("NUS Venues is null, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        week = nusVenues.get(roomName);
        if (week == null) {
            logger.warning(roomName + " data is null, venueinformation.json file is corrupted.");
            throw new CorruptedVenueInformationException();
        }
        week.setRoomName(roomName);
    }

    @Override
    public String toString() {
        return roomName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && roomName.equals(((Room) other).roomName)); // state check
    }

    @Override
    public int hashCode() {
        return roomName.hashCode();
    }

}
