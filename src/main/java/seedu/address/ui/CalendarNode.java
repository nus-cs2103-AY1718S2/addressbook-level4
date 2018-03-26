package seedu.address.ui;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;


/**
 * Create an anchor pane that can store additional data.
 */
public class CalendarNode extends AnchorPane {


    private LocalDate date;

    /**
     * Create a calendar node.
     */
    public CalendarNode() {
        super(FXML);

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AnchorPane getAP() {
        return ap;
    }

    public void setTaskList(ObservableList<Task> list) {
        taskList = list;
        task.setText(String.valueOf(taskList.size()) + "Tasks. ");
    }
}
