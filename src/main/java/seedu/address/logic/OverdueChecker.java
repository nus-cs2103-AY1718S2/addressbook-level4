package seedu.address.logic;

import static seedu.address.logic.commands.util.OverdueCheckerUtil.markAsFinished;
import static seedu.address.logic.commands.util.OverdueCheckerUtil.markAsOverdue;

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
 * A completed task will not be tagged as overdue, even though it has passed its due date.
 * An event that has passed its end date is tagged as finished.
 *
 * This class also records the number of tasks tagged "Overdue".
 */
public class OverdueChecker implements Runnable {

    private Model model;
    private ObservableList<Activity> taskList;
    private ObservableList<Activity> eventList;
    private static int numOverdueTasks;

    // Constructor
    public OverdueChecker(Model model) {
        this.model = model;
        taskList = model.getFilteredTaskList();
        eventList = model.getFilteredEventList();
    }

    public void run() {
        numOverdueTasks = 0;
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Platform.runLater is needed as you cannot update UI components
        // from a thread other than the JavaFx Application thread
        Platform.runLater(() -> {
            try {
                markingAsOverdue(taskList, currentDateTime);
                markingAsFinished(eventList, currentDateTime);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace(); // impossible to reach here
            } catch (DuplicateActivityException e) {
                e.printStackTrace(); // impossible to reach here
            }
        });
    }

    private void markingAsOverdue(ObservableList<Activity> taskList, LocalDateTime currentDateTime)
            throws ActivityNotFoundException, DuplicateActivityException {
        for (int i = 0; i < taskList.size(); i++) {
            Task task = (Task) taskList.get(i);
            if (task.getDueDateTime().getLocalDateTime().isBefore(currentDateTime)
                    && !task.isCompleted()) {
                markAsOverdue(task, model);
                numOverdueTasks++;
            }
        }
    }

    private void markingAsFinished(ObservableList<Activity> eventList, LocalDateTime currentDateTime)
            throws ActivityNotFoundException, DuplicateActivityException {
        for (int i = 0; i < eventList.size(); i++) {
            Event event = (Event) eventList.get(i);
            if (event.getEndDateTime().getLocalDateTime().isBefore(currentDateTime)) {
                markAsFinished(event, model);
            }
        }
    }

    public static int getNumOverdueTasks() {
        return numOverdueTasks;
    }
}
