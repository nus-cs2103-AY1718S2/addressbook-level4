package seedu.address.model.building;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Room in National University of Singapore.
 * Guarantees: immutable; is valid as declared in {@link #isValidRoom(String)}
 */
public class Room {

    public static final String MESSAGE_ROOM_CONSTRAINTS =
            "Room names should only contain alphanumeric characters and it should not be blank";
    public static final String ROOM_VALIDATION_REGEX = "\\p{Alnum}+";

    /**
     * Represents status of the {@code Room}
     */
    private enum RoomStatus {
        VACANT, OCCUPIED
    }

    public final String roomName;

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
    public static boolean isValidRoom(String test) {
        return test.matches(ROOM_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return roomName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Room // instanceof handles nulls
                && this.roomName.equals(((Room) other).roomName)); // state check
    }

    @Override
    public int hashCode() {
        return roomName.hashCode();
    }

}
