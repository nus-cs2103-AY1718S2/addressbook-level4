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
import seedu.address.commons.events.ui.ToDoPanelSelectionChangedEvent;
import seedu.address.model.todo.ToDo;

/**
 * Panel containing the list of todos.
 */
public class ToDoListPanel extends UiPart<Region> {
    private static final String FXML = "ToDoListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ToDoListPanel.class);

    @FXML
    private ListView<ToDoCard> todoListView;

    public ToDoListPanel(ObservableList<ToDo> todoList) {
        super(FXML);
        setConnections(todoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ToDo> todoList) {
        ObservableList<ToDoCard> mappedList = EasyBind.map(
                todoList, (todo) -> new ToDoCard(todo, todoList.indexOf(todo) + 1));
        todoListView.setItems(mappedList);
        todoListView.setCellFactory(listView -> new ToDoListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        todoListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in todo list panel changed to : '" + newValue + "'");
                        raise(new ToDoPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code ToDoCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            todoListView.scrollTo(index);
            todoListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ToDoCard}.
     */
    class ToDoListViewCell extends ListCell<ToDoCard> {

        @Override
        protected void updateItem(ToDoCard todo, boolean empty) {
            super.updateItem(todo, empty);

            if (empty || todo == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(todo.getRoot());
            }
        }
    }

}
