package seedu.address.model.activity;

import java.util.function.Predicate;

//@@author YuanQLLer
/**
 * This class gives a predicate that returns only the tasks in a list.
 */
public class TaskOnlyPredicate implements Predicate<Activity> {
    public TaskOnlyPredicate() {
        ;
    }

    @Override
    public boolean test(Activity activity) {
        return activity.getActivityType().equals("TASK");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskOnlyPredicate); // instanceof handles nulls
    }

}
