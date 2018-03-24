package seedu.address.model.building;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Room in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Room names can take any values, and it should not be blank";

    public static final String ROOM_VALIDATION_REGEX = "[^\\s].*";

    /**
     * Represents all rooms in National University of Singapore
     */
    private static HashMap<String, Week> nusVenues;

    private final String roomName;

    private HashMap<String, Week> nusRooms;
    private Week week;

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

    public HashMap<String, Week> getNusRooms() {
        return nusRooms;
    }

    public void setNusRooms(HashMap<String, Week> nusRooms) {
        this.nusRooms = nusRooms;
    }

    public String getRoomName() {
        return roomName;
    }

    public static HashMap<String, Week> getNusVenues() {
        return nusVenues;
    }

    public static void setNusVenues(HashMap<String, Week> nusVenues) {
        Room.nusVenues = nusVenues;
    }

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     */
    public ArrayList<String> getWeekDaySchedule() {
        initializeWeek();
        return week.getWeekDaySchedule();
    }

    public void initializeWeek() {
        week = nusVenues.get(roomName);
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
