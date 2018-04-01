package seedu.address.testutil;

import java.util.HashMap;

import seedu.address.model.building.Room;
import seedu.address.model.building.Week;

/**
 * A utility class to help with building {@code Room} objects
 */
public class RoomBuilder {

    public static final String DEFAULT_ROOMNAME = "COM2-0108";

    private static HashMap<String, Week> nusVenues = null;

    private String roomName;
    private HashMap<String, Week> nusRooms;
    private Week week = null;

    public RoomBuilder() {
        roomName = DEFAULT_ROOMNAME;
        nusRooms = createValidNusRooms();
        nusVenues = nusRooms;
        week = new WeekBuilder().build();
    }

    /**
     * Initializes the RoomBuilder with the data of {@code roomToCopy}.
     */
    public RoomBuilder(Room roomToCopy) {
        roomName = roomToCopy.getRoomName();
        nusRooms = roomToCopy.getNusRooms();
        nusVenues = Room.getNusVenues();
        week = roomToCopy.getWeek();
    }

    /**
     * Creates a valid {@code nusRooms}
     */
    private HashMap<String, Week> createValidNusRooms() {
        HashMap<String, Week> validNusRooms = new HashMap<>();
        Week validWeek = new WeekBuilder().build();
        validNusRooms.put(roomName, validWeek);
        return validNusRooms;
    }

    /**
     * Sets the {@code roomName} into a {@code Room} that we are building.
     */
    public RoomBuilder withRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    /**
     * Sets the {@code nusVenues} into a {@code Room} that we are building.
     */
    public RoomBuilder withNusVenues(HashMap<String, Week> nusVenues) {
        RoomBuilder.nusVenues = nusVenues;
        return this;
    }

    /**
     * Sets the {@code nusRooms} into a {@code Room} that we are building.
     */
    public RoomBuilder withNusRooms(HashMap<String, Week> nusRooms) {
        this.nusRooms = nusRooms;
        return this;
    }

    /**
     * Sets the {@code week} into a {@code Room} that we are building.
     */
    public RoomBuilder withWeek(Week week) {
        this.week = week;
        return this;
    }

    /**
     * Builds a {@code Room} object
     */
    public Room build() {
        Room room = new Room(roomName);
        room.setNusRooms(nusRooms);
        Room.setNusVenues(nusVenues);
        room.setWeek(week);
        return room;
    }
}
