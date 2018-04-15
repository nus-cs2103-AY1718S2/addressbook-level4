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
import seedu.address.commons.events.ui.ClientListSwitchEvent;
import seedu.address.commons.events.ui.ClientPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.person.Client;
import seedu.address.ui.util.ListPanelController;

/**
 * Panel containing the list of tutors.
 */
public class TutorListPanel extends UiPart<Region> {

    private static final String FXML = "TutorListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TutorListPanel.class);

    @FXML
    private ListView<ClientCard> tutorListView;

    private final ObservableList<Client> tutorList;
    private final ObservableList<Client> closedTutorList;

    public TutorListPanel(ObservableList<Client> tutorList, ObservableList<Client> closedTutorList) {
        super(FXML);
        this.tutorList = tutorList;
        this.closedTutorList = closedTutorList;
        setConnectionsForTutors();
        registerAsAnEventHandler(this);
    }

    private void setConnectionsForTutors() {
        ObservableList<ClientCard> mappedList = EasyBind.map(
                tutorList, (client) -> new ClientCard(client, tutorList.indexOf(client) + 1));
        tutorListView.setItems(mappedList);
        tutorListView.setCellFactory(listView -> new StudentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setConnectionsForClosedTutors() {
        ObservableList<ClientCard> mappedList = EasyBind.map(
                closedTutorList, (client) -> new ClientCard(client, closedTutorList.indexOf(client) + 1));
        tutorListView.setItems(mappedList);
        tutorListView.setCellFactory(listView -> new StudentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    //@@author olimhc
    /**
     * Switch the displayed tutor's list
     */
    private void switchListDisplay() {
        ListPanelController listPanelController = ListPanelController.getInstance();
        switch (listPanelController.getCurrentListDisplayed()) {
        case activeList:
            setConnectionsForClosedTutors();
            break;

        case closedList:
            setConnectionsForTutors();
            break;

        default:
            throw new AssertionError("This should not be possible.");
        }
    }
    //@@author

    private void setEventHandlerForSelectionChangeEvent() {
        tutorListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in tutor list panel changed to : '" + newValue + "'");
                        raise(new ClientPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ClientCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            tutorListView.scrollTo(index);
            tutorListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    //@@author olimhc
    @Subscribe
    private void handleClientListSwitchEvent(ClientListSwitchEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchListDisplay();
    }
    //@@author

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ClientCard}.
     */
    class StudentListViewCell extends ListCell<ClientCard> {

        @Override
        protected void updateItem(ClientCard client, boolean empty) {
            super.updateItem(client, empty);

            if (empty || client == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(client.getRoot());
            }
        }
    }

}
