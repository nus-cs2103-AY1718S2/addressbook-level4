package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowMilestonesEvent;
import seedu.address.commons.events.ui.ShowStudentNameInDashboardEvent;
import seedu.address.model.student.Name;
import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.dashboard.Milestone;

//@@author yapni
/**
 * Panel containing the student's dashboard.
 */
public class DashboardPanel extends UiPart<Region> {

    private static final String FXML = "DashboardPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Label studentName;

    @FXML
    private ListView<MilestoneCard> milestoneListView;

    public DashboardPanel() {
        super(FXML);
        loadDefaultDashboard();

        registerAsAnEventHandler(this);
    }

    /**
     * Loads the default dashboard at start up
     */
    private void loadDefaultDashboard() {
        studentName.setText("John Doe");
        ObservableList<MilestoneCard> milestoneCardList = FXCollections.observableArrayList();
        milestoneCardList.add(new MilestoneCard(new Milestone(
                new Date("31/12/2018 23:59"), "placeholder"), 1));
        milestoneListView.setItems(milestoneCardList);
        milestoneListView.setCellFactory(listView -> new MilestoneListViewCell());
    }

    /**
     * Loads the list of milestones into the dashboard
     */
    private void loadMilestoneList(ObservableList<Milestone> milestoneList) {
        ObservableList<MilestoneCard> mappedList = EasyBind.map(
                milestoneList, (milestone) -> new MilestoneCard(milestone, milestoneList.indexOf(milestone) + 1));
        milestoneListView.setItems(mappedList);
    }

    private void loadStudentName(Name name) {
        studentName.setText(name.fullName);
    }

    @Subscribe
    public void handleShowMilestonesEvent(ShowMilestonesEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMilestoneList(event.getMilestoneList().asObservableList());
    }

    @Subscribe
    public void handleShowStudentNameInDashboardEvent(ShowStudentNameInDashboardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadStudentName(event.getName());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MilestoneCard}.
     */
    class MilestoneListViewCell extends ListCell<MilestoneCard> {

        @Override
        protected void updateItem(MilestoneCard milestone, boolean empty) {
            super.updateItem(milestone, empty);

            if (empty || milestone == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(milestone.getRoot());
            }
        }
    }
}
