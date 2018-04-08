package seedu.address.logic;

import static seedu.address.logic.commands.util.OverdueCheckerUtil.isMarkedCompleted;
import static seedu.address.logic.commands.util.OverdueCheckerUtil.isMarkedOverdue;

import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;

//@@author Kyomian
/**
 * A class that checks if tasks and events have passed their due dates and end dates
 * respectively.
 *
 * A task that has passed its due date is tagged as overdue.
 * An event that has passed its end date is tagged as finished.
 */
public class OverdueChecker implements Runnable {

    private Model model;
    private ObservableList<Activity> taskList;
    private ObservableList<Activity> eventList;

    // Constructor
    public OverdueChecker(Model model) {
        this.model = model;
        taskList = model.getFilteredTaskList();
        eventList = model.getFilteredEventList();
    }

    public void run() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Platform.runLater is needed as you cannot update UI components
        // from a thread other than the JavaFx Application thread
        Platform.runLater(() -> {
            try {
                markAsOverdue(taskList, currentDateTime);
                markAsCompleted(eventList, currentDateTime);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            } catch (DuplicateActivityException e) {
                e.printStackTrace();
            }
        });
    }

    private void markAsOverdue(ObservableList<Activity> taskList, LocalDateTime currentDateTime)
            throws ActivityNotFoundException, DuplicateActivityException {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = (Task) taskList.get(i);
            if (task.getDueDateTime().getLocalDateTime().isBefore(currentDateTime)) {
                isMarkedOverdue(task, model);
            }
        }
    }

    private void markAsCompleted(ObservableList<Activity> eventList, LocalDateTime currentDateTime)
            throws ActivityNotFoundException, DuplicateActivityException {
        for (int i = 0; i < eventList.size(); i++) {
            Event event = (Event) eventList.get(i);
            if (event.getEndDateTime().getLocalDateTime().isBefore(currentDateTime)) {
                isMarkedCompleted(event, model);
            }
        }
    }
}
