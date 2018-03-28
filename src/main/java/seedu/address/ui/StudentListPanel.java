package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.StudentPanelSelectionChangedEvent;
import seedu.address.model.student.Student;

/**
 * Panel containing the list of students.
 */
public class StudentListPanel extends UiPart<Region> {
    private static final String FXML = "StudentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudentListPanel.class);

    @FXML
    private ListView<StudentCard> studentListView;

    public StudentListPanel(ObservableList<Student> studentList) {
        super(FXML);
        setConnections(studentList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Student> studentList) {
        ObservableList<StudentCard> mappedList = EasyBind.map(
                studentList, (student) -> new StudentCard(student, studentList.indexOf(student) + 1));
        studentListView.setItems(mappedList);
        studentListView.setCellFactory(listView -> new StudentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        studentListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in student list panel changed to : '" + newValue + "'");
                        raise(new StudentPanelSelectionChangedEvent(newValue));
                    } else {
                        raise(new StudentPanelSelectionChangedEvent(oldValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code StudentCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        studentListView.scrollTo(index);
        studentListView.getSelectionModel().clearAndSelect(index);
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        int beforeSelectedIndex = studentListView.getSelectionModel().getSelectedIndex();

        scrollTo(event.targetIndex);

        /* To handle to case where user selects the current student card after the show dashboard command */
        if (event.targetIndex == beforeSelectedIndex) {
            raise(new StudentPanelSelectionChangedEvent(studentListView.getSelectionModel().getSelectedItem()));
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudentCard}.
     */
    class StudentListViewCell extends ListCell<StudentCard> {

        @Override
        protected void updateItem(StudentCard student, boolean empty) {
            super.updateItem(student, empty);

            if (empty || student == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(student.getRoot());
            }
        }
    }

}
