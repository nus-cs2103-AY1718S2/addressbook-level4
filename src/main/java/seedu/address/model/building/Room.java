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
            "Room names should only contain alphanumeric characters and it should not be blank";
    public static final String ROOM_VALIDATION_REGEX = "\\p{Alnum}+";

    /**
     * Represents the status of the {@code Room}
     */
    public enum RoomStatus {
        VACANT, OCCUPIED
    }

    /**
     * Represents all rooms in National University of Singapore
     */
    public static HashMap<String, Week> nusVenues;

    private final String roomName;

    private HashMap<String, Week> nusRooms;
    private Week week;

    public Room() {
        roomName = null;
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

    /**
     * Retrieves the {@code Room}'s weekday schedule in an ArrayList
     * @param roomName
     */
    public ArrayList<String> getWeekDaySchedule(String roomName) {
        initializeWeek(roomName);
        return week.getWeekDaySchedule();
    }

    public void initializeWeek(String roomName) {
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
