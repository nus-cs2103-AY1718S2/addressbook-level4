package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserDisplayEvent;
import seedu.address.commons.events.ui.ShowMilestonesEvent;
import seedu.address.commons.events.ui.ShowStudentDashboardEvent;
import seedu.address.commons.events.ui.ShowStudentNameInDashboardEvent;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;

//@@author yapni
/**
 * Panel that contains the browser panel and the dashboard panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private DashboardPanel dashboardPanel;

    @FXML
    private StackPane browserPanelPlaceholder;

    @FXML
    private StackPane dashboardPanelPlaceholder;

    public InfoPanel() {
        super(FXML);

        browserPanel = new BrowserPanel();
        browserPanelPlaceholder.getChildren().add(browserPanel.getRoot());

        dashboardPanel = new DashboardPanel();
        dashboardPanelPlaceholder.getChildren().add(dashboardPanel.getRoot());

        browserPanelPlaceholder.toFront();

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
        browserPanelPlaceholder.setVisible(true);
        browserPanelPlaceholder.toFront();
    }

    /**
     * Show the dashboard panel
     */
    private void showDashboardPanel() {
        browserPanelPlaceholder.setVisible(false);
        dashboardPanelPlaceholder.setVisible(true);
        dashboardPanelPlaceholder.toFront();
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
}
