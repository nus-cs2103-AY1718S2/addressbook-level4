//@@author kush1509
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JobPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToJobListRequestEvent;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.model.job.Job;

/**
 * Panel containing the list of jobs.
 */
public class JobListPanel extends UiPart<Region> {
    private static final String FXML = "JobListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(JobListPanel.class);

    @FXML
    private ListView<JobCard> jobListView;

    public JobListPanel(ObservableList<Job> jobList) {
        super(FXML);
        setConnections(jobList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Job> jobList) {
        ObservableList<JobCard> mappedList = EasyBind.map(
                jobList, (job) -> new JobCard(job, jobList.indexOf(job) + 1));
        jobListView.setItems(mappedList);
        jobListView.setCellFactory(listView -> new JobListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        jobListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in job list panel changed to : '" + newValue + "'");
                        raise(new JobPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code JobCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            jobListView.scrollTo(index);
            jobListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToJobListRequestEvent(JumpToJobListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code JobCard}.
     */
    class JobListViewCell extends ListCell<JobCard> {

        @Override
        protected void updateItem(JobCard job, boolean empty) {
            super.updateItem(job, empty);

            if (empty || job == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(job.getRoot());
            }
        }
    }
}
