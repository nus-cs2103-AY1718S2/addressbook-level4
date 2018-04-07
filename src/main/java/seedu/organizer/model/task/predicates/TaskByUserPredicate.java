package seedu.organizer.model.task.predicates;

import java.util.function.Predicate;

import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Tests that a {@code Task}'s {@code User} matches the given user.
 */
public class TaskByUserPredicate implements Predicate<Task> {

    private final User user;

    public TaskByUserPredicate(User user) {
        this.user = user;
    }

    @Override
    public boolean test(Task task) {
        return task.getUser().equals(user);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskByUserPredicate // instanceof handles nulls
                && this.user.equals(((TaskByUserPredicate) other).user)); // state check
    }
}
