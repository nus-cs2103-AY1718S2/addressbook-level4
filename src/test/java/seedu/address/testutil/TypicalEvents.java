package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.event.CalendarEvent;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final CalendarEvent SPECTRA = new EventBuilder().withTitle("Spectra")
            .withDescription("Light and water show").withLocation("Marina Bay Sands")
            .withDatetime("01-09-2017 1900").build();
    public static final CalendarEvent DEEPAVALI = new EventBuilder().withTitle("Deepavali")
            .withDescription("Deepavali Celebrations 2017").withLocation("Little India")
            .withDatetime("12-11-2017 1900").build();
    public static final CalendarEvent HENNA = new EventBuilder().withTitle("Henna")
            .withDescription("Henna Workshop").withLocation("Orchard Gateway")
            .withDatetime("18-10-2017 1500").build();
    public static final CalendarEvent WINE = new EventBuilder().withTitle("Wine Fest")
            .withDescription("Singapore Wine Fiesta 2017").withLocation("Clifford Square")
            .withDatetime("26-10-2017 1500").build();

    // Manually added
    public static final CalendarEvent NETWORK = new EventBuilder().withTitle("Network Talk")
            .withDescription("Networking meeting session").withLocation("IMDA").withDatetime("13-05-2017 1300").build();
    public static final CalendarEvent SECURITY = new EventBuilder().withTitle("Security Talk")
            .withDescription("Security meeting session").withLocation("CSIT").withDatetime("26-10-2017 1300").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook eb = new EventBook();
        for (CalendarEvent event : getTypicalEvents()) {
            try {
                eb.addEvent(event);
            } catch (CommandException e) {
                assert false : "not possible";
            }
        }
        return eb;
    }

    public static List<CalendarEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(SPECTRA, DEEPAVALI, HENNA, WINE));
    }
}
