//@@author nhatquang3112
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ToDoPanelSelectionChangedEvent;
import seedu.address.model.todo.ToDo;

/**
 * Panel containing the list of to-dos.
 */
public class ToDoListPanel extends UiPart<Region> {
    private static final String FXML = "ToDoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ToDoListPanel.class);

    @FXML
    private ListView<ToDoCard> toDoListView;

    public ToDoListPanel(ObservableList<ToDo> toDoList) {
        super(FXML);
        setConnections(toDoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ToDo> toDoList) {
        ObservableList<ToDoCard> mappedList = EasyBind.map(
                toDoList, (toDo) -> new ToDoCard(toDo, toDoList.indexOf(toDo) + 1));
        toDoListView.setItems(mappedList);
        toDoListView.setCellFactory(listView -> new ToDoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        toDoListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in to-do list panel changed to : '" + newValue + "'");
                        raise(new ToDoPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ToDoCard}.
     */
    class ToDoListViewCell extends ListCell<ToDoCard> {

        @Override
        protected void updateItem(ToDoCard toDo, boolean empty) {
            super.updateItem(toDo, empty);

            if (empty || toDo == null) {
                setGraphic(null);
                setText(null);
                return;
            }

            this.getStylesheets().clear();
            if (toDo.isDone()) {
                this.getStylesheets().add("view/ToDoDone.css");
            } else {
                this.getStylesheets().add("view/ToDoUnDone.css");
            }
            setGraphic(toDo.getRoot());
        }
    }
}
