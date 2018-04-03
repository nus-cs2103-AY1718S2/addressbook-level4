package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.event.CalendarEvent;

/**
 * The Centre Panel of the App that can switch between Person Panel and Calendar Panel
 */
public class CentrePanel extends UiPart<Region> {

    private static final String FXML = "CentrePanel.fxml";

    private CalendarPanel calendarPanel;
    private PersonPanel personPanel;
    private ObservableList<CalendarEvent> calendarEvents;

    @FXML
    private StackPane centrePlaceholder;

    public CentrePanel(ObservableList<CalendarEvent> calendarEvents) {
        super(FXML);

        this.calendarEvents = calendarEvents;

        displayPersonPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays the Person Panel.
     */
    public void displayPersonPanel() {
        personPanel = new PersonPanel();
        centrePlaceholder.getChildren().add(personPanel.getRoot());
    }

    /**
     * Provides a method to access PersonPanel's method.
     */
    public void freeResources() {
        personPanel.freeResources();
    }

    /**
     * Displays the Calendar Panel.
     */
    public void displayCalendarPanel() {
        calendarPanel = new CalendarPanel(calendarEvents);
        centrePlaceholder.getChildren().clear();
        centrePlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    @Subscribe
    private void handleDisplayCalendarRequestEvent(DisplayCalendarRequestEvent event) {
        calendarEvents = event.getCalendarEvents();
        displayCalendarPanel();
        calendarPanel.handleDisplayCalendarRequestEvent(event);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        displayPersonPanel();
        personPanel.handlePersonPanelSelectionChangedEvent(event);
    }
}
