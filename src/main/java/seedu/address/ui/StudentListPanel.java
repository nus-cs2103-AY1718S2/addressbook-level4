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
 * Panel containing the list of students.
 */
public class StudentListPanel extends UiPart<Region> {

    private static final String FXML = "StudentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);

    @FXML
    private ListView<ClientCard> studentListView;

    private final ObservableList<Client> studentList;
    private final ObservableList<Client> closedStudentList;

    public StudentListPanel(ObservableList<Client> studentList, ObservableList<Client> closedStudentList) {
        super(FXML);
        this.studentList = studentList;
        this.closedStudentList = closedStudentList;
        setConnectionsForStudents();
        registerAsAnEventHandler(this);
    }

    private void setConnectionsForStudents() {
        ObservableList<ClientCard> mappedList = EasyBind.map(
                studentList, (client) -> new ClientCard(client, studentList.indexOf(client) + 1));
        studentListView.setItems(mappedList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setConnectionsForClosedStudents() {
        ObservableList<ClientCard> mappedList = EasyBind.map(
                closedStudentList, (client) -> new ClientCard(client, closedStudentList.indexOf(client) + 1));
        studentListView.setItems(mappedList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    //@@author olimhc
    /**
     * Switch the displayed student's list
     */
    private void switchListDisplay() {
        ListPanelController listPanelController = ListPanelController.getInstance();
        switch (listPanelController.getCurrentListDisplayed()) {
        case activeList:
            setConnectionsForClosedStudents();
            break;

        case closedList:
            setConnectionsForStudents();
            break;

        default:
            throw new AssertionError("This should not be possible.");
        }
    }
    //@@author

    private void setEventHandlerForSelectionChangeEvent() {
        studentListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in student list panel changed to : '" + newValue + "'");
                        raise(new ClientPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ClientCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            studentListView.scrollTo(index);
            studentListView.getSelectionModel().clearAndSelect(index);
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
