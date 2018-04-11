package seedu.progresschecker.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.ui.JumpToListRequestEvent;
import seedu.progresschecker.model.issues.Issue;

//@@author adityaa1998
/**
 * Panel containing the issues on github.
 */
public class IssueListPanel extends UiPart<Region> {
    private static final String FXML = "IssueListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IssueListPanel.class);

    @javafx.fxml.FXML
    private ListView<IssueCard> issueListView;

    public IssueListPanel(ObservableList<Issue> issueList) {
        super(FXML);
        setConnections(issueList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Issue> issueList) {
        ObservableList<IssueCard> mappedList = EasyBind.map(
                issueList, (issue) -> new IssueCard(issue, issue.getIssueIndex()));
        issueListView.setItems(mappedList);
        issueListView.setCellFactory(listView -> new IssueListViewCell());
    }

    /**
     * Scrolls to the {@code IssueCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            issueListView.scrollTo(index);
            issueListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code IssueCard}.
     */
    class IssueListViewCell extends ListCell<IssueCard> {

        @Override
        protected void updateItem(IssueCard issue, boolean empty) {
            super.updateItem(issue, empty);

            if (empty || issue == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(issue.getRoot());
            }
        }
    }

}
