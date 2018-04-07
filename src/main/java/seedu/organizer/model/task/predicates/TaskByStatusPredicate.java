package seedu.organizer.model.task.predicates;

//@@author dominickenn

import java.util.function.Predicate;

import seedu.organizer.model.task.Status;
import seedu.organizer.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code Status} matches the given status.
 */
public class TaskByStatusPredicate implements Predicate<Task> {

    private final Status status;

    public TaskByStatusPredicate(Status status) {
        this.status = status;
    }

    @Override
    public boolean test(Task task) {
        return task.getStatus().equals(status);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskByStatusPredicate // instanceof handles nulls
                && this.status.equals(((TaskByStatusPredicate) other).status)); // state check
    }
}

