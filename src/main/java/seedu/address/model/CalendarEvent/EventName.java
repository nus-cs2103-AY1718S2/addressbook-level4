package seedu.address.model.CalendarEvent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author wayneong95

/**
 * Represents the name of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventName(String)}
 */

public class EventName {

    public static final String MESSAGE_EVENT_NAME_CONSTRAINTS =
            "Event name should only contain alphanumeric characters and spaces,"
                    + " and it should not be blank. Eg. Weights Training";

    public static final String EVENTNAME_VALIDATION_REGEX = "^[a-zA-Z0-9_ ]+$";

    public final String eventName;

    /**
     * Constructs a {@code EventName}.
     *
     * @param eventName A valid eventName.
     */

    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidEventName(eventName), MESSAGE_EVENT_NAME_CONSTRAINTS);
        this.eventName = eventName;
    }

    /**
     * Returns true if a given string is a valid event name.
     */
    public static boolean isValidEventName(String test) {
        return test.matches(EVENTNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return eventName;
    }
}
