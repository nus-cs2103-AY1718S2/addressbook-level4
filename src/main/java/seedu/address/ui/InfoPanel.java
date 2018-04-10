package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ScheduleChangedEvent;
import seedu.address.commons.events.ui.BrowserDisplayEvent;
import seedu.address.commons.events.ui.ShowMilestonesEvent;
import seedu.address.commons.events.ui.ShowScheduleEvent;
import seedu.address.commons.events.ui.ShowStudentDashboardEvent;
import seedu.address.commons.events.ui.ShowStudentNameInDashboardEvent;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.address.logic.Logic;
import seedu.address.model.AddressBook;
import seedu.address.model.Schedule;

//@@author yapni
/**
 * Panel that contains the browser panel and the dashboard panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private DashboardPanel dashboardPanel;
    private Logic logic;
    @FXML
    private StackPane browserPanelPlaceholder;

    @FXML
    private StackPane dashboardPanelPlaceholder;

    @FXML
    private StackPane calendarPlaceholder;

    public InfoPanel() {
        super(FXML);
        this.logic = logic;
        browserPanel = new BrowserPanel();
        browserPanelPlaceholder.getChildren().add(browserPanel.getRoot());

        dashboardPanel = new DashboardPanel();
        dashboardPanelPlaceholder.getChildren().add(dashboardPanel.getRoot());

        CalendarPanel calendarPanel = new CalendarPanel(new Schedule(), new AddressBook());
        calendarPlaceholder.getChildren().add(calendarPanel.getRoot());

        dashboardPanelPlaceholder.setVisible(false);
        browserPanelPlaceholder.setVisible(false);
        calendarPlaceholder.toFront();

        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    /**
     * Show the browser panel
     */
    private void showBrowserPanel() {
        dashboardPanelPlaceholder.setVisible(false);
        calendarPlaceholder.setVisible(false);
        browserPanelPlaceholder.setVisible(true);
        browserPanelPlaceholder.toFront();
    }

    /**
     * Show the dashboard panel
     */
    private void showDashboardPanel() {
        browserPanelPlaceholder.setVisible(false);
        calendarPlaceholder.setVisible(false);
        dashboardPanelPlaceholder.setVisible(true);
        dashboardPanelPlaceholder.toFront();
    }

    /**
     * Show the Calendar panel
     */
    private void showCalendarPanel() {
        browserPanelPlaceholder.setVisible(false);
        dashboardPanelPlaceholder.setVisible(false);
        calendarPlaceholder.setVisible(true);
        calendarPlaceholder.toFront();
    }

    @Subscribe
    public void handleStudentPanelSelectionChangedEvent(StudentPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showBrowserPanel();
        raise(new BrowserDisplayEvent(event.getNewSelection()));
    }

    @Subscribe
    public void handleShowStudentDashboardEvent(ShowStudentDashboardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showDashboardPanel();
        raise(new ShowStudentNameInDashboardEvent(event.getTargetStudent().getName()));
        raise(new ShowMilestonesEvent(event.getTargetStudent().getDashboard().getMilestoneList()));
    }

    @Subscribe
    public void handleShowScheduleEvent(ShowScheduleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showCalendarPanel();
        raise(new ScheduleChangedEvent(event.getSchedule(), event.getAddressBook()));
    }
}
