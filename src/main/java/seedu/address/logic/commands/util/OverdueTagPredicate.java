package seedu.address.logic.commands.util;

import java.util.function.Predicate;

import seedu.address.model.activity.Activity;
import seedu.address.model.tag.Tag;

//@@author Kyomian
/**
 * Tests if an {@code Activity}'s {@code Tags} contains "Overdue".
 */
public class OverdueTagPredicate implements Predicate<Activity> {

    private final Tag overdueTag = new Tag("Overdue");

    @Override
    public boolean test(Activity activity) {
        return activity.getTags().contains(overdueTag);
    }
}
