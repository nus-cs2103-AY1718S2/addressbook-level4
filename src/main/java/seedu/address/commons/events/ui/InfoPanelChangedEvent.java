package seedu.address.commons.events.ui;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Indicates a change in Info Panel (Used for automated testing purpose)
 */
public class InfoPanelChangedEvent extends Event {
    public static final EventType<InfoPanelChangedEvent> INFO_PANEL_EVENT =
            new EventType<>("InfoPanelChangedEvent");

    public InfoPanelChangedEvent() {
        this(INFO_PANEL_EVENT);
    }

    public InfoPanelChangedEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
