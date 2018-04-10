package seedu.address.logic.commands.util;

import java.util.HashSet;

import seedu.address.model.Model;
import seedu.address.model.activity.DateTime;
import seedu.address.model.activity.Event;
import seedu.address.model.activity.Location;
import seedu.address.model.activity.Name;
import seedu.address.model.activity.Remark;
import seedu.address.model.activity.Task;
import seedu.address.model.activity.exceptions.ActivityNotFoundException;
import seedu.address.model.activity.exceptions.DuplicateActivityException;
import seedu.address.model.tag.Tag;

//@@author Kyomian
/**
 * A utility class that tags task as overdue and event as finished.
 */
public class OverdueCheckerUtil {

    public static void markAsOverdue(Task task, Model model)
            throws ActivityNotFoundException, DuplicateActivityException {
        Task newTask = computeNewTask(task);
        model.updateActivity(task, newTask);
    }

    public static void markAsFinished(Event event, Model model)
            throws ActivityNotFoundException, DuplicateActivityException {
        Event newEvent = computeNewEvent(event);
        model.updateActivity(event, newEvent);
    }

    private static Task computeNewTask(Task task) {
        Name name = task.getName();
        DateTime dateTime = task.getDueDateTime();
        Remark remark = task.getRemark();
        Boolean isCompleted = task.isCompleted();

        HashSet<Tag> tags = new HashSet<>(task.getTags()); // copy constructor
        tags.add(new Tag("Overdue"));

        return new Task(name, dateTime, remark, tags, isCompleted);
    }

    private static Event computeNewEvent(Event event) {
        Name name = event.getName();
        DateTime startDateTime = event.getStartDateTime();
        DateTime endDateTime = event.getEndDateTime();
        Location location = event.getLocation();
        Remark remark = event.getRemark();
        Boolean isCompleted = event.isCompleted();

        HashSet<Tag> tags = new HashSet<>(event.getTags());
        tags.add(new Tag("Finished"));

        return new Event(name, startDateTime, endDateTime, location, remark, tags, isCompleted);
    }
}
