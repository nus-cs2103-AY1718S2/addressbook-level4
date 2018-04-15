//@@author deeheenguyen
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InterviewPanelSelectionChangedEvent;
import seedu.address.model.interview.Interview;

/**
 * Panel containing the list of jobs.
 */
public class InterviewListPanel extends UiPart<Region> {
    private static final String FXML = "InterviewListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InterviewListPanel.class);

    @FXML
    private ListView<InterviewCard> interviewListView;

    public InterviewListPanel(ObservableList<Interview> interviewList) {
        super(FXML);
        setConnections(interviewList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Interview> interviewList) {
        ObservableList<InterviewCard> mappedList = EasyBind.map(
                interviewList, (interview) ->
                        new InterviewCard(interview, interviewList.indexOf(interview) + 1));
        interviewListView.setItems(mappedList);
        interviewListView.setCellFactory(listView -> new InterviewListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        interviewListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in interview list panel changed to : '" + newValue + "'");
                        raise(new InterviewPanelSelectionChangedEvent(newValue));
                    }
                });
    }


    /**
     * Scrolls to the {@code JobCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            interviewListView.scrollTo(index);
            interviewListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InterviewCard}.
     */
    class InterviewListViewCell extends ListCell<InterviewCard> {

        @Override
        protected void updateItem(InterviewCard interview, boolean empty) {
            super.updateItem(interview, empty);

            if (empty || interview == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(interview.getRoot());
            }
        }
    }


}
