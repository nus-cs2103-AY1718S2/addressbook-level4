package seedu.address.ui;
//@@author SuxianAlicia
import com.calendarfx.model.Calendar;
import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
import seedu.address.commons.events.ui.DisplayPersonPanelRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ResetPersonPanelRequestEvent;
import seedu.address.model.event.CalendarEntry;


/**
 * The Center Panel of the App that can switch between Person Panel and Calendar Panel.
 * Center Panel subscribes to Events meant for Person Panel and Calendar Panel
 * in order to handle the switching between the displays.
 */
public class CenterPanel extends UiPart<Region> {

    private static final String FXML = "CenterPanel.fxml";

    private CalendarPanel calendarPanel;

    private PersonPanel personPanel;
    private ObservableList<CalendarEntry> calendarEvents;

    @FXML
    private StackPane centerPlaceholder;

    public CenterPanel(Calendar calendar) {
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
        if (!centerPlaceholder.getChildren().contains(personPanel.getRoot())) {
            centerPlaceholder.getChildren().clear();
            centerPlaceholder.getChildren().add(personPanel.getRoot());
        }
    }

    /**
     * Displays the Calendar Panel.
     */
    public void displayCalendarPanel() {
        if (!centerPlaceholder.getChildren().contains(calendarPanel.getRoot())) {
            centerPlaceholder.getChildren().clear();
            centerPlaceholder.getChildren().add(calendarPanel.getRoot());
        }
    }

    @Subscribe
    private void handleChangeCalendarViewRequestEvent(ChangeCalendarViewRequestEvent event) {
        calendarPanel.handleChangeCalendarViewRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    public void handleChangeCalendarPageRequestEvent(ChangeCalendarPageRequestEvent event) {
        calendarPanel.handleChangeCalendarPageRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    private void handleChangeCalendarDateRequestEvent(ChangeCalendarDateRequestEvent event) {
        calendarPanel.handleChangeCalendarDateRequestEvent(event);
        displayCalendarPanel();
    }

    @Subscribe
    private void handleDisplayPersonPanelRequestEvent(DisplayPersonPanelRequestEvent event) {
        displayPersonPanel();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        personPanel.handlePersonPanelSelectionChangedEvent(event);
        displayPersonPanel();
    }

    @Subscribe
    private void handleResetPersonPanelRequestEvent(ResetPersonPanelRequestEvent event) {
        personPanel.handleResetPersonPanelRequestEvent(event);
        displayPersonPanel();
    }
}
