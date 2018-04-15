package guitests.guihandles;
//@@author SuxianAlicia

import java.time.LocalDate;

import javafx.scene.layout.StackPane;

/**
 * A handler for {@code CenterPanel} of the Ui.
 */
public class CenterPanelHandle extends NodeHandle<StackPane> {

    public static final String CENTER_PANEL_ID = "#centerPlaceholder";

    private PersonPanelHandle personPanelHandle;
    private CalendarPanelHandle calendarPanelHandle;

    public CenterPanelHandle(StackPane rootNode) {
        super(rootNode);
    }

    /**
     * Sets Up {@code CalendarPanelHandle}.
     * This method is only invoked only after {@code CenterPanel} adds {@code CalendarPanel} to its root node.
     */
    public void setUpCalendarPanelHandle() {
        calendarPanelHandle = new CalendarPanelHandle(getChildNode(CalendarPanelHandle.CALENDAR_PANEL_ID));
    }

    public CalendarPanelHandle getCalendarPanelHandle() {
        setUpCalendarPanelHandle();
        return calendarPanelHandle;
    }

    /**
     * Sets Up {@code PersonPanelHandle}.
     * This method is only invoked only after {@code CenterPanel} adds {@code PersonPanel} to its root node.
     */
    public void setUpPersonPanelHandle() {
        personPanelHandle = new PersonPanelHandle(getChildNode(PersonPanelHandle.PERSON_PANEL_ID));
    }

    public PersonPanelHandle getPersonPanelHandle() {
        setUpPersonPanelHandle();
        return personPanelHandle;
    }

    // CalendarPanelHandle-related operations

    /**
     * Returns the current view of the calendar.
     */
    public String getCalendarCurrentView() {
        return calendarPanelHandle.getCurrentView();
    }

    /**
     * Returns the current date displayed in the calendar.
     */
    public LocalDate getCalendarCurrentDate() {
        return calendarPanelHandle.getCurrentDate();
    }

    /**
     * Returns the date set as today in the calendar.
     */
    public LocalDate getCalendarTodayDate() {
        return calendarPanelHandle.getTodayDate();
    }

    // PersonPanelHandle-related operations

}
