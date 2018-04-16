package seedu.address.testutil;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * A utility class containing a list of {@code EventBook} objects to be used in tests.
 */
public class TypicalEventBook {

    private TypicalEventBook() {} // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical persons.
     */
    public static EventBook getTypicalEventBook() {
        EventBook eb = new EventBook();
        for (ReadOnlyEvent event : TypicalEvents.getTypicalEvents()) {
            try {
                eb.addEvent(event);
            } catch (DuplicateEventException | CommandException e) {
                throw new AssertionError("not possible");
            }
        }
        return eb;
    }

}
