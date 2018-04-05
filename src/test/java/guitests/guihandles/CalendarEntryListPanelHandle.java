package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.event.CalendarEntry;
import seedu.address.ui.CalendarEntryCard;

/**
 * Provides a handle for {@code CalendarEntryListPanel} containing the list of {@code CalendarEntryCard}.
 */
public class CalendarEntryListPanelHandle extends NodeHandle<ListView<CalendarEntryCard>> {
    public static final String CALENDAR_ENTRY_LIST_VIEW_ID = "#calendarEntryCardListView";

    public CalendarEntryListPanelHandle(ListView<CalendarEntryCard> rootNode) {
        super(rootNode);
    }

    /**
     * Navigates the listview to display and select the calendar entry.
     */
    public void navigateToCard(CalendarEntry calendarEntry) {
        List<CalendarEntryCard> entryCards = getRootNode().getItems();
        Optional<CalendarEntryCard> matchingCard = entryCards.stream()
                .filter(entryCard -> entryCard.calendarEntry.equals(calendarEntry)).findFirst();

        if (!matchingCard.isPresent()) {
            throw  new IllegalArgumentException("Calendar Entry does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });

        guiRobot.pauseForHuman();
    }

    /**
     * Returns the calendar entry card handle of an calendar entry associated with the {@code index} in the list.
     */
    public CalendarEntryCardHandle getCalendarEntryCardHandle (int index) {
        return getCalendarEntryCardHandle(getRootNode().getItems().get(index).calendarEntry);
    }

    /**
     * Returns the calendar entry card handle of an calendar entry
     * associated with the {@code calendarEntry} in the list.
     */
    private CalendarEntryCardHandle getCalendarEntryCardHandle(CalendarEntry calendarEntry) {
        Optional<CalendarEntryCardHandle> handle = getRootNode().getItems().stream()
                .filter(entryCard -> entryCard.calendarEntry.equals(calendarEntry))
                .map(orderCard -> new CalendarEntryCardHandle(orderCard.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Calendar Entry does not exist."));
    }

}
