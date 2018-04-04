package seedu.address.model.activity;

import java.util.function.Predicate;

//@@author YuanQLLer
/**
 * This class gives a predicate that returns only the event in a list.
 */
public class EventOnlyPredicate implements Predicate<Activity> {

    public EventOnlyPredicate() {
        ;
    }

    @Override
    public boolean test(Activity activity) {
        return activity.getActivityType().equals("EVENT");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventOnlyPredicate); // instanceof handles nulls
    }

}
