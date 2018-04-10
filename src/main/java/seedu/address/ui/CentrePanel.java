package seedu.address.ui;
//@@author SuxianAlicia
import com.calendarfx.model.Calendar;
import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ResetPersonPanelEvent;
import seedu.address.model.event.CalendarEntry;


/**
 * The Centre Panel of the App that can switch between Person Panel and Calendar Panel.
 * Centre Panel subscribes to Events meant for Person Panel and Calendar Panel
 * in order to handle the switching between the displays.
 */
public class CentrePanel extends UiPart<Region> {

    private static final String FXML = "CentrePanel.fxml";

    private CalendarPanel calendarPanel;

    private PersonPanel personPanel;
    private ObservableList<CalendarEntry> calendarEvents;

    @FXML
    private StackPane centrePlaceholder;

    public CentrePanel(Calendar calendar) {
        super(FXML);

        personPanel = new PersonPanel();
        calendarPanel = new CalendarPanel(calendar);
        this.calendarEvents = calendarEvents;

        displayPersonPanel();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays the Person Panel.
     */
    public void displayPersonPanel() {
        centrePlaceholder.getChildren().clear();
        centrePlaceholder.getChildren().add(personPanel.getRoot());
    }

    /**
     * Displays the Calendar Panel.
     */
    public void displayCalendarPanel() {
        centrePlaceholder.getChildren().clear();
        centrePlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    @Subscribe
    private void handleDisplayCalendarRequestEvent(DisplayCalendarRequestEvent event) {
        calendarPanel.handleDisplayCalendarRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    public void handleChangeCalendarPageRequestEvent(ChangeCalendarPageRequestEvent event) {
        calendarPanel.handleChangeCalendarPageRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        personPanel.handlePersonPanelSelectionChangedEvent(event);
        displayPersonPanel();
    }

    @Subscribe
    private void handleResetPersonPanelEvent(ResetPersonPanelEvent event) {
        if (centrePlaceholder.getChildren().contains(personPanel.getRoot())) {
            personPanel = new PersonPanel();
            displayPersonPanel();
        }
    }
}
