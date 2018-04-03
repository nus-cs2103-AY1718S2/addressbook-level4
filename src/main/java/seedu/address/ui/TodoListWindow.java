package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowTodoListDisplayContentEvent;
import seedu.address.model.event.Event;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;


import com.google.api.client.util.DateTime;
import com.google.common.eventbus.Subscribe;

import static java.util.Objects.requireNonNull;


/**
 * Controller for to do list window
 */
public class TodoListWindow{

    /** Resource folder where FXML files are stored. */
    public static final String FXML_FILE_FOLDER = "/view/";
    private static final Logger logger = LogsCenter.getLogger(TodoListWindow.class);
    private static final String FXML = "TodoListWindow.fxml";

    @FXML
    private Button addButton;

    @FXML
    private ListView<Event> eventList;

    ObservableList<Event> list = FXCollections.observableArrayList();

    @FXML
    private void add(javafx.event.Event event) {
        list.add(new Event("test", "NUS", new DateTime("2018-06-06T19:00:00")));
        eventList.setItems(list);
    }

    /**
     * Creates a new TodoListWindow.
     *
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
        logger.info(LogsCenter.getEventHandlingLogMessage(e));
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML));
            fxmlLoader.setLocation(getFxmlFileUrl(FXML));
            try {
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e2) {
            System.out.println(e2.getMessage());
        }
        //list.add(new Event("test", "NUS", new DateTime("2018-06-06T19:00:00")));
        //eventList.setItems(list);
    }

    /**
     * Returns the FXML file URL for the specified FXML file name within {@link #FXML_FILE_FOLDER}.
     */
    private static URL getFxmlFileUrl(String fxmlFileName) {
        requireNonNull(fxmlFileName);
        String fxmlFileNameWithFolder = FXML_FILE_FOLDER + fxmlFileName;
        URL fxmlFileUrl = MainApp.class.getResource(fxmlFileNameWithFolder);
        return requireNonNull(fxmlFileUrl);
    }
}
