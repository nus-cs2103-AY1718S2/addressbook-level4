package seedu.address.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowTodoListDisplayContentEvent;
import seedu.address.model.listevent.ListEvent;


/**
 * Controller for to do list window
 */
public class TodoListWindow {

    private static final Logger logger = LogsCenter.getLogger(TodoListWindow.class);
    private static final String FXML = "TodoListWindow.fxml";

    private ObservableList<ListEvent> list = FXCollections.observableArrayList();

    @FXML
    private ListView<ListEvent> eventList;

    /**
     * Creates a new TodoListWindow.
     */
    public TodoListWindow() {
        registerAsAnEventHandler(this);
    }

    public void show() {
        logger.fine("Showing to do list.");
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleShowTodoListDisplayContentEvent(ShowTodoListDisplayContentEvent e) {

        ArrayList<ListEvent> allEvents = e.getListEvent();
        for (int i = 0; i < allEvents.size(); i++) {
            list.add(allEvents.get(i));
        }
        eventList.setItems(list);
    }

}
